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
        cid: '',
        columns: [],
        showType:false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
      
        wx.request({
            url: app.globalData.baseurl+'/api/city/queryPage',
            method: 'POST',
            data:{
                limit:100,  
                            
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                var cnmes = that.data.columns ;
               
                for(var i = 0; i < res.data.data.list.length; i++){
                    cnmes.push(res.data.data.list[i].name);
                }  
              that.setData({
                   clist:res.data.data.list,
                   columns:cnmes
                });
                
              
             
            }
          });
        
     
    },
    save(){
        if(!that.data.sname){
            wx.showToast({
              title: '请输入起点',
            })
        }
        if(!that.data.ename){
            wx.showToast({
              title: '请输入终点',
            })
        }
        if(!that.data.cid){
            wx.showToast({
              title: '请选择城市',
            })
        }
        wx.request({
            url: app.globalData.baseurl+'/line/searchLine',
            method: 'POST',
            data:{
                sname:that.data.sname,  
                ename:that.data.ename, 
                cid:that.data.cid,              
            },            
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
               
              that.setData({
                  
                   info:res.data.data
                });
                
              
             
            }
          });
    },
   
    typeConfirm(event) {
    
        const { picker, value, index } = event.detail;
        that.setData({
            showType: false,
            cname:that.data.clist[index].name,
            cid:that.data.clist[index].id
          }); 
      },
      choseCity(){
        that.setData({
            showType: true
          });   
      },
      typeCancel() {
        that.setData({
            showType: false
          });   
      },
      onChangeS(e){
         
        that.setData({
            sname: e.detail
          }); 
       
      
    },
    onChangeZ(e){
        console.log(e.detail)   
       
        that.setData({
            ename: e.detail
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