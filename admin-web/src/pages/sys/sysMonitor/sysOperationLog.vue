<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container" gap="20" style="display: flex;justify-content:flex-start;flex:10">

      <el-input v-model="listQuery.addBy"  clearable class="filter-item" style="width: 200px;margin-left: 5px" placeholder="人员姓名"/>

      <el-input v-model="listQuery.logRemark"  clearable class="filter-item" style="width: 200px;margin-left: 5px" placeholder="操作内容"/>
      <div class="filter-item">
        <el-date-picker
          style="width: 200px;margin-left: 5px"
          v-model="listQuery.startOpTime"
          type="date"
          placeholder="操作起始日期"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>
      <div class="filter-item">
        <el-date-picker
          style="width: 200px;margin-left: 5px"
          v-model="listQuery.endOpTime"
          type="date"
          placeholder="操作完成日期"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>
      <el-button size="small" class="filter-item" type="primary" icon="el-icon-search" style="margin-left: 5px" @click="handleFilter">查找</el-button>

    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

       <el-table-column align="center" label="操作时间" width="160px" :formatter="dateForma" prop="addTime" />
       <el-table-column align="center" label="日志类型" width="100px"  prop="logTypeName" />
       <el-table-column align="center" label="操作用户" width="100px" prop="addBy" />
      <el-table-column align="center" label="流程节点" width="260px" prop="nodeRemark" />
       <el-table-column align="center" label="操作内容"  prop="logRemark" />


    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />


  </div>
</template>

<style>

  .viewer-container{
    z-index: 9999 !important;
  }
</style>

<script>

    import { listSysOperationLog} from '@/api/sysOperationLog'
    import Pagination from '@/components/Pagination'
    import moment from 'moment'

    export default {
        name: 'SysOperationLog',
        components: { Pagination },
        props: {
          processInstanceId: {
            type: String,
            default: "",
          }
        },
        data() {
            return {

                list: [],
                total: 0,
                listLoading: true,
                listQuery: {
                    pageNum: 1,
                    pageSize: 20,
                    sortOrder: 'addTime desc',
                    addBy: undefined,
                    startOpTime:undefined,
                    endOpTime:undefined,
                    logRemark: undefined,
                    flowInstanceId: this.processInstanceId,
                },
            }
        },
        computed: {
            headers() {
                return {
                    'X-Litemall-Admin-Token': getToken()
                }
            }
        },

        watch: {
          processInstanceId:{
            immediate: true,
            handler: function (val){
              //如果实例id发生变化，则重新获取该实例的所有附件
              this.getList()
            },
          },
        },
        methods: {
          dateForma:function(row,column){
            var date = row[column.property];
            if(date == undefined||date==""){return ''};
            return moment(date).format("YYYY-MM-DD HH:mm:ss")
          },
            getList() {
                this.listLoading = true
                listSysOperationLog(this.listQuery)
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
                this.listQuery.pageNum = 1
                this.getList()
            },

        }
    }
</script>
