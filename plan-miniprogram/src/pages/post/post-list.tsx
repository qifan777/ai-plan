import Taro from "@tarojs/taro";
import { Photograph } from "@nutui/icons-react-taro";
import { useAppDispatch, usePageHelper } from "@/hook";
import { processPostComment } from "@/pages/post/components/comment";
import PostRow from "@/pages/post/components/post-row";
import { useImmer } from "use-immer";
import { api } from "@/utils/api-instance";
import { PostCommentInput } from "@/apis/__generated/model/static";
import { switchPage } from "@/utils/common";
import CommentTitle from "@/pages/post/components/comment-title";
import { getUserInfo } from "@/features/user/userSlice";
import "./post-list.scss";
import RegisterPopup from "@/components/register-popup";

export default function PostList() {
  const dispatch = useAppDispatch();
  Taro.useLoad(() => {
    dispatch(getUserInfo());
  });
  const box = () => {
    const res = Taro.getMenuButtonBoundingClientRect();
    return {
      height: res.height + "px",
      top: res.top + "px",
    };
  };
  const { height, top } = box();

  const { dataList, setDataList, reloadPageData, loadPageData } = usePageHelper(
    api.postForFrontController.query,
    api.postForFrontController,
    { pageNum: 1, pageSize: 10, query: {} },
    { enableLoad: true, enablePullDownRefresh: true },
  );
  const postList = dataList.map((item) => {
    return { ...item, commentList: processPostComment([...item.comments]) };
  });
  const [visible, setVisible] = useImmer(false);
  const [comment, setComment] = useImmer<PostCommentInput>({
    content: "",
    postId: "",
  });
  const handleComment = (id: string) => {
    setComment((draft) => {
      draft.postId = id;
      draft.parentId = undefined;
    });
    setVisible(true);
  };
  const handleReply = (id: string, parentId: string) => {
    setComment((draft) => {
      draft.postId = id;
      draft.parentId = parentId;
    });
    setVisible(true);
  };
  const handlePublish = async (content: string) => {
    Taro.showLoading();
    await api.postCommentForFrontController.save({
      body: { ...comment, content },
    });
    Taro.hideLoading();
    setVisible(false);
    Taro.showToast({ title: "发布成功", icon: "success" });
    await handleRefresh(comment.postId);
  };
  const handleRefresh = async (id: string, all?: boolean) => {
    if (all) {
      return await reloadPageData();
    }
    const index = dataList.findIndex((row) => row.id == id);
    const value = await api.postForFrontController.findById({ id });
    setDataList((draft) => {
      draft.splice(index, 1, {
        ...value,
        pictures: [...value.pictures],
        likes: [...value.likes],
        comments: [...value.comments],
      });
    });
  };
  Taro.eventCenter.on("bottom", (active: number) => {
    if (active == 0) {
      loadPageData();
    }
  });
  return (
    <div className="community">
      <div className="nav-bar" style={{ paddingTop: top, height: height }}>
        <div className="center" onClick={() => switchPage("./post")}>
          发现 <Photograph className="camera" />
        </div>
      </div>
      <div className="post-list">
        {postList.map((post) => {
          return (
            <PostRow
              key={post.id}
              post={post}
              onReply={(parentId) => handleReply(post.id, parentId)}
              onComment={() => handleComment(post.id)}
              onRefresh={(all) => handleRefresh(post.id, all)}
            ></PostRow>
          );
        })}
        <CommentTitle
          visible={visible}
          onClose={() => setVisible(false)}
          onPublish={handlePublish}
        ></CommentTitle>
      </div>
      <RegisterPopup />
    </div>
  );
}
