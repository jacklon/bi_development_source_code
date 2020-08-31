<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.username" @keyup.enter.native="editConditionChange" clearable class="filter-item" style="width: 150px;"
                placeholder="管理员名称"/>
      <el-input v-model="listQuery.telphone" @keyup.enter.native="editConditionChange" clearable class="filter-item" style="width: 150px;"
                placeholder="手机号"/>
      <el-select style="width: 200px;" multiple class="filter-item" clearable v-model="listQuery.roleIds" multiple
                 placeholder="请选择角色">
        <el-option
          v-for="item in selectRoleList"
          :key="item.value"
          :label="item.label"
          :value="item.value"/>
      </el-select>
<!--      <y-cascader  :options="deptList"   @change="getLastDeptIds"-->
<!--                  :props="multiSelectProps" width="200" height="30px" style="margin-top: 0px" ></y-cascader>-->
      <el-cascader  clearable ref="belongClass"
                   :props="multiSelectProps"  style="width: 200px;height: 30px"  :options="deptList"
                    expand-trigger="hover" @change="getLastDeptIds"/>
<!--      <el-cascader class="filter-item" ref="belongClass" :show-all-levels="false" placeholder="请选择所属部门"-->
<!--                   clearable :props="multiSelectProps" style="width: 200px;" :options="deptList"-->
<!--                    expand-trigger="hover" @change="getLastDeptIds" />-->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download"
                 @click="handleDownload">导出
      </el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" label="ID" width="90px"  prop="id"/>
      <el-table-column align="center" label="名称" prop="username"/>
      <el-table-column align="center" label="邮箱" prop="email"/>
      <el-table-column align="center" label="手机号" width="110px" prop="telphone"/>
      <el-table-column align="center" label="头像" prop="avatar">
        <template slot-scope="scope">
          <img v-if="scope.row.avatar" :src="scope.row.avatar" width="40">
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色" prop="roleIds">
        <template slot-scope="scope">
          <el-tag type="primary" style="margin-right: 20px;">
            {{formatRole(scope.row.roleIds)}}
          </el-tag>
