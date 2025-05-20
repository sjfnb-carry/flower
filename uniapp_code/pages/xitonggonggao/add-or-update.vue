<template>
<view class="content">
<view :style='{"width":"100%","padding":"0px 12px 42px 12px","position":"relative","background":"#F6F4F5","height":"100%"}'>
<form :style='{"width":"100%","padding":"12px 12px 92px 12px","margin":"0 0  20px   0 ","background":"#FFFFFF","display":"block","height":"auto"}' class="app-update-pv">
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">标题</view>
<input :style='{"border":"0","padding":"0px 12px 0px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#ffffff00","fontSize":"14px","height":"40px"}' :disabled="ro.biaoti" v-model="ruleForm.biaoti" placeholder="标题"></input>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="" @tap="fengmianTap">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">封面</view>
<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' class="avator" v-if="ruleForm.fengmian" :src="baseUrl+ruleForm.fengmian.split(',')[0]" mode="aspectFill"></image>
<image :style='{"width":"40px","borderRadius":"100%","objectFit":"cover","display":"block","height":"40px"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">简介</view>
<textarea :style='{"border":"0","padding":"12px 12px 12px 12px","margin":"0 0 0 0","color":"#000","borderRadius":"4px","flex":"1","background":"#F6F4F5","fontSize":"14px","height":"120px"}' v-model="ruleForm.jianjie" placeholder="简介"></textarea>
</view>
<view :style='{"border":"0","padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","alignItems":"center","color":"#211B1D","display":"flex","borderRadius":"5px","borderWidth":"0 0 1px 0","background":"#F6F4F5","width":"100%","fontSize":"14px","borderStyle":"solid","height":"auto"}' class=" elect">
<view :style='{"width":"80px","padding":"0 10px 0 0","lineHeight":"40px","fontSize":"14px","color":"#333","textAlign":"right"}' class="title">发布日期</view>
<picker  :disabled="ro.faburiqi" :style='{"border":"0","padding":"0px 12px 0 12px","margin":"0","color":"rgb(33, 27, 29)","borderRadius":"5px","flex":"1","background":"#F6F4F5","width":"100%","fontSize":"14px","height":"44px"}' mode="date" :value="ruleForm.faburiqi" @change="faburiqiChange">
	<view :style='{"width":"100%","lineHeight":"44px","fontSize":"14px","color":"#211B1D"}' class="uni-input">{{ruleForm.faburiqi?ruleForm.faburiqi:"请选择发布日期"}}</view>
</picker>
</view>

<!-- 否 -->
<!-- 否 评价 -->
<view :style='{"padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","borderWidth":"0","width":"100%","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"100%","lineHeight":"40px","fontSize":"14px","color":"#333","fontWeight":"500","display":"none"}' class="title">内容</view>
<xia-editor ref="editor" :style='{"minHeight":"150px","padding":"10px","borderRadius":"5px","color":"#000","background":"#F6F4F5","width":"100%","height":"auto"}' v-model="ruleForm.neirong" placeholder="内容" @editorChange="neirongChange"></xia-editor>
</view>

<view :style='{"width":"100%","flexWrap":"wrap","display":"flex","height":"auto"}' class="btn" >
<button :style='{"border":"0","padding":"0 0 0 0","margin":"10px 0","color":"#fff","borderRadius":"4px","background":"#F8678C","width":"100%","lineHeight":"44px","fontSize":"16px","height":"44px"}' @tap="onSubmitTap" class="bg-red">提交</button>
</view>
</form>

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

biaoti: '',

fengmian: '',

jianjie: '',

neirong: '',

faburiqi: '',
},
// 登录用户信息
user: {},
ro:{
biaoti : false,
fengmian : false,
jianjie : false,
neirong : false,
faburiqi : false,
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
this.ruleForm.faburiqi = this.$utils.getCurDate();

let table = uni.getStorageSync("nowTable");
// 获取用户信息
let res = await this.$api.session(table);
this.user = res.data;

// ss读取



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
res = await this.$api.info(`xitonggonggao`, this.ruleForm.id);
this.ruleForm = res.data;
}
// 跨表
this.cross = options.cross;
if(options.cross){
var obj = uni.getStorageSync('crossObj');
for (var o in obj){
if(o=='biaoti'){
this.ruleForm.biaoti = obj[o];
this.ro.biaoti = true;
continue;
}
if(o=='fengmian'){
this.ruleForm.fengmian = obj[o].split(",")[0];
this.ro.fengmian = true;
continue;
}
if(o=='jianjie'){
this.ruleForm.jianjie = obj[o];
this.ro.jianjie = true;
continue;
}
if(o=='neirong'){
this.ruleForm.neirong = obj[o];
this.ro.neirong = true;
continue;
}
if(o=='faburiqi'){
this.ruleForm.faburiqi = obj[o];
this.ro.faburiqi = true;
continue;
}
}
}
this.styleChange()
this.$forceUpdate()
},
methods: {
jianjieChange(e) {
this.ruleForm.jianjie = e
},
neirongChange(e) {
this.ruleForm.neirong = e
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



faburiqiChange(e) {
this.ruleForm.faburiqi = e.target.value;
this.$forceUpdate();
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
if((!this.ruleForm.biaoti)){
this.$utils.msg(`标题不能为空`);
return
}





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
let res = await this.$api.list(`xitonggonggao`, params);
if (res.data.total >= crossoptnum) {
this.$utils.msg(uni.getStorageSync('tips'));
uni.removeStorageSync('crossCleanType');
return false;
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`xitonggonggao`, this.ruleForm);
}else{
await this.$api.add(`xitonggonggao`, this.ruleForm);
}
this.$utils.msgBack('提交成功');
}
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`xitonggonggao`, this.ruleForm);
}else{
await this.$api.add(`xitonggonggao`, this.ruleForm);
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
