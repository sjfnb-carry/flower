<template>
<view>
<view class="cu-chat">
<view v-for="(item,index) in list" v-bind:key="index">
<view v-if="item.ask" class="cu-item self">
<view class="main">
<view class="content bg-green shadow">
<text>{{item.ask}}</text>
</view>
</view>
<image class="cu-avatar radius"
   :src="baseUrl+avatarurl">
</image>
<!-- <view class="cu-avatar radius" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big107000.jpg);"></view> -->
<!-- <view class="date">2018年3月23日 13:23</view> -->
</view>
<view v-if="item.reply" class="cu-item">
<image class="cu-avatar radius"src="http://clfile.zggen.cn/20250121/eba896e2a3f44fa0aba83f6e4cc50095.jpg" style="border-radius: 10px;" mode="aspectFill" />
<view class="main">
<view class="content shadow">
<text>{{item.reply}}</text>
</view>
</view>
<!-- <view class="date "> 13:23</view> -->
</view>
</view>
</view>

<view class="cu-bar foot input" :style="{'bottom':tabBarHeight + 'px'}">
<input v-model="ask" class="solid-bottom" :adjust-position="false" :focus="false" maxlength="300" cursor-spacing="10"></input>
<button @tap="onSendTap" class="cu-btn bg-green shadow">发送</button>
</view>

</view>
</template>

<script>
export default {
data() {
return {
// 定时器
inter: {},
list: [],
ask: '',
tabBarHeight: 0,
avatarurl:''
};
},
onLoad() {
let _this = this;
let inter = setInterval(function() {
_this.init();
}, 3000)
this.inter = inter
uni.getSystemInfo({
success: function(info) {
_this.tabBarHeight = info.windowBottom
_this.$forceUpdate()

}
});
this.avatarurl=uni.getStorageSync('headportrait')

},
onUnload() {
if (this.inter) {
// 清除定时器
clearInterval(this.inter);
}
},
computed:{
userid() {
return uni.getStorageSync("userid")
},
baseUrl() {
return this.$base.url;
},
mypic() {
this.$forceUpdate()
return uni.getStorageSync('headportrait')?(this.$base.url + uni.getStorageSync('headportrait')):'https://ossweb-img.qq.com/images/lol/web201310/skin/big107000.jpg'
}
},
methods: {
async onSendTap() {
await this.$api.add('chat', {
ask: this.ask,
userid: this.userid,
isreply: 1
});
this.ask = '';
this.init();
},
async init() {
let res = await this.$api.page('chat', {
sort: 'addtime',
order: 'asc',
limit: 1000,
});
this.list = res.data.list;
}
}
}
</script>

<style>
page {
padding-bottom: 100upx;
}
</style>
