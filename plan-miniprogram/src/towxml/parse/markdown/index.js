let hljs;
hljs = require("../highlight");

const config = require("../../config"),
  mdOption = (() => {
    let result = {
      html: true,
      xhtmlOut: true,
      typographer: true,
      breaks: true,
    };

    if (config.highlight.length && hljs) {
      result.highlight = (code, lang, callback) => {
        let lineLen = code.split(/\r|\n/gi).length,
          result = hljs.highlightAuto(code).value;

        // 代码块多换行的问题
        result = result.replace(/(\r|\n){2,}/g, (str) => {
          return new Array(str.length).join("<p>&nbsp;</p>");
        });
        result = result.replace(/\r|\n/g, (str) => {
          return "<br/>";
        });

        // 代码空格处理
        result = result
          .replace(/>[^<]+</g, (str) => {
            return str.replace(/\s/g, "&nbsp;");
          })
          .replace(/\t/g, new Array(4).join("&nbsp;"));

        if (config.showLineNumber) {
          let lineStr = (() => {
            let str = `<ul class="h2w__lineNum">`;
            for (let i = 0; i < lineLen - 1; i++) {
              str += `<li class="h2w__lineNumLine">${i + 1}</li>`;
            }

            str += `</ul>`;
            return str;
          })();
          return lineStr + result;
        }
        return result;
      };
    }
    return result;
  })(),
  md = require("./markdown")(mdOption);

// 应用Markdown解析扩展，包括自定义组件（['sub','sup','ins','mark','emoji','todo','latex','yuml','echarts']）
// [...config.markdown,...config.components].forEach(item => {
//     if(!/^audio-player|table|todogroup|img$/.test(item)){
//         md.use(require(`./plugins/${item}`));
//     };
// });
md.use(require("./plugins/sub"));
md.use(require("./plugins/sup"));
md.use(require("./plugins/ins"));
md.use(require("./plugins/mark"));
md.use(require("./plugins/emoji"));
md.use(require("./plugins/todo"));
md.use(require("./plugins/latex"));
md.use(require("./plugins/yuml"));

// 定义emoji渲染规则
md.renderer.rules.emoji = (token, index) => {
  let item = token[index];
  return `<g-emoji class="h2w__emoji h2w__emoji--${item.markup}">${item.content}</g-emoji>`;
};

// 导出模块
module.exports = (str) => {
  return md.render(str);
};
