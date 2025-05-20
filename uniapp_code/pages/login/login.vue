<template>
	<view class="content">
		<view  :style='{"width":"100%","padding":"21vh 0 0  0 ","position":"relative","background":"url(http://clfile.zggen.cn/20241212/a60a4219dfa341d8873a303cb93db215.png) no-repeat center / 100% 100%","height":"100%"}'>
			<view :style='{"minHeight":"79vh","padding":"30px","margin":"5px 0 0 0","borderRadius":"60px 60px 0 0","background":"#fff","bottom":"0","display":"block","width":"100%","height":"100%"}'>
				<image :style='{"width":"80px","margin":"0 auto 12px auto","borderRadius":"4px","display":"none","height":"80px"}' src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" mode="aspectFill"></image>
				<view v-if="loginType==1" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' class="uni-form-item uni-column">
					<input v-model="username" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="text" class="uni-input" name="" placeholder="请输入账号" />
				</view>
				<view v-if="loginType==1" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' class="uni-form-item uni-column">
					<input v-model="password" password :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="password" class="uni-input" name="" placeholder="请输入密码" />
				</view>
				<picker v-if="roleNum>1" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"20px 0","color":"rgb(33, 27, 29)","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' @change="optionsChange" :value="index" :range="options">
					<view class="uni-picker-type" :style='{"width":"100%","lineHeight":"44px","fontSize":"14px","color":"#211B1D"}'>{{options[index]}}</view>
				</picker>
			<button v-if="loginType==1" class="btn-submit" @tap="onLoginTap" type="primary" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}'>登录</button>
				<button v-if="loginType==2" class="btn-submit" @tap="onFaceLoginTap" type="primary" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}'>人脸识别登录</button>
				<view class="links" :style='{"width":"100%","padding":"10px 0","margin":"0 0 12px 0","justifyContent":"space-between","display":"flex","height":"auto"}'>
					<view class="link-highlight" @tap="onRegisterTap('yonghu')" :style='{"padding":"12px 20px","fontSize":"14px","color":"#F8678C","borderRadius":"5px","background":"#F6F4F5"}'>注册用户</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import menu from '@/utils/menu'
	export default {
		data() {
			return {
				username: '',
				password: '',
                loginType:1,
				codes: [{
				  num: 1,
				  color: '#000',
				  rotate: '10deg',
				  size: '16px'
				}, {
				  num: 2,
				  color: '#000',
				  rotate: '10deg',
				  size: '16px'
				}, {
				  num: 3,
				  color: '#000',
				  rotate: '10deg',
				  size: '16px'
				}, {
				  num: 4,
				  color: '#000',
				  rotate: '10deg',
				  size: '16px'
				}],
				options: [
				],
                optionsValues: [
				],
				index: 1,
				roleNum:0,
			}
		},
		onLoad() {
			var that = this;
			var c = this;
			let options = ['请选择登录用户类型'];
			let optionsValues=['请选择登录用户类型']
			let menus = menu.list();
			this.menuList = menus;
			for(let i=0;i<this.menuList.length;i++){
				if(this.menuList[i].hasFrontLogin=='是' && this.menuList[i].roleName ){
					options.push(this.menuList[i].roleName);
					optionsValues.push(this.menuList[i].pathName)
					this.roleNum++;
				}
			}
			if(this.roleNum==1) {
				this.index = 1;
			}

			this.options = options;
			this.optionsValues=optionsValues;
			this.styleChange()
		},
		methods: {
						getmenuName(){
				const role = this.options[this.index];
				const menu = this.menuList;
				var loginRole;
				menu.map(obj => {
					if (role == obj.roleName) {
						loginRole = obj.pathName
					}
				})
				return loginRole
			},
			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('.uni-input .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.loginFrom.content.input.backgroundColor
					// })
				})
			},
			onRegisterTap(tableName) {
                uni.setStorageSync("loginTable", tableName);
				this.$utils.jump('../register/register')
			},
			async onLoginTap() {
                if (!this.username) {
					this.$utils.msg('请输入用户名')
					return
				}
                if (!this.password) {
					this.$utils.msg('请输入用户密码')
					return
				}
	            if (this.index==0) {
					this.$utils.msg('请选择登录用户类型')
					return
				}
	           let loginRole = this.getmenuName()
				let res = await this.$api.login(`${loginRole}`, {
					username: this.username,
					password: this.password
				});
                uni.removeStorageSync("useridTag");
				uni.setStorageSync("token", res.token);
				uni.setStorageSync("nickname",this.username);
				uni.setStorageSync("nowTable", `${loginRole}`);
				res = await this.$api.session(`${loginRole}`);
                if(res.data.touxiang) {
                    uni.setStorageSync('headportrait', res.data.touxiang);
                } else if(res.data.headportrait) {
                    uni.setStorageSync('headportrait', res.data.headportrait);
                }	// 保存用户id
	uni.setStorageSync("userid", res.data.id);
				uni.setStorageSync('userSession',JSON.stringify(res.data))

				if(res.data.vip) {
					uni.setStorageSync("vip", res.data.vip);
				}
				uni.setStorageSync("appRole", `${this.options[this.index]}`);
				this.$utils.tab('../index/index');
			},
			optionsChange(e) {
				this.index = e.target.value
			},
		}
	}
</script>

<style lang="scss" scoped>

.content {
min-height: calc(100vh - 44px);
box-sizing: border-box;
}
</style>
