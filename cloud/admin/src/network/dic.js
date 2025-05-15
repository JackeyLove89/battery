import { request } from './request'

//获取列表数据
export function getPList(data){
  return request({
    url: '/api/app/queryPage',
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



//添加
export function addPInfo(data){
  return request({
    url: '/api/app/addInfo',
    method: 'post',
    data: data,
   
  })
}

// 根据id查询
export function getPInfo(id){
  return request({
    url: '/api/app/getInfo' ,
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



// 修改
export function upPInfo(data){
  return request({
    url: '/api/app/upInfo' ,
    method: 'post',
    data: data,	
	
 }) 
}

//删除
export function delPInfo(id){
  return request({
    url: '/api/app/delInfo',
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
//获取列表数据
export function getDList(data){
  return request({
    url: '/api/dc/queryPage',
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



//添加
export function addDInfo(data){
  return request({
    url: '/api/orders/addInfo',
    method: 'post',
    data: data,
   
  })
}

// 根据id查询
export function getDInfo(id){
  return request({
    url: '/api/orders/getInfo' ,
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



// 修改
export function upDInfo(data){
  return request({
    url: '/api/orders/upInfo' ,
    method: 'post',
    data: data,	
	
 }) 
}

//删除
export function delDInfo(id){
  return request({
    url: '/api/orders/delInfo',
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

//获取列表数据
export function getList(data){
  return request({
    url: '/api/bx/queryPage',
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



//添加
export function addInfo(data){
  return request({
    url: '/api/bx/addInfo',
    method: 'post',
    data: data,
   
  })
}

// 根据id查询
export function getInfo(id){
  return request({
    url: '/api/bx/getInfo' ,
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



// 修改
export function upInfo(data){
  return request({
    url: '/api/bx/upInfo' ,
    method: 'post',
    data: data,	
	
 }) 
}

//删除
export function delInfo(id){
  return request({
    url: '/api/bx/delInfo',
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



//获取
export function getLList(data){
	return request({
	  url: '/api/line/queryPage',
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
  export function addLInfo(data){
	return request({
	  url: '/api/line/addInfo',
	  method: 'post',
	  data: data,
	 
	})
  }

  
  
  
  // 修改用户信息
  export function upLInfo(data){
	return request({
	  url: '/api/line/upInfo' ,
	  method: 'post',
	  data: data,	
	  
   }) 
  }
  
  //删除用户
  export function delLInfo(id){
	return request({
	  url: '/api/line/delInfo',
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
  
  //获取
  export function getLrList(data){
  	return request({
  	  url: '/api/lr/queryPage',
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
    export function addLrInfo(data){
  	return request({
  	  url: '/api/lr/addInfo',
  	  method: 'post',
  	  data: data,
  	 
  	})
    }
  
    
    
    
    // 修改用户信息
    export function upLrInfo(data){
  	return request({
  	  url: '/api/lr/upInfo' ,
  	  method: 'post',
  	  data: data,	
  	  
     }) 
    }
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
    //删除用户
    export function delLrInfo(id){
  	return request({
  	  url: '/api/lr/delInfo',
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
	//获取
	export function getRList(data){
		return request({
		  url: '/api/record/queryPage',
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