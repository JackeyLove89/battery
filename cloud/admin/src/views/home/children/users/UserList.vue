<template>
  <div>
    <!-- 导航 -->
    <mbx-nav one-nav="用户管理" two-nav="用户列表"></mbx-nav>
    <!-- 卡片试图 -->
    <el-card>
      <!-- 操作 区域 -->
      <el-row :gutter="30">
       <el-col :span="10">
          <el-input placeholder="请输入账号/姓名" v-model="queryInfo.key" clearable @clear="getUsersInfo">
            <el-button slot="append" icon="el-icon-search" @click="getUsersInfo"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addUser">添加</el-button>        
       
        </el-col>
      </el-row>
      <!-- 用户列表表格 区域 -->
      <el-table id="etable" :data="userList" stripe border class="user-table">
        <el-table-column type="index" label="#"></el-table-column>
		<el-table-column label="头像" prop="avatarurl">		 
		  <template v-slot="scope">
		   <img  v-image-preview :src="getImgUrl(scope.row.avatarurl)"  class="el-avatar"/>
		  </template>
		  </el-table-column>
        <el-table-column prop="account" label="账号"></el-table-column>
		<el-table-column prop="pwd" label="密码">******</el-table-column>
		<el-table-column prop="nickname" label="姓名"></el-table-column>     
		 <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
		 
		 <el-table-column prop="ctime" label="创建时间"></el-table-column>
       <el-table-column label="禁用/启用" prop="status">
         <!-- 作用于插槽 取出本行数据 (在父组件中使用了子组件，在子组件插槽中（标签区域）需要获取子组件的数据  v-slot="xxx"   xxx.row:子组件数据) -->
         <template v-slot="scope">
			 <el-switch v-model="scope.row.status"
			 :active-value="0"
			 :inactive-value="1"
			 @change="statusClick(scope.row)">			
			 </el-switch>
			
       
         </template>
       </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="changeUserInfo(scope.row)"></el-button>	
			<el-button size="mini" type="danger" icon="el-icon-delete"  @click="delClick(scope.row)"></el-button>
      <!--      <el-button size="mini" type="warning" icon="el-icon-delete"  v-if="scope.row.status==0" @click="statusClick(scope.row,1)">禁用</el-button>
			<el-button size="mini" type="success" icon="el-icon-delete"  v-else @click="statusClick(scope.row,0)">启用</el-button>  -->  
	
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
    <!-- 添加用户对话框 -->
    <el-dialog
      title="添加用户"
      :visible.sync="addUserShow"
      width="50%"
      :before-close="addClose">
      <el-form :rules="rules" ref="addForm" :model="addForm" label-width="70px">
		  <el-form-item label="头像" prop="avatarurl">
		   <el-upload
		     class="avatar-uploader"
		     action="http://localhost:9320/api/upload"
		     :show-file-list="false"
		     :on-success="addAvatarSuccess"
		     :before-upload="beforeAvatarUpload">
		     <img v-if="addForm.avatarurl" :src="getImgUrl(addForm.avatarurl)" class="avatar">
		     <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		   </el-upload>
		  </el-form-item>
        <el-form-item label="账户" prop='account'>
          <el-input v-model="addForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop='pwd'>
          <el-input v-model="addForm.pwd"></el-input>
        </el-form-item>
		<el-form-item label="姓名" prop='nickname'>
		  <el-input v-model="addForm.nickname"></el-input>
		</el-form-item>
		<el-form-item label="性别" prop='gender' >
			<el-select v-model="addForm.gender" placeholder="请选择性别">
			      <el-option label="男" value="男"></el-option>
			      <el-option label="女" value="女"></el-option>
			    </el-select>
			
		</el-form-item>
        <el-form-item label="电话" prop='phone'>
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>		
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addClose">取 消</el-button>
        <el-button type="primary" @click="addClick">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户对话框 -->
    <el-dialog
      title="修改用户"
      :visible.sync="changeUserShow"
      width="50%"
      :before-close="changeClose">
      <el-form :rules="rules" ref="changeForm" :model="changeForm" label-width="70px">
		  <el-form-item label="头像" prop="avatarurl">
		   <el-upload
		     class="avatar-uploader"
		     action="http://localhost:9320/api/upload"
		     :show-file-list="false"
		     :on-success="changeAvatarSuccess"
		     :before-upload="beforeAvatarUpload">
		     <img v-if="changeForm.avatarurl" :src="getImgUrl(changeForm.avatarurl)" class="avatar">
		     <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		   </el-upload>
		  </el-form-item>
        <el-form-item label="账户" prop="account">
          <el-input v-model="changeForm.account" ></el-input>
        </el-form-item>
		<el-form-item label="密码" prop="pwd">
		  <el-input v-model="changeForm.pwd" ></el-input>
		</el-form-item>
		<el-form-item label="姓名" prop='nickname'>
		  <el-input v-model="changeForm.nickname" ></el-input>
		</el-form-item>
        <el-form-item label="性别" prop='gender' >
     	<el-select v-model="changeForm.gender" placeholder="请选择性别">
     	      <el-option label="男" value="男"></el-option>
     	      <el-option label="女" value="女"></el-option>
     	    </el-select>
     	
     </el-form-item>
        <el-form-item label="电话" prop='phone'>
          <el-input v-model="changeForm.phone"></el-input>
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
  getUsers, 
  addUserInfo,
  queryIdUser,
  changeUser,
  delUsers,
  } from 'network/user'
