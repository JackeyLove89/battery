const app=getApp()
var that={}
Component({
    /**
     * 组件的属性列表
     */
    properties: {

    },

    /**
     * 组件的初始数据
     */
    data: {

    },
    attached(){
         that=this;
         var user=  wx.getStorageSync('user')
         if(app.globalData.time<new Date().getTime()/1000){
          
            return
        }
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
               that.setData({
                 userInfo: res.data.data
                 });
            
              
             }
           });
    },
  
    pageLifetimes: {

        show() { // 页面被展示时执行
    
          console.log('show')
    
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
                that.setData({
                  userInfo: res.data.data
                  });
             
               
              }
            });
    
        },
    
        hide() { // 页面被隐藏时执行
    
          console.log('hide')
    
        },
    
        resize() { // 页面尺寸变化时执行
    
          console.log('resize')
    
        }
        
    
      },
    
    /**
     * 组件的方法列表
     */
    methods: {
        jumpstore(){
            wx.navigateTo({
              url: '/pages/com/mine/store/store',
            })
        },
        jumpcar(){
            wx.navigateTo({
                url: '/pages/com/mine/car/car',
              })
        },
        jumporder(){
            wx.navigateTo({
                url: '/pages/com/mine/order/order',
              })
        },
    }
})
