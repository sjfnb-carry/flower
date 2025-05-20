<template>
	<div class="home_view">
		<div class="projectTitle">欢迎使用 {{projectName}}</div>
		<div class="count_list">
			<el-collapse-transition v-if="btnAuth('shangpinfenlei','首页总数')">
				<el-card v-show="countTypeList.closeshangpinfenleiCountType" class="card_view count-item">
					<template #header>
						<div class="index_card_head">
							<div class="card_head_title">
								商品分类
							</div>
							<div class="card_head_right">
								<el-icon @click="countTypeClick('hiddenshangpinfenleiCountType')" class="showIcons"
									:class="countTypeList.hiddenshangpinfenleiCountType?'showIcons1':''">
									<ArrowUpBold />
								</el-icon>
								<el-icon @click="countTypeClick('closeshangpinfenleiCountType')" class="closeIcons">
									<CloseBold />
								</el-icon>
							</div>
						</div>
					</template>
					<el-collapse-transition>
						<div class="count_item" v-show="countTypeList.hiddenshangpinfenleiCountType">
							<div class="count_title">商品分类总数</div>
							<div class="count_num">{{shangpinfenleiCount}}</div>
						</div>
					</el-collapse-transition>
				</el-card>
			</el-collapse-transition>
			<el-collapse-transition v-if="btnAuth('orders','首页总数')">
				<el-card v-show="countTypeList.closeordersCountType" class="card_view count-item">
					<template #header>
						<div class="index_card_head">
							<div class="card_head_title">
								商品订单
							</div>
							<div class="card_head_right">
								<el-icon @click="countTypeClick('hiddenordersCountType')" class="showIcons"
									:class="countTypeList.hiddenordersCountType?'showIcons1':''">
									<ArrowUpBold />
								</el-icon>
								<el-icon @click="countTypeClick('closeordersCountType')" class="closeIcons">
									<CloseBold />
								</el-icon>
							</div>
						</div>
					</template>
					<el-collapse-transition>
						<div class="count_item" v-show="countTypeList.hiddenordersCountType">
							<div class="count_title">商品订单总数</div>
							<div class="count_num">{{ordersCount}}</div>
						</div>
					</el-collapse-transition>
				</el-card>
			</el-collapse-transition>
		</div>
		<div class="card_list">
			<el-collapse-transition v-if="btnAuth('shangpinfenlei','首页统计')">
				<el-card v-show="cardTypeList.closeshangpinfenleiChartType1" class="card_view chart-item">
					<template #header>
						<div class="index_card_head">
							<div class="card_head_title">
								商品分类
							</div>
							<div class="card_head_right">
								<el-icon @click="cardTypeClick('hiddenshangpinfenleiChartType1')" class="showIcons"
										 :class="cardTypeList.hiddenshangpinfenleiChartType1?'showIcons1':''">
									<ArrowUpBold />
								</el-icon>
								<el-icon @click="cardTypeClick('closeshangpinfenleiChartType1')" class="closeIcons">
									<CloseBold />
								</el-icon>
							</div>
						</div>
					</template>
					<el-collapse-transition>
						<div class="card_item" v-show="cardTypeList.hiddenshangpinfenleiChartType1">
							<div id="shangpinfenleishangpinfenleiEchart1" class="Echart" style="width: 100%;height: 400px;"></div>
						</div>
					</el-collapse-transition>
				</el-card>
			</el-collapse-transition>
			<el-collapse-transition v-if="btnAuth('orders','首页统计')">
				<el-card v-show="cardTypeList.closeordersChartType1" class="card_view chart-item">
					<template #header>
						<div class="index_card_head">
							<div class="card_head_title">
								商品订单
							</div>
							<div class="card_head_right">
								<el-icon @click="cardTypeClick('hiddenordersChartType1')" class="showIcons"
										 :class="cardTypeList.hiddenordersChartType1?'showIcons1':''">
									<ArrowUpBold />
								</el-icon>
								<el-icon @click="cardTypeClick('closeordersChartType1')" class="closeIcons">
									<CloseBold />
								</el-icon>
							</div>
						</div>
					</template>
					<el-collapse-transition>
						<div class="card_item" v-show="cardTypeList.hiddenordersChartType1">
							<div id="orderstotalEchart1" class="Echart" style="width: 100%;height: 400px;"></div>
						</div>
					</el-collapse-transition>
				</el-card>
			</el-collapse-transition>
		</div>
	</div>
