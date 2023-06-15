<template>
	<view>
		<view class="head">
			<view class="userInfo" @click="hasLogin ? goEdit(): goLogin()">
				<u-avatar :src="userInfo.avatarUrl"></u-avatar>
				<view class="detail">
					<block v-if="hasLogin">
						<text class="sub-txt1">{{ userInfo.nickName }}</text>
						<!-- <text class="sub-txt2">{{ userInfo.telephone }}</text> -->
					</block>
					<block v-else>
						<text class="sub-txt1">点击登录/注册</text>
					</block>
				</view>
				<view class="right-txt" v-if="hasLogin">
					修改
				</view>
				<view class="right-icon">
					<u-icon name="arrow-right" color="#aaaa7f" size="18"></u-icon>
				</view>
			</view>
		</view>
		<view class="block-wrap">
			<u-cell-group>
				<u-cell title="了解待办" :isLink="true" arrow-direction="right">
					<u-icon class="icon" slot="icon" color="#5555ff" size="20" name="bookmark"></u-icon>
				</u-cell>
				<u-cell title="设置">
					<u-icon class="icon" slot="icon" color="#aaaa7f" size="20" name="setting"></u-icon>
				</u-cell>
			</u-cell-group>
		</view>
		<view class="exit" v-if="hasLogin">
			<u-button shape="circle" :custom-style="exitBtn" text="退出登录" @click="goLogout"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: {
					nickName: '',
					avatarUrl: ''
				},
				hasLogin: false,
				exitBtn: {
					color: "#ffffff",
					backgroundColor: '#FACC31',
				},
			};
		},
		onShow() {
			if (uni.getStorageSync('token')) {
				this.getUserInfo();
				this.hasLogin = true;
			} else {
				this.hasLogin = false;
			}
		},
		methods: {
			getUserInfo() {
				let openId = uni.getStorageSync('openId');
				this.userInfo = JSON.parse(uni.getStorageSync('userInfo'))
				this.$http.get("login", {
					openId: openId
				}).then(res => {
					this.userInfo.nickName = res.data.nickname
				})
			},
			goLogin() {
				uni.navigateTo({
					url: '/pages/login/login'
				});
			},
			goEdit() {
				uni.navigateTo({
					url: '/pages/user/userInfo'
				});
			},
			goLogout() {
				uni.showModal({
					title: '提示',
					content: '确认退出登录吗？',
					success: res => {
						if (res.confirm) {
							uni.setStorageSync('token', '')
							uni.setStorageSync('openId', '')
							uni.setStorageSync('userInfo', '')
							this.hasLogin = false;
							this.userInfo = {
								nickName: '',
								avatarUrl: ''
							}
						}
					}
				});
			}
		}
	};
</script>

<style>
	page {
		background-color: #f5f5f5;
	}
</style>
<style lang="scss" scoped>
	.head {
		padding: 20rpx;
		background-color: #fff;
		margin-bottom: 20rpx;

		.userInfo {
			display: flex;
			align-items: center;
			padding: 20rpx;
			padding-right: 5rpx;

			.detail {
				flex: 6;
				display: flex;
				flex-direction: column;
				margin-left: 20rpx;
			}

			.sub-txt1 {
				font-size: 38rpx;
				font-weight: 700;
				color: #000000;
				display: block;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 1;
				overflow: hidden;
			}

			.sub-txt2 {
				font-size: 24rpx;
				color: #6a6a6a;
				display: block;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 1;
				overflow: hidden;
			}

			.right-txt {
				flex: 1;
				font-size: 30rpx;
				color: #aaaa7f;
			}

		}
	}

	.block-wrap {
		background-color: #fff;
		border-radius: 20rpx;
		margin: 20rpx 0;
		overflow: hidden;

		.icon {
			margin-right: 10rpx;
		}
	}

	.exit {
		margin: 20rpx;
		overflow: hidden;
	}
</style>
