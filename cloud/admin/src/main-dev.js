import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import './plugins/element-ui.js'
import ElementUI from 'element-ui'
Vue.use(ElementUI)
//图片放大组件
import VueDirectiveImagePreviewer from 'vue-directive-image-previewer'
import 'vue-directive-image-previewer/dist/assets/style.css'
Vue.use(VueDirectiveImagePreviewer)
import BaiduMap from 'vue-baidu-map'
Vue.use(BaiduMap, {
  ak: 'iQAwNMwvqOPOc8HJ5SYLSpHIPmVFhU66' // 这里的AK是百度的密钥
})
// 导入富文本编辑器
import VueQuillEditor from 'vue-quill-editor'
// 注册
Vue.use(VueQuillEditor)

import echarts from "echarts";
Vue.prototype.$echarts = echarts
//背景
import VueParticles from 'vue-particles'  
Vue.use(VueParticles)

// vue中导出excel表格模板
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
  
Vue.prototype.$FileSaver = FileSaver; //设置全局
Vue.prototype.$XLSX = XLSX; //设置全局
// 导入富文本编辑器样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

// 导入进度条样式
import 'nprogress/nprogress.css'

Vue.config.productionTip = false

new Vue({ 
  render: h => h(App),
  router
}).$mount('#app')
