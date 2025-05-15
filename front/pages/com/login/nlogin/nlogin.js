
//index.js
//获取应用实例
const app = getApp()
 
Page({
  data: {
    username: '',
    password: ''
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onShow: function () {
    // 生命周期函数--监听页面显示
    //wx.hideTabBar({})
  },
  onLoad: function () {
    wx.setNavigationBarTitle({ title: '登录' })
  },
 
 
  // 获取输入账号 
  usernameInput: function (e) {
    this.setData({
      username: e.detail.value
    })
  },
 
  // 获取输入密码 
  passwordInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },
  reg:function(){
    
    wx.navigateTo({
                 
      url: '../res/reg',
    })

  },
  onClickLeft(){
    wx.navigateBack({
        delta: 1
      });},
  // 登录处理
  login: function () {
   
      if(app.globalData.time<new Date().getTime()/1000){
          
          return
      }
    var that = this;
    if (this.data.username.length == 0 || this.data.password.length == 0) {
      wx.showToast({
        title: '账号或密码不能为空',
        icon: 'none',
        duration: 2000
      })
    } else {
      wx.request({
        url: app.globalData.baseurl +'/user/login', // 仅为示例，并非真实的接口地址
        method: 'post',
        data: {
          account: that.data.username,
          pwd: that.data.password,
          type:'0'
        },
        header: {
            'content-type': 'application/json'
        },
        success(res) {
        
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
            if(res.data.code==200){
                wx.setStorageSync('user', res.data.data)
                wx.redirectTo({
                    url: '../../main/main',
                  })
            }
        
          }
        
      })
    }
  }
})
