<template>
    <div style="display: flex;height: 100%">
        <div class="page menu" :class="{'menu-list_collapsed' : isCollapsed }" v-loading="loading">
            <div class="menu-list_wrap">
                <!-- 展开和隐藏按钮 -->
                <div class="narrow-wrapper" @click="changeUnfoldStatus(1)" v-if="!isCollapsed">
                    <img src='./menu-left.svg' />
                </div>
                <div class="narrow-wrapper" @click="changeUnfoldStatus(0)" v-else>
                    <img src='./menu-right.svg' />
                </div>
                <!-- 展开和隐藏按钮 END -->
                <ul class="menu-list_main_wrap">
                    <li class="menu-list_main" v-for="item in menuList" :title="item.name" :key="item.id"  @click="onMenuClick(item)" >
                        <router-link :to="{path: item.path}" class="menu-list_link" >
                            <div class="menu-list_item"  :class="{'menu-list_item_active': item.id === activeIndex}"   :data-index="item.id">
                                <el-tooltip placement="right-start" v-if="isCollapsed&&item.children.length>0"  :visible-arrow="false" popper-class="sub-menu">
                                    <div slot="content">
                                        <ul class="menu-list-subitem_collapse">
                                            <li class="menu-list-sub"
                                                v-for="subitem in item.children"
                                                :key="subitem.id"
                                                :data-index="subitem.id"
                                                :class="{'menu-list_item_active': subitem.id === activeSubIndex}"
                                                @click="onSubMenuClick(subitem.id)">
                                                <span><i class={subitem.icon}></i></span>{{subitem.name}}
                                            </li>
                                        </ul>
                                    </div>
                                    <i class="el-icon-s-platform el-icon-lefts" />
                                </el-tooltip>
                                <i class="el-icon-lefts" :class="item.icon" v-else/>
                                <div class="menu-list_item_title">{{item.name}}</div>

                                <i v-if="item.children!=null&&item.children.length>0&&item.firstMenuOpen==false" class="el-icon-arrow-down el-icon-rights" style="margin-right: 3px"></i>
                                <i v-if="item.children!=null&&item.children.length>0&&item.firstMenuOpen==true" class="el-icon-arrow-up el-icon-rights" style="margin-right: 3px"></i>
                            </div>
                        </router-link>
                        <ul class="menu-list_sub_wrap" v-show="item.id === activeIndex">
                            <li class="menu-list-sub"
                                v-for="subitem in subMenuList"
                                :key="subitem.id"
                                :data-index="subitem.id"
                                @click.stop="onSubMenuClick(subitem)">
                                <router-link :to="{path: subitem.path}" class="menu-list_link">
                                    <div class="menu-list-subitem" :class="{'menu-list_item_subactive': subitem.id === activeSubIndex}" >
                                        <div class="menu-list_item_subtitle">
                                            <span style="margin-right: 2px">
                                            <i :class="subitem.icon==null?'':subitem.icon" ></i>
                                            </span>{{subitem.name}}
                                        </div>
                                        <div>
                                            <i  v-if="subitem.children!=null&&subitem.children.length>0" class="el-icon-arrow-right el-icon-rights"></i>
                                        </div>

                                    </div>
                                </router-link>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

        </div>
        <div class="sub-bar" v-if="thirdSubMenuList!=null&&thirdSubMenuList.length>0">

            <div class="sub-header-title">{{submenuTitle}}</div>
            <div class="sub-menu-list">
                <ul class="menu-list_sub_wrap" >
                    <li class="menu-list-sub"
                        v-for="(thirdItem,index) in thirdSubMenuList"
                        :key="index"
                        :data-index="thirdItem.id"
                        @click="onThirdMenuClick(thirdItem)">
                        <router-link :to="thirdItem.path" :key="index" class="menu-list_link">

                            <div class="sub-menu-item" :class="{'sub-menu-item-active': thirdItem.id === activeThirdIndex}" >
                                <div class="menu-list_item_subtitle">
                                    <span style="margin-right: 2px">
                                    <i :class="thirdItem.icon==null?'':thirdItem.icon" ></i>
                                    </span>{{thirdItem.name}}
                                </div>

                            </div>

                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </div>





</template>

