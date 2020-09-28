<template>
  <div class="page app-container">
    <el-container>

      <el-aside style="width: 300px;">
        <el-card style="background: rgb(23,27,34);" >
<!--          <h3 class="box-title" slot="header" style="width: 100%;margin-bottom: 10px">-->
<!--            <el-button type="primary" size="mini"   @click="load">刷新菜单</el-button>-->
<!--          </h3>-->
          <h3 class="box-title" slot="header" style="width: 100%;">
            <el-button type="primary" size="mini"   @click="newAdd">新增菜单</el-button>
            <el-button type="danger" size="mini"   @click="batchDelete">批量删除</el-button>
          </h3>
          <el-tree style="background: rgb(28, 34, 43);color: #bcc9d4;"  v-if="menuTree"
                   ref="menuTree"
                   :data="menuTree"
                   show-checkbox
                   :check-strictly=false
                   highlight-current
                   :render-content="renderContent"
                   @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps"></el-tree>

<!--          <y-tree  ref="lKTree" v-if="menuTree" :data="menuTree"></y-tree>-->

        </el-card>
      </el-aside>
      <el-main style="margin: 0px;padding: 0px;margin-left: 20px; ">
        <y-form-item title="上级节点"
                       :width="120"
                       height="auto"
                       align="topToBottom"
                       :staticTitle = "true" >
          <y-cascader :options="menuTree" v-model="form.parentId" @change="handleParentNodeChange"
                      :props="defaultCascadeProps" ></y-cascader>

        </y-form-item>
        <y-form-item   title="代码"
                       :width="80"
                       height="auto"
                       align="topToBottom"
                       :ifMustInput="true"
                       :staticTitle = "true" >
          <y-input v-model="form.code"></y-input>
        </y-form-item>
        <y-form-item  title="路径"
                       :width="80"
                       height="auto"
                       align="topToBottom"
                      :ifMustInput="false"
                       :staticTitle = "true" >
          <y-input v-model="form.path"></y-input>
        </y-form-item>
        <y-form-item   title="名称"
                       :width="80"
                       height="auto"
                       align="topToBottom"
                       :ifMustInput="true"
                       :staticTitle = "true" >
          <y-input v-model="form.name"></y-input>
        </y-form-item>


        <y-form-item   title="排序"
                       :width="80"
                       height="auto"
                       align="topToBottom"
                       :staticTitle = "true" >
          <y-input v-model="form.sort"></y-input>
        </y-form-item>
        <y-form-item   title="备注"
                       :width="80"
                       height="auto"
                       align="topToBottom"
                       :staticTitle = "true" >
          <y-input v-model="form.remarks"></y-input>
        </y-form-item>

        <y-form-item   title="图标"
                       :width="80"
                       height="auto"

                       :staticTitle = "true" >
          <i :class="form.icon" v-model="form.icon">
            <el-button type="text" @click="selectIconDialog=true">选择</el-button>
            <el-button type="text" @click="clearIcon">清空</el-button>
          </i>
        </y-form-item>

        <div class="project-list_confirm marginT20">
          <el-button type="primary" size="medium"  @click="onSubmit" v-text="form.id?'修改':'新增'"></el-button>
          <el-button type="danger" size="medium"   @click="deleteSelected" icon="delete" v-show="form.id && form.id!=null">删除
          </el-button>
        </div>


      </el-main>
    </el-container>

      <el-dialog title="选择图标" v-if="selectIconDialog" :visible.sync="selectIconDialog"  :close-on-click-modal="false"  size="tiny">
        <div class="select-tree">
          <el-scrollbar
            tag="div"
            class='is-empty'
            wrap-class="el-select-dropdown__wrap"
            view-class="el-select-dropdown__list">

            <div class="icons-wrapper">
              <i class="el-icon-info" @click="selectIcon($event)"></i>
              <i class="el-icon-error" @click="selectIcon($event)"></i>
              <i class="el-icon-success" @click="selectIcon($event)"></i>
              <i class="el-icon-warning" @click="selectIcon($event)"></i>
              <i class="el-icon-question" @click="selectIcon($event)"></i>
              <i class="el-icon-back" @click="selectIcon($event)"></i>
              <i class="el-icon-arrow-left" @click="selectIcon($event)"></i>
              <i class="el-icon-arrow-down" @click="selectIcon($event)"></i>
              <i class="el-icon-arrow-right" @click="selectIcon($event)"></i>
              <i class="el-icon-arrow-up" @click="selectIcon($event)"></i>
              <i class="el-icon-caret-left" @click="selectIcon($event)"></i>
              <i class="el-icon-caret-bottom" @click="selectIcon($event)"></i>
              <i class="el-icon-caret-top" @click="selectIcon($event)"></i>
              <i class="el-icon-caret-right" @click="selectIcon($event)"></i>
              <i class="el-icon-d-arrow-left" @click="selectIcon($event)"></i>
              <i class="el-icon-d-arrow-right" @click="selectIcon($event)"></i>
              <i class="el-icon-minus" @click="selectIcon($event)"></i>
              <i class="el-icon-plus" @click="selectIcon($event)"></i>
              <i class="el-icon-remove" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-plus" @click="selectIcon($event)"></i>
              <i class="el-icon-remove-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-plus-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-close" @click="selectIcon($event)"></i>
              <i class="el-icon-check" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-close" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-check" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-close-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-circle-check-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-zoom-out" @click="selectIcon($event)"></i>
              <i class="el-icon-zoom-in" @click="selectIcon($event)"></i>
              <i class="el-icon-d-caret" @click="selectIcon($event)"></i>
              <i class="el-icon-sort" @click="selectIcon($event)"></i>
              <i class="el-icon-sort-down" @click="selectIcon($event)"></i>
              <i class="el-icon-sort-up" @click="selectIcon($event)"></i>
              <i class="el-icon-tickets" @click="selectIcon($event)"></i>
              <i class="el-icon-document" @click="selectIcon($event)"></i>
              <i class="el-icon-goods" @click="selectIcon($event)"></i>
              <i class="el-icon-sold-out" @click="selectIcon($event)"></i>
              <i class="el-icon-news" @click="selectIcon($event)"></i>
              <i class="el-icon-message" @click="selectIcon($event)"></i>
              <i class="el-icon-date" @click="selectIcon($event)"></i>
              <i class="el-icon-printer" @click="selectIcon($event)"></i>
              <i class="el-icon-time" @click="selectIcon($event)"></i>
              <i class="el-icon-bell" @click="selectIcon($event)"></i>
              <i class="el-icon-mobile-phone" @click="selectIcon($event)"></i>
              <i class="el-icon-" @click="selectIcon($event)"></i>
              <i class="el-icon-view" @click="selectIcon($event)"></i>
              <i class="el-icon-menu" @click="selectIcon($event)"></i>
              <i class="el-icon-more" @click="selectIcon($event)"></i>
              <i class="el-icon-more-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-star-on" @click="selectIcon($event)"></i>
              <i class="el-icon-star-off" @click="selectIcon($event)"></i>
              <i class="el-icon-location" @click="selectIcon($event)"></i>
              <i class="el-icon-location-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-phone" @click="selectIcon($event)"></i>
              <i class="el-icon-phone-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-picture" @click="selectIcon($event)"></i>
              <i class="el-icon-picture-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-delete" @click="selectIcon($event)"></i>
              <i class="el-icon-search" @click="selectIcon($event)"></i>
              <i class="el-icon-edit" @click="selectIcon($event)"></i>
              <i class="el-icon-edit-outline" @click="selectIcon($event)"></i>
              <i class="el-icon-rank" @click="selectIcon($event)"></i>
              <i class="el-icon-refresh" @click="selectIcon($event)"></i>
              <i class="el-icon-share" @click="selectIcon($event)"></i>
              <i class="el-icon-setting" @click="selectIcon($event)"></i>
              <i class="el-icon-upload" @click="selectIcon($event)"></i>
              <i class="el-icon-upload2" @click="selectIcon($event)"></i>
              <i class="el-icon-download" @click="selectIcon($event)"></i>
              <i class="el-icon-loading" @click="selectIcon($event)"></i>
              <i class="el-icon--right" @click="selectIcon($event)"></i>
              <i class="el-icon--left" @click="selectIcon($event)"></i>
            </div>
          </el-scrollbar>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="selectIconDialog = false">取 消</el-button>
          <el-button type="primary" @click="selectIconDialog = false">确 定</el-button>
          </span>
      </el-dialog>

  </div>
