import { request } from './request'

//登录
export function login(data){
  return request({
    url: '/user/login',
	data: data,
     method: 'post',
	})
}

//登录
export function register(data){
  return request({
    url: '/user/register',
	data: data,
     method: 'post'
	})
}
//登录
export function findPwd(data){
  return request({
    url: '/user/findPwd',
	data: data,
     method: 'post',
	})
}
