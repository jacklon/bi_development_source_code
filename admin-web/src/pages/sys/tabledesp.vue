<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.chineseDesc" clearable class="filter-item" style="width: 200px;" placeholder="请输入表名称"/>
      <el-input v-model="listQuery.fieldName" clearable class="filter-item" style="width: 200px;" placeholder="字段名称"/>
      <el-input v-model="listQuery.fieldChineseDesp" clearable class="filter-item" style="width: 200px;margin-right: 10px" placeholder="字段描述"/>

      <el-button size="small" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button size="small" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
      <el-button size="small" class="filter-item" type="primary" icon="el-icon-edit" @click="handleTest">测试</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="视图ID" prop="id" sortable/>

      <el-table-column align="center" label="表名或视图名" prop="dbTableOrViewName"/>

      <el-table-column align="center" label="中文含义" prop="chineseDesc"/>

      <el-table-column align="center" label="主键名称" prop="primaryFieldName"/>

      <el-table-column align="center" label="排序" prop="ordernumber"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button  type="primary" size="mini"  @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button  type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

     <!-- 添加或修改表结构字段 -->
    <el-dialog :title="textMap[dialogStatus]"  v-if="dialogFormVisible" :before-close="closeModifyForm"
               style="padding-top: 0px;padding-bottom: 0px" width="1024px" :close-on-click-modal="false" :visible.sync="dialogFormVisible">
      <el-card >
        <el-form ref="dataForm" :rules="rules" :model="dataForm" label-width="120px">
          <el-col :span="24" >
            <el-form-item label="表或视图名" prop="oltParentId">
              <el-input  style="width: 200px" v-model="dataForm.dbTableOrViewName"/>
              <el-select clearable style="width: 310px" filterable v-model="dataForm.dbTableOrViewName"
                         placeholder=""  @change="changeTableOrView($event)" >
                <el-option
                  v-for="item in tablesAndViewDefList"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-button size="small" type="primary"  icon="el-icon-refresh" @click="getTableListAll()" >刷新库表</el-button>
              <el-button size="small" type="primary"  style=" margin-left:0" @click="getTableOrViewFieldList()" >更新字段</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="表描述" prop="chineseDesc">
              <el-input  style="width: 200px" v-model="dataForm.chineseDesc"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="主键名" prop="oltForeignFieldName">
              <el-input style="width: 200px"   v-model="dataForm.primaryFieldName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="排序" prop="ordernumber">
              <el-input style="width: 200px"  v-model="dataForm.ordernumber" />
            </el-form-item>
          </el-col>
        </el-form>
      </el-card>

        <el-table :data="tableFields">
          <el-table-column property="fieldName" label="字段名称">
            <template slot-scope="scope"  >
              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.fieldName}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.fieldName}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="fieldChineseDesp" label="字段描述">
            <template slot-scope="scope"  >
                <el-input v-model="scope.row.fieldChineseDesp" clearable  style="width: 150px;" />
            </template>
          </el-table-column>

          <el-table-column property="fieldType" label="字段类型">
            <template slot-scope="scope"  >
              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.fieldType}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.fieldType}}
              </slot>
            </template>
          </el-table-column>


          <el-table-column property="ordernumber" label="排序">
            <template slot-scope="scope"  >
              <el-input v-model="scope.row.ordernumber" clearable  style="width: 100px;" />
            </template>
          </el-table-column>

          <el-table-column align="center" label="操作" width="170" class-name="small-padding fixed-width">
            <template slot-scope="scope">
<!--              <el-button type="primary" size="mini"   @click="handleTableFieldSave(scope.row)">保存</el-button>-->
              <el-button  v-if="scope.row==null||scope.row.deleted==null||scope.row.deleted==0"  type="danger" size="mini" @click="handleTableFieldDelete(scope.row)">删除</el-button>
              <el-button  v-else-if="scope.row.fieldName!='id'&&(scope.row.deleted==1)"  type="primary" size="mini" @click="scope.row.deleted=0">撤销</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="op-container" style="text-align: center;margin-top: 15px;margin-bottom: 0px">
          <el-button type="primary" v-loading="loading" v-if="dialogStatus=='create'" @click="createData" v-preventReClick>保存</el-button>
          <el-button type="primary"  v-loading="loading" v-if="dialogStatus=='update'" @click="updateData" v-preventReClick>保存</el-button>
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

