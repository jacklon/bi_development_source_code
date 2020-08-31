<template>
  <div class="app-container">
    <el-form ref="dataForm"  :model="config" status-icon label-width="300px">
      <h3>登陆设置</h3>
      <el-form-item label="登陆背景图" prop="litemall_order_service_yunque">
        <el-input v-model="config.sys_para_login_backgroud_image" class="input-width">
<!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">图片保存在/static下</span>
      </el-form-item>
      <el-form-item label="Logo" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_main_form_logo" class="input-width">
<!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">系统首页左上角的系统Logo</span>
      </el-form-item>
      <el-form-item label="发起流程图标" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_flow_start_logo" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">图片保存在/static/icons下</span>
      </el-form-item>
      <el-form-item label="系统显示名称" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_main_form_app_name" class="input-width">
          <template slot="append"></template>
        </el-input>
        <span class="info">系统首页左上角的系统名称</span>
      </el-form-item>
      <el-form-item   >
        <el-button-group>
          <el-button type="primary" style="margin-right: 5px"
                     @click="saveBlankSet">空白设置</el-button>
          <el-button type="primary" style="margin-right: 5px"
                     @click="saveRongDeJi">荣德基设置</el-button>
          <el-button type="primary" style="margin-right: 5px"
                     @click="saveYiHua">益华编务设置</el-button>
        </el-button-group>

      </el-form-item>

      <h3>阿里短信设置</h3>
      <el-form-item label="阿里短信ID" prop="litemall_order_service_yunque">
        <el-input v-model="config.sys_para_sms_access_key_id" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">AccessKeyId</span>
      </el-form-item>
      <el-form-item label="阿里短信密码" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_sms_access_key_secret" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">AccessKeySecret</span>
      </el-form-item>
      <el-form-item label="任务短信模板" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_sms_task_template_code" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">TemplateCode</span>
      </el-form-item>

      <el-form-item label="管理提前预警" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_sms_manage_before_template_code" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">提前天数提醒</span>
      </el-form-item>
      <el-form-item label="管理到期通知" prop="litemall_order_service_phone">
        <el-input v-model="config.sys_para_sms_manage_currenday_template_code" class="input-width">
          <!--          <el-button slot="append" icon="el-icon-search"></el-button>-->
        </el-input>
        <span class="info">到期当天9点提醒</span>
      </el-form-item>

      <el-form-item label="考核节点告知人" prop="sys_para_sms_supervision_admin_list">
        <el-select  class="input-width" clearable v-model="selectPerson"
                    filterable   multiple placeholder="请选择" :filter-method="pinyinMatch" @change="changeGetSupervisionIdListToString">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.username"
            :value="item.id">
            <span style="float: left">{{ item.username }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.deptName }}</span>
          </el-option>
        </el-select>
      </el-form-item>

<!--      <h3>特产</h3>-->
<!--      <el-form-item label="用户下单后超时" prop="litemall_order_unpaid">-->
<!--        <el-input v-model="dataForm.litemall_order_unpaid" class="input-width">-->
<!--          <template slot="append">分钟</template>-->
<!--        </el-input>-->
<!--        <span class="info">用户未付款，则订单自动取消</span>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="订单发货后超期" prop="litemall_order_unconfirm">-->
<!--        <el-input v-model="dataForm.litemall_order_unconfirm" class="input-width">-->
<!--          <template slot="append"> 天</template>-->
<!--        </el-input>-->
<!--        <span class="info">未确认收货，则订单自动确认收货</span>-->
<!--      </el-form-item>-->

      <el-form-item>
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="update">确定</el-button>
      </el-form-item>

      <h3>系统工具</h3>
        <el-input style="width: 300px" v-model="processInstanceId" placeholder="输入实例ID"></el-input>
        <el-button-group>

          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingCurrentInfo"
                     @click="saveFlowStartMainCurrentInfo">更新当前节点信息</el-button>
          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingLuPaiInfo"
                     @click="saveFlowStartMainLuPaiInfo">更新录排节点信息</el-button>
          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingSongSheInfo"
                     @click="saveFlowStartMainSongSheInfo">更新送社节点信息</el-button>
          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingJiaopianInfo"
                     @click="saveFlowStartMainJiaopianInfo">更新交片节点信息</el-button>
          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingMainRukuInfo"
                     @click="saveFlowStartMainRukuInfo">更新入库节点信息</el-button>
          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingShouldFinishTime"
                     @click="updateShouldFinishTime">更新流程明细应完成日期</el-button>

          <el-button type="primary" style="margin-right: 5px;margin-top: 5px" v-loading="loadingNodeFactDays"
                     @click="updateNodeFactDays">更新实际用时天数</el-button>
        </el-button-group>
      <div>

      </div>

    </el-form>
  </div>
</template>

<script>
import { listSysParas, saveConfig,updateToolFlowStartMainCurrentInfo,
  updateToolFlowStartMainJiaopianInfo,updateToolFlowStartMainRukuInfo,
  updateToolFlowStartMainLuPaiInfo,updateToolFlowStartMainSongSheInfo,
  updateShouldFinishTime,updateNodeFactDays} from '@/api/config'
import {queryAllAdmin} from '@/api/admin'
import {asyncRouterMap} from '@/router'



