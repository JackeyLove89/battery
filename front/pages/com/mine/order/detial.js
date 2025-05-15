var that={}
import Dialog from '@vant/weapp/dialog/dialog';
const app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
      
        latitude: '26.080429',
        longitude: ' 119.303470',
        scale: 16,
        "marksList": [{
			"id": 1,
			"latitude": "39.91443798613679",
			"longitude": "116.41800458066636",
			"iconPath": "/image/marker1.png",
			"width": 30,
			"height": 30,
			"callout": {
				"content": "王府井站点",
				"color": "#EE5E7B",
				"borderWidth": 1,
				"borderColor": " #EE5E78",
				"borderRadius": 5,
				"padding": 5,
				"fontSize": 16,
				"textAlign": "center",
				"display": "ALWAYS"
			}
		}]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
        that.setData({
            info: JSON.parse(options.info),         
          });
          that.getDetial()
    },   
 
    getDetial(){
        wx.request({
            url: app.globalData.baseurl+'/api/bx/queryPage',
            method: 'POST',
            data:{
                oid:that.data.info.id,              
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
          
              that.setData({
                   bxlist:res.data.data.list,
                 
                });
               
             
            }
          });
      
    },
 
    yuyue: function (e) {
        Dialog.confirm({
            title: '确定预约吗？',
            message: '预约后请在10分钟内扫码充电，超时系统会自动取消',
          })
            .then(() => {
                var user=  wx.getStorageSync('user')    
                wx.request({
                    url: app.globalData.baseurl+'/api/orders/addInfo',
                    method:'POST',
                    data: {
                        aid:that.data.info.id,
                        status:'预约中',
                       
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
            })
            .catch(() => {
              // on cancel
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