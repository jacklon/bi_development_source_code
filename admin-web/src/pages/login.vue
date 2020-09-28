<template>

    <div class="login-container" :style="imageSrc=='none'? {backgroundImage:'none'}: {backgroundImage:'url('+require('../../public/img/background/'+imageSrc)+')'}"  >
<!--        <div class="login-container"    >-->

        <el-form ref="loginForm" :model="loginForm" :rules="loginRules"
                 class="login-form" auto-complete="on" >
            <div class="title-container">
                <svg-icon icon-class="eye" />
                <h3 class="title">BI登录</h3>
            </div>

            <el-form-item prop="username">

              <span class="svg-container svg-container_login">
                <svg-icon icon-class="user" />
              </span>
                <el-input v-model="loginForm.username" name="username" type="text" auto-complete="on" placeholder="用户名" />
            </el-form-item>

            <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
                <el-input :type="passwordType" v-model="loginForm.password" name="password" auto-complete="on"  placeholder="密码" @keyup.enter.native="handleLogin" />
           <span class="show-pwd" @click="showPwd">
            <svg-icon icon-class="eye" />
          </span>
            </el-form-item>

            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">登录</el-button>

        </el-form>

    </div>
    <!--  </div>-->
</template>
<script>
    import {listSysParas} from '@/api/config'
    import { getUserInfo } from '@/api/login'
    export default {
        name: 'Login',
        data() {
            const validateUsername = (rule, value, callback) => {
                if (validateUsername == null) {
                    callback(new Error('请输入正确的管理员用户名'))
                } else {
                    callback()
                }
            }
            const validatePassword = (rule, value, callback) => {
                if (value.length < 6) {
                    callback(new Error('管理员密码长度应大于等于6位'))
                } else  if (value.length > 20) {
                    callback(new Error('管理员密码长度应小于等于20位'))
                } {
                    callback()
                }
            }
            return {
                backgroundDiv: {
                    backgroundImage:'url(' + require('../../public/img/background/login-background.png') + ')',
                    backgroundRepeat:'no-repeat',
                    backgroundSize:'100% 100%'

                },

                // imageSrc: 'loginBackGround.jpg',
                imageSrc: 'none',
                loginForm: {
                    // username: '',
                    // password: ''
                    username: 'AdminSys',
                    password: '123456'
                },
                loginRules: {
                    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePassword }]
                },
                passwordType: 'password',
                loading: false
            }
        },
        watch: {
            $route: {
                handler: function(route) {
                    this.redirect = route.query && route.query.redirect
                },
                immediate: true
            }

        },
        created() {

            this.listSysParas();
            // window.addEventListener('hashchange', this.afterQRScan)
        },
        destroyed() {
            // window.removeEventListener('hashchange', this.afterQRScan)
        },
        methods: {
            listSysParas(){
                listSysParas().then(response => {
                    let config = response.data.data
                    if(config==null||config.sys_para_login_backgroud_image==null||config.sys_para_login_backgroud_image==''){
                        this.imageSrc='none'
                    } else
                    {
                        this.imageSrc=config.sys_para_login_backgroud_image
                    }
                })
            },
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
            },
            handleLogin() {
                // this.$router.push('/');
                this.$refs.loginForm.validate(valid => {
                    if (valid && !this.loading) {
                        this.loading = true
                        //这里要直接转到主页面，而不是上次别的用户登陆的页面
                        let redirectPage="/";
                        this.$store.dispatch('LoginByUsername', this.loginForm).then((userLogIn) => {
                            this.loading = false
                            this.$router.push({ path: redirectPage || '/' })
                            //获取用户信息
                            console.log(this.$store.getters.token)
                            getUserInfo().then((userInfo)=>{
                                    //获取MongoDbServer访问的Token
                                    // this.$axios.post('/auth/login',{username:this.$refs.loginForm.username,
                                    //     password:this.$refs.loginForm.password,userId:userInfo.data.data.userId}).then(res => {
                                    //     this.$mUtils.setLocalStorage('token', 'Bearer ' + res.body.token)
                                    // })
                                    this.$axios.post('/auth/login',{username:'lizs',
                                        password:'lizs'}).then(res => {
                                        this.$mUtils.setLocalStorage('token', 'Bearer ' + res.body.token)
                                    })
                            }

                            )

                            // sessionStorage.setItem()
                        }).catch(response => {
                            this.$message.error( '失败:'+response.data.errmsg);
                            this.loading = false
                        })
                    } else {
                        return false
                    }
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" >
    $bg:#2d3a4b;
    $light_gray:#eee;

    /* reset element-ui css */
    .login-container {
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;
            input {
                background: transparent;
                border: 0px;
                -webkit-appearance: none;
                border-radius: 0px;
                padding: 12px 5px 12px 15px;
                color: $light_gray;
                height: 47px;
                &:-webkit-autofill {
                    box-shadow: 0 0 0px 1000px $bg inset !important;
                    -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
                    -webkit-text-fill-color: #fff !important;
                }
            }
        }
        .el-form-item {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
        }
    }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
    $bg:#2d3a4b;
    $dark_gray:#889aa4;
    $light_gray:#eee;

    .login-container {
        position: fixed;
        height: 100%;
        width: 100%;
        background-color: $bg;
        background-image: url("../../public/img/background/login-background.png");
        background-position: center center;
        background-size: cover;
        .login-form {
            position: absolute;
            /*left: 0;*/
            right: 0;
            width: 520px;
            padding: 35px 35px 15px 35px;
            margin: 120px auto;
        }
        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            span {
                &:first-of-type {
                    margin-right: 16px;
                }
            }
        }
        .svg-container {
            padding: 6px 5px 6px 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 50px;
            display: inline-block;
            &_login {
                font-size: 20px;
            }
        }
        .title-container {
            position: relative;
            .title {
                font-size: 26px;
                font-weight: 400;
                color: $light_gray;
                margin: 0px auto 40px auto;
                text-align: center;
                font-weight: bold;
            }
        }
        .show-pwd {
            position: absolute;
            width: 30px;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
    }
</style>
