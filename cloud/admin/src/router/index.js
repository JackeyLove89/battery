import Vue from 'vue'
import VueRouter from 'vue-router'

/**
 * 重写路由的push方法、replace方法,防止报错
 */
const routerPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error=> error)
}
const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err);
};

const Login = () => import('views/login/Login')
const Reg = () => import('../views/reg/Reg.vue')
const Home = () => import('views/home/Home')

const Welcome = () => import('views/home/children/Welcome')
const UserList = () => import('views/home/children/users/UserList')
const AdminList = () => import('views/home/children/users/AdminList')
const NoticeList = () => import('views/home/children/notice/NoticeList')
const AdviceList = () => import('views/home/children/advice/AdviceList')

const Statistics = () => import('views/home/children/statistics/Statistics')
const ShopStatistics = () => import('views/home/children/statistics/ShopStatistics')

const CityList = () => import('views/home/children/dic/CityList')
const GoodsList = () => import('views/home/children/shop/GoodsList')

const ParkList = () => import('views/home/children/dic/ParkList')
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login,
	
  },
  {
    path: '/reg',
    component: Reg,	
  },
  {
    path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      {
        path: '/welcome',
        component: Welcome
      },
      {
        path: '/users',
        component: UserList
      },
	  {
	    path: '/admins',
	    component: AdminList
	  },
      {
        path: '/notice',
        component: NoticeList
      },
      {
        path: '/notice/advice',
        component: AdviceList
      },
     
	  {
	    path: '/statistics',
	    component: Statistics
	  },

	  {
	    path: '/city',
	    component: CityList
	  },
    {
	    path: '/goods',
	    component: GoodsList
	  },
    
	  {
	      path: '/park',
	      component: ParkList
	    },
    {
	    path: '/shopstatistics',
	    component: ShopStatistics
	  },
    ]
  }
]
const router = new VueRouter({
    routes
})

// 是否已经登录  导航守卫
// router.beforeEach((to, from, next) => {
//   if(to.path === '/login'){
//     return next()
//   }
//   // 获取到token (有无token判断，是否登录)
//   const tokenStr = window.sessionStorage.getItem('token')
//   if(!tokenStr){
//     return next('/login')
//   }else{
//     return next()
//   }
// })

export default router