<!--          <el-tag v-for="roleId in scope.row.roleIds" :key="roleId" type="primary" style="margin-right: 20px;"> {{-->
<!--            formatRole(roleId) }}-->
<!--          </el-tag>-->
        </template>
      </el-table-column>
      <el-table-column align="center" label="部门" prop="deptName">
      </el-table-column>
      <el-table-column align="left" min-width="180" label="操作">
        <template slot-scope="scope">
            <el-button-group>
            <el-button type="primary" size="mini" style="margin-right: 4px" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" style="margin-right: 4px" @click="handleDelete(scope.row)">删除</el-button>

          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getList"/>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" width="750px" :close-on-click-modal="false"
               v-if="dialogFormVisible"  :visible.sync="dialogFormVisible" :before-close="closeDialogAndRefresh">
      <div style="display: flex;flex-direction: column" >
        <div>
          <el-card>
            <el-form ref="dataForm" :rules="rules" :model="dataForm" status-icon label-width="100px"
                     style="margin-left: 10px">
              <el-col :span="12">
                <el-form-item label="管理员名称" prop="username">
                  <el-input v-model="dataForm.username" style="width: 200px;"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="dataForm.email" style="width: 200px;"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手机" prop="telphone">
                  <el-input v-model="dataForm.telphone" style="width: 200px;"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="管理员密码" prop="password">
                  <!--          <el-input v-model="dataForm.password" style="width: 200px;"  type="password" auto-complete="off"/>-->
                  <el-input style="width: 200px" v-model="dataForm.password" clearable>
                    <el-button slot="append" type="primary" @click="handleModifyPassword">更新密码</el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="管理员头像" prop="avatar">
                  <el-upload
                          style="height: 32px;width: 32px"
                    :headers="headers"
                    :action="uploadPath"
                    :show-file-list="false"
                    :on-success="uploadAvatar"
                    class="avatar-uploader"
                    accept=".jpg,.jpeg,.png,.gif">
                    <img v-if="dataForm.avatar" :src="dataForm.avatar" class="avatar" style="width: 32px;height: 32px">
                    <i v-else class="el-icon-plus avatar-uploader-icon"/>
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="管理员角色" prop="roleIds">
                  <el-select style="width: 200px;" clearable v-model="dataForm.roleIds" multiple placeholder="请选择">
                    <el-option
                      v-for="item in ModifyRoleList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所属组织" prop="deptId">
                  <!--              <el-cascader :options="list" :props="defaultProps" v-model="orgTypeIds" clearable @change="handleDeptChange"></el-cascader>-->
                  <el-cascader ref="cascaderDept" class="filter-item" clearable :props="defaultProps" style="width: 200px;"
                               :options="deptList" :show-all-levels="false"
                               v-model="dataForm.deptId" expand-trigger="hover" @change="handleDeptChange"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="停用标记" prop="stopFlag">
                  <el-radio-group style="width: 300px" v-model="dataForm.stopFlag">
                    <el-radio :label="false">正常</el-radio>
                    <el-radio :label="true">停用</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="移动端角色" prop="dataDeptString">
                  <el-select style="width: 200px;" clearable v-model="dataForm.mobileRoleIds" multiple placeholder="请选择">
                    <el-option
                      v-for="item in mobileRoleList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="部门数据权限" prop="dataDeptString">
                  <el-cascader ref="cascaderDataDeptIds" class="filter-item" clearable :props="multiSelectProps"
                               style="width: 550px;"
                               :options="deptList" :show-all-levels="false"
                               v-model="selectDataDeptIds" expand-trigger="hover" @change="handleDataDeptChange"/>
                </el-form-item>
              </el-col>


            </el-form>
          </el-card>
        </div>
        <div style="margin-left: 20px;margin-right: 20px">
          <div style="font-size: medium;font-weight: bold;margin-bottom: 20px;color: #fff;">表数据权限:
            <el-button size="mini" type="primary" @click="handlePrvFieldShow">添加</el-button>
            <el-button v-show="!prvFieldVisiable" size="mini" type="primary" @click="handlePrvFieldDisplay">显示
            </el-button>
            <el-button v-show="prvFieldVisiable" size="mini" type="primary" @click="handlePrvFieldHide">隐藏</el-button>
          </div>
        </div>
        <div >

            <el-form
              v-show="prvFieldVisiable"
              ref="dicCodeForm"
              :model="prvFieldForm"
              :rules="dialogrules"
              status-icon
              label-width="100px">

              <el-col :span="12">
                <el-form-item label="表或视图名" prop="tableViewName">
                  <el-select clearable filterable style="width: 200px"
                             v-model="prvFieldForm.sysTableDespId" placeholder="请选择" @change="changeSysTableDesp($event)">
                    <el-option
                      v-for="tableOrViewName in sysTableDespList"
                      :key="tableOrViewName.id"
                      :label="tableOrViewName.oltTableDesp"
                      :value="tableOrViewName.id">
                      <span style="float: left">{{ tableOrViewName.oltTableDesp }}</span>
                      <span
                        style="float: right; color: #8492a6; font-size: 13px">{{ tableOrViewName.oltDbTalbeViewName }}</span>
                    </el-option>
                  </el-select>


                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="字段名称" prop="fieldName">

                  <el-select clearable filterable style="width: 200px"
                             v-model="prvFieldForm.fieldName" @change="changeFieldsUnique">
                    <el-option
                      v-for="item in defineFieldsList"
                      :key="item.columnName"
                      :label="item.columnComment"
                      :value="item.columnName">
                      <span style="float: left">{{ item.columnComment }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.columnName }}</span>
                    </el-option>
                  </el-select>

                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="操作符" prop="fieldOper">
                  <el-select style="width: 200px" v-model="prvFieldForm.fieldOper">
                    <el-option label="等于" value="=">等于</el-option>
                    <el-option label="大于" value=">">大于</el-option>
                    <el-option label="大等于" value=">=">大等于</el-option>
                    <el-option label="小于" value="<">小于</el-option>
                    <el-option label="小等于" value="<=">小等于</el-option>
                    <el-option label="不等于" value="<>">不等于</el-option>
                    <el-option label="相似于" value="like">相似于</el-option>
                    <el-option label="不相似" value="not like">不相似</el-option>
                    <el-option label="在..里" value="in">在..里</el-option>
                    <el-option label="不在..里" value="not in">不在..里</el-option>
                    <el-option label="为空" value="is null">为空</el-option>
                    <el-option label="不为空" value="is not null">不为空</el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="字段值" prop="fieldValue">
                  <!--                <el-input v-model="prvFieldForm.fieldValue" style="width: 200px"/>-->

                  <el-input v-if="(fieldDesp.olfDicId==null||fieldDesp.olfDicId=='')&&(fieldDesp.olfFieldType=='文本型'||fieldDesp.olfFieldType=='整数型'
                        ||fieldDesp.olfFieldType=='浮点型'||fieldDesp.olfFieldType=='长文本')"
                            v-model="prvFieldForm.fieldValue" clearable class="filter-item" style="width: 200px;"/>

                  <el-date-picker style="width: 200px;"
                                  v-if="(fieldDesp.olfDicId==null||fieldDesp.olfDicId=='')&&(fieldDesp.olfFieldType=='日期型')"
                                  v-model="prvFieldForm.fieldValue"
                                  type="datetime"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  placeholder="选择日期时间">
                  </el-date-picker>

                  <el-select v-if="(fieldDesp.olfDicId==null||fieldDesp.olfDicId=='')&&(fieldDesp.olfFieldType=='布尔型')"
                             v-model="prvFieldForm.fieldValue" clearable class="filter-item" style="width: 200px">
                    <el-option label="否" :value="'否'"/>
                    <el-option label="是" :value="'是'"/>
                  </el-select>

                  <el-select multiple filterable
                             v-if="(fieldDesp.olfDicId!=null&&fieldDesp.olfDicId!='')||(fieldDesp.oltTableName!=null&&fieldDesp.oltTableName!='')"
                             v-model="prvFieldForm.fieldValue" clearable class="filter-item" style="width: 200px">
                    <el-option v-for=" code in fieldCodes"
                               :value="code.id"
                               :key="code.id"
                               :label="code.name">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

            </el-form>

        </div>
        <div style="margin-left: 10px;margin-right: 10px">
          <el-table :data="prvFieldList" >
            <el-table-column property="code" label="表描述">
              <template slot-scope="scope">
                <slot v-if="scope.row.deleted==1">
                  <s style="color: red">{{scope.row.tableViewDesc}}</s>
                </slot>
                <slot v-else-if="scope.row.deleted==0||scope.row.deleted==null">
                  {{scope.row.tableViewDesc}}
                </slot>
              </template>
            </el-table-column>
            <el-table-column property="code" label="字段描述">
              <template slot-scope="scope">
                <slot v-if="scope.row.deleted==1">
                  <s style="color: red">{{scope.row.fieldDesc}}</s>
                </slot>
                <slot v-else-if="scope.row.deleted==0||scope.row.deleted==null">
                  {{scope.row.fieldDesc}}
                </slot>
              </template>
            </el-table-column>

            <el-table-column property="name" label="操作符">
              <template slot-scope="scope">
                <slot v-if="scope.row.deleted==1">
                  <s style="color: red">{{scope.row.fieldOper}}</s>
                </slot>
                <slot v-else-if="scope.row.deleted==0||scope.row.deleted==null">
                  {{scope.row.fieldOper}}
                </slot>
              </template>
            </el-table-column>

            <el-table-column property="ordernumber" label="字段值">
              <template slot-scope="scope">
                <slot v-if="scope.row.deleted==1">
                  <s style="color: red">{{scope.row.fieldValue}}</s>
                </slot>
                <slot v-else-if="scope.row.deleted==0||scope.row.deleted==null">
                  {{scope.row.fieldValue}}
                </slot>
              </template>
            </el-table-column>

            <el-table-column align="center" label="操作" width="144" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="handlePrivEdit(scope.row)">编辑</el-button>
                <el-button v-if="scope.row==null||scope.row.deleted==null||scope.row.deleted==0" type="danger" size="mini"
                           @click="handlePrivDelete(scope.row)">删除
                </el-button>
                <el-button v-else-if="scope.row.deleted==1" type="primary" size="mini" @click="scope.row.deleted=0">撤销
                </el-button>

              </template>
            </el-table-column>
          </el-table>
        </div>
        <div slot="footer" class="dialog-footer" style="text-align: center;margin-top: 15px">
          <el-button size="small" @click="dialogFormVisible = false">取消</el-button>
          <el-button size="small" v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
          <el-button size="small" v-else type="primary" @click="updateData">确定</el-button>
        </div>
      </div>

    </el-dialog>

  </div>
