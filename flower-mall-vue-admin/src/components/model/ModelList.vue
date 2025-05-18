<template>
    <div class="block">
        <!-- <p><el-button @click="As">取反</el-button></p> -->
        <!-- 加载图标 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>权限列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row :gutter="20">
            <el-col :span="4">
                <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
                <el-card>
                    <div class="treeModel">
                        <el-tree
                                class="filter-tree"
                                :data="data"
                                :props="defaultProps"
                                default-expand-all
                                :filter-node-method="filterNode"
                                @node-click="handleNodeClick"
                                ref="tree"
                        ></el-tree>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="19">
                <el-card>
                    <el-row>
                        <el-col>
                            <el-button type="primary" @click="dialogAddUser=true">添加子权限</el-button>
                        </el-col>
                    </el-row>
                    <div class="formAll">
                        <el-form ref="form" label-width="80px">
                            <el-form-item label="权限名称">
                                <el-input v-model="GetByMode.menuName"></el-input>
                            </el-form-item>
                            <el-form-item label="权限编码">
                                <el-input v-model="GetByMode.menuCode" :disabled="true"></el-input>
                            </el-form-item>
                            <el-form-item label="请求地址">
                                <el-input v-model="GetByMode.menuPath"></el-input>
                            </el-form-item>
                            <el-form-item label="图标">
                                <el-input v-model="GetByMode.icon" :disabled="true" placeholder="选择图标">
                                    <template slot="prepend">
                                        <i :class="GetByMode.icon"></i>
                                    </template>
                                    <el-popover placement="right" width="400" trigger="click" slot="append">
                                        <el-row>
                                            <el-col v-for="(item,index) in logoList" :key="index" :span="3">
                                                <el-button :icon="item" @click="geticon(item)"></el-button>
                                            </el-col>
                                        </el-row>
                                        <el-button slot="reference">选择</el-button>
                                    </el-popover>
                                </el-input>
                            </el-form-item>
                            <el-form-item label="类型">
                                <template>
                                    <el-radio v-model="GetByMode.isBtn" label="0">菜单</el-radio>
                                    <el-radio v-model="GetByMode.isBtn" label="1">按钮</el-radio>
                                </template>
                            </el-form-item>
                            <el-form-item label="上级名称">
                                <el-input placeholder="顶级" :disabled="true" v-model="GetByMode.parentName"></el-input>
                            </el-form-item>
                            <el-form-item label="是否启用">
                                <el-switch
                                        active-color="#13ce66"
                                        inactive-color="#ff4949"
                                        v-model="GetByMode.isEnable"
                                ></el-switch>
                            </el-form-item>
                            <el-form-item label="排序">
                                <el-input-number v-model="GetByMode.sort" :min="0" :max="99999"
                                                 label="描述文字"></el-input-number>
                            </el-form-item>
                        </el-form>
                        <!-- 按钮 -->
                        <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="upt()">更新</el-button>
              <el-button type="danger" @click="del(GetByMode.id)">删除</el-button>
            </span>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <el-dialog title="添加权限" :visible.sync="dialogAddUser" width="50%" @close="closeInit">
            <!-- 内容主体 -->

            <el-form
                    :model="GetByModeAdd"
                    ref="addFormRef"
                    label-width="70px"
                    class="demo-ruleForm"
            >
                <el-form-item label="权限名称">
                    <el-input v-model="GetByModeAdd.menuName"></el-input>
                </el-form-item>
                <el-form-item label="请求地址">
                    <el-input v-model="GetByModeAdd.menuPath"></el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <el-input v-model="GetByModeAdd.icon" :disabled="true" placeholder="选择图标">
                        <template slot="prepend">
                            <i :class="GetByModeAdd.icon"></i>
                        </template>
                        <el-popover placement="right" width="400" trigger="click" slot="append">
                            <el-row>
                                <el-col v-for="(item,index) in logoList" :key="index" :span="3">
                                    <el-button :icon="item" @click="geticonAdd(item)"></el-button>
                                </el-col>
                            </el-row>
                            <el-button slot="reference">选择</el-button>
                        </el-popover>
                    </el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <template>
                        <el-radio v-model="GetByModeAdd.isBtn" label="0">菜单</el-radio>
                        <el-radio v-model="GetByModeAdd.isBtn" label="1">按钮</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="上级名称">
                    <el-input placeholder="顶级" :disabled="true" v-model="GetByMode.menuName"></el-input>
                </el-form-item>
                <el-form-item label="上级编码">
                    <el-input placeholder="00" :disabled="true" v-model="GetByMode.menuCode"></el-input>
                </el-form-item>
                <el-form-item label="是否启用">
                    <el-switch
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            v-model="GetByModeAdd.isEnable"
                            :active-value="0"
                            :inactive-value="1"
                    ></el-switch>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input-number v-model="GetByModeAdd.sort" :min="0" :max="99999" label="描述文字"></el-input-number>
                </el-form-item>
            </el-form>
            <!-- 按钮 -->
            <span slot="footer" class="dialog-footer">
          <el-button @click="dialogAddUser = false">取 消</el-button>
          <el-button type="primary" @click="addUser(GetByMode.menuCode)">确 定</el-button>
        </span>
        </el-dialog>
    </div>
</template>
<script>
  import logo from '../utils/logoModel'

  export default {
    data() {
      return {
        logoList: logo.logoAll,
        visible: false,
        data: [],
        defaultProps: {
          children: 'children',
          label: 'menuName'
        },
        filterText: '',
        GetByMode: {},
        GetByModeAdd: {
          icon: 'el-icon-menu',
          isBtn:0
        },
        dialogAddUser: false,
      }
    },
    created() {
      this.queryPage()
      this.GetByModeAdd.sort = 0
      this.GetByModeAdd.isEnable = true
      this.GetByModeAdd.isBtn = '0'
    },
    methods: {
      async queryPage() {
        const { data: res } = await this.$http.get(
          '/mallMenuRole/getByModuleCode' + '/' + this.$route.query.jId
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.data = res.data.tree
      },
      // 关闭添加
      closeInit() {
        this.GetByModeAdd = {
          sort: 0,
          isEnable: true,
          isBtn: '0',
          icon: 'el-icon-menu'
        }

      },
      // 添加
      async addUser(parentId) {
        this.GetByModeAdd.parentId = parentId==undefined?"00":parentId
        const { data: res } = await this.$http.post(
          '/mallMenu/save',
          this.GetByModeAdd
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddUser = false
        this.queryPage()
        return this.$message.success(res.message)
      },
      geticonAdd(ico) {
        this.GetByModeAdd.icon = ico
        // console.log(this.GetByModeAdd)
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      async handleNodeClick(data) {
        const { data: res } = await this.$http.get(
          '/mallMenuRole/getById' + '/' + data.id
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.GetByMode = res.data.data
      },
      geticon(ioc) {
        this.GetByMode.icon = ioc
      },
      async upt(){
        const { data: res } = await this.$http.post(
          '/mallMenu/upt',
          this.GetByMode
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddUser = false
        this.queryPage()
        return this.$message.success(res.message)
      },
      async del(id){
        const { data: res } = await this.$http.delete(
          '/mallMenu/del' + '/' + id
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.queryPage()
        this.GetByMode={}
        return this.$message.success(res.message)
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      }
    }
  }
</script>
<style lang="less" scoped>
    .el-popover .el-row {
        height: 500px;
        overflow-y: scroll;
    }

    .treeModel {
        width: 90%;
        margin: 0 auto;
        margin-top: 10px;
    }

    .formAll {
        width: 65%;
        margin-top: 15px;
    }
</style>
