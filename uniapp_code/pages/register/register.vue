<template>
<view class="content">
	<view class="box" :style='{"width":"100%","padding":"21vh 0 0  0 ","position":"relative","background":"url(http://clfile.zggen.cn/20241212/a60a4219dfa341d8873a303cb93db215.png) no-repeat center / 100% 100%","height":"100%"}'>
		<view :style='{"minHeight":"79vh","padding":"40px","borderRadius":"60px 60px 0 0","background":"#fff","bottom":"0","display":"block","width":"100%","overflow-y":"scroll","height":"100%"}'>
			<image :style='{"width":"80px","margin":"0 auto 12px auto","borderRadius":"4px","display":"none","height":"80px"}' src="http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg" mode="aspectFill"></image>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='users'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.username" type="text" class="uni-input" name="" placeholder="用户名" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='users'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.password" type="text" class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='users'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.role" type="text" class="uni-input" name="" placeholder="角色" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.yonghuxingming" type="text" class="uni-input" name="" placeholder="用户姓名" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.nianling" type="text" class="uni-input" name="" placeholder="年龄" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.lianxifangshi" type="text" class="uni-input" name="" placeholder="联系方式" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.zhanghao" type="text" class="uni-input" name="" placeholder="账号" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' v-model="ruleForm.mima" type="password" class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' v-model="ruleForm.mima2" type="password" class="uni-input" name="" placeholder="确认密码" />
			</view>
            <view :style='{"width":"100%","padding":"20px 0 0 0","height":"100%"}' v-if="tableName=='yonghu'" @tap="yonghutouxiangTap" class="">
                <image :style='{"width":"80px","borderRadius":"100%","display":"block","height":"80px"}' v-if="ruleForm.touxiang" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
                <image :style='{"width":"80px","borderRadius":"100%","display":"block","height":"80px"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
				<view :style='{"color":"#AAA9A9"}' >请上传头像</view>
            </view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='shangjia'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.shangjiamingcheng" type="text" class="uni-input" name="" placeholder="商家名称" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='shangjia'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.lianxidianhua" type="text" class="uni-input" name="" placeholder="联系电话" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='shangjia'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}'  v-model="ruleForm.lianxidizhi" type="text" class="uni-input" name="" placeholder="联系地址" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='shangjia'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' v-model="ruleForm.mima" type="password" class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"width":"100%","margin":"20px 0 ","height":"auto"}' v-if="tableName=='shangjia'" class="uni-form-item uni-column">
				<input :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' v-model="ruleForm.mima2" type="password" class="uni-input" name="" placeholder="确认密码" />
			</view>
            <view :style='{"width":"100%","padding":"20px 0 0 0","height":"100%"}' v-if="tableName=='shangjia'" @tap="shangjiatouxiangTap" class="">
                <image :style='{"width":"80px","borderRadius":"100%","display":"block","height":"80px"}' v-if="ruleForm.touxiang" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
                <image :style='{"width":"80px","borderRadius":"100%","display":"block","height":"80px"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
				<view :style='{"color":"#AAA9A9"}' >请上传头像</view>
            </view>
			<button :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' class="btn-submit" @tap="register" type="primary">注册</button>
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
                username: '',
                password: '',
                role: '',
                yonghuxingming: '',
                nianling: '',
                lianxifangshi: '',
                zhanghao: '',
                mima: '',
                touxiang: '',
                jf: '',
                money: '',
                shangjiamingcheng: '',
                lianxidianhua: '',
                lianxidizhi: '',
                mima: '',
                touxiang: '',
                dianpuzizhi: '',
                shhf: '',
				},
				tableName:""
			}
		},
        components: {
            multipleSelect
        },
        computed: {
            baseUrl() {
                return this.$base.url;
            },
        },
		async onLoad() {
			let res = [];
			let table = uni.getStorageSync("loginTable");
            this.tableName = table;

			
			this.styleChange()
		},
		methods: {

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
					  const icon = {"borderColor":"#666","background":"#fff"}
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
			// 注册
			async register() {
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
                if(`yonghu` == this.tableName && (this.ruleForm.mima!=this.ruleForm.mima2)){
                    this.$utils.msg(`两次密码输入不一致`);
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
                if(`shangjia` == this.tableName && (this.ruleForm.mima!=this.ruleForm.mima2)){
                    this.$utils.msg(`两次密码输入不一致`);
                    return
                }
				await this.$api.register(`${this.tableName}`, this.ruleForm);
				this.$utils.msgBack('注册成功');;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.box{
width: 100%;
padding: 21vh 0 0  0 ;
position: relative;
background: url(http://clfile.zggen.cn/20241212/a60a4219dfa341d8873a303cb93db215.png) no-repeat center / 100% 100%;
height: 100%;
	}
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
