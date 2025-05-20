<template>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
	<view class="content">
		<view :style='{"width":"100%","padding":"0 0 0 0","position":"relative","background":"#EFEFEF","height":"100%"}'>
			<view :style='{"minHeight":"50px","alignItems":"center","background":"#EFEFEF","display":"flex","width":"100%","position":"relative","justifyContent":"space-between"}'>
				<view :style='{"border":"1px solid #E9607F","backgroundColor":"#fff","margin":"0 10px","color":"#333333","alignItems":"center","borderRadius":"5px","flex":"1","display":"flex","lineHeight":"40px","fontSize":"12px","height":"40px"}'>
					<text class="iconfont icon-sousuo1" :style='{"margin":" 8px","background":"none"}'></text>
					<input v-model="searchForm.title" type="text" placeholder="标题" :style='{"width":"100%","borderRadius":"5px","background":"#fff","height":"100%"}'></input>
				</view>
				<button @tap="search" :style='{"padding":"0 15px","margin":"0 10px 0 0","borderRadius":"5px","color":"#fff","textAlign":"center","background":"#F8678C","width":"90px","fontSize":"14px","lineHeight":"40px","height":"40px"}'>搜索</button>
			</view>





			<!-- 样式6 -->
			<view class="news-box6" :style='{"width":"100%","padding":"12px 12px 12px 12px","margin":"0 0 0 0","height":"auto"}'>
				<view @tap="onDetailTap(product)" v-for="(product,index) in list" :key="index" class="list-item" :style='{"width":"100%","padding":"13px 80px","margin":"0 0 10px 0","position":"relative","background":"url(http://clfile.zggen.cn/20241213/b599b53b5b704fe7a56f985d40ee1a58.png) no-repeat center / 100% 100%","height":"auto"}'>
					<view :style='{"padding":"0 0 0 0","margin":"-18px 0 0 0","borderColor":"#000000","color":"#F8678C","textAlign":"center","borderRadius":"100%","top":"50%","left":"20px","borderWidth":"2px","background":"#F8678C","width":"36px","lineHeight":"34px","fontSize":"14px","position":"absolute","borderStyle":"solid","height":"36px"}' class="num">
						<view style="width: 100%;height: 100%;position: absolute;left: 0;top: 0;" :style="{transform: 'rotate('+(-index*60)+'deg)'}" class="dian">
							<view :style='{"transform":"translateX(-50%)","borderRadius":"100%","top":"-4px","left":"50%","background":"#000000","width":"8px","position":"absolute","height":"8px"}' />
						</view>{{ index + 1 < 10 ? '0'+(index+1) : index+1 }}
					</view>
					<view class="item-list-body" :style='{"width":"100%","margin":"0 0 0 0","height":"auto"}'>
						<view :style='{"width":"100%","padding":"2px 10px 2px 10px","lineHeight":"1.4","fontSize":"14px","color":"#333"}' class="title ">{{product.title}}</view>
						<view :style='{"width":"100%","padding":"2px 10px 2px 10px","lineHeight":"1.2","fontSize":"14px","color":"#666"}' class="desc ">{{product.introduction}}</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</mescroll-uni>
</template>
<script>
	export default {
		data() {
			return {
				list:[],
				searchForm: {},
				mescroll: null, //mescroll实例对象
			}
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onShow() {
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		async onLoad(options) {
            if(options.userid) {
                this.userid=options.userid;
            } else {
                this.userid = "";
            }
			// 重新加载数据
			if (this.mescroll) this.mescroll.resetUpScroll()
		},
		methods:{
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
					limit: mescroll.size
				}
				if(this.searchForm.title){
					params['title'] = '%' + this.searchForm.title + '%'
				}
				let res = {}
				if(this.userid) {
					res = await this.$api.page(`news`, params);
				} else {
					res = await this.$api.list(`news`, params);
				}
				// 如果是第一页数据置空
				if (mescroll.num == 1) this.list = [];
				this.list = this.list.concat(res.data.list);
				let length = Math.ceil(this.list.length/6)
				let arr = [];
				for (let i = 0; i<length; i++){
					arr[i] = this.list.slice(i*6, (i+1)*6)
				}
				this.lists = arr
				if (res.data.list.length == 0) this.hasNext = false;
				mescroll.endSuccess(mescroll.size, this.hasNext);
			},
			onDetailTap(row){
				this.$utils.jump(`../news-detail/news-detail?id=${row.id}`)
			},
			// 搜索
			async search(){
				this.mescroll.num = 1
				let searchForm = {
					page: this.mescroll.num,
					limit: this.mescroll.size
				}
				if(this.searchForm.title){
					searchForm['title'] = '%' + this.searchForm.title + '%'
				}
                let res = {};
                if(this.userid) {
                    res = await this.$api.page(`news`, searchForm);
                } else {
                    res = await this.$api.list(`news`, searchForm);
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
	}
</script>