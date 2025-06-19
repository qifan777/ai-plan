import { Popup } from "@nutui/nutui-react-taro";
import { Button, Textarea } from "@tarojs/components";
import { useImmer } from "use-immer";
import "./comment-title.scss";

export default function CommentTitle({
  visible,
  placeholder = "说点什么吧",
  tabBar,
  onClose,
  onPublish,
}: {
  visible: boolean;
  placeholder?: string;
  tabBar?: boolean;
  onClose: () => void;
  onPublish: (content: string) => void;
}) {
  const [content, setContent] = useImmer("");
  const [bottom, setBottom] = useImmer("0px");

  const handlePublish = () => {
    onPublish(content);
    setContent("");
  };

  return (
    <div className="comment-title">
      <Popup visible={visible} onClose={onClose} round position="bottom">
        <div className="comment-wrapper">
          <div className="comment-input" style={{ paddingTop: bottom }}>
            <Textarea
              className="input"
              placeholder={placeholder}
              value={content}
              onInput={(event) => {
                setContent(event.detail.value);
              }}
              autoHeight
              showConfirmBar={false}
              maxlength={2000}
              adjustPosition={false}
              onKeyboardHeightChange={(event) => {
                setBottom(
                  (event.detail.height
                    ? event.detail.height - (tabBar ? 50 : 0)
                    : 0) + "px",
                );
              }}
            ></Textarea>
            <Button className="submit" onClick={handlePublish}>
              发送
            </Button>
          </div>
        </div>
      </Popup>
    </div>
  );
}
