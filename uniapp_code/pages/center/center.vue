<template>
<view class="content">
	<view :style='{"width":"100%","padding":"0 0 0 0","position":"relative","background":"#F6F4F5","height":"100%"}'>
		<view v-if="role"  :style='{"padding":"0 12px 0 12px","margin":"50px 10px 20px 10px","borderRadius":"10px","flexWrap":"wrap","background":"#E9607F","display":"flex","width":"calc(100% - 20px)","position":"relative","height":"auto"}' class="header" v-bind:class="{'status':isH5Plus}" @tap="onPageTap2('../user-info/user-info',1)">
			<view :style='{"width":"100%","alignItems":"center","display":"flex","height":"auto"}'>



<image :style='{"border":"10px solid #E9607F","margin":"0 20px 0 0 ","borderRadius":"100%","top":"-20px","left":"30px","display":"flex","width":"80px","position":"absolute","height":"80px"}' :src="user.touxiang?baseUrl+user.touxiang:'/static/gen/upload.png'"></image>

<view :style='{"width":"auto","margin":"20px 0 0 40%","color":"#fff","display":"flex","height":"auto"}'>{{user.zhanghao}}<text v-if="user.vip&& user.vip=='是'">(VIP)</text></view>



</view>
<view :style='{"width":"100%","padding":"0 0 10px 0","alignItems":"center","display":"flex","height":"auto"}' v-if="tableName=='users'" class="userinfo">
<view :style='{"margin":"25px 0 0 0","flex":"1","flexDirection":"column","display":"flex","width":"100%","justifyContent":"center","height":"100%"}' class="info">
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","background":"#fff","borderWidth":"0 0 1px 0 ","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.money">余额：{{user.money}}</view>
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","borderRadius":"0 0 10px 10px","background":"#fff","borderWidth":"0","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.jf||user.jf==0">积分：{{user.jf}}</view>
</view>
</view>
<view :style='{"width":"100%","padding":"0 0 10px 0","alignItems":"center","display":"flex","height":"auto"}' v-if="tableName=='yonghu'" class="userinfo">
<view :style='{"margin":"25px 0 0 0","flex":"1","flexDirection":"column","display":"flex","width":"100%","justifyContent":"center","height":"100%"}' class="info">
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","background":"#fff","borderWidth":"0 0 1px 0 ","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.money">余额：{{user.money}}</view>
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","borderRadius":"0 0 10px 10px","background":"#fff","borderWidth":"0","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.jf||user.jf==0">积分：{{user.jf}}</view>
</view>
</view>
<view :style='{"width":"100%","padding":"0 0 10px 0","alignItems":"center","display":"flex","height":"auto"}' v-if="tableName=='shangjia'" class="userinfo">
<view :style='{"margin":"25px 0 0 0","flex":"1","flexDirection":"column","display":"flex","width":"100%","justifyContent":"center","height":"100%"}' class="info">
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","background":"#fff","borderWidth":"0 0 1px 0 ","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.money">余额：{{user.money}}</view>
<view :style='{"padding":"0 10px","borderColor":"#C4C4C4","color":"#000","borderRadius":"0 0 10px 10px","background":"#fff","borderWidth":"0","width":"100%","lineHeight":"40px","fontSize":"12px","borderStyle":"solid","height":"40px"}' v-if="user.jf||user.jf==0">积分：{{user.jf}}</view>
</view>
</view>
			<view :style='{"alignItems":"center","top":"-25px","display":"flex","width":"56px","position":"absolute","right":"10px","justifyContent":"center","height":"100%"}' class="setting">
			<view :style='{"border":"0","width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fff","borderRadius":"0"}' class="iconfont icon-shezhi1"></view>
			</view>
			</view>
			<view v-if="!role"  :style='{"padding":"0 12px 0 12px","margin":"50px 10px 20px 10px","borderRadius":"10px","flexWrap":"wrap","background":"#E9607F","display":"flex","width":"calc(100% - 20px)","position":"relative","height":"auto"}' class="header" v-bind:class="{'status':isH5Plus}" @tap="onPageTap2('../user-info/user-info',1)">
				<view :style='{"padding":"30px 0","alignItems":"center","color":"#fff","textAlign":"center","display":"flex","width":"100%","fontSize":"20px","justifyContent":"center","height":"100%"}'>登录/注册</view>
			</view>
			<view v-if="role"   :style='{"padding":"20px 10px","margin":"0 10px","borderRadius":"10px","flexWrap":"wrap","background":"#fff","display":"flex","width":"calc(100% - 20px)","height":"auto"}' class="list">
				<block v-for="(item,index) in menuList" v-bind:key="item.roleName" >
					<block v-if="item.child.length>1  &&item.child[0].tableName!='orders'">
						<view @tap="secondClick(index)" :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}'
							class="li" hover-class="hover">
							<view v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}'  class="iconfont"  :class="item.fontClass"></view>
							<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}' class="text">{{item.menu}}</view>
							<view v-if="true" :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
						</view>
						<uni-transition :style='{"width":"100%"}' :mode-class="['fade']" :duration="500"
							:show="secondIndex==index?true:false">
							<block v-bind:key="sort" v-for=" (child,sort) in item.child">
								<!-- #ifndef MP-WEIXIN -->
								<view :style='{"borderColor":"#ccc","margin":"0 0 0 10%","alignItems":"center","borderWidth":"0 0 1px","display":"flex","width":"90%","borderStyle":"solid","height":"auto"}'
									v-if="child.tableName!='yifahuodingdan' && child.tableName!='yituikuandingdan' &&child.tableName!='yiquxiaodingdan' && child.tableName!='weizhifudingdan' && child.tableName!='yizhifudingdan' && child.tableName!='yiwanchengdingdan'  && child.tableName!='examquestion' "
									@tap="onPageTap(child.classname,child.menuJump)"
									class="li" hover-class="hover">
									<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#333","flex":"1"}' class="text">{{child.menu}}</view>
									<view v-if="true" :style='{"width":"14px","lineHeight":"14px","fontSize":"14px","color":"#999"}' class="iconfont icon-gengduo2"></view>
								</view>
								<!-- #endif -->
								<!-- #ifdef  MP-WEIXIN -->
								<view :style='{"borderColor":"#ccc","margin":"0 0 0 10%","alignItems":"center","borderWidth":"0 0 1px","display":"flex","width":"90%","borderStyle":"solid","height":"auto"}'
									  v-if="child.tableName!='yifahuodingdan' && child.tableName!='yituikuandingdan' &&child.tableName!='yiquxiaodingdan' && child.tableName!='weizhifudingdan' && child.tableName!='yizhifudingdan' && child.tableName!='yiwanchengdingdan'  && child.tableName!='examquestion' "
									  @tap="WeixinSecondPage" :data-item="child"
									  class="li" hover-class="hover">
									<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#333","flex":"1"}' class="text">{{child.menu}}</view>
									<view v-if="true" :style='{"width":"14px","lineHeight":"14px","fontSize":"14px","color":"#999"}' class="iconfont icon-gengduo2"></view>
								</view>
								<!-- #endif -->
							</block>
						</uni-transition>
					</block>
					<block v-else-if="item.child.length&&item.child[0].tableName=='orders'">
						<!-- #ifndef MP-WEIXIN -->
						<view :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' @tap="onPageTap(item.child[0].tableName,item.child[0].menuJump)"
							class="li" hover-class="hover">
							<view v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}'  class="iconfont" :class="item.fontClass"></view>
							<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}' class="text">{{item.menu}}</view>
							<view v-if="true" :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
						</view>
						<!-- #endif -->
						<!-- #ifdef  MP-WEIXIN -->
						<view :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}'   @tap="onPageTapWechat" :data-item="item"
							  class="li" hover-class="hover">
							<view v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}'  class="iconfont" :class="item.fontClass"></view>
							<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}' class="text">{{item.menu}}</view>
							<view v-if="true" :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
						</view>
						<!-- #endif -->
					</block>
					<block v-else-if="!item.child[0].tableName.includes('coupon')">
						<!-- #ifndef MP-WEIXIN -->
						<view @tap="onPageTap(item.child[0].classname,item.child[0].menuJump)"   :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' class="li" hover-class="hover">
							<view  v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}' class="iconfont" :class="item.fontClass"></view>
							<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}' class="text">{{item.child[0].menu}}</view>
							<view v-if="true"  :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
						</view>
						<!-- #endif -->
						<!-- #ifdef  MP-WEIXIN -->
						<view wx:if="item.child.length>0"  @tap="onPageTapWechat" :data-item="item" :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' class="li" hover-class="hover">
							<view  v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}' class="iconfont" :class="item.fontClass"></view>
							<view :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}' class="text">{{item.child[0].menu}}</view>
							<view v-if="true"  :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
						</view>
						<!-- #endif -->
					</block>
				</block>

				<view v-if="user&&user.id" @tap="onPageTap2('../shop-recharge/pay-confirm')" :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' class="li" hover-class="hover">
					<view v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}' class="iconfont icon-chongzhi1"></view>
					<view class="text" :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}'>用户充值</view>
					<view v-if="true" :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
				</view>
				<view v-if="user&&user.id" @tap="passwordShow()" :style='{"borderColor":"#ccc","alignItems":"center","borderWidth":"0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' class="li" hover-class="hover">
					<view v-if="true" :style='{"width":"32px","lineHeight":"32px","fontSize":"32px","color":"#fa861c"}' class="iconfont icon-suoding1 "></view>
					<view class="text" :style='{"width":"100%","padding":"0 10px 0 10px","lineHeight":"44px","fontSize":"14px","color":"#000","flex":"1"}'>修改密码</view>
					<view v-if="true" :style='{"color":"#999","top":"20%","width":"14px","lineHeight":"14px","fontSize":"14px","position":"absolute","right":"0"}' class="iconfont icon-gengduo2"></view>
				</view>

			</view>
		</view>

		<uni-popup class="popup-content" ref="passwordPopup" type="bottom">
			<view class="passwordForm">
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.mima" placeholder="原密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima" placeholder="新密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima1" placeholder="确认密码" />
				</view>
				<view class="passwordBtnView">
					<button class="passwordBtn" @click="updatePassword">更新</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	import menu from '@/utils/menu'
	export default {
		data() {
		return {
headportrait:'',
				isH5Plus: true,
				user: {},
				tableName:'',
				role: '',
				menuList: [],
				secondIndex: -1,
				orderList: [{
						name: '全部',
						icon: 'icon-dingdan1'
					},
					{
						name: '未支付',
						icon: 'icon-dingdan2'
					},
					{
						name: '已支付',
						icon: 'icon-dingdan3'
					},
					{
						name: '已完成',
						icon: 'icon-dingdan4'
					},
					{
						name: '已取消',
						icon: 'icon-dingdan5'
					},
				],
				passwordForm: {
					mima: '',
					newmima: '',
					newmima1: '',
				},
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onLoad(){
			this.role = uni.getStorageSync("appRole");
			let table = uni.getStorageSync("nowTable");
			let res = await this.$api.session(table);
				this.user = res.data;
			this.headportrait=uni.getStorageSync("headportrait");
			this.tableName = table;
			let menus = menu.list();
			for (let x in menus) {
				if (menus[x].tableName == this.tableName) {
					this.menuList = menus[x].backMenu.filter(v=>v.menu!="我的优惠券管理")
					break
				}
			}
		},
		async onShow(){
            uni.removeStorageSync("useridTag");
			this.role = uni.getStorageSync("appRole");
			let table = uni.getStorageSync("nowTable");
			this.headportrait=uni.getStorageSync("headportrait");
			let res = await this.$api.session(table);
				this.user = res.data;
			this.tableName = table;
			let menus = menu.list();
			for (let x in menus) {
				if (menus[x].tableName == this.tableName) {
					this.menuList = menus[x].backMenu.filter(v=>v.menu!="我的优惠券管理")
					break
				}
			}
		},
		methods: {
			async getSession(){
				let table = uni.getStorageSync("nowTable");
				let res = await this.$api.session(table);
				this.user = res.data;
				this.$forceUpdate()
			},
			secondClick(index) {
				if (this.secondIndex == index) {
					this.secondIndex = -1
					return false
				}
				this.secondIndex = index
			},
onPageTap(tableName,menuJump) {
uni.setStorageSync("useridTag",1);
if(tableName=='orders'){
this.onPageTap2('../shop-order/shop-order')
return false
}
if(tableName=='address'){
this.onPageTap2('../shop-address/shop-address')
return false
}
if(tableName=='cart'){
this.onPageTap2('../shop-cart/shop-cart')
return false
}
if(tableName=='chat'){
this.onPageTap2('../chat/chat')
return false
}
if(tableName=='exampaper'){
this.onPageTap2('../exampaperlist/list')
return false
}
if(tableName=='examrecord'&&menuJump=='22'){
this.onPageTap2('../examfailrecord/list',2)
return false
}
if(tableName=='storeup'){
this.onPageTap2('../storeup/list?userid=' + this.user.id + '&menuJump=' + menuJump ,1)
return false
}
if(tableName=='news'){
this.onPageTap2('../news-list/news-list')
return false
}
// tableName=='popup_remind'?tableName='popupRemind':'';
this.onPageTap2(`../${tableName}/list`,2)
},
onPageTap2 (url,type=1){
if(!this.user){
uni.navigateTo({
url:'/pages/login/login'
})
return false
}
let that = this
uni.navigateTo({
url: url + (type==2?`?userid=${that.user.id}`:''),
fail: function() {
// #ifndef MP-WEIXIN
uni.switchTab({
url: url + (type==2?`?userid=${that.user.id}`:'')
});
// #endif
// #ifdef  MP-WEIXIN
uni.switchTab({
url: url
});
uni.setStorageSync('userid',type==2?`${that.user.id}`:'')
// #endif
}
});
},
// #ifdef  MP-WEIXIN
WeixinSecondPage(e){
const item=	e.currentTarget.dataset.item;
const tableName= item.classname;
const menuJump= item.menuJump;
this.onPageTap(tableName,menuJump)
},
onPageTapWechat(e){
const item=	e.currentTarget.dataset.item;
if(item.child.length &&item.child[0].tableName=='orders'){
uni.navigateTo({
url:'/pages/shop-order/shop-order'
})
return false
}else if(item.child[0].tableName=='chat'|| item.child[0].tableName=='cart' ||item.child[0].tableName=='address'||item.child[0].tableName=='storeup'){
this.onPageTap(item.child[0].tableName,item.child[0].menuJump)
return false
}else{
const tableName=item.child[0].classname;
const menuJump= item.menuJump;
this.onPageTap(tableName,menuJump)
}
},
// #endif
passwordShow() {
				this.passwordForm = {
					mima: '',
					newmima: '',
					newmima1: '',
				}
				this.$forceUpdate()
				this.$refs.passwordPopup.open()
			},
			async updatePassword() {
				if (this.passwordForm.mima == ''){
					this.$utils.msg('原密码不能为空')
					return false
				}
				if (this.passwordForm.newmima == ''){
					this.$utils.msg('新密码不能为空')
					return false
				}
				if (this.passwordForm.newmima1 == ''){
					this.$utils.msg('确认密码不能为空')
					return false
				}
				let password = ''
				if (this.tableName=='yonghu') {
					password = this.user.mima;
				}
				let newpassword = this.passwordForm.mima
				if(password != newpassword){
					this.$utils.msg('原密码不正确')
					return false
				}
				if (this.passwordForm.newmima != this.passwordForm.newmima1){
					this.$utils.msg('两次密码不一致')
					return false
				}
				if (this.tableName == 'yonghu') {
				}
				if (this.tableName=='yonghu') {
					this.user.mima = this.passwordForm.newmima
				}
				await this.$api.update(this.tableName,this.user)
				this.$utils.msg('修改密码成功,下次登录系统生效')
				this.$refs.passwordPopup.close()
				this.getSession()
			},


		}
	}
</script>

<style lang="scss" scoped>
	.no-coupon{
		position: absolute;
		right: 0;
		width: 100px;
		height: 100px;
		top: 0;
	}
	.content {
		//height: calc(100vh - 94px);
		box-sizing: border-box;
		overflow-y: scroll;
	}
	.passwordForm {
		width: 100%;
		padding: 10px 20px 60px;
		background: #fff;

		.passwordInput {
			background: #eee;
			margin: 0 0 10px;
			padding: 0 10px;

			input {
				height: 36px;
				line-height: 36px;
				font-size: 14px;
			}
		}

		.passwordBtnView {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 10px 0;

			.passwordBtn {
				padding: 0 20px;
				width: auto;
				height: 20px;
				line-height: 20px;
				font-size: 14px;
				color: #fff;
				background: #007aff;
				border: none;
				border-radius: 5px;

			}
		}
	}
</style>
