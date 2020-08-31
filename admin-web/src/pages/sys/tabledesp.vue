<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.oltTableDesp" clearable class="filter-item" style="width: 200px;" placeholder="请输入代码表名称"/>
      <el-input v-model="listQuery.olfFieldName" clearable class="filter-item" style="width: 200px;" placeholder="字段名称"/>
      <el-input v-model="listQuery.olfFieldDesp" clearable class="filter-item" style="width: 200px;" placeholder="字段描述"/>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button v-permission="['POST /admin/tableDesp/create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="库表绑定ID" prop="id" sortable/>

      <el-table-column align="center" label="表段名称" prop="oltDbTalbeViewName"/>

      <el-table-column align="center" label="表段描述" prop="oltTableDesp"/>

      <el-table-column align="center" label="外键名称" prop="oltForeignFieldName"/>

      <el-table-column align="center" label="外键描述" prop="oltForeignFieldDes"/>

      <el-table-column align="center" label="排序" prop="ordernumber"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['POST /admin/tableDesp/update']" type="primary" size="mini"  @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-permission="['POST /admin/tableDesp/delete']"  type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

     <!-- 添加或修改表结构字段 -->
    <el-dialog :title="textMap[dialogStatus]"  v-if="dialogFormVisible" :before-close="closeModifyForm"
               style="padding-top: 0px;padding-bottom: 0px" :close-on-click-modal="false" :visible.sync="dialogFormVisible">
      <el-card >
        <el-form ref="dataForm" :rules="rules" :model="dataForm" label-width="120px">
          <el-col :span="24" >
            <el-form-item label="表或视图名" prop="oltParentId">
              <el-input  style="width: 200px" v-model="dataForm.oltDbTalbeViewName"/>
              <el-select clearable style="width: 310px" filterable v-model="dataForm.oltDbTalbeViewName"
                         placeholder=""  @change="changeTableOrView($event)" >
                <el-option
                  v-for="item in tablesAndViewDefList"
                  :key="item.tableName"
                  :label="item.tableName+'('+item.tableComment+')'"
                  :value="item.tableName"
                >
                  <span style="float: left">{{ item.tableName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.tableComment }}</span>
                </el-option>
              </el-select>
              <el-button  type="primary"  icon="el-icon-refresh" @click="getTableOrViewList()" >刷新库表</el-button>
<!--              <el-button  type="primary"  style=" margin-left:0"  @click="getTableOrViewFieldListNoShengJiFields()" >非审计字段</el-button>-->
              <el-button  type="primary"  style=" margin-left:0" @click="getTableOrViewFieldList()" >更新字段</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="表描述" prop="oltTableDesp">
              <el-input  style="width: 200px" v-model="dataForm.oltTableDesp"/>
            </el-form-item>
          </el-col>
<!--          <el-col :span="8">-->
<!--            <el-form-item label="主键名" prop="oltPrimaryFieldName">-->
<!--              <el-select style="width: 200px" clearable v-model="dataForm.oltPrimaryFieldName" placeholder="请选择">-->
<!--                <el-option-->
<!--                  v-for="item in tableFieldsDefList"-->
<!--                  :key="item.columnName"-->
<!--                  :label="item.columnComment"-->
<!--                  :value="item.columnName"/>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="8" >
            <el-form-item label="父表" prop="oltParentId">
<!--              <el-select clearable filterable v-model="dataForm.oltParentId" placeholder="请选择">-->
<!--                <el-option-->
<!--                  v-for="item in parentList"-->
<!--                  :key="item.id"-->
<!--                  :label="item.oltTableDesp"-->
<!--                  :value="item.id"/>-->
<!--              </el-select>-->

              <el-select style="width: 200px" clearable filterable v-model="dataForm.oltParentId" @change="changeParentOltTableDesp" placeholder=""   >
                <el-option
                  v-for="item in parentList"
                  :key="item.id"
                  :label="item.oltTableDesp+'('+item.oltDbTalbeViewName+')'"
                  :value="item.id"
                 >
                  <span style="float: left">{{ item.oltTableDesp }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.oltDbTalbeViewName }}</span>
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="外键名" prop="oltForeignFieldName">
              <el-input style="width: 200px"  :disabled="sameTableNameDisable" v-model="dataForm.oltForeignFieldName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="外键描述" prop="oltForeignFieldDes">
              <el-input style="width: 200px"  v-model="dataForm.oltForeignFieldDes" />
            </el-form-item>
          </el-col>
<!--          <el-col :span="8">-->
<!--            <el-form-item label="Json包名称" prop="oltJsonPackageName">-->
<!--              <el-input style="width: 200px"  v-model="dataForm.oltJsonPackageName" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="8">
            <el-form-item label="唯一性约束" prop="oltUniqueFieldNames">
              <el-select  clearable filterable style="width: 200px" multiple
                         v-model="dataForm.oltUniqueFieldNames" placeholder="唯一性约束" @change="changeFieldsUnique">
                <el-option
                  v-for="item in defineFieldsList"
                  :key="item.columnName"
                  :label="item.columnComment"
                  :value="item.columnName"/>
              </el-select>

            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="排序" prop="ordernumber">
              <el-input style="width: 200px"  v-model="dataForm.ordernumber" />
            </el-form-item>
          </el-col>
        </el-form>
      </el-card>

      <el-card >
        <div class="hot-header" style="padding-top: 0px;margin-top: 0">
<!--          <h3 class="title common-title left" >字段列表</h3>-->
          <div class="right" style="padding-top: 0px">
<!--            <el-button  size="mini"   type="primary" @click="handleTableFieldShow">添加</el-button>-->
            <el-button v-show="!dialogFormtableFieldVisiable"     size="mini"   type="primary" @click="handleTableFieldDisplay">显示</el-button>
            <el-button v-show="dialogFormtableFieldVisiable"    size="mini"  type="primary" @click="handleTableFieldHide">隐藏</el-button>
          </div>
        </div>

        <el-form
          v-show="dialogFormtableFieldVisiable"
          ref="dicCodeForm"
          :model="dataFieldForm"
          :rules="rulesDataField"
          status-icon
          label-position="right"
          label-width="100px"
          style="margin-top: 15px">
<!--          <el-col :span="8">-->
<!--            <el-form-item label="字段名称" prop="olfFieldName">-->
<!--              <el-input :disabled="true" v-model="dataFieldForm.olfFieldName" style="width: 200px" @change="convertCmal"/>-->
<!--            </el-form-item>-->
<!--          </el-col>-->

          <el-col :span="8">
            <el-form-item label="字段名称" prop="fieldName" >
              <el-input style="width: 200px" placeholder="请输入内容" v-model="dataFieldForm.olfFieldName" :disabled="true"  clearable  >
                <el-button v-if="dataForm.oltDbTalbeViewName!=undefined&&dataForm.oltDbTalbeViewName!=''"
                           slot="append" type="primary" style="width: 70px"
                           @click="handleSelectTableField">选择</el-button>
              </el-input>

            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="字段描述"  prop="olfFieldDesp">
              <el-input  v-model="dataFieldForm.olfFieldDesp" style="width: 200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="字段类型"  prop="olfFieldType">
              <el-select :disabled="true" clearable v-model="dataFieldForm.olfFieldType"
                         placeholder="" style="width: 200px">
                <el-option value="文本型" label="文本型" />
                <el-option value="布尔型" label="布尔型" />
                <el-option value="整数型" label="整数型" />
                <el-option value="浮点型" label="浮点型" />
                <el-option value="日期型" label="日期型" />
                <el-option value="长文本" label="长文本" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8" >
            <el-form-item label="排序" prop="ordernumber">
              <el-input v-model="dataFieldForm.ordernumber" style="width: 200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="字典取值" prop="olfDicId">
              <el-select clearable  filterable style="width: 200px" v-model="dataFieldForm.olfDicName"
                         placeholder="" @change="changeDicmain($event)">
                <el-option
                  v-for="item in dicMainList"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name"/>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8" >
            <el-form-item label="取值其它表"  prop="oltTableName">
              <el-select filterable clearable style="width: 200px" filterable v-model="dataFieldForm.oltTableName"
                         placeholder=""  @change="changeTable($event)" >
                <el-option
                  v-for="item in tablesAllDefList"
                  :key="item.tableName"
                  :label="item.tableName+'('+(item.oltTableDesp==null||item.oltTableDesp==''?item.tableComment:item.oltTableDesp)+')'"
                  :value="item.tableName">
                  <span style="float: left">{{ item.tableName }}</span>
                  <span style="float: right; color: #8492a6">{{item.oltTableDesp==null||item.oltTableDesp==''?item.tableComment:item.oltTableDesp }}</span>
                </el-option>

              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="名字段"  prop="oltTableFieldLabel">
              <el-select style="width: 200px" filterable clearable v-model="dataFieldForm.oltTableFieldLabel" placeholder="">
                <el-option
                  v-for="nameItem in tablesAllFieldsList"
                  :key="nameItem.columnName"
                  :label="nameItem.columnComment"
                  :value="nameItem.columnName">
                  <span style="float: left">{{ nameItem.columnName }}</span>
                  <span style="float: right; color: #8492a6">{{ nameItem.columnComment }}</span>
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="值字段"  prop="oltTableFieldValue">
              <el-select style="width: 200px" filterable clearable v-model="dataFieldForm.oltTableFieldValue" :disabled="true" placeholder="">
                <el-option
                  v-for="valueItem in tablesAllFieldsList"
                  :key="valueItem.columnName"
                  :label="valueItem.columnComment"
                  :value="valueItem.columnName">
                  <span style="float: left">{{ valueItem.columnName }}</span>
                  <span style="float: right; color: #8492a6">{{ valueItem.columnComment }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="多值选择"   prop="olfIfMultiValue">
              <el-radio-group style="width: 300px" v-model="dataFieldForm.olfIfMultiValue">
                <el-radio :label="false"  >否</el-radio>
                <el-radio :label="true"  >是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <div class="op-container" style="text-align: right;margin-top: 15px;margin-bottom: 0px">
            <el-button type="primary"  @click="addNewTableField">新增字段</el-button>

          </div>

        </el-form>

        <el-table :data="tableFields">
          <el-table-column property="olfFieldName" label="字段名称">
            <template slot-scope="scope"  >
              <!--<slot :class="{'deletedStyle':scope.row.deleted==0||scope.row.deleted==null}">-->
              <!--{{scope.row.brandName}}-->
              <!--</slot>-->
              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.olfFieldName}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.olfFieldName}}
              </slot>
            </template>
          </el-table-column>
