<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>
                <a href="/">活动管理</a>
            </el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card>
            <!-- 搜索 -->
            <el-row :gutter="20">
                <el-col :span="7">
                    <el-input
                            placeholder="请输入内容"
                            class="input-with-select"
                            v-model="uservo.sunpxitUser.username"
                            clearable
                            @clear="queryPage"
                    >
                        <el-button slot="append" icon="el-icon-search" @click="sel()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="dialogAddUser=true,passEditUser=true" v-if="is_authority.is_user_add">添加用户
                    </el-button>
                </el-col>
            </el-row>
            <!-- 数据 -->
            <el-table :data="userList" border stripe size='mini'
                      :cell-style="{'text-align':'center'}"
                      :header-cell-style="{background:'rgb(29,28,46)','text-align':'center'}">
                <el-table-column type="index"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="mobile" label="手机号码"></el-table-column>
                <el-table-column prop="roleName" label="角色"></el-table-column>
                <el-table-column label="状态">
                    <!-- 作用域插槽 -->
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.mgState"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                @change="uptStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180px">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-tooltip
                                class="item"
                                effect="dark"
                                content="编辑"
                                placement="top-start"
                                :enterable="false"
                                v-if="is_authority.is_user_upt"
                        >
                            <el-button type="primary" icon="el-icon-edit" size="mini" @click="editUser(scope.row)"></el-button>
                        </el-tooltip>
                        <!-- 删除 -->
                        <el-tooltip
                                class="item"

                                effect="dark"
                                content="删除"
                                placement="top-start"
                                :enterable="false"
                                v-if="is_authority.is_user_del"

                        >
                            <el-button type="danger" icon="el-icon-delete" size="mini"
                                       @click="del(scope.row)"></el-button>
                        </el-tooltip>

                        <!-- 分配角色 -->
                        <el-tooltip
                                class="item"
                                effect="dark"
                                content="分配角色"
                                placement="top-start"
                                :enterable="false"
                                v-if="is_authority.is_user_add_role"
                        >
                            <el-button type="warning" icon="el-icon-setting" size="mini"
                                       @click="fprole(scope.row)"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="page.current"
                    :page-sizes="[5,10, 20, 30, 40]"
                    :page-size="page.size"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.total"
            ></el-pagination>
            <!-- 对话框 :before-close="handleClose" 在对话框关闭之前执行-->
            <!-- 标题 -->
            <el-dialog title="添加用户" :visible.sync="dialogAddUser" width="50%" @close="closeInit">
                <!-- 内容主体 -->

                <el-form
                        :model="addUserForm"
                        :rules="addUserRules"
                        ref="addFormRef"
                        label-width="70px"
                        class="demo-ruleForm"
                >
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="addUserForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" v-if="passEditUser">
                        <el-input v-model="addUserForm.password"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="addUserForm.email"></el-input>
                    </el-form-item>
                    <el-form-item label="手机" prop="mobile">
                        <el-input v-model="addUserForm.mobile"></el-input>
                    </el-form-item>
                </el-form>
                <!-- 按钮 -->
                <span slot="footer" class="dialog-footer">
          <el-button @click="dialogAddUser = false">取 消</el-button>
          <el-button type="primary" @click="addUser">确 定</el-button>
        </span>
            </el-dialog>

            <el-dialog title="分配角色" :visible.sync="dialogAddRole" width="20%" @close="closeAddRole">
                <el-form ref="form" v-model="addUserForm" label-width="80px">
                    <el-form-item label="选择角色">
                        <el-select v-model="addUserForm.roleId" placeholder="请选择角色">
                            <el-option :label="item.roleName" :value="item.id"  v-for="(item,index) in role_list "
                                       :key="index"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-button @click="addRole" type="primary" round>确定</el-button>
                    <el-button round @click="closeAddRole">取消</el-button>
                </el-form>

            </el-dialog>
        </el-card>
    </div>
