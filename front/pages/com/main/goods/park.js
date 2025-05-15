const app=getApp()
var that={}
let chart;
let chart1;
let chart2;
let chart3;
import * as echarts from '../../../../components/ec-canvas/echarts';
function initChart(canvas, width, height) {
     chart = echarts.init(canvas, null, {
      width: width,
      height: height
    });
    canvas.setChart(chart);
  
    const option = {
        tooltip: {
          formatter: '{a} <br/>{b} : {c}%'
        },
       
        series: [
            
          {
                             // 将最大值动态设置为200
            max: that.data.max ,
            name: 'Pressure',
            type: 'gauge',
            progress: {
              show: true
            },
            detail: {
              valueAnimation: true,
              formatter: '{value}'
            },
            data: [
              {
                value: that.data.xh,
                name:'续航(km)'
              }
            ]
          }
        ]
      };
    chart.setOption(option,true);
    return chart;
  }

  function initChart1(canvas, width, height) {
     chart1 = echarts.init(canvas, null, {
      width: width,
      height: height
    });
    canvas.setChart(chart1);
  
    var option = {
        xAxis: {
          type: 'category',
          data: that.data.xlist
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: that.data.ylist,
            type: 'bar'
          }
        ]
      };
    chart1.setOption(option,true);
    return chart1;
  }

Page({

    /**
     * 页面的初始数据
     */
    data: {
        active:0,
        curmonth:'2023',
        list: [],
        dayx:[],
        sgy:[],
        tzy:[],
        bmiy:[],
        dyy:[],
        gyy:[],
        srb:[ { value: 1048, name: 'Search Engine' }],
        zcb:[ { value: 1048, name: 'Search Engine' }],
        ec: {
            chart: initChart
          },
         ec1: {
            chart: initChart1
          },
      
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        that=this;
        var user=  wx.getStorageSync('user')
        that.setData({
           
            id:options.id
          });
         
      
    },
   
    onClickLeft(){
        wx.navigateBack({
            delta: 1
          });
    },
    cd(){
        wx.showLoading({
          title: '快速充电中',
        })
        wx.request({
            url: app.globalData.baseurl+'/api/dc/upInfo',
            method:'POST',
            data: that.data.info,    
            header: {
                'content-type': 'application/json'
            },     
            success: function (res) {
              
                setTimeout(() => {
                    wx.showToast({
                        title: "充电完成",
                      })
                    wx.navigateBack()
                }, 5000);
              
             
            }
          });    
    },
   
    getSr(){
        var user=  wx.getStorageSync('user')
        wx.request({
            url: app.globalData.baseurl+'/api/dc/getInfo',
            method:'POST',
            data: {
              id: that.data.id,
             
            },    
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },     
            success: function (res) {
                var info=res.data.data
                var xlist = ['容量保持率','电压一致性','内阻一致性','温度一致性','自放电率'] ;
                var ylist =[info.rlb,info.dyb,info.nzb,info.wdb,info.zfdb] ;
               
                that.setData({
                    info:res.data.data,
                    max:parseInt(res.data.data.car.dec),
                    xh:res.data.data.xh,
                        xlist:xlist,
                        ylist:ylist       
                    });   
                  
               
                           
                 
                   
                     
                    
                      that.setData({ //重新setData渲染canvas
                        ec: {
                          onInit: initChart
                        }
                      })
                      that.setData({ //重新setData渲染canvas
                        ec1: {
                          onInit: initChart1
                        }
                      })
                   
                    
                    const option = {
                        tooltip: {
                          formatter: '{a} <br/>{b} : {c}%'
                        },
                        series: [
                            {
                                // 将最大值动态设置为200
                                max: that.data.max ,
                                name: 'Pressure',
                                type: 'gauge',
                                progress: {
                                    show: true
                                },
                                detail: {
                                    valueAnimation: true,
                                    formatter: '{value}'
                                },
                                data: [
                                    {
                                    value: that.data.xh,
                                    name: '续航(km)'
                                    }
                                ]
                                }
                        ]
                      };          
                    
                      chart.setOption(option,true);
                      var option1 = {
                        xAxis: {
                          type: 'category',
                          data: xlist
                        },
                        yAxis: {
                          type: 'value'
                        },
                        series: [
                          {
                            data: ylist,
                            type: 'bar'
                          }
                        ]
                      };
                      chart1.setOption(option1,true);

                   
            }
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
        that.getSr()
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