// pages/com/login/login.js
var WXBizDataCrypt = require('../../../utils/cryptojs/RdWXBizDataCrypt.js')
var that={}
const app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
      
        userInfo: {
            nickName:'微信用户',
            
        },
        hasUserInfo: false,
        canIUseGetUserProfile: false,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
        if (wx.getUserProfile) {
            this.setData({
              canIUseGetUserProfile: true
            })
          }
    },
    getUserProfile(e) {
        // 推荐使用 wx.getUserProfile 获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
        // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
        wx.getUserProfile({
          desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
          success: (res) => {
            this.setData({
              userInfo: res.userInfo,
              hasUserInfo: true
            })
            console.log(" 登录后返回值",  res.userInfo);
          }
        })
      },
      getUserInfo(e) {
        // 不推荐使用 getUserInfo 获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
        this.setData({
          userInfo: e.detail.userInfo,
          hasUserInfo: true
        })
        console.log(" 登录后返回值", e.detail.userInfo);
      },
      getPhoneNumber(e){
        console.log(e)
        wx.showLoading({
          title: '加载中',
        })
        wx.login({
            success(res) {
            
              console.log("0 登录后返回值", res);
              setTimeout(() => {
              wx.request({

                url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + app.globalData.appId + '&secret=' + app.globalData.secret + '&js_code=' + res.code + '&grant_type=authorization_code',
                method: 'POST',
                header: {
                  'content-type': 'application/json'
                },
                success: function (res) {
                  console.log("4", res);
                  that.setData({
                    sessionkey :res.data.session_key,
                    openid : res.data.openid
           
                  })
                 var pc = new WXBizDataCrypt(app.globalData.appId,res.data.session_key)     
 
                  var data = pc.decryptData(e.detail.encryptedData,e.detail.iv)
                 console.log('解密后 data:',data)
                 wx.request({
                    url: app.globalData.baseurl+'/user/wxregister',
                    method: 'POST',
                    data:{
                        account: res.data.openid,
                        pwd:'wx',
                        nickname:that.data.userInfo.nickName,
                        gender:(that.data.userInfo.gender==0?"男":"女"),   
                        avatarurl:that.data.userInfo.avatarUrl,
                        phone:data.phoneNumber
                    },
                    header: {
                      'content-type': 'application/json'
                    },
                    success: function (res) {
                       wx.hideLoading()
                       wx.showToast({
                         title: res.data.msg,
                       })
                       wx.setStorageSync('user', res.data.data)
                       wx.redirectTo({
                         url: '../main/main',
                       })
                    },
                    fail:function (res) {
                        wx.hideLoading()
                        wx.showToast({
                          title: '请检查服务是否开启',
                        })
                      
                     },
                  })
                }
              })



            }, 500)
            }
          })



       


      },
      onClickLeft() {
        wx.showToast({ title: '点击返回', icon: 'none' });
      },
      onClickRight() {
        wx.showToast({ title: '点击按钮', icon: 'none' });
      },
      nlogin(){
          wx.navigateTo({
            url: './nlogin/nlogin',
          })
      },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})