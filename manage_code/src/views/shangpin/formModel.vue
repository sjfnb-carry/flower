<template>
	<div>
		<el-dialog modal-class="edit_form_modal" class="edit_form" v-model="formVisible" :title="formTitle" width="80%" destroy-on-close :fullscreen='false'>
			<el-form class="formModel_form" ref="formRef" :model="form" :rules="rules">
				<el-row >
					<el-col :span="12">
						<el-form-item label="商品名称" prop="shangpinmingcheng">
							<el-input class="list_inp" v-model="form.shangpinmingcheng" placeholder="商品名称"
                                type="text"
								:readonly="!isAdd||disabledForm.shangpinmingcheng?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="规格" prop="guige">
							<el-input class="list_inp" v-model="form.guige" placeholder="规格"
                                type="text"
								:readonly="!isAdd||disabledForm.guige?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item prop="fengmian"
									  label="封面"
						>
							<uploads
								:disabled="!isAdd||disabledForm.fengmian?true:false"
								action="file/upload"
								tip="请上传封面"
								style="width: 100%;text-align: left;"
								:fileUrls="form.fengmian?form.fengmian:''" 
								@change="fengmianUploadSuccess">
							</uploads>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="商家名称" prop="shangjiamingcheng">
							<el-input class="list_inp" v-model="form.shangjiamingcheng" placeholder="商家名称"
                                type="text"
								:readonly="!isAdd||disabledForm.shangjiamingcheng?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="商品分类" prop="shangpinfenlei">
							<el-select
								class="list_sel"
								:disabled="!isAdd||disabledForm.shangpinfenlei?true:false"
								v-model="form.shangpinfenlei" 
								placeholder="请选择商品分类"
								>
								<el-option v-for="(item,index) in shangpinfenleiLists" :label="item"
									:value="item"
									>
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="商品列表" prop="shangpinliebiao">
							<el-input class="list_inp" v-model="form.shangpinliebiao" placeholder="商品列表"
                                type="text"
								:readonly="!isAdd||disabledForm.shangpinliebiao?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="价格" prop="price">
							<el-input class="list_inp" v-model.number="form.price" placeholder="价格"
                                type="number"
                                @mousewheel.native.prevent
								:readonly="!isAdd||disabledForm.price?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="单限" prop="onelimittimes">
							<el-input class="list_inp" v-model.number="form.onelimittimes" placeholder="单限"
                                type="text"
								:readonly="!isAdd||disabledForm.onelimittimes?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="库存" prop="alllimittimes">
							<el-input class="list_inp" v-model.number="form.alllimittimes" placeholder="库存"
                                type="text"
								:readonly="!isAdd||disabledForm.alllimittimes?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="积分" prop="jf">
							<el-input class="list_inp" v-model.number="form.jf" placeholder="积分"
                                type="text"
								:readonly="!isAdd||disabledForm.jf?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="团购价" prop="groupprice">
							<el-input class="list_inp" v-model.number="form.groupprice" placeholder="团购价"
                                type="number"
                                @mousewheel.native.prevent
								:readonly="!isAdd||disabledForm.groupprice?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="拼团人数" prop="grouppeople">
							<el-input class="list_inp" v-model.number="form.grouppeople" placeholder="拼团人数"
                                type="text"
								:readonly="!isAdd||disabledForm.grouppeople?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="当前人数" prop="curpeople">
							<el-input class="list_inp" v-model.number="form.curpeople" placeholder="当前人数"
                                type="text"
								:readonly="!isAdd||disabledForm.curpeople?true:false" />
						</el-form-item>
					</el-col>

					<el-col :span="24">
						<el-form-item label="商品介绍" prop="shangpinjieshao">
							<editor :value="form.shangpinjieshao" placeholder="请输入商品介绍" :readonly="!isAdd||disabledForm.shangpinjieshao?true:false"
								class="list_editor" @change="(e)=>editorChange(e,'shangpinjieshao')"></editor>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer v-if="isAdd||type=='logistics'||type=='reply'">
				<span class="formModel_btn_box">
					<el-button class="cancel_btn" @click="closeClick">取消</el-button>
					<el-button class="confirm_btn" type="primary" @click="save"
						>
						提交
					</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>
