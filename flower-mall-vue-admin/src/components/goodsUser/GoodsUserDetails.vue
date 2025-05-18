<template>

  <div style='height: 100%;'>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{path:'/goodsUserList'}">用户详情</el-breadcrumb-item>
      <el-breadcrumb-item>订单详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class='bode_main main-ka'>
      <div class='center_zx'>
        <div class='msmsm'>
          <div class='ksk'>
            <img width='100' height='100'
                 src='../../assets/images/667788.jpg'>
            <p style='color: #1e1d1d'>{{ goodsUserInfo.username }}</p>
          </div>
          <el-row style='margin: 20px;color: #1b1f23'>
            <p>{{ goodsUserInfo.gmtCreate }}</p>
            <div style="text-align: center; margin-top: 20px">
              <el-button type='danger' size='mini' @click="Tes">返回</el-button>
            </div>
          </el-row>
        </div>
      </div>
      <div class='center_right'>
        <el-tabs>
          <el-tab-pane label='基本信息'>
            <div class='orderMap'>
              <table>
                <tr>
                  <td height='40' class='orderKey'>编号:</td>
                  <td class='orderValue'>{{ goodsUserInfo.id }}</td>
                  <td height='40' class='orderKey'>用户名:</td>
                  <td class='orderValue'>{{ goodsUserInfo.username }}</td>
                  <td class='orderKey'>手机号:</td>
                  <td class='orderValue'>{{ goodsUserInfo.mobile }}</td>
                </tr>
                <tr>
                  <td class='orderKey'>状态:</td>
                  <td :class="goodsUserInfo.mgState===true?'orderValueY orderValue':'orderValueW orderValue'">
                    {{ goodsUserInfo.mgState === true ? '启用' : '禁用' }}
                  </td>


                  <td height='40' class='orderKey'>余额:</td>
                  <td :class="goodsUserInfo.money>0?'orderValueY orderValue':'orderValueW orderValue'">
                    {{ goodsUserInfo.money }}
                  </td>
                </tr>
              </table>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class='x_main'>
      <div class='z_ka main-ka gdt'>
        <div class='tableDataDey1 gdt'>
          <p style='font-size: 20px ;color: #1c1a1a ;margin: 10px ;font-weight:bold'>订单记录</p>

          <el-table :data='mallOrderList' border stripe :show-header='true' size='mini'
                    :header-cell-style="{background:'rgb(29,28,46)','text-align':'center'}"
                    :cell-style="{'text-align':'center'}">
            >
            <el-table-column prop='orderUmber' label='订单编号' show-overflow-tooltip>
              <template slot-scope='scope'>
                <el-link @click="test({path: '/orderDetail', query: { jId: scope.row.orderUmber }})" type='primary'>
                  {{ scope.row.orderUmber }}
                </el-link>
              </template>
            </el-table-column>
            <el-table-column prop='amount' label='金额' width='80'></el-table-column>
            <el-table-column prop='quantity' label='数量' width='80'></el-table-column>
            <el-table-column prop='isPay' label='支付结果' :formatter='stateFormat' width='80'></el-table-column>
            <el-table-column prop='gmtCreate' label='创建时间' width='150'></el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            @size-change='mallOrderHandleSizeChange'
            @current-change='mallOrderHandleCurrentChange'
            :current-page='mallOrderPage.current'
            :page-sizes='[5,10, 20, 30, 40]'
            :page-size='mallOrderPage.size'
            layout='total, sizes, prev, pager, next, jumper'
            :total='mallOrderPage.total'
          ></el-pagination>
        </div>
      </div>
      <div class='y_ka main-ka  '>
        <div class='tableDataDey1 gdt'>
          <p style='font-size: 20px ;color: #151414 ;margin: 10px ;font-weight:bold'>充值记录</p>

          <el-table :data='goodsTopUpList' border stripe :show-header='true' size='mini'
                    :header-cell-style="{'text-align':'center'}"
                    :cell-style="{'text-align':'center'}">
            >
            <el-table-column prop='gmtCreate' label='充值时间'></el-table-column>
            <el-table-column prop='goodsTopUpMoney' label='充值结果'></el-table-column>
            <el-table-column prop='isPay' label='充值结果' :formatter='stateFormat'>
              <template slot-scope='scope'>
                <span :class="scope.row.isPay===1?'orderValueE':scope.row.isPay===2?'orderValueD':'orderValueW'">

                  {{ scope.row.isPay === 1 ? '以到账' : scope.row.isPay === 2 ? '待审核' : '充值失败' }}

                </span>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            @size-change='topUpHandleSizeChange'
            @current-change='topUpHandleCurrentChange'
            :current-page='mallOrderPage.current'
            :page-sizes='[5,10, 20, 30, 40]'
            :page-size='goodsTopUpPage.size'
            layout='total, sizes, prev, pager, next, jumper'
            :total='goodsTopUpPage.total'
          ></el-pagination>
        </div>

      </div>
    </div>

  </div>

</template>
<style lang='less' scoped>
#myChart4 {
  height: 80%;
  width: 100%;
}

video {
  width: 100%;
  height: 100%;
  margin: auto;
}

.msmsm {
  width: 70%;
  height: 100%;

  .ksk {
    height: 65%;
    border-bottom: 1px solid #fff;
  }

  img {
    margin-top: 10%;
  }
}

.bode_main {
  height: 40%;
  width: 80%;
  margin-left: 10%;
  display: flex;
  align-items: center;

}