</template>
<script type="text/babel">
  import merge from 'element-ui/src/utils/merge';
  import { listMenu, createMenu, updateMenu, deleteMenu,deleteMenulist,readMenu,batchAddMenu} from '@/api/menu'
  import { asyncRouterMap } from '@/router'
  import YTree from "../../../base/tree/src/tree";
  // import YCascader from "../../../base/cascader/src/cascader";
  export default {
    name: 'menuSet',
    components: {
      // YCascader,
       YTree

    },
    data(){
      return {
        loading:false,
        selectIconDialog: false,
        formLabelWidth: '150px',
        defaultProps: {
          children: 'children',
          label: 'name',
          id: "id",
        },
        defaultCascadeProps: {
            children: 'children',
            label: 'name',
            value: "id",
            checkStrictly: true
        },
        menuTree: [],
        form: {
          id: null,
          code:'',
          name: '',
          sort: null,
          icon: '',
          href: '',
          path: '',
          component: '',
          isShow: true,
          parentId: 0,
          remarks: '',
        },
        tableDespList:[],
        definFormList:[],
        asyncRouterMap:asyncRouterMap,//路由菜单

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
    created(){
      this.load();

    },
    methods: {
      clearIcon(){
        this.form.icon=''
      },
      changeMenuCode(val){
        this.form.href=val

      },

      changeTableOrView($event){
        var result = this.tableDespList.filter(item => {
            return item.id == $event
          }
        );
        if (result.length <= 0) {
          return false;
        }
        this.form.oltTableName=result[0].oltDbTalbeViewName;
        this.form.oltTableDesp=result[0].oltTableDesp;
      },

      handleParentNodeChange(value) {
          if(value==null||value.length==0){
            this.form.parentId =0
            return;
          }
          if( value[value.length - 1]==this.form.id){

            readMenu(this.form.id).then(res=>{
              this.form=res.data.data
            })
            this.$message.error("自己不能关联自己");
            return;
          }

          this.form.parentId = value[value.length - 1]

      },
      selectIcon(event){
        this.form.icon = event.target.className;
        this.selectIconDialog = false;
      },
      renderContent(h, { node, data, store }) {
        return (
          <span>
            <span>
              <span><i class={data.icon}></i>&nbsp;{node.label}</span>
            </span>
          </span>);
      },
      newAdd(){
        //将当前节点做为父节点
        let parentId;
        if (this.form.id != null && this.form.id != undefined) {
          parentId = this.form.id;
        }
        this.resetForm();
        this.form.parentId = parentId;
      },
      resetForm(){
          this.form = {
              id: null,
              code:'',
              name: '',
              sort: null,
              icon: '',
              href: '',
              isShow: true,
              ifDisplayFlowStartName:false,
              parentId: 0,
              remarks: ''
          };
      },
      deleteSelected(){
          if(this.form.id==null){
              this.form = {
                  id: null,
                  code:'',
                  name: '',
                  sort: null,
                  icon: '',
                  href: '',
                  isShow: true,
                  ifDisplayFlowStartName:false,
                  parentId: 0,
                  remarks: ''
              };
          } else
          {
              deleteMenu(this.form)
                  .then(response => {
                      this.load();
                      this.$message.success('删除成功');

                  })
                  .catch(response => {
                      this.$message.error( '失败:'+response.data.errmsg);
                  })
          }

      },
      batchDelete(){
        var checkKeys = this.$refs.menuTree.getCheckedKeys();
        if (checkKeys == null || checkKeys.length <= 0) {
          this.$message.warning('请选择要删除的菜单');
          return;
        }
        this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            deleteMenulist(checkKeys)
                .then(response => {
                    this.load();
                    this.$message.success('删除成功');

                })
                .catch(response => {
                    this.$message.error( '失败:'+response.data.errmsg);
                })

          });

      },
      handleNodeClick(data){
        this.form = merge({}, data);
          readMenu(this.form.id).then(res=>{
              this.form=res.data.data
          })
      },
      onSubmit(){
        if(this.form.parentId==undefined){
          this.$message.error("请选择父级");
          return;
        }

        if (this.form.id == null) {
            createMenu(this.form)
                .then(response => {
                    this.load();
                    this.$message.success('创建成功');
                    this.newAdd();
                })
                .catch(response => {
                    this.$message.error( '失败:'+response.data.errmsg);
                })
        } else
        {
            updateMenu(this.form)
                .then(response => {
                    this.load();
                    this.$message.success('修改成功');

                })
                .catch(response => {
                    this.$message.error( '失败:'+response.data.errmsg);
                })
        }
      },
      load(){
        listMenu().then(response => {
            this.menuTree = response.data.data.list
        }).catch(error=>{
            this.$message.error(error)
        })
      }
    },
  }
</script>

<style>


  .select-tree .icons-wrapper {
    display: block;
  }

  .select-tree .is-empty i {
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
  }

  .select-tree .is-empty i:hover {
    /*background-color: #0d6aad;*/
    /*color: #ffffff;*/
  }
  .el-cascader-panel .el-radio{
    width: 200px;
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
