<template>
  <div>
    <!-- 导航 -->
    <mbx-nav one-nav="充电桩管理" two-nav="充电桩列表"></mbx-nav>
    <!-- 卡片试图 -->
    <el-card>	
      <!-- 操作 区域 -->
      <el-row :gutter="20">
      <el-col :span="10">
         <el-input placeholder="请输入关键词" v-model="queryInfo.key" clearable @clear="getUsersInfo">
           <el-button slot="append" icon="el-icon-search" @click="getUsersInfo"></el-button>
         </el-input>
       </el-col>    
       
        <el-col :span="6">
          <el-button type="primary" @click="addInfo">添加</el-button>
        </el-col>
       
      </el-row>
      <!-- 用户列表表格 区域 -->
      <el-table :data="dataList" stripe border class="user-table">
      <el-table-column type="index" label="#"></el-table-column>
		<el-table-column label="图片" prop="img">
		  <template v-slot="scope">
		   <img  v-image-preview :src="getImgUrl(scope.row.img)"  class="el-avatar"/>
		  </template>
		  </el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
	     
		  <el-table-column prop="lat" label="维度"></el-table-column>		
		  <el-table-column prop="lng" label="经度">	</el-table-column>	
		  <el-table-column prop="address" label="地址">	</el-table-column>	
         <el-table-column prop="dec" label="简介">	</el-table-column>	
		  <el-table-column prop="ktime" label="开放时间">	</el-table-column>	
		   <el-table-column prop="price" label="收费(元/每小时)">	</el-table-column>	
		    <el-table-column prop="gl" label="最大支持功率(kw)">	</el-table-column>	
			 <el-table-column prop="status" label="状态">	</el-table-column>	
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="changeInfo(scope.row)"></el-button>	
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
    <!-- 添加用户对话框 -->
    <el-dialog
      title="添加"
      :visible.sync="addInfoShow"
      width="50%"
      :before-close="addClose">
      <el-form :rules="rules" ref="addForm" :model="addForm" label-width="70px">
		 <el-form-item label="图片" prop="img">
		  <el-upload
		    class="avatar-uploader"
		    action="http://localhost:9320/api/upload"
		    :show-file-list="false"
		    :on-success="addAvatarSuccess"
		    :before-upload="beforeAvatarUpload">
		    <img v-if="addForm.img" :src="getImgUrl(addForm.img)" class="avatar">
		    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		  </el-upload>
		 </el-form-item>
        <el-form-item label="名称" prop='name'>
          <el-input  v-model="addForm.name"></el-input>
        </el-form-item>
		<el-row>
					   <el-col :span="8"><el-form-item label="选址地址" prop='name'>
							<el-button  @click="handleMapSelect" type="primary">选择地址</el-button>	
						</el-form-item>
						</el-col>
					    <el-col :span="8"> <el-form-item label="维度" prop='title'>
		 
						  <el-input readonly v-model="lat"></el-input>
						</el-form-item>
						</el-col>
						  <el-col :span="8">  
						   <el-form-item label="经度" prop='title'>
        
						 <el-input readonly v-model="lng"></el-input>
					   </el-form-item>
					   </el-col>
		</el-row>
		
		
       
    
	   <el-form-item label="详细地址" prop='address'>
	    
	     <el-input v-model="address"></el-input>
	   </el-form-item>
	   <el-form-item label="简介" prop='dec'>
	    
	     <el-input v-model="addForm.dec"></el-input>
	   </el-form-item>
	   <el-row>
	   			   <el-col :span="8">
					<el-form-item label="开放时间" prop='title'>
						    
					  <el-input  v-model="addForm.ktime"></el-input>
					</el-form-item>
	   				</el-col>
	   			    <el-col :span="8">
						<el-form-item label="价格(元/每小时)" prop='title'>
							    
						  <el-input type="number" v-model="addForm.price"></el-input>
						</el-form-item>
	   				</el-col>
	   				  <el-col :span="8">  
	   				  <el-form-item label="功率(KW)" prop='title'>
	   				  	    
	   				    <el-input type="number" v-model="addForm.gl"></el-input>
	   				  </el-form-item>
	   			   </el-col>
	   </el-row>
	   
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addClose">取 消</el-button>
        <el-button type="primary" @click="addClick">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户对话框 -->
    <el-dialog
      title="修改"
      :visible.sync="changeUserShow"
      width="50%"
      :before-close="changeClose">
      <el-form :rules="rules" ref="changeForm" :model="changeForm" label-width="70px">
		 <el-form-item label="图片" prop="img">
		  <el-upload
		    class="avatar-uploader"
		    action="http://localhost:9320/api/upload"
		    :show-file-list="false"
		    :on-success="changeAvatarSuccess"
		    :before-upload="beforeAvatarUpload">
		    <img v-if="changeForm.img" :src="getImgUrl(changeForm.img)" class="avatar">
		    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
		  </el-upload>
		 </el-form-item>
       <el-form-item label="名称" prop='name'>
          <el-input  v-model="changeForm.name"></el-input>
        </el-form-item>
       		<el-row>
       					   <el-col :span="8"><el-form-item label="选址地址" prop='name'>
       							<el-button  @click="handleMapSelect" type="primary">选择地址</el-button>	
       						</el-form-item>
       						</el-col>
       					    <el-col :span="8"> <el-form-item label="维度" prop='title'>
       		 
       						  <el-input readonly v-model="lat"></el-input>
       						</el-form-item>
       						</el-col>
       						  <el-col :span="8">  
       						   <el-form-item label="经度" prop='title'>
        
       						 <el-input readonly v-model="lng"></el-input>
       					   </el-form-item>
       					   </el-col>
       		</el-row>
       		
       		
       
           
       <el-form-item label="详细地址" prop='address'>
        
         <el-input v-model="address"></el-input>
       </el-form-item>
       <el-form-item label="简介" prop='dec'>
        
         <el-input v-model="changeForm.dec"></el-input>
       </el-form-item>
       <el-row>
       			   <el-col :span="8">
       					<el-form-item label="开放时间" prop='title'>
       						    
       					  <el-input  v-model="changeForm.ktime"></el-input>
       					</el-form-item>
       				</el-col>
       			    <el-col :span="8">
       						<el-form-item label="价格(元/每小时)" prop='title'>
       							    
       						  <el-input type="number" v-model="changeForm.price"></el-input>
       						</el-form-item>
       				</el-col>
       				  <el-col :span="8">  
       				  <el-form-item label="功率(KW)" prop='title'>
       				  	    
       				    <el-input type="number" v-model="changeForm.gl"></el-input>
       				  </el-form-item>
       			   </el-col>
       </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeClose">取 消</el-button>
        <el-button type="primary" @click="changeClick">确 定</el-button>
      </span>
    </el-dialog>
     <el-dialog
       title="经纬度选择"
       :visible.sync="mapDialogVisible"
       :close-on-click-modal="false"
       width="50%"
     >
       <el-form label-position="right" label-width="120px">
         <el-form-item label="详细地址">
           <el-autocomplete
             v-model="address"
             :fetch-suggestions="kwSearch"
             placeholder="请输入详细地址"
             style="width: 100%"
             :trigger-on-focus="false"
             :debounce=0
             @select="handleSelectAddress"
           />
         </el-form-item>
       </el-form>
     
       <baidu-map class="map" :center="center" style="width: 700px;height: 400px;" :zoom="zoom" @ready="handler" :scroll-wheel-zoom="true" @click="clickMap">
         <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
         <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
       </baidu-map>
       <div slot="footer" class="dialog-footer">
         <el-button @click="handleMapSubmit" type="primary">提交</el-button>
         <el-button @click="mapDialogVisible = false">返回</el-button>
       </div>
     </el-dialog>
     

   
  
  </div>
