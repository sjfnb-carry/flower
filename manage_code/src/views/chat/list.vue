<template>
	<div>
		<div class="center_view">
			<el-table v-loading="listLoading" :data="list" border
				:stripe='true'>
				<el-table-column prop="ask" :resizable='true' :sortable='true' align="left" header-align="left" label="新消息">
					<template #default="scope">
						{{scope.row.ask}}
					</template>
				</el-table-column>
				<el-table-column prop="ask" :resizable='true' :sortable='true' align="left" header-align="left" label="状态">
					<template #default="scope">
						<el-tag v-if="scope.row.isreply==1" type="success">未回复</el-tag>
						<el-tag v-if="scope.row.isreply==0" type="info">已回复</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="ask" :resizable='true' :sortable='true' align="left" header-align="left" label="操作">
					<template #default="scope">
						<el-button class="edit_btn" type="primary" @click="replyClick(scope.row)">回复</el-button>
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
				@size-change="sizeChange"
				@current-change="currentChange"  />
			<el-dialog v-model="formVisible" title="回复" width="70%" destroy-on-close @close="delTimer">
				<el-form ref="formRef" :model="form" class="chat_view">
					<div class="chat-content">
						<div class="chat-item" :key="item.id" v-for="(item,index) in chatList">
                            <div v-if="index>1&&moment(chatList[index-1].addtime).date()!=moment(item.addtime).date()||index==0"
                                 style="font-size: 12px;text-align: center;margin: 4px 0;">
                                {{moment(item.addtime).format("MM-DD HH:mm")}}
                            </div>
							<div class="chat_time" v-if="item.showTime">{{item.addtime}}</div>
							<div v-if="item.ask" class="left-content">
								<el-alert v-if="!item.img" class="text-content" :title="item.ask" :closable="false"
									type="success"></el-alert>
                                <video v-else-if="item.ask.endsWith('.mp4')" controls style="width: 200px;height: 160px">
                                    <source  :src="$config.url + item.ask">
                                </video>
								<el-image v-if="item.img" :src="$config.url + item.img"
									class="chat_img" fit="scale-down"
									:preview-src-list="[$config.url + item.img]"></el-image>
							</div>
							<div v-else class="right-content">
								<el-alert v-if="!item.img" class="text-content" :title="item.reply" :closable="false"
									type="warning"></el-alert>
                                <video v-else-if="item.reply.endsWith('.mp4')" controls style="width: 200px;height: 160px">
                                    <source  :src="$config.url + item.reply">
                                </video>
								<el-image v-if="item.img" :src="$config.url + item.img"
									class="chat_img" fit="scale-down"
									:preview-src-list="[$config.url + item.img]"></el-image>
							</div>
							<div class="clear-float"></div>
						</div>
					</div>
					<div class="input_box">
						<el-input class="input" v-model="form.reply" placeholder="请回复" type="textarea" />
						<img src="http://chy2.gdnxeco.com/20230912/f60a0c39a6d34289b50c151949711b67.png" class="inp_add"
							@click="askType=!askType" alt="">
					</div>
					<div class="upload_box" v-if="askType">
						<el-upload class="upload-demo" :action="action" :on-success="uploadSuccess"
							:show-file-list="false">
							<el-button type="primary">上传文件</el-button>
						</el-upload>
					</div>
					<div class="save_box">
						<el-button type="primary" @click="replySave">发送</el-button>
					</div>
				</el-form>
			</el-dialog>
		</div>
	</div>
</template>

