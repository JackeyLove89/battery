import { request } from './request'

//获取列表数
export function getList(data){
  return request({
    url: '/video/queryPage',
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
    url: '/video/addInfo',
    method: 'post',
    data: data,
   
  })
}

// 根据id查询
export function getInfo(id){
  return request({
    url: '/video/getInfo' ,
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
    url: '/video/upInfo' ,
    method: 'post',
    data: data,	
	
 }) 
}
export function postUpload (file) {
	let formData = new FormData()
  	formData.append('file', file)
    return request({
        url: '/api/upload',
        method: 'post',
        data: formData,
        // headers: {
        //     'Content-Type': 'multipart/form-data'
        // }
    })
}
//删除
export function delInfo(id){
  return request({
    url: '/video/delInfo',
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


