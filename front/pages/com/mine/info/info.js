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
       var user=  wx.getStorageSync('user')
        wx.request({
            url: app.globalData.baseurl+'/user/getInfo',
            method:'POST',
            data: {
              id: user.id,
          
            },    
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                var  files=[];
                files.push({ url:app.globalData.baseurl+"/"+res.data.data.avatarurl});
                console.log(files)             
                that.setData({
                    user: res.data.data,
                    fileList:files
                  }); 
                    
             
             
            }
          });
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
            const  fileList = [] ;
            fileList.push({ ...file, url: (app.globalData.baseurl+"/"+json.data)});
            that.setData({ fileList });
            that.data.user.avatarurl=(json.data)
            console.log(that.data.user.avatarurl)
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
    onChangeNick(e){
        console.log(e.detail)   
       
        that.data.user.nickname=e.detail
      
    },
    onChangePhone(e){
        console.log(e.detail)   
       
        that.data.user.phone=e.detail
      
    },
    onChangeCtype(e){
        console.log(e.detail)   
       
        that.data.user.cartype=e.detail
      
    },
    onChangeCar(e){
        console.log(e.detail)   
       
        that.data.user.car=e.detail
      
    },
    onChangeGl(e){
        console.log(e.detail)   
      
        that.data.user.gl=  parseInt(e.detail)
      
    },
    onChangeGender(e){
        console.log(e.detail)    
        that.data.user.gender=e.detail
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