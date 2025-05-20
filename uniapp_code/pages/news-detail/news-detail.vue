<template>
<view>
<view class="content">
<view class="container" :style='{"width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}'>
  <view style="width: 100%;height: 60px;text-align: center;line-height: 40px;font-weight: 600;font-size: 18px;">{{detail.title}}</view>
  <!-- 点赞收藏盒子 -->
  <view style="display: flex;flex-wrap: wrap;position: relative;">
</view>
<view class="detail-list-item" :style='{"margin":"0 0 24rpx 0","borderColor":"#D8D8D8","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"auto","padding":"0 10rpx 0 0","lineHeight":"48rpx","fontSize":"32rpx","color":"#333","textAlign":"right"}'>简介：</view>
<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"32rpx","color":"rgb(0, 0, 0)","flex":"1"}'>{{detail.introduction}}</view>
</view>


<view class="detail-list-item rich" :style='{"margin":"0 0 24rpx 0","borderColor":"#ccc","borderWidth":"0","background":"#fff","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
<view class="lable" :style='{"width":"auto","padding":"0 10rpx 0 0","lineHeight":"48rpx","fontSize":"32rpx","color":"#333","textAlign":"right"}'>内容：</view>
<view class="rich-text" :style='{"padding":"0px 24rpx 24rpx 0","margin":"0","lineHeight":"48rpx","fontSize":"32rpx","flex":"1"}'>
<rich-text :nodes="detail.content"></rich-text>
</view>
</view>

<view class="bottom-content bg-white tabbar border shop" :style='{"width":"100%","padding":"0","flexWrap":"wrap","background":"none","display":"flex","height":"auto"}'>

<button :style='{"border":"0","padding":"0 20rpx","margin":"0","color":"rgb(255, 255, 255)","background":"rgb(255, 170, 51)","width":"auto","fontSize":"32rpx","height":"80rpx"}' v-if="userid&&isAuth('news','私聊')" @tap="chatClick">联系TA</button>
<button :style='{"border":"0","padding":"0 20rpx","margin":"0","color":"rgb(255, 255, 255)","background":"rgb(255, 170, 51)","width":"auto","fontSize":"32rpx","height":"80rpx"}' v-if="!userid&&isAuthFront('news','私聊')" @tap="chatClick">联系TA</button>

</view>
</view>
</view>
</view>
</view>
</template>
<script>
export default {
data() {
return {
detail: {},
id: '',
likeType: false,
likeForm: {},
collectType: false,
collectForm: {},
allList: [],
currentIndex: 0,
}
},
async onLoad(options) {
this.id = options.id;
this.getInfo()

},
onReachBottom() {
this.page++
this.getCommentList()
},
computed:{
baseUrl() {
return this.$base.url;
},
userid(){
return uni.getStorageSync('userid')
}
},
methods: {
async getInfo(){
let res = await this.$api.info('news', this.id)
res.data.content = res.data.content.replace(/<img/g,'<img style="width: 100%;"');
this.detail = res.data;
uni.pageScrollTo({
scrollTop: 0,
duration: 100,
})
this.getLike()
res = await this.$api.list('news',{page:1,limit:100,sort:'addtime',order:'desc'})
for(let x in res.data.list){
if(res.data.list[x].id == this.id){
this.currentIndex = Number(x)
break
}
}
this.allList = res.data.list

},

// 上一篇
prepDetailClick(){
if(this.currentIndex == 0){
this.$message.error('已经是第一篇了')
return false
}
this.currentIndex--
this.id = this.allList[this.currentIndex].id
this.page = 1
this.getInfo()
},
// 下一篇
nextDetailClick(){
if(this.currentIndex == this.allList.length - 1){
this.$message.error('已经是最后一篇了')
return false
}
this.currentIndex++
this.id = this.allList[this.currentIndex].id
this.page = 1
this.getInfo()
},
toDetail(id){
this.id = id
this.getInfo()
},

}
}
</script>

<style lang="scss">
page {
--animate-duration: 1s;
--animate-delay: 1s;
--animate-repeat: 1;
}

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
