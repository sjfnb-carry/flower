
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
<view class="content">
<view class="container" :style='{"padding":"0","flexWrap":"wrap","background":"#EFEFEF","display":"flex","width":"100%","position":"relative","height":"100%"}'>
<swiper :style='{"width":"100%","margin":"10px","height":"190px"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='#00000030' :duration='500' :interval='5000' :vertical='false'>
<swiper-item :style='{"width":"100%","borderRadius":"10px","background":"none","height":"180px"}' v-for="(swiper,index) in swiperList" :key="index" @tap="previewImgTap(swiper)">
<img :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' mode="aspectFill" v-if="swiper.substring(0,4)=='http'" :src="swiper"></img>
<img :style='{"width":"100%","objectFit":"cover","borderRadius":"10px","display":"block","height":"180px"}' mode="aspectFill" v-else :src="baseUrl+swiper"></img>
</swiper-item>
</swiper>
<view :style='{"width":"100%","padding":"12px 12px 12px 12px","flexWrap":"wrap","background":"#EFEFEF","display":"flex","height":"auto"}' class="detail-content">
<view :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","justifyContent":"space-between","height":"auto"}' class="detail-list-item price priceFavor">
<view :style='{"display":"flex"}' class="text">
<view :style='{"color":"red","lineHeight":"40px","fontSize":"24px"}'>￥</view>
<view :style='{"color":"red","lineHeight":"40px","fontSize":"16px"}'>{{detail.price}}</view>
</view>
<view :style='{"lineHeight":"40px","fontSize":"24px","color":"red","display":"block"}' v-if="storeupFlag==1" class="iconfont icon-likefill1" @click="shoucang"></view>
<view :style='{"lineHeight":"40px","fontSize":"24px","color":"red","display":"block"}' v-if="storeupFlag==0" class="iconfont icon-likeline1" @click="shoucang"></view>
</view>
<view v-if="detail.groupprice&&detail.groupprice>0&&detail.grouppeople>1" class="price detail-list-item priceFavor" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","justifyContent":"space-between","height":"auto"}'>
<view :style='{"display":"flex"}' class="text">
<view :style='{"color":"red","lineHeight":"40px","fontSize":"24px"}'>￥</view>
<view :style='{"color":"red","lineHeight":"40px","fontSize":"16px"}'>{{Number(detail.groupprice).toFixed(2)}}拼团价</view>
</view>
</view>
<view v-if="detail.grouppeople>1" class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>成团人数：</view>
<view class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.grouppeople}}人团(仅差{{detail.grouppeople-detail.curpeople}}人成团)</view>
</view>
<view :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","justifyContent":"space-between","height":"auto"}' class="detail-list-item price priceFavor">
<view :style='{"display":"flex"}' class="text">
<view :style='{"color":"red","lineHeight":"40px","fontSize":"16px"}'>{{Number(detail.jf)}}积分</view>
</view>
</view>
<view :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="detail-list-item title">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="lable">商品名称：</view>
<view :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}' class="text">{{detail.shangpinmingcheng}}</view>
</view>

<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>规格：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.guige}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>商家名称：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.shangjiamingcheng}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>商品分类：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.shangpinfenlei}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>商品列表：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.shangpinliebiao}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>收藏数：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.storeupNumber}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>评论数：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.discussNumber}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>库存：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.alllimittimes}}</view>
</view>





<view class="detail-list-item rich" :style='{"margin":"0 0 12px 0","borderColor":"#E9607F","borderWidth":"0 0 1px 0","background":"#EFEFEF","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#000","textAlign":"right"}'>商品介绍</view>
<view class="rich-text" :style='{"padding":"12px 12px 12px 0","margin":"0 0 0 0"}'>
<rich-text :nodes="detail.shangpinjieshao"></rich-text>
</view>
</view>


<view class="time-content" :style='{"width":"100%","margin":"0 0 12px 0","height":"auto","order":"5"}'>
<view class="comoment-header" :style='{"margin":"0 5px 0 0","borderRadius":"5px","background":"none","display":"flex","width":"100%","position":"relative","justifyContent":"space-between","height":"40px"}'>
<view :style='{"border":"1px solid#F8678C","padding":"0 12px 0 12px","color":"#333","borderRadius":"5px","background":"#fff","width":"70%","lineHeight":"40px","fontSize":"14px"}'>评论</view>
<view :style='{"padding":"0 10px 0 10px","margin":"0 0  0 5px","borderRadius":"5px","background":"#F8678C","display":"flex","position":"absolute","right":"0"}' @click="onCommentTap" class="btn-comment-content" style="display: flex;align-items: center;">
<view :style='{"margin":"0 4px 0 0","lineHeight":"40px","fontSize":"14px","color":"#fff"}' class="iconfont icon-xinzeng1"></view>
<view :style='{"color":"#fff","lineHeight":"40px","fontSize":"14px"}'>添加评论</view>
</view>
</view>