<script>
export default {
    data(){
        return {
            isCollapsed: false, //判断展开还是隐藏一级菜单,
            unfoldItemMenuIndex: 0, //判断展开还是隐藏一个菜单列表

            isShowMainMenu: false,
            loading:false,
            activeIndex: 1,


            activeSubIndex: -1,
            activeThirdIndex: -1,
            menuList:[],
            hideSubMenu:false,
            submenuTitle:'',
            subMenuList:[],
            thirdSubMenuList:[]
        }
    },
    props:{
        collapsed:Boolean
    },
    computed:{
        // isCollapsed(){
        //     return this.collapsed
        // }
    },
    mounted(){
        // this.loading = true;
        this.menuList=this.$store.getters.menus;
        this.menuList.forEach(item=>{
            item["firstMenuOpen"]=false;
        })
        // this.$axios.get('/menus/info').then(res => {
        //     this.loading = false;
        //     if (res.body) {
        //         this.menuList = res.body
        //     }
        // }).catch(() => {
        //     this.loading = false;
        // })
    },
    methods:{
        changeUnfoldStatus(code) {
            if (code == 1) {
                this.isCollapsed = true;
                sessionStorage.setItem("isCollapsed", "1");
            } else {
                this.isCollapsed = false;
                sessionStorage.setItem("isCollapsed", "0");
            }
        },
        onCollapsed(){
            this.$emit('collapsed')
        },
        onMenuClick(item){
            //清空第三级菜单
            this.activeThirdIndex=-1
            this.thirdSubMenuList=[]
            //将当前点击菜单赋给一级菜单Index
            if(this.activeIndex !== item.id){
                this.activeIndex = item.id
            }
            //判断一级菜单是否已经展开
            if(item.children!=null&&item.children.length>0){
                //如果当前菜单没有展开，则向二级菜单赋值展开
                if(item.firstMenuOpen==false){
                    this.subMenuList=item.children
                    //将不等于当前菜单的其它菜单展开标记设置成false
                    this.menuList.forEach(firstLevel=>{
                        if(firstLevel.id!=item.id){
                            firstLevel["firstMenuOpen"]=false;
                        }
                    })
                } else
                {
                    //设置当前子菜单为空
                    this.subMenuList=[]
                }
            }
            item.firstMenuOpen=!item.firstMenuOpen
        },
        onSubMenuClick(subMenu){
            this.activeThirdIndex=-1
            if(this.activeSubIndex !== subMenu.id){
                this.activeSubIndex = subMenu.id
            }
            if(subMenu.children!=null&&subMenu.children.length>0){
                this.submenuTitle=subMenu.name;
                this.thirdSubMenuList=[...subMenu.children]

                //通过push进行跳转
                this.$router.push('/blank')
            } else
            {
                this.activeThirdIndex=-1
                this.thirdSubMenuList=[]

            }

        },
        onThirdMenuClick(thirdMenu){
            if(this.activeThirdIndex !== thirdMenu.id){
                this.activeThirdIndex = thirdMenu.id
            }


        }
    }
}
</script>

