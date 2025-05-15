import { request } from './request'

//获取用户列表数据
export function getUsers(data){
  return request({
    url: '/user/queryPage',
	data: data,
     method: 'post',
	 transformRequest: [function (data) {  // 将{username:111,password:111} 转成 username=111&password=111
	                     var ret = '';
	                     for (var it in data) {
	                         // 如果要发送中文 编码
	                         ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
	                     }
	                     return ret.substring(0,ret.length-1)
	                 }]
  })
}

//添加用户
export function addUserInfo(userInfo){
  return request({
    url: '/user/register',
    method: 'post',
    data: userInfo,
   
  })
}

// 根据id查询用户信息
export function queryIdUser(id){
  return request({
    url: '/user/getUserInfo' ,
	method: 'post',
    data: {id},
 	transformRequest: [function (data) {  // 将{username:111,password:111} 转成 username=111&password=111
 		                    var ret = '';
 		                    for (var it in data) {
 		                        // 如果要发送中文 编码
 		                        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
 		                    }
 		                    return ret.substring(0,ret.length-1)
 		                }]
 	})
}



// 修改用户信息
export function changeUser(userInfo){
  return request({
    url: '/user/upInfo' ,
    method: 'post',
    data: userInfo,	
	
 }) 
}

//删除用户
export function delUsers(id){
  return request({
    url: '/user/delUser',
    method: 'post',
	data:{id},
	transformRequest: [function (data) {  // 将{username:111,password:111} 转成 username=111&password=111
		                    var ret = '';
		                    for (var it in data) {
		                        // 如果要发送中文 编码
		                        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
		                    }
		                    return ret.substring(0,ret.length-1)
		                }]
	
  })
}


// 系统统计
export function getTj(){
	return request({
	  url: '/user/getTj' ,
	  method: 'post',
	 
	  
   }) 
  }


export function getShopTj(data){
  return request({
    url: '/api/shop/tj',
	data: data,
     method: 'post',
	 transformRequest: [function (data) {  // 将{username:111,password:111} 转成 username=111&password=111
	                     var ret = '';
	                     for (var it in data) {
	                         // 如果要发送中文 编码
	                         ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
	                     }
	                     return ret.substring(0,ret.length-1)
	                 }]
  })
}
  
  