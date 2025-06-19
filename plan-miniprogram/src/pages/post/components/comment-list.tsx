import { PostCommentResponse } from "@/pages/post/components/comment";
import { useAppSelector } from "@/hook";
import { selectUserInfo } from "@/features/user/userSlice";
import { useImmer } from "use-immer";
import Taro from "@tarojs/taro";
import { ActionSheet } from "@nutui/nutui-react-taro";
import { Text } from "@tarojs/components";
import "./comment-list.scss";

export default function CommentList({
  comments,
  onReply,
  onDelete,
}: {
  comments?: PostCommentResponse[];
  onReply: (parentId: string) => void;
  onDelete: (id: string) => void;
}) {
  const userInfo = useAppSelector(selectUserInfo);

  const handleCommentClick = (comment: PostCommentResponse) => {
    if (userInfo?.id == comment.creator.id) {
      setActive(comment);
      setShow(true);
      return;
    }
    onReply(comment.id);
  };
  const [active, setActive] = useImmer<PostCommentResponse | undefined>(
    undefined,
  );
  const [show, setShow] = useImmer(false);
  const menuItems = [
    {
      name: "复制",
    },
    {
      name: "删除",
    },
    {
      name: "回复",
    },
  ];
  const handleChoose = (item: { name: string }) => {
    if (!active) return;
    if (item.name == "删除") {
      return onDelete(active.id);
    }
    if (item.name == "复制") {
      Taro.setClipboardData({
        data: active.content,
      });
    }
    if (item.name == "回复") {
      onReply(active.id);
    }
  };
  if (!comments) return <div></div>;
  return (
    <div className="comment-list">
      {comments.map((comment) => {
        return (
          <div className="comment" key={comment.id}>
            <div
              className="comment-item"
              onClick={() => handleCommentClick(comment)}
            >
              <div className="nickname">{comment.creator.nickname}</div>
              {comment.parent
                ? [
                    <div className="reply" key={1}>
                      回复
                    </div>,
                    <div className="nickname" key={2}>
                      {comment.parent.creator.nickname}
                    </div>,
                  ]
                : []}
              <Text>:</Text>
              <div className="content">{comment.content}</div>
            </div>
            <div className="children">
              <CommentList
                onReply={onReply}
                onDelete={onDelete}
                comments={comment.children}
              ></CommentList>
            </div>
          </div>
        );
      })}
      <ActionSheet
        options={menuItems}
        visible={show}
        onClose={() => setShow(false)}
        cancelText="取消"
        onSelect={handleChoose}
      ></ActionSheet>
    </div>
  );
}
