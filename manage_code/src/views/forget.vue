<template>
	<div>
		<div class="forget_view">
			<el-form :model="forgetForm" class="forget_box">
				<div class="forget_title">基于微信小程序的海外拼团购物平台的设计和实现{{pageType==1?'找回密码':pageType==2?'输入密保':'重置密码'}}</div>
				<div class="tab_line">
					<div class="line"></div>
					<div class="num_line">
						<div class="line_number" :class="pageType==1?'line_number1':'',pageType>1?'line_number2':''"><div class="number" v-if="pageType<2">1</div><el-icon v-else><Check /></el-icon></div>
						<div class="line_number" :class="pageType==2?'line_number1':'',pageType>2?'line_number2':''"><div class="number" v-if="pageType<3">2</div><el-icon v-else><Check /></el-icon></div>
						<div class="line_number" :class="pageType==3?'line_number1':''"><div class="number">3</div></div>
					</div>
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
    import { useRoute } from 'vue-router'
    const route = useRoute()
	const context = getCurrentInstance()?.appContext.config.globalProperties;
    onMounted(()=>{
    })
	const pageType = ref(1)
	const forgetForm = ref({})
	const userForm = ref({})
	//返回登录
	const close = () => {
		context?.$router.push({
			path: "/login"
		});
	}
</script>

<style lang="scss" scoped>
	.forget_view {
        background-image: url("http://clfile.zggen.cn/20241022/0c61db5c3eac46e6bc32ce0375738a4a.webp")!important;
		// 表单盒子
		.forget_box {
			// 标题
			.forget_title {
			}
			// tab
			// 盒子
			.tab_line{
				// 中间线
				.line{
				}
				// item盒子
				.num_line{
					// 默认样式
					.line_number{
					}
					// 选中样式
					.line_number1{
					}
					// 完成样式
					.line_number2{
					}
				}
			}
			// item
			.list_item {
				// label
				.item_label {
				}
				// 输入框
				:deep(.list_inp) {
				}
				//下拉框样式
				:deep(.list_sel) {
					//去掉默认样式
					.select-trigger{
						height: 100%;
						.el-input{
							height: 100%;
						}
					}
				}
			}
			// 按钮盒子
			.list_btn {
				// 获取密保
				:deep(.el-button--success) {
				}
				// 获取密保悬浮
				:deep(.el-button--success:hover) {
				}
				// 确认密保
				:deep(.el-button--primary) {
				}
				// 确认密保悬浮
				:deep(.el-button--primary:hover) {
				}
				// 重置密码
				:deep(.el-button--warning) {
				}
				// 重置密码悬浮
				:deep(.el-button--warning:hover) {
				}
				.r-login {
				}
			}
		}
	}

	
</style>
<style>
.forget_view {
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


.forget_box{
   width: 500px;
   margin:0 auto;
   padding: 30px 50px 40px 50px;
   border-radius: 10px;
   z-index: 5;
   background: #fff;
   box-shadow: 0px 8px 24px 0px rgba(17,17,26,0.1);
}



.forget_title{
   width: 100%;
   float: left;
   line-height: 46px;
   font-size:24px;
   font-weight: 600;
   letter-spacing: 2px;
   color: #727272;
   position: relative;
   margin-bottom:20px;
   text-align: center;
}



.list_item{  display: flex; align-items: center;  width: 100%; justify-content: flex-start; margin: 10px 0px;  }
.list_item .item_label { margin-right:10px; white-space: nowrap;  font-size: 16px; }
.list_item .item_label i {  font-size:24px; color:#999;  }
.list_item .list_inp{ width:100%; border:none; border-bottom:1px solid #ddd; height:40px; line-height:40px; font-size: 16px;  }
.list_item .el-select{width:100%;border: none;border-bottom: 1px solid #ddd;height:40px;line-height:40px;font-size: 16px;color:#999;}
.list_item .el-select .el-input__inner{ font-size: 16px;  color:#999;  }

.list_type{ margin-bottom:20px;  }


.list_btn{  text-align:center;  }

.list_btn .get_btn{ width: 100%; height: 50px; line-height: 46px; background: var(--theme); border: 0px solid #ccc; font-weight: 600; font-size: 20px; color: #fff; margin-bottom:20px; padding:0;  margin-top:20px; } 

.list_btn .valid_btn{width: 100%;height: 50px;line-height: 46px;background: var(--theme);border: 0px solid #ccc;font-weight: 600;font-size: 20px;color: #fff;margin-bottom:20px;padding:0;margin-top:20px;} 

.list_btn .update_btn{ width: 100%; height: 50px; line-height: 46px; background: var(--theme); border: 0px solid #ccc; font-weight: 600; font-size: 20px; color: #fff; margin-bottom:20px; padding:0;  margin-top:20px; } 

.list_btn .r-login{  font-size: 16px; color: #999;  } 
.list_btn .r-login:hover{cursor:pointer;color: var(--theme);} 

.line_number.line_number1,.line_number.line_number2 {
    background: rgb(255, 255, 255);
    width: 40px;
    height: 40px;
    font-size: 22px;
    border-radius: 50%;
    border-width: 4px;
    border-style: solid;
    border-color: var(--theme);
    border-image: initial;
    display: flex;
    align-items: center;
    justify-content: center;
    color: var(--theme);
}

.line {
    width: 80%;
    height: 2px;
    background: rgb(204, 204, 204);
    position: absolute;
    top: 50%;
    z-index: -1;
}

.tab_line {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 30px 10px;
    position: relative;
}


.line_number {
    background: rgb(255, 255, 255);
    width: 40px;
    height: 40px;
    font-size: 18px;
    border-radius: 50%;
    border: 4px solid rgb(221, 221, 221);
    display: flex;
    align-items: center;
    justify-content: center;
    color: rgb(170, 170, 170);
}

.num_line {
    display: flex;
    justify-content: space-between;
    width: 100%;
}

</style>