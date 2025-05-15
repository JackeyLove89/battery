<template>
  <div>
    <!-- 导航 -->
    <mbx-nav one-nav="数据统计" two-nav="系统数据统计"></mbx-nav>
    <!-- 卡片试图 -->
    <el-card>
      <el-row :gutter="20">
          <el-col :span="8"><div class="grid-content bg-purple-1">总人数:{{info.userNum}}</div></el-col>
           <el-col :span="8"><div class="grid-content bg-purple-2">今日活跃人数:{{info.loginNum}}</div></el-col>
          <el-col :span="8"><div class="grid-content bg-purple-3">今日新增人数:{{info.todayAddNum}}</div></el-col>
         
    </el-row>

    <el-row :gutter="250" class="chart-row">
          <el-col :span="6">
            <div class="chart-content">
              <div id="chart1" :style="{ width: '460px', height: '500px' }"></div>
            </div>
          </el-col>
          <el-col :span="18">
            <div class="chart-content">
              <div id="chart2" :style="{ width: '920px', height: '500px' }"></div>
            </div>
          </el-col>
        
          
         
    </el-row>

    </el-card>
  
  </div>
</template>

<script>
import mbxNav from 'components/MbxNav'
import { 
  getTj,
 
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
	 
    }
  },
  created(){
    this.getTj()
	
  },
  mounted() {
   
  },
  methods: {
    /**
     * 获取、更新用户数据
     */
	//   获取所有的商品分类列表
	getTj () {
	  getTj().then(res => {
	    // console.log(res)
	    if (res.data.code !== 200) {
	      return this.$message.error('获取数据列表失败！')
	    }
	    this.info = res.data.data

      this.drawPie("chart1");
    this.drawLine("chart2");
	  })
	},
  
  drawLine(name) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById(name));
      let datex=[],hyd=[],xzs=[];
      this.info.sevenTjs.forEach(function(item, index) {
        datex[index]=item.day
        hyd[index]=item.loginNum
        xzs[index]=item.todayAddNum 
      })
   let   option = {
        title: {
          text: '用户近七日活跃度'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['活跃数', '新增数']
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
            name: '活跃数',
            type: 'line',
            stack: 'Total',
            data: hyd
          },
          {
            name: '新增数',
            type: 'line',
            stack: 'Total',
            data: xzs
          }
        ]
      };
           myChart.setOption(option);
      },

          drawPie(name) {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById(name));
            
            let  option = {
              title: { text: "男女比例" },
                  legend: {
                    top: '5%',
                    left: 'center'
                  },
                  series: [
                    {
                      name: '男女比例',
                      type: 'pie',
                      radius: ['40%', '70%'],
                      avoidLabelOverlap: false,
                      itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                      },
                      label: {
                        show: false,
                        position: 'center'
                      },
                      emphasis: {
                        label: {
                          show: true,
                          fontSize: '40',
                          fontWeight: 'bold'
                        }
                      },
                      labelLine: {
                        show: false
                      },
                      data: [
                        { value: this.info.manNum, name: '男' },
                        { value: this.info.womanNum, name: '女' },
                        { value: this.info.otherNum, name: '未知' }
                      
                      ]
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