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
        idx:0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        if(app.globalData.time<new Date().getTime()/1000){
          
            return
        }
        that=this;
        that.setData({
            id: options.id,         
          });
     
    },
   
 
    getDetial(){
        wx.request({
            url: app.globalData.baseurl+'/api/app/getInfo',
            method: 'POST',
            data:{
                id:that.data.id,              
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                
              that.setData({
                idx:res.data.data.index,

                });
               
             
            }
          });
        wx.request({
            url: app.globalData.baseurl+'/api/lr/queryPage',
            method: 'POST',
            data:{
                lid:that.data.id,              
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                
              that.setData({
                   list:res.data.data.list,

                });
               
             
            }
          });
          wx.request({
            url: app.globalData.baseurl+'/api/lr/queryPageMark',
            method: 'POST',
            data:{
                lid:that.data.id,              
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
              var mark=  res.data.data.marksList[0] 
             
              that.setData({
                   info:res.data.data,
                   latitude: mark.latitude,
                   longitude: mark.longitude
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
        this.getDetial()  
     
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