<style lang="stylus" scoped>
    /**
    *   定义颜色过渡
    **/
    @keyframes hoverColor {
        from {color:rgb(121,121,136)}
        to {color:rgb(255,255,255)}
    }
    /**
    *   定义形状过渡 宽度由大变小
    **/
    @keyframes decreaseWidth {
        from {width:217px}
        to {width:60px}
    }
    /**
    *   定义形状过渡 宽度由小变大
    **/
    @keyframes growthWidth {
        from {width:60px}
        to {width:217px}
    }
    .menu{
        width:140px;

        /**
        *  菜单栏
        **/
        .menu-list_wrap{

            .menu-list_main_wrap{
                .menu-list_main{
                    display:flex;
                    flex-direction: column;
                    align-items:center;
                    .menu-list_link{
                        width: 100%;
                        display: block;
                    }
                    .menu-list_item{
                        display:flex;
                        flex-direction: row;
                        align-items:center;
                        width:100%;
                        height:50px;
                        line-height: 50px;
                        color: rgb(166,166,181);
                        cursor: pointer;
                        .el-icon-lefts{
                            font-size:16px;
                            margin-left:20px;
                            margin-right:5px;
                            color: rgb(121,121,136);
                        }
                        .el-icon-rights{
                            font-size:10px;
                            font-weight:800;
                            margin-right:15px;
                            color: rgb(121,121,136);
                        }
                        .menu-list_item_title{
                            width:100%;
                            font-size:14px;
                        }
                        &:hover{
                            .menu-list_item_title{
                                color: white;
                                animation: hoverColor 0.5s;
                            }
                            .el-icon-arrow-down,.el-icon-s-platform{
                                color: white;
                            }
                        }
                        &.menu-list_item_active{
                            .el-icon-lefts,.el-icon-rights,.menu-list_item_title{
                                color: white;
                            }
                        }
                        &.menu-list_item_active:hover{
                            .menu-list_item_title{
                                color: white;
                                animation: none;
                            }
                            .el-icon-lefts,.el-icon-rights,{
                                color: white;
                            }
                        }
                    }
                    .menu-list_sub_wrap{
                        width:100%;
                        text-align:center;
                        .menu-list-sub{
                            text-align:center;
                            .menu-list-subitem{
                                display:flex;
                                flex-direction: row;
                                align-items:center;
                                justify-content:space-between;
                                width:100%;
                                height:50px;
                                line-height: 50px;
                                color: rgb(166,166,181);
                                cursor: pointer;
                                background:rgb(20,20,51);
                                .menu-list_item_subtitle{
                                    font-size: 14px;
                                    padding-left: 48px;
                                }
                                &:hover{
                                    color: white;
                                    animation: hoverColor 0.5s;
                                }
                                &.menu-list_item_subactive{
                                    background: rgb(15,189,160);
                                    color:white;
                                }
                                &.menu-list_item_subactive:hover{
                                    color: white;
                                    animation: none;
                                }
                            }
                        }
                    }
                }

            }
        }
        /**
        *  折叠菜单栏
        **/
        &.menu-list_collapsed{
            animation: decreaseWidth 0.5s;
            width:60px;
            .menu-list_item_title,.el-icon-arrow-down,.menu-list_sub_wrap,.menu-title{
                display:none;
            }
            .el-icon-lefts{
                padding-right:15px;
            }
        }
    }
    .menu-list-subitem_collapse{
        .menu-list-sub{
            display:flex;
            flex-direction: column;
            align-items:flex-start;
            width:150px;
            line-height: 40px;
            color: rgb(166,166,181);
            cursor: pointer;
            font-size: 14px;
            text-overflow: clip;
            padding-left:20px;
            &:hover{
                color: white;
                animation: hoverColor 0.5s;
            }
            &.menu-list_item_active{
                background: rgb(15,189,160);
                color:white;
            }
            &.menu-list_item_active:hover{
                color: white;
                animation: none;
            }
        }
    }
    .sub-bar {
        overflow:hidden;
        width: 150px;
        height: 100%;
        transition: width 0.2s;
        background: #0fbda0;
        position: relative;
        .sub-fold-wrapper {
            width: 20px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            background: #f7f7f7;
            cursor: pointer;
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            z-index: 2;
            margin: auto 0;
            .sub-fold-icon {
                font-size: 16px;
                color: #546478;
            }
        }
        .sub-unfold-wrapper {
            width: 20px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            background: #d9dee4;
            cursor: pointer;
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            z-index: 2;
            margin: auto 0;
            .sub-unfold-icon {
                font-size: 16px;
                color: #546478;
            }
        }
        .sub-header-title {
            display:flex;
            justify-content:center;
            line-height: 30px;
            background: #0fbda0;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-weight: 600;
            font-size: 12px;
            /*text-indent: 20px;*/
            box-sizing: border-box;
        }
        .sub-menu-list {
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            box-sizing: border-box;
            overflow-y: auto;
            text-align: center;
            .sub-menu-item {
                display: block;
                height: 40px;
                line-height: 40px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                /*text-indent: 20px;*/
                color:#fff;
                font-size: 14px;
            }
            .sub-menu-item:hover {
                background: #1f83ff;
                /*background: #2c3e50;*/
            }


            .sub-menu-item-active  {
                /*background: #fff;*/
                color:#fff;
                background: #2c3e50;
            }
        }
    }

    .narrow-wrapper {
        width: 100%;
        height: 30px;
        background: #4a5064;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        .narrow-icon {
            font-size: 12px;
            color: #aeafa7;
        }
    }
    .narrow-wrapper:hover .narrow-icon {
        color: #fff;
    }

    .m-logo_wrapper {
        width 65px
        height 50px
        display flex
        justify-content: center
        align-items: center
        cursor pointer
        font-size 20px
        color rgb(89, 89, 89)
        background: #4a5064;
        &:hover {
            background: rgb(230, 242, 255)
        }
    }
</style>