</template>
<script>
  import { mapState } from 'vuex'

  export default {
    data() {
      //验证邮箱
      var eml = (rule, value, cd) => {
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
        if (reg.test(value)) {
          return cd()
        }
        return cd(new Error('非法邮箱'))
      }
      var mobile = (rule, value, cd) => {
        var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/
        if (myreg.test(value)) {
          return cd()
        }
        return cd(new Error('非法手机号码'))
      }
      return {
        uservo: {
          sunpxitUser: { username: '' },
          page: {
            current: 1,
            size: 5
          }
        },
        dialogAddRole: false
        ,

        page: {
          current: 1,
          size: 5,
          total: 0
        },
        userList: [],
        role_list: []
        ,
        //控制对话框
        dialogAddUser: false,
        passEditUser: true,
        // 添加用户
        addUserForm: {
        },
        addUserRules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 5, max: 15, message: '长度必须在5到15位之间', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 5, max: 15, message: '长度必须在5到15位之间', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { validator: eml, trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机', trigger: 'blur' },
            { validator: mobile, trigger: 'blur' }
          ]
        },
        is_authority: {
          is_user_del: false,
          is_user_upt: false,
          is_user_add: false,
          is_user_add_role: false
        }
      }
    },
    created() {
      this.queryPage()
      this.isAuthority()
    },
    methods: {
      async queryPage() {
        const { data: res } = await this.$http.post(
          '/mallSysUser/queryPage',
          this.uservo
        )

        if (res.code != 20000) return this.$message.error(res.message)
        this.userList = res.data.list.records
        this.page.total = res.data.list.total
        this.page.current = res.data.list.current
        this.page.size = res.data.list.size
        console.log( this.userList)
      },
      // 监听每页展示的条数
      handleSizeChange(newSize) {
        this.uservo.page.size = newSize
        this.queryPage()
      },
      //监听页码
      handleCurrentChange(newCurrent) {
        this.uservo.page.current = newCurrent
        this.queryPage()
      },
      //更新状态
      async uptStatus(status) {
        const { data: res } = await this.$http.put(
          `/mallSysUser/users/${status.id}/state/${status.mgState}`
        )
        if (res.code != 20000) {
          status.mgState = !status.mgState
          return this.$message.error(res.message)
        }
        return this.$message.success(res.message)
      },
      sel() {
        this.queryPage()
      },
      //分配角色初始化数据
      async fprole(status) {

        if (status.roleId!=undefined){
          this.addUserForm.roleId = status.roleId
        }
        const { data: res } = await this.$http.get(
          '/mallSysRole/queryAll'
        )


        this.dialogAddRole = true
        if (res.code != 20000) {
          return this.$message.error(res.message)
        }
        this.role_list = res.data.data
        this.addUserForm.id = status.id

      },
      // 添加角色
      async addRole() {
        const { data: res } = await this.$http.post(
          '/mallSysUser/save',
          this.addUserForm
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddRole = false
        this.queryPage()
        return this.$message.success(res.message)
      },
      //删除
      async del(status) {
        const { data: res } = await this.$http.delete(
          '/mallSysUser/del' + '/' + status.id
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.queryPage()
        this.userList = []
        return this.$message.success(res.message)
      },
      //关闭添加页面时初始化
      closeInit() {
        this.addUserForm = {}
        this.$refs.addFormRef.resetFields()
        this.passEditUser=false
      },
      closeAddRole() {
        this.dialogAddRole = false
        this.addUserForm = {}
      },
      //添加用户
      addUser() {
        this.$refs.addFormRef.validate(async valid => {
          if (!valid) return
          const { data: res } = await this.$http.post(
            '/mallSysUser/save',
            this.addUserForm
          )
          if (res.code != 20000) return this.$message.error(res.message)
          this.dialogAddUser = false
          this.passEditUser=false
          this.queryPage()
          return this.$message.success(res.message)
        })
      },
      isAuthority() {
        var arr = this.btus.split(',')
        for (var key in this.is_authority) {
          if (arr.indexOf(key) != -1) {
            this.is_authority[key] = true
          }
        }
      },
      //编辑用户
      async editUser(status){
        this.passEditUser=false;
        this.dialogAddUser=true;
        const id=status.id
        const { data: res } = await this.$http.get(
          '/mallSysUser/getById'+"/"+id
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.addUserForm=res.data.userInfoAll


      }
    },
    computed: {
      ...mapState(['btus'])
    }
  }
</script>
<style lang="less" scoped>
    .el-table {
        margin-top: 15px;
        font-size: 12px;
    }

    .el-pagination {
        margin-top: 15px;
    }
</style>