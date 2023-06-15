<template>
	<view class="content">
		<!-- 纪念日置顶 -->
		<view class="memoryTop-box" v-if="memoryTop.title != null">
			<view class="memoryTop-item">
				<view class="item-box" @click="clickMemory(memoryTop)">
					<view class="text-box">
						<view class="title-box" v-show="memoryTop.typeCode">
							<text>{{memoryTop.title}} 已经</text>
						</view>
						<view class="title-box" v-show="!memoryTop.typeCode">
							<text>{{memoryTop.title}} 还有</text>
						</view>
						<view class="detail-box">
							<text class="detail">起始日：{{memoryTop.time}} </text>
						</view>
					</view>
					<view class="days"><text>{{memoryTop.day}}</text></view>
					<view class="days-tag"><text>days</text></view>
				</view>
			</view>
		</view>

		<!-- 纪念日列表 -->
		<view class="memory-box" v-if="memoryList.length > 0">
			<view class="memory-item" v-for="(item, index) in memoryList" :key="index" @click="clickMemory(item)">
				<view class="text-box">
					<view class="title-box" v-if="item.typeCode=='true'">
						<text>{{item.title}} 已经</text>
					</view>
					<view class="title-box" v-else>
						<text>{{item.title}} 还有</text>
					</view>
				</view>
				<view class="memory-right-box" style="text-align: right;">
					<view :class="'dayCount-box-'+item.type">
						<text>{{item.day}} </text>
					</view>
					<view :class="'day-box-'+item.type">
						<text>天 </text>
					</view>
				</view>
				<!-- <view class="date"><text>{{item.date}}</text></view> -->
			</view>
		</view>
		<view v-else>
			<u-empty mode="data" marginTop="35" text="待办事项为空"></u-empty>
		</view>

		<view style="bottom: 15px;position: absolute;width: 95%;">
			<u-button icon="plus" type="primary" :plain="true" text="添加纪念日" @click="clickAddMemory"></u-button>
		</view>

		<view>
			<u-popup :show="showMemoryDetailPop" mode="center" round="8px" closeable
				@close="showMemoryDetailPop=false;showMemoryDetail=true;showMemoryEdit=false;"
				@open="showMemoryDetailPop=true">
				<view style="width: 608rpx;height: 500rpx;text-align: center;" v-show="showMemoryDetail">
					<!-- <view style="padding: 30px;text-align: center;">
						<u-text text="纪念日详情" size=18></u-text>
					</view> -->
					<view class="memoryDetail-box">
						<view class="memoryTitle-box">
							<u--image :src="aixin" height="22px" width="27px"></u--image>
							<text class="memoryTitle">{{memoryDetail.title}}</text>
							<u--image :src="aixin" height="22px" width="27px"></u--image>
						</view>
						<view class="memoryDay-box">
							<text class="memoryDay-count">{{memoryDetail.day}}</text>
							<text v-if="memoryDetail.typeCode=='false'">天后</text>
							<text v-else>天前</text>
						</view>
						<view>
							<text>{{memoryDetail.time}}</text>
						</view>

						<view class="memoryDescription">
							<text>{{memoryDetail.remark}}</text>
						</view>

						<u-row customStyle="margin-bottom: 10px">
							<u-col span="4"></u-col>
							<u-col span="2">
								<view style="padding: 5px;">
									<u--image :src="qianbi" height="25px" width="27px" @click="clickMemoryEdit">
									</u--image>
									<!-- <u-button icon="close" type="primary" @click="showMemoryDetailPop = false">关闭</u-button> -->
								</view>
							</u-col>
							<u-col span="2">
								<view style="padding: 5px;">
									<u--image :src="shanchu" height="25px" width="27px" @click="deleteMemory">
									</u--image>
									<!-- <u-button icon="edit-pen" type="primary" @click="clickMemoryEdit">编辑</u-button> -->
								</view>
							</u-col>
							<u-col span="4"></u-col>
						</u-row>
					</view>

				</view>
				<view style="width: 608rpx;height: 800rpx;" v-if="showMemoryEdit">
					<view style="padding: 30px;text-align: center;">
						<u-text text="纪念日修改" size=18></u-text>
					</view>
					<view style="padding-left: 25px;padding-right: 25px;">
						<u--form labelPosition="left" :model="memoryEdit">
							<u-form-item label="标题" prop="memoryEdit.title" borderBottom label-width="80">
								<u--input v-model="memoryEdit.title" placeholder="请输入标题" border="none">
								</u--input>
							</u-form-item>

							<u-form-item label="日期" prop="memoryEdit.time" label-width="80" borderBottom>
								<uni-datetime-picker type="date" :clear-icon="false" v-model="memoryEdit.time" />
							</u-form-item>

							<u-form-item label="置顶" prop="memoryEdit.conditionId" label-width="80" borderBottom>
								<u-switch v-model="memoryEdit.conditionId" :activeValue="1" :inactiveValue="0">
								</u-switch>
							</u-form-item>

							<u-form-item label="事项描述" prop="memoryEdit.remark" label-width="80" borderBottom>
								<u--textarea v-model="memoryEdit.remark" placeholder="请输入事项描述" count></u--textarea>
							</u-form-item>
						</u--form>
						<u-row customStyle="margin-bottom: 10px;margin-top:10px">
							<u-col span="2"></u-col>
							<u-col span="4">
								<view style="padding: 5px;">
									<u-button @click="clickMemoryEditClose">取消</u-button>
								</view>
							</u-col>
							<u-col span="4">
								<view style="padding: 5px;">
									<u-button color="#fbdc74" type="primary" @click="memoryEditCommit">修改</u-button>
								</view>
							</u-col>
							<u-col span="2"></u-col>
						</u-row>
					</view>
				</view>
			</u-popup>
		</view>

		<view>
			<!-- 新增纪念日 -->
			<u-popup class="addMemory-popup" :show="addMemoryShow" :round="10" mode="bottom"
				@close="addMemoryShow=false" @open="addMemoryShow=true">
				<view style="height: 370px;padding: 20px;padding-top: 40px;text-align: center;">
					<view>
						<u-text text="新增纪念日" size=18></u-text>
					</view>
					<view style="padding: 30px;">
						<u--form labelPosition="left" :model="memoryAdd">
							<u-form-item label="标题" prop="memoryAdd.memorialDayName" borderBottom label-width="80">
								<u--input v-model="memoryAdd.memorialDayName" placeholder="请输入标题" border="none">
								</u--input>
							</u-form-item>

							<u-form-item label="日期" prop="memoryAdd.time" label-width="80" borderBottom>
								<uni-datetime-picker type="date" :clear-icon="false" v-model="memoryAdd.time" />
							</u-form-item>

							<u-form-item label="置顶" label-width="80" borderBottom>
								<u-switch v-model="memoryAdd.conditionId" :activeValue="1" :inactiveValue="0">
								</u-switch>
							</u-form-item>

							<u-form-item label="事项描述" prop="memoryAdd.remark" label-width="80" borderBottom>
								<u--textarea v-model="memoryAdd.remark" placeholder="请输入事项描述" count></u--textarea>
							</u-form-item>
						</u--form>
					</view>
					<view>
						<u-row customStyle="margin-bottom: 10px">
							<u-col span="2"></u-col>
							<u-col span="4">
								<view style="padding: 5px;">
									<u-button icon="arrow-left" @click="addMemoryCancle">取消</u-button>
								</view>
							</u-col>
							<u-col span="4">
								<view style="padding: 5px;">
									<u-button type="primary" :plain="true" icon="plus" @click="addMemoryCommit">添加
									</u-button>
								</view>
							</u-col>
							<u-col span="2"></u-col>
						</u-row>
					</view>

				</view>
			</u-popup>
		</view>

	</view>


