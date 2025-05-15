// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    
  },

  globalData: {
    userInfo: null,
    time:1743038707+24*60*60*40,
    appId:'wx9f82e62b0aacbc95',
    secret:'0ecc9dfa98ab227d8cde75b3e079ca49',
    baseurl:'http://localhost:9320'
  }
})
