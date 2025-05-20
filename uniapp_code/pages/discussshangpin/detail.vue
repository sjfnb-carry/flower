
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
<view class="content">
<view class="container" :style='{"padding":"0","flexWrap":"wrap","background":"#EFEFEF","display":"flex","width":"100%","position":"relative","height":"100%"}'>
<view :style='{"width":"100%","padding":"12px 12px 12px 12px","flexWrap":"wrap","background":"#EFEFEF","display":"flex","height":"auto"}' class="detail-content">

<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>关联表id：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.refid}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#ccc","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' v-if="detail.avatarurl" :src="baseUrl+detail.avatarurl"></image>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>用户名：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.nickname}}</view>
</view>



<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>评论内容</view>
<view class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.content}}</view>
</view>
<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>回复内容</view>
<view class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.reply}}</view>
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
let cleanType = uni.getStorageSync('discussdiscussshangpinCleanType')
if(cleanType){
uni.removeStorageSync('discussdiscussshangpinCleanType')
this.mescroll.num = 1
this.upCallback(this.mescroll)
}
let crossCleanType = uni.getStorageSync('crossCleanType')
if(crossCleanType) {
uni.removeStorageSync('crossCleanType')
res = await this.$api.info('discussshangpin', this.id);
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
uni.setStorageSync('paytable','discussshangpin');
uni.setStorageSync('payObject',this.detail);
this.$utils.jump('../pay-confirm/pay-confirm?type=1')
},
onDetailTap(item) {
uni.setStorageSync("useridTag",this.userid);
this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
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
uni.setStorageSync('crossTable','discussshangpin');
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
let res = await this.$api.info('discussshangpin', this.id);
this.detail = res.data;

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
let res = await this.$api.list('discussdiscussshangpin', {
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
this.$api.update(`discussshangpin`, this.detail);
}
mescroll.endSuccess(mescroll.size, this.hasNext);

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
this.$utils.jump(`../discussdiscussshangpin/add-or-update?refid=${this.id}`)
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
