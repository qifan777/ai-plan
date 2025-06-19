import { useImmer } from "use-immer";
import React from "react";
import "./post-menu.scss";

export default function PostMenu({
  menuList,
  length,
}: {
  menuList: React.JSX.Element;
  length: number;
}) {
  const [menuVisible, setMenuVisible] = useImmer<boolean>(false);
  return (
    <div className="post-menu-list">
      {menuVisible ? (
        <div
          className="post-mask"
          onClick={() => {
            setMenuVisible(false);
          }}
        ></div>
      ) : (
        ""
      )}
      <div
        className="comment-menu"
        onClick={() => {
          setMenuVisible(!menuVisible);
        }}
      >
        <div className="dot"></div>
        <div className="dot"></div>
      </div>
      <div
        className="menu-list"
        style={{
          visibility: menuVisible ? "visible" : "hidden",
          left: length > 2 ? "-380rpx" : "-250rpx",
        }}
      >
        {menuList}
      </div>
    </div>
  );
}
