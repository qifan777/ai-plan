import Taro, { useLaunch } from "@tarojs/taro";
import { injectStore } from "@/utils/request";
import { Provider } from "react-redux";
import { PropsWithChildren } from "react";
import dayjs from "dayjs";
import "dayjs/locale/zh-cn.js";
import relativeTime from "dayjs/plugin/relativeTime";
import duration from "dayjs/plugin/duration";
import calendar from "dayjs/plugin/calendar";
import "@nutui/nutui-react-taro/dist/styles/themes/default.css";
import store from "./store";
import "./app.scss";

dayjs.extend(calendar);
dayjs.extend(relativeTime);
dayjs.extend(duration);
dayjs.locale("zh-cn");

function App({ children }: PropsWithChildren<any>) {
  injectStore(store);
  useLaunch(() => {
    console.log("App launched.");
    Taro.setStorageSync("active", 0);
  });

  // children 是将要会渲染的页面
  return <Provider store={store}>{children}</Provider>;
}

export default App;