<view :style='{"boxShadow":"0 1px 6px #00000030","padding":"12px 12px 12px 12px","margin":"12px 0 0 0","borderColor":"#F8678C","flexWrap":"wrap","background":"#fff","borderWidth":"0 0 2px 0","display":"flex","width":"100%","position":"relative","borderStyle":"solid","height":"auto"}' v-for="(item,index) in commentList" v-bind:key="index" class="cu-item comment-item">
<view :style='{"width":"100%","display":"flex","height":"auto","order":"3"}'>
<image :style='{"width":"30px","margin":"0 4px 0 0","borderRadius":"5px","display":"block","height":"30px"}' v-if="item.avatarurl" mode="aspectFill" :src="baseUrl+item.avatarurl"></image>
<view :style='{"color":"#000000","lineHeight":"30px","fontSize":"14px"}' class="text-grey">{{item.nickname}}</view>
</view>
<view :style='{"width":"100%","margin":"4px 0 4px 0","lineHeight":"1.5","fontSize":"14px","color":"#000000"}' class="text-gray text-content text-df">
<rich-text :nodes="item.content"></rich-text>
</view>
<view :style='{"color":"#8F8F8F","bottom":"10px","lineHeight":"12px","fontSize":"12px","position":"absolute","right":"10px","order":"4"}' class="margin-top-sm text-gray text-df">{{item.addtime}}</view>
<view v-if="item.reply" :style='{"width":"100%","margin":"4px 0 4px 0","lineHeight":"1.5","fontSize":"14px","color":"#000000"}' class="text-gray text-content text-df">
回复:    <rich-text :nodes="item.reply"></rich-text>
</view>
</view>
</view>

<view class="bottom-content bg-white tabbar border shop" :style='{"padding":"5px 12px 5px 12px","flexWrap":"wrap","background":"none","display":"flex","width":"100%","height":"auto","order":"7"}'>




<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="detail.alllimittimes>0" @tap="onCartTap" class="cu-btn bg-orange round shadow-blur" >加入购物车</button>
<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="detail.alllimittimes>0" @tap="onBuyTap">立即购买</button>
<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="!detail.curpeople&&detail.grouppeople>1" @tap="onGroupBuyTap">我要开团</button>
<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="detail.curpeople&&detail.grouppeople>1" @tap="onGroupBuyTap">去参团</button>
<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="detail.alllimittimes>0" @tap="onJifenTap">积分兑换</button>
<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}' v-if="detail.alllimittimes<=0">已售罄</button>
</view>
</view>



</view>
</view>
</mescroll-uni>

</view>

</template>

