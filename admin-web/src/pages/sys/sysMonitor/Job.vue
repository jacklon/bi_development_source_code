<template>
  <div class="container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleSearch" style="width: 200px;" class="filter-item" placeholder="任务名称" v-model="listQuery.jobName">
      </el-input>

      <el-input clearable style="width: 200px" class="filter-item" v-model="listQuery.jobGroup" placeholder="任务组">
      </el-input>

      <el-select clearable class="filter-item" style="width: 130px" v-model="listQuery.jobStatus" placeholder="状态">
        <el-option v-for="item in  jobStatusOptions" :key="item.key" :label="item.display_name" :value="item.key">
        </el-option>
      </el-select>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">新增</el-button>
    </div>

      <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。"
                style="width: 100%" size="medium"
                stripe border :fit="false" highlight-current-row
          >
      <el-table-column label="操作" width="290px" >
        <template slot-scope="scope">
          <el-button-group>
            <el-button size="mini" type="success" title="编辑" icon="el-icon-edit"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handleEdit(scope.row)"></el-button>
            <el-button :disabled="scope.row.jobStatus!='未启动'" size="mini" type="success" title="启动" icon="el-icon-s-flag"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handleStart(scope.row)"></el-button>
            <el-button :disabled="scope.row.jobStatus=='未启动'" size="mini" type="success" title="立即执行" icon="el-icon-check"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handleExecute(scope.row)"></el-button>
            <el-button :disabled="scope.row.jobStatus!='运行'" size="mini" type="warning" title="暂停" icon="el-icon-close"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handlePause(scope.row)"></el-button>
            <el-button :disabled="scope.row.jobStatus!='暂停'" size="mini" type="primary" title="恢复执行" icon="el-icon-refresh"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handleResume(scope.row)"></el-button>

            <el-button size="mini" type="danger" title="删除" icon="el-icon-delete"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:4px"
                       @click="handleDelete(scope.row)"></el-button>
            <el-button size="mini" type="info" title="日志" icon="el-icon-star-off"
                       style="width: 32px;height: 32px;align-content: center;padding: 1px;margin-right:1px"
                       @click="handleErrorLog(scope.row)"></el-button>
          </el-button-group>

        </template>
      </el-table-column>

      <el-table-column prop="jobName" label="任务名称" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="jobGroup" label="任务组" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.jobGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="springId" label="SpringId" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.springId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cron" label="cron" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.cron }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="methodName" label="执行方法" width="120"></el-table-column>
      <el-table-column prop="jobStatus" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag>{{scope.row.jobStatus}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="任务描述" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page.sync="listQuery.page"
                   :page-sizes="[5,10,15,20,25,30,35,40,45,50,100]"
                   :page-size="listQuery.limit"
                   layout="total, sizes, prev, pager, next, jumper" :total="total" background>
    </el-pagination>

    <el-dialog :title="titleDialog" v-if="showDialogVisible" :visible.sync="showDialogVisible" width="1024px" :close-on-click-modal="false" >
      <el-form :rules="rules" class="small-space" :model="jobModel" ref="jobModel" label-position="right" label-width="120px" >

        <el-col :span="12" >
          <el-form-item label="任务名：" prop="jobName">
            <el-input v-model="jobModel.jobName" style="width: 350px"  ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="任务状态：" prop="jobStatus">
            <el-input v-model="jobModel.jobStatus" :disabled="true" style="width: 350px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="执行类名：" prop="className">
            <el-input v-model="jobModel.className" style="width: 350px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="执行方法：" prop="methodName">
            <el-input v-model="jobModel.methodName" style="width: 350px"  ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="Cron表达式：" prop="cron">
            <el-input v-model="jobModel.cron" style="width: 350px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="任务组：" prop="jobGroup">
            <el-input v-model="jobModel.jobGroup" style="width: 350px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" >
          <el-form-item label="参数列表(JSON数组)：" prop="parameters">
            <el-input type="textarea" rows="3" v-model="jobModel.parameters" style="width: 350px" ></el-input>
          </el-form-item>
        </el-col>


        <el-col :span="12" >
        <el-form-item label="任务描述：" prop="description">
          <el-input type="textarea" rows="3"  v-model="jobModel.description" style="width: 350px" ></el-input>
        </el-form-item>
        </el-col>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showCronDesDialogVisible=true">关天Cron说明</el-button>
        <el-button @click="handleBack">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="handleInstance('jobModel')">确 定</el-button>
        <el-button v-else type="primary" @click="handleInstance">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="'Cron说明'" v-if="showCronDesDialogVisible" :visible.sync="showCronDesDialogVisible"
               :close-on-click-modal="false" width="1200px" >
      <el-input type="textarea" :rows="20"
                v-model="dataHint"
                style="width: 1024px"
      ></el-input>
    </el-dialog>

    <el-dialog :title="'错误列表'" v-if="showErrorLogDialogVisible" :visible.sync="showErrorLogDialogVisible" :close-on-click-modal="false" width="1024px" >
      <el-input type="textarea" :rows="20"
                v-model="jobModel.errorLog"
                style="width: 1024px"
      ></el-input>
    </el-dialog>

  </div>
</template>

<script>
  import { listJob, createJob,startJob, updateJob, deleteJob, executeJob, pauseJob, resumeJob,detailJob } from '@/api/sysQuartzTaskManage'
  export default {
    name: 'Job',
    data () {
      return {
        list: [],
        multipleSelection: [],
        jobStatusOptions: [
          {'key': '暂停', 'display_name': '暂停'},
          {'key': '未启动', 'display_name': '未启动'},
          {'key': '运行', 'display_name': '运行'}],
        importanceOptions: [],
        dialogStatus: 'create',
        showDialogVisible: false,
        showCronDesDialogVisible: false,
        showErrorLogDialogVisible: false,
        show: false,
        total: null,
        titleDialog: '',
        listLoading: true,
        disabled: false,
        jobModel: {
          id:undefined,
          jobName:undefined,
          jobGroup:undefined,
          jobStatus:"未启动",
          cron:'',
          className:'',
          parameters:'',
          methodName:'',
          description:'',
          errorLog:''
        },
        rules: {
          jobName: [
            { required: true, message: '任务名称不能为空', trigger: 'blur' }
          ],
          jobGroup: [
            { required: true, message: '任务组名称不能为空', trigger: 'blur' }
          ],
          cron: [
            { required: true, message: '定时器参数不能为空', trigger: 'blur' }
          ]
        },

        listQuery: {
          page: 1,
          limit: 15,
          jobName: undefined,
          jobGroup: undefined,
          jobStatus: undefined,
          sort: 'add_time desc'
        },
        dataHint:'Quartz里面有一个cron表达式，用来设置定时任务执行的时间\n' +
          '1.Cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每一个域代表一个含义，Cron有如下两种语法格式： \n' +
          'Seconds Minutes Hours DayofMonth Month DayofWeek Year或 Seconds Minutes Hours DayofMonth Month DayofWeek\n' +
          '\n' +
          '2.每一个域可出现的字符如下： \n' +
          'Seconds:可出现, - * /,;四个字符，有效范围为0-59的整数 \n' +
          'Minutes:可出现, - * /&quot;四个字符，有效范围为0-59的整数 \n' +
          'Hours:可出现, - * /&quot;四个字符，有效范围为0-23的整数 \n' +
          'DayofMonth:可出现;, - * / ? L W C&quot;八个字符，有效范围为0-31的整数 \n' +
          'Month:可出现, - * /&quot;四个字符，有效范围为1-12的整数或JAN-DEc \n' +
          'DayofWeek:可出现, - * / ? L C #&quot;四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推 \n' +
          'Year:可出现, - * /&quot;四个字符，有效范围为1970-2099年\n' +
          '每一个域都使用数字，但还可以出现如下特殊字符，它们的含义是：\n' +
          '\n' +
          '(1)*：表示匹配该域的任意值，假如在Minutes域使用*, 即表示每分钟都会触发事件。\n' +
          '(2)?:只能用在DayofMonth和DayofWeek两个域。它也匹配域的任意值，但实际不会。因为DayofMonth和DayofWeek会相互影响。\n' +
          '     例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, \n' +
          '其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。\n' +
          '(3)-: 表示范围，例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次 。\n' +
          '(4)/：表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次.。\n' +
          '(5),: 表示列出枚举值值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。\n' +
          '(6)L: 表示最后，只能出现在DayofWeek和DayofMonth域，如果在DayofWeek域使用5L,意味着在最后的一个星期四触发。\n' +
          '(7)W: 表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在        \n' +
          '      DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；\n' +
          '      如果5日在星期一到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份。\n' +
          '(8)LW: 这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。 \n' +
          '(9)#: 用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2，表示某月的第二个星期三。\n' +
          '\n' +
          '举几个例子: \n' +
          '0 */1 * * * ? 表示每隔1分钟执行一次 \n' +
          '0 0 2 1 * ? * 表示在每月的1日的凌晨2点调度任务 \n' +
          '0 15 10 ? * MON-FRI 表示周一到周五每天上午10：15执行作业 \n' +
          '0 15 10 ? 6L 2002-2006 表示2002-2006年的每个月的最后一个星期五上午10:15执行作\n' +
          '一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。 \n' +
          '按顺序依次为 \n' +
          '秒（0~59） \n' +
          '分钟（0~59） \n' +
          '小时（0~23） \n' +
          '天（月）（0~31，但是你需要考虑你月的天数） \n' +
          '月（0~11） \n' +
          '天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT） \n' +
          '年份（1970－2099\n' +
          '其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于&quot;月份中的日期&quot;和&quot;星期中的日期&quot;这两个元素互斥的,必须要对其中一个设置?\n' +
          '0 0 10,14,16 * * ? 每天上午10点，下午2点，4点 \n' +
          '0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时 \n' +
          '0 0 12 ? * WED 表示每个星期三中午12点 \n' +
          '0 0 12 * * ? 每天中午12点触发 \n' +
          '0 15 10 ? * * 每天上午10:15触发 \n' +
          '0 15 10 * * ? 每天上午10:15触发 \n' +
          '0 15 10 * * ? * 每天上午10:15触发 \n' +
          '0 15 10 * * ? 2005 2005年的每天上午10:15触发 \n' +
          '0 * 14 * * ? 在每天下午2点到下午2:59期间的每1分钟触发 \n' +
          '0 0/5 14 * * ? 在每天下午2点到下午2:55期间的每5分钟触发 \n' +
          '0 0/5 14,18 * * ? 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 \n' +
          '0 0-5 14 * * ? 在每天下午2点到下午2:05期间的每1分钟触发 \n' +
          '0 10,44 14 ? 3 WED 每年三月的星期三的下午2:10和2:44触发 \n' +
          '0 15 10 ? * MON-FRI 周一至周五的上午10:15触发 \n' +
          '0 15 10 15 * ? 每月15日上午10:15触发 \n' +
          '0 15 10 L * ? 每月最后一日的上午10:15触发 \n' +
          '0 15 10 ? * 6L 每月的最后一个星期五上午10:15触发 \n' +
          '0 15 10 ? * 6L 2002-2005 2002年至2005年的每月的最后一个星期五上午10:15触发 \n' +
          '0 15 10 ? * 6#3 每月的第三个星期五上午10:15触发\n' +
          '\n' +
          '3. 有些子表达式能包含一些范围或列表。\n' +
          '例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT”\n' +
          '“*”字符代表所有可能的值\n' +
          '因此，“*”在子表达式（月）里表示每个月的含义，“*”在子表达式（天（星期））表示星期的每一天\n' +
          '\n' +
          '4. “/”字符用来指定数值的增量 \n' +
          '例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟 \n' +
          '在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样\n' +
          '\n' +
          '5. “？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值 \n' +
          '当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”\n' +
          '“L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写，但是它在两个子表达式里的含义是不同的。 \n' +
          '在天（月）子表达式中，“L”表示一个月的最后一天 \n' +
          '在天（星期）自表达式中，“L”表示一个星期的最后一天，也就是SAT\n' +
          '如果在“L”前有具体的内容，它就具有其他的含义了\n' +
          '例如：“6L”表示这个月的倒数第６天，“FRIL”表示这个月的最一个星期五 \n' +
          '注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题。\n'
      }
    },
    filters: {

    },
    created () {
      this.loadList()
    },
    methods: {
      handleErrorLog(row){
        this.jobModel.errorLog=row.errorLog
        this.showErrorLogDialogVisible=true;
      },
      loadList () {
        this.listLoading = true
        listJob(this.listQuery).then(response => {
          this.list = response.data.data.list
          this.total = response.data.data.total
          this.listLoading = false
        }).catch(error => {
          console.log(error)
          this.listLoading = false
        })
      },
      toggleSelection (rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row)
          })
        } else {
          this.$refs.multipleTable.clearSelection()
        }
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      handleEdit (row) {
        this.jobModel = row
        this.showDialogVisible = true
        this.disabled = true
        this.dialogStatus = 'edit'
        this.titleDialog = '编辑'
      },
      handleDelete (row) {
        this.$confirm('您确认要删除此Job任务【' + row.jobName + '】任务吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.jobModel = row
          deleteJob({'id': this.jobModel.id}).then(response => {
            if (response.data.errno === 0) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.loadList()
            } else
            {
              this.$message.error(response.data.errmsg);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      handleCreate () {
        this.titleDialog = '创建'
        this.dialogStatus = 'create'
        this.showDialogVisible = true
        this.disabled = false
        this.jobModel = {jobStatus:"未启动"}
      },
      handleSearch () {
        this.listQuery.page = 1
        this.loadList()
      },
      handlePause (row) {
        this.$confirm('您确认要暂停此Job任务【' + row.jobName + '】任务吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.jobModel = row
          pauseJob({'id': this.jobModel.id}).then(response => {
            if (response.data.errno === 0) {
              this.$message({
                type: 'success',
                message: '暂停成功!'
              })
              this.loadList()
            } else
            {
              this.$message.error(response.data.errmsg)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消暂停·'
          })
        })
      },
      handleResume (row) {
        this.$confirm('您确认要恢复此Job任务【' + row.jobName + '】任务吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.jobModel = row
          resumeJob({'id': this.jobModel.id}).then(response => {
            if (response.data.errno === 0) {
              this.$message({
                type: 'success',
                message: '恢复成功!'
              })
              this.loadList()
            } else
            {
              this.$message.error(response.data.errmsg);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作·'
          })
        })
      },
      handleStart (row) {
        this.$confirm('您确认要启动此Job任务【' + row.jobName + '】吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.jobModel = row
          startJob(this.jobModel).then(response => {
            if (response.data.errno === 0) {
              this.$message({
                type: 'success',
                message: '启动成功!'
              })
              this.loadList()
            } else
            {
              this.$message.error(response.data.errmsg);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作·'
          })
        })
      },

      handleExecute (row) {
        this.$confirm('您确认要立即执行此Job任务【' + row.jobName + '】吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.jobModel = row
          executeJob({'id': this.jobModel.id}).then(response => {
            if (response.data.errno === 0) {
              this.$message({
                type: 'success',
                message: '执行成功!'
              })
              this.loadList()
            } else
            {
              this.$message.error(response.data.errmsg);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作·'
          })
        })
      },
      handleSizeChange (val) {
        this.listQuery.limit = val
        this.loadList()
      },
      handleCurrentChange (val) {
        this.listQuery.page = val
        this.loadList()
      },
      handleInstance () {
        this.$refs['jobModel'].validate(valid => {
          if (!valid) {
             return;
          }
        })
        if (this.dialogStatus === 'create') {
          createJob(this.jobModel).then(
            (res)=>{
              this.jobModel=res.data.data;
              this.showDialogVisible=false;
              this.loadList()
            }
          );

        } else {
          updateJob(this.jobModel).then(
            (res)=>{
              this.jobModel=res.data.data;
              this.showDialogVisible=false;
              this.loadList()
            }
          )

        }
      },
      handleBack () {
        this.showDialogVisible = false
        this.loadList()
      },

    }
  }
</script>
<style scope>
  .container {
    padding: 20px;
  }
  .filter-container {
    padding-bottom: 10px;
    display: flex;
  }
  .filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 10px;
  }
</style>
