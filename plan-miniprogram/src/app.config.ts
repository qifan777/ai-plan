export default defineAppConfig({
  pages: [
    "pages/index/index",
    "pages/feedback/feedback",
    "pages/post/post-list",
    "pages/post/post",
    "pages/chat/chat",
    "pages/sign/sign",
    "pages/task/task",
    "pages/user/user",
    "pages/user/user-edit",
  ],
  window: {
    backgroundTextStyle: "light",
    navigationBarBackgroundColor: "#fff",
    navigationBarTitleText: "",
    navigationBarTextStyle: "black",
  },
  tabBar: {
    borderStyle: "black",
    backgroundColor: "#ffffff",
    color: "#D0CCDA",
    selectedColor: "#307EB7",
    list: [
      {
        pagePath: "pages/index/index",
        iconPath: "assets/icons/home.png",
        selectedIconPath: "assets/icons/home-active.png",
        text: "首页",
      },
      {
        pagePath: "pages/chat/chat",
        iconPath: "assets/icons/service.png",
        selectedIconPath: "assets/icons/service-active.png",
        text: "AI",
      },
      {
        pagePath: "pages/post/post-list",
        iconPath: "assets/icons/community.png",
        selectedIconPath: "assets/icons/community-active.png",
        text: "发现",
      },
      {
        pagePath: "pages/user/user",
        iconPath: "assets/icons/user.png",
        selectedIconPath: "assets/icons/user-active.png",
        text: "我的",
      },
    ],
  },
  usingComponents: {},
});
