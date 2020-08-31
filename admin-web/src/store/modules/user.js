import { loginByUsername, logout, getUserInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import fa from "element-ui/src/locale/lang/fa";

const user = {
	state: {
		code: '',
		token: getToken(),
		name: '',
		avatar: '',
		roles: [],
		menus: [],
		deptId:'',
		deptIdString:'',
		deptName:'',
		ifAdminRole:false
	},


	mutations: {
		SET_CODE: (state, code) => {
			state.code = code
		},
		SET_TOKEN: (state, token) => {
			state.token = token
		},
		SET_NAME: (state, name) => {
			state.name = name
		},
		SET_AVATAR: (state, avatar) => {
			state.avatar = avatar
		},
		SET_ROLES: (state, roles) => {
			state.roles = roles
		},
		SET_MENUS: (state, menus) => {
			state.menus = menus
		},
		SET_DEPTID: (state, deptId) => {
			state.deptId = deptId
		},
		SET_DEPTIDSTRING: (state, deptIdString) => {
			state.deptIdString = deptIdString
		},
		SET_DEPTNAME: (state, deptName) => {
			state.deptName = deptName
		},
		SET_IFADMINROLE: (state, ifAdminRole) => {
			state.ifAdminRole = ifAdminRole
		},
	},

	actions: {
		// 用户名登录
		LoginByUsername({ commit }, userInfo) {
			const username = userInfo.username.trim()
			return new Promise((resolve, reject) => {
				loginByUsername(username, userInfo.password).then(response => {
					const token = response.data.data
					commit('SET_TOKEN', token)
					setToken(token)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},

		// 获取用户信息
		GetUserInfo({ commit, state }) {
			return new Promise((resolve, reject) => {
				getUserInfo(state.token).then(response => {
					const data = response.data.data
					commit('SET_CODE', data.userId)
					commit('SET_ROLES', data.roles)
					commit('SET_NAME', data.name)
					commit('SET_AVATAR', data.avatar)
					commit('SET_MENUS', data.menus)
					commit('SET_DEPTID', data.deptId)
					commit('SET_DEPTIDSTRING', data.deptIdString)
					commit('SET_DEPTNAME', data.deptName)
					commit('SET_IFADMINROLE', data.ifAdminRole)
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},


		// 登出
		logOut({ commit, state }) {
			return new Promise((resolve, reject) => {
				logout(state.token).then(() => {
					commit('SET_CODE', '')
					commit('SET_NAME', '')
					commit('SET_TOKEN', '')
					commit('SET_ROLES', [])
					commit('SET_MENUS', [])
					commit('SET_DEPTID', '')
					commit('SET_IFADMINROLE', false)
					removeToken()
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},

		// 前端 登出
		FedLogOut({ commit }) {
			return new Promise(resolve => {
				commit('SET_TOKEN', '')
				removeToken()
				resolve()
			})
		},

		// 动态修改权限
		ChangeRoles({ commit, dispatch }, role) {
			return new Promise(resolve => {
				commit('SET_TOKEN', role)
				setToken(role)
				getUserInfo(role).then(response => {
					const data = response.data
					commit('SET_ROLES', data.roles)
					commit('SET_MENUS', data.menus)
					commit('SET_NAME', data.name)
					commit('SET_AVATAR', data.avatar)
					dispatch('GenerateRoutes', data) // 动态修改权限后 重绘侧边菜单
					resolve()
				})
			})
		}
	}
}

export default user
