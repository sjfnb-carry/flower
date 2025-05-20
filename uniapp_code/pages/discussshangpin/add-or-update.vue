<template>
<view class="content">
<view :style='{"width":"100%","padding":"0px 12px 42px 12px","position":"relative","background":"#F6F4F5","height":"100%"}'>
<form :style='{"width":"100%","padding":"12px 12px 92px 12px","margin":"0 0  20px   0 ","background":"#FFFFFF","display":"block","height":"auto"}' class="app-update-pv">

<!-- ${location} -->
<!-- 是 评价 -->
<view :style='{"padding":"6px 0 6px 0","margin":"0 0 12px 0","borderColor":"#ccc","borderWidth":"0","width":"100%","borderStyle":"solid","height":"auto"}' class="">
<view :style='{"width":"100%","lineHeight":"40px","fontSize":"14px","color":"#333","fontWeight":"500","display":"none"}' class="title">评论内容</view>
<xia-editor ref="editor" :style='{"minHeight":"150px","padding":"10px","borderRadius":"5px","color":"#000","background":"#F6F4F5","width":"100%","height":"auto"}' v-model="ruleForm.content" placeholder="评论内容" @editorChange="contentChange"></xia-editor>
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

refid: '',

userid: '',

avatarurl: '',

nickname: '',

content: '',

reply: '',
},
// 登录用户信息
user: {},
ro:{
refid : false,
userid : false,
avatarurl : false,
nickname : false,
content : false,
reply : false,
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
res = await this.$api.info(`discussshangpin`, this.ruleForm.id);
this.ruleForm = res.data;
}
// 跨表
this.cross = options.cross;
if(options.cross){
var obj = uni.getStorageSync('crossObj');
for (var o in obj){
if(o=='refid'){
this.ruleForm.refid = obj[o];
this.ro.refid = true;
continue;
}
if(o=='userid'){
this.ruleForm.userid = obj[o];
this.ro.userid = true;
continue;
}
if(o=='avatarurl'){
this.ruleForm.avatarurl = obj[o].split(",")[0];
this.ro.avatarurl = true;
continue;
}
if(o=='nickname'){
this.ruleForm.nickname = obj[o];
this.ro.nickname = true;
continue;
}
if(o=='content'){
this.ruleForm.content = obj[o];
this.ro.content = true;
continue;
}
if(o=='reply'){
this.ruleForm.reply = obj[o];
this.ro.reply = true;
continue;
}
}
}
this.styleChange()
this.$forceUpdate()
},
methods: {
contentChange(e) {
this.ruleForm.content = e
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




avatarurlTap() {
let _this = this;
this.$api.upload(function(res) {
_this.ruleForm.avatarurl = 'file/' + res.file;
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
this.ruleForm.avatarurl = uni.getStorageSync('headportrait')?uni.getStorageSync('headportrait'):'';
var sensitiveWords = "";
var sensitiveWordsArr = [];
if(sensitiveWords) {
sensitiveWordsArr = sensitiveWords.split(",");
}
for(var i=0; i<sensitiveWordsArr.length; i++){
//全局替换
var reg = new RegExp(sensitiveWordsArr[i],"g");
//判断内容中是否包括敏感词
if (this.ruleForm.content.indexOf(sensitiveWordsArr[i]) > -1) {
// 将敏感词替换为 **
this.ruleForm.content = this.ruleForm.content.replace(reg,"**");
}
}













//跨表计算判断
var obj;
if((!this.ruleForm.refid)){
this.$utils.msg(`关联表id不能为空`);
return
}

if((!this.ruleForm.userid)){
this.$utils.msg(`用户id不能为空`);
return
}



if((!this.ruleForm.content)){
this.$utils.msg(`评论内容不能为空`);
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
let res = await this.$api.list(`discussshangpin`, params);
if (res.data.total >= crossoptnum) {
this.$utils.msg(uni.getStorageSync('tips'));
uni.removeStorageSync('crossCleanType');
return false;
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`discussshangpin`, this.ruleForm);
}else{
await this.$api.add(`discussshangpin`, this.ruleForm);
}
uni.setStorageSync('discussshangpinCleanType',true);
this.$utils.msgBack('提交成功');
}
} else {
//跨表计算
if(this.ruleForm.id){
await this.$api.update(`discussshangpin`, this.ruleForm);
}else{
await this.$api.add(`discussshangpin`, this.ruleForm);
}
uni.setStorageSync('discussshangpinCleanType',true);
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
