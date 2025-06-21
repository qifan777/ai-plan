import { useImmer } from "use-immer";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
import { Button, Popup } from "@nutui/nutui-react-taro";
import { Image, Input, ScrollView } from "@tarojs/components";
import SessionItem from "@/pages/chat/components/session-item";
import { AiMessageInput } from "@/apis/__generated/model/static";
import { createParser, EventSourceMessage } from "eventsource-parser";
import _ from "lodash";
import { Del, Plus } from "@nutui/icons-react-taro";
import commentIcon from "@/assets/icons/comment.png";
import UserAvatar from "@/components/user/user-avatar";
import { AiSessionDto } from "@/apis/__generated/model/dto";
import "./chat.scss";
import markdownTowXml from "../../towxml";

type ChatResponse = {
  metadata: {
    usage: {
      totalTokens: number;
    };
  };
  result: {
    metadata: {
      finishReason: string;
    };
    output: {
      messageType: string;
      text: string;
    };
  };
};
export type AiMessage = Pick<
  AiMessageInput,
  "textContent" | "medias" | "type" | "sessionId"
> & {
  id: string;
};

export default function Chat() {
  const [sessionList, setSessionList] = useImmer<
    AiSessionDto["AiSessionRepository/COMPLEX_FETCHER_FOR_FRONT"][]
  >([]);

  const loadSession = async () => {
    const list = (
      await api.aiSessionForFrontController.query({
        body: { pageNum: 1, pageSize: 1000, query: {} },
      })
    ).content;
    setSessionList([...list]);
    if (list.length > 0) {
      changeSession(0, list);
    }
  };
  const [visible, setVisible] = useImmer(false);
  const createSession = async (name: string = "新的聊天") => {
    const res = await api.aiSessionForFrontController.save({
      body: {
        name,
      },
    });
    await loadSession();
    return res;
  };
  const handleClearSession = () => {
    Taro.showModal({
      title: "是否确认清空所有会话",
      async success(res) {
        if (res.confirm) {
          await api.aiMessageForFrontController.delete({
            body: sessionList.map((item) => item.id),
          });
          await loadSession();
        }
      },
    });
  };
  const [activeIndex, setActiveIndex] = useImmer(-1);
  const [messageList, setMessageList] = useImmer<AiMessage[]>([]);
  const changeSession = (
    index: number,
    sessions: ReadonlyArray<
      AiSessionDto["AiSessionRepository/COMPLEX_FETCHER_FOR_FRONT"]
    >,
  ) => {
    setActiveIndex(index);
    setMessageList([...sessions[index].messages]);
  };
  const [message, setMessage] = useImmer("");
  const scrollToBottom = _.throttle(() => {
    Taro.pageScrollTo({
      selector: ".last-row",
      duration: 50,
    });
  }, 50);
  Taro.useLoad(async () => {
    await loadSession();
  });
  const handleSendMessage = async () => {
    Taro.showLoading();
    if (activeIndex < 0) {
      Taro.showToast({ title: "请新建会话" });
      return;
    }
    const activeSession = sessionList[activeIndex];
    // 用户的提问
    const chatMessage = {
      id: new Date().getTime().toString(),
      sessionId: activeSession.id,
      textContent: message,
      type: "USER",
    } satisfies AiMessage;
    const responseMessage = {
      id: new Date().getTime().toString(),
      medias: [],
      type: "ASSISTANT",
      textContent: "",
      sessionId: activeSession.id,
    } satisfies AiMessage;
    const chatRes = Taro.request({
      url: (process.env.TARO_APP_API ?? "") + "/front/ai-message/chat",
      header: {
        token: Taro.getStorageSync("token"),
      },
      enableChunked: true,
      method: "POST",
      data: chatMessage,
    });

    const parser = createParser({ onEvent });
    parser.reset();
    chatRes.onChunkReceived(async (res) => {
      const decodeRes = new TextDecoder().decode(res.data);
      parser.feed(decodeRes);
    });
    setMessageList((draft) => {
      draft.push(...[chatMessage, responseMessage]);
    });
    setMessage("");
    let responseText = "";
    async function onEvent(event: EventSourceMessage) {
      Taro.hideLoading();
      if (event.event == "error") {
        setMessageList((draft) => {
          draft[draft.length - 1].textContent = event.data;
        });
        return;
      }
      const response = JSON.parse(event.data) as ChatResponse;
      responseText += response.result.output.text;
      setMessageList((draft) => {
        draft[draft.length - 1].textContent += response.result.output.text;
      });
      scrollToBottom();
      if (response.result.metadata.finishReason == "STOP") {
        console.log(messageList);
        await api.aiMessageForFrontController.save({
          body: chatMessage,
        });
        await api.aiMessageForFrontController.save({
          body: { ...responseMessage, textContent: responseText },
        });
        return;
      }
    }
  };
  const getMarkdownXml = (content: string) => {
    return markdownTowXml(content, "markdown", {
      theme: "light", // 主题，默认`light`
      events: {
        // 为元素绑定的事件方法
        tap: (e) => {
          console.log("tap", e);
        },
      },
    });
  };

  return (
    <div className="chat-page">
      <Popup
        style={{ height: "100%", width: "550rpx" }}
        visible={visible}
        onClose={() => setVisible(false)}
        position="left"
      >
        <ScrollView className="scroll-view" scrollY>
          {sessionList.map((row, index) => {
            return (
              <div
                onClick={() => changeSession(index, sessionList)}
                key={row.id}
              >
                <SessionItem
                  active={activeIndex == index}
                  session={row}
                  onUpdateSession={(session) => {
                    setSessionList(async (draft) => {
                      draft[index].name = session.name;
                    });
                  }}
                  onDelete={loadSession}
                ></SessionItem>
              </div>
            );
          })}
          <div className="buttons">
            <Button
              className="new-session"
              type="info"
              icon={<Plus />}
              onClick={async () => {
                await createSession();
              }}
            >
              新建会话
            </Button>
            <Button
              className="clear-session"
              type="primary"
              icon={<Del />}
              onClick={() => {
                handleClearSession();
              }}
            >
              清空会话
            </Button>
          </div>
        </ScrollView>
      </Popup>
      <div className="messages">
        {messageList.map((row, index) => {
          // @ts-ignore
          return (
            <div
              className={[
                "row",
                index == messageList.length - 1 ? "last-row" : "",
              ].join(" ")}
              key={row.id}
            >
              {row.type == "ASSISTANT" ? (
                <div className="chat-row chat-left">
                  <div className="avatar">
                    <UserAvatar width="65rpx" radius="10rpx"></UserAvatar>
                  </div>
                  <div className="message">
                    <towxml nodes={getMarkdownXml(row.textContent)} />
                  </div>
                </div>
              ) : (
                <div className="chat-row chat-right">
                  <div className="message">
                    <div className="text-message">{row.textContent}</div>
                  </div>
                  <div className="avatar">
                    <UserAvatar width="65rpx" radius="10rpx"></UserAvatar>
                  </div>
                </div>
              )}
            </div>
          );
        })}
      </div>
      <div className="bottom">
        <div
          className="bottom-icon"
          onClick={() => {
            setVisible(true);
          }}
        >
          <Image src={commentIcon}></Image>
        </div>
        <div className="mid">
          <div className="input-view">
            <Input
              type="text"
              value={message}
              onInput={(event) => {
                setMessage(event.detail.value);
              }}
              onConfirm={async () => {
                await handleSendMessage();
              }}
            />
          </div>
        </div>
        <div
          className="right"
          onClick={async () => {
            await handleSendMessage();
          }}
        >
          发送
        </div>
      </div>
    </div>
  );
}
