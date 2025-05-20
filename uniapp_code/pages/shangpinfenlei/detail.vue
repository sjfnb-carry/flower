
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback">
<view class="content">
<view class="container" :style='{"padding":"0","flexWrap":"wrap","background":"#EFEFEF","display":"flex","width":"100%","position":"relative","height":"100%"}'>
<view :style='{"width":"100%","padding":"12px 12px 12px 12px","flexWrap":"wrap","background":"#EFEFEF","display":"flex","height":"auto"}' class="detail-content">

<view class="detail-list-item" :style='{"margin":"0 0 12px 0","borderColor":"#F8678C","borderWidth":"0 0 1px 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}'>商品分类：</view>
<view  class="text" :style='{"padding":"0 0 0 0","margin":"0 0 0 0","lineHeight":"40px","fontSize":"14px","color":"#000"}'>{{detail.shangpinfenlei}}</view>
</view>








<view class="bottom-content bg-white tabbar border shop" :style='{"padding":"5px 12px 5px 12px","flexWrap":"wrap","background":"none","display":"flex","width":"100%","height":"auto","order":"7"}'>




<button :style='{"border":"0","padding":"0 10px","margin":"5px","color":"#fff","display":"flex","justifyContent":"center","outline":"none","borderRadius":"25px","background":"#000000","width":"auto","fontSize":"14px","lineHeight":"40px","height":"40px"}'  @tap="onCartTap" class="cu-btn bg-orange round shadow-blur" >加入购物车</button>
</view>
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
let crossCleanType = uni.getStorageSync('crossCleanType')
if(crossCleanType) {
uni.removeStorageSync('crossCleanType')
res = await this.$api.info('shangpinfenlei', this.id);
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
uni.setStorageSync('paytable','shangpinfenlei');
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
uni.setStorageSync('crossTable','shangpinfenlei');
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
let res = await this.$api.info('shangpinfenlei', this.id);
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
mescroll.endSuccess(mescroll.size, this.hasNext);

},
async onCartTap() {
if(!this.user){

return false
}
const params = {
userid: this.user.id,
tablename:'shangpinfenlei',
goodid: this.detail.id
}
let res = await this.$api.list('cart',params);
if (res.data.list.length>0) {
this.$utils.msg('该商品已添加到购物车')
return
}
await this.$api.add('cart', {
tablename:'shangpinfenlei',
goodid: this.detail.id,
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
this.$utils.jump(`../discussshangpinfenlei/add-or-update?refid=${this.id}`)
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
