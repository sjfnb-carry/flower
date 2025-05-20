<template>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
	<view class="content">
		<view :style='{"width":"100%","padding":"0 0 0 0","position":"relative","background":"#EFEFEF","height":"100%"}'>
			<view :style='{"minHeight":"50px","alignItems":"center","background":"#EFEFEF","display":"flex","width":"100%","position":"relative","justifyContent":"space-between"}'>
				<view :style='{"border":"1px solid #E9607F","backgroundColor":"#fff","margin":"0 10px","color":"#333333","alignItems":"center","borderRadius":"5px","flex":"1","display":"flex","lineHeight":"40px","fontSize":"12px","height":"40px"}'>
					<text class="iconfont icon-sousuo1" :style='{"margin":" 8px","background":"none"}'></text>
					<input v-model="searchForm.shangpinfenlei" type="text" placeholder="商品分类" :style='{"width":"100%","borderRadius":"5px","background":"#fff","height":"100%"}'></input>
				</view>
				<button @tap="search" :style='{"padding":"0 15px","margin":"0 10px 0 0","borderRadius":"5px","color":"#fff","textAlign":"center","background":"#F8678C","width":"90px","fontSize":"14px","lineHeight":"40px","height":"40px"}'>搜索</button>
			</view>
			<view :style='{"width":"100%","margin":"0","background":"#EFEFEF","height":"auto"}'>



			<!-- 样式2 -->
			<view class="list" :style='{"width":"100%","padding":"12px 12px 12px 12px","margin":"0 0 0 0","height":"auto"}'>
				<view @tap="onDetailTap(product)" class="listm flex flex-between" :style='{"boxShadow":"0 1px 6px #00000030","padding":"10px","margin":"0 0 10px 0","borderRadius":"10px","flexWrap":"wrap","background":"#FFFFFF","display":"flex","width":"100%","height":"auto"}' v-for="(product,index) in list" :key="index">

				  <view class="listmr" :style='{"padding":"0 0 0 0","margin":"0 10px","flexDirection":"column","display":"flex","width":"calc(100% - 120px)","justifyContent":"center","height":"auto"}'>
				  </view>

					<view :style='{"width":"calc(100% - 100px)","padding":"4px 10px 4px 10px","margin":"0 0 0 30%","justifyContent":"space-between","display":"flex","height":"auto"}'>
						<view :style='{"display":"flex"}' v-if="(userid && isAuth('shangpinfenlei','修改')) || (!userid && isAuthFront('shangpinfenlei','修改'))" @click.stop="onUpdateTap(product.id)">
							<text :style='{"margin":"0 4px 0 0","fontSize":"14px","lineHeight":"1","color":"#F8678C","display":"inline-block"}' class="iconfont icon-xiugai1" ></text>
							<text :style='{"fontSize":"14px","lineHeight":"1","color":"#F8678C","display":"inline-block"}'>修改</text>
						</view>
						<view :style='{"display":"flex"}' v-if="(userid && isAuth('shangpinfenlei','删除')) || (!userid && isAuthFront('shangpinfenlei','删除'))" @click.stop="onDeleteTap(product.id)">
							<text :style='{"margin":"0 4px 0 0","fontSize":"14px","lineHeight":"1","color":"#000000","display":"inline-block"}' class="iconfont icon-shanchu1" ></text>
							<text :style='{"fontSize":"14px","lineHeight":"1","color":"#000000","display":"inline-block"}'>删除</text>
						</view>
					</view>
				</view>
			</view>





			</view>



		</view>
		<button :style='{"border":"0","boxShadow":"0 1px 6px #00000030","color":"#fff","bottom":"20px","right":"4px","outline":"none","borderRadius":"100%","background":"#F8678C","width":"40px","lineHeight":"40px","fontSize":"14px","position":"absolute","height":"40px","zIndex":"999"}' v-if="userid && isAuth('shangpinfenlei','新增')" class="add-btn" @click="onAddTap()">新增</button>
		<button :style='{"border":"0","boxShadow":"0 1px 6px #00000030","color":"#fff","bottom":"20px","right":"4px","outline":"none","borderRadius":"100%","background":"#F8678C","width":"40px","lineHeight":"40px","fontSize":"14px","position":"absolute","height":"40px","zIndex":"999"}' v-if="!userid && isAuthFront('shangpinfenlei','新增')" class="add-btn" @click="onAddTap()">新增</button>
	</view>

</mescroll-uni>
</template>

