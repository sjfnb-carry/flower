<template>
	<view v-if="user.id">
		<scroll-view  scroll-x class="bg-white nav">
			<view class='flex text-center'>
				<view class="cu-item flex-sub text-df " @click="navselect(index,nav)" v-for="(nav,index) in navlist" :key="index"
				 :class="[index == currentIndex ? 'text-red cur' : '']">
					{{nav}}
				</view>
			</view>
		</scroll-view>
		<mescroll-uni :top="88" @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
			<view v-for="(item,index) in list" :key="index">
				<view class="bg-white margin-sm padding-sm solids-bottom radius text-content">
					<view class="flex justify-between">
						<view class="text-bold" style="flex-shrink: 0;">
							<text class="text-red">{{ item.status == '未支付'?'未支付':'' }}</text>
							<text class="text-green">{{ item.status == '已支付'?'已支付':'' }}</text>
							<text class="text-green">{{ item.status == '拼团中'?'拼团中':'' }}</text>
							<text class="text-red">{{ item.status == '已发货'?'已发货':'' }}</text>
							<text class="text-blue">{{ item.status == '已完成'?'已完成':'' }}</text>
							<text class="text-grey">{{ item.status == '已取消'?'已取消':'' }}</text>
							<text class="text-grey">{{ item.status == '已退款'?'已退款':'' }}</text>
							<text v-if="item.sfsh=='待审核'&&item.status=='已完成'" class="text-red">退货审核中</text>
							<text v-if="item.sfsh=='待审核'&&item.status=='已支付'" class="text-red">退款审核中</text>
							<text v-if="item.sfsh=='是'&&item.status=='已完成'"  class="text-red">退货成功</text>
						</view>
						<view><text class="icon-goods padding-right-sm"></text><text class="text-bold solids-bottom">{{ item.orderid }}</text></view>
					</view>
					<view class="bg-white margin-xs padding-xs  radius text-content">
						<view class="flex padding-top-sm solids-top">
							<image :src="baseUrl+item.picture" style="flex-shrink: 0;" mode="aspectFill" class=" cover radius solids"></image>
							<view class="text-content padding-left" style="flex-grow: 1;">
								<view>{{ item.goodname }}</view>
								<view v-if="item.type==1 || item.type==3">{{ item.buynumber }} x ￥{{priceChange(item.price)}}</view>
								<view v-if="item.type==2">{{ item.buynumber }} x {{priceChange(item.price)}}积分</view>
								<view class="flex justify-end">收货人：{{item.consignee}}</view>
								<view class="flex justify-end">电话：{{item.tel}}</view>
								<view class="flex justify-end">地址：{{item.address}}</view>
								<view class="flex justify-end">下单时间：{{item.addtime}}</view>
								<view class="flex justify-end">备注：{{item.remark}}</view>
							</view>
						</view>
						<view v-if="item.type==1 || item.type==3" class="flex justify-end">合计{{priceChange(item.total)}}元</view>
						<view v-if="item.type==2" class="flex justify-end">合计{{ item.total }}积分</view>
					</view>
					<view class="flex justify-end"></view>
					<view class="flex justify-end" style="font-size: 24upx;">
						<view v-if="item.status == '未支付'">
							<view @tap="pay(item)" class="round cu-btn lines-grey mid  margin-right-sm">支付</view>
							<view @tap="cancel(item)" class="round cu-btn lines-grey mid  margin-right-sm">取消订单</view>
						</view>
						<view v-if="item.status == '已支付'">