<script>
const utils = require('../../utils/index.js')
export default {
data() {
return {
username:"",
btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
id: '',
userid: '',
detail: {},
swiperList: [],
commentList: [],
mescroll: null, //mescroll实例对象
downOption: {
auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
},
upOption: {
noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
textNoMore: '~ 没有更多了 ~',
},
hasNext: true,
user: null,
storeupFlag: 0,
freeChapterList: [],
count: 0,
timer: null,

}
},
components: {
},
computed: {
baseUrl() {
return this.$base.url;
},
},
async onLoad(options) {
this.id = options.id;
if(options.userid) {
this.userid = options.userid;
}
// 渲染数据
this.init();
},
onUnload() {
if(this.timer) {
clearInterval(this.timer);
}
},
async onShow(options) {
let table = uni.getStorageSync("nowTable");
// 获取用户信息
let res = await this.$api.session(table);
this.user = res.data;
this.btnColor = this.btnColor.sort(()=> {
return (0.5-Math.random());
});
this.getStoreup();
let cleanType = uni.getStorageSync('discussshangpinCleanType')
if(cleanType){
uni.removeStorageSync('discussshangpinCleanType')
this.mescroll.num = 1
this.upCallback(this.mescroll)
}
let crossCleanType = uni.getStorageSync('crossCleanType')
if(crossCleanType) {
uni.removeStorageSync('crossCleanType')
res = await this.$api.info('shangpin', this.id);
this.detail = res.data;
}
},
destroyed: function() {
//window.clearInterval(this.inter);
},
methods: {
  previewImgTap(currenturl){
    const curUrl=this.$base.url+currenturl;
    const list=this.swiperList.map(v=>{
      return this.$base.url+v
    })
    uni.previewImage({
      urls:list, // 需要预览的图片链接列表
      current: curUrl// 设置当前预览的图片
    });
  },

// 支付
onPayTap(){
let that = this
if(!this.user){
this.$utils.msg("请先登录")
setTimeout(()=>{
that.$utils.jump('../login/login')
},1500)
return false
}
uni.setStorageSync('paytable','shangpin');
uni.setStorageSync('payObject',this.detail);
this.$utils.jump('../pay-confirm/pay-confirm?type=1')
},
onDetailTap(item) {
uni.setStorageSync("useridTag",this.userid);
this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
},
// 收藏
async getStoreup() {
if(!this.user){

return false
}
let params = {
page: 1,
limit: 1,
refid : this.id,
tablename : 'shangpin',
userid: this.user.id,
type: 1,
}
let res = await this.$api.list(`storeup`, params);
this.storeupFlag = res.data.list.length;
},
async shoucang(){
if(!this.user){

return false
}
let _this = this;
let params = {
page: 1,
limit: 1,
refid : _this.detail.id,
tablename : 'shangpin',
userid: _this.user.id,
type: 1,
}
let res = await _this.$api.list(`storeup`, params);
if (res.data.list.length == 1) {
let storeupId = res.data.list[0].id;
uni.showModal({
title: '提示',
content: '是否取消',
success: async function(res) {
if (res.confirm) {
await _this.$api.del('storeup', JSON.stringify([storeupId]));
_this.detail.storeupNumber--
await _this.$api.update('shangpin',_this.detail)
_this.$utils.msg('取消成功');
_this.getStoreup();
}
}
});
return;
}
uni.showModal({
title: '提示',
content: '是否收藏',
success: async function(res) {
if (res.confirm) {
await _this.$api.add('storeup', {
userid: _this.user.id,
name: _this.detail.shangpinmingcheng,
picture: _this.swiperList[0],
refid: _this.detail.id,
tablename: `shangpin`,
type: 1
});
_this.detail.storeupNumber++
await _this.$api.update('shangpin',_this.detail)
_this.$utils.msg('收藏成功');
_this.getStoreup();
}
}
});
},
// 跨表
async onAcrossTap(tableName,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
let that = this
if(!this.user){
this.$utils.msg("请先登录")
setTimeout(()=>{
that.$utils.jump('../login/login')
},1500)
return false
}
uni.setStorageSync('crossTable','shangpin');
uni.setStorageSync(`crossObj`, this.detail);
uni.setStorageSync(`statusColumnName`, statusColumnName);
uni.setStorageSync(`statusColumnValue`, statusColumnValue);
uni.setStorageSync(`tips`, tips);
if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
var obj = uni.getStorageSync('crossObj');
for (var o in obj){
if(o==statusColumnName && obj[o]==statusColumnValue){
this.$utils.msg(tips);
return
}
}
}
this.$utils.jump(`../${tableName}/add-or-update?cross=true`);
},
// 获取详情
async init(){
if(this.timer) {
clearInterval(this.timer);
}
let res = await this.$api.info('shangpin', this.id);
this.detail = res.data;

this.title = this.detail.shangpinmingcheng
// 轮播图片
this.swiperList = this.detail.fengmian ? this.detail.fengmian.split(",") : [];
//修改富文本的图片样式
this.detail.shangpinjieshao = this.detail.shangpinjieshao.replace(/img src/gi,"img style=\"width:100%;\" src");
},
// mescroll组件初始化的回调,可获取到mescroll对象
mescrollInit(mescroll) {
this.mescroll = mescroll;
},
/*下拉刷新的回调 */
downCallback(mescroll) {
this.hasNext = true
mescroll.resetUpScroll()
},
/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
async upCallback(mescroll) {
let res = await this.$api.list('discussshangpin', {
page: mescroll.num,
limit: 10,
refid: this.id
});
let data = res.data.list.map(function (item) {
item.content =  utils.addImgWidthStyle(item.content, '60px');
return item;
})
// 如果是第一页数据置空
if (mescroll.num == 1) this.commentList = [];
this.commentList = this.commentList.concat(data);
if (res.data.list.length == 0) this.hasNext = false;
// 评论数增加
if(this.detail.discussNumber!=res.data.total){
this.detail.discussNumber= res.data.total;
this.$api.update(`shangpin`, this.detail);
}
mescroll.endSuccess(mescroll.size, this.hasNext);

},
async onCartTap() {
if(!this.user){

return false
}
const params = {
userid: this.user.id,
tablename:'shangpin',
goodid: this.detail.id
}
let res = await this.$api.list('cart',params);
if (res.data.list.length>0) {
this.$utils.msg('该商品已添加到购物车')
return
}
await this.$api.add('cart', {
tablename:'shangpin',
goodid: this.detail.id,
goodname: this.detail.shangpinmingcheng,
shangjiamingcheng: this.detail.shangjiamingcheng,
picture: this.swiperList[0],
buynumber: 1,
userid: this.user.id,
vipprice: this.detail.vipprice,
price: this.detail.price,
discountprice: this.detail.vipprice
});
uni.setStorageSync(`cart${this.detail.id}${this.user.id}`, true);
this.$utils.msg('添加到购物车成功')
},
onBuyTap() {
if(!this.user){
return false
}

let datashangpin={
tablename:'shangpin',
goodid: this.detail.id,
goodname: this.detail.shangpinmingcheng,
shangjiamingcheng: this.detail.shangjiamingcheng,
picture: this.swiperList[0],
buynumber: 1,

discountprice: this.detail.vipprice
}
datashangpin['vipprice']=this.detail.vipprice
datashangpin['price']= this.detail.price;
uni.setStorageSync('orderGoods', [datashangpin])
this.$utils.jump('../shop-order-confirm/shop-order-confirm?type=1');
},

async onGroupBuyTap() {
if(!this.user){

return false
}
let params = {
goodid: this.detail.id,
userid: this.user.id,
status: '拼团中',
page: 1,
limit: 1,
}
let res = await this.$api.list('orders', params);
if(res.data.list.length==1) {
this.$utils.msg('您已参团')
return
}
uni.setStorageSync('orderGoods', [{
tablename:'shangpin',
goodid: this.detail.id,
goodname: this.detail.shangpinmingcheng,
shangjiamingcheng: this.detail.shangjiamingcheng,
picture: this.swiperList[0],
buynumber: 1,
price: this.detail.groupprice,
grouppeople: this.detail.grouppeople,
curpeople: this.detail.curpeople,
}])
this.$utils.jump('../shop-order-confirm/shop-order-confirm?type=3');
},

// 积分兑换
onJifenTap(){
uni.setStorageSync('orderGoods', [{
tablename:'shangpin',
goodid: this.detail.id,
goodname: this.detail.shangpinmingcheng,
shangjiamingcheng: this.detail.shangjiamingcheng,
picture: this.swiperList[0],
buynumber: 1,
price: this.detail.jf,
discountprice: this.detail.vipprice
}])
this.$utils.jump('../shop-order-confirm/shop-order-confirm?type=2');
},
onChatTap() {
this.$utils.jump('../chat/chat')
},
// 下载
download(url){
let _this = this;
url=_this.$base.url +  url;
uni.downloadFile({
url: url,
success: (res) => {
if (res.statusCode === 200) {
_this.$utils.msg('下载成功');
//  #ifndef  MP-WEIXIN
window.open(url);
// #endif
//  #ifdef  MP-WEIXIN
  uni.openDocument({
    filePath: res.tempFilePath,
    showMenu: true,
    success: function (res) {
      console.log('打开文档成功')
    }
  })
  console.log('点击查看文件', res.tempFilePath);
// #endif
}
}
});
},
//
onCartTabTap() {
this.$utils.tab('../shop-cart/shop-cart')
},
// 添加评论
async onCommentTap() {
if(!this.user){

return false
}
let params = {
page: 1,
limit: 1,
status: '已完成',
goodid: this.detail.id
}
let res = await this.$api.page('orders', params);
if (res.data.list.length == 0) {
this.$utils.msg('请完成订单后再评论');
return;
}
this.$utils.jump(`../discussshangpin/add-or-update?refid=${this.id}`)
},
onSHTap() {
if(!this.user){

return false
}
this.$refs.popup.open()
},
}
}
</script>

<style lang="scss" scoped>
.content {
min-height: calc(100vh - 44px);
box-sizing: border-box;
}

.seat-list {
display: flex;
align-items: center;
flex-wrap: wrap;
background: #FFFFFF;
margin: 20upx;
border-radius: 20upx;
padding: 20upx;
font-size: 30upx;
border: 1px solid #ccc;
.seat-item {
width: 33.33%;
display: flex;
align-items: center;
flex-direction: column;
margin-bottom: 20upx;

.seat-icon {
width: 50upx;
height: 50upx;
margin-bottom: 10upx;
}
}
}

audio {
display: flex;
flex-direction: column;
}

.audio /deep/ .uni-audio-default {
width: inherit;
}

</style>
