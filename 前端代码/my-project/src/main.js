// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vant, { List, Dialog, NavBar } from 'vant'
import 'vant/lib/index.css'
// 配置axios,设置默认请求接口地址
import axios from 'axios'

import * as echarts from 'echarts'
Vue.use(echarts)
Vue.use(NavBar)
// 全局注册
Vue.use(Dialog)
Vue.use(List)
Vue.use(Vant)
Vue.use(ElementUI)
Vue.use(router)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  components: { App },
  template: '<App/>'
})

// new Vue({
//   el: '#app',
//   render: h => h(App)
// })

axios.defaults.baseURL = '/'
Vue.prototype.$axios = axios
Vue.prototype.$Message.config({
  top: 200,
  duration: 3
})
