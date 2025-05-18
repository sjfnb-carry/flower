<template xmlns="">
  <div class="block">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品类型表</el-breadcrumb-item>
      <el-breadcrumb-item>商品类型表列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>

      <!-- 搜索 -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="4">
          <!--搜索条件 -->
          <el-input v-model="input" placeholder="请输入内容" size="mini"></el-input>

        </el-col>
        <el-col :span="4">
          <!--搜索条件 -->
          <el-input v-model="input" placeholder="请输入内容" size="mini"></el-input>

        </el-col>
        <el-col :span="4">

          <el-button type="primary" @click="queryPage()" size="mini">搜索
          </el-button>

          <el-button type="primary" @click="dialogAddMallGoodsType=true,MallGoodsTypeInfo={},dialogBewrite='添加商品类型表'" size="mini">添加
          </el-button>
        </el-col>
      </el-row>


      <el-table :data="MallGoodsTypeList" border stripe  size='mini'
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}">
        <el-table-column prop="typeName" label="类型名称"></el-table-column>
        <el-table-column prop="imgType" label="类型描述" ></el-table-column>
        <el-table-column prop="status" label="状态" :formatter="stateFormat"></el-table-column>
        <el-table-column prop="sort" label="排序"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-tooltip class="item" effect="dark" content="编辑" placement="top-start" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                         @click="editMallGoodsType(scope.row)"></el-button>
            </el-tooltip>
            <!-- 删除 -->
            <el-tooltip class="item" effect="dark" content="删除" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="del(scope.row)"></el-button>
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

      <el-dialog :title="dialogBewrite" :visible.sync="dialogAddMallGoodsType" width="50%" @close="closeInit">
        <el-form :model="MallGoodsTypeInfo" ref="addMallGoodsTypeRef" label-width="70px" class="demo-form-inline" size="mini" :inline="true">

          <el-form-item label="类型名称">
            <el-input v-model="MallGoodsTypeInfo.typeName"></el-input>
          </el-form-item>
          <el-form-item label="类型描述">
            <el-input v-model="MallGoodsTypeInfo.imgType"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="MallGoodsTypeInfo.status" placeholder="请选择类型状态">
              <el-option label="开启" :value="1"></el-option>
              <el-option label="关闭" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="MallGoodsTypeInfo.sort"></el-input>
          </el-form-item>

          <el-form-item label="首页推荐">
            <el-select v-model="MallGoodsTypeInfo.isHome" placeholder="是否在首页推荐">
              <el-option label="推荐" :value="1"></el-option>
              <el-option label="不推荐" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="MallGoodsTypeInfo.createTime"></el-input>
          </el-form-item>
          <el-form-item>
            <template slot-scope="scope">
              <el-button type="primary" @click="addMallGoodsType()">保存</el-button>
              <el-button @click="dialogAddMallGoodsType=false">取消</el-button>
            </template>
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
      MallGoodsTypeList: [],
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      MallGoodsTypeVo: {
        page: {
          current: 1,
          size: 5
        }
      },
      dialogAddMallGoodsType: false,
      MallGoodsTypeInfo: {
      },
      dialogBewrite: ""

    }
  },
  created() {
    this.queryPage()
  },
  methods: {
    // 监听每页展示的条数
    handleSizeChange(newSize) {
      this.MallGoodsTypeVo.page.size = newSize
      this.queryPage()
    },
    //监听页码
    handleCurrentChange(newCurrent) {
      this.MallGoodsTypeVo.page.current = newCurrent
      this.queryPage()
    },
    stateFormat(row, column){

      if (row.status == 1) {
        return '开启'
      } else  {
        return '关闭'
      }
    },
    //列表
    async queryPage() {
      const {data: res} = await this.$http.post(
          '/mallGoodsType/queryMallGoodsTypeList',
          this.MallGoodsTypeVo
      )

      if (res.code != 20000)
        return this.$message.error(res.message)
      this.MallGoodsTypeList = res.data.dataList.records
      this.page.total = res.data.dataList.total
      this.page.current = res.data.dataList.current
      this.page.size = res.data.dataList.size
    },
    //查询详情
    async editMallGoodsType(row) {
      this.dialogAddMallGoodsType = true;
      this.dialogBewrite = '编辑商品类型表'
      const id = row.id

      const {data: res} = await this.$http.get(
          '/mallGoodsType/getByIdMallGoodsType/' + id
      )
      if (res.code != 20000) return this.$message.error(res.message)
      this.MallGoodsTypeInfo = res.data.entity
    },
    closeInit() {
      this.addMallGoodsTypeForm = {}
      this.dialogAddMallGoodsType = false
    },
    addMallGoodsType() {
      this.$refs.addMallGoodsTypeRef.validate(async valid => {
        if (!valid) {
          return
        }
        const {data: res} = await this.$http.post(
            '/mallGoodsType/saveOrUpdateMallGoodsType/',
            this.MallGoodsTypeInfo
        )
        if (res.code != 20000) return this.$message.error(res.message)
        this.dialogAddMallGoodsType = false
        this.queryPage()
        return this.$message.success(res.message)
      })
    },
    async del(status) {

      this.$confirm('确认永久删除吗？')
          .then(async _ => {
            const {data: res} = await this.$http.delete(
                '/mallGoodsType/removeByIdMallGoodsType/' + status.id
            )
            if (res.code != 20000) return this.$message.error(res.message)
            this.queryPage()
            return this.$message.success(res.message)
          })
          .catch(_ => {
            return this.$message.info("取消删除")
          });


    }
  },


}
</script>