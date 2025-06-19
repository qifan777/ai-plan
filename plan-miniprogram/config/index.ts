// @ts-ignore
import path from "path";
import { defineConfig, type UserConfigExport } from "@tarojs/cli";
import TsconfigPathsPlugin from "tsconfig-paths-webpack-plugin";
import devConfig from "./dev";
import prodConfig from "./prod";
// https://taro-docs.jd.com/docs/next/config#defineconfig-辅助函数
export default defineConfig<"webpack5">(async (merge, { command, mode }) => {
  const baseConfig: UserConfigExport<"webpack5"> = {
    projectName: "plan-miniprogram",
    date: "2024-11-25",
    // 开启 HTML 插件
    plugins: ["@tarojs/plugin-html"],
    designWidth(input) {
      // 配置 NutUI 375 尺寸
      if (input && typeof input == "object" && input.file) {
        if (input.file.replace(/\\+/g, "/").indexOf("@nutui") > -1) {
          return 375;
        }
      }
      // 全局使用 Taro 默认的 750 尺寸
      return 750;
    },
    deviceRatio: {
      640: 2.34 / 2,
      750: 1,
      828: 1.81 / 2,
      375: 2 / 1,
    },
    sourceRoot: "src",
    outputRoot: "dist",
    defineConstants: {},
    copy: {
      patterns: [],
      options: {},
    },
    framework: "react",
    compiler: {
      type: "webpack5",
      prebundle: {
        enable: false,
        exclude: ["@nutui/nutui-react-taro", "@nutui/icons-react-taro"],
      },
    },
    alias: {
      "@/components": path.resolve(__dirname, "..", "src/components"),
      "@/utils": path.resolve(__dirname, "..", "src/utils"),
      "@/typings": path.resolve(__dirname, "..", "src/typings"),
      "@/apis": path.resolve(__dirname, "..", "src/apis"),
      "@/hook": path.resolve(__dirname, "..", "src/hook"),
      "@/store": path.resolve(__dirname, "..", "src/store"),
      "@/assets": path.resolve(__dirname, "..", "src/assets"),
      "@/pages": path.resolve(__dirname, "..", "src/pages"),
      "@/features": path.resolve(__dirname, "..", "src/features"),
    },
    cache: {
      enable: false, // Webpack 持久化缓存配置，建议开启。默认配置请参考：https://docs.taro.zone/docs/config-detail#cache
    },
    mini: {
      miniCssExtractPluginOption: {
        ignoreOrder: true,
      },
      webpackChain(chain) {
        chain.resolve.plugin("tsconfig-paths").use(TsconfigPathsPlugin);
      },
    },
    h5: {
      publicPath: "/",
      staticDirectory: "static",
      output: {
        filename: "js/[name].[hash:8].js",
        chunkFilename: "js/[name].[chunkhash:8].js",
      },
      miniCssExtractPluginOption: {
        ignoreOrder: true,
        filename: "css/[name].[hash].css",
        chunkFilename: "css/[name].[chunkhash].css",
      },
      postcss: {
        autoprefixer: {
          enable: true,
          config: {},
        },
        cssModules: {
          enable: true, // 默认为 false，如需使用 css modules 功能，则设为 true
          config: {
            namingPattern: "module", // 转换模式，取值为 global/module
            generateScopedName: "[name]__[local]___[hash:base64:5]",
          },
        },
      },
      webpackChain(chain) {
        chain.resolve.plugin("tsconfig-paths").use(TsconfigPathsPlugin);
      },
    },
    rn: {
      appName: "taroDemo",
      postcss: {
        cssModules: {
          enable: false, // 默认为 false，如需使用 css modules 功能，则设为 true
        },
      },
    },
  };
  if (process.env.NODE_ENV === "development") {
    // 本地开发构建配置（不混淆压缩）
    return merge({}, baseConfig, devConfig);
  }
  // 生产构建配置（默认开启压缩混淆等）
  return merge({}, baseConfig, prodConfig);
});
