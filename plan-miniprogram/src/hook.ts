import Taro from "@tarojs/taro";
import { useImmer } from "use-immer";
import type { Page, QueryRequest } from "@/apis/__generated/model/static";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import _ from "lodash";
import { useRef } from "react";
import type { AppDispatch, RootState } from "./store";

export type PageResult<T> = Pick<
  Page<T>,
  "content" | "number" | "size" | "totalElements" | "totalPages"
>;
export const recursiveOmit = <T extends Object>(obj: T) => {
  const obj2 = {
    ..._.omitBy(obj, (row) => {
      if (_.isString(row)) {
        return _.isEmpty(row);
      } else {
        return _.isNil(row);
      }
    }),
  };
  const keys = Object.keys(
    _.pickBy(obj, (row) => {
      if (_.isArray(row)) {
        return false;
      }
      return _.isObject(row);
    }),
  );
  keys.forEach((key) => {
    obj2[key] = recursiveOmit(obj[key]);
  });
  return obj2 as T;
};
export const usePageHelper = <T extends Object, E>(
  // 调用后端的查询接口
  queryApi: (options: {
    readonly body: QueryRequest<T>;
  }) => Promise<PageResult<E>>,
  object: unknown,
  // 查询条件
  initQuery: QueryRequest<T>,
  config?: {
    // 分页数据后置处理
    postProcessor?: (data: PageResult<E>) => void;
    enablePullDownRefresh?: boolean;
    enableReachBottom?: boolean;
    enableLoad?: boolean;
    enableShowLoad?: boolean;
  },
) => {
  config = {
    enablePullDownRefresh: true,
    enableReachBottom: true,
    enableLoad: true,
    enableShowLoad: false,
    ...config,
  };
  const [dataList, setDataList] = useImmer<E[]>([]);
  const queryRequest = useRef<QueryRequest<T>>({
    query: {} as T,
    pageNum: 1,
    pageSize: 10,
    likeMode: "ANYWHERE",
    sorts: [{ property: "createdTime", direction: "DESC" }],
    ..._.omitBy(initQuery, _.isNil),
  });

  let finish = useRef(false);

  // 请求分页数据
  const loadPageData = async (
    request: Partial<QueryRequest<T>> = {},
    reload?: boolean,
  ) => {
    // 通用查询对象，防止传入空值
    queryRequest.current = {
      ...queryRequest.current,
      ..._.omitBy(request, _.isNil),
    };
    queryRequest.current = {
      ...queryRequest.current,
      query: recursiveOmit(queryRequest.current.query) as T,
    };
    if (finish.current) return;
    // 显示加载动画
    Taro.showLoading({
      title: "加载中",
    });
    try {
      // 调用查询接口
      const res: PageResult<E> = await queryApi.apply(object, [
        { body: queryRequest.current },
      ]);
      if (config?.postProcessor !== undefined) {
        config.postProcessor(res);
      }
      // 返回结果
      setDataList([...(reload ? [] : dataList), ...res.content]);
      finish.current = res.content.length < res.size;
      queryRequest.current = {
        ...queryRequest.current,
        pageNum: (queryRequest.current.pageNum || 1) + 1,
      };
      // 取消加载动画
      Taro.hideLoading();
      return dataList;
    } catch (e) {
      Taro.hideLoading();
    }
  };
  // 重新请求分页数据，pageNum=1, pageSize=10
  const reloadPageData = async (request?: {
    pageSize?: number;
    pageNum?: number;
    query?: T;
  }) => {
    finish.current = false;
    await loadPageData(
      {
        pageNum: 1,
        pageSize: request?.pageSize ?? 10,
        query: request?.query,
      },
      true,
    );
  };
  // 下拉刷新
  Taro.usePullDownRefresh(async () => {
    if (config?.enablePullDownRefresh) {
      await reloadPageData();
      setTimeout(() => {
        Taro.stopPullDownRefresh();
      }, 300);
    }
  });
  // 触底加载
  Taro.useReachBottom(async () => {
    if (config?.enableReachBottom) {
      await loadPageData();
    }
  });

  // 首次进入页面加载
  Taro.useLoad(async () => {
    if (config?.enableLoad) {
      await reloadPageData();
    }
  });
  // 每次进入页面刷新数据
  Taro.useDidShow(async () => {
    if (config?.enableShowLoad) {
      console.log("reloadPageData");
      await reloadPageData();
    }
  });

  return {
    queryRequest,
    dataList,
    setDataList,
    loadPageData,
    reloadPageData,
  };
};

// 在整个应用程序中使用，而不是简单的 `useDispatch` 和 `useSelector`
export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
