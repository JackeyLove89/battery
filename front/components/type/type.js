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
      url: app.globalData.baseurl+'/api/car/queryPage',
      method:'POST',
      data: {
        page:page,
        limit:page_size,
        uid: wx.getStorageSync('user').id ,
        lng:that.data.lng,
        key:that.data.keys
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
        active:0,
        hidden: true,
        list: [],
        scrollTop: 0,
        scrollHeight: 0,
        keys:'',   
        address:'位置',   
        lat:0, 
        lng:0,   
    },
    attached(){
        that=this;
        var address=  wx.getStorageSync('address') 
        var lat=  wx.getStorageSync('lat') 
        var lng=  wx.getStorageSync('lng') 
        if(lat){
            that.setData({
                address:address,
                lat:lat,
                lng:lng
            })
        }else{
            that.setData({
                address:'位置',
               
            })
        }
       
      
        loadMore(that);
        wx.getSystemInfo({
            success: function (res) {
              that.setData({
                scrollHeight: res.windowHeight
              });
            }
          });
    },
    /**
     * 组件的方法列表
     */
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
        itemClick: function (e) {
            let index = e.currentTarget.dataset.index;
            let item = this.data.list[index];
       
            wx.navigateTo({
                url: '/pages/com/main/goods/park?id='+item.id,          
              })
          },
          onClickLeft(){
           wx.chooseLocation({
               success(res){
                    console.log(res)
                    that.setData({
                        address:res.name.substr(0,8),
                        lat:res.latitude,
                        lng:res.longitude
                    })
                    wx.setStorageSync('address',res.name.substr(0,8))
                    wx.setStorageSync('lat',res.latitude)
                    wx.setStorageSync('lng',res.longitude)
                    loadMore(that);
               }
           })
        },
          onSearch(e){
            that.setData({
                keys: e.detail
              });
              loadMore(that);
          }
    }
})
