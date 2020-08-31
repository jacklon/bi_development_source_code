
import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress Configuration

// permission judge function
function hasPermission(perms, permissions) {
    if (perms.indexOf('*') >= 0) return true // admin permission passed directly
    if (!permissions) return true
    return perms.some(perm => permissions.indexOf(perm) >= 0)
}

router.beforeEach((to, from, next) => {
    NProgress.start()// start progress bar
    if(to.name=='Login'){
        next()
        return false;
    }
    let token = getToken();

    if ((token==''||token==null)||(token!=null&&(store.getters.code==null||store.getters.code== ''))) { // determine if there has token
        /* has token*/
        console.log(store.getters)
        if (store.getters.code==null||store.getters.code== '') { // 判断当前用户是否已经登陆并获取到了用户信息
            store.dispatch('GetUserInfo').then(res => { // 拉取user_info
                const menus = res.data.data.menus
                store.dispatch('GenerateRoutes', { menus }).then(() => { // 根据perms权限生成可访问的路由表
                    router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                    // next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                    next()
                })
            }).catch((err) => {
                store.dispatch('FedLogOut').then(() => {
                    Message.error(err || 'Verification failed, please login again')
                    next({ path: '/login' })
                    next()
                })
            })
        } else {
            next()
        }
    } else {
        next()
        // return false;
    }
})

router.afterEach(() => {
    NProgress.done() // finish progress bar
})


