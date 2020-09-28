<template>
    <div class="m-header">
        <div class="menu-logo_wrap">
            <!--            <img src="../logo.svg" class="menu-logo"/>-->
            <div class="menu-title">
                <div class="menu-main_title">BI智能(Do Want You Do)</div>

            </div>
        </div>

        <div class="right-menu" ref="rightMenu">
            <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
                <div class="avatar-wrapper">
                    <img src="/img/avatar/avatar_24px_1102729_easyicon.net.png" class="user-avatar">
                    <i class="el-icon-caret-bottom" />
                </div>
                <el-dropdown-menu slot="dropdown">
                    <router-link to="/profile/index">
                        <el-dropdown-item>个人中心</el-dropdown-item>
                    </router-link>
                    <a target="_blank" href="#">
                        <el-dropdown-item>Github</el-dropdown-item>
                    </a>
                    <el-dropdown-item divided>
                        <span style="display:block;" @click="logout">退出登陆</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>

    import './header.styl'
    export default {
        name:'mheader',
        data(){
            return {
            }
        },
        props:{
            collapsed:Boolean
        },
        computed:{
            isCollapsed(){
                return this.collapsed
            }
        },
        methods:{
            logout(){
                this.$store.dispatch('logOut')
                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            },
            onCollapsed(){
                this.$emit('collapsed')
            }
        }
    }
</script>
<style>
    /**
*  品牌标识logo
**/
    .menu-logo_wrap {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 10px;
        height: 35px;
        color: white;
    }
    .menu-logo{
        height: 100%;
        width:34px;
    }
    .menu-title {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 31px;
        color: white;
        margin-left: 10px;
    }
    .menu-main_title{
        font-size: 15px;
        /*font-family:"youdu" !important;*/
        font-style:normal;
        font-weight: bold;
        -webkit-font-smoothing: antialiased;
        /*-webkit-text-stroke-width: 0.1px;*/
        -moz-osx-font-smoothing: grayscale;
    }
    .menu-sub_title{
        font-size: 8px;
        font-family:"youdu" !important;
    }


</style>

