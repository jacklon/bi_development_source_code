<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input
                    v-model="listQuery.datasourceName"
                    clearable
                    placeholder="数据源名称"
                    style="width: 200px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter"
            />
            <el-button size="small" v-waves class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="fetchData">
                搜索
            </el-button>
            <el-button size="small" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
                添加
            </el-button>
            <!-- <el-checkbox v-model="showReviewer" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">
              reviewer
            </el-checkbox> -->
        </div>
        <el-table
                v-loading="listLoading"
                :data="list"
                element-loading-text="Loading"
                border
                fit
                highlight-current-row>
            <!-- <el-table-column align="center" label="序号" width="95">
              <template slot-scope="scope">{{ scope.$index }}</template>
            </el-table-column> -->
            <el-table-column label="数据源" width="200" align="center">
                <template slot-scope="scope">{{ scope.row.datasource }}</template>
            </el-table-column>
            <el-table-column label="数据源名称" width="150" align="center">
                <template slot-scope="scope">{{ scope.row.datasourceName }}</template>
            </el-table-column>
<!--            <el-table-column label="数据源分组" width="200" align="center">-->
<!--                <template slot-scope="scope">{{ scope.row.datasourceGroup }}-->
<!--                </template>-->
<!--            </el-table-column>-->
            <!--<el-table-column label="用户名" width="150" align="center">
              <template slot-scope="scope">{{ scope.row.jdbcUsername ? scope.row.jdbcUsername:'-' }}</template>
            </el-table-column>-->
            <el-table-column label="jdbc连接串" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{ scope.row.jdbcUrl ? scope.row.jdbcUrl:'-' }}</template>
            </el-table-column>
            <!-- <el-table-column label="jdbc驱动类" width="200" align="center" :show-overflow-tooltip="true">
              <template slot-scope="scope">{{ scope.row.jdbcDriverClass ? scope.row.jdbcDriverClass:'-' }}</template>
            </el-table-column>-->
<!--            <el-table-column label="ZK地址" width="200" align="center" :show-overflow-tooltip="true">-->
<!--                <template slot-scope="scope">{{ scope.row.zkAdress ? scope.row.zkAdress:'-' }}</template>-->
<!--            </el-table-column>-->
            <el-table-column label="数据库名" width="100" align="center" :show-overflow-tooltip="true">-->
                <template slot-scope="scope">{{ scope.row.databaseName ? scope.row.databaseName:'-' }}</template>-->
            </el-table-column>
<!--            <el-table-column label="备注" width="150" align="center">-->
<!--                <template slot-scope="scope">{{ scope.row.comments }}</template>-->
<!--            </el-table-column>-->
            <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
                <template slot-scope="{row}">
                    <el-button type="primary" size="mini" @click="handleUpdate(row)">
                        编辑
                    </el-button>
                    <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination
                v-show="total>0"
                :total="total"
                :page.sync="listQuery.current"
                :limit.sync="listQuery.size"
                @pagination="fetchData"/>

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="800px">
            <el-form ref="dataForm" :rules="rules" :model="dataConnection" label-position="right" label-width="100px">
                <el-form-item label="数据源" prop="datasource">
                    <el-select
                            v-model="dataConnection.datasource"


                            @change="selectDataSource(dataConnection.datasource)">
                        <el-option v-for="item in dataSources" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="数据源名称" prop="datasourceName">
                    <el-input v-model="dataConnection.datasourceName"  />
                </el-form-item>
