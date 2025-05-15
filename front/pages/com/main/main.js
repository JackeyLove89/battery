import Dialog from '@vant/weapp/dialog/dialog';
const app=getApp()
var that={}
Page({

    /**
     * 页面的初始数据
     */
    data: {
        active: 0,
    },
    onChange(event) {     
        // if(app.globalData.time<new Date().getTime()/1000){
          
        //     return
        // }
        wx.request({
            url: app.globalData.baseurl+'/api/msg/queryPage1',
            method:'POST',
            data: {
              uid: wx.getStorageSync('user').id,    
              
            },    
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                    if(res.data.data){
                        Dialog.alert({
                            title: '预警消息',
                            message: res.data.data.car.name+'--'+res.data.data.warning,
                            theme: 'round-button',
                          }).then(() => {
                            wx.request({
                                url: app.globalData.baseurl+'/api/msg/upInfo',
                                method:'POST',
                                data: res.data.data,    
                                header: {
                                    'content-type': 'application/json'
                                },     
                                success: function (res) {
                                   
                                 
                                }
                              });
                          });
                    }
             
            }
          });
        this.setData({ active: event.detail });     
      },
    
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
       that=this
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
        console.log("我主页面显示了")
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {
        console.log("我主页面隐藏了")
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