export default {
  name: 'ConfigOrder',
  data() {
    return {
      userList: [],//所有用户列表
      sourceUserList: [],//所有用户列表
      config: {},
      loadingCurrentInfo:false,
      loadingLuPaiInfo:false,
      loadingSongSheInfo:false,
      loadingJiaopianInfo:false,
      loadingMainRukuInfo:false,
      loadingShouldFinishTime:false,
      loadingNodeFactDays:false,
      selectPerson:[],
      processInstanceId:'',
    }
  },
  created() {
    // this.init()
    this.queryAllAdmin();
  },
  methods: {
    changeGetSupervisionIdListToString($events){
      this.config.sys_para_sms_supervision_admin_list=$events.toString();
    },
    pinyinMatch(val) {
      this.userList = [...this.sourceUserList]
      const PinyinMatch = require('pinyin-match');
      if (val!=null&&val!='') {
        var result = []
        this.userList.forEach(i => {
          var m = PinyinMatch.match(i.username, val);
          if (m) {
            result.push(i);
          }
        })
        if(result.length>0)
        {
          this.userList = result
        } else
        {
          this.userList= [...this.sourceUserList]
        }
      }
    },
    queryAllAdmin() {
      queryAllAdmin().then(
        (res) => {
          this.sourceUserList = res.data.data.list;
          this.userList= res.data.data.list;
          this.init();
        }
      )
    },
    saveFlowStartMainCurrentInfo(){
      this.loadingCurrentInfo=true;
      updateToolFlowStartMainCurrentInfo({processInstanceId:this.processInstanceId}).then(
        (res)=>{
          this.loadingCurrentInfo=false;
           if(res.data.data=="执行成功"){
             this.$message.success(res.data.data)
           }
        }
      ).catch(err=>{
        this.loadingCurrentInfo=false;
      });
    },
    saveFlowStartMainJiaopianInfo(){
      this.loadingJiaopianInfo=true;
      updateToolFlowStartMainJiaopianInfo().then(
        (res)=>{
          this.loadingJiaopianInfo=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingJiaopianInfo=false;
      });
    },
    saveFlowStartMainLuPaiInfo(){
      this.loadingLuPaiInfo=true;
      updateToolFlowStartMainLuPaiInfo().then(
        (res)=>{
          this.loadingLuPaiInfo=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingLuPaiInfo=false;
      });
    },
    saveFlowStartMainSongSheInfo(){
      this.loadingSongSheInfo=true;
      updateToolFlowStartMainSongSheInfo().then(
        (res)=>{
          this.loadingSongSheInfo=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingSongSheInfo=false;
      });
    },
    saveFlowStartMainRukuInfo(){
      this.loadingMainRukuInfo=true;
      updateToolFlowStartMainRukuInfo().then(
        (res)=>{
          this.loadingMainRukuInfo=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingMainRukuInfo=false;
      });
    },

    updateShouldFinishTime(){
      this.loadingShouldFinishTime=true;
      updateShouldFinishTime().then(
        (res)=>{
          this.loadingShouldFinishTime=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingShouldFinishTime=false;
      });
    },

    updateNodeFactDays(){
      this.loadingNodeFactDays=true;
      updateNodeFactDays().then(
        (res)=>{
          this.loadingNodeFactDays=false;
          if(res.data.data=="执行成功"){
            this.$message.success(res.data.data)
          }
        }
      ).catch(err=>{
        this.loadingNodeFactDays=false;
      });
    },

    init: function() {
      this.selectPerson=[]
      listSysParas().then(response => {
        this.config = response.data.data
        let selectPersonIdArray=this.config.sys_para_sms_supervision_admin_list.split(',')
         selectPersonIdArray.forEach(
           (item)=>{
              let filterResult= this.userList.filter((user)=>{return user.id==item})
              if(filterResult!=null&&filterResult.length>0){
                this.selectPerson.push(filterResult[0].username)
              }
           }
         )
      })
    },
    cancel() {
      this.init()
    },
    update() {
      let menuHome=  asyncRouterMap.filter((item)=>{
        return item.path==''
      })
      saveConfig(this.config)
        .then(response => {
          if(this.config.sys_para_main_form_logo!=undefined&& this.config.sys_para_main_form_logo!=null){
            this.$set(menuHome[0].children[0].meta,"icon",this.config.sys_para_main_form_logo)
          }
          if(this.config.sys_para_main_form_app_name!=undefined&& this.config.sys_para_main_form_app_name!=null) {
            this.$set(menuHome[0].children[0].meta, "title", this.config.sys_para_main_form_app_name)
          }

          this.$message.success('系统参数保存成功');

          this.init()
        })
        .catch(response => {
          this.$message.error( '失败:'+response.data.errmsg);
        })
    },

    saveRongDeJi(){
      this.config={
        sys_para_login_backgroud_image:"loginBackGround.jpg",
        sys_para_main_form_logo:"guide",
        sys_para_flow_start_logo:"logo.jpg",
        sys_para_main_form_app_name:"荣德基编务"
      }
      this.update();
    },
    saveYiHua(){
      this.config={
        sys_para_login_backgroud_image:"YiHuaBackGround.jpg",
        sys_para_main_form_logo:"guide",
        sys_para_flow_start_logo:"益华Logo.jpg",
        sys_para_main_form_app_name:"益华编务"
      }
      this.update();
    },
    saveBlankSet(){
      this.config={
        sys_para_login_backgroud_image:"CommonBackGround.jpg",
        sys_para_main_form_logo:"guide",
        sys_para_flow_start_logo:"劲远科技.jpg",
        sys_para_main_form_app_name:"编务系统"
      }
      this.update();
    },
  }
}
</script>
<style scoped>
  .input-width {
    width: 50%;
  }
  .info {
    margin-left: 15px;
  }
</style>
