var that={}
const app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
      
        latitude: '26.080429',
        longitude: ' 119.303470',
        scale: 16,
       
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
      
        wx.authorize({  
            scope: 'scope.userLocation',  
            success: function () {  
              // 用户同意授权，接下来可以调用 wx.getLocation 获取位置信息  
              wx.getLocation({  
                type: 'wgs84',  
                success: function (res) {  
                    that.setData({
                        latitude:res.latitude,
                        longitude:res.longitude,
                     });
                  console.log('获取位置成功', res);  
                   that.getDetial()  
                },  
                fail: function (error) {  
                  console.error('获取位置失败', error);  
                }  
              });  
            },  
            fail: function () {  
              // 用户不同意授权，可以进行一些错误处理，比如显示提示信息  
              wx.showModal({  
                title: '提示',  
                content: '应用需要获取您的位置信息，请前往设置页面进行授权',  
                showCancel: false,  
                success: function (res) {  
                  if (res.confirm) {  
                    // 用户点击了确定按钮，可以引导用户打开设置页面  
                    wx.openSetting({  
                      withSubscriptions: true, // 设置为 true 可显示公众号订阅  
                      success: function (res) {  
                        if (res.authSetting['scope.userLocation']) {  
                          // 用户重新授权成功，可以再次调用 wx.getLocation 获取位置信息  
                          wx.getLocation({  
                            type: 'wgs84',  
                            success: function (res) {  
                                that.setData({
                                    latitude:res.latitude,
                                    longitude:res.longitude,
                                 });
                                 that.getDetial() 
                            },  
                            fail: function (error) {  
                              console.error('获取位置失败', error);  
                            }  
                          });  
                        }  
                      }  
                    });  
                  }  
                }  
              });  
            }  
          });
     
    },
   
 
    getDetial(){
        wx.request({
            url: app.globalData.baseurl+'/api/dw/queryPageFj',
            method: 'POST',
            data:{
                lat:that.data.latitude,  
                lng:that.data.longitude,                   
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                
              that.setData({
                   list:res.data.data,

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