</template>

<script setup>
	import {
		inject,
		nextTick,
		ref,
		getCurrentInstance
	} from 'vue';
	const context = getCurrentInstance()?.appContext.config.globalProperties;
	const projectName = context.$project.projectName
	const countTypeList = ref({
	})
	const getCountList=()=>{
		countTypeList.value.closeshangpinfenleiCountType = true
		countTypeList.value.hiddenshangpinfenleiCountType = true
		if(btnAuth('shangpinfenlei','首页总数')){
			getshangpinfenleiCount()
		}
		countTypeList.value.closeordersCountType = true
		countTypeList.value.hiddenordersCountType = true
		if(btnAuth('orders','首页总数')){
			getordersCount()
		}
	}
	const shangpinfenleiCount = ref(0)
	const getshangpinfenleiCount = () => {
		context?.$http({
			url:'shangpinfenlei/count',
			method: 'get'
		}).then(res=>{
			shangpinfenleiCount.value = res.data.data
		})
	}
	const ordersCount = ref(0)
	const getordersCount = () => {
		context?.$http({
			url:'orders/count',
			method: 'get'
		}).then(res=>{
			ordersCount.value = res.data.data
		})
	}
	const countTypeClick = (e) => {
		countTypeList.value[e] = !countTypeList.value[e]
	}
	//权限验证
	const btnAuth = (e,a)=>{
		return context?.$toolUtil.isAuth(e,a)
	}
	let echarts = inject("echarts")
	const cardTypeClick = (e) =>{
		cardTypeList.value[e] = !cardTypeList.value[e]
		setTimeout(()=>{
			getCardList()
		},1000)
	}
	const cardTypeList = ref({
		closeshangpinfenleiChartType1: true,
		hiddenshangpinfenleiChartType1: true,
		closeordersChartType1: true,
		hiddenordersChartType1: true,
	})
	const getCardList = () => {
		if(btnAuth('shangpinfenlei','首页统计')){
			getshangpinfenleiChart1()
		}
		if(btnAuth('orders','首页统计')){
			getordersChart1()
		}
	}
	import '@/assets/js/echarts-theme'
	const getshangpinfenleiChart1 = () => {
		nextTick(()=>{
			var shangpinfenleiEchart1 = echarts.init(document.getElementById("shangpinfenleishangpinfenleiEchart1"),'theme');
			context?.$http({
				url: "shangpinfenlei/group/shangpinfenlei",
				method: "get",
			}).then(obj=>{
				let res = obj.data.data
				let xAxis = [];
				let yAxis = [];
				let dataList = []
				for(let i=0;i<res.length;i++){
				    xAxis.push(res[i].shangpinfenlei);
				    yAxis.push(parseFloat((res[i].total)));
					dataList.push({
				        value: parseFloat((res[i].total)),
				        name: res[i].shangpinfenlei
				    })
				}
				var option = {};
				option = {
    title:{
        text: '商品分类',
        left: 'center'
    },
    legend: {
        orient: 'vertical',
        left: 'left'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b} : {c} ({d}%)'
    },
    series: [
        {
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: dataList,
            emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
}
				shangpinfenleiEchart1.clear()
				// 使用刚指定的配置项和数据显示图表。
				shangpinfenleiEchart1.setOption(option);
				//根据窗口的大小变动图表
				shangpinfenleiEchart1.resize();
			})
		})
	}
	const getordersChart1 = () => {
		nextTick(()=>{
			var totalEchart1 = echarts.init(document.getElementById("orderstotalEchart1"),'theme');
			context?.$http({
				url: `orders/value/goodname/total?order=desc`,
				method: "get",
			}).then(obj=>{
				let res = obj.data.data
				let xAxis = [];
				let yAxis = [];
				let dataList = []
				for(let i=0;i<res.length;i++){
				    xAxis.push(res[i].goodname);
				    yAxis.push(parseFloat((res[i].total)));
					dataList.push({
				        value: parseFloat((res[i].total)),
				        name: res[i].goodname
				    })
				}
				var option = {};
				option = {
    title: {
        text: '销售数据',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b} : {c}'
    },
    xAxis: {
        data: xAxis,
        type: 'category',
    },
    yAxis: {
        type: 'value'
    },
    series:{
        data: yAxis,
        type: 'line'
    }
}
                Object.assign(option.series,{smooth: true})
				totalEchart1.clear()
				// 使用刚指定的配置项和数据显示图表。
				totalEchart1.setOption(option);
				//根据窗口的大小变动图表
				totalEchart1.resize();
			})
		})
	}

	const init=()=>{
		getCountList()
		getCardList()
	}
	init()
