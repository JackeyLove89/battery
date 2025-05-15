// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
   
  },
 
  onLoad() {   
      wx.setNavigationBarTitle({
        title: '启动页',
      }) 
    setTimeout(()=>//不能缺少
    {
       wx.redirectTo({
         url: '../com/login/nlogin/nlogin',
       })
    }, 2000)
  },
  
  
})
