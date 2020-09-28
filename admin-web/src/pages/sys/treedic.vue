<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.name" clearable class="filter-item" style="width: 200px;margin-right: 10px" placeholder="请输入代码表名称"/>
      <el-button size="small" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button size="small"  class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
      <el-button size="small" :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download"
                 @click="handleDownload">导出
      </el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="字典表ID" prop="id" sortable/>

      <el-table-column align="center" label="字典表名称" prop="name"/>

      <el-table-column align="center" label="字典表描述" prop="desp"/>

      <el-table-column align="center" label="排序" prop="ordernumber"/>

      <el-table-column align="center" label="是否系统" prop="systemed">
        <template slot-scope="scope">
          <el-tag :type="scope.row.systemed ? 'success' : 'error' ">{{ scope.row.systemed ? '系统' : '用户' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否启用" prop="enabled">
        <template slot-scope="scope">
          <el-tag :type="scope.row.enabled ? 'success' : 'error' ">{{ scope.row.enabled ? '启用' : '不启用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button   type="primary" size="mini" style="width: 80px" @click="handleUpdate(scope.row)">编辑主表</el-button>
          <el-button   type="primary" size="mini" style="width: 80px" @click="handleUpdateChildrenNode(scope.row)">编辑节点
          </el-button>
          <el-button   type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getList"/>

    <!-- 添加或修改代码主表对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :close-on-click-modal="false" v-if="dialogFormVisible" :visible.sync="dialogFormVisible">
       <el-card>
         <el-form ref="dataForm" :rules="rulesMain" :model="dataForm" label-width="120px">
           <el-col :span="12">
             <el-form-item label="字典名称" prop="name">
               <el-input style="width: 200px" v-model="dataForm.name"/>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="字典描述" prop="desp">
               <el-input style="width: 200px" v-model="dataForm.desp"/>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="字典排序值" prop="ordernumber">
               <el-input style="width: 200px" v-model="dataForm.ordernumber"/>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="是否系统" prop="systemed">
               <el-radio-group style="width: 200px" v-model="dataForm.systemed">
                 <el-radio :label="true">系统</el-radio>
                 <el-radio :label="false">用户</el-radio>
               </el-radio-group>
             </el-form-item>
           </el-col>
           <el-col :span="12">
             <el-form-item label="是否启用" prop="enabled">
               <el-radio-group style="width: 200px" v-model="dataForm.enabled">
                 <el-radio :label="true">启用</el-radio>
                 <el-radio :label="false">不启用</el-radio>
               </el-radio-group>
             </el-form-item>
           </el-col>
         </el-form>
       </el-card>
       <div slot="footer" class="dialog-footer">
         <el-button size="small" @click="dialogFormVisible = false">取消</el-button>
         <el-button size="small" v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
         <el-button size="small" v-else type="primary" @click="updateData">确定</el-button>
       </div>
    </el-dialog>

    <!-- 添加或修改代码子表对话框 -->
    <el-dialog :title="'树形代码编辑'" style="padding-top: 0px;padding-bottom: 0px" width="1024px" :close-on-click-modal="false"
               v-if="dialogNodeVisible"  :visible.sync="dialogNodeVisible">
      <el-container>

        <el-aside style="width: 300px;height: 450px">
          <el-card>
            <h3 class="box-title" slot="header" style="padding-top: 0px;padding-bottom: 0px;width: 100%;">
              <el-button size="small" type="primary" icon="plus" @click="newAdd">新增</el-button>
              <el-button size="small" type="danger" icon="delete" @click="batchDelete">批量删除</el-button>
            </h3>
            <el-tree v-if="nodeTree"
                     style="height: 290px"
                     ref="nodeTree"
                     :data="nodeTree"
                     show-checkbox
                     :check-strictly=true
                     highlight-current
                     :render-content="renderContent"
                     @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps"></el-tree>
          </el-card>
        </el-aside>

        <el-main style="margin: 0px;padding: 0px">
          <el-card style="width:620px;height:500px;margin: 0px 20px 0px 20px">
            <el-form :model="dataNodeForm" :rules="rulesNode" ref="dataNodeForm">
              <el-col :span="12">
                <el-form-item label="上级节点" :label-width="formLabelWidth">
                  <!--              <el-cascader :options="list" :props="defaultProps" v-model="orgTypeIds" clearable @change="handleDeptChange"></el-cascader>-->
                  <el-cascader class="filter-item" clearable ref="belongClass" :props="defaultCascadeProps"
                               style="width: 190px;"
                               :options="nodeTree"
                               v-model="dataNodeForm.parentId" expand-trigger="hover" @change="handleParentNodeChange"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.code" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="名称" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.name" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否停用" :label-width="formLabelWidth">
                  <el-radio-group  v-model="dataNodeForm.enabled">
                    <el-radio :label="true">是</el-radio>
                    <el-radio :label="false">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="排序" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.ordernumber" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.remark" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性1" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr1" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性2" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr2" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性3" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr3" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性4" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr4" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性5" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr5" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性6" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr6" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性7" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr7" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性8" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr8" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性9" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr9" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="代码属性10" :label-width="formLabelWidth">
                  <el-input v-model="dataNodeForm.attr10" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
<!--              <el-col :span="12">-->
<!--                <el-form-item label="参与分析" :label-width="formLabelWidth">-->
<!--                  <el-radio-group  v-model="dataNodeForm.ifAnalyze">-->
<!--                    <el-radio :label="true">是</el-radio>-->
<!--                    <el-radio :label="false">否</el-radio>-->
<!--                  </el-radio-group>-->
<!--                </el-form-item>-->
<!--              </el-col>-->
            </el-form>
          </el-card>
            <div class="op-container" style="text-align: center;margin-top: 15px;margin-bottom: 0px">
              <el-button size="small" type="primary" @click="saveTreeNode" v-text="dataNodeForm.id?'修改':'新增'"></el-button>
              <el-button size="small" type="danger" @click="deleteSelected" icon="delete"
                         v-show="dataNodeForm.id && dataNodeForm.id!=null">删除
              </el-button>
            </div>

        </el-main>

      </el-container>


      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button @click="dialogFormVisible = false">取消</el-button>-->
      <!--        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>-->
      <!--        <el-button v-else type="primary" @click="updateData">确定</el-button>-->
      <!--      </div>-->
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

  import {
    listDicTreeMain,
    createDicTreeMain,
    updateDicTreeMain,
    deleteDicTreeMain,
    readDicTreeMain
  } from '@/api/dictreemain'
  import {
    listDicTreeCode,
    createDicTreeCode,
    updateDicTreeCode,
    deleteDicTreeCode,
    readDicTreeCode,
    deleteDicTreeCodeList
  } from '@/api/dictreecode'
  import Pagination from '@/components/Pagination'


  export default {
    name: 'DicTreeMain',
    components: {
      Pagination,
    },
    data() {
      return {
        mainId: undefined,
        mainName: undefined,
        formLabelWidth: '100px',
        defaultProps: {
          children: 'children',
          label: 'name',
          id: "id",
          checkStrictly: true
        },
        defaultCascadeProps: {
          children: 'children',
          label: 'name',
          value: "id",
          checkStrictly: true
        },
        list: [],
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          name: undefined,
          remark: undefined,
          sort: 'ordernumber asc,add_time desc',
          order: 'asc'
        },
        dataForm: {
          id: undefined,
          name: undefined,
          desp: undefined,
          ordernumber: 1,
          systemed: false,
          enabled: true
        },
        nodeTree: [],
        listNodeQuery: {
          mainId: undefined,
          mainName: undefined,
          parentId: undefined,
          remark: undefined,
          id: undefined,
          name: undefined,
          sort: 'ordernumber asc,add_time desc'
        },
        dataNodeForm: {
          id: undefined,
          mainId: undefined,
          mainName: undefined,
          code: undefined,
          name: undefined,
          ordernumber: undefined,
          remark: undefined,
          systemed: undefined,
          enabled: true,
          ifAnalyze: true,
          attr1: undefined,
          attr2: undefined,
          attr3: undefined,
          attr4: undefined,
          attr5: undefined,
          attr6: undefined,
          attr7: undefined,
          attr8: undefined,
          attr9: undefined,
          attr10: undefined,
          level: undefined,
          parentId: undefined,
          ifLeaf: false,
        },
        dialogFormVisible: false,
        dialogNodeVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建'
        },
        rulesMain: {
          name: [
            {required: true, message: '字典表名称不能为空', trigger: 'blur'}
          ]
        },
        rulesNode: {
          name: [
            {required: true, message: '字典项名称不能为空', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '字典项代码不能为空', trigger: 'blur'}
          ]
        },
        downloadLoading: false
      }
    },
    computed: {},
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
      this.getList()
    },
    methods: {
      handleParentNodeChange(value) {
        if (value == null || value.length == 0) {
          this.dataNodeForm.parentId = 0
        } else {
          this.dataNodeForm.parentId = value[value.length - 1]
        }

      },
      getList() {
        this.listLoading = true
        listDicTreeMain(this.listQuery)
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
          desp: undefined,
          ordernumber: 1,
          systemed: false,
          enabled: true
        }
      },
      resetNodeForm() {
        this.dataNodeForm = {
          mainId: undefined,
          mainName: undefined,
          code: undefined,
          name: undefined,
          ordernumber: undefined,
          remark: undefined,
          systemed: undefined,
          enabled: true,
          ifAnalyze: true,
          attr1: undefined,
          attr2: undefined,
          attr3: undefined,
          attr4: undefined,
          attr5: undefined,
          attr6: undefined,
          attr7: undefined,
          attr8: undefined,
          attr9: undefined,
          attr10: undefined,
          level: undefined,
          parentId: undefined,
          ifLeaf: false,
        };
      },
      // handleCreate() {
      //   this.$router.push({ path: '/sys/dicmain/create' })
      // },
      handleCreate() {
        // this.$router.push({ path: '/dicmain/create' })
        this.dataForm = {
          id: undefined,
          name: undefined,
          desp: undefined,
          ordernumber: 1,
          systemed: false,
          enabled: true
        },
          this.dialogStatus = 'create';
        this.mainId = null;
        this.mainName = null;
        this.dialogFormVisible = true


      },
      handleUpdate(row) {
        // this.$router.push({ path: '/sys/dicmain/edit', query: { id: row.id }})
        this.dialogStatus = 'update'
        this.mainId = row.id;
        this.mainName = row.name;
        readDicTreeMain(row.id)
          .then(response => {
            this.dataForm = response.data.data
          })
          .catch(() => {

          })
        this.dialogFormVisible = true
      },
      createData() {
        this.$refs['dataForm'].validate(valid => {
          if (valid) {
            createDicTreeMain(this.dataForm)
              .then(response => {
                this.list.unshift(response.data.data)
                this.dialogFormVisible = false
                this.$message.success('创建成功');
                // this.$notify.success({
                //   title: '成功',
                //   message: '创建成功'
                // })
              })
              .catch(response => {
                this.$message.error('失败:' + response.data.errmsg)
                // this.$notify.error({
                //   title: '失败',
                //   message: response.data.errmsg
                // })
              })
          }
        })
      },
      updateData() {
        this.$refs['dataForm'].validate(valid => {
          if (valid) {
            updateDicTreeMain(this.dataForm)
              .then(() => {
                for (const v of this.list) {
                  if (v.id === this.dataForm.id) {
                    const index = this.list.indexOf(v)
                    this.list.splice(index, 1, this.dataForm)
                    break
                  }
                }
                this.dialogFormVisible = false
                this.$message.success('更新代码表成功');

              })
              .catch(response => {
                this.$message.error('失败:' + response.data.errmsg);
              })
          }
        })
      },
      handleDelete(row) {
        if (confirm('确实要删除当前记录吗?') == false) {
          return
        }
        deleteDicTreeMain(row)
          .then(response => {
            this.$message.success('删除成功');

            const index = this.list.indexOf(row)
            this.list.splice(index, 1)
          })
          .catch(response => {
            this.$message.error('失败:' + response.data.errmsg);
          })
      },
      handleDownload() {
        this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = [
            '字典表ID',
            '字典表名称',
            '描述',
            '排序',
            '是否系统',
            '是否启用'
          ]
          const filterVal = [
            'id',
            'name',
            'desp',
            'ordernumber',
            'systemed',
            'enabled'
          ]
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '字典表信息')
          this.downloadLoading = false
        })
      },

      // ******************************************
      //编辑数形子节点
      renderContent(h, {node, data, store}) {
        return (
          < span >
          < span >
          < span > {node.label} < /span>
          < /span>
          < /span>);
      },
      newAdd() {
        //将当前节点做为父节点
        let parentId;
        if (this.dataNodeForm.id != null && this.dataNodeForm.id != undefined) {
          parentId = this.dataNodeForm.id;
        }
        this.resetNodeForm();
        this.dataNodeForm.parentId = parentId;
      },
      deleteSelected() {
        if (this.dataNodeForm.id == null) {
          this.dataNodeForm = {
            mainId: undefined,
            mainName: undefined,
            code: undefined,
            name: undefined,
            ordernumber: undefined,
            remark: undefined,
            systemed: undefined,
            enabled: true,
            ifAnalyze: true,
            attr1: undefined,
            attr2: undefined,
            attr3: undefined,
            attr4: undefined,
            attr5: undefined,
            attr6: undefined,
            level: undefined,
            parentId: undefined,
            ifLeaf: false,
          };
        } else {
          if (confirm("确认要删除吗?") == false) {
            return
          }
          deleteDicTreeCode(this.dataNodeForm)
            .then(response => {
              this.loadDicTreeCode();
              this.$message.success('删除成功');

            })
            .catch(response => {
              this.$message.error('失败:' + response.data.errmsg);
            })
        }

      },
      batchDelete() {
        var checkKeys = this.$refs.nodeTree.getCheckedKeys();
        if (checkKeys == null || checkKeys.length <= 0) {
          this.$message.warning('请选择要删除的菜单');
          return;
        }
        this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteDicTreeCodeList(checkKeys)
            .then(response => {
              this.loadDicTreeCode();
              this.$message.success('删除成功');

            })
            .catch(response => {
              this.$message.error('失败:' + response.data.errmsg);
            })

          // this.batchDeleteFromTree(this.menuTree, checkKeys);
        });

      },
      handleUpdateChildrenNode(mainRow) {
        this.mainId = mainRow.id;
        this.mainName = mainRow.name;
        this.dialogNodeVisible = true;

        this.loadDicTreeCode();
      },
      handleNodeClick(data) {
        // this.dataNodeForm = merge({}, data);
        readDicTreeCode(data.id).then(res => {
          this.dataNodeForm = res.data.data
        })
      },
      saveTreeNode() {
        this.dataNodeForm.mainId = this.mainId;
        this.dataNodeForm.mainName = this.mainName;
        if (this.dataNodeForm.id == null) {
          createDicTreeCode(this.dataNodeForm)
            .then(response => {
              this.loadDicTreeCode();
              this.$message.success('创建成功');
              // this.dialogNodeVisible=false
              this.resetNodeForm()
            })
            .catch(response => {
              this.$message.error('失败:' + response.data.errmsg);
            })
        } else {
          if(this.dataNodeForm.id==this.dataNodeForm.parentId){
            return this.$message.error('树形结点不能挂到自身');
          }
          updateDicTreeCode(this.dataNodeForm)
            .then(response => {
              this.loadDicTreeCode();
              this.$message.success('修改成功');
              // this.dialogNodeVisible=false
            })
            .catch(response => {
              this.$message.error('失败:' + response.data.errmsg);
            })
        }
      },
      loadDicTreeCode() {
        listDicTreeCode({mainId: this.mainId}).then(response => {
          this.nodeTree = response.data.data.list
        })
      },

    }
  }
</script>
<stle>

</stle>
