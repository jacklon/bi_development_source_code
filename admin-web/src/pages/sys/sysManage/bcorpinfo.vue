<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.name" clearable class="filter-item" style="width: 200px;margin-right: 10px" placeholder="请输入组织名称"/>
<!--      <y-input v-model="listQuery.name" width="200" placeholder="请输入组织名称"></y-input>-->
      <el-button  class="filter-item" type="primary" size="small" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button  class="filter-item" type="primary" size="small" icon="el-icon-edit" @click="handleCreate">添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。"
              border fit highlight-current-row row-key="id" class="el-table" >

      <el-table-column align="center" label="组织ID" prop="id" sortable/>

      <el-table-column align="center" label="组织名称" width="150px" prop="name" />

      <el-table-column align="left" label="组织类型" prop="citName" />

      <el-table-column align="left" label="部门属性" prop="depTypeName" />


      <el-table-column align="center" label="排序" width="80px" prop="ordernumber" sortable/>

      <el-table-column align="center" label="是否停用" prop="enabled">
        <template slot-scope="scope">
          <el-tag :type="scope.row.enabled ? 'success' : 'error' ">{{ scope.row.enabled ? '停用' : '正常' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="left" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button  type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button  type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" customClass="customWidth" :close-on-click-modal="false"
               v-if="dialogFormVisible" :visible.sync="dialogFormVisible" style="margin-top:10px;">
      <el-card >

        <el-form ref="dataForm" :rules="rules" :model="dataForm" status-icon  label-width="100px"  >

          <el-col :span="12"  >
            <el-form-item label="上级组织" prop="pid">
              <el-cascader class="filter-item" clearable ref="belongClass"
                           :props="defaultProps"  style="width: 200px;"  :options="allDepList"
                           v-model="dataForm.pid" expand-trigger="hover" @change="handleDeptChange"/>
            </el-form-item>
          </el-col>

          <el-col :span="12"  >
            <el-form-item label="组织名称" prop="name">
              <el-input style="width: 200px" v-model="dataForm.name"/>
            </el-form-item>

           </el-col>
          <el-col :span="12"  >
            <el-form-item label="组织类型" prop="citId">
              <el-select clearable  class="filter-item" filterable  v-model="dataForm.citId"
                         style="width:200px;" @change="changeOrgType(dataForm.citId)"  >
                <el-option  v-for=" item in orgTypeList" :value="item.id"  :key="item.id" :label="item.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"  >
            <el-form-item label="部门属性" prop="depTypeId">
              <el-select clearable  class="filter-item" filterable  v-model="dataForm.depTypeId" style="width:200px;" @change="changeDepType(dataForm.depTypeId)"  >
                <el-option  v-for=" item in deptTypeList" :value="item.id"  :key="item.id" :label="item.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"  >
            <el-form-item label="负责人" prop="chargePerson">
              <el-input style="width: 200px" v-model="dataForm.chargePerson"/>
            </el-form-item>
          </el-col>
          <el-col :span="12"  >
            <el-form-item label="核算编码" prop="cwCode">
              <el-input style="width: 200px" v-model="dataForm.cwCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="12"  >
            <el-form-item label="排序" prop="ordernumber">
              <el-input style="width: 200px" v-model="dataForm.ordernumber"/>
            </el-form-item>
          </el-col>
        <el-col :span="24"  >
            <el-form-item label="组织简介" prop="ordernumber">
                <el-input type="textarea" :rows="5"  style="width: 600px" v-model="dataForm.desc"/>
            </el-form-item>
        </el-col>
<!--          <el-col :span="24" style="height: 300px"  >-->
<!--            <el-form-item label="公司简介">-->
<!--              <editor style="height: 150px" :init="editorInit" v-model="dataForm.desc"/>-->
<!--            </el-form-item>-->
<!--          </el-col>-->


        </el-form>
      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="dialogFormVisible = false">取消</el-button>
        <el-button  v-if="dialogStatus=='create'" type="primary" size="mini" @click="createData">确定</el-button>
        <el-button v-else type="primary" size="mini" @click="updateData">确定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<style>
.el-dialog__body {
  padding: 0px 0px;
  color: #606266;
  font-size: 14px;
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
.hot-header{
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
.icon {
  margin-left: 8px;
}
.customWidth {
  width: 850px;
}
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

<script>
import { listBCorpInfo,listAllBCorpInfo, createBCorpInfo,
  updateBCorpInfo, deleteBCorpInfo,readBCorpInfo,dataTransNewDept} from '@/api/bcorpinfo'
import { createStorage, uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { listDiccode } from '@/api/diccode'


export default {
  name: 'Company',
  components: { Editor },
  data() {
    return {

      uploadPath,
      allDepList:[],
      deptList: [],
      list: [],
      total: 0,
      listLoading: true,
      orgTypeList:[],
      deptTypeList:[],
      pidList:[],
      defaultProps: {
          children: 'children',
          label: 'name',
          value: "id",
          checkStrictly: true
      },
      listQuery: {
        name: undefined,
        sort: 'add_time desc',
      },

      dataForm: {
        id: undefined,
        citId: undefined,
        citName: undefined,
        depTypeId: undefined,
        depTypeName: undefined,
        chargePerson: undefined,
        cwCode: undefined,
        name: undefined,
        desc: undefined,
        pid: undefined,
        deptIdString: [],
        ifLeaf: undefined,
        enabled: undefined,
        level: undefined,
        ordernumber: undefined,
      },
      dataTransForm:{
        sourceDeptId:undefined,
        sourceDeptName:undefined,
        targetDeptId:undefined,
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        name: [
          { required: true, message: '组织名称不能为空', trigger: 'blur' }
        ]

      },
      editorInit: {
          language: 'zh_CN',
          convert_urls: false,
          plugins: [
              'advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'
          ],
          toolbar: [
              'searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample',
              'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'
          ],
          images_upload_handler: function(blobInfo, success, failure) {
              const formData = new FormData()
              formData.append('file', blobInfo.blob())
              createStorage(formData)
                  .then(res => {
                      success(res.data.data.url)
                  })
                  .catch(() => {
                      failure('上传失败，请重新上传')
                  })
          }
      }
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
    this.listAllBCorpInfo()
    this.getDeptList();
    this.getOrgTypeList();
    this.getDeptTypeList();
  },
  computed: {
      headers() {
          return {
              'X-Litemall-Admin-Token': getToken()
          }
      }
  },
  methods: {

      getDeptList() {
        listAllBCorpInfo()
          .then(response => {
            this.deptList = response.data.data.list

          })
          .catch(() => {

          })
      },



      changeOrgType($event){
        var result = this.orgTypeList.filter(
            item =>{
                return  item.id==$event
            }
        )
        if (result.length <= 0) {
            return false;
        }
        this.dataForm.citName=result[0].name;
    },
    changeDepType($event){
        var result = this.deptTypeList.filter(
            item =>{
                return  item.id==$event
            }
        )
        if (result.length <= 0) {
            return false;
        }
        this.dataForm.depTypeName=result[0].name;
    },
    getOrgTypeList(){
        listDiccode({dicmainName:"系统_组织机构类型"}).then(response => {
            this.orgTypeList = response.data.data.list
        }).catch(() => {
            this.orgTypeList = []
        })
    },
    getDeptTypeList(){
        listDiccode({dicmainName:"系统_部门属性"}).then(response => {
            this.deptTypeList = response.data.data.list
        }).catch(() => {
            this.deptTypeList = []
        })
    },
    getList() {
      this.listLoading = true
      this.list=[]
      this.total=0,
      listBCorpInfo(this.listQuery)
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
    listAllBCorpInfo() {
      this.listLoading = true
       let allListQuery={
          name: undefined,
          sort: 'add_time desc',
        }
       listAllBCorpInfo(allListQuery)
        .then(response => {
          this.list = response.data.data.list
          this.allDepList=response.data.data.list
          this.total = response.data.data.total
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.allDepList=[]
          this.total = 0
          this.listLoading = false
        })
    },
    handleFilter() {
      this.listQuery.page = 1
      if(this.listQuery.name==undefined||this.listQuery.name==''){
        this.listAllBCorpInfo()
      } else
      {
        this.getList()
      }

    },
    resetForm() {
      this.dataForm = {
          id: undefined,
          citId: undefined,
          citName: undefined,
          depTypeId: undefined,
          depTypeName: undefined,
          chargePerson: undefined,
          cwCode: undefined,
          name: undefined,
          desc: undefined,
          pid: undefined,
          deptIdString: [],
          ifLeaf: undefined,
          enabled: undefined,
          level: undefined,
          ordernumber: undefined,
      };

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
          createBCorpInfo(this.dataForm)
            .then(response => {
              this.dialogFormVisible = false
              this.$message.success('创建成功');
              this.listAllBCorpInfo();
              // this.getList();
            })
            .catch(response => {
              this.$message.error( '失败:'+response.data.errmsg);
            })
        }
      })
    },


    handleUpdate(row) {
      // this.dataForm = Object.assign({}, row)
      this.resetForm()
      readBCorpInfo(row.id)
      .then((response) => {
          this.dataForm =response.data.data;
      })
      .catch(response => {

      }),
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {

          updateBCorpInfo(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              this.dialogFormVisible = false
              this.$message.success('更新公司成功');

            })
            .catch(response => {
              this.$message.error( '失败:'+response.data.errmsg);
            })
        }
      })
    },
    handleDelete(row) {

      if(confirm("确实要删除该记录吗？")==false){
        return;
      }
      deleteBCorpInfo(row)
        .then(response => {
          this.$message.success('删除成功');
          this.listAllBCorpInfo()

        })
        .catch(response => {
          this.$message.error( '失败:'+response.data.errmsg);
        })
    },

    handleDeptChange(value) {
      if(value==null||value.length==0){
        this.dataForm.pid =undefined
        this.dataForm.deptIdString = []
        return;
      }
      this.dataForm.pid = value[value.length - 1]

      this.dataForm.deptIdString = []
      value.forEach(item => {
        this.dataForm.deptIdString.push(item.toString())
      })

    },

    handleNewDeptChange(value) {
      if(value==null||value.length==0){
        this.dataTransForm.targetDeptId =undefined
        return;
      }
      this.dataTransForm.targetDeptId = value[value.length - 1]
    },
  }
}
</script>
