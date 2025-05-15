const app=getApp()
var that={};
Page({

    /**
     * 页面的初始数据
     */
    data: {
        fileList: [],
      
    },

    /**
     * 生命周期函数--监听页面加载
     */

    onLoad(options) {
       that=this;         
       that.setData({
        info: JSON.parse(options.info),
       
      });
    
    },
    save(){ 
       

        var user=  wx.getStorageSync('user')
        wx.request({
            url: app.globalData.baseurl+'/api/bx/addInfo',
            method:'POST',
            data:{
                dec:that.data.content,
                oid:that.data.info.id,
                aid:that.data.info.aid,
             
                uid:user.id,
                
                bx:"待处理"
            },    
            header: {
                'content-type': 'application/json'
            },     
            success: function (res) {
                wx.showToast({
                  title: res.data.msg,
                })

                that.data.info.status="退款中"
                wx.request({
                  url: app.globalData.baseurl+'/api/orders/upInfo',
                  method:'POST',
                  data: that.data.info,    
                  header: {
                      'content-type': 'application/json' 
                  },     
                  success: function (res) {
                    
                   
                  }
                });

                wx.navigateBack({
                    delta: 1
                  });
             
            }
          });
    },
    afterRead(event) {
        const { file } = event.detail;
        // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
        wx.uploadFile({
          url: app.globalData.baseurl+'/api/upload',// 仅为示例，非真实的接口地址
          filePath: file.url,
          name: 'file',         
          success(res) {
            // 上传完成需要更新 fileList
            
           var json= JSON.parse(res.data);
            console.log(json.data)
            const { fileList = [] } = that.data;
            fileList.push({ ...file, url: (app.globalData.baseurl+"/"+json.data)});
            that.setData({ fileList });
          
            console.log(that.data.fileList)
          },
        });
      },
      deleteImg(event) {
        const delIndex = event.detail.index
        const { fileList } = this.data
        fileList.splice(delIndex, 1)
        this.setData({
          fileList
        })
      },
      contentInput(e){
        console.log(e.detail)   
        that.setData({
            content: e.detail.value
          });
    
      
    },
    titleInput(e){
        console.log(e.detail)   
        that.setData({
            title: e.detail.value
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