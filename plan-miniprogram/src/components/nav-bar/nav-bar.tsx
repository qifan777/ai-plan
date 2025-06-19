import { ArrowLeft } from "@nutui/icons-react-taro";
import Taro from "@tarojs/taro";
import "./nav-bar.scss";

export default function NavBar({
  onBack,
  title,
}: {
  onBack: () => void;
  title: string;
}) {
  const res = Taro.getMenuButtonBoundingClientRect();
  const clientWidth = Taro.getSystemInfoSync().windowWidth;
  return (
    <div
      className="nav-bar-component"
      style={{
        width: clientWidth - res.width - 10 + "px",
        height: res.height + "px",
        paddingTop: res.top + "px",
      }}
    >
      <div className="image-wrapper" onClick={onBack}>
        <ArrowLeft></ArrowLeft>
      </div>
      <div className="left-slot"></div>
      <div className="title">{title}</div>
      <div className="right"></div>
    </div>
  );
}