<script setup>
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		onMounted,
	} from 'vue'
    import moment from "moment"
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const list = ref([])
	const listQuery = ref({
		page: 1,
		limit: 20,
		sort: 'id',
		order: 'desc',
		isreply: 1
	})
	const listLoading = ref(false)
	//获取列表
	const getList = () => {
		let params = listQuery.value
		listLoading.value = true
		context?.$http({
			url: 'chat/page',
			method: 'get',
			params: params
		}).then(res => {
			listLoading.value = false
			list.value = res.data.data.list
			total.value = Number(res.data.data.total)
		})
	}
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
	//form
	const formVisible = ref(false)
	const nowId = ref(0)
	const form = ref({
		reply: ''
	})
	//对话列表
	const chatList = ref([])
	//回复按钮
	const replyClick = (row) => {
		nowId.value = row.userid
		getChatList()
		formVisible.value = true
		timer.value = setInterval(() => {
			getChatList()
		}, 3000)
	}
	//定时器
	const timer = ref(null)
	const delTimer = () => {
		clearInterval(timer.value)
	}
    const scrollFlag = ref(true)
	const getChatList = () => {
		context?.$http({
			url: 'chat/page',
			method: 'get',
			params: {
				limit: 1000,
				sort: 'addtime',
				order: 'asc',
				userid: nowId.value
			}
		}).then(res => {
			for (let x in res.data.data.list) {
				if (res.data.data.list[x].ask) {
					if (res.data.data.list[x].ask.split('/').length > 1) {
						if (res.data.data.list[x].ask.split('/')[0] == 'file') {
							res.data.data.list[x].img = res.data.data.list[x].ask
						}
					}
				}
				if (res.data.data.list[x].reply) {
					if (res.data.data.list[x].reply.split('/').length > 1) {
						if (res.data.data.list[x].reply.split('/')[0] == 'file') {
							res.data.data.list[x].img = res.data.data.list[x].reply
						}
					}
				}
				if(x==0){
					res.data.data.list[x].showTime = 1
				}else{
					let jian = new Date(res.data.data.list[x].addtime).getTime() - new Date(res.data.data.list[x-1].addtime).getTime()
					if(jian>18000){
						res.data.data.list[x].showTime = 1
					}else{
						res.data.data.list[x].showTime = 0
					}
				}
			}
			chatList.value = res.data.data.list
			nextTick(() => {
				let div = document.getElementsByClassName('chat-content')[0]
				setTimeout(() => {
                    if (div){
                        if(div.scrollTop+div.clientHeight==div.scrollHeight || scrollFlag.value){
                            div.scrollTop = div.scrollHeight
                            scrollFlag.value = false
                        }
                    }
				}, 100)
			})
		})
	}
	//发送信息
	const replySave = () => {
		form.value.userid = nowId.value
		if (form.value.reply) {
			context?.$http({
				url: 'chat/save',
				method: 'post',
				data: form.value
			}).then(res => {
				context?.$toolUtil.message('发送成功', 'success', () => {
					getChatList()
					form.value.reply = ''

				})
			})
		}
	}
	//图片
	const action = ref(`${context?.$config.name}/file/upload`)
	const askType = ref(false)
	const uploadSuccess = (e) => {

		let params = {
			reply: 'file/' + e.file,
			userid: nowId.value
		}
		context?.$http({
			url: 'chat/save',
			method: 'post',
			data: params
		}).then(res => {
			context?.$toolUtil.message('发送成功', 'success', () => {
				getChatList()
				form.value.reply = ''

			})
		})
	}
	const init = () => {
		getList()
	}
	init()
</script>

