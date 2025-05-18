<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/roles' }">角色列表</el-breadcrumb-item>
      <el-breadcrumb-item>角色修改</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-card>
            <el-tree
              :data="data"
              :props="defaultProps"
              node-key="menuCode"
              show-checkbox
              :default-expand-all="true"
              :default-checked-keys="defkeys"
              ref="treeRef"
            ></el-tree>
          </el-card>
        </div>
      </el-col>
      <el-col :span="18">
        <el-card>
          <el-form ref="form" :model="role" label-width="80px">
            <el-form-item label="角色名称">
              <el-input v-model="role.roleName"></el-input>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-input v-model="role.createTime" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="角色介绍">
              <el-input
                type="textarea"
                v-model="role.remark"
                :autosize="{ minRows: 7, maxRows: 6 }"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <div class="but_cl">
          <el-button @click='Tes()'>取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
      data: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      defkeys: [],
      role: {}
    }
  },
  created() {
    this.queryPage()
  },
  methods: {
    //初始化表单
    Tes(){
      this.$router.push({ path: '/roles'})
    },

    async queryPage() {
      const { data: res } = await this.$http.get(
        '/mallMenuRole/getByModuleCode' + '/' + this.$route.query.jId
      )
      // console.log(res)
      if (res.code != 20000) return this.$message.error(res.message)
      this.data = res.data.tree
      this.defkeys = res.data.finallyList
      this.role = res.data.role
    },
   async save() {
      //获取所有被选中的属性
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      //获取角色信息
      const roleht = this.role
      const { data: res } = await this.$http.post(
        '/mallMenuRole/save',{
          list: keys,
          roleId: roleht
        }
      )
       if (res.code != 20000) return this.$message.error(res.message)
       this.data=[]
       this.defkeys=[]
       this.role={}
       this.$router.push({path:'/roles'})
       return this.$message.success('修改成功')


    }
  }
}
</script>
<style lang="less" scoped>
.el-card {
  width: 100%;
  // height: 150%;
}
.but_cl {
  margin-top: 20px;
  position: absolute;
  left: 50%;
  
}

</style>