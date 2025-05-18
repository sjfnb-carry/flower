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
                            v-model="Logevo.sunpxLog.logContent"
                            clearable
                            @clear="queryPage"
                    >


                    </el-input>
                </el-col>
                <el-col :span="13">
                    <el-select v-model="Logevo.sunpxLog.logType" placeholder="请选择类型">
                        <el-option :label="item" :value="index+1"  v-for="(item,index) in type_list "
                                   :key="index"></el-option>
                    </el-select>
                    <span class="tj">
                        <el-button type="primary">搜索</el-button>
                        <el-button>清空</el-button>
                        <el-button type="success">导出</el-button>
                        <el-button type="danger">清空日志</el-button>

                    </span>
                </el-col>
            </el-row>
            <el-table
                    :data="logAllList"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="logTypeString"
                        label="日志类型"
                        width="150px"
                >
                </el-table-column>
                <el-table-column
                        prop="logContent"
                        label="内容"
                        :show-overflow-tooltip="true"
                >
                </el-table-column>
                <el-table-column
                        prop="operatorName"
                        label="操作人"
                        width="150px"
                >
                </el-table-column>
                <el-table-column
                        prop="createtime"
                        label="操作时间"
                        width="300px">
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
                        >
                            <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>
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
        </el-card>
    </div>
</template>
<script>
  export default {
    data() {
      return {
        logAllList: [],
        page: {
          current: 1,
          size: 10,
          total: 0
        },
        Logevo: {
          sunpxLog: { logContent: '' },
          page: {
            current: 1,
            size: 10
          }
        },
        type_list:["添加", "编辑", "删除", "导入", "导出", "用户角色变更", "角色权限变更", "登录", "退出登录"]
      }
    },

    created() {
      this.queryPage()
    },
    methods: {
      // 监听每页展示的条数
      handleSizeChange(newSize) {
        this.Logevo.page.size = newSize
        this.queryPage()
      },
      //监听页码
      handleCurrentChange(newCurrent) {
        this.Logevo.page.current = newCurrent
        this.queryPage()
      }
      ,
      sel(){

      },
      async queryPage() {
        const { data: res } = await this.$http.post(
          '/sunpx-log/queryPage',
          this.Logevo
        )

        if (res.code != 20000) return this.$message.error(res.message)
        this.logAllList = res.data.queryList.records
        this.page.total = res.data.queryList.total
        this.page.current = res.data.queryList.current
        this.page.size = res.data.queryList.size
        console.log(this.userList)
      }
    }
  }
</script>
<style lang="less" scoped>
    .tj {
        margin-left: 10px;
    }
</style>