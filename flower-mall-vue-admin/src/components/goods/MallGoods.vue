<template>
  <div class='block'>
    <el-breadcrumb separator='/'>
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品表</el-breadcrumb-item>
      <el-breadcrumb-item>商品表列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <!-- 搜索 -->
      <el-row :gutter='20' style='margin: 10px'>
        <el-col :span='4'>
          <el-button size='mini' type='primary' @click="dialogAddMallGoods=true,MallGoodsInfo={},dialogBewrite='添加商品表'">
            添加
          </el-button>
        </el-col>
      </el-row>


      <el-table :data='MallGoodsList' border stripe size='mini'
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}">
        <el-table-column prop='goodsName' label='商品名称'></el-table-column>
        <el-table-column prop='goodsPayType' label='支付方式' :formatter='stateFormat' width='80'></el-table-column>
        <el-table-column prop='goodsAmount' label='数量' width='80'></el-table-column>
        <el-table-column prop='goodsPrice' label='价格' width='80'></el-table-column>
        <el-table-column prop='goodsDiscount' label='优惠价格' width='80'></el-table-column>
        <el-table-column prop='status' label='商品状态' width='80' :formatter='statusFormat'></el-table-column>
        <el-table-column prop='goodsOrigin' label='产地' width='80'></el-table-column>
        <el-table-column prop='goodsAddress' label='描述' width='400' :show-overflow-tooltip='true'></el-table-column>
<!--        <el-table-column prop='createTime' label='创建时间' width='200'></el-table-column>-->
        <el-table-column label='操作' width='180px'>
          <template slot-scope='scope'>

            <!-- 修改 -->
            <el-tooltip class='item' effect='dark' content='上传图片' placement='top-start' :enterable='false'>
              <el-button type='warning' icon='el-icon-camera-solid' size='mini'
                         @click='getEditMallGoodsId(scope.row.id)'></el-button>
            </el-tooltip>

            <!-- 修改 -->
            <el-tooltip class='item' effect='dark' content='编辑' placement='top-start' :enterable='false'>
              <el-button type='primary' icon='el-icon-edit' size='mini'
                         @click='editMallGoods(scope.row)'></el-button>
            </el-tooltip>
            <!-- 删除 -->
            <el-tooltip class='item' effect='dark' content='删除' placement='top-start' :enterable='false'>
              <el-button type='danger' icon='el-icon-delete' size='mini' @click='del(scope.row)'></el-button>
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

      <el-dialog :title='dialogBewrite' :visible.sync='dialogAddMallGoods' width='50%' @close='closeInit'>
        <el-form :model='MallGoodsInfo' ref='addMallGoodsRef' label-width='70px' class='demo-form-inline' size='mini'
                 :inline='true'>
          <el-form-item label='商品名称'>
            <el-input v-model='MallGoodsInfo.goodsName'></el-input>


          </el-form-item>
          <el-form-item label='支付方式'>
            <!--            <el-input v-model="MallGoodsInfo.goodsPayType"></el-input>-->
            <el-select v-model='MallGoodsInfo.goodsPayType' placeholder='请选择付款方式'>
              <el-option label='线上支付' :value='1'></el-option>
              <el-option label='货到付款' :value='2'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label='数量'>
            <el-input v-model='MallGoodsInfo.goodsAmount'></el-input>
          </el-form-item>
          <el-form-item label='价格'>
            <el-input v-model='MallGoodsInfo.goodsPrice'></el-input>
          </el-form-item>
          <el-form-item label='优惠价格'>
            <el-input v-model='MallGoodsInfo.goodsDiscount'></el-input>
          </el-form-item>
          <el-form-item label='商品类型'>
            <el-select v-model='MallGoodsInfo.goodsTypeCode' placeholder='请选择商品类型'>
              <el-option v-for='item in MallGoodsTypeListAll' :label='item.key' :value='item.value'
                         :key='item.value'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label='状态'>
            <!--            <el-input v-model="MallGoodsInfo.status"></el-input>-->
            <el-select v-model='MallGoodsInfo.status' placeholder='请选择商品状态'>
              <el-option label='开启' :value='1'></el-option>
              <el-option label='关闭' :value='2'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label='产地'>
            <el-input v-model='MallGoodsInfo.goodsOrigin'></el-input>
          </el-form-item>

          <div class='aa'>
            <span>
              描述:
            </span>
            <el-input v-model='MallGoodsInfo.goodsAddress' type='textarea' :rows='4'></el-input>
          </div>


          <el-form-item label='标签'>
            <el-select v-model="goodsMarkCode" multiple filterable allow-create default-first-option placeholder="请选商品章标签">
              <el-option v-for="item in options" :key="item.value" :label="item.key" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
