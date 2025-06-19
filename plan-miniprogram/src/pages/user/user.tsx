import { Image, View } from "@tarojs/components";
import UserAvatar from "@/components/user/user-avatar";
import { useAppDispatch, useAppSelector } from "@/hook";
import Taro from "@tarojs/taro";
import { getUserInfo, selectUserInfo } from "@/features/user/userSlice";
import calenderIcon from "@/assets/icons/calendar.png";
import inboxIcon from "@/assets/icons/inbox.png";

import { switchPage } from "@/utils/common";
import RegisterPopup from "@/components/register-popup";
import "./user.scss";

export default function User() {
  const dispatch = useAppDispatch();
  Taro.showShareMenu({
    withShareTicket: true,
    showShareItems: ["shareAppMessage", "shareTimeline"],
  });
  const userInfo = useAppSelector(selectUserInfo);
  const handleEdit = () => {
    Taro.navigateTo({
      url: "./user-edit",
    });
  };
  Taro.useLoad(async () => {
    dispatch(getUserInfo());
  });
  return (
    <View className="user-page">
      <Image
        className="background"
        mode="aspectFill"
        src="https://my-resource-share.oss-cn-qingdao.aliyuncs.com/config/BJ.jpg"
      ></Image>
      <View className="user-wrapper">
        <View className="user-section">
          <View className="user-info" onClick={() => handleEdit()}>
            <UserAvatar
              src={userInfo?.avatar}
              radius="50%"
              width="100rpx"
              height="100rpx"
            ></UserAvatar>
            <View className="username">
              {userInfo ? (
                <View className="name">{userInfo.nickname}</View>
              ) : (
                <View
                  className="name not-login"
                  onClick={(event) => {
                    event.stopPropagation();
                    dispatch(getUserInfo());
                  }}
                >
                  您还未登录,点击登录
                </View>
              )}
            </View>
          </View>
        </View>
      </View>
      <View className="divider">
        <View className="front">个人中心</View>
        <View className="rear">MY SERVICE</View>
      </View>
      <View className="functions">
        <View
          className="function"
          onClick={() => switchPage("/pages/sign/sign")}
        >
          <Image src={calenderIcon} mode="widthFix" className="icon" />
          <View className="name">签到</View>
        </View>
        <View
          className="function"
          onClick={() => switchPage("/pages/feedback/feedback")}
        >
          <Image src={inboxIcon} mode="widthFix" className="icon" />
          <View className="name">留言反馈</View>
        </View>
      </View>
      <RegisterPopup></RegisterPopup>
    </View>
  );
}
