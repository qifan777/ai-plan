import Taro from "@tarojs/taro";
import { Result } from "@/typings";
import { BaseUploader } from "@nutui/nutui-react-taro";

export type UploadFile = {
  name: string;
  url: string;
  status: string;
  message: string;
  type: string;
};
export const uploadFile = async (filePath: string) => {
  return await new Promise<string>((resolve, reject) => {
    Taro.uploadFile({
      url: (process.env.TARO_APP_API ?? "") + "/oss/upload",
      header: {
        token: Taro.getStorageSync("token"),
      },
      name: "file",
      filePath,
      success: (res) => {
        resolve(JSON.parse(res.data).result);
      },
      fail: (result) => {
        console.log(result);
        reject(result);
      },
    });
  });
};
export const onUpload: BaseUploader["upload"] = async (
  file: File & { tempFilePath: string },
) => {
  console.log(file);
  const url = await uploadFile(file.tempFilePath);
  return {
    status: "success",
    name: file.name,
    url: url,
    type: "image",
    path: file.tempFilePath,
    file: file,
    percentage: 100,
  };
};
export const beforeXhrUpload = async (_: any, options: any) => {
  return await new Promise<string>((resolve, reject) => {
    Taro.compressImage({
      src: options.taroFilePath,
      compressedWidth: 1000,
      quality: 30,
      success(result) {
        //taroUploadFile  是 Taro.uploadFile ， 你也可以自定义设置其它函数
        const uploadTask = Taro.uploadFile({
          url: `${process.env.TARO_APP_API}/oss/upload`,
          filePath: result.tempFilePath,
          header: {
            "Content-Type": "multipart/form-data",
            token: Taro.getStorageSync("token"),
            ...options.headers,
          }, //
          formData: options.formData,
          name: options.name,
          success(response) {
            if (options.xhrState == response.statusCode) {
              resolve(JSON.parse(response.data).result);
              options.onSuccess?.(JSON.parse(response.data).result, options);
            } else {
              reject();
              options.onFailure?.(response, options);
            }
          },
          fail(e) {
            reject();
            options.onFailure?.(e, options);
          },
        });
        options.onStart?.(options);
        uploadTask.progress((res) => {
          options.onProgress?.(res, options);
        });
      },
    });
  });
};

export type UploadCallback = ({
  data,
  options,
  fileItem,
}: {
  data: { data: string };
  options: any;
  fileItem: UploadFile;
}) => void;
export const onFailure: UploadCallback = ({ data, fileItem }) => {
  console.log(data);
  fileItem.message = (JSON.parse(data.data) as Result<void>).msg;
  fileItem.status = "error";
};