<!--          <el-form-item label='创建时间'>
            <el-input v-model='MallGoodsInfo.createTime'></el-input>
          </el-form-item>-->
          <el-form-item label='排序'>
            <el-input v-model='MallGoodsInfo.sort'></el-input>
          </el-form-item>
        </el-form>
        <el-button type='primary' @click='addMallGoods()'>保存</el-button>
        <el-button @click='dialogAddMallGoods=false'>取消</el-button>
      </el-dialog>

      <el-drawer
        title='我是标题'
        :visible.sync='drawer'
        :with-header='false'
        size='35%'
        :before-close='gbct'
        style='height: 100%'

      >
        <div style='overflow-y:auto; height: 40%'>
          <el-table :data='MallGoodsImgList' border stripe :show-header='false'
                    :header-cell-style="{'text-align':'center'}"
                    :cell-style="{'text-align':'center'}">
            <el-table-column prop='imgUrl' label='图片'>
              <template slot-scope='scope'>
                <img :src="'http://127.0.0.1:8001/'+scope.row.imgUrl" width='100' height='100' class='head_pic'
                     alt='' />
              </template>
            </el-table-column>
            <el-table-column prop='imgAddress' label='图片描述'></el-table-column>
            <el-table-column label='操作' width='180'>
              <template slot-scope='scope'>

                <!-- 修改 -->
                <el-tooltip class='item' effect='dark' content='编辑' placement='top-start' :enterable='false'>
                  <el-button type='primary' icon='el-icon-edit' size='mini'
                             @click='editMallGoodsImg(scope.row.id)'></el-button>
                </el-tooltip>
                <!-- 删除 -->
                <el-tooltip class='item' effect='dark' content='删除' placement='top-start' :enterable='false'>
                  <el-button type='danger' icon='el-icon-delete' size='mini' @click='delIMG(scope.row)'></el-button>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </div>

        商城管理系统
        <div style='margin: 20px;'>
          <el-form :model='MallGoodsInfo' ref='addMallGoodsImgRef' label-width='70px' class='demo-form-inline'
                   size='mini' :inline='true'>
            <el-form-item label='图片类型'>
              <el-select v-model='MallGoodsImgInfo.imgType' placeholder='请选择付款方式'>
                <el-option label='主图' :value='1'></el-option>
                <el-option label='详情图' :value='2'></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label='状态'>
              <el-switch
                v-model='MallGoodsImgInfo.status'
                active-color='#13ce66'
                inactive-color='#ff4949'
                :active-value='1'
                :inactive-value='2'
                active-text='开启'
                inactive-text='关闭'
              >
              </el-switch>
            </el-form-item>
            <el-form-item label='图片描述'>
              <el-input v-model='MallGoodsImgInfo.imgAddress'></el-input>
            </el-form-item>

            <el-form-item v-if='MallGoodsImgInfo.createTime' label='创建时间'>
              <el-input v-model='MallGoodsImgInfo.createTime' disabled></el-input>
            </el-form-item>
            <el-form-item label='排序'>
              <el-input-number v-model='MallGoodsImgInfo.sort' :min='0' :max='100' label='描述文字'></el-input-number>
            </el-form-item>

            <!--            <el-form-item style="width: 90%; display: block;" v-if="MallGoodsImgInfo.imgUrl">-->
            <!--              <img :src="MallGoodsImgInfo.imgUrl" alt="" width="200" height="200"/>-->
            <!--            </el-form-item>-->

            <el-form-item label='图片'>
              <el-upload ref='uploader' :auto-upload='false' :class="imageUrl ? 'hide': ''" :limit='1'
                         :on-change='handleChange'
                         :on-remove='removeChange'
                         :file-list='filelist'
                         action='#'
                         list-type='picture-card' style='width: 148px;height: 148px;margin: 0 auto;'>
                <!--                <img :src="MallGoodsImgInfo.imgUrl" v-if="MallGoodsImgInfo.imgUrl" alt="" width="100" height="100"/>-->
                <i class='el-icon-plus avatar-uploader-icon'></i>
              </el-upload>
              <el-button type='warning' v-if='imageUrl' @click='uploadGoodsImg' i>上传</el-button>
            </el-form-item>
            <el-form-item label='操作'>
              <el-button type='primary' @click='addMallGoodsImg'>保存</el-button>
              <el-button type='danger' v-if='aaa' @click='cz()'>改为新增</el-button>
              <el-button @click='gbct()'>取消</el-button>
            </el-form-item>
          </el-form>
        </div>


      </el-drawer>

    </el-card>


  </div>
