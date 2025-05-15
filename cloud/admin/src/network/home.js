import { request } from './request'

//获取侧边栏数据
export function getMenus(){
  return request({
    url: '/menus'
  })
}


