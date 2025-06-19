import user from "@/assets/icons/default-user.png";
import { Image, ImageProps, View } from "@tarojs/components";

export default function UserAvatar({
  src,
  width = "60rpx",
  height = "60rpx",
  radius = "999rpx",
  mode = "widthFix",
}: {
  src?: string;
  width?: string;
  height?: string;
  radius?: string;
  mode?: ImageProps["mode"];
}) {
  return (
    <View style={{ display: "flex" }}>
      <Image
        src={src || user}
        mode={mode}
        style={{ width, height, borderRadius: radius }}
      ></Image>
    </View>
  );
}