</template>
<script>
export default {
  data() {
    return {
      drawer: false,
      MallGoodsList: [],
      imageUrl: '',
      ctid: '',
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      aaa: '',
      filelist: [],
      MallGoodsImgList: [],
      MallGoodsVo: {
        page: {
          current: 1,
          size: 5
        }
      },
      dialogAddMallGoods: false,
      MallGoodsInfo: {},
      dialogBewrite: '',
      MallGoodsImgInfo: {
        imgType: 2,
        imgUrl: '',
        imgAddress: '',
        status: 1,
        sort: 0


      },
      MallGoodsTypeListAll: [],
      options:[],
      goodsMarkCode:[]

    }
  },
  created() {
    this.queryPage()
    this.getMallGoodsTypeList()
    this.getMallGoodsTagList()
  },
  methods: {


    gbct() {
      this.removeChange()
      this.MallGoodsImgList = []
      this.cz()
      this.drawer = false


    },
    async delIMG(status) {

      this.$confirm('确认永久删除吗？')
        .then(async _ => {
          const { data: res } = await this.$http.delete(
            '/mallGoodsImg/removeByIdMallGoodsImg/' + status.id
          )

          if (res.code != 20000) return this.$message.error(res.message)
          this.getEditMallGoodsId(this.ctid)
          return this.$message.success(res.message)
        })
        .catch(_ => {
          return this.$message.info('取消删除')
        })


    },
    cz() {
      this.removeChange()
      this.MallGoodsImgInfo = { imgType: 2, imgUrl: '', imgAddress: '', status: 1, sort: 0 }
      this.aaa = ''
    },

    async uploadGoodsImg() {
      let form = new FormData()
      form.append('httpFile', this.imageUrl)
      form.append('goodsId', this.ctid)
      const { data: resAll } = await this.$http.post('/mallGoodsImg/uploadMallGoodsImg', form
        , {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Token': window.sessionStorage.getItem('token')
          }
        })
      this.MallGoodsImgInfo.imgUrl = 'http://127.0.0.1:8001' + resAll.data.path
      this.imageUrl = 'http://127.0.0.1:8001' + resAll.data.path
      this.filelist = [{ url: 'http://127.0.0.1:8001' + resAll.data.path }]
    },
    stateFormat(r, c) {
      if (r.goodsPayType == 1) {
        return '在线支付'
      } else {
        return '货到付款'
      }
    },
    removeChange() {
      this.filelist = []
      this.imageUrl = ''
      // file.raw=null
    },
    handleChange(file) {
      this.imageUrl = file.raw
    },
    statusFormat(r, c) {
      if (r.status == 1) {
        return '开启'
      } else {
        return '关闭'
      }
    },
    async editMallGoodsImg(id) {
      this.aaa = 'a'
      const { data: resAll } = await this.$http.get(
        '/mallGoodsImg/getByIdMallGoodsImg/' + id
      )
      if (resAll.code != 20000) return this.$message.error(res.message)
      this.MallGoodsImgInfo = resAll.data.entity
      this.filelist = [{ url: 'http://127.0.0.1:8001' + this.MallGoodsImgInfo.imgUrl }]
      this.MallGoodsImgInfo.goodsId = resAll.data.entity.goodsId + ''
      this.imageUrl = 'http://127.0.0.1:8001' + resAll.data.path
    },
    async getEditMallGoodsId(res) {
      const a = res
      this.ctid = a
      this.drawer = true
      const { data: resAll } = await this.$http.get(
        '/mallGoodsImg/queryMallGoodsImgList/' + res
      )

      this.MallGoodsImgList = resAll.data.dataList
    },


    // 监听每页展示的条数
    handleSizeChange(newSize) {
      this.MallGoodsVo.page.size = newSize
      this.queryPage()
    },
    //监听页码
    handleCurrentChange(newCurrent) {
      this.MallGoodsVo.page.current = newCurrent
      this.queryPage()
    },
    //列表
    async queryPage() {
      const { data: res } = await this.$http.post(
        '/mallGoods/queryMallGoodsList',
        this.MallGoodsVo
      )

      if (res.code != 20000)
        return this.$message.error(res.message)
      this.MallGoodsList = res.data.dataList.records
      this.page.total = res.data.dataList.total
      this.page.current = res.data.dataList.current
      this.page.size = res.data.dataList.size
    },
    //查询详情
    async editMallGoods(row) {
      this.dialogAddMallGoods = true
      this.dialogBewrite = '编辑商品表'
      const id = row.id

      const { data: res } = await this.$http.get(
        '/mallGoods/getByIdMallGoods/' + id
      )
      if (res.code != 20000) return this.$message.error(res.message)
      this.MallGoodsInfo = res.data.entity
      this.goodsMarkCode = res.data.entity.goodsMarkCode.split(",")
    },
    async getMallGoodsTypeList() {
      const { data: res } = await this.$http.get(
        '/mallGoodsType/mallGoodsTypeList/'
      )
      if (res.code != 20000) return this.$message.error(res.message)

      this.MallGoodsTypeListAll = res.data.dataList
      console.log(this.MallGoodsTypeListAll)
    },

    async getMallGoodsTagList() {
      const { data: res } = await this.$http.get(
        '/mallGoods/removeByIdMallGoodsTag/'
      )
      if (res.code != 20000) return this.$message.error(res.message)
      this.options=res.data.dataList
    },
    closeInit() {
      this.addMallGoodsForm = {}
      this.dialogAddMallGoods = false
      this.goodsMarkCode=[]
    },
    addMallGoods() {
      this.$refs.addMallGoodsRef.validate(async valid => {
        if (!valid) {
          return
        }
        this.MallGoodsInfo.goodsMarkCode=this.goodsMarkCode.toString()
        const { data: res } = await this.$http.post(
          '/mallGoods/saveOrUpdateMallGoods/',
          this.MallGoodsInfo
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddMallGoods = false
        this.queryPage()
        return this.$message.success(res.message)
      })
    },
    addMallGoodsImg() {
      this.MallGoodsImgInfo.goodsId = this.ctid
      this.$refs.addMallGoodsImgRef.validate(async valid => {
        if (!valid) {
          return
        }
        const { data: res } = await this.$http.post(
          '/mallGoodsImg/saveOrUpdateMallGoodsImg/',
          this.MallGoodsImgInfo
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.MallGoodsImgInfo = { imgType: 2, imgUrl: '', imgAddress: '', status: 1, sort: 0 }
        this.getEditMallGoodsId(this.ctid)
        this.filelist = []
        this.imageUrl = ''
        return this.$message.success(res.message)
      })
    },
    async del(status) {

      this.$confirm('确认永久删除吗？')
        .then(async _ => {
          const { data: res } = await this.$http.delete(
            '/mallGoods/removeByIdMallGoods/' + status.id
          )
          if (res.code != 20000) return this.$message.error(res.message)
          this.queryPage()
          return this.$message.success(res.message)
        })
        .catch(_ => {
          return this.$message.info('取消删除')
        })


    }
  }


}
</script>
<style lang='less' scoped>
.aall {
  margin: 10px;
  margin-top: 20px;
}

.hide::v-deep .el-upload--picture-card {
  display: none;
}
</style>