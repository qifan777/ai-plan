import { FormProps, View } from "@tarojs/components";
import { Form, FormItem, Button, Input, Picker } from "@nutui/nutui-react-taro";
import { uploadFile } from "@/utils/upload";
import { useImmer } from "use-immer";
import Taro from "@tarojs/taro";
import { UserDto } from "@/apis/__generated/model/dto";
import { api } from "@/utils/api-instance";
import UserAvatar from "@/components/user/user-avatar";
import { Gender } from "@/apis/__generated/model/enums";
import { useAppDispatch } from "@/hook";
import { getUserInfo } from "@/features/user/userSlice";
import "./user-edit.scss";

export default function UserEdit() {
  const dispatch = useAppDispatch();

  const [user, setUser] = useImmer<
    UserDto["UserRepository/USER_ROLE_FETCHER"] | undefined
  >(undefined);
  Taro.useLoad(async () => {
    const res = await api.userForFrontController.getUserInfo();
    setUser(res);
  });

  const chooseAvatar = async (avatar: string) => {
    const url = await uploadFile(avatar);
    setUser((draft) => {
      if (!draft) return;
      draft.avatar = url;
    });
  };
  const chooseNickname = async (nickname: string) => {
    setUser((draft) => {
      if (!draft) return;
      console.log(nickname);
      draft.nickname = nickname;
    });
  };
  const chooseSex = async (sex: Gender) => {
    console.log(sex)
    setUser((draft) => {
      if (!draft) return;
      draft.gender = sex;
    });
  };
  const [sexVisible, setSexVisible] = useImmer(false);
  const submit: FormProps["onSubmit"] = (values: any) => {
    console.log(values);
    if (!user) return;
    api.userForFrontController.updateInfo({ body: user }).then(() => {
      Taro.showToast({ title: "保存成功" });
      Taro.navigateBack();
      dispatch(getUserInfo());
    });
  };
  const options = [
    { label: "男", value: "MALE" },
    { label: "女", value: "FEMALE" },
  ];
  return (
    <View className="user-edit">
      <Form
        onFinish={submit}
        footer={
          <Button className="submit" type="primary" nativeType="submit" block>
            提交
          </Button>
        }
      >
        <FormItem label="头像" name="avatar">
          <Button
            className="choose-avatar"
            plain
            shape="square"
            openType="chooseAvatar"
            onChooseAvatar={(e) => chooseAvatar(e.detail.avatarUrl)}
            icon={
              <UserAvatar
                width="80rpx"
                height="80rpx"
                mode="aspectFill"
                src={user?.avatar}
              ></UserAvatar>
            }
          ></Button>
        </FormItem>
        <FormItem label="用户名">
          <div>
            <Input
              className="nickname"
              value={user?.nickname}
              onChange={(e) => chooseNickname(e)}
            />
          </div>
        </FormItem>
        <FormItem label="性别" name="sex">
          <View
            className="sex"
            onClick={() => user?.gender || setSexVisible(true)}
          >
            {user?.gender
              ? options.find(o=>o.value==user.gender)?.label
              : "请选择性别"}
          </View>
        </FormItem>
      </Form>
      <Picker
        title="选择性别(确认后不可变更)"
        visible={sexVisible}
        defaultValue={["MALE"]}
        options={[options]}
        onConfirm={(_list, values) => chooseSex(values[0] as Gender)}
        onClose={() => setSexVisible(false)}
      ></Picker>
    </View>
  );
}
