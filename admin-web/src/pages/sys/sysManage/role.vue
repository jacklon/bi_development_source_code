<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.name" clearable class="filter-item" style="width: 200px;margin-right: 10px" placeholder="请输入角色名称"/>
      <el-button class="filter-item" type="primary" size="small" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button class="filter-item" type="primary" size="small" icon="el-icon-edit" @click="handleCreate">添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>
      <el-table-column align="center" label="角色ID" prop="id" width="120px" sortable/>
      <el-table-column align="center" label="角色名称" prop="name" sortable/>

      <el-table-column align="center" label="说明" prop="desc"/>

      <el-table-column align="center" label="操作" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button  type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button  type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

          <el-button  type="primary" size="mini" @click="handleMenu(scope.row)">菜单权</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :close-on-click-modal="false" v-if="dialogFormVisible" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" status-icon  label-width="100px" >
        <el-form-item label="角色名称" prop="name">
          <el-input style="width: 300px" v-model="dataForm.name"/>
        </el-form-item>
        <el-form-item label="说明" prop="desc">
          <el-input style="width: 300px" v-model="dataForm.desc"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small"  @click="dialogFormVisible = false">取消</el-button>
        <el-button size="small"  v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button size="small"  v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!-- 菜单权限配置对话框 -->
    <el-dialog :close-on-click-modal="false" v-if="menuDialogFormVisible"  :visible.sync="menuDialogFormVisible" title="权限配置">
      <el-tree
        ref="treeMenu"
        :data="systemMenus"
        :default-checked-keys="assignedMenus"
        show-checkbox
        node-key="id"
        style="margin-left: 30px;"
        highlight-current>
        <span slot-scope="{ node, data }" class="custom-tree-node">
          <span>{{ data.name }}</span>
          <el-tag v-if="data.menuId" type="success" size="mini">{{ data.menuId }}</el-tag>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button size="small"  @click="menuDialogFormVisible = false">取消</el-button>
        <el-button size="small"   type="primary" v-loading="saveRoleMenuLoading" @click="updateRoleMenu">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listRole, createRole, updateRole, deleteRole,readRole, getPermission, updatePermission } from '@/api/role'
import { getRoleMenus, updateRoleMenu } from '@/api/menu'
import Pagination from '@/components/Pagination'
export default {
  name: 'Role',
  components: { Pagination },
  data() {
    return {
      saveRoleMenuLoading:false,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined,
        sort: 'add_time desc',

      },
      dataForm: {
        id: undefined,
        name: undefined,
        desc: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        name: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' }
        ]
      },

      menuDialogFormVisible:false,

      systemMenus:null,
      assignedMenus:null,
      menuForm: {
          roleId: undefined,
          menus: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listRole(this.listQuery)
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
        name: undefined,
        desc: undefined
      }
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
          createRole(this.dataForm)
            .then(response => {
              this.list.unshift(response.data.data)
              this.dialogFormVisible = false
              this.$message.success('添加角色成功');

            })
            .catch(response => {
              this.$message.error( '失败:'+response.data.errmsg);
            })
        }
      })
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      readRole(this.dataForm.id).then(
        (response)=>{
          this.dataForm=response.data.data;
        }
      )
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateRole(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              this.dialogFormVisible = false
              this.$message.success('更新管理员成功');

            })
            .catch(response => {
              this.$message.error( '失败:'+response.data.errmsg);
            })
        }
      })
    },
    handleDelete(row) {
      if(confirm('确实要删除当前记录吗?')==false){
        return
      }
      deleteRole(row)
        .then(response => {
          this.$message.success('删除管理员成功');

          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
        })
        .catch(response => {
          this.$message.error( '失败:'+response.data.errmsg);
        })
    },

    handleMenu(row) {
        this.menuDialogFormVisible = true
        this.menuForm.roleId = row.id
        getRoleMenus({ roleId: row.id })
            .then(response => {
                this.systemMenus = response.data.data.systemMenus
                this.assignedMenus = response.data.data.assignedMenus
            })
    },

      getCheckedNodesAndParents() {
          var rad=''
          var ridsa = this.$refs.treeMenu.getCheckedKeys().join(',')// 获取当前的选中的数据[数组] -id, 把数组转换成字符串
          var ridsb = this.$refs.treeMenu.getCheckedNodes()// 获取当前的选中的数据{对象}
          // ridsb.forEach(ids=>{//获取选中的所有的父级id
          //     if(ids.parentId!=0){
          //         rad+=','+ids.parentId
          //     }
          // })
          // rad=rad.substr(1) // 删除字符串前面的','
          // var rids=rad+','+ridsa
          var rids=ridsa
          var arr=rids.split(',')//  把字符串转换成数组
          arr=[...new Set(arr)]; // 数组去重
          //rids=arr.join(',')// 把数组转换成字符串
          console.log(arr)
          return arr;
      },
      updateRoleMenu() {
          //this.menuForm.menus = this.$refs.treeMenu.getCheckedKeys(false)
          this.menuForm.menus = this.getCheckedNodesAndParents()
          this.saveRoleMenuLoading=true;
          updateRoleMenu(this.menuForm)
              .then(response => {
                  this.saveRoleMenuLoading=false
                  this.menuDialogFormVisible = false
                  this.$message.success('菜单功能授权成功');

              })
              .catch(response => {
                  this.saveRoleMenuLoading=false
                  this.$message.error( '失败:'+response.data.errmsg);
              })
      }

  }
}
</script>
