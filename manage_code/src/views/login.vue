<template>
	<div>
		<div class="login_view">
			<el-form :model="loginForm" class="login_form">
				<div class="title_view">基于微信小程序的海外拼团购物平台的设计和实现登录</div>
				<div class="list_item" v-if="loginType==1">
					<div class="list_label">
						账号：
					</div>
					<input class="list_inp" v-model="loginForm.username" placeholder="请输入账号" name="username" />
				</div>
				<div class="list_item" v-if="loginType==1">
					<div class="list_label">
						密码：
					</div>
					<input class="list_inp" v-model="loginForm.password" type="password" placeholder="请输入密码" @keydown.enter.native="handleLogin"  />
				</div>
				<div class="list_type" v-if="userList.length>1">
					<div class="list_label">
						用户类型：
					</div>
				  <el-select v-model="loginForm.role" placeholder="请选择用户类型">
				    <el-option v-for="(item,index) in userList" :label="item.roleName" :value="item.roleName"></el-option>
				  </el-select>
				</div>
				<div class="btn_view">
					<el-button class="login" v-if="loginType==1" type="success" @click="handleLogin">登录</el-button>
					<el-button class="register" type="primary" @click="handleRegister('shangjia')">注册商家</el-button>
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
	} from "vue";
	import { useStore } from 'vuex'
	const store = useStore()
	const userList = ref([])
	const menus = ref([])
	const loginForm = ref({
		role: '',
		username: '',
		password: ''
	})
	const tableName = ref('')
	const loginType = ref(1)
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	//注册
    const handleRegister = (tableName) => {
    	context?.$router.push(`/${tableName}Register`)
    }
	const handleLogin = () => {
		if (!loginForm.value.username) {
			context?.$toolUtil.message('请输入用户名', 'error')
			
			return;
		}
		if (!loginForm.value.password) {
			context?.$toolUtil.message('请输入密码', 'error')
			return;
		}
		if (userList.value.length > 1) {
			if (!loginForm.value.role) {
				context?.$toolUtil.message('请选择角色', 'error')
				verifySlider.value.reset()
				return;
			}
			for (let i = 0; i < menus.value.length; i++) {
				if (menus.value[i].roleName == loginForm.value.role) {
					tableName.value = menus.value[i].pathName||menus.value[i].tableName;
				}
			}
		} else {
			tableName.value = userList.value[0].pathName||userList.value[0].tableName;
			loginForm.value.role = userList.value[0].roleName;
		}
		login()
	}
	const login = () => {
		context?.$http({
			url: `${tableName.value}/login?username=${loginForm.value.username}&password=${loginForm.value.password}`,
			method: 'post'
		}).then(res => {
			context?.$toolUtil.storageSet("Token", res.data.token);
			context?.$toolUtil.storageSet("role", loginForm.value.role);
			context?.$toolUtil.storageSet("sessionTable", tableName.value);
			context?.$toolUtil.storageSet("adminName", loginForm.value.username);
			store.dispatch('user/getSession')	//vuex中获取用户信息并保存
			context?.$router.push('/')
		}, err => {
		})
	}
	//获取菜单
	const getMenu=()=> {
      let params = {
        page: 1,
        limit: 1,
        sort: 'id',
      }

      context?.$http({
        url: "menu/list",
        method: "get",
        params: params
      }).then(res => {
          menus.value = JSON.parse(res.data.data.list[0].menujson)
          for (let i = 0; i < menus.value.length; i++) {
            if (menus.value[i].hasBackLogin=='是') {
              userList.value.push(menus.value[i])
            }
          }
			loginForm.value.role = userList.value[0].roleName
          context?.$toolUtil.storageSet("menus", JSON.stringify(menus.value));
      })
    }
	//初始化
	const init = () => {
		getMenu();
	}
	onMounted(()=>{
		init()
		

	})
</script>

