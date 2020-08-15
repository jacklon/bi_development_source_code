const Menu=require('../models/menu')
const router=require('koa-router')()
const mongoose=require('mongoose')
/**
 *  获取菜单列表
 */
router.get('/info',async ctx=>{
    let result=await Menu.find({})
    // let result=await Menu.find({},null,{sort: [['id', -1]]}, {});
        // .find({name:'admin'}, null, {sort: [['_id', -1]]}, callback);
    //把平行数据转成树形数据
    let mainMenu = []
    let subMenu = []
    mainMenu = result.filter(function(t){return t.pid === 0})
    subMenu = result.filter(function(t){return t.pid !== 0})
    let menuList = []
    for (let index = 0; index < mainMenu.length; index++) {
        const mainItem = mainMenu[index];
        menuList.push({
            id:mainItem.id,
            pid:mainItem.pid,
            name:mainItem.name,
            iconClass:mainItem.iconClass,
            routerPath:mainItem.routerPath,
            sort:mainItem.sort,
            title:mainItem.title,
            children:[]
        })
        for (let findex = 0; findex < subMenu.length; findex++) {
            const subItem = subMenu[findex];
            if(subItem.pid === mainItem.id){
                menuList[index].children.push(subItem)
            }
        }
    }
    ctx.body= menuList
})
module.exports=router