<script>
	export default {
		data() {
return {
btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				list: [],
				lists: [],
                userid: '',
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
				},
				hasNext: true,
				searchForm:{},
				CustomBar: '0',
				screenBoxShow:false
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onShow() {
			this.btnColor = this.btnColor.sort(()=> {
                                return (0.5-Math.random());
                        });
			this.hasNext = true
// 重新加载数据
if (this.mescroll) this.mescroll.resetUpScroll()

		},
		async onLoad(options) {
            if(options.userid) {
                this.userid=options.userid;
            } else {
                this.userid = "";
            }
			this.hasNext = true
// 重新加载数据
if (this.mescroll) this.mescroll.resetUpScroll()

		},
		methods: {
// true &&(

            priceChange(price) {
                return Number(price).toFixed(2);
            },
            preHttp(str) {
                return str && str.substr(0,4)=='http';
            },
			//查询条件切换
			queryChange(e) {
				this.queryIndex=e.detail.value;
				this.searchForm.shangpinfenlei="";
			},
			//类别搜索
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
limit: 10,
				}


				if(this.searchForm.shangpinfenlei){
					params['shangpinfenlei'] = '%' + this.searchForm.shangpinfenlei + '%'
				}



let user = uni.getStorageSync("userid")?JSON.parse(uni.getStorageSync('userSession')):{};
// 获取用户信息
let table = uni.getStorageSync("nowTable");
let userRes = await this.$api.session(table);
let res = {}
if(this.userid) {
			res = await this.$api.page(`shangpinfenlei`, params);
                } else {
                    res = await this.$api.list(`shangpinfenlei`, params);
                }
				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
this.$forceUpdate()
let length = Math.ceil(this.list.length/6)
let arr = [];
for (let i = 0; i<length; i++){
	arr[i] = this.list.slice(i*6, (i+1)*6)
}
this.lists = arr;
if (res.data.list.length == 0) this.hasNext = false;
				mescroll.endSuccess(mescroll.size, this.hasNext);
			},
			// 详情
			onDetailTap(item) {
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
			},
			// 修改
			onUpdateTap(id){
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./add-or-update?id=${id}`)
			},
			// 添加
			onAddTap(){
                uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./add-or-update`)
			},
			onDeleteTap(id){
				var _this = this;
				uni.showModal({
					title: '提示',
					content: '是否确认删除',
					success: async function(res) {
						if (res.confirm) {
							await _this.$api.del('shangpinfenlei', JSON.stringify([id]));
							_this.hasNext = true
							// 重置分页参数页数为1
							_this.mescroll.resetUpScroll()
						}
					}
				});
			},
			// 搜索
			async search(){
				this.mescroll.num = 1
				let searchForm = {
					page: this.mescroll.num,
					limit: this.mescroll.size
				}


				if(this.searchForm.shangpinfenlei){
					searchForm['shangpinfenlei'] = '%' + this.searchForm.shangpinfenlei + '%'
				}

                let res = {};
                if(this.userid) {
                    res = await this.$api.page(`shangpinfenlei`, searchForm);
                } else {
                    res = await this.$api.list(`shangpinfenlei`, searchForm);
                }
				// 如果是第一页数据置空
				if (this.mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				let length = Math.ceil(this.list.length/6)
				let arr = [];
				for (let i = 0; i<length; i++){
					arr[i] = this.list.slice(i*6, (i+1)*6)
				}
				this.lists = arr
				if (res.data.list.length == 0) this.hasNext = false;
				this.mescroll.endSuccess(this.mescroll.size, this.hasNext);
				this.screenBoxShow = false
			}
		}
	};
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}

	.category-one .tab {
		cursor: pointer;
		border: 1px solid #F8678C;
		border-radius: 5px;
		padding: 0 10px 0 10px;
		margin: 0 10px 0 0 ;
		color: #9C9C9C;
		background: #E3E2E2;
		display: inline-block;
		width: auto;
		font-size: 14px;
		line-height: 40px;
	}

	.category-one .tab.active {
		cursor: pointer;
		border: 1px solid #F8678C;
		border-radius: 5px;
		padding: 0 10px 0 10px;
		margin: 0 10px 0 0 ;
		color: #F8678C;
		background: #EEE3E9;
		display: inline-block;
		width: auto;
		font-size: 14px;
		line-height: 40px;
	}

	.category-two .tab {
		cursor: pointer;
		padding: 0 0 0 0;
		color: #fff;
		background: blue;
		display: inline-block;
		width: 100%;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
	}

	.category-two .tab.active {
		cursor: pointer;
		padding: 0 0 0 0;
		color: #fff;
		background: #000;
		display: inline-block;
		width: 100%;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
	}

	.category-three .tab {
		cursor: pointer;
		padding: 0 0 0 0;
		color: #fff;
		background: burlywood;
		display: inline-block;
		width: 100%;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
	}

	.category-three .tab.active {
		cursor: pointer;
		padding: 0 0 0 0;
		color: #fff;
		background: #000;
		display: inline-block;
		width: 100%;
		font-size: 14px;
		line-height: 40px;
		text-align: center;
	}
	.screenBoxBG {
		position: fixed;
		width: 100%;
		height: 100%;
		z-index: 665;
		top: 0;
		left: 0;
		background: rgba(0, 0, 0, .3);
	}

	.screenBox {
		width: 80%;
		position: fixed;
		height: 100%;
		right: 0;
		top: 0;
		z-index: 666;
		-webkit-transform: translate3d(100%, 0, 0);
		transform: translate3d(100%, 0, 0);
		transition: transform .3s;
		overflow-y: scroll;
		.screenTab{
			border-radius: 20px;
			margin: 5px 0 5px 0;
			background: #fff;
			width: calc(100% / 3 - 12px);
			line-height: 36px;
			text-align: center;
		}
		.screenTabActive{
			border-radius: 20px;
			margin: 5px 0 5px 0;
			background: #138bff;
			width: calc(100% / 3 - 10px);
			line-height: 36px;
			text-align: center;
		}
	}

	.screenBoxActive {
		-webkit-transform: translate3d(0%, 0, 0);
		transform: translate3d(0%, 0, 0);
	}
</style>
