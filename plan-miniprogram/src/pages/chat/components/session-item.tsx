import { AiSessionDto } from "@/apis/__generated/model/dto";
import commentIcon from "@/assets/icons/comment.png";
import { Image } from "@tarojs/components";
import { Input } from "@nutui/nutui-react-taro";
import { useImmer } from "use-immer";
import { Check, Close, Del, Edit } from "@nutui/icons-react-taro";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import "./session-item.scss";

type AiSession = AiSessionDto["AiSessionRepository/COMPLEX_FETCHER_FOR_FRONT"];

export default function SessionItem({
  active,
  session,
  onUpdateSession,
  onDelete,
}: {
  active: boolean;
  session: AiSession;
  onUpdateSession: (session: AiSession) => void;
  onDelete: () => void;
}) {
  const [name, setName] = useImmer("");
  const [edit, setEdit] = useImmer(false);
  const handleDelete = () => {
    Taro.showModal({
      title: "是否确认删除该会话",
      async success(res) {
        if (res.confirm) {
          await api.aiSessionForFrontController.delete({ body: [session.id] });
          onDelete();
        }
      },
    });
  };
  const handleConfirm = async () => {
    await api.aiSessionForFrontController.save({
      body: { ...session, name },
    });
    onUpdateSession({ ...session, name });
    setEdit(false);
    Taro.showToast({ title: "修改成功", icon: "success" });
  };
  const handleClose = () => {
    setEdit(false);
    setName(session.name);
  };
  const handleEdit = () => {
    setName(session.name);
    setEdit(true);
  };
  return (
    <div className={["session-item", active ? "active" : ""].join(" ")}>
      <Image src={commentIcon} className="icon"></Image>
      <div className="info">
        {edit ? (
          <Input
            className="input-name"
            value={name}
            onChange={(e) => {
              setName(e);
            }}
            clearIcon={
              <div>
                <Close className="btn1" size={14} onClick={handleClose}></Close>
                <Check
                  className="btn2"
                  size={14}
                  onClick={handleConfirm}
                ></Check>
              </div>
            }
          ></Input>
        ) : (
          <div className="name">{session.name}</div>
        )}
        <div className="time-count">
          <div className="count">{session.messages.length}条对话</div>
          <div className="time">{session.createdTime}</div>
        </div>
      </div>
      <Edit className="btn" size={14} onClick={handleEdit}></Edit>
      <Del className="btn2" size={14} onClick={handleDelete}></Del>
    </div>
  );
}
