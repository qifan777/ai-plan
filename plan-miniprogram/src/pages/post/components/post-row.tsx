import UserAvatar from "@/components/user/user-avatar";
import { Post } from "@/pages/post/post";
import { Image, Text } from "@tarojs/components";
import dayjs from "dayjs";
import PostMenu from "@/pages/post/components/post-menu";
import likeIcon from "@/assets/icons/like.png";
import likeRedIcon from "@/assets/icons/like-red.png";
import commentIcon from "@/assets/icons/comment.png";
import deleteIcon from "@/assets/icons/delete.png";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import { useAppSelector } from "@/hook";
import { selectUserInfo } from "@/features/user/userSlice";
import CommentList from "@/pages/post/components/comment-list";
import "./post-row.scss";

export default function PostRow({
  post,
  onReply,
  onComment,
  onRefresh,
}: {
  post: Post;
  onReply: (parentId: string) => void;
  onComment: () => void;
  onRefresh: (all?: boolean) => void;
}) {
  const userInfo = useAppSelector(selectUserInfo);

  const handlePreview = (index: number) => {
    Taro.previewImage({
      current: index,
      urls: post.pictures as string[],
    });
  };

  const handleDelete = async (id: string) => {
    await api.postCommentForFrontController.delete({ body: [id] });
    Taro.showToast({ title: "删除成功", icon: "success" });
    onRefresh();
  };
  const handleReply = (parentId: string) => {
    onReply(parentId);
  };
  const myLike = post.likes
    .filter((like) => like.creator.id == userInfo?.id)
    .map((like) => like.id);
  const handleDeleteLike = async () => {
    await api.postLikeForFrontController.delete({ body: myLike });
    onRefresh();
  };
  const handleLike = async () => {
    await api.postLikeForFrontController.save({
      body: { postId: post.id },
    });
    onRefresh();
  };
  const handleComment = () => {
    onComment();
  };
  const handleDeletePost = async () => {
    console.log("delete");
    await api.postForFrontController.delete({
      body: [post.id],
    });
    onRefresh(true);
  };
  return (
    <div className="post-row">
      <div className="avatar">
        <UserAvatar src={post.creator.avatar} width="90rpx"></UserAvatar>
      </div>
      <div className="content">
        <div className="name">{post.creator.nickname}</div>
        <div className="text">{post.content}</div>
        <div className="pictures">
          {post.pictures.map((pic, index) => {
            return (
              <div
                className="picture"
                key={pic}
                onClick={() => handlePreview(index)}
              >
                <Image src={pic} mode="aspectFill" />
              </div>
            );
          })}
        </div>
        <div className="date-menu">
          <div className="date">{dayjs(post.createdTime).fromNow()}</div>
          <PostMenu
            length={userInfo?.id === post.creator.id ? 3 : 2}
            menuList={
              <div className="menu-buttons">
                {myLike.length ? (
                  <div className="button" onClick={handleDeleteLike}>
                    <Image src={likeRedIcon} mode="heightFix"></Image>
                    <div className="button-text">取消</div>
                  </div>
                ) : (
                  <div className="button" onClick={handleLike}>
                    <Image src={likeIcon} mode="heightFix"></Image>
                    <div className="button-text">赞</div>
                  </div>
                )}
                <div className="divider"></div>
                <div className="button" onClick={handleComment}>
                  <Image src={commentIcon} mode="heightFix"></Image>
                  <div className="button-text">留言</div>
                </div>
                {userInfo?.id == post.creator.id
                  ? [
                      <div className="divider" key={1}></div>,
                      <div
                        className="button"
                        onClick={handleDeletePost}
                        key={2}
                      >
                        <Image src={deleteIcon} mode="heightFix"></Image>
                        <div className="button-text">删除</div>
                      </div>,
                    ]
                  : ""}
              </div>
            }
          ></PostMenu>
        </div>
        {post.comments.length || post.likes.length ? (
          <div className="comment-like">
            {post.likes.length ? (
              <div className="like-list">
                <Image src={likeRedIcon} mode="widthFix" className="icon" />
                {post.likes.map((like, index) => (
                  <div className="like-name" key={like.id}>
                    {like.creator.nickname}
                    {index != post.likes.length - 1 ? <Text>,</Text> : ""}
                  </div>
                ))}
              </div>
            ) : (
              ""
            )}
            {post.comments.length && post.likes.length ? (
              <div className="divider"></div>
            ) : (
              ""
            )}
            <CommentList
              onReply={handleReply}
              onDelete={handleDelete}
              comments={post.commentList}
            ></CommentList>
          </div>
        ) : (
          ""
        )}
      </div>
    </div>
  );
}
