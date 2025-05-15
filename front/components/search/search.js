const app=getApp()
var that={}

var page = 1;
var page_size = 100;
var loadmore=true;
// 请求数据
var loadMore = function (that) {
    that.setData({
      hidden: false
    });
    if(app.globalData.time<new Date().getTime()/1000){
          
        return
    }
    wx.request({
      url: app.globalData.baseurl+'/api/msg/queryPage',
      method:'POST',
      data: {
        uid: wx.getStorageSync('user').id,    
        page:1,
        limit: page_size      
      },    
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },     
      success: function (res) {
        that.setData({
            hidden: false
          });
      
        that.setData({
          list: res.data.data
        });
        page++;
       
      }
    });
  }
 
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
    hidden: true,
    list: [],
    scrollTop: 0,
    scrollHeight: 0,
    keys:'',     
    },
    attached(){
        that=this    
        wx.getSystemInfo({
            success: function (res) {
              that.setData({
                scrollHeight: res.windowHeight
              });
            }
          });
          loadMore(that);
     
    },
    /**
     * 组件的方法列表
     */
    pageLifetimes: {

        show() { // 页面被展示时执行
    
            loadMore(that);
    
        },
    
        hide() { // 页面被隐藏时执行
    
          console.log('hide')
    
        },
    
        resize() { // 页面尺寸变化时执行
    
          console.log('resize')
    
        }
        
    
      },
    
    methods: {
       
        
         //下拉加载
        bindDownLoad: function () {
            var that = this;
            if(loadmore){
                loadMore(that);
            }else{
                wx.showToast({
                  title: '没有更多数据',
                })
            }
           
        },
        
        scroll: function (event) {
            //该方法绑定了页面滚动时的事件，这里记录了当前的 position.y 的值,为了请求数据之后把页面定位到这里来。
            this.setData({
            scrollTop: event.detail.scrollTop
            });
        },
        search(e){
            
              wx.navigateTo({
                url: '/pages/com/main/goods/search',
              })
            },
     
        onClickLeft(){
            wx.navigateTo({
                url: '/pages/com/main/goods/fj',          
              })
        },
        itemClick: function (e) {
            let index = e.currentTarget.dataset.index;
            let item = this.data.list[index];
       
           wx.navigateTo({
             url: '/pages/com/main/goods/goods?id='+item.id,          
           })
          },

            }
})
