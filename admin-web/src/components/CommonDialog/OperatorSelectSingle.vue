<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.username" @keyup.enter.native="editConditionChange" clearable class="filter-item"
                style="width: 150px;" placeholder="请输入人员名称"/>

      <el-cascader class="filter-item" ref="belongClass" placeholder="请选择所属部门"
                   clearable :props="multiSelectProps" :show-all-levels="false" style="width: 200px;"
                   :options="deptList"
                   expand-trigger="hover" @change="setDeptIds"/>
      <el-select style="width: 200px;" multiple class="filter-item" clearable v-model="listQuery.roleIds" multiple
                 placeholder="请选择角色">
        <el-option
          v-for="item in roleList"
          :key="item.value"
          :label="item.label"
          :value="item.value"/>
      </el-select>

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
      row-key="id"
      @row-click="selectCurrentRow"

      @current-change="checkChange"
    >
      <el-table-column
        label="操作"
        width="55">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.checked"></el-checkbox>
        </template>
      </el-table-column>

      <el-table-column align="center" label="管理员ID" prop="id"/>
      <el-table-column align="center" label="管理员名称" prop="username"/>
      <el-table-column align="center" label="邮箱" prop="email"/>
      <el-table-column align="center" label="手机号" prop="telphone"/>
      <el-table-column align="center" label="管理员头像" prop="avatar">
        <template slot-scope="scope">
          <img v-if="scope.row.avatar" :src="scope.row.avatar" width="40">
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理员角色" prop="roleIds">
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
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getAdminList"/>

  </div>

</template>

<script>
  import {listAdmin} from '@/api/admin'
  import {roleOptions} from '@/api/role'
  import {listAllBCorpInfo} from '@/api/bcorpinfo'

  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

  export default {
    components: {Pagination},
    name: 'OperatorSelectSingle',
    props: {
      default: {
        type: [],
        required: false
      },
    },
    data() {
      return {
        //可选的查询条件
        dataList: [],

        listLoading: false,
        total: 0, // 可推荐的数据列表总计
        multiSelectProps: {
          children: 'children',
          label: 'name',
          value: "id",
          multiple: true,
          // checkStrictly: true
        },
        listQuery: {
          page: 1,
          limit: 10,
          username: undefined,
          stopFlag:false,
          roleIds: undefined,
          deptIds: undefined,
          sort: 'add_time desc',
        },
        returnDataForm: {
          id: undefined,
          username: undefined
        },
        returnUserList: {},
        deptList: [],
        roleList: [],

      }
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
    created() {
      this.getDeptList()
      this.getRoleList();
      this.getAdminList()
    },
    watch: {
      default: {
        deep: true,
        handler(val) {
          this.listQuery.roleId = val.roleId;
          this.listQuery.comId = val.roleId;

        }
      },

    },
    methods: {
      setDeptIds(val){
        this.listQuery.deptIds=[];
        if(val!=null&&val.length>0){
          val.forEach(item=>{
            this.listQuery.deptIds.push(item[item.length-1])
          })
        }
      },
      editConditionChange($event) {
        // alert($event)
        //如果是回车键，进行检索
        if ($event.code == 'Enter') {
          this.handleAdminFilter();

        }
      },
      // changeSelUserList(val) {
      //   this.returnUserList = [];
      //   for (var aa in val) {
      //     this.returnUserList.push(val[aa])
      //   }
      //   // val.foreach(item=>{
      //   //     this.returnUserList.push(item.id)
      //   // })
      // },
      selectCurrentRow(row){
        row.checked=true;
        this.checkChange(row)
      },
      checkChange(row) {
        this.returnUserList=row
        // this.returnUserList.push(row);
        if (row == null) {
          return
        }

        this.dataList.forEach(item => {
          // 排他,每次选择时把其他选项都清除
          if (item.id !== row.id) {
            item.checked = false
          }
        })
      },
      // formatRole(roleId) {
      //   for (let i = 0; i < this.roleList.length; i++) {
      //     if (roleId === this.roleList[i].value) {
      //       return this.roleList[i].label
      //     }
      //   }
      //   return ''
      // },

      formatRole(roleIds) {
        let roleJson= JSON.parse(roleIds);
        let jsonResult=''
        roleJson.forEach(role=>{
          let findRole= this.roleList.filter(item=>{return item.value==role})
          if(findRole!=null&&findRole.length>0){
            jsonResult+=findRole[0].label+","
          }
        })
        if(jsonResult!=''){
          jsonResult=jsonResult.substr(0,jsonResult.length-1);
        }
        return jsonResult
      },
      getDeptList() {
        listAllBCorpInfo(this.listQuery)
          .then(response => {
            this.deptList = response.data.data.list
          })
          .catch(() => {

          })
      },
      getRoleList() {
        roleOptions()
          .then(response => {
            this.roleList = response.data.data.list;
            console.log(this.roleList)
          })
      },
      /**
       * 进行检索
       */
      handleAdminFilter() {
        this.listQuery.page = 1
        this.getAdminList()

      },
      getAdminList() {
        this.listLoading = true
        listAdmin(this.listQuery).then(response => {
          this.dataList = response.data.data.list
          this.total = response.data.data.total
          this.listLoading = false
        }).catch(() => {
          this.dataList = []
          this.total = 0
          this.listLoading = false
        })
      },
      // checkChange(row) {
      //     if(row==null){
      //         return
      //     }
      //     this.returnDataForm.id=row.id
      //     this.returnDataForm.username=row.username
      // },
      addCheckData() {
        this.$emit('closeAndReturnSelect', this.returnUserList)
      },



    }
  }

</script>
<style type="text/css" scoped>
  .el-cascader-panel .el-radio{
    width: 100%;
    height: 100%;
    z-index: 10;
    position: absolute;
    top: 10px;
    right: -10px;
    visibility: hidden;
  }
  .el-cascader-panel .el-cascader-node__postfix{
    　　top: 10px;
  }
</style>
