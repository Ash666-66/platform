import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.use(ElementUI)

// 配置axios
axios.defaults.baseURL = '/api'
Vue.prototype.$axios = axios

Vue.config.productionTip = false

new Vue({
  router,
  render: function(h) {
    return h(App);
  }
}).$mount('#app')