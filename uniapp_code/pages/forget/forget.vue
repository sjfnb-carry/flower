<template>
	<view class="content">
		<view class="box" :style='{"width":"100%","padding":"0px 12px 12px 12px","background":"#F6F4F5","height":"100%"}' v-if="type==1">
			<view :style='{"width":"100%","padding":"12px 12px 92px 12px","background":"#FFFFFF","display":"block","height":"auto"}'>
				<view class="forget-input" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}'>
					<input v-model="username" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="text" placeholder="请输入您的账号" />
				</view>
				<picker :style='{"border":"0","padding":"0px 12px 0 12px","margin":"20px 0","color":"rgb(33, 27, 29)","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' @change="optionsChange" :value="index" :range="options">
					<view class="uni-input" :style='{"width":"100%","lineHeight":"44px","fontSize":"14px","color":"#9B9B9B"}'>{{options[index]}}</view>
				</picker>
				<button class="landing" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' @tap="nextClick" type="primary">下一步</button>
			</view>
		</view>
        <view class="box" :style='{"width":"100%","padding":"0px 12px 12px 12px","background":"#F6F4F5","height":"100%"}' v-if="type==2">
            <view :style='{"width":"100%","padding":"12px 12px 92px 12px","background":"#FFFFFF","display":"block","height":"auto"}'>
                <view class="forget-input" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}'>
                    <input v-model="userForm.pquestion"  :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="text" placeholder="密保问题" disabled="disabled"/>
                </view>
                <view class="forget-input" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}'>
                    <input v-model="panswer1" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="text" placeholder="密保答案" />
                </view>
                <button class="landing" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' @tap="confirmClick" type="primary">确定</button>
            </view>
        </view>
        <view class="box" :style='{"width":"100%","padding":"0px 12px 12px 12px","background":"#F6F4F5","height":"100%"}' v-if="type==3">
            <view :style='{"width":"100%","padding":"12px 12px 92px 12px","background":"#FFFFFF","display":"block","height":"auto"}'>
                <view class="forget-input" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}'>
                    <input v-model="password" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="password" placeholder="密码" />
                </view>
                <view class="forget-input" :style='{"width":"100%","margin":"20px 0 ","height":"auto"}'>
                    <input v-model="repassword" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0 0 0 0","color":"#211B1D","borderRadius":"5px","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' type="password" placeholder="确认密码" />
                </view>
                <button class="landing" :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' @tap="updateClick" type="primary">修改密码</button>
            </view>
        </view>
	</view>
</template>

<script>
  import menu from '@/utils/menu'
	export default {
		data() {
			return {
              username:"",
				options: [
                ],
                optionsValues: [
				],
roleNum:0,
				index: 0,
                tableName: '',
                type: 1,
                repassword: '',
                password: '',
                panswer1: '',
                userForm: {
                }
			}
		},
		onLoad() {
          let options = ['请选择登录用户类型'];
          let optionsValues=['请选择登录用户类型']
          let menus = menu.list();
          this.menuList = menus;
          for(let i=0;i<this.menuList.length;i++){
            if(this.menuList[i].hasFrontLogin=='是' && this.menuList[i].roleName ){
              options.push(this.menuList[i].roleName);
              optionsValues.push(this.menuList[i].tableName)
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
			async nextClick() {
				if(this.username==undefined) {
					this.$utils.msg('请输入账号')
					return;
                                }
				if(this.index==0) {
					this.$utils.msg('请选择角色')
                    return;
				}
                this.tableName = this.optionsValues[this.index];
                let res = await this.$api.security(this.tableName,{
                    username: this.username
                })
                if(!res.data) {
                    this.$utils.msg('用户不存在')
                }
                if (res.code == 0) {
                    this.userForm = res.data
                    this.type = 2
                }
			},
			optionsChange(e) {
				this.index = e.target.value
			},
			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('.forget-input .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.restPwFrom.content.input.backgroundColor
					// })
				})
			},
            confirmClick() {
                if (this.userForm.panswer !== this.panswer1) {
                    this.$utils.msg('密保答案不正确')
                    return false
                }
                this.$utils.msg('验证成功')
                this.password = ''
                setTimeout(() => {
                    this.type = 3
                }, 1000)
            },
            async updateClick() {
                if(!this.password) {
                    this.$utils.msg('密码不能为空')
                    return false
                }
                if (this.password != this.repassword) {
                    this.$utils.msg('两次密码输入不一致')
                    return false
                }
                if(this.userForm.mima) {
                    this.userForm.mima=this.password;
                } else {
                    this.userForm.password=this.password;
                }
                await this.$api.update(this.tableName, this.userForm)
                this.$utils.msg('密码修改成功')
                setTimeout(() => {
                    uni.navigateBack({
            
                    })
                }, 1000)
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
