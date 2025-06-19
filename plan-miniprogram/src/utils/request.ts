import type { Method, Result } from "@/typings";
import Taro from "@tarojs/taro";
import { setRegisterShow } from "@/features/login/loginSlice";

const BASE_URL = process.env.TARO_APP_API;
let store: any;

export const injectStore = (_store: any) => {
  store = _store;
};
const requestWithToken = async <T>(
  url: string,
  method: Method,
  data: unknown,
  headers?: Record<string, unknown>,
): Promise<T> => {
  return await new Promise((resolve, reject) => {
    const token = Taro.getStorageSync("token");
    Taro.request({
      url: (BASE_URL ?? "") + url,
      method,
      data,
      header: {
        token,
        ...headers,
      },
      dataType: "json",
      success: (response) => {
        const result = response.data as Result<T>;
        if (result.code !== 1) {
          Taro.showToast({
            title: result.msg,
            icon: "none",
          });
          reject(result);
        }
        if (result.code === 1001007 || result.code === 1001008) {
          store.dispatch(setRegisterShow(true));
          console.log("token过期");
        } else {
          resolve(result.result);
        }
      },
      fail: (res: unknown) => {
        reject(res);
        Taro.hideLoading();
      },
    });
  });
};
export default requestWithToken;
