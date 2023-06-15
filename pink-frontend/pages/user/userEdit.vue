<template>
	<view>
		<view class="input">
			<u--input placeholder="请输入用户昵称" border="surround" v-model="nickName" clearable maxlength="20"></u--input>
		</view>
		<view class="save">
			<u-button shape="circle" :custom-style="saveBtn" text="保存" @click="save"
				:disabled="originName == nickName  || nickName == ''? true: false"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				originName: '',
				nickName: '',
				saveBtn: {
					color: "#ffffff",
					backgroundColor: '#FACC31',
				},
			}
		},
		onShow() {
			this.getUserInfo();
		},
		methods: {
			getUserInfo() {
				let openId = uni.getStorageSync('openId');
				this.$http.get("login", {
					openId: openId
				}).then(res => {
					this.nickName = res.data.nickname
					this.originName = res.data.nickname
				})
			},
			save() {
				let openId = uni.getStorageSync('openId');
				this.$http.put('login/updateNickname', {
					openId: openId,
					nickname: this.nickName,
				}).then(res => {
					this.getUserInfo();
					uni.showToast({
						title: '修改成功',
					});
					this.goBack()
				}).catch(error => {
					uni.showToast({
						title: '修改失败',
						icon: 'error'
					});
				})
			},
			goBack() {
				const pages = getCurrentPages();
				if (pages.length === 2) {
					uni.navigateBack({
						delta: 1
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
			}
		}
	}
</script>

<style>
	page {
		background-color: #f5f5f5;
	}
</style>
<style lang="scss" scoped>
	.input {
		background: #ffffff;
	}

	.save {
		margin: 20rpx;
		overflow: hidden;
	}
</style>
