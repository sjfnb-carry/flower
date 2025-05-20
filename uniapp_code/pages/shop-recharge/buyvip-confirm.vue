<template>
	<view class="container">
		<radio-group>
			<view class="pay-type-content">
				<view class="pay-type-item">
					会员卡
				</view>
				<!-- #ifdef APP-PLUS -->
				<view class="pay-type-item" style="background:#07c160">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/weixin.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">微信付款</text>
					</view>
					<radio label="微信支付" checked="true" value="1" ></radio>
				</view>
				<view class="pay-type-item" style="background:#1678ff">
					<img class="pay-icon" mode="heightFix" src="static/pay-confirm/zhifubao.png" alt>
					<view style="display: flex;align-items: center;">
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">支付宝付款</text>
					</view>
					<radio label="支付宝支付" value="2" ></radio>
				</view>
				<view class="pay-type-item" style="background:#003a90">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="static/pay-confirm/jianshe.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">建设银行</text>
					</view>
					<radio label="建设银行"  value="3" ></radio>
				</view>
				<view class="pay-type-item"  style="background:#008566">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="static/pay-confirm/nongye.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">农业银行</text>
					</view>
					<radio label="农业银行"  value="4" ></radio>
				</view>
				<view class="pay-type-item"  style="background:#008566">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="static/pay-confirm/zhongguo.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">中国银行</text>
					</view>
					<radio label="中国银行" value="5" ></radio>
				</view>
				<view class="pay-type-item" style="background:#003a90">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="static/pay-confirm/jiaotong.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">交通银行</text>
					</view>
					<radio label="交通银行" value="6"></radio>
				</view>
				<!-- #endif -->

				<!-- #ifndef APP-PLUS -->
				<view class="pay-type-item" style="background:#07c160">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/weixin.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">微信付款</text>
					</view>
					<radio label="微信付款" checked="true" value="1" ></radio>
				</view>
				<view class="pay-type-item" style="background:#1678ff">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/zhifubao.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">支付宝付款</text>
					</view>
					<radio label="支付宝付款" value="2" ></radio>
				</view>
				<view class="pay-type-item"  style="background:#008566">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/jianshe.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">建设银行</text>
					</view>
					<radio label="建设银行"  value="3" ></radio>
				</view>
				<view class="pay-type-item"  style="background:#008566">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/nongye.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">农业银行</text>
					</view>
					<radio label="农业银行"  value="4" ></radio>
				</view>
				<view class="pay-type-item" style="background:#e60012">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/zhongguo.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">中国银行</text>
					</view>
					<radio label="中国银行" value="5" ></radio>
				</view>
				<view class="pay-type-item" style="background:#003a90">
					<view style="display: flex;align-items: center;">
						<img class="pay-icon" mode="heightFix" src="../../static/pay-confirm/jiaotong.png" alt>
						<text style="font-size: 16px;font-weight: 700;color: #FFD;margin: 0 0 0 10px;">交通银行</text>
					</view>
					<radio label="交通银行" value="6"></radio>
				</view>
				<!-- #endif -->


			</view>
			<view class="buton-content">
				<button @tap="onPageTap('buyvip-result')" class="margin-tb-sm" style="color: white;background:#E9607F;">确定</button>

			</view>
		</radio-group>
	</view>
</template>
<script>
	export default {
		data() {
			return {
			}
		},
		mounted() {
		},
		async onShow() {
			let table = uni.getStorageSync("nowTable");
			let res = await this.$api.session(table);
			this.user = res.data;
		},
		methods: {
			async onPageTap(url) {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '是否充值会员',
					success: async function(res) {
						if (res.confirm) {
							let table = uni.getStorageSync("nowTable");
							if(_this.user.vip=="是") {
								_this.$utils.msg('您已是我们的尊贵会员。');
								return false;
							}
							_this.user.vip = "是";
							let res = await _this.$api.update(table, _this.user)
							uni.setStorageSync("vip", "是");
							uni.navigateTo({
								delta: 1,
								url: url,
								fail: function() {
									uni.switchTab({
										url: url
									});
								}
							});
						}
					}
				});
			}
		}
	};
</script>
<style lang="scss" scoped>
.pay-icon{
width:50px;
height:50px;
}

	.container {
		margin: 10px;
		font-size: 14px;

		.top-content {
			display: flex;
			align-items: center;
			padding: 20upx;
		}

		.price-content {
			display: flex;
			align-items: center;
			margin-top: 20upx;
			padding-bottom: 20px;
			padding: 20upx;
			border-bottom: 1upx solid #eeeeee;
			font-size: 20upx;
			font-weight: bold;
			color: red;
		}

		.pay-type-content {
			display: flex;
			align-items: center;
			margin-top: 20upx;
			flex-wrap: wrap;

			.pay-type-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				width: 100%;
				border: 1upx solid #eeeeee;
				padding: 20upx;
				background: #FFFFFF;
				border-radius: 5px;
				margin: 0 0 10px 0;
			}
		}

		.buton-content {
			margin: 20upx;
		}
	}
</style>
