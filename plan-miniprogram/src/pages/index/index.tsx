import { useImmer } from "use-immer";
import { SlideshowDto } from "@/apis/__generated/model/dto";
import { api } from "@/utils/api-instance";
import Taro from "@tarojs/taro";
import {
  Button,
  Form,
  FormItem,
  Input,
  Popup,
  Swiper,
  SwiperItem,
} from "@nutui/nutui-react-taro";
import WalterFall from "@/components/walter-fall/walter-fall";
import ListingItem from "@/pages/index/components/listing-item";

import { usePageHelper } from "@/hook";
import { Add } from "@nutui/icons-react-taro";
import { ListingInput } from "@/apis/__generated/model/static";
import { Image } from "@tarojs/components";
import RegisterPopup from "@/components/register-popup";
import "./index.scss";

export default function Index() {
  const [slideshowList, setSlideshowList] = useImmer<
    SlideshowDto["SlideshowRepository/COMPLEX_FETCHER_FOR_FRONT"][]
  >([]);
  const { dataList, setDataList, reloadPageData } = usePageHelper(
    api.listingForFrontController.query,
    api.listingForFrontController,
    { query: {} },
  );
  Taro.useLoad(async () => {
    const res = await api.slideshowForFrontController.query({
      body: {
        pageSize: 100,
        pageNum: 1,
        query: { valid: true },
        sorts: [{ property: "sort", direction: "ASC" }],
      },
    });
    setSlideshowList([...res.content]);
  });
  const handleChange = (listId: string, taskId: string, value: boolean) => {
    setDataList((draft) => {
      const task = draft
        .filter((item) => item.id === listId)[0]
        .tasks.filter((item) => item.id === taskId)[0];
      task.checked = value;
      api.taskForFrontController.save({
        body: { ...task, listingId: listId, checked: value },
      });
    });
  };
  const [visible, setVisible] = useImmer(false);
  const [form] = Form.useForm();

  const handleSubmit = async (input: ListingInput) => {
    await api.listingForFrontController.save({
      body: { ...input, userId: "123" },
    });
    await reloadPageData();
    setVisible(false);
  };
  const handleTaskDelete = async (taskId: string) => {
    await api.taskForFrontController.delete({ body: [taskId] });
    await reloadPageData();
  };
  const handleListingDelete = async (listingId: string) => {
    await api.listingForFrontController.delete({ body: [listingId] });
    await reloadPageData();
  };
  const handleEdit = async (listingId: string) => {
    const res = await api.listingForFrontController.findById({ id: listingId });
    console.log(res);
    form.setFieldsValue(res);
    setVisible(true);
  };
  return (
    <div className="index-page">
      <Swiper className="slideshow-swiper" interval={3000}>
        {slideshowList.map((slide) => {
          return (
            <SwiperItem key={slide.id} className="nut-swiper-item">
              <Image
                src={slide.picture}
                className="slideshow-image"
                mode="aspectFill"
              ></Image>
            </SwiperItem>
          );
        })}
      </Swiper>

      <div className="section">
        <WalterFall
          dataList={dataList}
          item={(row) => (
            <ListingItem
              value={row}
              onChange={handleChange}
              onDeleteTask={handleTaskDelete}
              onDelete={handleListingDelete}
              onEdit={handleEdit}
            ></ListingItem>
          )}
        ></WalterFall>
      </div>
      <div className="create" onClick={() => setVisible(true)}>
        <Add color="white" size="30" />
      </div>
      <Popup
        visible={visible}
        position="center"
        style={{ width: "600rpx" }}
        round
      >
        <div className="listing-form">
          <Form
            form={form}
            labelPosition="top"
            onFinish={handleSubmit}
            footer={
              <>
                <Button nativeType="submit" block type="primary">
                  提交
                </Button>
              </>
            }
          >
            <FormItem label="列表名称" name="name" required>
              <Input placeholder="请输入列表名称" />
            </FormItem>
          </Form>
        </div>
      </Popup>
      <RegisterPopup></RegisterPopup>
    </div>
  );
}
