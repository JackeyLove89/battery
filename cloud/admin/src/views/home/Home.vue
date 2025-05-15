<template>
  <el-container class="home-page">
    <!-- 头部 -->
    <el-header>
      <div class="home-welcome">
        <img src="~assets/img/logo.jpg" alt="">
        <span>电车电池监测管理系统</span>
      </div>
    <el-button type="primary">{{ timeDate|formatDate }}</el-button>
	  <el-button>当前用户：{{nickname}}</el-button>
      <el-button type="danger" @click="logoutClick">退出</el-button>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '66px' : '200px'">
        <!-- 折叠按钮 -->
        <p class="btn-zd" @click="zdClick" >|||</p>
        <!-- 侧边菜单 -->
        <el-menu
          :default-active="activePath"
          class="aside-nav"
          background-color="#7DBE3E"
          text-color="#e0e0e0"
          active-text-color="#ff0000"
          :unique-opened="true"
          :collapse="isCollapse"
          router
          :collapse-transition="false">
          <!-- 每次只能展开一个 unique-opened="true" -->
          <!-- 导航一 -->
          <el-submenu :index="item.path" v-for="(item, index) in menusList" :key="index">
            <template slot="title">
              <i class="icon-color" :class="menuIcon[index]"></i>
              <span>{{item.authName}}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menuItem :index="'/' + i.path" v-for="(i, j) in item.children" :key="j" @click="itemClick(i.path)">
              <template slot="title">
                <i class="icon-color el-icon-menu"></i>
                <span>{{i.authName}}</span>
              </template>
            </el-menuItem>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 主体内容 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getMenus } from 'network/home'
var formatNum = function(num){
  return num < 10 ? "0" + num : num
}
export default {
  name: 'Home',
  data(){
    return {
      menusList:
	  [
		
  
		   
      ],
	  menusList1:
	  [
	  		{"id":1,"authName":"用户管理","path":"users","children":[
	  		
	  			  {"id":2,"authName":"用户列表","path":"users","children":[],"order":null},
	  			  ],"order":1},
	   {"id":2,"authName":"公告管理","path":"notice","children":[
	   		  {"id":1,"authName":"公告列表","path":"notice","children":[],"order":null}],"order":2},
			  {"id":3,"authName":"电池信息","path":"goods","children":[
			  		  {"id":1,"authName":"电池信息","path":"goods","children":[],"order":null}],"order":3},
			  
	   
				  {"id":6,"authName":"意见反馈","path":"advice","children":[
				  		  {"id":1,"authName":"意见反馈","path":"advice","children":[],"order":1},
				  		
				  		  ],"order":6},
				  
			{"id":7,"authName":"数据统计","path":"statistics","children":[
					  {"id":1,"authName":"系统数据统计","path":"statistics","children":[],"order":1}, 
						 
					  ],"order":7},			  
						  
	    
	  		   
	  ],
	 
      menuIcon: ['el-icon-s-custom', 'el-icon-s-operation', 'el-icon-s-goods','el-icon-s-goods', 'el-icon-s-order', 'el-icon-s-data','el-icon-files'],
      isCollapse: false,
      activePath:'',
	     nickname:'',
       timeDate:new Date(),
    }
  },

  filters: {
    // 实时-时间-数据转换
    formatDate(value) {
      var year = value.getFullYear()
      var month = formatNum(value.getMonth() + 1)
      var day = formatNum(value.getDate())
      var hour = formatNum(value.getHours())
      var min = formatNum(value.getMinutes())
      var sec = formatNum(value.getSeconds())
      return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec
    }
  },
 
  mounted() {
    // 实时-时间-计时
    var that = this
    var timer = setInterval(function(){
      that.timeDate = new Date()
    },1000)
  },
 
  // 实时-时间
  beforeDestroy() {
    if(this.timer){
      clearInterval(this.timer)
    }
  },

  methods: {
    /**
     * 注销账号模块
     */
    logoutClick(){
      window.sessionStorage.clear()
      this.$message('账号已注销')
      this.$router.push('/login')
    },
  
    /**
     * 折叠侧边栏模块
     */
    zdClick(){
      this.isCollapse = !this.isCollapse
    },
    /**
     * 二级菜单被点击模块
     */
    itemClick(path){
      this.activePath = '/' + path
      // console.log(path)
      // 保存当前激活的菜单path
      window.sessionStorage.setItem('activePath',this.activePath)
    }
  },
  created(){
    /**
     * 侧边栏数据请求处理模块
     */
    // getMenus().then((res) => {
    //   this.menusList = res.data.data
    // })
    // 获取当前激活的菜单path
    this.activePath = window.sessionStorage.getItem('activePath')
	   this.nickname = window.sessionStorage.getItem('nickname')
	   
	    this.type = window.sessionStorage.getItem('type')
		if(this.type=='1'){
			this.menusList=this.menusList1
		}else{
			this.menusList=this.menusList2
		}
  }
}
</script>

<style lang="less" scoped>
  .home-page{
    height: 100%;
  }
  .home-welcome{
	  width: 100%;
  }
  .el-header{
    background-color:#7DBE3E;
    display: flex;
    justify-content: space-between;
    align-items: center;
    >div{
      display: flex;
      align-items: center;
      img{
        display: inline-block;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background-color: #fff;
      }
    }
    span{
      font-size: 16px;
      color:#fff;
      padding-left: 10px;
    }
  }
  .el-aside{
    overflow-x: hidden;
    background-color:#7DBE3E;
    transition: width .3s;
  }
  .el-menu{
    border-right: none;
  }
  .el-main{
    background-color: #d8e3e7;
  }
  .icon-color{
    color:#e0e0e0;
    margin-right: 5px;
  }
  .btn-zd{
    text-align: center;
    color:#e0e0e0;
    background-color:#7DBE3E;
    height: 30px;
    line-height: 28px;
    box-shadow: 0 0 2px #0f59a4;
    cursor: pointer;
    user-select:none;
  }
</style>