<view   v-if="item.status=='已支付'" @tap="onCancelTap1(item)" class="round cu-btn lines-grey mid  margin-right-sm">退款</view>
						</view>
						<view v-if="item.status == '拼团中'">
							<view @tap="cancelGroup(item)" class="round cu-btn lines-grey mid  margin-right-sm">取消拼团</view>
						</view>
						<view v-if="item.status == '已完成'">
							<view @tap="returnGood(item)" class="round cu-btn lines-grey mid  margin-right-sm">退货</view>
				<view @tap="orderDiscuss(item)" class="round cu-btn lines-grey mid  margin-right-sm">评价</view>
						</view>
						<view v-if="item.status == '已发货'">
							<view @tap="onConfirm(item)" class="round cu-btn lines-grey mid  margin-right-sm">确认收货</view>
						</view>
                        <view v-if="item.status == '已取消'||item.status == '已完成'||item.status == '已退款'">
                            <view @tap="del(item.id)" class="round cu-btn lines-grey mid  margin-right-sm">删除订单</view>
                        </view>
                        <view v-if="item.logistics" @tap="logistics(item)" class="round cu-btn lines-grey mid  margin-right-sm">物流</view>
					</view>
				</view>
			</view>
		</mescroll-uni>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				navlist: [
					'全部', '未支付', '拼团中', '已支付', '已发货' ,'已完成', '已取消', '已退款'
				],
				currentIndex: 0,
				pages: 1,
				list: [],
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					page: {
						// num: 0, // 当前页码,默认0,回调之前会加1,即callback(page)会从1开始
						// size: 5 // 每页数据的数量
					},
					noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
				},
				hasNext: true,
				status: '',
				user: ''
			}
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onShow() {
			let table = uni.getStorageSync("nowTable");
			let res = await this.$api.session(table)
			this.user = res.data
			this.hasNext = true
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		methods: {
			navselect(index, nav) {
				this.currentIndex = index
				if (nav == '全部') {
					this.status = "";
				} else if (nav == '已支付') {
					this.status = "已支付";
				} else {
					this.status = nav
				}
				this.hasNext = true
				// 重新加载数据
				if (this.mescroll) this.mescroll.resetUpScroll()
			},
            priceChange(price) {
                return Number(price).toFixed(2);
            },
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},
			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				// 重置分页参数页数为1
				mescroll.resetUpScroll()
			},
			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				let params = {
					page: mescroll.num,
					limit: mescroll.size,
                    sort: "addtime",
                    order: "desc",
					userid: this.user.id,
				}
				if (this.status) {
					params['status'] = this.status
				}
				let res = await this.$api.list('orders', params);
				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				if (res.data.list.length == 0) this.hasNext = false;
				mescroll.endSuccess(mescroll.size, this.hasNext);
			},
			async onCancelTap1(order) {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '是否确认退款',
					success: async function(res) {
						let table = uni.getStorageSync("nowTable");
						if (res.confirm) {
							order.status = '已退款';
							// 设置订单状态为取消
							let res = await _this.$api.update('orders', order);
							res = await _this.$api.info(order.tablename, order.goodid);
							if(res.code==0){
							res.data.alllimittimes?res.data.alllimittimes = Number(res.data.alllimittimes) + Number(order.buynumber):'';
							res.data.jf?_this.user.jf = Number(_this.user.jf) - Number(order.total):'';
							await _this.$api.update(order.tablename,  res.data);
                          }

							_this.hasNext = true;
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()
							if(order.type==1 || order.type==3) {
								// 退回用户余额
								_this.user.money = Number(_this.user.money) + Number(order.total);
							} else if(order.type==2) {
								// 退回用户积分
								_this.user.jf = Number(_this.user.jf) + Number(order.total);
							}
						_this.$api.update(table, _this.user);
						}
					}
				});
			},
			async onCancelTap(order) {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '确定要申请退款',
					success: async function(res) {
						let table = uni.getStorageSync("nowTable");
						if (res.confirm) {
//是
							console.log('是')
							order.sfsh = '待审核';
							order.role = uni.getStorageSync("nowTable");
							// 设置订单状态为取消
							let res = await _this.$api.update('orders', order);
					     	_this.$utils.msg(`退款申请已提交`);

							_this.hasNext = true;
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()

						}
					}
				});
			},
			orderDiscuss(order) {
				this.$utils.jump(`../discuss`+order.tablename+`/add-or-update?refid=`+order.goodid);
			},
            //物流
            logistics(order) {
                this.$utils.jump(`./logistics?id=`+order.id);
            },
			async returnGood(order) {
				let _this = this;
				uni.showModal({
					title: '是否退货?',
					content: '退货审核通过后,金额将返回账户中',
					success: async function(res) {
						order.sfsh = '待审核';
						order.role = uni.getStorageSync("nowTable");
					await  _this.$api.update('orders', order);
					_this.$utils.msg(`申请已提交`);
						// let table = uni.getStorageSync("nowTable");
// 						if (res.confirm) {
// 							order.status = '已退款';
// 							// 设置订单状态为取消
// 							let res = await _this.$api.update('orders', order);
// // // // // // // // // // // // // // // // // // // // //                             // 加回库存数量
//                             if(order.tablename == `shangpin` && order.type!=3){
//                                 res = await _this.$api.info(order.tablename, order.goodid);
//                                 if(res.code==0){
//                                     res.data.alllimittimes = Number(res.data.alllimittimes) + Number(order.buynumber);
//                                     await _this.$api.update(order.tablename,  res.data);
//                                 }
//                             }
// // // // // // // // // // // // // 							_this.hasNext = true;
// 							// 重新加载数据
// 							if (_this.mescroll) _this.mescroll.resetUpScroll()
// 							if(order.type==1 || order.type==3) {
// 								// 退回用户余额
// 								_this.user.money = Number(_this.user.money) + Number(order.total);
// 							} else if(order.type==2) {
// 								// 退回用户积分
// 								_this.user.jf = Number(_this.user.jf) + Number(order.total);
// 							}
// 							_this.$api.update(table, _this.user);
// 						}
					}
				});
			},
			async cancelGroup(order) {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '是否取消拼团',
					success: async function(res) {
						let table = uni.getStorageSync("nowTable");
						if (res.confirm) {
                            let res = await _this.$api.info(order.tablename, order.goodid);
                            let goodDetail = res.data;
                            goodDetail.curpeople = goodDetail.curpeople-1;
							res = await _this.$api.update(order.tablename, goodDetail);
							order.status = '已退款';
							// 设置订单状态为取消
							res = await _this.$api.update('orders', order);
							_this.hasNext = true;
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()
                            // 退回用户余额
                            _this.user.money = Number(_this.user.money) + Number(order.total);
                            if(goodDetail.jf) {
                                // 减去用户积分
                                _this.user.jf = Number(_this.user.jf) - Number(order.total);
                            }
							_this.$api.update(table, _this.user);
						}
					}
				});
			},
			/**
			 * 取消订单
			 */
			async cancel(order) {
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '是否取消订单',
					success: async function(res) {
						if (res.confirm) {
							order.status = '已取消';
							// 设置订单状态为取消
							let res = await _this.$api.update('orders', order);
                            // 加回库存数量
                            if(order.tablename == `shangpin`){
                                res = await _this.$api.info(order.tablename, order.goodid);
                                if(res.code==0){
                                    res.data.alllimittimes = res.data.alllimittimes + order.buynumber;
                                    await _this.$api.update(order.tablename,  res.data);
                                }
                            }
							_this.hasNext = true;
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()
						}
					}
				});
			},
			/**
			 * 支付订单
			 */
			async pay(order) {
				let _this = this;
				let table = uni.getStorageSync("nowTable");
				uni.showModal({
					title: '提示',
					content: '是否支付订单',
					success: async function(res) {
						if (res.confirm) {
							if(order.type==1){
								if (_this.user.money - order.total < 0) {
									_this.$utils.msg('余额不足，请先充值');
									return;
								}
								_this.$utils.msgBack('支付成功');
								// 减少余额
								_this.user.money = _this.user.money - order.total;
								if(_this.user.jf||_this.user.jf==0){
									_this.user.jf = parseFloat(_this.user.jf) + parseFloat(order.total);
								}
								await _this.$api.update(table, _this.user);
							}else if(order.type==2){
								if (_this.user.jf - order.total < 0) {
									_this.$utils.msg('积分不足，兑换商品失败');
									return;
								}
								_this.$utils.msgBack('兑换成功');
								// 减少积分
								_this.user.jf = _this.user.jf - order.total;
								await _this.$api.update(table, _this.user);
							}
							order.status = '已支付';
							// 设置订单状态为已支付
							let res = await _this.$api.update('orders', order);
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()
						}
					}
				});
			},
			/**
			 * 确认收货
			 */
			async onConfirm(order){
				let _this = this;
				uni.showModal({
					title: '提示',
					content: '是否确认收货',
					success: async function(res) {
						let table = uni.getStorageSync("nowTable");
						if (res.confirm) {
							order.status = '已完成';
							// 设置订单状态为取消
							let res = await _this.$api.update('orders', order);
							_this.hasNext = true;
							// 重新加载数据
							if (_this.mescroll) _this.mescroll.resetUpScroll()
						}
					}
				});
			},
            async del(id) {
                var _this = this;
                uni.showModal({
                    title: '提示',
                    content: '是否确认删除',
                    success: async function(res) {
                        if (res.confirm) {
                            let res = await _this.$api.del('orders', JSON.stringify([id]));
                            if (_this.mescroll) _this.mescroll.resetUpScroll();
                            _this.$utils.msg('删除成功')
                        }
                    }
                });
            }
		}
	}
</script>

<style>
	.cover {
		width: 150upx;
		height: 150upx;
	}
</style>


<style>

</style>
