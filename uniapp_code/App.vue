<script>
	let remindTimer = null
	export default {
		onLaunch: function() {
		},
		onShow: function() {
		},
		onHide: function() {
			console.log('App Hide')
		},
}
</script>

<style lang="scss">
	@import "/colorui/main.css";
	@import "/colorui/icon.css";
	@import "/colorui/font/iconfont.css";
	/*每个页面公共css */
	@import "/assets/css/style.scss";
	* {
		box-sizing: border-box;
	}
	
	view, form, scroll-view, swiper, button, input, textarea, label, navigator, image {
	    box-sizing: border-box;
	}
	
	.uni-product-list {
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		flex-direction: row;
		margin-top: 60px;
	}
</style>
