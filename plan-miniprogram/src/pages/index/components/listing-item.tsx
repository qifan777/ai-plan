import { ListingDto } from "@/apis/__generated/model/dto";
import { useImmer } from "use-immer";
import { Button, Checkbox, Popover, Swipe } from "@nutui/nutui-react-taro";
import { Check, More } from "@nutui/icons-react-taro";
import dayjs from "dayjs";
import { switchPage } from "@/utils/common";
import "./listing-item.scss";

export default function ListingItem({
  value,
  onChange,
  onDelete,
  onDeleteTask,
  onEdit,
}: {
  value: ListingDto["ListingRepository/COMPLEX_FETCHER_FOR_FRONT"];
  onChange: (listId: string, taskId: string, value: boolean) => void;
  onDelete: (listId: string) => void;
  onDeleteTask: (taskId: string) => void;
  onEdit: (listId: string) => void;
}) {
  const [visible, setVisible] = useImmer(false);
  const taskItem = (
    task: ListingDto["ListingRepository/COMPLEX_FETCHER_FOR_FRONT"]["tasks"][0],
  ) => {
    return (
      <Swipe
        rightAction={
          <div>
            <Button
              type="danger"
              shape="square"
              onClick={() => onDeleteTask(task.id)}
            >
              删除
            </Button>
            <Button
              type="primary"
              shape="square"
              onClick={() => switchPage("/pages/task/task?id=" + task.id)}
            >
              编辑
            </Button>
          </div>
        }
      >
        <div className={["task-item", task.checked ? "checked" : ""].join(" ")}>
          <Checkbox
            defaultChecked={task.checked}
            icon={<Check size="14" />}
            activeIcon={
              <Check size="14" className="nut-checkbox-icon-checked" />
            }
            onChange={(v) => onChange(value.id, task.id, v)}
          >
            <div className="task-title">{task.name}</div>
          </Checkbox>
          <div className="time">{dayjs(task.finishTime).fromNow()}</div>
        </div>
      </Swipe>
    );
  };
  return (
    <div className="item-wrapper">
      <div className="top">
        <div className="title">{value.name}</div>
        <Popover
          visible={visible}
          location="bottom"
          onClick={() => setVisible(!visible)}
        >
          <More />
          <div className="menus">
            <div className="menu-item" onClick={() => onEdit(value.id)}>
              编辑
            </div>
            <div className="menu-item" onClick={() => onDelete(value.id)}>
              删除
            </div>
            <div
              className="menu-item"
              onClick={() =>
                switchPage("/pages/task/task?listingId=" + value.id)
              }
            >
              添加任务
            </div>
          </div>
        </Popover>
      </div>
      <div className="tasks">{value.tasks.map((task) => taskItem(task))}</div>
    </div>
  );
}