</template>

<script>
import mbxNav from 'components/MbxNav'
import loadBMap from '../../../../network/loadBMap.js'
const AK = 'iQAwNMwvqOPOc8HJ5SYLSpHIPmVFhU66'
let local = null
import { 
  getPList, 
  addPInfo,
  getPInfo,
  upPInfo,
  delPInfo, 
  getUsers
  } from 'network/dic'
import {baseUrl} from 'network/global'
export default {
  name: 'NoticeList',
  components: {
    mbxNav
  },
  data(){
    
    return {
      dataList: [],
	 
	  imageUrl: '',
   
     // 订单列表查询参数
     queryInfo: {
       key: '',	   
       page: 1,
       limit: 10
     },    
      // 全部用户个数
      total: 0,
      // 添加用户对话框 显示隐藏
      addInfoShow: false,
      // 添加用户数据
      addForm: {
      name:'',
	  img:'',
	  lat:'',
      lng:'',
	  dec:'',
	  address:''
      },
      // 修改用户对话框 显示隐藏
      changeUserShow: false,
      //修改用户数据
      changeForm: {
		 
	  },
	  
      // 添加用户表单验证规则
      rules: {
       
      },  
	  lat:'',
	  lng:'',
	    mapDialogVisible: false,
	           address: '',
	           longitude: null,
	           latitude: null,
	           center: {
				  
	             lng: 119.30346965887891,
	             lat: 26.080428771679063
	           },
	           zoom: 15,
	           map: null,
	           BMap: null

	
   
    }
  },
   async mounted() {
        await loadBMap(AK)
      },
  created(){
    this.getList()
   
  },
  methods: {
	
	getImgUrl(url){	

	      return  url
	    },

    getList(){		
      getPList(this.queryInfo).then((res) => {
       
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
     changeAvatarSuccess(res, file) {
		  this.imageUrl = URL.createObjectURL(file.raw);
		  if(res.code === 200){
		   //获取成功了
		         this.changeForm.img=res.data;
		  
		  }          
		  
         },
	addAvatarSuccess(res, file) {
		 		  this.imageUrl = URL.createObjectURL(file.raw);
		 		  if(res.code === 200){
		 		   //获取成功了
		 		      this.addForm.img=res.data;
		 		  
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
      this.getList()()
    },
    //监听 当前页 pagenum 发生改变
    handleCurrentChange(value){
      // console.log(value)
      this.queryInfo.page = value
      this.getList()
    },

    /**
     * 添加用户模块
     */
    // 点击添加用户 / 显示用户对话框
    addInfo(){
      this.addInfoShow = true
    },
    //添加用户对话框关闭
    addClose(){   
      this.addInfoShow = false
    },
    /**
     * 添加用户请求模块
     */
    addClick(){   
		this.addForm.lat=this.lat
		this.addForm.lng=this.lng
		this.addForm.address=this.address
          // 验证成功发（请求）
          addPInfo(this.addForm).then((res) => {
            // console.log(res)
            if(res.data.code === 200){
              this.addInfoShow = false
			  this.addForm={}
              this.$message.success(res.data.msg)
              this.getList()
              return
            }
            this.$message.success(res.data.msg) 
          })
           
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
      this.lat=this.changeForm.lat
      this.lng=this.changeForm.lng
	   this.address=this.changeForm.address
    },
    /**
     * 修改用户请求模块
     */
    changeClick(){     
          /**
           * 修改请求
           */
		  this.changeForm.lat=this.lat
		  this.changeForm.lng=this.lng
		  	this.changeForm.address=this.address
          upPInfo(this.changeForm).then((res) => {
            // console.log(res)
            if(res.data.code === 200){
              this.changeUserShow = false
              this.$message.success(res.data.msg)
              this.getList()
              return 
            }
            
          })
     
    },
	
	  handler ({BMap, map}) {
	         this.map = map
	         this.BMap = BMap
	         let _this = this
	         // 调用百度地图api 中的获取当前位置接口
	         geolocation.getCurrentPosition(function (r) {
	           let myGeo = new BMap.Geocoder()
	           myGeo.getLocation(new BMap.Point(r.point.lng, r.point.lat), function (result) {
	             if (result) {
	               _this.showMap({lng: result.point.lng, lat: result.point.lat})
	               _this.longitude = result.point.lng
	               _this.latitude = result.point.lat
	             }
	           })
	         })
	         //开启鼠标滚轮缩放
	         this.map.enableScrollWheelZoom(true)
	       },
	       clickMap(e) {
	         this.removeAllMarker(this.map)
	         const point = e.point
	         const marker = new BMap.Marker(point)
	         this.map.addOverlay(marker)
	         this.longitude = point.lng
	         this.latitude = point.lat
	       },
	       // 清除地图上的所有覆盖物
	       removeAllMarker(map) {
	         if (map) {
	           let overlays = map.getOverlays()
	           overlays.forEach((marker, index, arr) => {
	             map.removeOverlay(marker)
	           })
	         }
	       },
	       // 提交按钮点击事件
	       handleMapSubmit() {
	         // 给经纬度赋值
	         this.lat = this.latitude;
	         this.lng = this.longitude;
	         // 关闭经纬度选择对话框
	         this.mapDialogVisible = false
			 console.log(this.lat+':'+this.lng)
	       },
	       // 根据输入的关键词进行搜索
	       kwSearch(keyword, callback) {
	         let options = {
	           onSearchComplete: function(results) {
	             if (local1.getStatus() === 0) {
	               // 判断状态是否正确
	               var temp = []
	               for (let i = 0; i < results.getCurrentNumPois(); i++) {
	                 const x = results.getPoi(i)
	                 const item = { value: x.address + x.title, point: x.point }
	                 temp.push(item)
	                 callback(temp)
	               }
	             } else {
	               callback([])
	             }
	           }
	         }
	         var local1 = new this.BMap.LocalSearch(this.map, options)
	         local1.search(keyword)
	       },
	       // 选中搜索建议项
	       handleSelectAddress(item) {
	         let { point } = item
	         this.latitude = point.lat
	         this.longitude = point.lng
			
	         this.showMap({lng: point.lng, lat: point.lat})
	       },
	       // 在地图上画点
	       showMap (point) {
	         setTimeout(() => {
	           //移除地图覆盖物
	           this.map.centerAndZoom(point, 15)
	           this.map.clearOverlays()
	           if (point && point.lng && point.lat) {
	             let marker = new BMap.Marker(point)
	             this.map.addOverlay(marker)
	             this.center = point
	           }
	         }, 500)
	       },
	       
	       // 点击编辑时根据已有的经纬度信息在地图上画点
	       // 地图经纬度选择
	       handleMapSelect() {
	         this.mapDialogVisible = true;
	         this.address = '';
	         this.showMap({lng: this.rowFormTempData.lng, lat: this.rowFormTempData.lat})
	       },
	  
	
   
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
		    delPInfo(userInfo.id).then((res) => {
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