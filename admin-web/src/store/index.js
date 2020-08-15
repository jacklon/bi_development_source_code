import Vue from 'vue'
import Vuex from 'vuex'

// 各个模块
import user from './modules/user'
import scale from './modules/scale'
import powereditor from './modules/powereditor'
import image from './modules/image'
import getters from './getters'

import { Message } from 'element-ui'
import tagsView from './modules/tagsView'
/**
 * 全局状态管理
 */
const state = {

};
const actions = {

	/**
	 * 显示提示 msg.type 类型  msg.data 消息内容
	 * @param commit
	 * @param msg
	 */
	showMassage(store, msg) {
		console.log(msg)
		Message({
			type: msg.type,
			message: msg.message || msg.data
		})
	},
};
const mutations = {};


Vue.use(Vuex);
export default new Vuex.Store({
	state,
	getters,
	actions,
	mutations,
	modules: {
		user,
		scale,
		powereditor,
		image,
		app,
		tagsView,
	}
});