</template>

<style>
  .hot-header {
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
  }

  .left {
    height: 30px;
  }

  .right {
    height: 30px;
    line-height: 30px;
    font-size: 14px;
    color: #9E9E9E;
  }

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
    width: 32px;
    height: 32px;
    line-height: 32px;
    text-align: center;
  }

  .avatar {
    width: 145px;
    height: 145px;
    display: block;
  }

  .el-cascader-panel .el-radio {
    width: 100%;
    height: 100%;
    z-index: 10;
    position: absolute;
    top: 10px;
    right: -10px;
    visibility: hidden;
  }

  .el-cascader-panel .el-cascader-node__postfix {
    　　top: 10px;
  }
</style>

<script>
  import {listAdmin, createAdmin, updateAdmin, deleteAdmin, readminAdmin, modifyPassword} from '@/api/admin'
  import {roleOptions} from '@/api/role'
  import {uploadPath} from '@/api/storage'
  import {getToken} from '@/utils/auth'
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
  import {listAllBCorpInfo} from '@/api/bcorpinfo'
  import {queryAllTableDesp, getTableFieldList} from '@/api/tabledesp'
  import {getTableFieldDesp} from '@/api/tablefielddesp'
  import {
    listAdminFieldPriv,
    createAdminFieldPriv,
    readAdminFieldPriv,
    deleteAdminFieldPriv
  } from '@/api/adminFieldPriv'
  import {listDiccode} from '@/api/diccode'

  import {queryallByDeptId,queryAllAdmin} from "@/api/admin"

  export default {
    name: 'Admin',
    components: {Pagination},

    data() {
      return {
        userListDept: [],//用户部门列表
        userListAllTask: [],//所有用户列表
        sourceUserListAll:[],
        sourceListDept: [],

        selectCurrentUserId:undefined,
        selectCurrentUserName:undefined,

        sysTableDespList: [],
        defineFieldsList: [],
        prvFieldVisiable: false,
        prvFieldForm: {
          id: undefined,
          userId: undefined,
          username: undefined,
          sysTableDespId: undefined,
          tableViewName: undefined,
          tableViewDesc: undefined,
          fieldName: undefined,
          fieldDesc: undefined,
          fieldOper: "=",
          fieldValue: ""
        },
        fieldDesp: {},
        fieldCodes: {},
        prvFieldList: [],
        uploadPath,
        list: null,
        deptList: [],
        selectDataDeptIds: [],
        multiSelectProps: {
          children: 'children',
          label: 'name',
          value: "id",
          multiple: true,
          // checkStrictly: true
        },
        defaultProps: {
          children: 'children',
          label: 'name',
          value: "id",
          // checkStrictly: true
        },
        total: 0,
        selectRoleList: [],
        ModifyRoleList:[],
        mobileRoleList:[],
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          username: undefined,
          roleIds: undefined,
          deptIds: undefined,
          sort: 'add_time desc',
        },


        dataForm: {
          id: undefined,
          username: undefined,
          stopFlag: false,
          password: undefined,
          avatar: undefined,
          roleIds: [],
          mobileRoleIds: [],
          deptId: undefined,
          deptIdString:[],
          deptName: undefined,

        },


        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建'
        },
        rules: {
          username: [
            {required: true, message: '管理员名称不能为空', trigger: 'blur'}
          ],
          deptId: [
            {required: true, message: '所属部门不能为空', trigger: 'blur'}
          ],
          // password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
        },
        dialogrules: {
          tableViewName: [{required: true, message: '表名不能为空', trigger: 'blur'}],
          fieldName: [{required: true, message: '表字段名不能为空', trigger: 'blur'}],

        },
        downloadLoading: false
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
      this.queryAllTableDesp()
      this.getDeptList()
      this.getRoleList();
      this.getList()
      this.getMobileRoleList()
      this.queryAllAdmin()
    },
    mounted() {
      setInterval(function () {
        document.querySelectorAll('.el-cascader-node__label').forEach(el => {
          el.onclick = function () {
            if (this.previousElementSibling) this.previousElementSibling.click()
          }
        })
      }, 1000)

    },
    methods: {
      closeDialogAndRefresh(){
        this.dialogFormVisible = false
        this.sourceStopFlag=undefined
        this.sourceDeptId=undefined
        this.sourceUnhandleOwner=undefined
        this.sourceUnhandleTask=undefined
        this.sourceUnhandleSupervision=undefined
      },
      queryAllAdmin() {
        queryAllAdmin().then(
          (res) => {
            this.userListAllTask = res.data.data.list;
            this.userListAllOwner= res.data.data.list;
            this.sourceUserListAll= res.data.data.list;
          }
        )
      },
      queryallByDeptId(deptId) {
        queryallByDeptId({deptId:deptId}).then(
          (res) => {
            this.userListDept = res.data.data.list;
            this.sourceListDept = res.data.data.list;
          }
        )
      },


      getMobileRoleList() {
        listDiccode({dicmainName: "移动端角色功能"}).then(
          (res) => {
            this.mobileRoleList = res.data.data.list;
          }
        );
      },
      getLastDeptIds(val){
        this.listQuery.deptIds=[];
        if(val!=null&&val.length>0){
          val.forEach(item=>{
            this.listQuery.deptIds.push(item[item.length-1])
          })
        }
      },
      editConditionChange($event) {
        //如果是回车键，进行检索
        if ($event.code == 'Enter') {
          this.handleFilter();
        }
      },

      handlePrivEdit(row) {
        this.prvFieldVisiable=true;
        this.prvFieldForm = row;
        getTableFieldList({tableOrViewName: this.prvFieldForm.tableViewName}).then(response => {
          this.defineFieldsList = response.data.data.list;
        })
          .catch(() => {
          })
        //获取该字段属性
        this.getTableFieldDesp(this.prvFieldForm.sysTableDespId, this.prvFieldForm.fieldName)

        this.prvFieldVisiable = true;
      },
      handlePrivDelete(row) {
        if (row.id != null) {
          if (confirm("确认要删除吗?") == true) {
            deleteAdminFieldPriv({id:row.id}).then(res => {
              this.readminAdmin(this.dataForm.id);
            })
          }
        } else {
          const index = this.prvFieldList.indexOf(row)
          this.prvFieldList.splice(index, 1)
        }
      },
      getTableFieldDesp(sysTableDespId, fieldName) {
        getTableFieldDesp({sysTableDespId: sysTableDespId, fieldName: fieldName}).then(
          (res) => {
            this.fieldDesp = res.data.data.sysTableFieldDesp;
            this.fieldCodes = res.data.data.dicCodes;

            this.prvFieldForm.fieldValue=this.strToArray(this.prvFieldForm.fieldValue.toString(),this.fieldDesp)
          }
        );
      },

      changeFieldsUnique(value) {
        this.prvFieldForm.fieldValue = ""
        this.prvFieldForm.fieldOper = "="
        let result = this.defineFieldsList.filter(
          item => {
            return item.columnName == value
          }
        )
        if (result.length <= 0) {
          return false;
        }
        this.prvFieldForm.fieldDesc = result[0].columnComment;

        //获取该字段属性
        this.getTableFieldDesp(this.prvFieldForm.sysTableDespId, value)

      },

      changeSysTableDesp($event) {
        console.log($event)
        if ($event == null) {
          this.prvFieldForm.tableViewDesc = ''
          this.prvFieldForm.tableViewName = ''
          this.defineFieldsList = [];
          return;
        }
        var result = this.sysTableDespList.filter(item => {
            return item.id == $event
          }
        );
        if (result.length <= 0) {
          return false;
        }
        this.prvFieldForm.tableViewDesc = result[0].oltTableDesp;
        this.prvFieldForm.tableViewName = result[0].oltDbTalbeViewName;

        getTableFieldList({tableOrViewName: this.prvFieldForm.tableViewName}).then(response => {
          this.defineFieldsList = response.data.data.list;
        })
          .catch(() => {
          })

      },
      queryAllTableDesp() {
        this.sysTableDespList = [];
        queryAllTableDesp().then(response => {
          this.sysTableDespList = response.data.data.list
        })
          .catch(() => {
          })
      },
      handlePrvFieldDisplay() {
        this.prvFieldVisiable = true
      },
      handlePrvFieldHide() {
        this.prvFieldVisiable = false
      },

      handlePrvFieldShow() {
        this.prvFieldVisiable = true
        this.prvFieldForm = {
          id: undefined,
          userId: undefined,
          username: undefined,
          sysTableDespId: undefined,
          tableViewName: undefined,
          tableViewDesc: undefined,
          fieldName: undefined,
          fieldDesc: undefined,
          fieldOper: "=",
          fieldValue: ""
        }
        this.prvFieldList.push(this.prvFieldForm);
      },
      handleModifyPassword() {
        if (this.dataForm.id == undefined || this.dataForm.id == null) {
          this.$message.error("请先保存用户信息");
          return;
        }
        if (this.dataForm.password == undefined || this.dataForm.password == null || this.dataForm.password.length < 6) {
          this.$message.error("密码不能为空，且必须大于6位");
          return;
        }
        modifyPassword(this.dataForm)
          .then(response => {
            this.$message.success("更新成功");
          })
      },

      handleDeptChange(value) {
        if (value == null||value.length==0) {
          this.dataForm.deptId = undefined;
          this.dataForm.deptName=undefined;
          this.dataForm.deptIdString = []
          return;
        }
        this.dataForm.deptIdString = []
        value.forEach(item => {
          this.dataForm.deptIdString.push(item.toString())
        })
        this.dataForm.deptId = value[value.length - 1]
        var selectList = this.$refs['cascaderDept'].getCheckedNodes()[0].pathLabels;
        if (selectList != null && selectList.length > 0) {
          this.dataForm.deptName = selectList[selectList.length - 1];
        }
      },
      handleDataDeptChange(value) {
        console.log(value)
        this.dataForm.dataDeptString = []
        value.forEach(item => {
          this.dataForm.dataDeptString.push(item.toString())
        })
      },


      getRoleList() {
        roleOptions()
          .then(response => {
            this.selectRoleList = response.data.data.list;
            this.ModifyRoleList = response.data.data.list;
            console.log(this.selectRoleList)
          })
      },
      getDeptList() {
        listAllBCorpInfo()
          .then(response => {
            this.deptList = response.data.data.list
            if(this.dataForm.id!=null&&this.dataForm.id!=undefined){
              this.readminAdmin(this.dataForm.id)
            }
          })
          .catch(() => {

          })
      },
      formatRole(roleIds) {
        let roleJson= JSON.parse(roleIds);
        let jsonResult=''
        roleJson.forEach(role=>{
           let findRole= this.selectRoleList.filter(item=>{return item.value==role})
           if(findRole!=null&&findRole.length>0){
             jsonResult+=findRole[0].label+","
           }
        })
        if(jsonResult!=''){
          jsonResult=jsonResult.substr(0,jsonResult.length-1);
        }
        return jsonResult
      },

      getList() {
        this.listLoading = true
        listAdmin(this.listQuery)
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
      resetForm() {
        this.dataForm = {
          id: undefined,
          username: undefined,
          stopFlag: false,
          password: undefined,
          avatar: undefined,
          roleIds: [],
          mobileRoleIds: [],
          deptId: undefined,
          deptName: undefined,
          dataDeptIds:  [],
          dataDeptString: [],
        }

        this.selectDataDeptIds = []
      },
      uploadAvatar: function (response) {
        this.dataForm.avatar = response.data.url
      },
      handleCreate() {
        this.resetForm()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      createData() {
        this.$refs['dataForm'].validate(valid => {
          if (valid) {
            let adminAllInOne = {
              admin: this.dataForm,
              adminFieldPrivs: this.prvFieldList
            }
            createAdmin(adminAllInOne)
              // createAdmin(this.dataForm)
              .then(response => {

                this.list.unshift(response.data.data)
                this.dialogFormVisible = false
                this.prvFieldVisiable=false
                this.$message.success('添加管理员成功');
                this.getList();
              })
              .catch(response => {
                this.$message.error('失败:' + response.data.errmsg);
              })
          }
        })
      },
      //字符串转数组
      strToArray(val,fieldInfo) {
        let temp = []
        if (val == null || val == []) {
          return;
        }
        let arr = val.split(",")
        arr.forEach(item => {
          {

            if (fieldInfo!=null &&( (fieldInfo.olfDicId!=null&&fieldInfo.olfDicId!='')||
            (fieldInfo.oltTableName!=null&&fieldInfo.oltTableName!='')))
            {
              temp.push(Number(item))
            } else
            {
              temp= item;
            }

          }

        })
        return temp
      },
      strToArrayAndReturn(returnArr, val) {
        arr = [];
        if (val == null || val == []) {
          return;
        }
        let arr = eval(val)

        arr.forEach(item => {
          let newArr = item.split(",")
          returnArr.push(newArr)
        })
        return returnArr;
      },

      strToArrayAndReturnTwoDimmision(val) {
        let returnArr=[]
        arr = [];
        if (val == null || val == []) {
          return;
        }
        let arr = eval(val)
        arr.forEach(item => {
          let tempArr=[]
          let newArr = item.split(",")
          newArr.forEach(two=>{
            tempArr.push(two);
          })
          returnArr.push(tempArr)
        })
        return returnArr;
      },

      readminAdmin(userId) {
        readminAdmin(userId).then(
          (response) => {
            let data = response.data.data

            this.dataForm = data.admin;
            // this.selectDataDeptIds = Object.assign({}, this.dataForm.dataDeptString)
            // this.selectDataDeptIds=Ojbect.assign
            this.selectDataDeptIds=this.strToArrayAndReturnTwoDimmision(this.dataForm.dataDeptString);
            //加载全部时不转，当编辑时再进行转换
            // data.adminFieldPrivs.forEach(item => {
            //   item.fieldValue = this.strToArray(item.fieldValue);
            // })
            this.prvFieldList = data.adminFieldPrivs;
          }
        )
      },

      handleUpdate(row) {

        this.sourceStopFlag=row.stopFlag
        this.sourceDeptId=row.deptId;
        this.sourceUnhandleOwner=row.unhandleOwner
        this.sourceUnhandleTask=row.unhandleTask
        this.sourceUnhandleSupervision=row.sourceUnhandleSupervision
        this.dataForm = Object.assign({}, row)
        this.getDeptList();
        // this.dataForm.roleIds=row.roleIds;
        // this.dataForm.deptId=row.deptId;
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      }
      ,
      updateData() {
        this.$refs['dataForm'].validate(valid => {
          if (valid) {

            //如果用户的从原来的正常改成停用，或者用户的部门发生了变化，判断一下用户是否有未完成流程,
            //如果原来用户没有指定部门，则可以直接跳过，不用判断，直接保存成新的门
            if((this.dataForm.stopFlag==true&&this.sourceStopFlag==false)||(this.dataForm.deptId!=this.sourceDeptId&&
              this.dataForm.deptId!=null&&this.dataForm.deptId!=undefined))
            {
              if(this.sourceUnhandleOwner==true){
                this.$message.error('该用户还拥有已经创建未执行完毕的流程，请修改流程的拥有者信息,再尝试停用或者修改部门');
                return;
              }

              if(this.sourceUnhandleTask==true){
                this.$message.error('该用户还在未完成实例中有未执行任务，请先修改实例中的任务执行人,再尝试停用或者修改部门');
                return;
              }

              if(this.sourceUnhandleSupervision==true){
                this.$message.error('该用户还在未完成实例中有监督人，请先修改实例中的任务监督人,再尝试停用或者修改部门');
                return;
              }
            }

            this.prvFieldList.forEach(item => {
              item.fieldValue = item.fieldValue.toString();
            })

            let adminAllInOne = {
              admin: this.dataForm,
              adminFieldPrivs: this.prvFieldList
            }
            updateAdmin(adminAllInOne)
              // updateAdmin(this.dataForm)
              .then(() => {
                for (const v of this.list) {
                  if (v.id === this.dataForm.id) {
                    const index = this.list.indexOf(v)
                    this.list.splice(index, 1, this.dataForm)
                    break
                  }
                }
                this.dialogFormVisible = false
                this.prvFieldVisiable=false
                this.getList();
                this.$message.success('更新管理员成功');

              })
              .catch(response => {
                this.$message.error('失败:' + response.data.errmsg);
              })
          }
        })
      }
      ,
      handleDelete(row) {

        if(row.unhandleOwner==true){
          this.$message.error('该用户还拥有已经创建未执行完毕的流程，请修改流程的拥有者信息,再尝试删除');
          return;
        }

        if(row.unhandleTask==true){
          this.$message.error('该用户还在未完成实例中有未执行任务，请先修改实例中的任务执行人,再尝试删除');
          return;
        }

        if (confirm('确实要删除当前记录吗?') == false) {
          return
        }

        deleteAdmin({id:row.id})
          .then(response => {
            this.$message.success('删除管理员成功');

            const index = this.list.indexOf(row)
            this.list.splice(index, 1)
          })
          .catch(response => {
            this.$message.error('失败:' + response.data.errmsg);
          })
      }
      ,
      handleDownload() {
        let that=this;

        let listExportQuery=[...that.listQuery];
        that.$set(listExportQuery,"page",1)
        that.$set(listExportQuery,"limit",999999)
        that.$set(listExportQuery,"sort",'dept_name,id asc')


        that.downloadLoading = true
          listAdmin(listExportQuery)
            .then(response => {
              let excellist = response.data.data.list

              import('@/vendor/Export2Excel').then(excel => {
                const tHeader = ['管理员ID', '姓名', '邮箱','手机','部门名称']
                const filterVal = ['id', 'username', 'email','telphone','deptName']
                excel.export_json_to_excel2(
                  tHeader,
                  excellist,
                  filterVal,
                  '管理员信息'
                )
                that.downloadLoading = false
              })

            })
            .catch(() => {

              that.downloadLoading = false
            })


      }
    },

  }
</script>
