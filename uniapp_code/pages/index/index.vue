<template>
<view class="content">
	<view :style='{"width":"100%","padding":"0 10px","flexWrap":"wrap","background":"#EFEFEF","display":"flex","height":"100%"}'>


	<!-- #ifndef MP-WEIXIN -->
	<swiper :style='{"width":"100%","padding":"10px  0","background":"none","height":"190px"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='#00000030' :duration='500' :interval='5000' :vertical='false'>
		<swiper-item :style='{"width":"100%","borderRadius":"10px","background":"#fff","height":"230px"}' v-for="(swiper,index) in swiperList" :key="index" @tap="onSwiperTap(swiper)">
			<image :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' mode="aspectFill" :src="baseUrl+swiper.img"></image>
			<view v-if="false" :style='{"width":"100%","padding":"0 4px 0 4px","lineHeight":"30px","fontSize":"14px","color":"#333","background":"#fff"}'>{{ swiper.title }}</view>
		</swiper-item>
	</swiper>
	<!-- #endif -->
	<!-- #ifdef MP-WEIXIN -->
		<swiper :style='{"width":"100%","padding":"10px  0","background":"none","height":"190px"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='#00000030' :duration='500' :interval='5000' :vertical='false'>
			<swiper-item :style='{"width":"100%","borderRadius":"10px","background":"#fff","height":"230px"}' v-for="(swiper,index) in swiperList" :key="index" :data-src="swiper" @tap="previewImage">
				<image :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' mode="aspectFill" :src="baseUrl+swiper.img"></image>
				<view v-if="false" :style='{"width":"100%","padding":"0 4px 0 4px","lineHeight":"30px","fontSize":"14px","color":"#333","background":"#fff"}'>{{ swiper.title }}</view>
			</swiper-item>
		</swiper>
	<!-- #endif -->


		<!-- menu -->
<view v-if="true" class="menu" :style='{"padding":"0 0 0 0","margin":"10px 0 10px 0","flexWrap":"wrap","background":"none","display":"flex","width":"100%","height":"auto"}'>
	<block v-for="(item,index1) in menuList" v-bind:key="item.roleName">
		<block v-if="index1==0" v-bind:key="index" v-for=" (menu,index) in item.frontMenu">
			<block v-bind:key="sort" v-for=" (child,sort) in menu.child">
				<block v-bind:key="sort2" v-for=" (button,sort2) in child.buttons">
					<view :style='{"padding":"10px","margin":"10px 10px 10px 10px","borderRadius":"6px","background":"#FFFFFF","display":"flex","width":"calc(33.33% - 20px)","height":"auto"}' class="menu-list" v-if="button=='查看' && child.tableName!='yifahuodingdan' && child.tableName!='yituikuandingdan' &&child.tableName!='yiquxiaodingdan' && child.tableName!='weizhifudingdan' && child.tableName!='yizhifudingdan' && child.tableName!='yiwanchengdingdan' " @tap="onPageTap2(child.classname)">
						<view class="iconarr" :class="menu.fontClass" :style='{"padding":"0 0 0 0","margin":"0 auto 0 auto","color":"#F8678C","borderRadius":"100%","background":"#fff","display":"block","width":"32px","lineHeight":"32px","fontSize":"32px","height":"32px"}'></view>
						<view :style='{"padding":"0 0 0 0","margin":"6px auto 0 auto","color":"#000000","textAlign":"center","width":"100%","lineHeight":"14px","fontSize":"14px"}'>{{child.menu.split("列表")[0]}}</view>
					</view>
				</block>
			</block>
		</block>
	</block>