import { listTableDesp, createTableDesp, updateTableDesp,
    deleteTableDesp,readTableDesp,getTableListAll,
  getTableFieldList,updateTableField } from '@/api/tabledesp'

import { uploadPath } from '@/api/storage'
import Pagination from '@/components/Pagination'

import {ckdbOpTest} from '@/api/ckdbOp'

export default {
  name: 'TableDesp',
  components: { Pagination },

  data() {
    return {
      sameTableNameDisable:false,
      defineFieldsList:[],
      loading:false,
      uploadPath,
      list: [],

      tablesAndViewDefList:[],
      total: 0,
      listLoading: true,
      tableFields: [],

      listQuery: {
        chineseDesc: undefined,
        fieldName: undefined,
        fieldChineseDesp: undefined,
        page: 1,
        limit: 20,
        sort: 'ordernumber asc,add_time desc'
      },
      dataForm: {
        id: undefined,
        dbName: undefined,
        dbTableOrViewName: undefined,
        chineseDesc: undefined,
        primaryFieldName: undefined,
        ordernumber: undefined,
      },
      dataFieldForm: {
          id: undefined,
          stdId: undefined,
          stdChineseDesc: undefined,
          fieldName: undefined,
          fieldChineseDesp: undefined,
          fieldType: undefined,
          fieldRemark: undefined,
          ordernumber: undefined,
      },
      dialogFormVisible: false,

      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        chineseDesc: [
          { required: true, message: '表描述不能为空', trigger: 'blur' }
        ]
      },

    }
  },
  computed: {
  },
  created() {
    this.getList()
    this.getTableListAll();
  },
  methods: {
    closeModifyForm(){
      this.dialogFormVisible=false;
    },
    getTableListAll(){
      getTableListAll().then(res=>{
        this.tablesAndViewDefList=res.data.data.list
      })
    },
    changeTableOrView($event){

      var result = this.tablesAndViewDefList.filter(item => {
          return item.name == $event
        }
      );
      if (result.length <= 0) {
        return false;
      }

      this.dataForm.dbTableOrViewName=result[0].name;
      this.dataForm.primaryFieldName='';
      this.dataForm.chineseDesc='';
      this.getTableOrViewFieldList()
    },

    getTableOrViewFieldList(){
        if(this.dataForm.dbTableOrViewName==null){
            return;
        }
        if(this.tableFields!=null&& this.tableFields.length>0)
        {
            if(confirm("重新加载会更新已经设置的字段，确实要执行吗?")==false)
            {
                return;
            }
        }
        getTableFieldList({tableOrViewName:this.dataForm.dbTableOrViewName}).then(response => {
            let tableFieldsDefList = response.data.data.list;

            if(tableFieldsDefList!=null&&tableFieldsDefList.length>0){
                //还要将那些不存在的记录删除掉 由后台进行处理
                // let newTemp=[];
                tableFieldsDefList.forEach((item)=>{
                    let haveFind= this.tableFields.some(
                      (fieldItem)=>{
                        if(fieldItem.fieldName==item.name){
                          fieldItem.fieldType=this.convertDbType(item.type)
                          return true;
                        }
                      }
                    )
                    if(!haveFind){
                      let fieldValue={
                        id: undefined,
                        stdId: this.dataForm.id,
                        stdChineseDesc: this.dataForm.chineseDesc,
                        fieldName: item.name,
                        fieldChineseDesp: item.name,
                        fieldType:this.convertDbType(item.type),
                        ordernumber: undefined,
                      }
                      this.tableFields.push(fieldValue)
                    }
                })
            }
        })
        .catch(() => {
        })

    },
    convertDbType(dbType){
      //UInt8，UInt16，UInt32，UInt64，Int8，Int16，Int32，Int64
      if(dbType=='UInt8'||dbType=='UInt16'||dbType=='UInt32'||dbType=='UInt64'||dbType=='Int8'||dbType=='Int16'||dbType=='Int32'||dbType=='Int64'){
        return '整型';
      }
      //Float32, Float64
      //浮点型
      if(dbType=='Float32'||dbType=='Float64'){
        return '浮点型';
      }
      //String
      //字符串
      if(dbType=='String'){
        return '字符串';
      }
      //Date
      //日期型
      if(dbType=='Date'){
        return '日期型';
      }
      //DateTime
      //日期时间型
      if(dbType=='Date'){
        return '日期时间型';
      }
      //Enum8或Enum16
      //枚举型
      if(dbType=='Enum8'||dbType=='Enum16'){
        return '枚举型';
      }
      //Array
      //数组型
      if(dbType.indexOf('Array')>=0){
        return '数组型';
      }
    },

    getList() {
      this.listLoading = true
      listTableDesp(this.listQuery)
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
        dbName: undefined,
        dbTableOrViewName: undefined,
        chineseDesc: undefined,
        primaryFieldName: undefined,
        ordernumber: undefined,
      }
    },

    handleCreate() {
      this.resetForm();
      this.tableFields=[];
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleTest() {
      ckdbOpTest().then(
              (res)=>{
                console.log(res.data.data.list)
              }
      )
    },
    handleUpdate(row) {
      this.resetForm();
      this.tableFields=[];
      this.dialogStatus = 'update'
      this.dialogFormVisible = true

      readTableDesp(row.id)
          .then(response => {
              this.dataForm=response.data.data.tableDesp;
              if(this.dataForm.oltForeignFieldName=='id'){
                this.sameTableNameDisable=true;
              }
              this.tableFields=response.data.data.fieldsDesp;
          })
          .catch(() => {
          })
    },

    createData() {
        this.loading=true;
        const tableDespAllinone = {
            tableDesp: this.dataForm,
            fieldsDesp: this.tableFields
        }
        createTableDesp(tableDespAllinone)
            .then(response => {
                 this.loading=false;
                //重新加载
                this.getList();
                this.dialogFormVisible = false
                this.$message.success('创建成功');
                // this.$notify.success({
                //     title: '成功',
                //     message: '创建成功'
                // })

            })
            .catch(response => {
                this.loading=false;
                this.$message.error('失败:'+response.data.errmsg)
                // this.$notify.error({
                //     title: '失败',
                //     message: response.data.errmsg
                // })
            })
    },

    updateData() {
        this.loading=true;
        const tableDespAllinone = {
            tableDesp: this.dataForm,
            fieldsDesp: this.tableFields
        }
        updateTableDesp(tableDespAllinone)
          .then(() => {
            this.loading=false;
            //重新加载
            this.getList();
            this.dialogFormVisible = false
            this.$message.success('更新库表绑定成功');

          })
          .catch(response => {
            this.loading=false;
            this.$message.error( '失败:'+response.data.errmsg);
          })

    },
    handleDelete(row) {
      if(confirm('确实要删除当前记录吗?')==false){
        return
      }
      deleteTableDesp(row)
        .then(response => {
          this.$message.success('删除成功');
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
        })
        .catch(response => {
          this.$message.error( '失败:'+response.data.errmsg);
        })
    },

    handleTableFieldSave(row) {
        if(this.dataForm.id==null||this.dataForm.id==undefined){
          this.$message.error("请先保存当前表信息")
          return;
        }
        this.dataFieldForm = row
        if(this.dataFieldForm.fieldName==''){
          this.tableFields =[];
          return;
        }
        getTableFieldList({tableOrViewName:this.dataFieldForm.fieldName}).then(response => {
          this.tableFields = response.data.data.list;
        })
          .catch(() => {
          })
    },


    handleTableFieldDelete(row) {
        const index = this.tableFields.indexOf(row)
        if (row.id==null) {
            this.tableFields.splice(index, 1)
        } else {
            row.deleted = 1
        }
    },
  }
}
</script>
