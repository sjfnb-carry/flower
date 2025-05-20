	import {
		createRouter,
		createWebHashHistory
	} from 'vue-router'
	import news from '@/views/news/list'
	import discussshangpin from '@/views/discussshangpin/list'
	import shangpin from '@/views/shangpin/list'
	import address from '@/views/address/list'
	import xitonggonggao from '@/views/xitonggonggao/list'
    import menu_manage from '@/views/menu_manage/list'
	import storeup from '@/views/storeup/list'
	import users from '@/views/users/list'
	import shangjia from '@/views/shangjia/list'
	import chat from '@/views/chat/list'
	import yonghu from '@/views/yonghu/list'
	import shangpinfenlei from '@/views/shangpinfenlei/list'
	import orders from '@/views/orders/list'
	import config from '@/views/config/list'
	import usersCenter from '@/views/users/center'
	import shangjiaRegister from '@/views/shangjia/register'
	import shangjiaCenter from '@/views/shangjia/center'

export const routes = [{
		path: '/login',
		name: 'login',
		component: () => import('../views/login.vue')
	},{
		path: '/',
		name: '首页',
		component: () => import('../views/index'),
		children: [{
			path: '/',
			name: '首页',
			component: () => import('../views/HomeView.vue'),
			meta: {
				affix: true
			}
		}, {
			path: '/updatepassword',
			name: '修改密码',
			component: () => import('../views/updatepassword.vue')
		}
		
		,{
			path: '/usersCenter',
			name: '管理员个人中心',
			component: usersCenter
		}
		,{
			path: '/shangjiaCenter',
			name: '商家个人中心',
			component: shangjiaCenter
		}
		,{
			path: '/news',
			name: '营销与推广',
			component: news
		}
		,{
			path: '/discussshangpin',
			name: '商品评论-评论',
			component: discussshangpin
		}
		,{
			path: '/shangpin',
			name: '商品',
			component: shangpin
		}
		,{
			path: '/address',
			name: '地址',
			component: address
		}
		,{
			path: '/xitonggonggao',
			name: '系统公告',
			component: xitonggonggao
		}
        ,{
            path: '/menu',
            name: '菜单权限管理',
            component: menu_manage
        }
		,{
			path: '/storeup',
			name: '我的收藏',
			component: storeup
		}
		,{
			path: '/users',
			name: '管理员',
			component: users
		}
		,{
			path: '/shangjia',
			name: '商家',
			component: shangjia
		}
		,{
			path: '/chat',
			name: '售后服务',
			component: chat
		}
		,{
			path: '/yonghu',
			name: '用户',
			component: yonghu
		}
		,{
			path: '/shangpinfenlei',
			name: '商品分类',
			component: shangpinfenlei
		}
		,{
			path: '/orders',
			name: '订单管理',
			component: orders
		}
		,{
			path: '/config',
			name: '轮播图',
			component: config
		}
		]
	},
	{
		path: '/shangjiaRegister',
		name: '商家注册',
		component: shangjiaRegister
	},
]

const router = createRouter({
	history: createWebHashHistory(process.env.BASE_URL),
	routes
})

export default router
