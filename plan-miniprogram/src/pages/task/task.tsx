import {
  Button,
  Checkbox,
  DatePicker,
  Form,
  FormItem,
  Input,
  type PickerValue,
  Uploader,
} from "@nutui/nutui-react-taro";
import { useImmer } from "use-immer";
import { TaskInput } from "@/apis/__generated/model/static";
import { onUpload } from "@/utils/upload";
import Taro from "@tarojs/taro";
import { api } from "@/utils/api-instance";
import { Del } from "@nutui/icons-react-taro";
import "./task.scss";

export default function Task() {
  Taro.useLoad(async ({ id, listingId }) => {
    if (id) {
      const res = await api.taskForFrontController.findById({ id });
      form.setFieldsValue({
        ...res,
        listingId: res.listing.id,
        remindTime: res.remindTime?.toString().substring(0, 16),
        finishTime: res.finishTime?.toString().substring(0, 16),
      });
      setSteps([...res.steps]);
    }
    if (listingId) {
      console.log("listingId", listingId);
      form.setFieldValue("listingId", listingId);
    }
  });
  const handleSubmit = async (values: TaskInput) => {
    const res = await api.taskForFrontController.save({
      body: {
        ...values,
        steps,
        remindTime: values.remindTime + ":00",
        finishTime: values.finishTime + ":00",
      },
    });
    form.setFieldValue("id", res);
    Taro.showToast({
      title: "保存成功",
    });
    Taro.navigateBack();
  };
  const [form] = Form.useForm();

  const [visible, setVisible] = useImmer(false);
  const [visible2, setVisible2] = useImmer(false);
  const handleFinishConfirm = (value: PickerValue[]) => {
    form.setFieldsValue({
      finishTime: `${value[0]}-${value[1]}-${value[2]} ${value[3]}:${value[4]}`,
    });
    setVisible(false);
  };
  const handleRemindConfirm = (value: PickerValue[]) => {
    form.setFieldsValue({
      remindTime: `${value[0]}-${value[1]}-${value[2]} ${value[3]}:${value[4]}`,
    });
    setVisible2(false);
  };
  const [steps, setSteps] = useImmer<TaskInput["steps"]>([]);
  return (
    <div>
      <Form
        form={form}
        onFinish={handleSubmit}
        footer={
          <Button type="primary" nativeType="submit" block>
            提交
          </Button>
        }
      >
        <FormItem label="任务名称" name="name">
          <Input name="name" placeholder="请输入任务名称" />
        </FormItem>
        <FormItem label="完成时间" name="finishTime">
          <Input
            readOnly
            placeholder="请选择完成时间"
            onClick={() => setVisible(true)}
          />
        </FormItem>
        <FormItem label="提醒时间" name="remindTime">
          <Input
            readOnly
            placeholder="请选择提醒时间"
            onClick={() => setVisible2(true)}
          />
        </FormItem>
        <FormItem label="附件" name="files">
          <Uploader upload={onUpload} />
        </FormItem>
        <FormItem label="步骤" name="steps" initialValue={[]}>
          <div className="steps">
            {steps.map((step, index) => {
              return (
                <div className="step" key={index}>
                  <Checkbox
                    checked={step.checked}
                    onChange={(v) => {
                      setSteps((draft) => {
                        draft[index].checked = v;
                      });
                    }}
                  ></Checkbox>
                  <Input
                    placeholder="请输入步骤名称"
                    value={step.name}
                    onChange={(e) => {
                      setSteps((draft) => {
                        draft[index].name = e;
                      });
                    }}
                  />
                  <Del
                    onClick={() => {
                      setSteps((draft) => {
                        draft.splice(index, 1);
                      });
                    }}
                  ></Del>
                </div>
              );
            })}
            <Button
              type="primary"
              onClick={() => {
                setSteps((draft) => {
                  draft.push({
                    name: "",
                    checked: false,
                  });
                });
              }}
            >
              添加步骤
            </Button>
          </div>
        </FormItem>
      </Form>
      <DatePicker
        title="时间选择"
        type="datetime"
        defaultValue={new Date()}
        visible={visible}
        minuteStep={5}
        onClose={() => setVisible(false)}
        onConfirm={(_options, values) => handleFinishConfirm(values)}
      />
      <DatePicker
        title="时间选择"
        type="datetime"
        defaultValue={new Date()}
        visible={visible2}
        minuteStep={5}
        onClose={() => setVisible2(false)}
        onConfirm={(_options, values) => handleRemindConfirm(values)}
      />
    </div>
  );
}
