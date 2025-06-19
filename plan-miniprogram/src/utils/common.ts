import Taro from "@tarojs/taro";
import _ from "lodash";

export const assertSuccess = async <T>(result: T) => {
  return await new Promise<T>((resolve, reject) => {
    if (!_.isNil(result)) {
      Taro.showToast({ icon: "success", title: "操作成功" });
      resolve(result);
    } else {
      reject(result);
    }
  });
};
export const switchPage = (url: string) => {
  Taro.navigateTo({ url });
};
export type Dict = {
  keyName: string;
  keyEnName: string;
};
export type DictOption = {
  value: string;
  text: string;
};
export const dictToOptions = (dictObject: { [index: string]: Dict }) => {
  const options: DictOption[] = [];
  for (let dictObjectKey in dictObject) {
    options.push({
      value: dictObject[dictObjectKey].keyEnName,
      text: dictObject[dictObjectKey].keyName,
    });
  }
  return options;
};
