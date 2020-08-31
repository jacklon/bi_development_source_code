import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const asyncRouterMap = [
	{
		path: '/login',
		name: 'Login',
		// component: () => import('@/pages/loginfor'),
		component: () => import('@/pages/login'),
		meta: {
			hideHeader: true,
			trust: true,
			noNeedLogin: true
		}
	},

	{
		path: '/',
		name: 'home',
		component: () => import('@/pages/home/index'),
		redirect: { name: 'threejsCreate' },
		children: [{
			path: '/project-create',
			name: 'projectCreate',
			component: () => import('@/pages/home/project-create'),
		}, {
			path: '/data-create',
			name: 'dataCreate',
			component: () => import('@/pages/home/data-create'),
		}, {
			path: '/pic-create',
			name: 'picCreate',
			component: () => import('@/pages/home/pic-create'),
		}, {
			path: '/threejs-create',
			name: 'threejsCreate',
			component: () => import('@/pages/home/threejs-create'),
		},
		{
			path: '/blank',
			name: 'blank',
			component: () => import('@/pages/home/blank'),
		},
		// {
		// 	path: 'treedic',
		// 	component: () => import('@/pages/sys/treedic'),
		// 	name: 'treedic',
		// 	meta: {
		// 		title: '树形代码',
		// 		noCache: true
		// 	}
		// },
		// {
		// 	path: 'dicmain',
		// 	component: () => import('@/pages/sys/dicmain'),
		// 	name: 'dicmain',
		// 	meta: {
		// 		title: '系统代码',
		// 		noCache: true
		// 	}
		// },
		//
		//
		// {
		// 	path: 'config/sysParas',
		// 	component: () => import('@/pages/sys/config/sysParas'),
		// 	name: 'sysParas',
		// 	meta: {
		// 		title: '系统参数',
		// 		noCache: true
		// 	}
		// },
		//
		// {
		// 	path: 'opadmindef',
		// 	component: () => import('@/pages/sys/info/opadmindef'),
		// 	name: 'opadmindef',
		// 	meta: {
		// 		title: '消息定义',
		// 		noCache: true
		// 	}
		// },
		// {
		// 	path: 'opadmininfo',
		// 	component: () => import('@/pages/sys/info/opadmininfo'),
		// 	name: 'opadmininfo',
		// 	meta: {
		// 		title: '消息列表',
		// 		noCache: true
		// 	}
		// },
		//
		{
			path: '/menuSet',
			component: () => import('@/pages/sys/sysManage/menuSet'),
			name: 'menuSet',
			meta: {
				title: '菜单管理',
				noCache: true
			}
		},
		{
			path: '/bcorpinfo',
			component: () => import('@/pages/sys/sysManage/bcorpinfo'),
			name: 'bcorpinfo',
			meta: {
				title: '组织管理',
				noCache: true
			}
		},
		{
			path: '/role',
			component: () => import('@/pages/sys/sysManage/role'),
			name: 'role',
			meta: {
				title: '角色管理',
				noCache: true
			}
		},
		{
			path: '/user',
			component: () => import('@/pages/sys/sysManage/admin'),
			name: 'user',
			meta: {
				title: '人员管理',
				noCache: true
			}
		},
		// {
		// 	path: 'bcorpinfo',
		// 	component: () => import('@/pages/sys/sysManage/bcorpinfo'),
		// 	name: 'bcorpinfo',
		// 	meta: {
		// 		title: '组织机构',
		// 		noCache: true
		// 	}
		// },
		// {
		// 	path: 'role',
		// 	component: () => import('@/pages/sys/sysManage/role'),
		// 	name: 'role',
		// 	meta: {
		// 		title: '角色管理',
		// 		noCache: true
		// 	}
		// },
		// {
		// 	path: 'admin',
		// 	component: () => import('@/pages/sys/sysManage/admin'),
		// 	name: 'admin',
		// 	meta: {
		// 		title: '操作员',
		// 		noCache: true
		// 	}
		// },
		// {
		// 	path: 'log',
		// 	component: () => import('@/pages/sys/sysManage/log'),
		// 	name: 'log',
		// 	meta: {
		// 		title: '操作日志',
		// 		noCache: true
		// 	}
		// },

		]
	},
	{
		path: '/power-editor',
		name: 'PowerEditor',
		component: () => import('@/pages/power-editor/power-editor')
	},

	{
		path: '/template-create',
		name: 'templateCreate',
		component: () => import('@/pages/home/template-create'),
	},
	{
		path: '/css-create',
		name: 'cssCreate',
		component: () => import('@/pages/css/interview'),
		meta: {
			trust: true,
			noNeedLogin: true
		}
	},
	{
		path: '/js-create',
		name: 'jsCreate',
		component: () => import('@/pages/js/interview'),
		meta: {
			trust: true,
			noNeedLogin: true
		}
	},


]

export default new Router({
	routes:asyncRouterMap})

