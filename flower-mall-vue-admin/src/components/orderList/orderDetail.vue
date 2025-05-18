<template>

  <!--
  order_umber:订单编号
  user_id:用户名
  quantity:货物总数
  is_pay:是否支付
  payment_time:支付时间
  gmt_create:创建时间
  

 收件人
 联系方式
 邮政编码
 详细地址

  -->
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="path">{{name}}</el-breadcrumb-item>
      <el-breadcrumb-item>订单详情</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class='box-card'>
      <p style='font-size: 20px ;color: #000;margin: 10px;font-weight:bold'>订单信息</p>
      <!--订单-->
      <div class='orderMap'>
        <table >
          <tr>
            <td height='40' class='orderKey'>订单编号:</td>
            <td class='orderValue'>{{ orderInfo.orderUmber }}</td>
            <td class='orderKey'>用户编号:</td>
            <td class='orderValue'>{{ orderInfo.userId }}</td>
            <td class='orderKey'>货物总数:</td>
            <td class='orderValue'>{{ orderInfo.quantity }}</td>
          </tr>
          <tr>
            <td height='40' class='orderKey'>是否支付:</td>
            <td :class="orderInfo.isPay===1?'orderValueY orderValue':'orderValueW orderValue'">
              {{ orderInfo.isPay === 1 ? '已支付' : '待支付' }}
            </td>
            <td class='orderKey'>支付时间:</td>
            <td class='orderValue'>{{ orderInfo.paymentTime }}</td>
            <td class='orderKey'>创建时间:</td>
            <td class='orderValue'>{{ orderInfo.gmtCreate }}</td>
          </tr>
          <tr>
            <td height='40' class='orderKey'>支付金额:</td>
            <td class='orderValueY orderValue'>{{ orderInfo.amount }}</td>
            <td class='orderKey'><span v-if='orderInfo.isPay === 1'>状态</span></td>
            <td :class="orderInfo.status===1?'orderValueY orderValue':'orderValueW orderValue'"><span v-if='orderInfo.isPay === 1'>{{ orderInfo.status===1? '已发货':'未发货' }}</span></td>
            <td class='orderKey'></td>
            <td class='orderValue'></td>
          </tr>
        </table>
      </div>
      <p style='font-size: 20px ;color: #000 ;margin: 10px ;font-weight:bold'>地址信息</p>

      <div class='orderMap'>
        <table>
          <tr>
            <td height='40' class='orderKey'>收件人:</td>
            <td class='orderValue'>{{ goodsAddressInfo.consignee }}</td>
            <td class='orderKey'>联系方式:</td>
            <td class='orderValue'>{{ goodsAddressInfo.phone }}</td>
            <td class='orderKey'>邮政编码:</td>
            <td class='orderValue'>{{ goodsAddressInfo.mailCode }}</td>
          </tr>
          <tr>
            <td height='40' class='orderKey'>所属地:</td>
            <td class='orderValue'>
              {{ goodsAddressInfo.province + '-' + goodsAddressInfo.city + '-' + goodsAddressInfo.county }}
            </td>
            <td height='40' class='orderKey'>详细地址:</td>
            <td class='orderValue'>{{ goodsAddressInfo.address }}</td>
          </tr>
        </table>
      </div>

    </el-card>
    <el-card class='box-card' style='margin-top:20px '>
      <p style='font-size: 20px ;color: #000;margin: 10px;font-weight:bold'>商品列表</p>
      <el-table :data='MallGoodsList' border stripe :show-header='true' size='mini'
                :header-cell-style="{background:'#000','text-align':'center'}"
                :cell-style="{'text-align':'center'}">
        <el-table-column prop='imgUrl' label='图片'>
          <template slot-scope='scope'>
            <img :src="'http://127.0.0.1:8001/'+scope.row.imgUrl" width='100' height='100' class='head_pic'
                 alt='' />
          </template>
        </el-table-column>
        <el-table-column prop='goodsName' label='商品名称'></el-table-column>
        <el-table-column prop='univalent' label='单价'></el-table-column>
        <el-table-column prop='quantity' label='数量'></el-table-column>
        <el-table-column prop='amount' label='价格'></el-table-column>
      </el-table>
      <div style="text-align: center; margin-top: 20px">
        <el-button type='danger' size='mini' @click="Tes">返回</el-button>
      </div>
    </el-card>


  </div>

</template>


<script>
export default {
  data() {
    return {
      orderInfo: {},
      goodsAddressInfo: {},
      MallGoodsList: [],
      path:{ path: '/orderList',query:{id:''}},
      name:'订单列表'

    }
  },
  mounted() {
    this.initOrder()
  },
  methods: {

    Tes(){
      this.$router.push(this.path)
    },
    async initOrder() {
      if (this.$route.query.name==='用户详情'){
        this.path.path=this.$route.query.path
        this.name=this.$route.query.name
        this.path.query.id=this.$route.query.id
      }


      const { data: res } = await this.$http.get(
        '/mallOrder/getByIdMallOrder/' + this.$route.query.jId
      )
      if (res.code !== 20000)
        return this.$message.error(res.message)
      this.orderInfo = res.data.orderInfo
      this.goodsAddressInfo = res.data.goodsAddressInfo
      this.MallGoodsList = res.data.MallGoodsList
    }
  }
}
</script>

<style lang='less' scoped>
.orderMap {
  table {
    width: 100%;
    color: #000;
    margin-top: 20px;
  }

}

.orderValueY {
  color: chartreuse;
}

.orderValueW {
  color: red;
}

.orderKey {
  width: 100px;
  font-size: 15px;
  text-align: center;
}

.orderValue {
  width: 400px;
}

</style>