<style lang="scss" scoped>

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
							// 回复
							.el-button--primary {
							}
							// 回复-悬浮
							.el-button--primary:hover {
							}
						}
					}
				}
				tr.el-table__row--striped {
					td {
						background: #FAFAFA !important;
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
			.number.active {
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
	// 聊天总盒子
	.chat_view {
		// 聊天内容盒子
		.chat-content {
			// 聊天时间
			.chat_time{
			}
			// 提问盒子
			.left-content {
				// 文字样式
				:deep(.el-alert--success){
					.el-alert__title {
						font-size: inherit;
					}
				}
				// 聊天图片
				.chat_img {
				}
			}
			// 回复盒子
			.right-content {
				// 文字样式
				:deep(.el-alert--warning){
					.el-alert__title {
						font-size: inherit;
					}
				}
				// 聊天图片
				.chat_img {
				}
			}
		}
		// 输入内容盒子
		.input_box{
			// 输入框
			:deep(.el-textarea__inner) {
			}
			// 操作按钮
			.inp_add {
			}
		}
		// 操作盒子
		.upload_box {
			:deep(.upload-demo) {
				// 按钮
				.el-button--primary {
				}
				// 按钮悬浮
				.el-button--primary:hover {
				}
			}
		}
		// 发送盒子
		.save_box {
			// 按钮
			:deep(.el-button--primary) {
			}
			// 按钮悬浮
			:deep(.el-button--primary:hover) {
			}
		}
		.clear-float {
			clear: both;
		}
	}
</style>
<style>
/* 总盒子 */
.chat_view {
    display: flex;
    flex-direction: column;
    margin: 30px 0px;
    padding: 30px;
    border: 0px solid rgb(221, 221, 221);
    border-radius: 4px;
    font-size: 16px;
}
/* 聊天列表 盒子 */
.chat_view .chat-content{
    margin: 0px 0px 30px;
    padding: 0px 0px 30px;
    max-height: 300px;
    height: 300px;
    overflow-y: scroll;
    border: 1px solid rgb(221, 221, 221) !important;
    background: rgb(255, 255, 255) !important;
    border-radius: 0px;
}
/* 时间 */
.chat_view .chat-content .chat-item .chat_time{
    width: 100%;
    text-align: center;
    font-size: 16px;
    color: rgb(153, 153, 153);
    padding: 6px 0px;
}
/* 提问 盒子 */
.chat_view .chat-content .chat-item .left-content{
    float: left;
    margin: 0px 0px 10px;
    padding: 10px;
}
/* 内容 */
.chat_view .chat-content .chat-item .left-content .text-content{
    background-color: rgb(240, 249, 235);
    color: rgb(103, 194, 58);
}
/* 图片 */
.chat_view .chat-content .chat-item .left-content .chat_img{
    width: 120px;
    margin: 10px;
}
/* 回复 盒子 */
.chat_view .chat-content .chat-item .right-content{
    float: right;
    margin: 0px 0px 10px;
    padding: 10px;
}
/* 内容 */
.chat_view .chat-content .chat-item .right-content .text-content{
    background-color: rgb(253, 246, 236);
    color: rgb(230, 162, 60);
}
/* 图片 */
.chat_view .chat-content .chat-item .right-content .chat_img{
    width: 120px;
    margin: 10px;
}

/* 操作栏 盒子 */
.chat_view .input_box{
    display: flex;
    align-items: center;
}
/* 输入框 盒子 */
.chat_view .input_box .el-textarea{
    position: relative;
    display: inline-block;
    width: 100%;
    vertical-align: bottom;
    font-size: 14px;
}
/* 输入框 */
.chat_view .input_box .el-textarea .el-textarea__inner{
    min-height: 120px !important;
}
/* 加号 */
.chat_view .input_box .inp_add{
    width: 36px;
    cursor: pointer;
    margin: 0px 0px 0px 10px;
}

/* 上传 盒子 */
.chat_view .upload_box{
    display: flex;
    align-items: center;
    width: 100%;
    justify-content: space-around;
    margin: 20px 0px 0px;
}
/* 按钮 */
.chat_view .upload_box .el-button--primary{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 36px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 4px;
    border: 0px solid rgb(252, 203, 130);
    background: rgba(68, 68, 68, 1);
    cursor: pointer;
}
/* 悬浮 */
.chat_view .upload_box .el-button--primary:hover{
}

/* 按钮 盒子 */
.chat_view .save_box{
width: 100%;
    display: flex;
    margin: 10px 0px 0px;
}
/* 按钮 */
.chat_view .save_box .el-button--primary{
    margin: 0px 10px 0px 0px;
    padding: 0px 24px;
    width: auto;
    height: 36px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    border-radius: 4px;
    border: 0px solid rgb(252, 203, 130);
    background: var(--theme);
    cursor: pointer;
    min-width: 110px;
}
/* 悬浮 */
.chat_view .save_box .el-button--primary:hover{
}

</style>