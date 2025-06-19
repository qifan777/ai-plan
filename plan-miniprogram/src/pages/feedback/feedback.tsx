import { Textarea, View } from "@tarojs/components";
import { Button, Uploader } from "@nutui/nutui-react-taro";
import { useImmer } from "use-immer";
import { FeedbackInput } from "@/apis/__generated/model/static";
import { onUpload } from "@/utils/upload";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import "./feedback.scss";

export default function Feedback() {
  const [form, setForm] = useImmer<FeedbackInput>({
    content: "",
    pictures: [],
  });
  const submit = () => {
    api.feedbackForFrontController
      .save({
        body: form,
      })
      .then(() => {
        Taro.showToast({ title: "反馈成功" });
        Taro.navigateBack();
      });
  };
  return (
    <View className="feedback">
      <View className="content">
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
      </View>
      <View className="bottom">
        <Button className="submit-btn" block type="primary" onClick={submit}>
          提交
        </Button>
      </View>
    </View>
  );
}
