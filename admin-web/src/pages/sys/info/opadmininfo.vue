<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">

      <el-input v-model="listQuery.opadminName" clearable class="filter-item" style="width: 150px;" placeholder="接收人员"/>
      <el-input v-model="listQuery.flowStartName" clearable class="filter-item" style="width: 150px;" placeholder="流程名称"/>
      <el-input v-model="listQuery.flowNodeName" clearable class="filter-item" style="width: 150px;" placeholder="节点名称"/>

      <el-input v-model="listQuery.title" clearable class="filter-item" style="width: 150px;" placeholder="消息主题"/>
      <el-input v-model="listQuery.content" clearable class="filter-item" style="width: 150px;" placeholder="消息内容"/>

      <el-button   class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>

    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading"  :cell-style="cellStyle" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" label="操作" width="120px"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <div class="filter-container" gap="20"
               style="display: flex;justify-content:flex-start;flex:10;padding-bottom: 0px">
            <el-button-group style=" vertical-align: center;">

              <el-button type="success" icon="el-icon-s-custom" v-if="scope.row!=null&&scope.row.flowInstanceId!=null&&scope.row.flowInstanceId!=''"
                         style="width: 24px;height: 24px;align-content: center;padding: 4px;margin-right:5px"
                         title="跟踪流程" @click="TraceFlow(scope.row)"></el-button>
              <el-button type="warning" icon="el-icon-s-data" v-if="scope.row!=null&&scope.row.flowInstanceId!=null&&scope.row.flowInstanceId!=''"
                         style="width: 24px;height: 24px;align-content: center;padding: 4px;margin-right:5px"
                         title="流程看板" @click="ViewKanBan(scope.row)"></el-button>

              <el-button v-if="scope.row!=null&&scope.row.flowInstanceId!=null&&scope.row.flowInstanceId!=''"
                         type="info" icon="el-icon-view"
                         style="width: 24px;height: 24px;align-content: center;padding: 1px;margin-right:3px"
                         title="流程信息" @click="displayFlowInfoView(scope.row)">
              </el-button>

            </el-button-group>

          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="接收人员" prop="opadminName"/>
      <el-table-column align="center" label="电话号码" prop="telephone"/>
      <el-table-column align="center" label="流程名称" prop="flowStartName"/>
      <el-table-column align="center" label="流程定义" prop="flowModelKey"/>
      <el-table-column align="center" label="节点名称" prop="flowNodeName"/>

      <el-table-column align="center" label="消息主题" prop="title"/>
      <el-table-column align="center"  width="300px"   label="消息内容" prop="content"/>
      <el-table-column align="center" label="创建时间" prop="addTime"/>-->

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="'查看后端消息详情'" :close-on-click-modal="false" v-if="dialogFormVisible" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :model="dataForm"
        status-icon>

        <el-col :span="12"  >
          <el-form-item label="消息类型" prop="typeName">
            <el-input v-model="dataForm.typeName"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="消息来源" prop="sourceName">
            <el-input v-model="dataForm.sourceName"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="消息主题" prop="title">
            <el-input v-model="dataForm.title"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="消息内容" prop="content">
            <el-input type="textarea"
                      :rows="5" v-model="dataForm.content"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="站内提醒" prop="webHint">
            <el-tag :type="dataForm.webHint ? 'success' : 'error' ">{{ dataForm.webHint ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="短信标志" prop="smsHint">
            <el-tag :type="dataForm.smsHint ? 'success' : 'error' ">{{dataForm.smsHint ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :span="12"  >
          <el-form-item label="邮件标志" prop="mailHint">
            <el-tag :type="dataForm.mailHint ? 'success' : 'error' ">{{ dataForm.mailHint ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :span="12"  >
          <el-form-item label="弹窗提醒" prop="mailHint">
            <el-tag :type="dataForm.popHint ? 'success' : 'error' ">{{ dataForm.popHint ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :span="12"  >
          <el-form-item label="停显标志" prop="mailHint">
            <el-tag :type="dataForm.displayFlag ? 'success' : 'error' ">{{ dataForm.displayFlag ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :span="12"  >
          <el-form-item label="查看标志" prop="mailHint">
            <el-tag :type="dataForm.ifViewed ? 'success' : 'error' ">{{ dataForm.ifViewed ? '是' : '否' }}</el-tag>
          </el-form-item>
        </el-col>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关闭</el-button>

      </div>
    </el-dialog>




  </div>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 145px;
    height: 145px;
    display: block;
  }
</style>

<script>
  import { listOpadmininfo,readOpadmininfo  } from '@/api/opadmininfo'
  import { listDiccode } from '@/api/diccode'
  import { uploadPath } from '@/api/storage'
  import { getToken } from '@/utils/auth'
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination


  export default {
    name: 'opadmininfo',
    components: { Pagination,FlowTrace,EchartGantt,FlowInfoView },
    data() {
      return {
        uploadPath,
        sourceIdList:[],
        typeIdList:[],
        list: [],
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          flowStartName:undefined,
          flowNodeName:undefined,
          opadminName:undefined,
          typeCode:undefined,
          sourceCode:undefined,
          title:undefined,
          content:undefined,
          startDate:undefined,
          endDate:undefined,
          sort: 'add_time desc',
        },
        dataForm:{},
        dialogFormVisible: false,

        dialogTraceVisible:false,
        flowInfoViewVisible:false,
        flowKanBanVisible:false,
        currentSelectInstanceId:null,
        currentSelectFlowStartName:null,
        currentSelectModeyKey:null,

        flowStartMain:{},
      }
    },
    computed: {
      headers() {
        return {
          'X-Litemall-Admin-Token': getToken()
        }
      }
    },
    created() {
      this.getTypeIdList()
      this.getSourceIdList()
      this.getList()
    },

    methods: {
      //设置指定行、列、具体单元格颜色
      cellStyle({row, column, rowIndex, columnIndex}) {
        if(row.title=="短信发送失败"){
          return 'background:red' //rgb(105,0,7)
        } else
        {
          return ''
        }
        // if (rowIndex === 1 && columnIndex === 2) { //指定坐标rowIndex ：行，columnIndex ：列
        //   return 'background:red' //rgb(105,0,7)
        // } else {
        //   return ''
        // }
      },

        readFlowStartMain(row){
        readFlowStartMain(row.flowStartId).then(
          (res)=>{
            let data=res.data.data;
            this.flowStartMain=data.flowStartMain;
          }
        );
      },
      displayFlowInfoView(row){
        this.selectCurrentRow=row
        this.readFlowStartMain(row)
        this.flowInfoViewVisible=true;
      },

      closeFlowInfoView() {
        this.flowInfoViewVisible = false;
        this.selectCurrentRow = [];
      },
      closeFlowKanBanForm() {
        this.currentSelectInstanceId = ''
        this.flowKanBanVisible = false;
      },
      closeFlowTraceForm() {
        this.currentSelectDataId = ''
        this.currentSelectModeyKey = ''
        this.currentSelectInstanceId = ''
        this.currentSelectFlowStartName = ''
        this.dialogTraceVisible = false;
      },
      ViewKanBan(row) {
        this.currentSelectInstanceId = row.flowInstanceId;
        this.currentSelectFlowStartName= row.flowStartName;
        this.flowKanBanVisible = true;
      },
      TraceFlow(row) {
        this.currentSelectDataId = row.id
        this.currentSelectModeyKey = row.flowModelKey
        this.currentSelectInstanceId = row.flowInstanceId;
        this.currentSelectFlowStartName = row.flowStartName;
        this.dialogTraceVisible = true;
      },


      getTypeIdList(){
        listDiccode({dicmainName:"后端消息_类型"}).then(response => {
          this.typeIdList = response.data.data.list
        }).catch(() => {
          this.typeIdList = []
        })
      },
      getSourceIdList(){
        listDiccode({dicmainName:"后端消息_来源"}).then(response => {
          this.sourceIdList = response.data.data.list
        }).catch(() => {
          this.sourceIdList = []
        })
      },

      getList() {
        this.listLoading = true
        listOpadmininfo(this.listQuery)
          .then(response => {
            this.list = response.data.data.list
            this.total = response.data.data.total
            this.listLoading = false
          })
          .catch(() => {
            this.list = []
            this.total = 0
            this.listLoading = false
          })
      },
      handleFilter() {
        this.listQuery.page = 1
        this.getList()
      },

      handleView(row) {
        this.dataForm = Object.assign({}, row)
        readOpadmininfo(this.dataForm.id).then(
          (response)=>{
            this.dataForm=response.data.data;
          }
        )
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },

    }
  }
</script>
