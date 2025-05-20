<template>
	<div>
		<div class="register_view">
			<el-form :model="registerForm" class="register_form">
				<div class="title_view">{{projectName}}注册</div>
				<div class="list_item">
					<div class="list_label">商家名称：</div>
					<el-input class="list_inp"
						 v-model="registerForm.shangjiamingcheng" 
						 placeholder="请输入商家名称"
						 type="text"
						/>
				</div>
				<div class="list_item">
					<div class="list_label">联系电话：</div>
					<el-input class="list_inp"
						 v-model="registerForm.lianxidianhua" 
						 placeholder="请输入联系电话"
						 type="text"
						/>
				</div>
				<div class="list_item">
					<div class="list_label">联系地址：</div>
					<el-input class="list_inp"
						 v-model="registerForm.lianxidizhi" 
						 placeholder="请输入联系地址"
						 type="text"
						/>
				</div>
				<div class="list_item">
					<div class="list_label">密码：</div>
					<el-input class="list_inp"
						 v-model="registerForm.mima" 
						 placeholder="请输入密码"
						 type="password"
						 />
				</div>
				<div class="list_item">
					<div class="list_label">确认密码：</div>
					<el-input class="list_inp" v-model="registerForm.mima2" type="password" placeholder="请输入确认密码" />
				</div>
				<div class="list_item">
					<div class="list_label">头像：</div>
					<div :style='{}' class="list_file_list">
						<uploads
							action="file/upload" 
							tip="请上传头像"
							:fileUrls="registerForm.touxiang?registerForm.touxiang:''" 
							@change="touxiangUploadSuccess">
						</uploads>
					</div>
				</div>
				<div class="list_btn">
					<el-button class="register" type="success" @click="handleRegister">注册</el-button>
					<div class="r-login" @click="close">已有账号，直接登录</div>
				</div>
			</el-form>
		</div>
	</div>
</template>
<script setup>
	import {
		ref,
		getCurrentInstance,
		nextTick,
		onMounted,
	} from 'vue';
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const projectName = context?.$project.projectName
	//获取注册类型
	import { useRoute } from 'vue-router';
	const route = useRoute()
	const tableName = ref('shangjia')
	
	//公共方法
	const getUUID=()=> {
		return new Date().getTime();
	}
	const registerForm = ref({
	})
	const init=()=>{
	}
    const touxiangUploadSuccess=(fileUrls)=> {
        registerForm.value.touxiang = fileUrls;
    }
	// 多级联动参数
	//注册按钮
	const handleRegister = () => {
		let url = tableName.value +"/register";
		if((!registerForm.value.shangjiamingcheng)){
			context?.$toolUtil.message(`商家名称不能为空`,'error')
			return false
		}
		if((!registerForm.value.lianxidianhua)){
			context?.$toolUtil.message(`联系电话不能为空`,'error')
			return false
		}
		if((!registerForm.value.lianxidizhi)){
			context?.$toolUtil.message(`联系地址不能为空`,'error')
			return false
		}
		if((!registerForm.value.mima)){
			context?.$toolUtil.message(`密码不能为空`,'error')
			return false
		}
		if(registerForm.value.mima!=registerForm.value.mima2){
			context?.$toolUtil.message('两次密码输入不一致','error')
			return false
		}
		if(registerForm.value.touxiang!=null){
			registerForm.value.touxiang = registerForm.value.touxiang.replace(new RegExp(context?.$config.url,"g"),"");
		}
		context?.$http({
			url:url,
			method:'post',
			data:registerForm.value
		}).then(res=>{
			context?.$toolUtil.message('注册成功','success', obj=>{
				context?.$router.push({
					path: "/login"
				});
			})
		})
	}
	//返回登录
	const close = () => {
		context?.$router.push({
			path: "/login"
		});
	}
	init()
	onMounted(()=>{

	})