<!--                <el-form-item label="数据源分组" prop="datasourceGroup">-->
<!--                    <el-input v-model="dataConnection.datasourceGroup" placeholder="数据源分组" style="width: 40%" />-->
<!--                </el-form-item>-->
                <el-form-item v-if="jdbc" label="用户名">
                    <el-input v-model="dataConnection.jdbcUsername"  />
                </el-form-item>
                <el-form-item v-if="visible" v-show="jdbc" label="密码">
                    <el-input v-model="dataConnection.jdbcPassword" type="password" >
                        <i slot="suffix" title="显示密码" style="cursor:pointer" class="el-icon-view" @click="changePass('show')" />
                    </el-input>
                </el-form-item>
                <el-form-item v-show="jdbc" v-else label="密码">
                    <el-input v-model="dataConnection.jdbcPassword" type="text" placeholder="密码" style="width: 40%">
                        <i slot="suffix" title="隐藏密码" style="cursor:pointer" class="el-icon-check" @click="changePass('hide')" />
                    </el-input>
                </el-form-item>
                <el-form-item v-if="jdbc" label="jdbc url" prop="jdbcUrl">
                    <el-input
                            v-model="dataConnection.jdbcUrl"
                    />
                </el-form-item>
                <el-form-item v-if="mongodb" label="地址" prop="jdbcUrl">
                    <el-input
                            v-model="dataConnection.jdbcUrl"
                            :autosize="{ minRows: 1, maxRows: 6}"
                            type="textarea"
                            placeholder="127.0.0.1:27017"
                            style="width: 60%"
                    />
                </el-form-item>
                <el-form-item v-if="jdbc" label="jdbc驱动类" prop="jdbcDriverClass">
                    <el-input v-model="dataConnection.jdbcDriverClass"   />
                </el-form-item>
                <el-form-item v-if="hbase" label="ZK地址" prop="zkAdress">
                    <el-input v-model="dataConnection.zkAdress" placeholder="127.0.0.1:2181"   />
                </el-form-item>
                <el-form-item v-if="mongodb" label="数据库名称" prop="databaseName">
                    <el-input v-model="dataConnection.databaseName"   />
                </el-form-item>
                <el-form-item label="注释">
                    <el-input v-model="dataConnection.comments"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogFormVisible = false">
                    取消
                </el-button>
                <el-button size="small" type="primary" @click="dialogStatus==='create'?createData():updateData()">
                    确认
                </el-button>
                <el-button size="small" type="primary" @click="testDataSource()">
                    测试连接
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import {listDataConnection,updateDataConnection,createDataConnection,
        deleteDataConnection,testDataConnection,getAllDataConnection} from '@/api/dataConnection'
    import waves from '@/directive/waves' // waves directive
    import Pagination from '@/components/Pagination'

    export default {
        name: 'DataConnnection',
        components: { Pagination },
        directives: { waves },
        filters: {
            statusFilter(status) {
                const statusMap = {
                    published: 'success',
                    draft: 'gray',
                    deleted: 'danger'
                }
                return statusMap[status]
            }
        },
        data() {
            return {
                list: null,
                listLoading: true,
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10
                },
                dialogFormVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '编辑',
                    create: '新建'
                },
                rules: {
                    datasourceName: [{ required: true, message: '必填', trigger: 'blur' }],
                    jdbcUsername: [{ required: true, message: '必填', trigger: 'blur' }],
                    jdbcPassword: [{ required: true, message: '必填', trigger: 'blur' }],
                    jdbcUrl: [{ required: true, message: '必填', trigger: 'blur' }],
                    jdbcDriverClass: [{ required: true, message: '必填', trigger: 'blur' }],
                    datasource: [{ required: true, message: '必填', trigger: 'change' }],
                    zkAdress: [{ required: true, message: '必填', trigger: 'blur' }],
                    databaseName: [{ required: true, message: '必填', trigger: 'blur' }]
                },
                dataConnection: {
                    id: undefined,
                    datasourceName: '',
                    datasourceGroup: 'Default',
                    jdbcUsername: '',
                    jdbcPassword: '',
                    jdbcUrl: '',
                    jdbcDriverClass: '',
                    comments: '',
                    datasource: '',
                    zkAdress: '',
                    databaseName: ''
                },
                visible: true,
                dataSources: [
                    { value: 'mysql', label: 'mysql' },
                    { value: 'oracle', label: 'oracle' },
                    { value: 'postgresql', label: 'postgresql' },
                    { value: 'sqlserver', label: 'sqlserver' },
                    { value: 'hive', label: 'hive' },
                    { value: 'hbase', label: 'hbase' },
                    { value: 'mongodb', label: 'mongodb' },
                    { value: 'clickhouse', label: 'clickhouse' }
                ],
                jdbc: true,
                hbase: false,
                mongodb: false
            }
        },
        created() {
            this.fetchData()
        },
        methods: {
            selectDataSource(datasource) {
                if (datasource === 'mysql') {
                    this.dataConnection.jdbcUrl = 'jdbc:mysql://{host}:{port}/{database}'
                    this.dataConnection.jdbcDriverClass = 'com.mysql.jdbc.Driver'
                } else if (datasource === 'oracle') {
                    this.dataConnection.jdbcUrl = 'jdbc:oracle:thin:@//{host}:{port}/{database}'
                    this.dataConnection.jdbcDriverClass = 'oracle.jdbc.OracleDriver'
                } else if (datasource === 'postgresql') {
                    this.dataConnection.jdbcUrl = 'jdbc:postgresql://{host}:{port}/{database}'
                    this.dataConnection.jdbcDriverClass = 'org.postgresql.Driver'
                } else if (datasource === 'sqlserver') {
                    this.dataConnection.jdbcUrl = 'jdbc:sqlserver://{host}:{port};DatabaseName={database}'
                    this.dataConnection.jdbcDriverClass = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
                } else if (datasource === 'clickhouse') {
                    this.dataConnection.jdbcUrl = 'jdbc:clickhouse://{host}:{port}/{database}'
                    this.dataConnection.jdbcDriverClass = 'ru.yandex.clickhouse.ClickHouseDriver'
                } else if (datasource === 'hive') {
                    this.dataConnection.jdbcUrl = 'jdbc:hive2://{host}:{port}/{database}'
                    this.dataConnection.jdbcDriverClass = 'org.apache.hive.jdbc.HiveDriver'
                    this.hbase = this.mongodb = false
                    this.jdbc = true
                }
                this.getShowStrategy(datasource)
            },
            fetchData() {
                this.listLoading = true
                listDataConnection(this.listQuery).then(response => {
                    let data=response.data.data
                    this.total = data.total
                    this.list = data.list
                    this.listLoading = false
                })
            },
            resetTemp() {
                this.dataConnection = {
                    id: undefined,
                    datasourceName: '',
                    datasourceGroup: 'Default',
                    jdbcUsername: '',
                    jdbcPassword: '',
                    jdbcUrl: '',
                    jdbcDriverClass: '',
                    comments: ''
                }
            },
            handleCreate() {
                this.resetTemp()
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        createDataConnection(this.dataConnection).then(() => {
                            this.fetchData()
                            this.dialogFormVisible = false
                            this.$notify({
                                title: 'Success',
                                message: 'Created Successfully',
                                type: 'success',
                                duration: 2000
                            })
                        })
                    }
                })
            },
            testDataSource() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        testDataConnection(this.dataConnection).then(response => {
                            if (response.data === false) {
                                this.$notify({
                                    title: 'Fail',
                                    message: response.data.msg,
                                    type: 'fail',
                                    duration: 2000
                                })
                            } else {
                                this.$notify({
                                    title: 'Success',
                                    message: 'Tested Successfully',
                                    type: 'success',
                                    duration: 2000
                                })
                            }
                        })
                    }
                })
            },
            handleUpdate(row) {
                this.getShowStrategy(row.datasource)
                this.dataConnection= Object.assign({}, row) // copy obj
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        const tempData = Object.assign({}, this.dataConnection)
                        updateDataConnection(tempData).then(() => {
                            this.fetchData()
                            this.dialogFormVisible = false
                            this.$notify({
                                title: 'Success',
                                message: 'Update Successfully',
                                type: 'success',
                                duration: 2000
                            })
                        })
                    }
                })
            },
            getShowStrategy(datasource) {
                if (datasource === 'hbase') {
                    this.jdbc = this.mongodb = false
                    this.hbase = true
                } else if (datasource === 'mongodb') {
                    this.jdbc = this.hbase = false
                    this.mongodb = true
                    this.dataConnection.jdbcUrl = 'mongodb://[username:password@]host1[:port1][,...hostN[:portN]]][/[database][?options]]'
                } else {
                    this.hbase = this.mongodb = false
                    this.jdbc = true
                }
            },
            handleDelete(row) {
                console.log('删除')
                const idList = []
                idList.push(row.id)
                // 拼成 idList=xx
                // 多个比较麻烦，这里不处理
                deleteDataConnection({ idList: row.id }).then(response => {
                    this.fetchData()
                    this.$notify({
                        title: 'Success',
                        message: 'Delete Successfully',
                        type: 'success',
                        duration: 2000
                    })
                })
                // const index = this.list.indexOf(row)
            },

            changePass(value) {
                this.visible = !(value === 'show')
            }
        }
    }
</script>
