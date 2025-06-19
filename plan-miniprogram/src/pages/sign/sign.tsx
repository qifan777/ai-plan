import { CalendarCard, CalendarCardDay } from "@nutui/nutui-react-taro";
import { api } from "@/utils/api-instance";
import { useImmer } from "use-immer";
import { SignDto, UserDto } from "@/apis/__generated/model/dto";
import Taro from "@tarojs/taro";
import dayjs from "dayjs";
import { Checked } from "@nutui/icons-react-taro";
import { Image } from "@tarojs/components";
import oneDay from "@/assets/icons/1day.png";
import oneDayInactive from "@/assets/icons/1day-inactive.png";
import threeDay from "@/assets/icons/3day.png";
import threeDayInactive from "@/assets/icons/3day-inactive.png";
import sevenDay from "@/assets/icons/7day.png";
import sevenDayInactive from "@/assets/icons/7day-inactive.png";
import fifteenDay from "@/assets/icons/15day.png";
import fifteenDayInactive from "@/assets/icons/15day-inactive.png";
import thirtyDay from "@/assets/icons/30day.png";
import thirtyDayInactive from "@/assets/icons/30day-inactive.png";
import sixtyDay from "@/assets/icons/60day.png";
import sixtyDayInactive from "@/assets/icons/60day-inactive.png";
import UserAvatar from "@/components/user/user-avatar";
import { Dictionaries } from "@/apis/__generated/model/enums/DictConstants";
import "./sign.scss";

const achievementList = [
  {
    icon: oneDay,
    iconInactive: oneDayInactive,
    value: 1,
  },
  {
    icon: threeDay,
    iconInactive: threeDayInactive,
    value: 3,
  },
  {
    icon: sevenDay,
    iconInactive: sevenDayInactive,
    value: 7,
  },
  {
    icon: fifteenDay,
    iconInactive: fifteenDayInactive,
    value: 15,
  },
  {
    icon: thirtyDay,
    iconInactive: thirtyDayInactive,
    value: 30,
  },
  {
    icon: sixtyDay,
    iconInactive: sixtyDayInactive,
    value: 60,
  },
];
export default function Sign() {
  const [signList, setSignList] = useImmer<
    SignDto["SignRepository/COMPLEX_FETCHER_FOR_FRONT"][]
  >([]);
  const [userList, setUserList] = useImmer<
    UserDto["SignRepository/SIGN_FETCHER"][]
  >([]);
  Taro.useLoad(async () => {
    const res = await api.signForFrontController.query({
      body: { pageSize: 1000, pageNum: 1, query: {} },
    });
    setSignList([...res.content]);
    const users = await api.signForFrontController.findUser();
    setUserList([...users]);
  });
  const renderDayTop = (day: CalendarCardDay) => {
    const sign = signList.find((item) => {
      const date = dayjs(item.createdTime);
      if (
        date.date() === day.date &&
        date.month() === day.month - 1 &&
        date.year() === day.year
      ) {
        return true;
      }
    });
    if (sign) {
      return <Checked color="red"></Checked>;
    }
    return "";
  };
  const handleDayClick = async (day: CalendarCardDay) => {
    const date = new Date();
    if (
      date.getDate() === day.date &&
      date.getMonth() === day.month - 1 &&
      date.getFullYear() === day.year
    ) {
      await api.signForFrontController.save({ body: { userId: "12" } });
      const res = await api.signForFrontController.query({
        body: { pageSize: 1000, pageNum: 1, query: {} },
      });
      setSignList([...res.content]);
      return;
    }
    Taro.showToast({
      title: "不能签到",
      icon: "none",
    });
  };
  return (
    <div className="sign">
      <CalendarCard
        renderDayTop={renderDayTop}
        onDayClick={handleDayClick}
      ></CalendarCard>
      <div className="rank">
        {userList.map((user) => {
          return (
            <div className="user" key={user.id}>
              <UserAvatar src={user.avatar} width="100rpx"></UserAvatar>
              <div className="info">
                <div className="nickname">{user.nickname}</div>
                <div className="bottom">
                  <div className="sex">
                    {Dictionaries.Gender[user.gender || "PRIVATE"].keyName}
                  </div>
                  <div className="sign-count">签到{user.signCount}次</div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="achievement-list">
        {achievementList.map((item) => {
          if (signList.length >= item.value) {
            return (
              <div className="achievement-item" key={item.value}>
                <Image src={item.icon} mode="widthFix"></Image>
              </div>
            );
          }
          return (
            <div className="achievement-item" key={item.value}>
              <Image src={item.iconInactive} mode="widthFix"></Image>
            </div>
          );
        })}
      </div>
    </div>
  );
}
