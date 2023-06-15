<template>
	<view>
		<view class="avatar">
			<u-avatar :src="userInfo.avatarUrl" shape="circle" size=120></u-avatar>
		</view>
		<view class="detail">
			<u-cell-group>
				<u-cell title="昵称" :value="userInfo.nickName" :isLink="true" arrow-direction="right" :url="link">
				</u-cell>
			</u-cell-group>
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
				link: '/pages/user/userEdit'
			}
		},
		onShow() {
			this.getUserInfo();
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
		}
	}
</script>

<style>
	page {
		background-color: #f5f5f5;
	}
</style>
<style lang="scss" scoped>
	.avatar {
		margin: 70rpx auto;

		/deep/ .u-avatar {
			margin: 0rpx auto;
		}
	}


	.detail {
		background-color: #fff;
		border-radius: 20rpx;
		margin: 20rpx 0;
		overflow: hidden;
	}
</style>