<style lang="scss" scoped>
	.login_view {
        background-image: url("http://clfile.zggen.cn/20241022/0c61db5c3eac46e6bc32ce0375738a4a.webp")!important;
		// 表单盒子
		.login_form {
		}
		.title_view {
		}
		// item盒子
		.list_item {
			// label
			.list_label {
			}
			// 输入框
			.list_inp {
			}
		}
		.list_type {
			.list_label {
			}
			// 下拉框样式
			:deep(.el-select) {
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
		.btn_view {
			// 登录
			.login {
			}
			// 注册
			.register {
			}
		}
	}

</style>
<style>
.login_view {
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

/*表单盒子*/
.login_view .login_form{width: 650px;margin: 6vh auto 0;padding:30px 50px 40px 50px;background: none;font-size:16px;border-radius:10px;transform:scale(1);-webkit-transform:scale(1);-ms-transform:scale(1);z-index:5;display: flex;flex-wrap: wrap;}

/*标题*/
.login_view .login_form .title_view{width:100%;float:left;line-height:46px;font-size:24px;font-weight:600;letter-spacing:2px;color: #fff;position: absolute;margin-bottom:20px;top: -10vh;left: 0;text-align: center;}

/*list_item*/
.login_view .login_form .list_item{display: flex;align-items: center;width: 100%;justify-content: flex-start;margin: 10px auto;background: #fff;border-radius: 6px;overflow: hidden;padding: 8px;border: 1px solid #D7DEE8;order: 2;}
.login_view .login_form .list_item .list_label {margin-right:10px;font-size:16px;white-space:nowrap;line-height: 50px;background: var(--theme);border-radius: 5px;text-align: center;width: 120px;flex-shrink: 0;}
.login_view .login_form .list_item .list_label i {  font-size:24px; color:#999;  }
.login_view .login_form .list_item .list_inp{width:100%;border:none;border-bottom: 0px solid #ddd;height:40px;line-height:40px;font-size:inherit;}
.login_view .login_form .list_item .el-select{  }

/*用户类型*/
.login_view .login_form .list_type{display: flex;align-items: center;width: 100%;justify-content: center;padding: 10px 0px;margin-bottom:10px;order: 1;background: #fff;border: 1px solid #D7DEE8;border-radius: 6px;padding: 8px;}
.login_view .login_form .list_type .el-radio{ }
.login_view .login_form .list_type .el-radio .el-radio__label{ color: #666;  font-size: 16px; }
.login_view .login_form .list_type .el-radio.is-checked{ }
.login_view .login_form .list_type .el-radio.is-checked .el-radio__label{ color: rgb(139, 92, 126);  font-size: 16px; }
.login_view .login_form .list_type .el-radio__input{ }
.login_view .login_form .list_type .el-radio__input.is-checked{ }
.login_view .login_form .list_type .el-radio__input .el-radio__inner{ background: rgb(255, 255, 255); border: 1px solid rgb(220, 223, 230); }
.login_view .login_form .list_type .el-radio__input.is-checked  .el-radio__inner{ background-color: rgb(139, 92, 126); border-color: rgb(139, 92, 126); }

/*记住密码*/
.login_view .login_form .remember_view{  margin-bottom:20px;  }
.login_view .login_form .remember_view .el-checkbox{ width: 20%; margin: 0px;  display: flex;  justify-content: center; align-items: center; }
.login_view .login_form .remember_view .el-checkbox .el-checkbox__label{ background: rgb(255, 255, 255); }
.login_view .login_form .remember_view .el-checkbox .el-checkbox__inner{ color: rgb(153, 153, 153); }
.login_view .login_form .remember_view .el-checkbox.is-checked .el-checkbox__label{ color:#8b5c7e; font-size: 15px;  }
.login_view .login_form .remember_view .el-checkbox.is-checked .el-checkbox__inner{  background: #8b5c7e;  border-color: #8b5c7e; font-size: 16px; }

/* 按钮 */
.login_view .login_form .btn_view{text-align:center;order: 4;width: 100%;margin-top: 60px;display: flex;flex-wrap: wrap;justify-content: space-between;position: relative;}
.login_view .login_form .btn_view .login{width: 100%;height: 50px;line-height: 46px;background: var(--theme);border: 0px solid #ccc;font-weight: 600;font-size: 20px;margin-bottom:20px;padding:0;color: #444444;} 
.login_view .login_form .btn_view .register{background: #444444;border:1px solid #ccc;font-size:16px;color: #fff;width: 48%;margin: 0;height: 50px;} 
.login_view .login_form .btn_view .forget{background: none;border: 0px solid #ccc;font-size:16px;color: #666;position: absolute;width: 100%;top: -50px;} 

.login_view .login_form .face{width:100%;height: 50px;line-height:46px;background: #fff;border: 3px solid #ccc;font-weight: 600;font-size: 20px;color: #999;margin-bottom:20px;text-align:center;margin-top:20px;padding:0;order: 5;cursor: pointer;} 
.login_view .login_form .face:hover {color: var(--theme);border-color: var(--theme);} 



.login_view .listCode_view {
    order: 3;
    width: 100%;
    display: flex;
    padding: 8px;
    background: #fff;
    border-radius: 6px;
}

.login_view .list_label {
    background: var(--theme);
    line-height: 50px;
    text-align: center;
    border-radius: 6px;
    width: 120px;
    flex-shrink: 0;
}

.login_view .el-select {
    flex: 1;
    height: 50px;
    border: none!important;
    padding: 0 10px;
}

.login_view .listCode_label {
    background: var(--theme);
    border-radius: 6px;
    line-height: 50px;
    width: 120px;
    flex-shrink: 0;
    text-align: center;
}

.login_view .listCode_view input {
    border: none;
    flex: 1;
}

.login_view .listCode_btn {
    background: var(--theme);
    display: flex;
    align-items: center;
    width: 110px;
    border-radius: 6px;
    justify-content: center;
}
</style>