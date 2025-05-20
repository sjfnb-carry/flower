<template>
	<div>
		<div class="center_view">
			<el-tabs
			    v-model="orderStatus"
			    type="card"
			    class="demo-tabs"
				@tab-change="orderTabClick"
				>
				<el-tab-pane label="全部" :name="''"></el-tab-pane>
				<el-tab-pane label="未支付" name="未支付" v-if="btnAuth(`orders/未支付`,'查看')"></el-tab-pane>
				<el-tab-pane label="拼团中" name="拼团中" v-if="btnAuth(`orders/拼团中`,'查看')"></el-tab-pane>
				<el-tab-pane label="已支付" name="已支付" v-if="btnAuth(`orders/已支付`,'查看')"></el-tab-pane>
				<el-tab-pane label="已发货" name="已发货" v-if="btnAuth(`orders/已发货`,'查看')"></el-tab-pane>
				<el-tab-pane label="已完成" name="已完成" v-if="btnAuth(`orders/已完成`,'查看')"></el-tab-pane>
				<el-tab-pane label="已退款" name="已退款" v-if="btnAuth(`orders/已退款`,'查看')"></el-tab-pane>
				<el-tab-pane label="已取消" name="已取消" v-if="btnAuth(`orders/已取消`,'查看')"></el-tab-pane>
			</el-tabs>
			<div class="list_search_view">
				<el-form :model="searchQuery" class="search_form" >
					<div class="search_view">
						<div class="search_label">
							订单编号：
						</div>
						<div class="search_box">
							<el-input class="search_inp" v-model="searchQuery.orderid" placeholder="订单编号"
								clearable>
							</el-input>
						</div>
					</div>
					<div class="search_view">
						<div class="search_label">
							审核状态：
						</div>
						<div class="search_box">
							<el-select
								class="search_sel"
								clearable
								v-model="searchQuery.sfsh"
								placeholder="审核状态"
								>
								<el-option v-for="item in approvalLists" :label="item" :value="item"></el-option>
							</el-select>
						</div>
					</div>
					<div class="search_btn_view">
						<el-button class="search_btn" type="primary" @click="searchClick()" size="small">搜索</el-button>
					</div>
				</el-form>
				<div class="btn_view">
					<el-button class="add_btn" type="success" @click="addClick" v-if="btnAuth('orders','新增')">
						新增
					</el-button>
					<el-button class="del_btn" type="danger" :disabled="selRows.length?false:true" @click="delClick(null)"  v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'删除')">
						删除
					</el-button>
					<el-button class="other_btn" type="default" :disabled="selRows.length?false:true" @click="exportClick" v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'导出')">
						导出
					</el-button>
                    <el-button class="statis_btn" type="warning" @click="echartClick1" v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'销售数据')">
                        销售数据
                    </el-button>
				</div>
			</div>
			<el-table
				v-loading="listLoading"
				border
				:stripe='true'
				@selection-change="handleSelectionChange"
				ref="table"
				v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'查看')"
				:data="list"
				@row-click="listChange">
				<el-table-column :resizable='true' align="left" header-align="left" type="selection" width="55" />
				<el-table-column label="序号" width="70" :resizable='true' align="left" header-align="left">
					<template #default="scope">{{ (listQuery.page-1)*listQuery.limit+scope.$index + 1}}</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="orderid"
					label="订单编号">
					<template #default="scope">
						{{scope.row.orderid}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="goodname"
					label="商品名称">
					<template #default="scope">
						{{scope.row.goodname}}
					</template>
				</el-table-column>
				<el-table-column label="图片" min-width="140" width="120" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">
						<div v-if="scope.row.picture">
							<el-image v-if="scope.row.picture.substring(0,4)=='http'" preview-teleported
								:preview-src-list="[scope.row.picture.split(',')[0]]"
								:src="scope.row.picture.split(',')[0]" style="width:100px;height:100px"></el-image>
							<el-image v-else preview-teleported
								:preview-src-list="[$config.url+scope.row.picture.split(',')[0]]"
								:src="$config.url+scope.row.picture.split(',')[0]" style="width:100px;height:100px">
							</el-image>
						</div>
						<div v-else>无图片</div>
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="buynumber"
					label="购买数量">
					<template #default="scope">
						{{scope.row.buynumber}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="price"
					label="单价">
					<template #default="scope">
						{{scope.row.price}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="discountprice"
					label="折扣价">
					<template #default="scope">
						{{scope.row.discountprice}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="total"
					label="总价">
					<template #default="scope">
						{{scope.row.total}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="discounttotal"
					label="折扣总价格">
					<template #default="scope">
						{{scope.row.discounttotal}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="type"
					:formatter="orderStatusFormatter"
					label="支付类型">
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="status"
					label="订单状态">
					<template #default="scope">
						{{scope.row.status}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="address"
					label="地址">
					<template #default="scope">
						{{scope.row.address}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="tel"
					label="电话">
					<template #default="scope">
						{{scope.row.tel}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="consignee"
					label="收货人">
					<template #default="scope">
						{{scope.row.consignee}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="remark"
					label="备注">
					<template #default="scope">
						{{scope.row.remark}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="addtime"
					label="创建时间">
					<template #default="scope">
						{{scope.row.addtime}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="shangjiamingcheng"
					label="商户名称">
					<template #default="scope">
						{{scope.row.shangjiamingcheng}}
					</template>
				</el-table-column>
				<el-table-column min-width="140"
					:resizable='true'
					:sortable='true'
					align="left"
					header-align="left"
					prop="groupno"
					label="团购编号">
					<template #default="scope">
						{{scope.row.groupno}}
					</template>
				</el-table-column>
                <template v-if="!orderStatus||orderStatus=='已完成'||orderStatus=='已支付'">
                    <el-table-column label="审核回复" min-width="140" :resizable='true' :sortable='true' align="left" header-align="left">
                        <template #default="scope">
                            {{scope.row.shhf}}
                        </template>
                    </el-table-column>
                    <el-table-column label="审核状态" min-width="140" :resizable='true' :sortable='true' align="left" header-align="left">
                        <template #default="scope">
                            <el-tag type="success" v-if="scope.row.sfsh=='是'">通过</el-tag>
                            <el-tag type="danger" v-else-if="scope.row.sfsh=='否'">未通过</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.sfsh=='待审核'">待审核</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="审核" v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'审核')" :resizable='true' :sortable='true' align="left" header-align="left">
                        <template #default="scope">
                            <el-button v-if="scope.row.sfsh=='待审核'" size="small" @click="approvalClick(scope.row)">审核</el-button>
                        </template>
                    </el-table-column>
                </template>
				<el-table-column prop="addtime" label="下单时间" min-width="140" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">
						{{scope.row.addtime}}
					</template>
				</el-table-column>
				<el-table-column label="操作" width="300" :resizable='true' :sortable='true' align="left" header-align="left">
					<template #default="scope">
						<el-button class="view_btn" type="info" v-if=" btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'查看')" @click="infoClick(scope.row.id)">
							详情
						</el-button>
						<el-button class="edit_btn" type="primary" @click="editClick(scope.row.id)" v-if=" btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'修改')">
							修改						</el-button>
						<el-button class="del_btn" type="danger" @click="delClick(scope.row.id)"  v-if="btnAuth('orders'+(orderStatus?'/'+orderStatus:''),'删除')">
							删除						</el-button>
						<el-button class="operate_btn" v-if="btnAuth('orders'+(scope.row.status?'/'+scope.row.status:''),'物流')" type="warning" @click="logisticsClick(scope.row)">
							物流
						</el-button>
						<el-button class="operate_btn" v-if="btnAuth('orders'+(scope.row.status?'/'+scope.row.status:''),'发货')" type="warning" @click="sendGoodClick(scope.row)">
							发货
						</el-button>
						<el-button class="operate_btn" v-if="btnAuth('orders'+(scope.row.status?'/'+scope.row.status:''),'确认收货')&&scope.row.userid==user.id" type="warning" @click="confirmGoodClick(scope.row)">
							确认收货
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				background
				:layout="layouts.join(',')"
				:total="total"
				:page-size="listQuery.limit"
                v-model:current-page="listQuery.page"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="false"
				:style='{"width":"100%","padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","fontWeight":"500"}'
				:page-sizes="[10, 20, 30, 40, 50, 100]"
				@size-change="sizeChange"
				@current-change="currentChange"  />
		</div>
		<formModel ref="formRef" @formModelChange="formModelChange"></formModel>
		<Approval ref="approvalRef" :tableName="tableName" @shChange="shChange"></Approval>
		<!-- 统计图弹窗 -->
		<el-dialog v-model="echartVisible" title="统计图" width="70%">
            <div  id="totalEchart1" style="width:100%;height:600px;"></div>
			<template #footer>
				<span class="formModel_btn_box">
					<el-button class="cancel_btn" @click="echartVisible=false">取消</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>
<script setup>
	import axios from 'axios'
    import moment from "moment"
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		onMounted,
		watch,
		computed,
		inject
	} from 'vue'
	import {
		useRoute,
		useRouter
	} from 'vue-router'
	import {
		ElMessageBox
	} from 'element-plus'
	import {
		useStore
	} from 'vuex';
	const store = useStore()
	const user = computed(()=>store.getters['user/session'])
	const avatar = ref(store.state.user.avatar)
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	import formModel from './formModel.vue'
	//基础信息

	const tableName = 'orders'
	const formName = '商品订单'
	const route = useRoute()
	watch(() => route.query,() => {
		init()
	})
	//基础信息
	onMounted(()=>{
	})
	//列表数据
	const list = ref(null)
	const table = ref(null)
	const listQuery = ref({
		page: 1,
		limit: 20,
		sort: 'id',
		order: 'desc'
	})
	const searchQuery = ref({})
	const selRows = ref([])
	const listLoading = ref(false)
	const listChange = (row) =>{
		nextTick(()=>{
			//table.value.clearSelection()
			table.value.toggleRowSelection(row)
		})
	}
	//列表
	const getList = () => {
		listLoading.value = true
		let params = JSON.parse(JSON.stringify(listQuery.value))
		params['sort'] = 'id'
		params['order'] = 'desc'
		if(orderStatus.value){
			params['status'] = orderStatus.value
		}
		if(searchQuery.value.orderid&&searchQuery.value.orderid!=''){
			params['orderid'] = '%' + searchQuery.value.orderid + '%'
		}
		if(searchQuery.value.sfsh && searchQuery.value.sfsh!=''){
			params['sfsh'] = searchQuery.value.sfsh
		}
		context.$http({
			url: `${tableName}/page`,
			method: 'get',
			params: params
		}).then(res => {
			listLoading.value = false
			list.value = res.data.data.list
			total.value = Number(res.data.data.total)
		})
	}
	//删
	const delClick = (id) => {
		let ids = ref([])
		if (id) {
			ids.value = [id]
		} else {
			if (selRows.value.length) {
				for (let x in selRows.value) {
					ids.value.push(selRows.value[x].id)
				}
			} else {
				return false
			}
		}
		ElMessageBox.confirm(`是否删除选中${formName}`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(() => {
			context.$http({
				url: `${tableName}/delete`,
				method: 'post',
				data: ids.value
			}).then(res => {
				context?.$toolUtil.message('删除成功', 'success',()=>{
					getList()
				})
			})
		}).catch(_ => {})
	}
	//多选
	const handleSelectionChange = (e) => {
		selRows.value = e
	}
	//列表数据
	//分页
	const total = ref(0)
	const layouts = ref(["total","prev","pager","next","sizes","jumper"])
	const sizeChange = (size) => {
		listQuery.value.limit = size
		getList()
	}
	const currentChange = (page) => {
		listQuery.value.page = page
		getList()
	}
	//分页
	//权限验证
	const btnAuth = (e,a)=>{
		return context?.$toolUtil.isAuth(e,a)
	}
    const shChange = async (type,row)=>{
        if(type=='是'){
            let res = await context.$http.get(`${row.role}/info/${row.userid}`)
            let userinfo = res.data.data
            context.$http({
            	url: `${row.tablename}/info/${row.goodid}`,
            	method: 'get'
            }).then(res => {
            	let data = res.data.data
            	// 如果商品存在库存，则加回去
            	if (data.alllimittimes) {
            		data.alllimittimes = parseInt(data.alllimittimes) + parseInt(row.buynumber)
            		// 更新商品库存
            		context.$http({
            			url: `${row.tablename}/update`,
            			method: 'post',
            			data: data
            		}).then(obj1 => {})
            	}
            	if (row.type == 2) {
            		// 如果是积分兑换，则把减去的积分加回去
            		userinfo.jf = parseInt(userinfo.jf) + parseInt(row.total)
            		// 更新用户信息
            		context.$http({
            			url: `${row.role}/update`,
            			method: 'post',
            			data: userinfo
            		}).then(obj => {
            			row.status = '已退款'
            			// 修改订单状态
            			context.$http({
            				url: 'orders/update',
            				method: 'post',
            				data: row
            			}).then(res1 => {
                            context.$message.success("退款成功")
            			})
            		})
            	} else {
            		// 如果是购物或者团购模式，且商品存在积分，则把加上的积分减去
            		if (data.jf) {
            			userinfo.jf = parseInt(userinfo.jf) - parseInt(row.total)
            		}
            		// 把减去的余额加回去
            		userinfo.money = (parseFloat(userinfo.money) + parseFloat(row.total)).toFixed(2)
            		// 更新用户信息
            		context.$http({
            			url: `${row.role}/update`,
            			method: 'post',
            			data: userinfo
            		}).then(obj => {
            			row.status = '已退款'
            			// 修改订单状态
            			context.$http({
            				url: 'orders/update',
            				method: 'post',
            				data: row
            			}).then(res1 => {
                            context.$message.success("退款成功")
            			})
            		})
            	}
            })
        }
        searchClick()
    }
	//搜索
	const searchClick = () => {
		listQuery.value.page = 1
		getList()
	}
	//表单
	const formRef = ref(null)
	const formModelChange=()=>{
		searchClick()
	}
	const addClick = ()=>{
		formRef.value.init()
	}
	const editClick = (id=null)=>{
		if(id){
			formRef.value.init(id,'edit')
			return
		}
		if(selRows.value.length){
			formRef.value.init(selRows.value[0].id,'edit')
		}
	}

	const infoClick = (id=null)=>{
		if(id){
			formRef.value.init(id,'info')
		}
		else if(selRows.value.length){
			formRef.value.init(selRows.value[0].id,'info')
		}
	}
	// 表单
	// 预览文件
	const preClick = (file) =>{
		if(!file){
			context?.$toolUtil.message('文件不存在','error')
		}
		window.open(context?.$config.url + file)
	}
	// 下载文件
	const download = (file) => {
		if(!file){
			context?.$toolUtil.message('文件不存在','error')
		}
		let arr = file.replace(new RegExp('file/', "g"), "")
		axios.get((location.href.split(context?.$config.name).length>1 ? location.href.split(context?.$config.name)[0] :'') + context?.$config.name + '/file/download?fileName=' + arr, {
			headers: {
				token: context?.$toolUtil.storageGet('Token')
			},
			responseType: "blob"
		}).then(({
			data
		}) => {
			const binaryData = [];
			binaryData.push(data);
			const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
				type: 'application/pdf;chartset=UTF-8'
			}))
			const a = document.createElement('a')
			a.href = objectUrl
			a.download = arr
			// a.click()
			// 下面这个写法兼容火狐
			a.dispatchEvent(new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
				view: window
			}))
			window.URL.revokeObjectURL(data)
		})
	}
    import '@/assets/js/echarts-theme'
	// 统计图1
	const echarts = inject("echarts")
	const echartVisible = ref(false)
	const echartClick1 = ()=>{
		echartVisible.value = true
		nextTick(()=>{
			var totalEchart1 = echarts.init(document.getElementById("totalEchart1"),'theme');
			context.$http({
				url: `${tableName}/value/goodname/total?order=desc`,
				method: 'get'
			}).then(res=>{
				let obj = res.data.data
				let xAxis = [];
				let yAxis = [];
				let dataList = []
				for(let i=0;i<obj.length;i++){
				    xAxis.push(obj[i].goodname);
				    yAxis.push(parseFloat((obj[i].total)));
                    dataList.push({
				        value: parseFloat((obj[i].total)),
				        name: obj[i].goodname
				    })
				}
				var option = {};
                option = {
    title: {
        text: '销售数据',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b} : {c}'
    },
    xAxis: {
        data: xAxis,
        type: 'category',
    },
    yAxis: {
        type: 'value'
    },
    series:{
        data: yAxis,
        type: 'line'
    }
}
                Object.assign(option.series,{smooth: true})
				// 使用刚指定的配置项和数据显示图表。
				totalEchart1.setOption(option);
				  //根据窗口的大小变动图表
				window.onresize = function() {
				    totalEchart1.resize();
				};
			})
		})
	}
	//导出选中数据
	const exportClick = () => {
		import('@/utils/Export2Excel').then(excel => {
			const tHeader = [
				"订单编号",
				"商品表名",
				"商品id",
				"商品名称",
				"图片",
				"购买数量",
				"单价",
				"折扣价",
				"总价",
				"折扣总价格",
				"支付类型",
				"订单状态",
				"地址",
				"电话",
				"收货人",
				"备注",
				"物流",
				"用户角色",
				"创建时间",
				"用户id",
				"是否审核",
				"回复内容",
				"商户名称",
				"团购编号",
			]
			const filterVal = [
				"orderid",
				"tablename",
				"goodid",
				"goodname",
				"picture",
				"buynumber",
				"price",
				"discountprice",
				"total",
				"discounttotal",
				"type",
				"status",
				"address",
				"tel",
				"consignee",
				"remark",
				"logistics",
				"role",
				"addtime",
				"userid",
				"sfsh",
				"shhf",
				"shangjiamingcheng",
				"groupno",
			]
			excel.export_json_to_excel2(tHeader, selRows.value, filterVal, formName)
		})
	}
	//审核
	import Approval from '@/components/common/approval.vue'
	const approvalRef = ref(null)
	const approvalClick = (row) => {
		let params = {
			id:row.id,
			orderid: row.orderid,
			tablename: row.tablename,
			goodid: row.goodid,
			goodname: row.goodname,
			picture: row.picture,
			buynumber: row.buynumber,
			price: row.price,
			discountprice: row.discountprice,
			total: row.total,
			discounttotal: row.discounttotal,
			type: row.type,
			status: row.status,
			address: row.address,
			tel: row.tel,
			consignee: row.consignee,
			remark: row.remark,
			logistics: row.logistics,
			role: row.role,
			addtime: row.addtime,
			userid: row.userid,
			sfsh: row.sfsh,
			shhf: row.shhf,
			shangjiamingcheng: row.shangjiamingcheng,
			groupno: row.groupno,
		}
		nextTick(() => {
			approvalRef.value.approvalClick(params )
		})
	}
	const orderTabClick = () => {
		getList()
	}
	const orderStatus = ref('')
    if(['未支付'  ,'已支付','已发货','已完成','已退款','已取消'].includes(route.query.menuJump)){
        orderStatus.value = route.query.menuJump
    }
	const orderStatusFormatter =(row, column)=> {
      var temp = ''
      switch (row.type-0) {
        case 1:
          temp = '现金'
          break
        case 2:
          temp = '积分'
          break
        case 3:
          temp = '拼团'
          break
      }
      return temp
    }
	//修改物流
	const logisticsClick = (row)=>{
		formRef.value.init(row.id,'logistics','',row)
	}
	//发货
	const sendGoodClick=(row)=>{
		ElMessageBox.confirm(`确定该订单商品发货？`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(()=>{
			row.status = '已发货'
			context.$http({
				url: 'orders/update',
				method: 'post',
				data: row
			}).then(res=>{
				context?.$toolUtil.message('已发货','success',()=>{
					searchClick()
				})
			})
		}).catch(_ => {})
	}
	//确认收货
	const confirmGoodClick=(row)=>{
		ElMessageBox.confirm(`确定该订单商品已收货？`, '提示', {
			confirmButtonText: '是',
			cancelButtonText: '否',
			type: 'warning',
		}).then(()=>{
			row.status = '已完成'
			context.$http({
				url: 'orders/update',
				method: 'post',
				data: row
			}).then(res=>{
				context?.$toolUtil.message('订单完成','success',()=>{
					searchClick()
				})
			})
		}).catch(_ => {})
	}
	//查询审核状态列表
	const approvalLists = ref([])
	//初始化
	const init = () => {
        approvalLists.value = "是,否,待审核".split(',');
        if(['未支付','已支付','已发货','已完成','已退款','已取消'].includes(route.query.menuJump)){
            orderStatus.value = route.query.menuJump
        }else{
            orderStatus.value = ''
        }
		getList()
	}
	init()
</script>
<style lang="scss" scoped>
	// 切换栏
	.demo-tabs {
		// 头部
		:deep(.el-tabs__header) {
			// 滑动区
			.el-tabs__nav-scroll {
				// list
				.el-tabs__nav {
					// item
					.el-tabs__item {
					}
					// item active
					.is-active {
					}
					// item hover
					.el-tabs__item:hover {
					}
				}
			}
		}
	}

	// 操作盒子
	.list_search_view {
		// 搜索盒子
		.search_form {
			// 子盒子
			.search_view {
				// 搜索label
				.search_label {
				}
				// 搜索item
				.search_box {
					// 输入框
					:deep(.search_inp) {
					}
					// 下拉框
					:deep(.search_sel) {
						//去掉默认样式
						.select-trigger{
							height: 100%;
							.el-input{
								height: 100%;
							}
						}
					}
				}
			}
			// 搜索按钮盒子
			.search_btn_view {
				// 搜索按钮
				.search_btn {
				}
				// 搜索按钮-悬浮
				.search_btn:hover {
				}
			}
		}
		//头部按钮盒子
		.btn_view {
			// 其他
			:deep(.el-button--default){
			}
			// 其他-悬浮
			:deep(.el-button--default:hover){
			}
			// 新增
			:deep(.el-button--success){
			}
			// 新增-悬浮
			:deep(.el-button--success:hover){
			}
			// 删除
			:deep(.el-button--danger){
			}
			// 删除-悬浮
			:deep(.el-button--danger:hover){
			}
			// 统计
			:deep(.el-button--warning){
			}
			// 统计-悬浮
			:deep(.el-button--warning:hover){
			}
		}
	}
	// 表格样式
	.el-table {
		:deep(.el-table__header-wrapper) {
			thead {
				tr {
					th {
						.cell {
						}
					}
				}
			}
		}
		:deep(.el-table__body-wrapper) {
			tbody {
				tr {
					td {
						.cell {
							// 编辑
							.el-button--primary {
							}
							// 编辑-悬浮
							.el-button--primary:hover {
							}
							// 详情
							.el-button--info {
							}
							// 详情-悬浮
							.el-button--info:hover {
							}
							// 删除
							.el-button--danger {
							}
							// 删除-悬浮
							.el-button--danger:hover {
							}
							// 跨表
							.el-button--success {
							}
							// 跨表-悬浮
							.el-button--success:hover {
							}
							// 操作
							.el-button--warning {
							}
							// 操作-悬浮
							.el-button--warning:hover {
							}
						}
					}
				}
				tr.el-table__row--striped {
					td {
						background: #FAFAFA;
					}
				}
				tr:hover {
					td {
					}
				}
			}
		}
	}
	// 分页器
	.el-pagination {
		// 总页码
		:deep(.el-pagination__total) {
			margin: 0 10px 0 0;
			color: #666;
			font-weight: 400;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
		}
		// 上一页
		:deep(.btn-prev) {
			border: none;
			border-radius: 2px;
			padding: 0;
			margin: 0 5px;
			color: #666;
			background: #f4f4f5;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			min-width: 35px;
			height: 28px;
		}
		// 下一页
		:deep(.btn-next) {
			border: none;
			border-radius: 2px;
			padding: 0;
			margin: 0 5px;
			color: #666;
			background: #f4f4f5;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			min-width: 35px;
			height: 28px;
		}
		// 上一页禁用
		:deep(.btn-prev:disabled) {
			border: none;
			cursor: not-allowed;
			border-radius: 2px;
			padding: 0;
			margin: 0 5px;
			color: #C0C4CC;
			background: #f4f4f5;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
		}
		// 下一页禁用
		:deep(.btn-next:disabled) {
			border: none;
			cursor: not-allowed;
			border-radius: 2px;
			padding: 0;
			margin: 0 5px;
			color: #C0C4CC;
			background: #f4f4f5;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
		}
		// 页码
		:deep(.el-pager) {
			padding: 0;
			margin: 0;
			display: inline-block;
			vertical-align: top;
			// 数字
			.number {
				cursor: pointer;
				padding: 0 4px;
				margin: 0 5px;
				color: #666;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 28px;
				border-radius: 2px;
				background: #f4f4f5;
				text-align: center;
				min-width: 30px;
				height: 28px;
			}
			// 数字悬浮
			.number:hover {
				cursor: pointer;
				padding: 0 4px;
				margin: 0 5px;
				color: #409EFF;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 28px;
				border-radius: 2px;
				background: #f4f4f5;
				text-align: center;
				min-width: 30px;
				height: 28px;
			}
			// 选中
			.number.is-active {
				cursor: default;
				padding: 0 4px;
				margin: 0 5px;
				color: #FFF;
				display: inline-block;
				vertical-align: top;
				font-size: 13px;
				line-height: 28px;
				border-radius: 2px;
				background: #409EFF;
				text-align: center;
				min-width: 30px;
				height: 28px;
			}
		}
		// sizes
		:deep(.el-pagination__sizes) {
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
			.el-select {
				border: 1px solid #DCDFE6;
				cursor: pointer;
				padding: 0;
				color: #606266;
				display: inline-block;
				font-size: 13px;
				line-height: 28px;
				border-radius: 3px;
				outline: 0;
				background: #FFF;
				width: 100%;
				text-align: center;
				height: 28px;
			}
		}
		// 跳页
		:deep(.el-pagination__jump) {
			margin: 0 0 0 24px;
			color: #606266;
			display: inline-block;
			vertical-align: top;
			font-size: 13px;
			line-height: 28px;
			height: 28px;
			// 输入框
			.el-input {
				border: 1px solid #DCDFE6;
				cursor: pointer;
				padding: 0 3px;
				color: #606266;
				display: inline-block;
				font-size: 14px;
				line-height: 28px;
				border-radius: 3px;
				outline: 0;
				background: #FFF;
				width: 100%;
				text-align: center;
				height: 28px;
			}
		}
	}
</style>
