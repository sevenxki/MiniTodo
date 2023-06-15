import App from './App'
import uView from "uview-ui";
import config from './utils/config.js';
import request from './utils/request.js';

Vue.use(uView);
// 配置文件
Vue.prototype.$config = config;
// http请求
Vue.prototype.$http = request;

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif