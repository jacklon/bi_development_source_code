<template>
     <div class="app-container">
      <!-- 查询和其他操作 -->
      <div class="filter-container">
        <el-input v-model="listQuery.olfFieldDesp" @keyup.enter.native="editConditionChange" clearable class="filter-item" style="width: 150px;" placeholder="请输入字段描述"/>

        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleAdminFilter">查找</el-button>
        <el-button type="primary" class="filter-item" @click="addCheckData">确定添加</el-button>
      </div>
      <!-- 查询结果 -->
      <el-table
        v-loading="listLoading"
        :data="dataList"
        element-loading-text="正在查询中。。。"
        border
        fit
        highlight-current-row
        @selection-change="changeSelFieldList"
        row-key="olfFieldDesp"
        >
        <el-table-column type="selection"/>

        <el-table-column align="center" label="主键" prop="id" />
        <el-table-column align="center" label="中文含义" prop="olfFieldDesp"/>
        <el-table-column align="center" label="数据库字段名" prop="olfFieldName"/>
        <el-table-column align="center" label="驼峰命名" prop="olfFieldTransName"/>
        <el-table-column align="center" label="字段类型" prop="olfFieldType"/>

        <el-table-column align="center" label="取值字典表" prop="olfDicName"/>
        <el-table-column align="center" label="取值其他表" prop="oltTableDesp"/>


      </el-table>

    </div>


</template>
<script>
    import { getTableFieldDespForTableGrid } from '@/api/tablefielddesp'

    export default {
        name: 'SysFieldsSelect',
        props: {

            displayId: {
              type: Number,
              default: null,
            },
            queryId: {
              type: Number,
              default: null,
            },
            // 返回类型 字段、排序、条件
            returnType:{
              type:String,
              default:'字段'
            },
            formVisible:{
              type:Boolean,
              default:false
            }
        },
        data() {
            return {
                //可选的查询条件
                dataList:[],
                listLoading:false,
                total: 0, // 可推荐的数据列表总计

                listQuery: {

                    displayId:this.displayId,
                    queryId:this.queryId,
                    returnType:this.returnType,
                    olfFieldDesp:undefined,
                },
                returnDataList:[],
            }
        },
        created(){

        },
        watch: {
          displayId: {
                immediate:true,
                handler (val) {
                  if(val!=null){
                    this.getListData(val,null)
                  }
                }
            },
          queryId: {
            immediate:true,
            handler (val) {
              if(val!=null){
                this.getListData(null,val)
              }
            }
          },
          formVisible: {
            immediate:true,
            handler (val) {
              if(val){
                this.getListData(this.displayId,this.queryId)
              }
            }
          },
        },
        methods: {
          editConditionChange($event) {
            // alert($event)
            //如果是回车键，进行检索
            if ($event.code == 'Enter') {
              this.handleAdminFilter();

            }
          },
            changeSelFieldList(val){
                this.returnDataList=[];
                for(var aa in val){
                   this.returnDataList.push(val[aa])
                   // this.returnDataList.push(val[aa].id)
                }

            },
            /**
             * 进行检索
             */
            handleAdminFilter() {
                this.listQuery.page = 1
                this.getListData(this.displayId,this.queryId);
            },
            getListData(displayFangAnId,queryFangAnId) {
                this.listLoading = true

                this.listQuery.displayId=displayFangAnId;
                this.listQuery.queryId=queryFangAnId;
                this.listQuery.returnType=this.returnType;
                getTableFieldDespForTableGrid(this.listQuery).then(response => {
                    this.dataList = response.data.data.list
                    this.listLoading = false
                }).catch(() => {
                    this.dataList = []
                    this.listLoading = false
                })
            },
            addCheckData() {

                this.$emit('closeAndReturnSelect', this.returnDataList)
            },

        }
    }

</script>
