const app=getApp()
var that={};
Page({

    /**
     * 页面的初始数据
     */
    data: {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
    },
    onChangeT(event) {
        // event.detail 为当前输入的值
       this.setData({
           title:event.detail
       })
      },
      onChangeP(event) {
        // event.detail 为当前输入的值
        this.setData({
            phone:event.detail
        })
      },
      onChangeC(event) {
        // event.detail 为当前输入的值
        this.setData({
            content:event.detail
        })
      },
      save(){      
        var user=  wx.getStorageSync('user')    
        wx.request({
            url: app.globalData.baseurl+'/notice/advice/addInfo',
            method:'POST',
            data: {
                title:that.data.title,
                phone:that.data.phone,
                content:that.data.content,
                uid:user.id,
            },    
            header: {
                'content-type': 'application/json'
            },     
            success: function (res) {
                wx.showToast({
                  title: res.data.msg,
                })
                wx.navigateBack({
                    delta: 1
                  });
             
            }
          });
    },
    onClickLeft(){
        wx.navigateBack({
            delta: 1
          });
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