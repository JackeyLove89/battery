const app=getApp()
var that={}

var page = 1;
var page_size = 10;
var loadmore=true;
// 请求数据
var loadMore = function (that) {
    that.setData({
      hidden: false
    });
    wx.request({
      url: app.globalData.baseurl+'/notice/advice/queryPage',
      method:'POST',
      data: {
        uid: wx.getStorageSync('user').id,
         
      },    
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },     
      success: function (res) {
        that.setData({
            hidden: false
          });
      
        that.setData({
          list: res.data.data.list
        });
       
       
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
    scrollHeight: 0
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
    methods: {
        onClickLeft(){
            wx.navigateBack({
                delta: 1
              });
        },
        
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
         
     
        itemClick: function (e) {
            let index = e.currentTarget.dataset.index;
            let item = this.data.list[index];
       
           wx.navigateTo({
             url: '/pages/com/mine/store/reply?id='+item.id,          
           })
          },
        
            }
})
