import "./walter-fall.scss";

export default function WaterFall<T>({
  dataList,
  item,
}: {
  dataList: T[];
  item: (value: T) => React.JSX.Element;
}) {
  // 划分左右两边的列表
  const leftList = dataList.filter((_value, index) => index % 2 === 0);
  const rightList = dataList.filter((_value, index) => index % 2 !== 0);

  return (
    <div className="water-fall">
      {/* 左侧瀑布流 */}
      <div className="left-water-fall">
        {leftList.map((row, index) => (
          <div key={index} className="item-wrapper">
            {item(row)}
          </div>
        ))}
      </div>
      {/* 右侧瀑布流 */}
      <div className="right-water-fall">
        {rightList.map((row, index) => (
          <div key={index} className="item-wrapper">
            {item(row)}
          </div>
        ))}
      </div>
    </div>
  );
}
