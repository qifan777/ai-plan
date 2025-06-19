import { PostDto } from "@/apis/__generated/model/dto";
import { PostCommentResponse } from "@/pages/post/components/comment";
import { Textarea } from "@tarojs/components";
import { useImmer } from "use-immer";
import { PostInput } from "@/apis/__generated/model/static";
import { onUpload } from "@/utils/upload";
import { Uploader } from "@nutui/nutui-react-taro";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
import "./post.scss";

export type Post = PostDto["PostRepository/COMPLEX_FETCHER_FOR_FRONT"] & {
  commentList: PostCommentResponse[];
};
export default function Post() {
  const [form, setForm] = useImmer<PostInput>({ pictures: [], content: "" });
  const submit = () => {
    api.postForFrontController.save({ body: form }).then(() => {
      Taro.showToast({ title: "发布成功" });
      Taro.navigateBack({
        success() {
          Taro.eventCenter.trigger("post");
        },
      });
    });
  };

  return (
    <div className="post-page">
      <div className="content">
        <Textarea
          value={form.content}
          onInput={(event) => {
            setForm((draft) => {
              draft.content = event.detail.value;
            });
          }}
        ></Textarea>
        <Uploader
          upload={onUpload}
          maxCount={5}
          multiple
          onChange={(params) => {
            console.log(params);
            setForm((draft) => {
              draft.pictures = params
                .filter((item) => item.status === "success")
                .map((item) => item.url as string);
            });
          }}
        ></Uploader>
      </div>
      <div className="bottom">
        <div className="submit-button" onClick={submit}>
          发布
        </div>
      </div>
    </div>
  );
}
