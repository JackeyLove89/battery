<template>
  <div>
    <!-- 导航 -->
    <mbx-nav one-nav="意见反馈" two-nav="反馈列表"></mbx-nav>
    <!-- 卡片试图 -->
    <el-card>
      <!-- 操作 区域 -->
      <el-row :gutter="2">        
       <el-col :span="6">
          <el-input placeholder="请输入关键词" v-model="queryInfo.key" clearable @clear="getList()">
            <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
          </el-input>
        </el-col>
        
       
       
      </el-row>
      <!-- 用户列表表格 区域 -->
      <el-table :data="dataList" stripe border class="user-table">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="uid" label="用户Id"></el-table-column>
    <el-table-column prop="title" label="标题"></el-table-column>
		<el-table-column prop="content" label="内容"></el-table-column>		
    <el-table-column prop="phone" label="联系电话"></el-table-column>
			 <el-table-column prop="reply" label="回复内容"></el-table-column>	
		 <el-table-column prop="ctime" label="创建时间"></el-table-column>
   
     <el-table-column label="操作">
          <template v-slot="scope">
           <el-button size="mini" type="primary" v-if="!scope.row.reply" @click="changeInfo(scope.row)">回复</el-button>	
			<el-button size="mini" type="danger" icon="el-icon-delete"  @click="delClick(scope.row)"></el-button>     
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页器 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

   <el-dialog
     title="回复"
     :visible.sync="changeUserShow"
     width="50%"
     :before-close="changeClose">
     <el-form :rules="rules" ref="changeForm" :model="changeForm" label-width="70px">
      
       <el-form-item label="内容" prop='reply'>
         <el-input v-model.trim="changeForm.reply"
                          type="textarea"
                          resize="none"
                          :rows="3"                           
                          :maxlength="500"
                           />
       </el-form-item>		
     </el-form>
     <span slot="footer" class="dialog-footer">
       <el-button @click="changeClose">取 消</el-button>
       <el-button type="primary" @click="changeClick">确 定</el-button>
     </span>
   </el-dialog>
 
  
  </div>
</template>

<script>
import mbxNav from 'components/MbxNav'

import { 
  getList,
  delInfo,
  upInfo
  } from 'network/advice'
import {baseUrl} from 'network/global'
export default {
  name: 'AdviceList',
  components: {
    mbxNav
  },
  data(){
    
    return {
      dataList: [],
	 
	
   
     // 订单列表查询参数
     queryInfo: {
       key: '',	   
      
       page: 1,
       limit: 10
     },    
      // 全部用户个数
      total: 0,
   // 修改用户对话框 显示隐藏
   changeUserShow: false,
   //修改用户数据
   changeForm: {
   		 
   },
    
   
    }
  },
  created(){
    this.getList()
  },
  methods: {
   

    getList(){		
      getList(this.queryInfo).then((res) => {
       
       if(res.data.code == 200){
         //获取成功了
          console.log(res.data.data)
         this.dataList = res.data.data.list
         this.total = res.data.data.total
        }
      }).catch((err) => {
        // console.log(err)
      })
    },	
    
    /**
     * 分页处理
     */
    // 监听 每页条数 pagesize 变化
    handleSizeChange(value){
      // console.log(value)
      this.queryInfo.limit = value
      this.getList()()
    },
    //监听 当前页 pagenum 发生改变
    handleCurrentChange(value){
      // console.log(value)
      this.queryInfo.page = value
      this.getList()
    },

   /**
    * 修改用户信息模块
    */
   // 修改用户对话框关闭
   changeClose(){     
     this.changeUserShow = false
   },
   //点击修改用户信息  显示修改用户对话框
   changeInfo(info){
     this.changeUserShow = true  
     this.changeForm = info

   },
   /**
    * 修改用户请求模块
    */
   changeClick(){     
         /**
          * 修改请求
          */
    this.$refs.changeForm.validate((reg) => {
       if(reg){
         // 验证成功发（请求）
         // 验证成功发（请求）
         upInfo(this.changeForm).then((res) => {
           // console.log(res)
           if(res.data.code === 200){
             this.changeUserShow = false
             this.$message.success(res.data.msg)
             this.changeForm={}           
             this.getList()
             return 
           }
           
         })
       }
     })
        
    
   },
    /**
     * 删除用户模块
     */
    // 
    delClick(userInfo){
    
		  // 确认删除吗消息框？
		  this.$confirm('您确定要永久删除吗？', '永久删除' ,{
		    confirmButtonText: '确定',
		    cancelButtonText: '取消',
		    type: 'warning'
		  }).then(() => {
		    /**
		     * 删除请求模块
		     */
		    delInfo(userInfo.id).then((res) => {
		      if(res.data.code === 200){
		        this.$message.success('删除成功')
		        this.getList()
		        return 
		      }
		      this.$message.success('删除失败，请稍后重试')
		    })
		  }).catch(() => {
		    this.$message('已取消')
		  })
	
     
    },   
	allotClose(){
	  this.selValue = ''
	},
   
 }
}
</script>

<style lang="less" scoped>
  .user-table{
    margin-top: 20px;
    font-size: 12px;
  }
  .el-pagination{
    margin-top: 20px;
  }
  .text{
    padding:5px 0;
  }
  .text-sel{
    padding-top: 10px;
  }
  
  .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 128px;
      height: 128px;
      display: block;
    }
	.el-avatar{
		width: 80px;
		height: 80px;
		display: block;
	}
</style>