<!--          <el-table-column property="olfFieldTransName" label="驼峰命名">-->
<!--            <template slot-scope="scope"  >-->
<!--              <slot v-if="scope.row.deleted==1">-->
<!--                <s style="color: red">{{scope.row.olfFieldTransName}}</s>-->
<!--              </slot>-->
<!--              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >-->
<!--                {{scope.row.olfFieldTransName}}-->
<!--              </slot>-->
<!--            </template>-->
<!--          </el-table-column>-->

          <el-table-column property="olfFieldDesp" label="字段描述">
            <template slot-scope="scope"  >

              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.olfFieldDesp}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.olfFieldDesp}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="olfFieldDesp" label="字段类型">
            <template slot-scope="scope"  >

              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.olfFieldType}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.olfFieldType}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="olfDicName" label="字典表名">
            <template slot-scope="scope"  >

              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.olfDicName}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.olfDicName}}
              </slot>
            </template>
          </el-table-column>
          <el-table-column property="oltTableName" label="取值表">
            <template slot-scope="scope"  >

              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.oltTableName}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.oltTableName}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="oltTableFieldLabel" label="名字段">
            <template slot-scope="scope"  >

              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.oltTableFieldLabel}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.oltTableFieldLabel}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="olfDicName" label="值字段">
            <template slot-scope="scope"  >
              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.oltTableFieldValue}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.oltTableFieldValue}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column property="ordernumber" label="排序">
            <template slot-scope="scope"  >
              <!--<slot :class="{'deletedStyle':scope.row.deleted==0||scope.row.deleted==null}">-->
              <!--{{scope.row.brandName}}-->
              <!--</slot>-->
              <slot v-if="scope.row.deleted==1">
                <s style="color: red">{{scope.row.ordernumber}}</s>
              </slot>
              <slot  v-else-if="scope.row.deleted==0||scope.row.deleted==null" >
                {{scope.row.ordernumber}}
              </slot>
            </template>
          </el-table-column>

          <el-table-column align="center" label="操作" width="170" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" v-if="scope.row.olfFieldName!='id'" @click="handleTableFieldEdit(scope.row)">编辑</el-button>

              <el-button  v-if="scope.row.olfFieldName!='id'&&(scope.row==null||scope.row.deleted==null||scope.row.deleted==0)"  type="danger" size="mini" @click="handleTableFieldDelete(scope.row)">删除</el-button>
              <el-button  v-else-if="scope.row.olfFieldName!='id'&&(scope.row.deleted==1)"  type="primary" size="mini" @click="scope.row.deleted=0">撤销</el-button>

            </template>
          </el-table-column>
        </el-table>
        <div class="op-container" style="text-align: center;margin-top: 15px;margin-bottom: 0px">
          <el-button type="primary" v-loading="loading" v-if="dialogStatus=='create'" @click="createData" v-preventReClick>保存</el-button>
          <el-button type="primary"  v-loading="loading" v-if="dialogStatus=='update'" @click="updateData" v-preventReClick>保存</el-button>
        </div>
      </el-card>


    </el-dialog>

    <el-dialog :title="'查询表字段'" :close-on-click-modal="false" v-if="queryTableFieldSelectVisible" :visible.sync="queryTableFieldSelectVisible"
               width="900px"  >
      <import-table-field-select :inParas="{tableName:dataForm.oltDbTalbeViewName,haveExistsFields:tableFields}"
                                 ref="importTableFieldSelect" @closeAndReturnSelect="closeQueryAndAddField">
      </import-table-field-select>
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
    deleteTableDesp,readTableDesp,getTableAndViewList,getTableListAll,
  getTableFieldList } from '@/api/tabledesp'
import { queryAllDicmain } from '@/api/dicmain'
import { uploadPath } from '@/api/storage'
import Pagination from '@/components/Pagination'

import {ImportTableFieldSelect} from '@/components/CommonDialog'