</script>
<style lang="scss" scoped>
	// 首页盒子
	.home_view {
	}
	.home_view .projectTitle{
	}

	.showIcons {
		transition: transform 0.3s;
		margin-right: 10px;
	}

	.showIcons1 {
		transform: rotate(-180deg);
	}
	
	// 总数盒子
	.count_list{
		// 总数card
		.card_view {
			// card头部
			.el-card__header {
				// 头部盒子
				.index_card_head {
					// 标题
					.card_head_title {
					}
					// 按钮盒子
					.card_head_right {
						// 按钮
						.el-icon {
						}
					}
				}
			}
			// body
			.el-card__body {
				// body盒子
				.count_item{
					// 总数标题
					.count_title{
					}
					// 总数数字
					.count_num{
					}
				}
			}
		}
	}

	// 统计图盒子
	.card_list {
		// 统计图card
		.card_view {
			// 头部
			.el-card__header {
				// 头部盒子
				.index_card_head {
					// 标题
					.card_head_title {
					}
					// 按钮盒子
					.card_head_right {
						// 按钮
						.el-icon{
						}
					}
				}
			}
			// body
			.el-card__body {
				// body盒子
				.card_item{
				}
			}
		}
	}
</style>
<style>
/*总盒子*/
.home_view{
    padding: 0px 20px;
    margin: 0px;
    height: auto;
    min-height: 100vh;
    background: #EAEAEA;
    width: 100%;
}
.home_view .projectTitle{
    width: 100%;
    font-size: 28px;
    font-weight: bold;
    padding: 40px 0px 20px;
    height: auto;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0px 0px 20px;
    color: #000;
}

/*总数*/
/*总盒子*/
.home_view .count_list {
    display:flex;
    flex-wrap:wrap;
    justify-content:center;
    padding:0px 0px 20px;
    width:100%;
    align-items:flex-start;
}
/*卡片盒子*/
.home_view .count_list .card_view {
    width:23%;
    height:auto;
    margin:0px 1% 20px;
    box-sizing:border-box;
    border:0px;
    border-radius:0px;
    box-shadow: 0 4px 0px 0 #2C2C2C;
    flex: 1;
    border-radius: 55px;
}