</view>
		<!-- menu -->
		
		

		<!-- 商品推荐 -->
		<view class="listBox recommend" :style='{"borderRadius":"10px","width":"100%","margin":"0 0 10px 0"}'>
			<view v-if="true && 1 == 1" class="idea recommendIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
			
			<view class="title" :style='{"width":"100%","padding":"0 12px 0 12px","margin":"0 0 0 0","textAlign":"center","background":"url(http://clfile.zggen.cn/20241213/822ba86cc96b4ef88f525fc5385e664e.png) no-repeat center / 100% 100%","height":"60px"}'>
				<view :style='{"color":"#fff","fontSize":"16px","lineHeight":"60px"}'>商品推荐</view>
			</view>
			
			<view v-if="true && 1 == 2" class="idea recommendIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
			
			
			
			  
			<!-- 样式5 -->
			<view class="list-box style5">
				<swiper :style='{"width":"100%","margin":"10px 0","borderRadius":"10px","background":"none","height":"180px"}' class="swiper" :indicator-dots='true' :autoplay='true' :circular='true' indicator-active-color='#e9607f' indicator-color='#e9607f' :duration='500' :interval='5000' :vertical='true'>
					<swiper-item :style='{"width":"100%","borderRadius":"10px","background":"#fff","height":"180px"}' @tap="onDetailTap('shangpin',product.id)" v-for="(product,index) in shangpinlist" :key="index">
						<view v-if="false" :style='{"width":"100%","padding":"0 4px 0 4px","lineHeight":"30px","fontSize":"14px","color":"#333","background":"#fff"}'>商品名称:{{product.shangpinmingcheng}}</view>
						<image :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' class="list-item-image" mode="aspectFill" v-if="product.fengmian.substring(0,4)=='http'" :src="product.fengmian"></image>
						<image :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' class="list-item-image" mode="aspectFill" v-else :src="product.fengmian?baseUrl+product.fengmian.split(',')[0]:''"></image>
					</swiper-item>
				</swiper>
			</view>
			
			
			  
			
			  
			<view v-if="true && 1 == 3" class="idea recommendIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
		</view>
		<!-- 商品推荐 -->
		
		

		<!-- 商品列表 -->
																																<!-- 商品列表 -->
		
		
		<!-- 新闻资讯 -->
																		<view class="listBox news" :style='{"width":"100%","margin":"0 0 10px 0","background":"none","order":"2"}'>
			<view v-if="true && 1 == 1" class="idea newsIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
			
			<view class="title" :style='{"padding":"0 20px","margin":"0","borderColor":"#85c3c2","textAlign":"left","background":"url(http://clfile.zggen.cn/20241213/e7961db92ac84ce0b3bb3faf84601846.png) no-repeat center / 100% 100%","borderWidth":"0","width":"100%","lineHeight":"60px","position":"relative","borderStyle":"solid","height":"60px"}'>
				<view :style='{"color":"#fff","fontSize":"18px","fontWeight":"600"}'>营销与推广</view>
				<text :style='{"color":"#fff","top":"0px","fontSize":"15px","lineHeight":"60px","position":"absolute","right":"20px","zIndex":"99"}' @tap="onPageTap('news')">查看更多</text>
			</view>
			
			<view v-if="true && 1 == 2" class="idea newsIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
			
					  
						
						
		  <!-- 样式4 -->
		  		  
		  <!-- 样式5 -->
		  		  
		  <!-- 样式6 -->
		  		  
		  <!-- 样式7 -->
		  		  
		  <!-- 样式8 -->
		  		  
		  <!-- 样式9 -->
		  		  <view class="news-box6" :style='{"width":"100%","padding":"12px 12px 12px 12px","margin":"0 0 0 0","height":"auto"}'>
			<view @tap="onNewsDetailTap(item.id)" v-for="(item,index) in news" :key="index" class="list-item" :style='{"width":"100%","padding":"13px 80px","margin":"0 0 10px 0","position":"relative","background":"url(http://clfile.zggen.cn/20241213/b599b53b5b704fe7a56f985d40ee1a58.png) no-repeat center / 100% 100%","height":"auto"}'>
			  <view :style='{"padding":"0 0 0 0","margin":"-18px 0 0 0","borderColor":"#000000","color":"#F8678C","textAlign":"center","borderRadius":"100%","top":"50%","left":"20px","borderWidth":"2px","background":"#F8678C","width":"36px","lineHeight":"34px","fontSize":"14px","position":"absolute","borderStyle":"solid","height":"36px"}' class="num">
			    <view style="width: 100%;height: 100%;position: absolute;left: 0;top: 0;" :style="{transform: 'rotate('+(-index*60)+'deg)'}" class="dian">
			      <view :style='{"transform":"translateX(-50%)","borderRadius":"100%","top":"-4px","left":"50%","background":"#000000","width":"8px","position":"absolute","height":"8px"}'></view>
			    </view>{{ index + 1 < 10 ? '0'+(index+1) : index+1 }}
			  </view>
			  <view class="item-list-body" :style='{"width":"100%","margin":"0 0 0 0","height":"auto"}'>
				<view :style='{"width":"100%","padding":"2px 10px 2px 10px","lineHeight":"1.4","fontSize":"14px","color":"#333"}' class="title ">{{item.title}}</view>
				<view :style='{"width":"100%","padding":"2px 10px 2px 10px","lineHeight":"1.2","fontSize":"14px","color":"#666","display":"none"}' class="desc ">{{item.introduction}}</view>
			  </view>
			</view>
		  </view>
		  			
			<view v-if="true && 1 == 3" class="idea newsIdea" :style='{"padding":"20px 20px 20px 20px","flexWrap":"wrap","background":"#efefef","justifyContent":"space-between","display":"none"}'>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box1"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box2"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box3"></view>
				<view :style='{"width":"20%","background":"#fff","height":"80px"}' class="box box4"></view>
			</view>
		</view>
																																																		<!-- 新闻资讯 -->
				



	</view>