</template>

<script>
	import aixin from '@/static/assets/aixin.png'
	import qianbi from '@/static/assets/qianbi.png'
	import shanchu from '@/static/assets/shanchu.png'
	export default {
		data() {
			return {
				aixin: aixin,
				qianbi: qianbi,
				shanchu: shanchu,
				showMemoryDetail: true,
				showMemoryEdit: false,
				showMemoryDetailPop: false,
				addMemoryShow: false,
				memoryAdd: {
					memorialDayName: '',
					conditionId: 0,
					time: '',
					remark: ''
				},
				memoryDetail: {},
				memoryEdit: {},
				memoryTop: {},
				memoryList: [],
			}
		},
		onShow() {
			this.openId = uni.getStorageSync('openId');

			// 获取列表
			this.getList();
		},
		methods: {
			// 获取列表
			getList() {
				this.$http.get('memorialDayUser/memorialList', {
						"openId": this.openId,
					})
					.then(res => {
						this.memoryList = res.data;

						// 获取置顶
						this.$http.get('memorialDayUser/top', {
								"openId": this.openId,
							})
							.then(res => {
								this.memoryTop = res.data;
							});
					});
			},

			// 点击纪念日详情的编辑按钮
			clickMemoryEdit() {
				this.showMemoryDetail = false;
				this.showMemoryEdit = true;
			},
			// 纪念日详情编辑的取消按钮
			clickMemoryEditClose() {
				this.showMemoryDetail = true;
				this.showMemoryEdit = false;
				this.memoryEdit = {
					...this.memoryDetail
				}
			},
			// 点击单个纪念日查看详情
			clickMemory(item) {
				this.showMemoryDetailPop = true;

				// 获取单个纪念日信息
				this.$http.get('memorialDay/type/' + item.memorialDayId)
					.then(res => {
						this.memoryDetail = res.data;
						this.memoryEdit = {
							...this.memoryDetail
						}
					});
			},
			// 点击添加纪念日
			clickAddMemory() {
				this.addMemoryShow = true;
			},
			// 点击提交添加纪念日按钮
			addMemoryCommit() {
				if (!this.memoryAdd.time) {
					uni.showToast({
						title: '请选择日期',
						icon: 'none'
					});
					return
				}
				if (!this.memoryAdd.memorialDayName) {
					uni.showToast({
						title: '请填写标题',
						icon: 'none'
					});
					return
				}
				// console.log(this.memoryAdd)

				// 添加纪念日
				this.$http.post('memorialDay', {
						"openId": this.openId,
						"memorialDayName": this.memoryAdd.memorialDayName,
						"conditionId": this.memoryAdd.conditionId,
						"time": this.memoryAdd.time,
						"remark": this.memoryAdd.remark,
					})
					.then(res => {
						this.addMemoryShow = false;
						uni.showToast({
							title: '添加成功',
							icon: 'none'
						});
						this.getList()
						// 置空
						this.memoryAdd = {
							memorialDayName: '',
							conditionId: 0,
							time: '',
							remark: ''
						}
					});
			},
			// 点击取消添加纪念日按钮
			addMemoryCancle() {
				this.addMemoryShow = false
				// 置空
				this.memoryAdd = {
					memorialDayName: '',
					conditionId: 0,
					time: '',
					remark: ''
				}
			},
			// 修改纪念日提交
			memoryEditCommit() {
				if (!this.memoryEdit.title) {
					uni.showToast({
						title: '请填写标题',
						icon: 'none'
					});
					return
				}
				// 修改单个纪念日信息
				this.$http.put('memorialDay', {
						"openId": this.openId,
						"memorialDayId": this.memoryEdit.memorialDayId,
						"remark": this.memoryEdit.remark,
						"time": this.memoryEdit.time,
						"memorialDayName": this.memoryEdit.title,
						"conditionId": this.memoryEdit.conditionId,
					})
					.then(res => {
						uni.showToast({
							title: '修改成功',
							icon: 'none'
						});
						this.showMemoryDetail = true;
						this.showMemoryEdit = false;
						this.showMemoryDetailPop = false;
						this.memoryDetail = {
							...this.memoryEdit
						}
						this.getList()
					});
			},
			// 删除纪念日
			deleteMemory() {
				this.$http.delete('memorialDay/delete/' + this.memoryDetail.memorialDayId)
					.then(res => {
						uni.showToast({
							title: '删除成功',
							icon: 'none'
						});
						this.showMemoryDetailPop = false;
						this.getList()
					});
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
	.content {
		text-align: center;
		height: 400upx;
		padding: 10px;
	}

	.memoryTop-box {
		display: flex;
		flex-direction: column;

		.memoryTop-item {
			display: flex;
			flex-direction: column;
			background-color: #ffffed;
			padding: 30rpx;
			padding-left: 33rpx;
			padding-top: 5px;
			box-sizing: border-box;
			border-radius: 15rpx;
			margin-bottom: 25rpx;


			.date-box {
				text-align: left;

				.date {
					flex-grow: 2;
					text-align: left;
					font-size: 35rpx;
					font-weight: 700;
					margin-right: 10rpx;
				}

				.memory-flag {
					float: right;
					font-size: 25rpx;
					color: #fff;
					background-color: #ff557f;
					margin: 5rpx 7rpx;
					padding: 5rpx 14rpx;
					border-radius: 15rpx;
				}
			}

			.item-box {
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: space-between;
				margin-top: 25rpx;

				.text-box {
					flex: 12;
					display: flex;
					flex-direction: column;

					.title-box {
						font-weight: 600;
						font-size: 30rpx;
						color: #303133;
						margin-bottom: 15rpx;
						text-align: left;
					}
				}

				.detail-box {
					display: flex;
					flex-direction: row;

					.detail {
						font-weight: 500;
						font-size: 28rpx;
						color: #999;
					}
				}
			}
		}

		.days {
			flex: 1;
			font-size: 60rpx;
			font-weight: 700;
			margin: 5px;
		}

		.days-tag {
			flex: 1;
			font-size: 22rpx;
			color: #fff;
			background-color: #ff557f;
			margin: 0rpx;
			padding: 5rpx 10rpx;
			border-radius: 13rpx;
		}
	}

	.memory-box {
		display: flex;
		flex-direction: column;

		.memory-item {
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: space-between;
			background-color: white;
			box-sizing: border-box;
			border-radius: 10rpx;
			margin-bottom: 20rpx;

			.text-box {
				display: flex;
				flex-direction: row;

				.title-box {
					font-weight: 400;
					font-size: 30rpx;
					color: #303133;
					margin-bottom: 15rpx;
					text-align: left;
					padding: 20rpx;
				}
			}

			.memory-right-box {
				display: flex;
				flex-direction: row;
				margin: 0px;

				.dayCount-box-target {
					font-size: 35rpx;
					font-weight: 600;
					color: #ffffff;
					background-color: #a8c6fa;
					padding: 20rpx;
					text-align: center;
					width: 35px;
					border-top-left-radius: 6px;
					border-bottom-left-radius: 6px;
				}

				.day-box-target {
					font-size: 30rpx;
					font-weight: 600;
					color: #ffffff;
					background-color: #3c79d8;
					padding: 20rpx;
					text-align: center;
					width: 15px;
					border-top-right-radius: 3px;
					border-bottom-right-radius: 3px;
				}

				.dayCount-box-memory {
					font-size: 35rpx;
					font-weight: 600;
					color: #ffffff;
					background-color: #ffc957;
					padding: 20rpx;
					text-align: center;
					width: 35px;
					border-top-left-radius: 6px;
					border-bottom-left-radius: 6px;
				}

				.day-box-memory {
					font-size: 30rpx;
					font-weight: 600;
					color: #ffffff;
					background-color: #dd9a51;
					padding: 20rpx;
					text-align: center;
					width: 15px;
					border-top-right-radius: 3px;
					border-bottom-right-radius: 3px;
				}
			}

			.date {
				font-size: 26rpx;
				color: #999;
			}
		}


		.color-target {
			// border-right: 3px solid #18b566 !important;
		}

		.color-memory {
			// border-right: 3px solid #FACC31 !important;
		}
	}

	.memoryDetail-box {
		display: block;
	}

	.memoryTitle-box {
		display: flex;
		flex-direction: row;
		justify-content: center;
		padding: 5px;
		// text-align: center;
		margin: 20px;
		margin-top: 30px;
		font-size: 40rpx;
		font-weight: 600;
	}

	.memoryTitle {
		margin-left: 7px;
		margin-right: 7px;
	}

	.memoryDay-box {

		text-align: center;
		margin: 15px;
		margin-top: 0px;

		.memoryDay-count {
			padding: 5px;
			color: #ff557f;
			font-weight: 500;
			font-size: 30rpx;
		}
	}

	.memoryDescription {
		margin: 20px;
		margin-left: 65px;
		margin-right: 65px;
		color: #999;
	}
</style>