import {baseUrl} from 'network/global'
export default {
  name: 'UserList',
  components: {
    mbxNav
  },
  data(){
    /**
     * 邮箱验证规则
     */
    var validateEmail = (rule, value, cb) => {
      // 验证邮箱的正则
      const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if(regEmail.test(value)){
        return cb()
      }
      cb(new Error('请输入合法的邮箱'))
    };

    /**
     * 手机号验证规则
     */
    var validateMobile = (rule, value, cb) => {
      // 验证手机号正则
      const regMobile = /^1[34578]\d{9}$/
      if(regMobile.test(value)){
        return cb()
      }
      cb(new Error('请输入合法的手机号'))
    }
    return {
      userList: [],
	  gender: '男',
	  imageUrl: '',
	 
     // 订单列表查询参数
     queryInfo: {
       key: '',
	    type: '0',
       page: 1,
       limit: 10
     },    
      // 全部用户个数
      total: 0,
      // 添加用户对话框 显示隐藏
      addUserShow: false,
      // 添加用户数据
      addForm: {
       account: '',
       pwd: '',
       nickname: '', 	 
       gender:'',	
       phone: '',  
	   avatarurl: '',  
	   type:'0'
      },
      // 修改用户对话框 显示隐藏
      changeUserShow: false,
      //修改用户数据
      changeForm: {
		 
	  },
	  
      // 添加用户表单验证规则
      rules: {
        account: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          { min: 3, max: 10, message: '请输入3到10个字符', trigger: 'blur' }
        ],
        pwd: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          { min: 6, message: '密码必须大于6个字符', trigger: 'blur' }
        ],
       
        phone: [
          { message: '请输入手机号', trigger: 'blur'},
          { validator: validateMobile, trigger: 'blur' }
        ]
      },   
      // 需要分配角色的用户信息
      userInfo: {},
      // 被选中的option  value(id)
      selValue: '',
      // 所有角色信息
      jsList: []
    }
  },
  created(){
    this.getUsersInfo()
  },
  methods: {
    /**
     * 获取、更新用户数据
     */
	getImgUrl(url){
		
	console.log(baseUrl())
	      return  url
	    },

    getUsersInfo(){		
      getUsers(this.queryInfo).then((res) => {
       
       if(res.data.code == 200){
         //获取成功了
          console.log(res.data.data)
         this.userList = res.data.data.list
         this.total = res.data.data.total
        }
      }).catch((err) => {
        // console.log(err)
      })
    },
	yesAllotClick(){},
     changeAvatarSuccess(res, file) {
		  this.imageUrl = URL.createObjectURL(file.raw);
		  if(res.code === 200){
		   //获取成功了
		         this.changeForm.avatarurl=res.data;
		  
		  }          
		  
         },
		 addAvatarSuccess(res, file) {
		 		  this.imageUrl = URL.createObjectURL(file.raw);
		 		  if(res.code === 200){
		 		   //获取成功了
		 		      this.addForm.avatarurl=res.data;
		 		  
		 		  }          
		 		  
		     },
     beforeAvatarUpload(file) {
        
           const isLt2M = file.size / 1024 / 1024 < 2;
   
           
           if (!isLt2M) {
             this.$message.error('上传头像图片大小不能超过 2MB!');
           }
           return  isLt2M;
         },
    /**
     * 分页处理
     */
    // 监听 每页条数 pagesize 变化
    handleSizeChange(value){
      // console.log(value)
      this.queryInfo.limit = value
      this.getUsersInfo()
    },
    //监听 当前页 pagenum 发生改变
    handleCurrentChange(value){
      // console.log(value)
      this.queryInfo.page = value
      this.getUsersInfo()
    },
    downloadExcel() {
      this.$confirm("确定要导出列表文件?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
        
          this.toExcel()
        
        })
        .catch(() => {});
    },
    toExcel(){
      let tables = document.getElementById("etable");
      let table_book = XLSX.utils.table_to_book(tables);
      var table_write = XLSX.write(table_book, {
        bookType: "xlsx",
        bookSST: true,
        type: "array",
      });
      try {
        this.$FileSaver.saveAs(
          new Blob([table_write], { type: "application/octet-stream" }),
          "sheetjs.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, table_write);
      }
      return table_write;
    },
  
    /**
     * 添加用户模块
     */
    // 点击添加用户 / 显示用户对话框
    addUser(){
      this.addUserShow = true
    },
    //添加用户对话框关闭
    addClose(){
      this.$refs.addForm.resetFields()
      this.addUserShow = false
    },
    /**
     * 添加用户请求模块
     */
    addClick(){
      // 进行表单验证
      this.$refs.addForm.validate((reg) => {
        if(reg){
          // 验证成功发（请求）
          addUserInfo(this.addForm).then((res) => {
            // console.log(res)
            if(res.data.code === 200){
              this.addUserShow = false
              this.$message.success(res.data.msg)
              this.getUsersInfo()
              return
            }
            this.$message.success(res.data.msg) 
          })
        }
      })
    },

    /**
     * 修改用户信息模块
     */
    // 修改用户对话框关闭
    changeClose(){
      this.$refs.changeForm.resetFields()
      this.changeUserShow = false
    },
    //点击修改用户信息  显示修改用户对话框
    changeUserInfo(info){
      this.changeUserShow = true
      // console.log(info)
      this.changeForm = info
      
    },
    /**
     * 修改用户请求模块
     */
    changeClick(){
      // 表单验证
      this.$refs.changeForm.validate((reg) => {
        // 验证成功
        if(reg){
          /**
           * 修改请求
           */
          changeUser(this.changeForm).then((res) => {
            // console.log(res)
            if(res.data.code === 200){
              this.changeUserShow = false
              this.$message.success(res.data.msg)
              this.getUsersInfo()
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
		  this.$confirm('您确定要永久删除用户吗？', '永久删除' ,{
		    confirmButtonText: '确定',
		    cancelButtonText: '取消',
		    type: 'warning'
		  }).then(() => {
		    /**
		     * 删除请求模块
		     */
		    delUsers(userInfo.id).then((res) => {
		      if(res.data.code === 200){
		        this.$message.success('删除成功')
		        this.getUsersInfo()
		        return 
		      }
		      this.$message.success('删除失败，请稍后重试')
		    })
		  }).catch(() => {
		    this.$message('已取消')
		  })
	
     
    },
/**
     * 启用禁用操作
     */
    // 
    statusClick(userInfo){		
     
      changeUser(userInfo).then((res) => {
        if(res.data.code !== 200){
          this.$message.error('更改失败，请稍后重试')
          userInfo.status = !userInfo.status
          return
        }
        this.$message.success('更改成功')
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