<template>
<view class="content">
<view :style='{"width":"100%","padding":"0px 12px 42px 12px","position":"relative","background":"#F6F4F5","height":"100%"}'>
<form :style='{"width":"100%","padding":"12px 12px 92px 12px","margin":"0 0  20px   0 ","background":"#FFFFFF","display":"block","height":"auto"}' class="app-update-pv">
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">商品名称</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.shangpinmingcheng" v-model="ruleForm.shangpinmingcheng" placeholder="商品名称"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">规格</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.guige" v-model="ruleForm.guige" placeholder="规格"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="" @tap="fengmianTap">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">封面</view>
<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' class="avator" v-if="ruleForm.fengmian" :src="baseUrl+ruleForm.fengmian.split(',')[0]" mode="aspectFill"></image>
<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">商家名称</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.shangjiamingcheng" v-model="ruleForm.shangjiamingcheng" placeholder="商家名称"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="select">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">商品分类</view>
<picker :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0","color":"rgb(33, 27, 29)","borderRadius":"5px","flex":"1","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' @change="shangpinfenleiChange" :value="shangpinfenleiIndex"  :range="shangpinfenleiOptions">
	<view :style='{"width":"100%","lineHeight":"44px","fontSize":"14px","color":"#211B1D"}' class="uni-input">{{ruleForm.shangpinfenlei?ruleForm.shangpinfenlei:"请选择商品分类"}}</view>
</picker>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">商品列表</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.shangpinliebiao" v-model="ruleForm.shangpinliebiao" placeholder="商品列表"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">价格</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.price" v-model="ruleForm.price" placeholder="价格"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">单限</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.onelimittimes" v-model="ruleForm.onelimittimes" placeholder="单限"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">库存</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.alllimittimes" v-model="ruleForm.alllimittimes" placeholder="库存"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">积分</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.jf" v-model="ruleForm.jf" placeholder="积分"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">团购价</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.groupprice" v-model="ruleForm.groupprice" placeholder="团购价"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">拼团人数</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.grouppeople" v-model="ruleForm.grouppeople" placeholder="拼团人数"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">当前人数</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.curpeople" v-model="ruleForm.curpeople" placeholder="当前人数"></input>
</view>

<!-- 否 -->
<!-- 是 评价 -->
<view :style='{"padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","borderWidth":"0","width":"100%","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"100%","lineHeight":"40px","fontSize":"14px","color":"#333","fontWeight":"500","display":"none"}' class="title">商品介绍</view>
<xia-editor ref="editor" :style='{"minHeight":"150px","padding":"10px","borderRadius":"5px","color":"#000","background":"#F6F4F5","width":"100%","height":"auto"}' v-model="ruleForm.shangpinjieshao" placeholder="商品介绍" @editorChange="shangpinjieshaoChange"></xia-editor>
</view>

<view :style='{"width":"100%","flexWrap":"wrap","display":"flex","height":"auto"}' class="btn" >
<button :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' @tap="onSubmitTap" class="bg-red">提交</button>
</view>
</form>

<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="clicktimeConfirm" ref="clicktime" themeColor="#333333"></w-picker>
</view>
</view>
</template>

