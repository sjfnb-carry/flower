<template>
  <div class='block'>
    <el-breadcrumb separator='/'>
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单详情表</el-breadcrumb-item>
      <el-breadcrumb-item>订单详情表列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>

      <!-- 搜索 -->
      <el-row :gutter='20'>
        <el-col :span='7'>
          <!--搜索条件 -->
        </el-col>
        <el-col :span='4'>

        </el-col>
      </el-row>


      <el-table :data='MallOrderList' border stripe size='mini'
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{background:'rgb(29,28,46)','text-align':'center'}">
        <el-table-column prop='userId' label='用户编号'></el-table-column>
        <el-table-column prop='orderUmber' label='订单编号'></el-table-column>
        <el-table-column prop='amount' label='金额' width='80'></el-table-column>
        <el-table-column prop='quantity' label='数量' width='80'></el-table-column>
        <el-table-column prop='isPay' label='支付结果' :formatter='stateFormat' width='80'></el-table-column>
        <el-table-column prop='gmtCreate' label='创建时间'></el-table-column>
        <el-table-column prop='status' label='状态' :formatter='stateFormatStatus'></el-table-column>
        <el-table-column label='操作'>
          <template slot-scope='scope'>
            <!-- 修改 -->
            <el-tooltip class='item' effect='dark' content='查看' placement='top-start' :enterable='false'>
              <el-button type='primary' icon='el-icon-postcard' size='mini'
                         @click='editMallOrder(scope.row)'></el-button>
            </el-tooltip>
            <!-- 删除 -->
            <el-tooltip class='item' effect='dark' content='删除' placement='top-start' :enterable='false'>
              <el-button type='danger' icon='el-icon-delete' size='mini' @click='del(scope.row)'></el-button>
            </el-tooltip>
            <!-- 删除 -->
            <el-tooltip class='item' effect='dark' content='发货' placement='top-start' :enterable='false'
                        v-if='scope.row.status ===2 && scope.row.isPay===1'>
              <el-button type='warning' icon='el-icon-platform-eleme' size='mini' @click='fh(scope.row)'></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change='handleSizeChange'
        @current-change='handleCurrentChange'
        :current-page='page.current'
        :page-sizes='[5,10, 20, 30, 40]'
        :page-size='page.size'
        layout='total, sizes, prev, pager, next, jumper'
        :total='page.total'
      ></el-pagination>
    </el-card>


  </div>
</template>
<script>
export default {
  data() {
    return {
      MallOrderList: [],
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      MallOrderVo: {
        page: {
          current: 1,
          size: 5
        }
      },
      dialogAddMallOrder: false,
      MallOrderInfo: {},
      dialogBewrite: ''

    }
  },
  created() {
    this.queryPage()
  },
  methods: {
    // 监听每页展示的条数
    handleSizeChange(newSize) {
      this.MallOrderVo.page.size = newSize
      this.queryPage()
    },
    //监听页码
    handleCurrentChange(newCurrent) {
      this.MallOrderVo.page.current = newCurrent
      this.queryPage()
    },
    //列表
    async queryPage() {
      const { data: res } = await this.$http.post(
        '/mallOrder/queryMallOrderList',
        this.MallOrderVo
      )

      if (res.code != 20000)
        return this.$message.error(res.message)
      this.MallOrderList = res.data.dataList.records
      this.page.total = res.data.dataList.total
      this.page.current = res.data.dataList.current
      this.page.size = res.data.dataList.size
    },

    async del(status) {

      this.$confirm('确认永久删除吗？')
        .then(async _ => {
          const { data: res } = await this.$http.delete(
            '/mallOrder/removeByIdMallOrder/' + status.id
          )
          if (res.code != 20000) return this.$message.error(res.message)
          this.queryPage()
          return this.$message.success(res.message)
        })
        .catch(_ => {
          return this.$message.info('取消删除')
        })


    },


    async fh(status) {

      this.$confirm('确定发货？')
        .then(async _ => {
          const { data: res } = await this.$http.get(
            '/mallOrder/sendCore/' + status.orderUmber
          )
          if (res.code != 20000) return this.$message.error(res.message)
          this.queryPage()
          return this.$message.success(res.message)
        })
        .catch(_ => {
          return this.$message.info('取消发货')
        })


    },


    stateFormat(r, c) {
      if (r.isPay === 1) {
        return '已支付'
      } else {
        return '未支付'
      }
    },
    stateFormatStatus(r, c) {
      if (r.status === 2) {
        return '未发货'
      } else {
        return '以发货'
      }
    },
    editMallOrder(res) {
      this.$router.push({ path: '/orderDetail', query: { jId: res.orderUmber } })
    }
  }


}
</script>