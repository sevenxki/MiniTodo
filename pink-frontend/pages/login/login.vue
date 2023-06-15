<template>
	<view class="login">
		<image class="logo" :src="logo"></image>
		<text class="txt1">申请获取以下权限</text>
		<text class="txt2">获取你的公开信息（头像、昵称等）</text>
		<u-button shape="circle" :custom-style="loginBtn" text="微信一键登录/注册" @click="login"></u-button>
		<u-button type="warning" shape="circle" :custom-style="cancleBtn" :plain="true" text="暂不登录" @click="goBack">
		</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loginBtn: {
					color: "#ffffff",
					backgroundColor: '#FACC31',
				},
				cancleBtn: {
					marginTop: '20rpx',
					color: '#FACC31'
				},
				logo: "../../static/logo.png"
			}
		},
		methods: {
			async login() {
				let loginData = await this.getUserInfo();
				let code = await this.getLoginCode();
				uni.showLoading({
					mask: true,
					title: '正在登录'
				});
				this.$http.post('login/save', {
					code: code,
					rawData: loginData.rawData,
					signature: loginData.signature,
					encryptedData: loginData.encryptedData,
					iv: loginData.iv
				}).then(res => {
					uni.setStorageSync("token", res.data.token);
					uni.setStorageSync("openId", res.data.openId);
					uni.hideLoading();
					uni.showToast({
						title: '登录成功',
					});
					this.goBack()
				}).catch(error => {
					console.log(error)
					uni.hideLoading();
					uni.showToast({
						title: '登录失败',
						icon: 'error'
					});
					this.goBack()
				})
			},
			getLoginCode() {
				return new Promise((resolve, reject) => {
					uni.login({
						provider: 'weixin',
						success: (res) => {
							resolve(res.code);
						},
						fail: () => {
							uni.showToast({
								title: '登录失败',
								icon: 'error'
							});
						}
					});
				});
			},
			getUserInfo() {
				return new Promise((resolve, reject) => {
					uni.getUserProfile({
						desc: '获取您的头像、昵称等用户信息',
						success: function(res) {
							uni.setStorageSync("userInfo", JSON.stringify(res.userInfo))
							resolve(res);
						},
						fail: () => {
							uni.showToast({
								title: '登录失败',
								icon: 'error'
							});
						}
					});
				});
			},
			goBack() {
				const pages = getCurrentPages();
				if (pages.length === 2) {
					// let beforePage = pages[pages.length - 2]; // 上一页
					uni.navigateBack({
						delta: 1,
						// success: () => {
						// 	beforePage.onLoad(beforePage.options); // 执行上一页的onLoad方法
						// }
					});
				} else if (pages.length === 1) {
					uni.switchTab({
						url: '/pages/user/user',
					})
				} else {
					uni.navigateBack({
						delta: 1
					});
				}
			},

		}
	}
</script>

<style lang="scss" scoped>
	.login {
		display: flex;
		flex-direction: column;
		padding: 100rpx;

		.logo {
			width: 230rpx;
			height: 230rpx;
			margin: 50rpx auto;
		}

		.txt1 {
			margin-bottom: 10rpx;
		}

		.txt2 {
			color: #999999;
			margin-bottom: 50rpx;
		}

		.txt3 {
			color: #8c8c8c;
			margin-top: 30rpx;
			text-align: center;
			font-size: 28rpx;
		}
	}
</style>