<script>
import wPicker from "@/components/w-picker/w-picker.vue";
import xiaEditor from '@/components/xia-editor/xia-editor';
import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
export default {
data() {
return {
cross:'',
ruleForm: {

shangpinmingcheng: '',

guige: '',

fengmian: '',

shangpinjieshao: '',

shangjiamingcheng: '',

shangpinfenlei: '',

shangpinliebiao: '',
price:0,
storeupNumber:0,
discussNumber:0,
onelimittimes:0,
alllimittimes:0,
jf:0,
groupprice:0,
grouppeople:0,
curpeople:0,
},
shangpinfenleiOptions: [],
shangpinfenleiIndex: 0,
// 登录用户信息
user: {},
ro:{
shangpinmingcheng : false,
guige : false,
fengmian : false,
shangpinjieshao : false,
shangjiamingcheng : false,
clicktime : false,
shangpinfenlei : false,
shangpinliebiao : false,
price : false,
storeupNumber : false,
discussNumber : false,
onelimittimes : false,
alllimittimes : false,
jf : false,
groupprice : false,
grouppeople : false,
curpeople : false,
},
}
},
components: {
wPicker,
xiaEditor,
multipleSelect
},
watch:{
},
computed: {
baseUrl() {
return this.$base.url;
},



},
async onLoad(options) {

let table = uni.getStorageSync("nowTable");
// 获取用户信息
let res = await this.$api.session(table);
this.user = res.data;

// ss读取
this.ruleForm.shangjiamingcheng = this.user.shangjiamingcheng;
this.ro.shangjiamingcheng = true;


// 下拉框
res = await this.$api.option(`shangpinfenlei`,`shangpinfenlei`,{});
this.shangpinfenleiOptions = res.data;
this.shangpinfenleiOptions.unshift("请选择商品分类");

// 如果有登录，获取登录后保存的userid
this.ruleForm.userid = uni.getStorageSync("userid")
if (options.refid) {
// 如果上一级页面传递了refid，获取改refid数据信息
this.ruleForm.refid = options.refid;
this.ruleForm.nickname = uni.getStorageSync("nickname");
}
// 如果是更新操作
if (options.id) {
this.ruleForm.id = options.id;
// 获取信息
res = await this.$api.info(`shangpin`, this.ruleForm.id);
this.ruleForm = res.data;
}
// 跨表
this.cross = options.cross;
if(options.cross){
var obj = uni.getStorageSync('crossObj');
for (var o in obj){
if(o=='shangpinmingcheng'){
this.ruleForm.shangpinmingcheng = obj[o];
this.ro.shangpinmingcheng = true;
continue;
}
if(o=='guige'){
this.ruleForm.guige = obj[o];
this.ro.guige = true;
continue;
}
if(o=='fengmian'){
this.ruleForm.fengmian = obj[o].split(",")[0];
this.ro.fengmian = true;
continue;
}
if(o=='shangpinjieshao'){
this.ruleForm.shangpinjieshao = obj[o];
this.ro.shangpinjieshao = true;
continue;
}
if(o=='shangjiamingcheng'){
this.ruleForm.shangjiamingcheng = obj[o];
this.ro.shangjiamingcheng = true;
continue;
}
if(o=='clicktime'){
this.ruleForm.clicktime = obj[o];
this.ro.clicktime = true;
continue;
}
if(o=='shangpinfenlei'){
this.ruleForm.shangpinfenlei = obj[o];
this.ro.shangpinfenlei = true;
continue;
}
if(o=='shangpinliebiao'){
this.ruleForm.shangpinliebiao = obj[o];
this.ro.shangpinliebiao = true;
continue;
}
if(o=='price'){
this.ruleForm.price = obj[o];
this.ro.price = true;
continue;
}
if(o=='storeupNumber'){
this.ruleForm.storeupNumber = obj[o];
this.ro.storeupNumber = true;
continue;
}
if(o=='discussNumber'){
this.ruleForm.discussNumber = obj[o];
this.ro.discussNumber = true;
continue;
}
if(o=='onelimittimes'){
this.ruleForm.onelimittimes = obj[o];
this.ro.onelimittimes = true;
continue;
}
if(o=='alllimittimes'){
this.ruleForm.alllimittimes = obj[o];
this.ro.alllimittimes = true;
continue;
}
if(o=='jf'){
this.ruleForm.jf = obj[o];
this.ro.jf = true;
continue;
}
if(o=='groupprice'){
this.ruleForm.groupprice = obj[o];
this.ro.groupprice = true;
continue;
}
if(o=='grouppeople'){
this.ruleForm.grouppeople = obj[o];
this.ro.grouppeople = true;
continue;
}
if(o=='curpeople'){
this.ruleForm.curpeople = obj[o];
this.ro.curpeople = true;
continue;
}
}
}
this.styleChange()
this.$forceUpdate()
},
methods: {
shangpinjieshaoChange(e) {
this.ruleForm.shangpinjieshao = e
},
styleChange() {
this.$nextTick(()=>{
document.querySelectorAll('.radioText').forEach(el => {
el.removeAttribute('style')
const icon = {"color":"#666"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.radioTextActive').forEach(el => {
el.removeAttribute('style')
const icon = {"color":"#E9607F"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.checkText').forEach(el => {
el.removeAttribute('style')
const icon = {"color":"#666"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.checkTextActive').forEach(el => {
el.removeAttribute('style')
const icon = {"color":"#E9607F"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.uni-radio-input').forEach(el => {
el.removeAttribute('style')
const icon = {"outlineColor":"#666","background":"#fff"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.uni-radio-input-checked').forEach(el => {
el.removeAttribute('style')
const icon = {"outlineColor":"#E9607F","background":"#E9607F"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.uni-checkbox-input').forEach(el => {
el.removeAttribute('style')
const icon = {"borderColor":"#666","background":"#fff"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
document.querySelectorAll('.uni-checkbox-input-checked').forEach(el => {
el.removeAttribute('style')
const icon = {"color":"#fff","borderColor":"#E9607F","background":"#E9607F"}
Object.keys(icon).forEach((key) => {
el.style[key] = icon[key]
})
})
})
},

// 多级联动参数



// 日长控件选择日期时间
clicktimeConfirm(val) {
this.ruleForm.clicktime = val.result;
this.$forceUpdate();
},

// 下拉变化
shangpinfenleiChange(e) {
this.shangpinfenleiIndex = e.target.value
this.ruleForm.shangpinfenlei = this.shangpinfenleiOptions[this.shangpinfenleiIndex]
},

fengmianTap() {
let _this = this;
this.$api.upload(function(res) {
_this.ruleForm.fengmian = 'file/' + res.file;
_this.$forceUpdate();
_this.$nextTick(()=>{
_this.styleChange()
})
});
},

getUUID () {
return new Date().getTime();
},
async onSubmitTap() {



































//跨表计算判断
var obj;
if((!this.ruleForm.shangpinmingcheng)){
this.$utils.msg(`商品名称不能为空`);
return
}

if((!this.ruleForm.guige)){
this.$utils.msg(`规格不能为空`);
return
}







if(this.ruleForm.price&&(!this.$validate.isNumber(this.ruleForm.price))){
this.$utils.msg(`价格应输入数字`);
return
}

if(this.ruleForm.price<=0){
this.$utils.msg(`价格不能为零`);
return
}

if(this.ruleForm.storeupNumber&&(!this.$validate.isIntNumer(this.ruleForm.storeupNumber))){
this.$utils.msg(`收藏数应输入整数`);
return
}
// // 

if(this.ruleForm.discussNumber&&(!this.$validate.isIntNumer(this.ruleForm.discussNumber))){
this.$utils.msg(`评论数应输入整数`);
return
}
// // 

if(this.ruleForm.onelimittimes&&(!this.$validate.isIntNumer(this.ruleForm.onelimittimes))){
this.$utils.msg(`单限应输入整数`);
return
}
// // if(this.ruleForm.onelimittimes<=0){
// this.$utils.msg(`单限不能为零`);
// return
// }
// 

if(this.ruleForm.alllimittimes&&(!this.$validate.isIntNumer(this.ruleForm.alllimittimes))){
this.$utils.msg(`库存应输入整数`);
return
}
// // if(this.ruleForm.alllimittimes<=0){
// this.$utils.msg(`库存不能为零`);
// return
// }
// 

if(this.ruleForm.jf&&(!this.$validate.isIntNumer(this.ruleForm.jf))){
this.$utils.msg(`积分应输入整数`);
return
}
// // if(this.ruleForm.jf<=0){
// this.$utils.msg(`积分不能为零`);
// return
// }
// 

if(this.ruleForm.groupprice&&(!this.$validate.isNumber(this.ruleForm.groupprice))){
this.$utils.msg(`团购价应输入数字`);
return
}

if(this.ruleForm.groupprice<=0){
this.$utils.msg(`团购价不能为零`);
return
}

if(this.ruleForm.grouppeople&&(!this.$validate.isIntNumer(this.ruleForm.grouppeople))){
this.$utils.msg(`拼团人数应输入整数`);
return
}
// // if(this.ruleForm.grouppeople<=0){
// this.$utils.msg(`拼团人数不能为零`);
// return
// }
// 

if(this.ruleForm.curpeople&&(!this.$validate.isIntNumer(this.ruleForm.curpeople))){
this.$utils.msg(`当前人数应输入整数`);
return
}
// // if(this.ruleForm.curpeople<=0){
// this.$utils.msg(`当前人数不能为零`);
// return
// }
// 

//更新跨表属性
var crossuserid;
var crossrefid;
var crossoptnum;
if(this.cross){
uni.setStorageSync('crossCleanType',true);
var statusColumnName = uni.getStorageSync('statusColumnName');
var statusColumnValue = uni.getStorageSync('statusColumnValue');
if(statusColumnName!='') {
if(!obj) {
obj = uni.getStorageSync('crossObj');
}
if(!statusColumnName.startsWith("[")) {
for (var o in obj){
if(o==statusColumnName){
obj[o] = statusColumnValue;
}

}
var table = uni.getStorageSync('crossTable');
await this.$api.update(`${table}`, obj);
} else {
crossuserid=Number(uni.getStorageSync('userid'));
crossrefid=obj['id'];
crossoptnum=uni.getStorageSync('statusColumnName');
crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
}
}
}
if(crossrefid && crossuserid) {
this.ruleForm.crossuserid=crossuserid;
this.ruleForm.crossrefid=crossrefid;
let params = {
page: 1,
limit:10,
crossuserid:crossuserid,
crossrefid:crossrefid,
}
let res = await this.$api.list(`shangpin`, params);
if (res.data.total >= crossoptnum) {
this.$utils.msg(uni.getStorageSync('tips'));
uni.removeStorageSync('crossCleanType');
return false;
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`shangpin`, this.ruleForm);
}else{
await this.$api.add(`shangpin`, this.ruleForm);
}
this.$utils.msgBack('提交成功');
}
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`shangpin`, this.ruleForm);
}else{
await this.$api.add(`shangpin`, this.ruleForm);
}
this.$utils.msgBack('提交成功');
}
},
optionsChange(e) {
this.index = e.target.value
},
bindDateChange(e) {
this.date = e.target.value
},
getDate(type) {
const date = new Date();
let year = date.getFullYear();
let month = date.getMonth() + 1;
let day = date.getDate();
if (type === 'start') {
year = year - 60;
} else if (type === 'end') {
year = year + 2;
}
month = month > 9 ? month : '0' + month;;
day = day > 9 ? day : '0' + day;
return `${year}-${month}-${day}`;
},
toggleTab(str) {
	if(this.ro[str]){
		return false
	}
this.$refs[str].show();
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
