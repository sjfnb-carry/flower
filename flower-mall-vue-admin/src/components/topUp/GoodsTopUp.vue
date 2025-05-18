<template>
  <div class='block'>
    <el-breadcrumb separator='/'>
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>充值记录表</el-breadcrumb-item>
      <el-breadcrumb-item>充值记录表列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>

      <!-- 搜索 -->
      <el-row :gutter='20'>
        <el-col :span='7'>
          <!--搜索条件 -->
        </el-col>
        <el-col :span='4'>
          <el-button type='primary' @click="dialogAddGoodsTopUp=true,GoodsTopUpInfo={},dialogBewrite='添加充值记录表'">添加
          </el-button>
        </el-col>
      </el-row>


      <el-table :data='GoodsTopUpList' border stripe size='mini'
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}">
        <el-table-column prop='accountId' label='账户id'></el-table-column>
        <el-table-column prop='goodsTopUpMoney' label='充值金额'></el-table-column>
        <el-table-column prop='gmtCreate' label='创建时间'></el-table-column>
        <el-table-column prop='userId' label='商城用户id'></el-table-column>
        <el-table-column prop='isPay' label='结果' :formatter='stateFormat'></el-table-column>
        <el-table-column prop='accountBalance' label='处理原因'></el-table-column>
        <el-table-column label='操作' width='180px'>
          <template slot-scope='scope'>
            <!-- 删除 -->
            <el-tooltip class='item' effect='dark' content='处理' placement='top-start' :enterable='false'
                        v-if='scope.row.isPay===2'>
              <el-button type='danger' icon='el-icon-wallet' size='mini' @click='del(scope.row)'></el-button>
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

      <!--
                  <el-dialog :title="dialogBewrite" :visible.sync="dialogAddGoodsTopUp" width="50%" @close="closeInit">
                      <el-form :model="GoodsTopUpInfo" ref="addGoodsTopUpRef" label-width="70px" class="demo-ruleForm">
                              <el-form-item label="账户id">
                                  <el-input v-model="GoodsTopUpInfo.accountId"></el-input>
                              </el-form-item>
                              <el-form-item label="充值金额">
                                  <el-input v-model="GoodsTopUpInfo.goodsTopUpMoney"></el-input>
                              </el-form-item>
                              <el-form-item label="账户余额">
                                  <el-input v-model="GoodsTopUpInfo.accountBalance"></el-input>
                              </el-form-item>
                              <el-form-item label="创建时间">
                                  <el-input v-model="GoodsTopUpInfo.gmtCreate"></el-input>
                              </el-form-item>
                              <el-form-item label="商城用户id">
                                  <el-input v-model="GoodsTopUpInfo.userId"></el-input>
                              </el-form-item>
                              <el-form-item label="处理结果">
                                  <el-input v-model="GoodsTopUpInfo.isPay"></el-input>
                              </el-form-item>
                          <el-form-item>
                              <template slot-scope="scope">
                                  <el-button type="primary"@click="addGoodsTopUp()">保存</el-button>
                                  <el-button @click="dialogAddGoodsTopUp=false">取消</el-button>
                              </template>
                          </el-form-item>
                      </el-form>
                  </el-dialog>
      -->

    </el-card>
    <el-dialog :title='dialogBewrite' :visible.sync='dialogAddGoodsTopUp' width='50%' @close='closeInit'>
      <el-form :model='GoodsTopUpInfo' ref='addGoodsTopUpRef' label-width='70px' class='demo-ruleForm'>
        <el-form-item label='充值结果'>
          <el-select v-model='GoodsTopUpInfo.isPay' placeholder='请选择'>
            <el-option :key='1' label='同意' :value='1'></el-option>
            <el-option :key='2' label='拒绝' :value='3'></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='拒绝原因' v-if='GoodsTopUpInfo.isPay===3'>
          <el-input v-model='GoodsTopUpInfo.accountBalance'></el-input>
        </el-form-item>
        <el-form-item>
          <template slot-scope='scope'>
            <el-button type='primary' @click='addGoodsTopUp()'>保存</el-button>
            <el-button @click='dialogAddGoodsTopUp=false'>取消</el-button>
          </template>
        </el-form-item>
      </el-form>
    </el-dialog>


  </div>
</template>
<script>
export default {
  data() {
    return {
      GoodsTopUpList: [],
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      GoodsTopUpVo: {
        page: {
          current: 1,
          size: 5
        }
      },
      dialogAddGoodsTopUp: false,
      GoodsTopUpInfo: {},
      dialogBewrite: ''

    }
  },
  created() {
    this.queryPage()
  },
  methods: {
    // 监听每页展示的条数
    handleSizeChange(newSize) {
      this.GoodsTopUpVo.page.size = newSize
      this.queryPage()
    },
    stateFormat(r, c) {
      if (r.isPay === 1) {
        return '成功'
      } else if (r.isPay === 2) {
        return '待确定'
      } else {
        return '拒绝'
      }
    },
    //监听页码
    handleCurrentChange(newCurrent) {
      this.GoodsTopUpVo.page.current = newCurrent
      this.queryPage()
    },
    //列表
    async queryPage() {
      const { data: res } = await this.$http.post(
        'topUp/queryTopUpList',
        this.GoodsTopUpVo
      )

      if (res.code != 20000)
        return this.$message.error(res.message)
      this.GoodsTopUpList = res.data.dataList.records
      this.page.total = res.data.dataList.total
      this.page.current = res.data.dataList.current
      this.page.size = res.data.dataList.size
    },
    closeInit() {
      this.addGoodsTopUpForm = {}
      this.dialogAddGoodsTopUp = false
    },
    addGoodsTopUp() {
      this.$refs.addGoodsTopUpRef.validate(async valid => {
        if (!valid) {
          return
        }
        const { data: res } = await this.$http.post(
          'topUp/uptGoodsUserAccount/',
          this.GoodsTopUpInfo
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddGoodsTopUp = false
        this.queryPage()
        return this.$message.success(res.message)
      })
    },
    async del(row) {
      this.dialogAddGoodsTopUp = true
      this.dialogBewrite = '编辑充值记录表'
      let le={}
      le.id=row.id
      le.accountId=row.accountId
      le.userId=row.userId
      le.goodsTopUpMoney=row.goodsTopUpMoney
      le.accountBalance=row.accountBalance
      le.gmtCreate=row.gmtCreate
      le.isPay = 1
      this.GoodsTopUpInfo = le
    }
  }
}
</script>