<script setup>
	import {
		reactive,
		ref,
		getCurrentInstance,
		nextTick,
		computed,
		defineEmits
	} from 'vue'
    import {
        useStore
    } from 'vuex';
    const store = useStore()
    const user = computed(()=>store.getters['user/session'])
	const context = getCurrentInstance()?.appContext.config.globalProperties;	
	const emit = defineEmits(['formModelChange'])
	//基础信息
	const tableName = 'shangpin'
	const formName = '商品'
	//基础信息
	//form表单
	const form = ref({})
	const disabledForm = ref({
        shangpinmingcheng : false,
        guige : false,
        fengmian : false,
        shangpinjieshao : false,
        shangjiamingcheng : false,
        clicktime : false,
        shangpinfenlei : false,
        shangpinliebiao : false,
        price : false,
        storeupNumber : false,
        discussNumber : false,
        onelimittimes : false,
        alllimittimes : false,
        jf : false,
        groupprice : false,
        grouppeople : false,
        curpeople : false,
	})
	const formVisible = ref(false)
	const isAdd = ref(false)
	const formTitle = ref('')
    
	const rules = ref({
		shangpinmingcheng: [
			{required: true,message: '请输入',trigger: 'blur'}, 
		],
		guige: [
			{required: true,message: '请输入',trigger: 'blur'}, 
		],
		fengmian: [
		],
		shangpinjieshao: [
		],
		shangjiamingcheng: [
		],
		clicktime: [
		],
		shangpinfenlei: [
		],
		shangpinliebiao: [
		],
		price: [
			{ validator: context.$toolUtil.validator.number, trigger: 'blur' },
		],
		storeupNumber: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		discussNumber: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		onelimittimes: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		alllimittimes: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		jf: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		groupprice: [
			{ validator: context.$toolUtil.validator.number, trigger: 'blur' },
		],
		grouppeople: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
		curpeople: [
			{ validator: context.$toolUtil.validator.intNumber, trigger: 'blur' },
		],
	})
	//表单验证
	
	const formRef = ref(null)
	const id = ref(0)
	const type = ref('')
	//封面上传回调
	const fengmianUploadSuccess=(e)=>{
		form.value.fengmian = e
	}
	//商品分类列表
	const shangpinfenleiLists = ref([])

	//获取唯一标识
	const getUUID =()=> {
      return new Date().getTime();
    }
	//重置
	const resetForm = () => {
		form.value = {
			shangpinmingcheng: '',
			guige: '',
			fengmian: '',
			shangpinjieshao: '',
			shangjiamingcheng: '',
			clicktime: '',
			shangpinfenlei: '',
			shangpinliebiao: '',
			price: '',
		}
	}
	//获取info
	const getInfo = ()=>{
		context?.$http({
			url: `${tableName}/info/${id.value}`,
			method: 'get'
		}).then(res => {
			let reg=new RegExp('../../../file','g')
			res.data.data.shangpinjieshao = res.data.data.shangpinjieshao?(res.data.data.shangpinjieshao.replace(reg,'../../../cl897763171/file')):'';
			form.value = res.data.data
			formVisible.value = true
		})
	}
	const crossRow = ref('')
	const crossTable = ref('')
	const crossTips = ref('')
	const crossColumnName = ref('')
	const crossColumnValue = ref('')
	//初始化
	const init=(formId=null,formType='add',formNames='',row=null,table=null,statusColumnName=null,tips=null,statusColumnValue=null)=>{
		resetForm()
		if(formId){
			id.value = formId
			type.value = formType
		}
		if(formType == 'add'){
			isAdd.value = true
			formTitle.value = '新增' + formName
			formVisible.value = true
		}else if(formType == 'info'){
			isAdd.value = false
			formTitle.value = '查看' + formName
			getInfo()
		}else if(formType == 'edit'){
			isAdd.value = true
			formTitle.value = '修改' + formName
			getInfo()
		}
		else if(formType == 'cross'){
			isAdd.value = true
			formTitle.value = formNames
			// getInfo()
			for(let x in row){
				if(x=='shangpinmingcheng'){
					form.value.shangpinmingcheng = row[x];
					disabledForm.value.shangpinmingcheng = true;
					continue;
				}
				if(x=='guige'){
					form.value.guige = row[x];
					disabledForm.value.guige = true;
					continue;
				}
				if(x=='fengmian'){
					form.value.fengmian = row[x];
					disabledForm.value.fengmian = true;
					continue;
				}
				if(x=='shangpinjieshao'){
					form.value.shangpinjieshao = row[x];
					disabledForm.value.shangpinjieshao = true;
					continue;
				}
				if(x=='shangjiamingcheng'){
					form.value.shangjiamingcheng = row[x];
					disabledForm.value.shangjiamingcheng = true;
					continue;
				}
				if(x=='clicktime'){
					form.value.clicktime = row[x];
					disabledForm.value.clicktime = true;
					continue;
				}
				if(x=='shangpinfenlei'){
					form.value.shangpinfenlei = row[x];
					disabledForm.value.shangpinfenlei = true;
					continue;
				}
				if(x=='shangpinliebiao'){
					form.value.shangpinliebiao = row[x];
					disabledForm.value.shangpinliebiao = true;
					continue;
				}
				if(x=='price'){
					form.value.price = row[x];
					disabledForm.value.price = true;
					continue;
				}
				if(x=='storeupNumber'){
					form.value.storeupNumber = row[x];
					disabledForm.value.storeupNumber = true;
					continue;
				}
				if(x=='discussNumber'){
					form.value.discussNumber = row[x];
					disabledForm.value.discussNumber = true;
					continue;
				}
				if(x=='onelimittimes'){
					form.value.onelimittimes = row[x];
					disabledForm.value.onelimittimes = true;
					continue;
				}
				if(x=='alllimittimes'){
					form.value.alllimittimes = row[x];
					disabledForm.value.alllimittimes = true;
					continue;
				}
				if(x=='jf'){
					form.value.jf = row[x];
					disabledForm.value.jf = true;
					continue;
				}
				if(x=='groupprice'){
					form.value.groupprice = row[x];
					disabledForm.value.groupprice = true;
					continue;
				}
				if(x=='grouppeople'){
					form.value.grouppeople = row[x];
					disabledForm.value.grouppeople = true;
					continue;
				}
				if(x=='curpeople'){
					form.value.curpeople = row[x];
					disabledForm.value.curpeople = true;
					continue;
				}
			}
			if(row){
				crossRow.value = row
			}
			if(table){
				crossTable.value = table
			}
			if(tips){
				crossTips.value = tips
			}
			if(statusColumnName){
				crossColumnName.value = statusColumnName
			}
			if(statusColumnValue){
				crossColumnValue.value = statusColumnValue
			}
			formVisible.value = true
		}

		context?.$http({
			url: `${context?.$toolUtil.storageGet('sessionTable')}/session`,
			method: 'get'
		}).then(res => {
			var json = res.data.data
			if(json.hasOwnProperty('shangjiamingcheng')&& context?.$toolUtil.storageGet("role")!="管理员"){
				form.value.shangjiamingcheng = json.shangjiamingcheng
				disabledForm.value.shangjiamingcheng = true;
			}
		})
		context?.$http({
			url: `option/shangpinfenlei/shangpinfenlei`,
			method: 'get'
		}).then(res=>{
			shangpinfenleiLists.value = res.data.data
		})
	}
	//初始化
	//声明父级调用
	defineExpose({
		init
	})
	//关闭
	const closeClick = () => {
		formVisible.value = false
	}
	//富文本
	const editorChange = (e,name) =>{
		form.value[name] = e
	}
	//提交
	const save= async ()=>{
		if(form.value.fengmian!=null) {
			form.value.fengmian = form.value.fengmian.replace(new RegExp(context?.$config.url,"g"),"");
		}
		var table = crossTable.value
		var objcross = JSON.parse(JSON.stringify(crossRow.value))
		let crossUserId = ''
		let crossRefId = ''
		let crossOptNum = ''
		if(type.value == 'cross'){
			if(crossColumnName.value!=''){
				if(!crossColumnName.value.startsWith('[')){
					for(let o in objcross){
						if(o == crossColumnName.value){
							objcross[o] = crossColumnValue.value
						}
					}
					//修改跨表数据
					changeCrossData(objcross)
				}else{
					crossUserId = user.value.id
					crossRefId = objcross['id']
					crossOptNum = crossColumnName.value.replace(/\[/,"").replace(/\]/,"")
				}
			}
		}
		formRef.value.validate((valid)=>{
			if(valid){
				if(crossUserId&&crossRefId){
					form.value.crossuserid = crossUserId
					form.value.crossrefid = crossRefId
					let params = {
						page: 1,
						limit: 1000, 
						crossuserid:form.value.crossuserid,
						crossrefid:form.value.crossrefid,
					}
					context?.$http({
						url: `${tableName}/page`,
						method: 'get', 
						params: params 
					}).then(res=>{
						if(res.data.data.total>=crossOptNum){
							context?.$toolUtil.message(`${crossTips.value}`,'error')
							return false
						}else{
							context?.$http({
								url: `${tableName}/${!form.value.id ? "save" : "update"}`,
								method: 'post', 
								data: form.value 
							}).then(async res=>{
								emit('formModelChange')
								context?.$toolUtil.message(`操作成功`,'success')
                                formVisible.value = false
							})
						}
					})
				}else{
					context?.$http({
						url: `${tableName}/${!form.value.id ? "save" : "update"}`,
						method: 'post', 
						data: form.value 
					}).then(async (res)=>{
						emit('formModelChange')
						context?.$toolUtil.message(`操作成功`,'success')
                        formVisible.value = false
					})
				}
			}else{
                context.$message.error('请完善信息')
            }
		})
	}
	//修改跨表数据
	const changeCrossData = async (row)=>{
        if(type.value == 'cross'){
            await context?.$http({
                url: `${crossTable.value}/update`,
                method: 'post',
                data: row
            }).then(res=>{})
        }
	}
</script>
<style lang="scss" scoped>
	// 表单
	.formModel_form{
		// form item
		:deep(.el-form-item) {
			//label
			.el-form-item__label {
			}
			// 内容盒子
			.el-form-item__content {
				// 输入框
				.list_inp {
				}
				// 下拉框
				.list_sel {
					//去掉默认样式
					.select-trigger{
						height: 100%;
						.el-input{
							height: 100%;
						}
					}
				}
				// 富文本
				.list_editor {
				}
				//图片上传样式
				.el-upload-list  {
					//提示语
					.el-upload__tip {
					}
					//外部盒子
					.el-upload--picture-card {
						//图标
						.el-icon{
						}
					}
					.el-upload-list__item {
					}
				}
			}
		}
	}
	// 按钮盒子
	.formModel_btn_box {
		.cancel_btn {
		}
		.cancel_btn:hover {
		}
		
		.confirm_btn {
		}
		.confirm_btn:hover {
		}
	}
</style>