.x_main {
  height: 55%;
  width: 80%;
  margin-left: 10%;
  margin-top: 20px;
  display: flex;
  align-items: stretch;
}

.z_ka {
  height: 100%;
  width: 49%;

}

.gdt {
  overflow-y: scroll;
}

.gdt::-webkit-scrollbar-track {
  // -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  background-color: #fff;
}

.gdt::-webkit-scrollbar {
  width: 5px;
  background-color: #fff
}

.gdt::-webkit-scrollbar-thumb {
  border-radius: 5px;
  // -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, .3);
  background-color: #fff
}


.y_ka {
  height: 100%;
  width: 49%;
  margin-left: 2%;
  /*padding: 10px;*/
}

.main-ka {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
  border: 1px solid #dbdae1;
  background-color: #fff;
  color: #EEEEEE;
  transition: .3s;

}

/*.stuff {*/
/*    background: #fff;*/
/*    border: 1px solid #dadce0;*/
/*    border-radius: 8px;*/
/*    height: 60%;*/
/*    width: 25%;*/
/*    margin: 8px;*/
/*}*/

.center_zx {
  width: 30%;
  height: 90%;
  border-right: 1px solid #1D1C2E;
  margin: 5px;
  display: flex;
  justify-content: center;
  text-align: center;
}

.center_right {
  width: 65%;
  height: 90%;
  margin-left: 2%;

}

.el-tabs--border-card > .el-tabs__content {
  padding: 15px;
  height: 100%;
}

.el-tabs {
  height: 100%;
}

.tableDataDey1 {
  width: 100%;
  height: 90%;
  padding-top: 10px;
  margin: auto;
}

.orderMap table {
    width: 100%;
    color: #151414;
    margin-top: 20px;
  }



.orderValueY {
  color: chartreuse;
}

.orderValueE {
  color: #93bb6b;
}

.orderValueW {
  color: red;
}

.orderValueD {
  color: #eca10b;
}


.orderKey {
  width: 120px;
  font-size: 15px;
  text-align: center;
}

.orderValue {
  width: 400px;
}

</style>

<script>
/*
        username: 'sunpx',
        money:1000,
        mgState:true,
        mobile:13036786330,
        id:1278946041459900418,
        gmtCreate:'2021-01-15 09:29:23'
 */
export default {
  data() {
    return {
      goodsUserInfo: {},
      //充值记录
      goodsTopUpList: [],
      //订单列表
      mallOrderList: [],

      goodsTopUpPage: { current: 1, size: 10, total: 0 },
      mallOrderPage: { current: 1, size: 10, total: 0 },
      goodsTopUpVo: { page: { current: 1, size: 10 }, accountId: this.$route.query.id },
      mallOrderVo: { page: { current: 1, size: 10 }, userId: this.$route.query.id }
    }
  },
  mounted() {
    this.queryList()
    this.mallOrderListQuery()
    this.goodsTopUpListQuery()

  },
  methods: {
    stateFormat(r, c) {
      if (r.isPay === 1) {
        return '已支付'
      } else {
        return '未支付'
      }
    },

    //查询用户基本信息
    async queryList() {

      const { data: res } = await this.$http.get(
        '/goodsUser/getByCarryMoneyDetail/' + this.$route.query.id
      )
      if (res.code !== 20000)
        return this.$message.error(res.message)
      this.goodsUserInfo = res.data.dataInfo

    },
    async mallOrderListQuery() {
      const { data: res } = await this.$http.post(
        '/mallOrder/queryMallOrderList',
        this.mallOrderVo
      )

      if (res.code !== 20000)
        return this.$message.error(res.message)
      this.mallOrderList = res.data.dataList.records
      this.mallOrderPage.total = res.data.dataList.total
      this.mallOrderPage.current = res.data.dataList.current
      this.mallOrderPage.size = res.data.dataList.size

    },

    async goodsTopUpListQuery() {

      const { data: res } = await this.$http.post(
        '/topUp/queryTopUpList',
        this.goodsTopUpVo
      )

      if (res.code !== 20000)
        return this.$message.error(res.message)
      this.goodsTopUpList = res.data.dataList.records
      this.goodsTopUpPage.total = res.data.dataList.total
      this.goodsTopUpPage.current = res.data.dataList.current
      this.goodsTopUpPage.size = res.data.dataList.size

    },

    // 监听每页展示的条数
    topUpHandleSizeChange(newSize) {
      this.mallOrderVo.page.size = newSize
      this.mallOrderListQuery()
    },
    //监听页码
    topUpHandleCurrentChange(newCurrent) {
      this.mallOrderVo.page.current = newCurrent
      this.mallOrderListQuery()
    }
    ,// 监听每页展示的条数
    mallOrderHandleSizeChange(newSize) {
      this.mallOrderVo.page.size = newSize
      this.mallOrderListQuery()
    },
    //监听页码
    mallOrderHandleCurrentChange(newCurrent) {
      this.mallOrderVo.page.current = newCurrent
      this.mallOrderListQuery()
    },
    test(res) {
      res.query.path = 'goodsUserDetails'
      res.query.code = this.$route.query.jId
      res.query.id = this.goodsUserInfo.id
      res.query.name = '用户详情'
      this.$router.push(res)
    },
    Tes(){
      this.$router.push({path:'/goodsUserList'})
    },

  }
}
</script>
