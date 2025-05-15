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
    if(app.globalData.time<new Date().getTime()/1000){
          
        return
    }
    wx.request({
      url: app.globalData.baseurl+'/notice/queryPage',
      method:'POST',
      data: {
        page: page,
        limit: page_size,
        type:'1'
      },    
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },     
      success: function (res) {
        that.setData({
            hidden: false
          });
        var list = that.data.list ;
        if(list.length<page_size){
            loadmore=false
        }
        for(var i = 0; i < res.data.data.list.length; i++){
            list.push(res.data.data.list[i]);
        }
        that.setData({
          list: list
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
    scrollHeight: 0
    },
    attached(){
        that=this
        this.getBannerList()
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
        getBannerList(){
            var that=this;
            wx.showLoading({
              title: '加载中',
            })
            wx.request({
                url: app.globalData.baseurl+'/notice/queryPage',
                method: 'POST',
                data:{
                    type:0,              
                }, 
                header: {
                    'content-type': 'application/x-www-form-urlencoded' 
                  },             
                success: function (res) {
                   wx.hideLoading()
                   console.log(res.data.data.list)
                   if(res.data.code==200){
                    that.setData({
                        bannerList:res.data.data.list
                    })
                 
                   }
                },
                fail:function (res) {
                    wx.hideLoading()
                    wx.showToast({
                      title: '请检查服务是否开启',
                    })
                  
                 },
              })
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
         
        itemBClick: function (e) {
            let index = e.currentTarget.dataset.index;
            let item = this.data.bannerList[index];
       
           wx.navigateTo({
             url: '/pages/com/main/detial/detial?id='+item.id,          
           })
          },
        itemClick: function (e) {
            let index = e.currentTarget.dataset.index;
            let item = this.data.list[index];
       
           wx.navigateTo({
             url: '/pages/com/main/detial/detial?id='+item.id,          
           })
          },

            }
})
