<template>
    <div class="reg-page">
        <div class="reg-box">        
           <el-form ref="regForm" :model="form" class="form-box" :rules="rules">
                <el-form-item prop="account">
                    <el-input v-model="form.account" prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item prop="pwd">
                    <el-input v-model="form.pwd" @keyup.enter.native='regClick' type="password" prefix-icon="el-icon-unlock"></el-input>
                </el-form-item>
                <el-form-item class="button-box">
                  <el-button type="primary" @click='regClick'>注册</el-button>
                  
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { register } from 'network/login'

export default {
    name: 'Reg',
    data() {
      return {
        form: {
            account: '',
            pwd: '',
			type:'1'
        },
        rules: {
          // 2.定义表单验证规则
          account: [
            // 必填，提示，失去焦点
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          pwd: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ]
        }
      }
    },
    created(){
      window.sessionStorage.removeItem('token')
      window.sessionStorage.removeItem('activePath')
    },
    methods: {
      // 登录成功提示
      regSuccess(){
        this.$message({
          message: '恭喜您，注册成功！',
          type: 'success'
        });
      },
      // 登录失败提示
      regError(){
        this.$message({
          message: '注册失败，请稍后再试！',
          type: 'error'
        });
      },
      regClick(){
         // 对整个表单进行校验，两个参数：是否校验成功 未通过校验的字段
               this.$refs.regForm.validate((valid) => {
                 // 验证通过 发送请求
                 if(valid){
                   register(this.form).then((res) => {
                     // console.log(res)
                     if(res.data.code == 200){
                       // 登录成功
                       this.regSuccess()                      
                       //转跳到主页
                       this.$router.push('/login');
                     }else{
                       // 登录失败
                       this.regError()
                     }
                   }).catch((err) => {
                     
                   })
                 }
               })
			   
          },
        
      reseClick(){
        // 对表单进行重置 resetFields()
        this.$refs.regForm.resetFields()
      }
    }
}
</script>

<style lang="less" scoped>
    .reg-page{
        height: 100%;
        background-color: #2474b5;
    }
    .reg-box{
        width: 600px;
        height: 380px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 0 4px rgba(0, 0, 0, .3);
        position: absolute;
        left:50%;
        top:50%;
        transform: translate(-50%,-50%)
    }
    .icon-tx{
        width: 200px;
        height: 200px;
        border-radius: 50%;
        overflow: hidden;
        padding:10px;
        background-color: #fff;
        box-shadow: 0 0 6px #ddd;
        box-sizing: border-box;
        position: absolute;
        left:50%;
        top:-100px;
        transform: translate(-50%, 0)
    }
    .icon-tx img{
        width: 100%;
        border-radius: 50%;
        box-shadow: 0 0 6px #ddd;
    }
    .form-box{
      width: 400px;
      margin:0 auto;
      padding-top: 130px;
    }
    .button-box{
      display: flex;
      justify-content: flex-end;
    }
</style>