</script>
<style lang="scss" scoped>
	
	.register_view {
        background-image: url("http://clfile.zggen.cn/20241022/0c61db5c3eac46e6bc32ce0375738a4a.webp")!important;
		// 表单盒子
		.register_form {
		}
		// 标题样式
		.title_view {
		}
		// item盒子
		.list_item {
			// label
			.list_label {
			}
			// 输入框
			:deep(.list_inp) {
			}
		}
		//按钮盒子
		.list_btn {
			//注册按钮
			.register {
			}
			//注册按钮悬浮样式
			.register:hover {
			}
			//已有账号
			.r-login {
			}
		}
		//图片上传样式
		.list_file_list  {
			//提示语
			:deep(.el-upload__tip){
			}
			//外部盒子
			:deep(.el-upload--picture-card){
				//图标
				.el-icon{
				}
			}
			:deep(.el-upload-list__item) {
			}
		}
	}
</style>
<style>
.register_view {
    min-height: 100vh;
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-image: url(http://clfile.zggen.cn/20240724/b0645dd6c97b41a799e314b57fba5f08.png);
    background-position: center center;
    background-size: 100% 100% !important;
    background-repeat: no-repeat;
    background-attachment: initial;
    background-origin: initial;
    background-clip: initial;
    background-color: initial;
}


.register_view .register_form{width: 650px;margin:30px auto;padding: 30px 50px 40px 50px;background: none;border-radius: 10px;transform: scale(1);-webkit-transform: scale(1);-ms-transform: scale(1);z-index: 5;}


.register_view .register_form .title_view{width: 100%;float: left;line-height: 46px;font-size:24px;font-weight: 600;letter-spacing: 2px;color: #fff;position: relative;margin-bottom:20px;text-align: center;}


.register_view .register_form .list_item {display: flex;align-items: center;width: 100%;justify-content: flex-start;margin: 10px auto;background: #fff;border-radius: 6px;padding: 8px;}
.register_view .register_form .list_item .list_label{display:block;margin-right:10px;white-space:nowrap;font-size:16px;line-height: 50px;background: var(--theme);width: 120px;flex-shrink: 0;text-align: center;border-radius: 6px;}
.register_view .register_form .list_item .list_label i {  font-size:24px; color:#999;  }
.register_view .register_form .list_item .el-input .el-input__inner{width:100%;border:none;border-bottom: 0px solid #ddd;height:40px;line-height:40px;border-radius:0;font-size:16px;color:#999;}
.register_view .register_form .list_item .el-select { width:100%; }
.register_view .register_form .list_type{ margin-bottom:20px;  }
.register_view .register_form .list_code{  }
.register_view .register_form .list_code .el-input .el-input__inner{width:100%;border:none;border-bottom: 0px solid #ddd;height:40px;line-height:40px;border-radius:0;font-size:16px;color:#999;}


.register_view .register_form .list_item .list_file_list{ width: 100%;  margin:10px 20px 10px 0px; flex:1; }

.register_view .register_form .list_item .list_file_list .el-upload{ background-color: rgb(255, 255, 255);  width: 100px;  height: 60px; line-height: 68px;  text-align: center;  border: 1px solid rgb(221, 221, 221);  border-radius: 0px; cursor: pointer; }

.register_view .register_form .list_item .list_file_list .el-upload .el-icon{ font-size: 26px; color: rgb(187, 187, 187); }

.register_view .register_form .list_item .list_file_list .img-uploader .el-upload__tip{ font-size: 16px;  color: rgb(102, 102, 102);margin: 7px 0px 0px; display: flex; align-items: center;  justify-content: flex-start; }


.register_view .register_form .list_item .el-upload-dragger{background-color: rgb(255, 255, 255);border: 1px solid rgb(221, 221, 221);border-radius: 0px;box-sizing: border-box;width: 100%;height: auto;text-align: center;cursor: pointer;overflow: hidden;padding: 4px 20px 8px;}

.register_view .register_form .list_item .el-upload-dragger .el-icon--upload{font-size: 48px;color: #444444;line-height: 40px;margin: 0px;}

.register_view .register_form .list_item .upload-demo .el-upload__tip{ font-size: 16px; color: rgb(102, 102, 102); margin: 7px 0px 0px; }

.register_view .register_form .list_item .el-upload-dragger .el-upload__text{  font-size: 16px;  }
.register_view .register_form .list_item .el-upload-dragger .el-upload__text em{ color: rgb(139, 92, 126); }


.register_view .register_form .list_item .list_radio{ display: flex; width: 80%; align-items: center; flex-wrap: wrap; background: none; height: 36px; flex: 1; }

.register_view .register_form .list_item .list_radio .el-radio{ width: auto; margin: 0px 20px 0px 0px; display: flex; justify-content: center; align-items: center; }

.register_view .register_form .list_item .list_radio .el-radio .el-radio__label { font-size: 16px; color: rgb(153, 153, 153);   }

.register_view .register_form .list_item .list_radio .el-radio.is-checked .el-radio__label {color: #444444;}

.register_view .register_form .list_item .list_radio .el-radio .el-radio__input .el-radio__inner{ border-color: rgb(153, 153, 153); background: rgb(255, 255, 255); }

.register_view .register_form .list_item .list_radio .el-radio .el-radio__input.is-checked .el-radio__inner{border-color: #444444;background: #444444;}


.register_view .register_form .list_item .list_checkbox { display: flex; width: 80%;  flex-wrap: wrap; align-items: center; background: none; height: 36px; flex:1; }

.register_view .register_form .list_item .list_checkbox .el-checkbox{ width: auto; margin: 0px 20px 0px 0px; display: flex;  justify-content: center;  align-items: center; }

.register_view .register_form .list_item .list_checkbox .el-checkbox .el-checkbox__label { color: rgb(153, 153, 153);  font-size: 16px; }

.register_view .register_form .list_item .list_checkbox .el-checkbox.is-checked .el-checkbox__label { color: rgb(139, 92, 126); }

.register_view .register_form .list_item .list_checkbox .el-checkbox .el-checkbox__input .el-checkbox__inner{ border-color: rgb(153, 153, 153);  background: rgb(255, 255, 255); }

.register_view .register_form .list_item .list_checkbox .el-checkbox.is-checked .el-checkbox__input .el-checkbox__inner{border-color: #444444;background: #444444;}


.register_view .register_form .list_code{display: flex;align-items: center;width: 100%;margin: 10px auto;background: #fff;padding: 8px;border-radius: 6px;}
.register_view .register_form .list_code .list_code_label{font-size:16px;line-height: 50px;width: 120px;text-align: center;background: var(--theme);margin-right: 10px;border-radius: 6px;}

.register_view .register_form .list_code .list_code_item{ width: calc(100% - 130px); display: flex; align-items: center; flex: 1;  }

.register_view .register_form .list_code .list_code_item .list_code_inp{  font-size:16px; color:#999;  }

.register_view .register_form .list_code .list_code_item .list_code_btn{width: 150px;height: 50px;line-height: 36px;padding: 0px;border: 0px solid rgb(73, 189, 227);background: var(--theme);border-radius: 6px;color: rgb(255, 255, 255);font-size: 16px;}


.register_view .register_form .list_btn{display: flex;flex-wrap: wrap;justify-content: center;margin-top: 30px;}
.register_view .register_form .list_btn .register{width: 100%;height: 50px;line-height: 46px;background: var(--theme);border: 0px solid #ccc;font-weight: 600;font-size: 20px;color: #fff;margin-bottom:20px;padding:0;margin-top:20px;order: 1;} 
.register_view .register_form .list_btn .r-login{  font-size: 16px; color: #999;  } 
.register_view .register_form .list_btn .r-login:hover{cursor:pointer;color: var(--theme);} 


.register_view .el-input {
    border: none!important;
}

</style>