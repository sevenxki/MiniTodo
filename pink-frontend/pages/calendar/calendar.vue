<template>
	<view class="content">
		<zui-calendar :isShrink="true" @click-active="clickActive" @click-task="clickTask" @click-change="clickChange"
			ref="child">
			<template slot="task">
				<view>
					<!-- 纪念日 -->
					<view class="memory-box">
						<view class="memory-item">
							<text class="date-box">
								<text class="date">{{date}}</text>
								<text class="memory-flag" v-if="memoryList.length > 0">{{memoryList.length}}</text>
								<text class="todo-flag" v-if="todoList.length > 0">{{todoList.length}}</text>
							</text>
							<view class="item-box" v-for="(item, index) in memoryList" :key="index">
								<view class="text-box">
									<view class="title-box">
										<text>{{item.memorialDayName}} 已经</text>
									</view>
									<view class="detail-box">
										<text class="detail">起始日：{{item.time}} </text>
									</view>
								</view>
								<view class="days"><text>{{item.day}}</text></view>
								<view class="days-tag"><text>days</text></view>
							</view>
						</view>
					</view>

					<!-- 待办事项 -->
					<view class="task-box" v-if="todoList.length > 0">
						<view class="task-item" :class="'color-'+item.toDoList.listPriority"
							v-for="(item, index) in todoList" :key="index" @click="clickTask(item, index)">
							<view class="text-box">
								<view class="title-box">
									<text>{{item.toDoList.listTitle}}</text>
								</view>
								<view class="tag-box" v-if="item.tag">
									<text class="tag">{{item.tag.tagName}}
									</text>
								</view>
							</view>
							<view class="date"><text>{{item.toDoList.listTime}}</text></view>
						</view>
					</view>
					<view v-else>
						<u-empty mode="data" marginTop="35" text="待办事项为空"></u-empty>
					</view>
				</view>
			</template>
		</zui-calendar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				openId: '',
				// 日历显示的特殊日期
				extraData: [],
				// 某天待办事项列表
				todoList: [],
				// 某天纪念日列表
				memoryList: [],
				// extraData: [{
				// 	date: '2022-11-23',
				// 	value: '待办项',
				// 	dot: true,
				// 	active: false,
				// 	level: 1, // 最高等级
				// 	both: false,
				// }, {
				// 	date: '2022-11-20',
				// 	value: '待办项',
				// 	dot: true,
				// 	active: false,
				// 	level: 2,
				// 	both: false,
				// }, {
				// 	date: '2022-11-25',
				// 	value: '纪念日',
				// 	dot: true,
				// 	active: true,
				// 	level: 0,
				// 	both: false,
				// }, {
				// 	date: '2022-11-26',
				// 	value: '纪&待',
				// 	dot: true,
				// 	active: true,
				// 	level: 2,
				// 	both: true
				// }],
				// upDatakey: '',
				year: 0,
				month: 0,
				day: 0,
				date: '',
				dateList1: [],
				dateList2: [],
			}
		},
		onShow() {
			this.openId = uni.getStorageSync('openId')
			this.init(this.year, this.month, true);
		},
		methods: {
			// 日历初始化
			init(year, month, flag) {
				this.extraData = []
				// 获取日历上的待办日日期列表
				this.$http.get('list/queryCalendarToDo/' + this.openId).then(res => {
					this.dateList1 = res.data;

					// 获取日历上的纪念日日期列表
					this.$http.get('memorialDayUser/dateList', {
						openId: this.openId,
						date: `${year}-${month}-1`,
					}).then(res => {
						this.dateList2 = res.data;

						// 数据转换
						const temp = []
						this.dateList1.forEach((item, index) => {
							let _index = this.dateList2.findIndex(value => value == item.date)
							if (_index > -1) {
								temp.push(item)
								this.dateList1.splice(index, 1)
								this.dateList2.splice(_index, 1)
							}
						})
						// console.log("temp:", temp)
						// console.log("dateList1:", this.dateList1)
						// console.log("dateList2:", this.dateList2)

						this.dateList1.forEach((item) => {
							this.extraData.push({
								date: item.date,
								value: '待办项',
								dot: true,
								active: false,
								level: item.level,
								both: false,
							})
						})
						this.dateList2.forEach((value) => {
							this.extraData.push({
								date: value,
								'value': '纪念日',
								dot: true,
								active: true,
								level: 0,
								both: false,
							})
						})
						temp.forEach((item) => {
							this.extraData.push({
								date: item.date,
								value: '纪&待',
								dot: true,
								active: true,
								level: item.level,
								both: true,
							})
						})
						// console.log("extraData:", this.extraData)
						// this.upDatakey = new Date().getTime()
						// console.log(this.upDatakey)
						this.$refs.child.update(this.extraData)

						// 获取当天事项
						if (flag) {
							this.getDetail()
						}
					})

				});
			},
			// 获取某一天详情
			getDetail() {
				// 获取选中日期的待办事项
				this.$http.get('list/queryNow', {
						openId: this.openId,
						date: this.date
					})
					.then(res => {
						this.todoList = res.data;
						this.todoList.forEach((item) => {
							item.toDoList.listTime = `${this.month}-${this.day}`
						})
					});

				// 获取选中日期的纪念日
				this.$http.get('memorialDayUser/queryMemorialDay', {
						openId: this.openId,
						date: this.date
					})
					.then(res => {
						this.memoryList = res.data;
					});
			},

			// 点击日历
			clickActive({
				year,
				month,
				day,
				date,
				index
			}) {
				this.year = year;
				this.month = month;
				this.day = day;
				this.date = date;
				// console.log(this.year, this.month, this.date, index)

				if (index == -1) {
					return
				}

				this.getDetail()
			},

			// 点击下个月、上个月、上一年、下一年
			clickChange({
				year,
				month
			}) {
				this.init(year, month, false)
			},

			// 点击事项
			clickTask({
				row,
				index
			}) {
				// console.log(row, index)
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
	.content {
		text-align: center;
		height: 400upx;
	}

	.memory-box {
		display: flex;
		flex-direction: column;

		.memory-item {
			display: flex;
			flex-direction: column;
			background-color: white;
			padding: 20rpx;
			padding-left: 33rpx;
			box-sizing: border-box;
			border-radius: 10rpx;
			margin: 20rpx 0;

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

				.todo-flag {
					float: right;
					font-size: 25rpx;
					color: #fff;
					background-color: #2b85e4;
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
						font-size: 30rpx;
						color: #303133;
						margin-bottom: 15rpx;
						text-align: left;
					}

					.detail-box {
						display: flex;
						flex-direction: row;

						.detail {
							font-size: 28rpx;
							color: #999;
						}
					}
				}
			}
		}

		.days {
			flex: 1;
			font-size: 60rpx;
			font-weight: 700;
		}

		.days-tag {
			flex: 1;
			font-size: 22rpx;
			color: #fff;
			background-color: #ff557f;
			margin: 0 5rpx;
			padding: 5rpx 10rpx;
			border-radius: 13rpx;
		}
	}

	.task-box {
		display: flex;
		flex-direction: column;

		.task-item {
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: space-between;
			background-color: white;
			padding: 20rpx;
			box-sizing: border-box;
			border-radius: 10rpx;
			margin-bottom: 20rpx;

			.text-box {
				display: flex;
				flex-direction: column;

				.title-box {
					font-size: 30rpx;
					color: #303133;
					margin-bottom: 15rpx;
					text-align: left;
				}

				.tag-box {
					display: flex;
					flex-direction: row;
					margin-left: -5rpx;

					.tag {
						font-size: 20rpx;
						color: #fff;
						background-color: #2b85e4;
						margin: 0 5rpx;
						padding: 5rpx 10rpx;
						border-radius: 13rpx;
					}
				}
			}

			.date {
				font-size: 26rpx;
				color: #999;
			}
		}
	}

	.color-4 {
		border-left: 6px solid #18b566 !important;
	}

	.color-3 {
		border-left: 6px solid #FACC31 !important;
	}

	.color-2 {
		border-left: 6px solid #ff5500 !important;
	}

	.color-1 {
		border-left: 6px solid #c21114 !important;
	}
</style>