</view>
</template>

<script>
    import menu from '@/utils/menu'
	import '@/assets/css/global-restaurant.css'
	import uniIcons from "@/components/uni-ui/lib/uni-icons/uni-icons.vue"
	export default {
		components: {
			uniIcons
		},
		data() {
			return {
				options2: {
					effect: 'flip',
					loop : true
				},
				options3: {
					effect: 'cube',
					loop : true,
					cubeEffect: {
						shadow: true,
						slideShadows: true,
						shadowOffset: 20,
						shadowScale: 0.94,
					}
				},
				rows: 2,
				column: 4,
                role : '',
                menuList: [],
                swiperMenuList:[],
                user: {},
                tableName:'',

				//轮播
				swiperList: [],
				shangpinlist: [],
				news: [],
			}
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
        async onLoad(){
        },
		async onShow() {
			this.swiperMenuList = []
			let table = uni.getStorageSync("nowTable");
			let res = await this.$api.session(table);
			this.user = res.data;
			this.tableName = table;
			let menus = menu.list();
			this.menuList = menus;
			let role = '管理员'
			this.menuList.forEach((item,key) => {
			    if(role==item.roleName) {
			        item.frontMenu.forEach((item2,key2) => {
						if(item2.child.length) {
							item2.childs = []
						    item2.child.forEach((item3,key3)=>{
								if(item3.buttons.indexOf("查看")>-1){
									item2.childs.push(item3)
									this.swiperMenuList.push(item3);
								}
							})
						}
			            
			        })
			    }
			})
            // let res;
			// 轮播图
			let swiperList = []
			res = await this.$api.list('config', {
				page: 1,
				limit: 3
			});
			for (let item of res.data.list) {
				if (item.value && item.value!="" && item.value!=null ) {
					swiperList.push({
						img: item.value,
                        title: item.name,
						...item
					});
				}
			}
			if (swiperList) {
				this.swiperList = swiperList;
			}
			// 营销与推广
			res = await this.$api.list('news', {
				page: 1,
				limit: 6
			});
			this.news = res.data.list

			// 推荐信息
			res = await this.$api.recommend('shangpin', {
                page: 1,
                limit: 4
            });
			this.shangpinlist = res.data.list

		},

		methods: {
onPageTapSearch(tableName){

uni.navigateTo({
	url: `../${tableName}/list`,
	fail: function(){
		uni.switchTab({
			url: `../${tableName}/list`
		});
	}
});
// this.$utils.jump(`../${tableName}/list`)
			},

			//轮播图跳转
//  #ifndef MP-WEIXIN
onSwiperTap(e) {
window.open(e.url)
},
// #endif
// #ifdef MP-WEIXIN
previewImage(e){
const curUrl= [this.$base.url+ e.currentTarget.dataset.src.img]
console.log('curUrl',curUrl);
uni.previewImage({
urls:curUrl,
current:curUrl
})
},
// #endif
			// 新闻详情
			onNewsDetailTap(id) {
				this.$utils.jump(`../news-detail/news-detail?id=${id}`)
			},
			// 推荐列表点击详情
			onDetailTap(tableName, id) {
				this.$utils.jump(`../${tableName}/detail?id=${id}`)
			},
onPageTap(tableName){
if(tableName=='news'){
uni.navigateTo({
url: `../news-list/news-list`,
fail: function(){
	uni.switchTab({
		url: `../news-list/news-list`
	});
}
});
return false
}

// tableName=='popup_remind'?tableName='popupRemind':'';
uni.navigateTo({
url: `../${tableName}/list`,
fail: function(){
uni.switchTab({
	url: `../${tableName}/list`
});
}
});
// this.$utils.jump(`../${tableName}/list`)
},
onPageTap2(index) {
		let url = '../' + index + '/list'
		if(index=='forum'){
			url = '../forum-index/forum-index'
		}
		if(index=='news'){
			url = '../news-list/news-list'
		}
      if(index=='chat'){
        url = '../chat/chat'
      }
		uni.setStorageSync("useridTag",0);
		uni.navigateTo({
			url: url,
			fail: function() {
				uni.switchTab({
					url: url
				});
			}
		});
	}


		}
	}
</script>

<style lang="scss" scoped>
.content {
	min-height: calc(100vh - 44px);
	box-sizing: border-box;
}
.search_picker_icon{
display: none;
}
</style>