export default {
  name: 'TableDesp',
  components: { Pagination,ImportTableFieldSelect },

  data() {
    return {
      sameTableNameDisable:false,
      queryTableFieldSelectVisible:false,
      defineFieldsList:[],
      loading:false,
      uploadPath,
      list: [],
      parentList:[],
      tablesAndViewDefList:[],
      tableFieldsDefList:[],
      tablesAllDefList:[],
      tablesAllFieldsList:[],
      dicMainList:[],
      total: 0,
      listLoading: true,
      tableFields: [],

      listQuery: {
        oltTableDesp: undefined,
        olfFieldName: undefined,
        olfFieldDesp: undefined,
        page: 1,
        limit: 20,
        sort: 'ordernumber asc,add_time desc'
      },
      dataForm: {
        id: undefined,
        oltTableDesp: undefined,
        oltPrimaryFieldName: 'id',
        oltUniqueFieldNames:[],
        oltUniqueFieldDeps:[],
        oltRemark: undefined,
        oltParentId: undefined,
        oltForeignFieldName: undefined,
        oltForeignFieldDes: undefined,
        oltDbTalbeViewName: undefined,
        oltJsonPackageName: undefined,
        ordernumber: undefined,
      },
      dataFieldForm: {
          id: undefined,
          oltId: undefined,
          mainTableDesp: undefined,
          olfFieldName: undefined,
          olfFieldTransName: undefined,
          olfFieldDesp: undefined,
          olfFieldType: undefined,
          olfDicId: undefined,
          olfDicName: undefined,
          olfRemark: undefined,
          ordernumber: undefined,
          oltTableName: undefined,
          oltTableDesp: undefined,
          oltTableFieldLabel: undefined,
          oltTableFieldValue: undefined,
          olfIfMultiValue: false,
      },
      dialogFormVisible: false,
      dialogFormtableFieldVisiable: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
         oltTableDesp: [
          { required: true, message: '表描述不能为空', trigger: 'blur' }
        ]
      },
      rulesDataField: {
          olfFieldName: [
              { required: true, message: '字段名称不能为空', trigger: 'blur' }
          ],
          olfFieldDesp: [
            { required: true, message: '字段描述不能为空', trigger: 'blur' }
          ],
          olfFieldType: [
            { required: true, message: '字段类型不能为空', trigger: 'blur' }
          ],

      },
      downloadLoading: false
    }
  },
  computed: {
  },
  created() {
    this.getList()
    this.getTableOrViewList();
    this.getTableAllList();
    this.getAllDicmainList();
  },
  methods: {
    changeParentOltTableDesp(val){
      this.sameTableNameDisable=false;
      let findOltId=this.parentList.filter(item=>{return item.id==val});
      if(findOltId!=null&&findOltId.length>0){
         if(findOltId[0].oltDbTalbeViewName==this.dataForm.oltDbTalbeViewName){
           this.dataForm.oltForeignFieldName="id";
           this.sameTableNameDisable=true;
         }
      }
    },
    addNewTableField(){
      if(this.dataFieldForm.id!=null){
        //更新字段
      } else{
        if(this.dataFieldForm.olfFieldName==null||this.dataFieldForm.olfFieldName==''){
          return this.$message.error("字段名称不能为空");
        }
        if(this.dataFieldForm.olfFieldDesp==null||this.dataFieldForm.olfFieldDesp==''){
         return this.$message.error("字段描述不能为空");
        }
        let findResult= this.tableFields.filter((item)=>{
          return item.olfFieldName==this.dataFieldForm.olfFieldName
        })
        if(findResult!=null&&findResult.length>0){
          let findTableField=this.tableFields.filter((item)=>{
            return item.olfFieldName==this.dataFieldForm.olfFieldName
          })
          if(findTableField!=null&&findTableField.length>0)
          {
            this.$message.error("该字段名称已经存，不能重复填加");
            return;
          }
        } else
        {
          this.tableFields.push(this.dataFieldForm)
          this.resetFormField();
        }
      }
    },
    handleSelectTableField(){
      this.queryTableFieldSelectVisible=true;
    },
    closeQueryAndAddField(para){
      console.log(para);
      this.queryTableFieldSelectVisible=false;
      if(para!=null){
        this.$set(this.dataFieldForm,"olfFieldName",para.columnName);
        this.$set(this.dataFieldForm,"olfFieldDesp",para.columnComment);
        this.$set(this.dataFieldForm,"olfFieldType",para.leiXing);
      }
    },
    changeFieldsUnique(value){
      this.dataForm.oltUniqueFieldDeps=[];
      value.forEach(
        (uniQueFieldName)=>{
          let result = this.defineFieldsList.filter(
            item =>{
              return  item.columnName==uniQueFieldName
            }
          )
          if (result.length <= 0) {
            return false;
          }
          this.dataForm.oltUniqueFieldDeps.push(result[0].columnComment);
        }
      )
    },
    getDefineFieldsList(tableOrViewName){
      getTableFieldList({tableOrViewName:tableOrViewName}).then(response => {
        this.defineFieldsList = response.data.data.list;
      })
        .catch(() => {
        })
    },
    closeModifyForm(){
      this.dialogFormVisible=false;
      this.dialogFormtableFieldVisiable=false;
    },
    convertCmal(){
      this.dataFieldForm.olfFieldTransName=this.toCamel(this.dataFieldForm.olfFieldName)
    },
    changeTableOrView($event){
        this.dataForm.oltPrimaryFieldName='';
        getTableFieldList({tableOrViewName:$event}).then(response => {
           this.defineFieldsList= response.data.data.list;
           this.tableFieldsDefList = response.data.data.list;
           if(this.tableFieldsDefList!=null&&this.tableFieldsDefList.length>0){
               var fieldPrimary = this.tableFieldsDefList.filter(item =>
               {return item.columnComment=='主键'});
               if (fieldPrimary.length <= 0) {
                   return false;
               }
               this.dataForm.oltPrimaryFieldName='主键'
           }
       })
       .catch(() => {
       })
        console.log($event)
        var result = this.tablesAndViewDefList.filter(item => {
           return item.tableName == $event
            }
        );

        if (result.length <= 0) {
            return false;
        }

        this.dataForm.oltTableDesp=result[0].tableComment;
        this.$forceUpdate()
    },

    changeTable($event){
      //tablesAllDefList
      this.tablesAllDefList.some(
        (item)=>{
          if(item.tableName==this.dataFieldForm.oltTableName){
            this.dataFieldForm.oltTableDesp=item.oltTableDesp;
            return true;
          }
        }
      );
      this.dataFieldForm.olfDicId='';
      this.dataFieldForm.olfDicName='';
      if($event==null||$event == ''){
        this.dataFieldForm.oltTableDesp=''
        this.dataFieldForm.oltTableFieldValue=''
      } else
      {
        this.dataFieldForm.oltTableFieldValue='id';
      }

      this.dataFieldForm.oltTableFieldLabel='';
      if(this.dataFieldForm.oltTableName==''){
        this.tablesAllFieldsList =[];
        return;
      }
      getTableFieldList({tableOrViewName:this.dataFieldForm.oltTableName}).then(response => {
        this.tablesAllFieldsList = response.data.data.list;
      })
      .catch(() => {
      })
    },

    changeDicmain($event){
        this.dataFieldForm.oltTableName='';
        this.dataFieldForm.oltTableFieldValue='';
        this.dataFieldForm.oltTableFieldLabel='';

        var result = this.dicMainList.filter(
            item =>{
              return  item.name==$event
            }
        )
        if (result.length <= 0) {
            return false;
        }

        this.dataFieldForm.olfDicId=result[0].id;

    },
    getTableOrViewList(){
      this.tablesAndViewDefList=[];
      getTableAndViewList().then(response => {
            this.tablesAndViewDefList = response.data.data.list
        })
        .catch(() => {
        })
    },
    getTableAllList(){
      getTableListAll().then(response => {
        this.tablesAllDefList = response.data.data.list

      })
        .catch(() => {
        })
    },
    getAllDicmainList(){
        queryAllDicmain().then(response => {
            this.dicMainList = response.data.data.list
        })
        .catch(() => {
        })
    },
    getTableOrViewFieldList(){
        if(this.dataForm.oltDbTalbeViewName==null){
            return;
        }
        if(this.tableFields!=null&& this.tableFields.length>0)
        {
            if(confirm("重新加载会更新已经设置的字段，确实要执行吗?")==false)
            {
                return;
            }
        }
        getTableFieldList({tableOrViewName:this.dataForm.oltDbTalbeViewName}).then(response => {
            this.tableFieldsDefList = response.data.data.list;

            if(this.tableFieldsDefList!=null&&this.tableFieldsDefList.length>0){
                //还要将那些不存在的记录删除掉 由后台进行处理
                // let newTemp=[];
                this.tableFieldsDefList.forEach((item)=>{
                  // if(item.columnName!='add_time'&&item.columnName!='add_by'&&item.columnName!='update_time'
                  //   &&item.columnName!='update_by'&&item.columnName!='deleted'){
                    let haveFind= this.tableFields.some(
                      (fieldItem)=>{
                        if(fieldItem.olfFieldName==item.columnName){
                          fieldItem.olfFieldTransName=this.toCamel(item.columnName)
                          fieldItem.olfFieldDesp= item.columnComment;
                          fieldItem.olfFieldType=item.leiXing;
                          // newTemp.push(fieldItem);
                          return true;
                        }
                      }
                    )
                    if(!haveFind){
                      let fieldValue={
                        id: undefined,
                        oltId: this.dataForm.id,
                        mainTableDesp: this.dataForm.oltTableDesp,
                        olfFieldName: item.columnName,
                        olfFieldTransName: this.toCamel(item.columnName),
                        olfFieldDesp: item.columnComment,
                        olfFieldType: item.leiXing,
                        olfDicId: undefined,
                        olfDicName: undefined,
                        oltTableName: undefined,
                        oltTableDesp: undefined,
                        oltTableFieldLabel: undefined,
                        oltTableFieldValue: undefined,
                        olfIfMultiValue: false,
                        olfRemark: undefined,
                        ordernumber: undefined,
                      }
                      this.tableFields.push(fieldValue)
                    }
                })
                // this.tableFields=newTemp;
            }
        })
        .catch(() => {
        })

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
          oltTableDesp: undefined,
          oltPrimaryFieldName: undefined,
          oltUniqueFieldNames:[],
          oltUniqueFieldDeps:[],
          oltRemark: undefined,
          oltParentId: undefined,
          oltForeignFieldName: undefined,
          oltForeignFieldDes: undefined,
          oltDbTalbeViewName: undefined,
          ordernumber: undefined,
          oltJsonPackageName: undefined,
      }
    },
      resetFormField() {
          this.dataFieldForm = {
            id: undefined,
            oltId: undefined,
            mainTableDesp: undefined,
            olfFieldName: undefined,
            olfFieldTransName: undefined,
            olfFieldDesp: undefined,
            olfFieldType: undefined,
            olfDicId: undefined,
            olfDicName: undefined,
            olfRemark: undefined,
            ordernumber: undefined,
            oltTableName: undefined,
            oltTableDesp: undefined,
            oltTableFieldLabel: undefined,
            oltTableFieldValue: undefined,
            olfIfMultiValue: false,
          }
      },
    handleCreate() {
      this.resetForm();
      this.resetFormField();
      this.getParentList(null);
      this.tableFields=[];
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.getDefineFieldsList(row.oltDbTalbeViewName)
      this.resetForm();
      this.resetFormField();
      this.tableFields=[];
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.getParentList(row.id);
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
    getParentList(id){
        //计算父表，从列表中去掉当前
        this.parentList=this.list;
        if(id!=undefined&&id!=null)
        {
            this.parentList= this.list.filter(item=>{
                return item.id!=id})
        }
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
    handleTableFieldShow() {
        this.dialogFormtableFieldVisiable = true
        this.resetFormField()
        this.dataFieldForm.mainTableDesp=this.dataForm.oltTableDesp;
        this.dataFieldForm.oltId=this.dataFieldForm.id;
        this.tableFields.push(this.dataFieldForm)
    },
    handleTableFieldDisplay() {
        this.dialogFormtableFieldVisiable = true
    },
    handleTableFieldHide() {
        this.dialogFormtableFieldVisiable = false
    },
    handleTableFieldEdit(row) {
        this.dialogFormtableFieldVisiable = true
        this.dataFieldForm = row
        //如果取值字段不为空，则进行加载
        //tablesAllDefList
        this.tablesAllDefList.some(
          (item)=>{
            if(item.tableName==this.dataFieldForm.oltTableName){
              this.dataFieldForm.oltTableDesp=item.oltTableDesp;
              return true;
            }
          }
        );
        // this.dataFieldForm.olfDicName='';
        // this.dataFieldForm.oltTableFieldValue='id';
        // this.dataFieldForm.oltTableFieldLabel='';
        if(this.dataFieldForm.oltTableName==''){
          this.tablesAllFieldsList =[];
          return;
        }
        getTableFieldList({tableOrViewName:this.dataFieldForm.oltTableName}).then(response => {
          this.tablesAllFieldsList = response.data.data.list;
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
      /**
       * 转驼峰命名
       * @param str
       * @returns {*}
       */
      toCamel(str) {
          if(str==null||str==""){
              return ""
          }
          return str.replace(/([^_])(?:_+([^_]))/g, function ($0, $1, $2) {
              return $1 + $2.toUpperCase();
          });
      },
      toLowerLine(str) {
          var temp = str.replace(/[A-Z]/g, function (match) {
              return "_" + match.toLowerCase();
          });
          if(temp.slice(0,1) === '_'){ //如果首字母是大写，执行replace时会多一个_，这里需要去掉
              temp = temp.slice(1);
          }
          return temp;
      },

  }
}
</script>
