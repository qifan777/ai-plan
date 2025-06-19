import Taro from "@tarojs/taro";
import { useState } from "react";
import { Checkbox, Popup } from "@nutui/nutui-react-taro";
import { Button, View } from "@tarojs/components";
import { useAppDispatch, useAppSelector } from "@/hook";
import {
  selectRegisterShow,
  setRegisterShow,
} from "@/features/login/loginSlice";
import { api } from "@/utils/api-instance";
import { getUserInfo } from "@/features/user/userSlice";
import "./index.scss";

export default function RegisterPopup({
  onRefresh,
}: {
  onRefresh?: () => void;
}) {
  const show = useAppSelector(selectRegisterShow);
  const [agree, setAgree] = useState(false);
  const dispatch = useAppDispatch();
  const handleClose = () => {
    dispatch(setRegisterShow(false));
  };
  const handleLogin = async (phoneCode?: string) => {
    if (!phoneCode) return;
    const loginRes = await Taro.login();
    await Taro.showLoading();
    // 调用微信登录接口
    const res = await api.userWeChatForFrontController.registerV2({
      body: {
        loginCode: loginRes.code,
        phoneCode: phoneCode,
        inviteCode: "",
      },
    });
    Taro.setStorageSync("token", res.tokenValue);
    dispatch(getUserInfo());
    handleClose();
    if (onRefresh) onRefresh();
    Taro.hideLoading();
  };
  return (
    <Popup
      zIndex={9999999}
      visible={show}
      onClose={handleClose}
      position="bottom"
    >
      <View className="register-section">
        <Button
          className="btn"
          openType="getPhoneNumber"
          onGetPhoneNumber={(e) => handleLogin(e.detail.code)}
        >
          手机号快捷登录
        </Button>
        <Checkbox
          className="agreement"
          checked={agree}
          onChange={(value) => setAgree(value)}
        >
          <View className="agreement-content">
            已阅读并同意
            <View>《用户服务协议》</View>及<View>《隐私政策》</View>
          </View>
        </Checkbox>
      </View>
    </Popup>
  );
}