/*head 总盒子*/
.home_view .count_list .card_view .el-card__header{
     width: 100%;
    border: 0px solid rgb(238, 238, 238);
    display: none;
}
/*item*/
.home_view .count_list .card_view .el-card__header .index_card_head{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 10px;
}
/*标题*/
.home_view .count_list .card_view .el-card__header .index_card_head .card_head_title{
    font-size: 14px;
    color: rgb(51, 51, 51);
}
/*按钮盒子*/
.home_view .count_list .card_view .el-card__header .index_card_head .card_head_right{
    display: flex;
    align-items: center;
}
/*按钮*/
.home_view .count_list .card_view .el-card__header .index_card_head .card_head_right .showIcons{
    color: rgb(102, 102, 102);
    font-size: 20px;
    cursor: pointer;
}
/*body 总盒子*/
.home_view .count_list .card_view .el-card__body{
    padding: 0px;
    background: none;
}
/*item*/
.home_view .count_list .card_view .el-card__body .count_item{
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    text-align: center;
}
/*标题*/
.home_view .count_list .card_view .el-card__body .count_item .count_title{
    font-size: 17px;
    color: #969696;
    line-height: 2;
    display: inline-block;
    margin: 0px 10px 0px 0px;
    font-weight: 400;
    order: 2;
}
/*数字*/
.home_view .count_list .card_view .el-card__body .count_item .count_num{
    font-size: 26px;
    color: #000;
    line-height: 2;
    display: inline-block;
    font-weight: 600;
}

/*图表*/
/*总盒子*/
.home_view .card_list{
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    padding: 0px 0px 20px;
    align-items: flex-start;
}
/*卡片 总盒子*/
.home_view .card_list .card_view{
    width: 48%;
    height: auto;
    margin: 0px 1% 30px;
    box-sizing: border-box;
    border: 0px;
    border-radius: 0px;
    padding: 20px 0px 0px;
}
/*head 总盒子*/
.home_view .card_list .card_view .el-card__header{
    width: 100%;
    border: 0px solid rgb(238, 238, 238);
    background: rgb(255, 255, 255);
    display: none;
}
/*item*/
.home_view .card_list .card_view .el-card__header .index_card_head{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 10px;
}
/*标题*/
.home_view .card_list .card_view .el-card__header .index_card_head .card_head_title{
}
/*按钮盒子*/
.home_view .card_list .card_view .el-card__header .index_card_head .card_head_right{
    display: none;
}
/*按钮*/
.home_view .card_list .card_view .el-card__header .index_card_head .card_head_right .showIcons{
    color: rgb(238, 238, 238);
    font-size: 20px;
    cursor: pointer;
}

/*body 总盒子*/
.home_view .card_list .card_view .el-card__body{
    padding: 0px;
    background: rgb(255, 255, 255);
}
.home_view .card_list .card_view .el-card__body .card_item{
    padding: 10px;
    text-align: center;
}

.home-calendar{
  border: 0px solid rgb(238, 238, 238);
  box-shadow: rgba(0, 0, 0, 0.1) 0px 0px 0px;
  margin: 0px auto 30px;
  border-radius: 0px;
  color: rgb(102, 102, 102);
  width: calc(100% - 310px);
  font-size: inherit;
  height: auto;
  background: #fff;
}
.home-calendar .header{
  padding: 17px 40px;
  border-color: rgba(126, 96, 16, 0.1);
  flex-wrap: wrap;
  background: var(--theme);
  border-width: 0px 0px 1px;
  display: flex;
  width: 100%;
  border-style: solid;
  justify-content: space-between;
  height: auto;
}
.home-calendar .header .btn{
  cursor: pointer;
  border: 0px solid rgb(153, 153, 153);
  padding: 0px 0px 0px 12px;
  align-items: center;
  color: rgb(102, 102, 102);
  border-radius: 4px;
  display: flex;
  width: auto;
  font-size: inherit;
  justify-content: center;
}
.home-calendar .header .title{
  padding: 0px 10px;
  font-size: 24px;
  align-items: center;
  justify-content: center;
  display: flex;
  color: rgb(51, 51, 51);
}
.home-calendar table{
  width: 100%;
  padding: 0px 0px 20px;
  height: auto;
}
.home-calendar tbody,thead{
  width: 100%;
}
.home-calendar tr{
  width: 100%;
  align-items: center;
  justify-content: center;
  display: flex;
}
.home-calendar th{
  align-items: center;
  flex: 1;
  display: flex;
  line-height: 50px;
  justify-content: center;
}
.home-calendar td{
  cursor: pointer;
  padding: 6px 12px 12px;
  flex: 1;
  display: flex;
  justify-content: center;
  text-align: center;
}
</style>
