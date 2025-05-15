// pages/com/mine/pwd/pwd.js
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
    onChangeOld(event) {
        // event.detail 为当前输入的值
       this.setData({
           opwd:event.detail
       })
      },
      onChangeNew(event) {
        // event.detail 为当前输入的值
        this.setData({
            npwd:event.detail
        })
      },
      onChangeNew1(event) {
        // event.detail 为当前输入的值
        this.setData({
            npwd1:event.detail
        })
      },
      save(){      
        wx.request({
            url: app.globalData.baseurl+'/user/upInfo',
            method:'POST',
            data: that.data.user,    
            header: {
                'content-type': 'application/json'
            },     
            success: function (res) {
                wx.showToast({
                  title: res.data.msg,
                })
              
             
            }
          });
    },
    onClickLeft(){
        wx.navigateBack({
            delta: 1
          });
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