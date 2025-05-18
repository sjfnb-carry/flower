<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>角色列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card>
            <el-row>
                <el-col>
                    <el-button type="primary" @click="dialogAddRole=true">添加角色</el-button>
                </el-col>
            </el-row>
            <!-- 角色列表 -->
            <el-table :data="roleData" border style="width: 100%" size='mini'
                      :cell-style="{'text-align':'center'}"
                      :header-cell-style="{'text-align':'center'}">
                <el-table-column prop="roleName" label="角色名称"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column prop="remark" label="角色说明"></el-table-column>
                <el-table-column label="操作" width="180px">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-tooltip
                                class="item"
                                effect="dark"
                                content="编辑"
                                placement="top-start"
                                :enterable="false"
                        >
                            <el-button type="primary" icon="el-icon-edit" size="mini"
                                       @click="test(scope.row.id)"></el-button>
                        </el-tooltip>
                        <!-- 删除 -->
                        <el-tooltip
                                class="item"
                                effect="dark"
                                content="删除"
                                placement="top-start"
                                :enterable="false"
                        >
                            <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
                        </el-tooltip>

                        <!-- 分配角色 -->
                        <el-tooltip
                                class="item"
                                effect="dark"
                                content="分配权限"
                                placement="top-start"
                                :enterable="false"
                        >
                            <el-button type="warning" icon="el-icon-setting" size="mini" @click='test(scope.row.id)'></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog title="添加角色" :visible.sync="dialogAddRole" width="50%" @close="closeAddRole">
                <el-form ref="form" v-model="addRoleForm" label-width="80px">
                    <el-form-item label="角色名称">
                        <el-input v-model="addRoleForm.roleName"></el-input>
                    </el-form-item>
                    <el-form-item label="角色描述">
                        <el-input type="textarea" v-model="addRoleForm.remark"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="addSave">立即创建</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>

            </el-dialog>
        </el-card>
    </div>
</template>
<script>
  export default {
    data() {
      return {
        //控制按钮权限
        is_authority: {
          is_role_del: false,
          is_role_upt: false,
          is_role_add: false,
          is_role_add_role: false
        },
        roleData: [],
        dialogAddRole: false,
        addRoleForm: {}
      }
    },
    created() {
      this.queryPage()
    }
    ,
    methods: {
      async queryPage() {
        const { data: res } = await this.$http.get(
          '/mallSysRole/queryPage'
        )
        // console.log(res)
        if (res.code != 20000) return this.$message.error(res.message)
        this.roleData = res.data.list
      },
      test(id) {
        this.$router.push({ path: '/resume', query: { jId: id } })
      },
      closeAddRole() {

      },
      async addSave() {
        const { data: res } = await this.$http.post(
          '/mallSysRole/save',this.addRoleForm
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddRole = false
        this.queryPage()
        return this.$message.success(res.message)
      },
      addDown() {

      }
    }

  }
</script>
<style lang="sass" scoped>

</style>