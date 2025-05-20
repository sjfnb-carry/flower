<template>
<view class="content">
	<view class="box" :style='{"width":"100%","padding":"0px 12px 42px 12px","position":"relative","background":"#F6F4F5","height":"100%"}'>
		<view :style='{"width":"100%","padding":"12px 12px 92px 12px","background":"#FFFFFF","display":"block","height":"auto"}'>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='users'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>用户名</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.username" placeholder="用户名"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='users'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>密码</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.password" placeholder="密码"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='users'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>角色</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.role" placeholder="角色"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>用户姓名</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.yonghuxingming" placeholder="用户姓名"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>年龄</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.nianling" placeholder="年龄"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>联系方式</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.lianxifangshi" placeholder="联系方式"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>账号</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' disabled="true"  v-model="ruleForm.zhanghao" placeholder="账号"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" @tap="yonghutouxiangTap" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>头像</view>
				<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' v-if="ruleForm.touxiang" style="margin: 0;" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' v-else class="avator" style="margin: 0;" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>积分</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' disabled="true"  v-model="ruleForm.jf" placeholder="积分"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='shangjia'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>商家名称</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' disabled="true"  v-model="ruleForm.shangjiamingcheng" placeholder="商家名称"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='shangjia'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>联系电话</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.lianxidianhua" placeholder="联系电话"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='shangjia'" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>联系地址</view>
				<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}'   v-model="ruleForm.lianxidizhi" placeholder="联系地址"></input>
			</view>
			<view :style='{"border":"0","padding":"0px 12px 0 12px","margin":"10px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"44px"}' v-if="tableName=='shangjia'" @tap="shangjiatouxiangTap" class="">
				<view class="title" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000000","textAlign":"right"}'>头像</view>
				<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' v-if="ruleForm.touxiang" style="margin: 0;" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' v-else class="avator" style="margin: 0;" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"width":"100%","flexDirection":"column","display":"flex","height":"auto"}' class="btn">
				<button @tap="update()" class="cu-btn lg" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}'>保存</button>
				<button @tap="logout()" class="cu-btn lg" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#E0BABA","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}'>退出登录</button>
			</view>
		</view>
	</view>
</view>
</template>

<script>
    import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				ruleForm: {
				},
				tableName:"",
			}
		},
        components: {
            multipleSelect
        },
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onLoad() {
	       var that = this;
			let table = uni.getStorageSync("nowTable");
			let res = await this.$api.session(table);
			this.ruleForm = res.data;
			this.tableName = table;
			this.styleChange()
            this.$forceUpdate()
		},
		methods: {
async getSession(){
let res = await this.$api.session(this.tableName)
this.ruleForm = res.data;
uni.setStorageSync('userSession',JSON.stringify(res.data))
},
			yonghutouxiangTap() {
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'file/' + res.file;
					_this.$forceUpdate();
				});
			},
			shangjiatouxiangTap() {
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'file/' + res.file;
					_this.$forceUpdate();
				});
			},

            toggleTab(str) {
                this.$refs[str].show();
            },

			styleChange() {
				this.$nextTick(()=>{
					document.querySelectorAll('.radioText').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"color":"#666"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.radioTextActive').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"color":"#E9607F"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.checkText').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"color":"#AAA9A9"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.checkTextActive').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"color":"#E9607F"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.uni-radio-input').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"border":"1px solid #AAA9A9","color":"#AAA9A9","background":"#fff"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.uni-radio-input-checked').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"borderColor":"#E9607F","background":"#E9607F"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.uni-checkbox-input').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"border":"1px solid #AAA9A9","background":"#fff"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
					document.querySelectorAll('.uni-checkbox-input-checked').forEach(el => {
					  el.removeAttribute('style')
					  const icon = {"color":"#fff","borderColor":"#E9607F","background":"#E9607F"}
					  Object.keys(icon).forEach((key) => {
						el.style[key] = icon[key]
					  })
					})
				})
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			logout() {
				uni.clearStorageSync()
				uni.reLaunch({
					url:'/pages/index/index'
				})
			},
			
			// 注册
			async update() {
				if((!this.ruleForm.username) && `users` == this.tableName){
					this.$utils.msg(`用户名不能为空`);
					return
				}
				if((!this.ruleForm.password) && `users` == this.tableName){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if((!this.ruleForm.yonghuxingming) && `yonghu` == this.tableName){
					this.$utils.msg(`用户姓名不能为空`);
					return
				}
				if((!this.ruleForm.nianling) && `yonghu` == this.tableName){
					this.$utils.msg(`年龄不能为空`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.nianling&&(!this.$validate.isIntNumer(this.ruleForm.nianling))){
					this.$utils.msg(`年龄应输入整数`);
					return
				}
				if((!this.ruleForm.lianxifangshi) && `yonghu` == this.tableName){
					this.$utils.msg(`联系方式不能为空`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.lianxifangshi&&(!this.$validate.isMobile(this.ruleForm.lianxifangshi))){
					this.$utils.msg(`联系方式应输入手机格式`);
					return
				}
				if((!this.ruleForm.zhanghao) && `yonghu` == this.tableName){
					this.$utils.msg(`账号不能为空`);
					return
				}
				if((!this.ruleForm.mima) && `yonghu` == this.tableName){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.jf&&(!this.$validate.isNumber(this.ruleForm.jf))){
					this.$utils.msg(`积分应输入数字`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.money&&(!this.$validate.isNumber(this.ruleForm.money))){
					this.$utils.msg(`余额应输入数字`);
					return
				}
				if((!this.ruleForm.shangjiamingcheng) && `shangjia` == this.tableName){
					this.$utils.msg(`商家名称不能为空`);
					return
				}
				if((!this.ruleForm.lianxidianhua) && `shangjia` == this.tableName){
					this.$utils.msg(`联系电话不能为空`);
					return
				}
				if((!this.ruleForm.lianxidizhi) && `shangjia` == this.tableName){
					this.$utils.msg(`联系地址不能为空`);
					return
				}
				if((!this.ruleForm.mima) && `shangjia` == this.tableName){
					this.$utils.msg(`密码不能为空`);
					return
				}
				let table = uni.getStorageSync("nowTable");
				await this.$api.update(table, this.ruleForm);
				this.$utils.msgBack('修改成功');;
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
