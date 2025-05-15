<template>
  <div>
    <!-- 导航 -->
    <mbx-nav one-nav="数据统计" two-nav="充电数据统计"></mbx-nav>
    <!-- 卡片试图 -->
    <el-card>
    

    <el-row :gutter="20" class="chart-row">
          <el-col :span="20">
            <div class="chart-content">
              <div id="chart1" :style="{ width: '1000px', height: '500px' }"></div>
            </div>
          </el-col>
       
      
		
          
         
    </el-row>

    <el-row :gutter="250" class="chart-row">
        
		<el-col :span="12">
		  <div class="chart-content">
		    <div id="chart3" :style="{ width: '700px', height: '500px' }"></div>
		  </div>
		</el-col>
          
         
    </el-row>
    </el-card>
  
  </div>
</template>

<script>
import mbxNav from 'components/MbxNav'
import { 
  getShopTj,
 
  } from 'network/user'

export default {
  name: 'Statistics',
  components: {
    mbxNav
  },
  data(){
 
    return {
    
      info: {
       
      },
	  queryInfo: {
	    type: 0,	   
	   
	   
	  }, 
	   queryInfo1: {
	     type: 1,	   
	    
	    
	   },  
	   queryInfo2: {
	     type: 2,	   
	    
	    
	   },  
	 
    }
  },
  created(){
    this.getTj1()
	
  },
  mounted() {
   
  },
  methods: {
    /**
     * 获取、更新用户数据
     */
	//   获取所有的商品分类列表
	getTj1 () {
	
	    getShopTj(this.queryInfo).then(res => {
	    // console.log(res)
	    if (res.data.code !== 200) {
	      return this.$message.error('获取数据列表失败！')
	    }
	    this.info1 = res.data.data

   
        this.drawLine("chart1",'充电桩充电情况统计');
		  })
		
		
		
		
	
	},
  
  drawLine(name,title) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById(name));
      let datex=[],zss=[],tks=[],qxs=[],prices=[];
      this.info1.forEach(function(item, index) {
        datex[index]=item.name
        zss[index]=item.tnum  
			  tks[index]=item.tknum  
			   zss[index]=item.tnum  
			    qxs[index]=item.qxnum  
				 prices[index]=item.price  
      })
   let   option = {
        title: {
          text: title+'充电情况'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['数量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: datex
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '下单总数量',
            type: 'line',
            stack: 'Total',
            data: zss
          },
		  {
		    name: '退款数量',
		    type: 'line',
		    stack: 'Total',
		    data: tks
		  },
		  {
		    name: '取消总数量',
		    type: 'line',
		    stack: 'Total',
		    data: qxs
		  },
		  {
		    name: '总金额',
		    type: 'line',
		    stack: 'Total',
		    data: prices
		  }
         
        ]
      };
           myChart.setOption(option);
      },
	 
 drawLine1(name,title) {
	        // 基于准备好的dom，初始化echarts实例
	        let myChart = this.$echarts.init(document.getElementById(name));
	        let datex=[],hyd=[],xzs=[];
	        this.info2.forEach(function(item, index) {
	          datex[index]=item.name
	          hyd[index]=item.value       
	        })
	     let   option = {
	          title: {
	            text: title+'评分排行榜'
	          },
	          tooltip: {
	            trigger: 'axis'
	          },
	          legend: {
	            data: ['评分']
	          },
	          grid: {
	            left: '3%',
	            right: '4%',
	            bottom: '3%',
	            containLabel: true
	          },
	          toolbox: {
	            feature: {
	              saveAsImage: {}
	            }
	          },
	          xAxis: {
	            type: 'category',
	            boundaryGap: false,
	            data: datex
	          },
	          yAxis: {
	            type: 'value'
	          },
	          series: [
	            {
	              name: '评分',
	              type: 'line',
	              stack: 'Total',
	              data: hyd
	            }
	           
	          ]
	        };
	             myChart.setOption(option);
	        },
			drawLine2(name,title) {
			      // 基于准备好的dom，初始化echarts实例
			      let myChart = this.$echarts.init(document.getElementById(name));
			      let datex=[],hyd=[],xzs=[];
			      this.info3.forEach(function(item, index) {
			        datex[index]=item.name
			        hyd[index]=item.value       
			      })
			   let   option = {
			        title: {
			          text: title+'评分排行榜'
			        },
			        tooltip: {
			          trigger: 'axis'
			        },
			        legend: {
			          data: ['评分']
			        },
			        grid: {
			          left: '3%',
			          right: '4%',
			          bottom: '3%',
			          containLabel: true
			        },
			        toolbox: {
			          feature: {
			            saveAsImage: {}
			          }
			        },
			        xAxis: {
			          type: 'category',
			          boundaryGap: false,
			          data: datex
			        },
			        yAxis: {
			          type: 'value'
			        },
			        series: [
			          {
			            name: '评分',
			            type: 'line',
			            stack: 'Total',
			            data: hyd
			          }
			         
			        ]
			      };
			           myChart.setOption(option);
			      },
        
  }
}
</script>

<style lang="less" scoped>
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
 
  .bg-purple-1 {
    background: #67C23A;
  }
  
  .bg-purple-2{
    background: #E6A23C;
  }
  
  .bg-purple-3 {
    background: #F56C6C;
  }
  
  .bg-purple-4{
    background: #909399;
  }
  .grid-content {
    border-radius: 4px;
    height: 200px;
    line-height: 200px;
    text-align: center;
    font-size: 35px;
    color: #fff;
  }
  .chart-row{
    margin-bottom: 100px;
    margin-top: 100px;
  }
  .chart-content{
    border-radius: 4px;
   
   
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>