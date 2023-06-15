<template>
	<view style="text-align: center">
		<view>
			<!-- <u-notify message="Hi uView" :show="true"></u-notify> -->
			<u-tabbar :value="value1" @change="changeTab()" :fixed="false" :placeholder="false"
				:safeAreaInsetBottom="false">
				<u-tabbar-item text="未完成" icon="order"></u-tabbar-item>
				<u-tabbar-item text="已完成" icon="checkbox-mark"></u-tabbar-item>
			</u-tabbar>

			<!-- 未完成页面 -->
			<view v-show="unfinishedListView" style="padding: 10px;padding-top: 0px;margin: 15px;">
				<u-row customStyle="margin-bottom: 10px">
					<u-col span="2">
						<view style="height: 25px;width: 25px;" v-show="rili1Show" @click="openTodoCal">
							<u--image :src="rili1" height="25px" width="25px"></u--image>
						</view>
						<!-- <view style="height: 25px;width: 25px;" v-show="rili2Show" @click="open">
							<u--image :src="rili2" height="25px" width="25px"></u--image>
						</view> -->
					</u-col>
					<u-col span="10">
						<u-search v-model="searchData.searchTodoTitle" placeholder="请输入标题搜索" :showAction="true"
							actionText="搜索" :animation="true" :clearabled="true" @search="getTodoByTitle()"
							@custom="getTodoByTitle()"></u-search>
					</u-col>
				</u-row>
				<!-- 日历 -->
				<view class="calendar-content">
					<uni-calendar ref="todoCalendar" :lunar="true" :start-date="'2000-1-1'" :end-date="'2030-12-31'"
						:clearDate="true" :range="true" :insert="false" @confirm="confirmTodoCal" />
				</view>
				<!-- 标签导航 -->
				<view style="padding: 5px;">
					<u-tabs :list="listTypes1" lineColor="#f1d593" :current="currentTodo" @click="clickTodoTabs">
					</u-tabs>
				</view>
				<!-- 未完成列表内容 -->
				<view style="height: 500px;">
					<!-- 新增按钮 -->
					<view>
						<drag-button v-show="dragNew" :isDock="true" :existTabBar="true" @btnClick="addUnfnsButton" />
					</view>
					<scroll-view class="scrollView" scroll-y="true" style="height: 500px;">
						<u-list v-show="unfinished">
							<view>
								<delSlideLeft v-for="(item, index) in todoList.data" :item="item"
									:data_transit="{index:index,item:item}" @delItem="delItem">
									<u-cell :key="item.listId" :title="item.listTitle" :value="item.listTime"
										@click="clickUnfinished(item.listId)" clickable>
										<u-icon slot="icon" size="32" name="search" color="#f4de6d"></u-icon>
									</u-cell>

								</delSlideLeft>
								<!-- <u-modal :show="delTodoShow" title="删除确认" content="是否确认删除?" :showCancelButton="true"
								@confirm="confirmDelTodo" @cancel="delTodoShow=false"
								></u-modal> -->
							</view>

						</u-list>
					</scroll-view>
				</view>

				<!-- 待办事项详情 -->
				<u-popup :show="showUfnPop" mode="center" round="8px" @close="close" @open="showUfnPop=true">
					<view style="width: 608rpx;height: 900rpx;" v-show="todoDetail">
						<view style="padding: 30px;text-align: center;">
							<u-text text="待办事项详情" size=18></u-text>
						</view>
						<view>
							<u-row customStyle="padding-top: 10px;margin-bottom: 27px;">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="标题：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<text>{{todoListDetail.listTitle}}</text>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 25px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="日期：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<text>{{todoListDetail.listTime}}</text>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 25px;">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<!-- <u-text text="标签:" size=15></u-text> -->
										<u-text text="类别:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="6">
									<!-- <u-scroll-list :indicator="true" indicatorActiveColor="#ffe146">
										<view style="display: inline-flex;">
											<u-tag text="工作" borderColor="#ff82ba" bgColor="#ff82ba"
												style="width: 50px;" :show="close1"></u-tag>
											<u-tag text="学习" borderColor="#ffbe25" bgColor="#ffbe25"
												style="width: 50px;" :show="close2"></u-tag>
											<u-tag text="运1" borderColor="#aa00ff" bgColor="#aa00ff"
												style="width: 50px;" :show="close3"></u-tag>
											<u-tag text="运2" borderColor="#ffe146" bgColor="#ffe146"
												style="width: 50px;" :show="close3"></u-tag>
										</view>
									</u-scroll-list> -->
									<view>
										<text>{{todoListDetail.tag.tagName}}</text>
										<!-- <u-text :text="this.todoListDetail.listType	" size=15></u-text> -->
									</view>
								</u-col>
								<u-col span="1">
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 30px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="优先级:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<text>{{todoListDetail.listPriorityName}}</text>
										<!-- <u-text :text="this.todoListDetail.listPriority	" size=15></u-text> -->
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 23px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="描述:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--textarea v-model="todoListDetail.listContent" disabled count>
										</u--textarea>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2"></u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button icon="edit-pen" @click="todoClickEdit">编辑</u-button>
									</view>
								</u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button color="#fbdc74" type="primary" icon="checkbox-mark"
											@click="toFinish(todoListDetail.listId)">已完成</u-button>
									</view>
								</u-col>
								<u-col span="2"></u-col>
							</u-row>
						</view>

					</view>
					<!-- 未完成修改 -->
					<view style="width: 608rpx;height: 900rpx;" v-show="todoEdit">
						<view style="padding: 30px;text-align: center;">
							<u-text text="待办事项修改" size=18></u-text>
						</view>
						<view>

							<u-row customStyle="margin-bottom: 10px;">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="标题：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--input placeholder="请输入事项标题" border="surround" v-model="editData.listTitle">
										</u--input>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="日期：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<uni-datetime-picker type="date" :clear-icon="false"
											v-model="editData.listTime" />
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<!-- <u-text text="标签:" size=15></u-text> -->
										<u-text text="类别:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="6">
									<view style="width: 150px;">
										<!-- <u-scroll-list :indicator="true" indicatorActiveColor="#ffe146">
											<view style="display: inline-flex;">
												<u-tag text="工作" borderColor="#ff82ba" bgColor="#ff82ba"
													style="width: 50px;" :show="close1" closable @close="close1=false">
												</u-tag>
												<u-tag text="学习" borderColor="#ffbe25" bgColor="#ffbe25"
													style="width: 50px;" :show="close2" closable @close="close2=false">
												</u-tag>
											</view>
										</u-scroll-list> -->
										<view class="typeView" style="width: 150px;">
											<combox-search labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
												:candidates="candidates1" placeholder="请选择类别"
												@getValue="getValue($event,'todoTag')" :value="editData.tag.tagName">
											</combox-search>
										</view>
									</view>
								</u-col>
								<u-col span="1">
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="优先级:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<view class="priorityView" style="width: 150px;">
											<combox-search labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
												:candidates="candidates" placeholder="请选择优先级"
												@getValue="getValue($event,'todoPriority')"
												:value="editData.listPriorityName"></combox-search>
										</view>
									</view>

								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 14px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="描述:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--textarea v-model="editData.listContent" placeholder="请输入事项描述" count>
										</u--textarea>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2"></u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button icon="close" @click="todoClickEditClose()">取消</u-button>
									</view>
								</u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button color="#fbdc74" type="primary" icon="checkbox-mark"
											@click="submitTodoEdit">修改</u-button>
									</view>
								</u-col>
								<u-col span="2"></u-col>
							</u-row>
						</view>
					</view>
				</u-popup>

				<!-- 新增待办事项 -->
				<u-popup :show="showAddUfnPop" mode="center" round="8px" :closeOnClickOverlay="false" closeable
					@close="showAddUfnPop=false;dragNew=true" @open="showAddUfnPop=true">
					<view style="width: 608rpx;height: 860rpx;">
						<view style="padding: 30px;padding-bottom: 0px">
							<u-text text="新增待办事项" size=18></u-text>
						</view>
						<view style="padding: 30px;">
							<u--form labelPosition="left" :model="addNew" ref="form1">
								<u-form-item label="待办事项" prop="addNew.listTitle" borderBottom ref="item1"
									label-width="80">
									<u--input v-model="addNew.listTitle" placeholder="请输入待办事项标题" border="none">
									</u--input>
								</u-form-item>

								<u-form-item label="完成日期" prop="addNew.deadline" label-width="80" borderBottom>
									<uni-datetime-picker type="date" :clear-icon="false" v-model="addNew.listTime" />
									<!-- <u-calendar v-show="pickerTime" :mode="single" @confirm="confirm"></u-calendar> -->
								</u-form-item>

								<!-- <u-form-item label="事项标签" prop="addNew.tag" label-width="80" borderBottom> -->
								<view class="priorityView">
									<combox-search label="事项类别" labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
										:candidates="candidates1" placeholder="请选择事项类别"
										@getValue="getValue($event,'addTag')" :value="addNew.tag.tagName">
									</combox-search>
								</view>
								<!-- <u-scroll-list :indicator="true" indicatorActiveColor="#ffe146">
										<view style="display: inline-flex;width: 100px;">
											<u-tag text="工作" borderColor="#ff82ba" bgColor="#ff82ba"
												style="width: 61px;" closable :show="close1" @close="close1 = false">
											</u-tag>
											<u-tag text="学习" borderColor="#ffbe25" bgColor="#ffbe25"
												style="width: 50px;" closable :show="close2" @close="close2 = false">
											</u-tag>
											<u-tag text="运1" borderColor="#aa00ff" bgColor="#aa00ff"
												style="width: 50px;" closable :show="close3" @close="close3 = false">
											</u-tag>
											<u-tag text="运2" borderColor="#ffe146" bgColor="#ffe146"
												style="width: 50px;" closable :show="close3" @close="close3 = false">
											</u-tag>
										</view>
									</u-scroll-list> -->
								<!-- </u-form-item> -->
								<view class="priorityView">
									<combox-search label="优先级别" labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
										:candidates="candidates" placeholder="请选择优先级别"
										@getValue="getValue($event,'addPriority')" :value="addNew.listPriorityName">
									</combox-search>
								</view>
								<u-form-item label="事项描述" prop="itemInfo.description" label-width="80" borderBottom>
									<u--textarea v-model="addNew.listContent" placeholder="请输入事项描述" count></u--textarea>
								</u-form-item>
							</u--form>
							<u-picker mode="time" v-model="pickerTime" @confirm="timeConfirm"></u-picker>
							<u-row customStyle="margin: 10px;">
								<u-col span="2"></u-col>
								<u-col span="4">
									<view style="padding: 5px;text-align: center;">
										<u-button @click="resetForm();this.dragNew=true">返回</u-button>
									</view>
								</u-col>
								<u-col span="5">
									<view style="padding: 5px;text-align: center;">
										<u-button icon="checkmark" type="primary" color="#f4b56d" @click="submitAdd">提交
										</u-button>
									</view>
								</u-col>
								<u-col span="1"></u-col>
							</u-row>
						</view>
					</view>
				</u-popup>
			</view>

			<!-- 已完成页面 -->
			<view v-show="finishedListView" style="padding: 10px;padding-top: 0px;margin: 15px;">
				<u-row customStyle="margin-bottom: 10px">
					<u-col span="2">
						<view style="height: 25px;width: 25px;" v-show="rili1Show" @click="openDoneCal">
							<u--image :src="rili1" height="25px" width="25px"></u--image>
						</view>
						<!-- <view style="height: 25px;width: 25px;" v-show="rili2Show" @click="open">
							<u--image :src="rili2" height="25px" width="25px"></u--image>
						</view> -->
					</u-col>
					<u-col span="10">
						<u-search v-model="searchData.searchDoneTitle" placeholder="请输入标题搜索" :showAction="true"
							actionText="搜索" :animation="true" :clearabled="true" @search="getDoneByTitle()"
							@custom="getDoneByTitle()"></u-search>
					</u-col>
				</u-row>
				<!-- 日历 -->
				<view class="calendar-content">
					<uni-calendar ref="doneCalendar" :lunar="true" :start-date="'2000-1-1'" :end-date="'2030-12-31'"
						:clearDate="true" :range="true" :insert="false" @confirm="confirmDoneCal" />
				</view>
				<!-- 标签导航 -->
				<view style="padding: 5px;">
					<u-tabs :list="listTypes2" lineColor="#f1d593" :current="currentDone" @click="clickDoneTabs">
					</u-tabs>
				</view>
				<view style="height: 500px;">
					<scroll-view class="scrollView" scroll-y="true" style="height: 500px;">
						<u-list v-show="finished">
							<view>
								<delSlideLeft v-for="(item, index) in doneList.data" :item="item"
									:data_transit="{index:index,item:item}" @delItem="delItem">
									<u-cell :key="item.listId" :title="item.listTitle" :value="item.listTime"
										@click="clickFinished(item.listId)" clickable>
										<u-icon slot="icon" size="30" name="checkmark-circle" color="#eeee81"></u-icon>
									</u-cell>
								</delSlideLeft>
							</view>
						</u-list>
					</scroll-view>
				</view>

				<!-- 已完成详情页 -->
				<u-popup :show="showFnsdPop" mode="center" round="8px" @close="close"
					@open="showFnsdPop=true">
					<!-- 已完成详情 -->
					<view style="width: 608rpx;height: 900rpx;" v-show="doneDetail">
						<view style="padding: 30px;text-align: center;">
							<u-text text="已办事项详情" size=18></u-text>
						</view>
						<view>
							<u-row customStyle="padding-top: 10px;margin-bottom: 27px;">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="标题：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<u-text :text="doneListDetail.listTitle" size=15></u-text>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 25px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="日期：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<u-text :text="doneListDetail.listTime" size=15></u-text>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 25px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<!-- <u-text text="标签:" size=15></u-text> -->
										<u-text text="类别:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="6">
									<view>
										<u-text :text="doneListDetail.tag.tagName" size=15></u-text>
									</view>
									<!-- <u-scroll-list :indicator="true" indicatorActiveColor="#ffe146">
										<view style="display: inline-flex;">
											<u-tag text="工作" borderColor="#ff82ba" bgColor="#ff82ba"
												style="width: 50px;" :show="close1"></u-tag>
											<u-tag text="学习" borderColor="#ffbe25" bgColor="#ffbe25"
												style="width: 50px;" :show="close2"></u-tag>
											<u-tag text="运1" borderColor="#aa00ff" bgColor="#aa00ff"
												style="width: 50px;" :show="close3"></u-tag>
											<u-tag text="运2" borderColor="#ffe146" bgColor="#ffe146"
												style="width: 50px;" :show="close3"></u-tag>
										</view>
									</u-scroll-list> -->
								</u-col>
								<u-col span="1">
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 30px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="优先级:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<u-text :text="doneListDetail.listPriorityName" size=15></u-text>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 23px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="描述:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--textarea v-model="doneListDetail.listContent" disabled count></u--textarea>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2"></u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button icon="edit-pen" @click="doneClickEdit">编辑</u-button>
									</view>
								</u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button color="#d6b04f" type="primary" icon="arrow-left"
											@click="toUnfinish(doneListDetail.listId)">未完成</u-button>
									</view>
								</u-col>
								<u-col span="2"></u-col>
							</u-row>
						</view>
					</view>
					<!-- 已完成修改 -->
					<view style="width: 608rpx;height: 900rpx;" v-show="doneEdit">
						<view style="padding: 30px;text-align: center;">
							<u-text text="已办事项修改" size=18></u-text>
						</view>
						<view>
							<u-row customStyle="margin-bottom: 10px;">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="标题：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--input placeholder="请输入事项标题" border="surround"
											v-model="doneListDetail.listTitle"></u--input>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="日期：" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<uni-datetime-picker type="date" :clear-icon="false"
											v-model="doneListDetail.listTime" />
									</view>
								</u-col>
							</u-row>

							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<!-- <u-text text="标签:" size=15></u-text> -->
										<u-text text="类别:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<view class="priorityView" style="width: 150px;">
											<combox-search labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
												:candidates="candidates1" placeholder="请选择类别"
												@getValue="getValue($event,'doneTag')"
												:value="doneListDetail.tag.tagName"></combox-search>
										</view>
									</view>
									<!-- <view style="width: 150px;">
										<u-scroll-list :indicator="true" indicatorActiveColor="#ffe146">
											<view style="display: inline-flex;">
												<u-tag text="工作" borderColor="#ff82ba" bgColor="#ff82ba"
													style="width: 50px;" :show="close1" closable @close="close1=false">
												</u-tag>
												<u-tag text="学习" borderColor="#ffbe25" bgColor="#ffbe25"
													style="width: 50px;" :show="close2" closable @close="close2=false">
												</u-tag>
											</view>
										</u-scroll-list>
									</view> -->
								</u-col>
								<!-- <u-col span="1">
								</u-col> -->
							</u-row>

							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="优先级:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view>
										<view class="priorityView" style="width: 150px;">
											<combox-search labelWidth="100px" :isDisabled="true" emptyTips="暂无数据"
												:candidates="candidates" placeholder="请选择优先级"
												@getValue="getValue($event,'donePriority')"
												:value="doneListDetail.listPriorityName"></combox-search>
										</view>
									</view>

								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 14px">
								<u-col span="2">
								</u-col>
								<u-col span="3">
									<view style="text-align: right;">
										<u-text text="描述:" size=15></u-text>
									</view>
								</u-col>
								<u-col span="7">
									<view style="width: 150px;">
										<u--textarea v-model="doneListDetail.listContent" placeholder="请输入事项描述" count>
										</u--textarea>
									</view>
								</u-col>
							</u-row>
							<u-row customStyle="margin-bottom: 10px">
								<u-col span="2"></u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button icon="close" @click="doneClickEditClose">取消</u-button>
									</view>
								</u-col>
								<u-col span="4">
									<view style="padding: 5px;">
										<u-button color="#fbdc74" type="primary" icon="checkbox-mark"
											@click="submitDoneEdit">修改</u-button>
									</view>
								</u-col>
								<u-col span="2"></u-col>
							</u-row>
						</view>
					</view>
				</u-popup>
			</view>

		</view>

	</view>

</template>

<script>
	import dragButton from "@/components/drag-button/drag-button.vue"
	import comboxSearch from "@/components/cuihai-combox/cuihai-combox.vue"
	import delSlideLeft from '@/components/ay-operate/del_slideLeft.vue'
	import rili11 from '@/static/assets/rili1.png'
	import rili22 from '@/static/assets/rili2.png'
	/**
	 * 获取任意时间
	 */
	function getDate(date, AddDayCount = 0) {
		if (!date) {
			date = new Date()
		}
		if (typeof date !== 'object') {
			date = date.replace(/-/g, '/')
		}
		const dd = new Date(date)

		dd.setDate(dd.getDate() + AddDayCount) // 获取AddDayCount天后的日期

		const y = dd.getFullYear()
		const m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1 // 获取当前月份的日期，不足10补0
		const d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate() // 获取当前几号，不足10补0
		return {
			fullDate: y + '-' + m + '-' + d,
			year: y,
			month: m,
			date: d,
			day: dd.getDay()
		}
	}
	export default {
		components: {
			dragButton,
			comboxSearch,
			delSlideLeft,
		},
		data() {
			return {
				currentTodo: 0,
				currentDone: 0,
				openId: "",
				title: "",
				delTodo: false,
				delTodoShow: false,
				// 待办事项详情--查看
				todoDetail: true,
				// 待办事项详情--编辑
				todoEdit: false,
				// 已办事项详情--查看
				doneDetail: true,
				// 已办事项详情--编辑
				doneEdit: false,

				// 待办事项--搜索日期
				rili1Show: true,
				rili2Show: false,
				rili1: rili11,
				rili2: rili22,
				// 标签页
				candidates: ['重要且紧急', '重要非紧急', '紧急非重要', '非紧急重要'],
				candidates1: ['全部', '学习', '工作', '生活', '其他'],
				listTypes1: [],
				listTypes2: [],
				value1: 0,
				value2: "",
				tagList: [],
				todoList: [],
				doneList: [],
				todoListDetail: {
					listContent: "",
					listId: "",
					listPriorityName: "",
					listTime: "",
					listTitle: "",
					tag: {
						tagName: ""
					},
				},
				doneListDetail: {
					listContent: "",
					listId: "",
					listPriorityName: "",
					listTime: "",
					listTitle: "",
					tag: {
						tagName: ""
					},
				},
				editData: {
					listContent: "",
					listId: "",
					listPriorityName: "",
					listTime: "",
					listTitle: "",
					tag: {
						tagName: ""
					},
				},
				selectData: {},
				addNew: {
					listContent: "",
					listPriorityName: "非紧急重要",
					listTime: "",
					listTitle: "",
					tag: {
						tagName: "全部"
					},
				},
				// 搜索参数
				searchData: {
					searchTodoTitle: "",
					searchDoneTitle: "",
					before: "",
					after: "",
				},
				itemInfo: {},
				// 界面显示
				filtTipsShow: false,
				dragNew: true,
				finishedListView: false,
				unfinishedListView: true,
				showUfnPop: false,
				showAddUfnPop: false,
				showFnsdPop: false,
				unfinished: true,
				finished: true,
				showPriority: false,
				pickerTime: false,
				close1: true,
				close2: true,
				close3: true,
				radios: [{
						checked: true
					},
					{
						checked: false
					},
					{
						checked: false
					}
				],
				checkboxs: [{
						checked: true
					},
					{
						checked: false
					},
					{
						checked: false
					}
				],
				// showTodoCalendar: false,
				// showDoneCalendar: false,
				info: {
					lunar: true,
					range: true,
					insert: false,
					selected: []
				}
			}
		},
		onShow() {
			this.openId = uni.getStorageSync('openId')

			// 第一次加载已完成页面
			this.changeTab(0);

		},
		methods: {
			close(){
				this.showUfnPop = false;
				this.showFnsdPop = false;
				this.todoDetail = true;
				this.todoEdit = false;
				this.doneDetail = true;
				this.doneEdit = false;
			},

			// 获取未完成标签列表
			getTodoTagList() {
				this.$http.get('list/queryTagName/' + this.openId).then(res => {
					this.listTypes1 = res.data;
				});
			},

			// 获取已完成标签列表
			getDoneTagList() {
				this.$http.get('list/queryTagName1/' + this.openId).then(res => {
					this.listTypes2 = res.data;
				});
			},

			// 获取未完成列表
			getUnfinishedList() {
				this.$http.get('list/queryAllToDo/' + this.openId).then(res => {
					this.todoList = res;
				});
			},

			// 获取已完成列表
			getFinishedList() {
				this.$http.get('list/queryAllFinish/' + this.openId).then(res => {
					this.doneList = res;
				});
			},

			// 搜索标题获取未完成列表
			getTodoByTitle() {
				this.$http.get('list/queryAllToDoByTitle', {
						"openId": this.openId,
						"listTitle": this.searchData.searchTodoTitle
					})
					.then(res => {
						this.todoList = res;
						this.currentTodo = 0;
						// console.log("this.currentTodo",this.currentTodo);
						//  this.clickTodoTabs({index: 1});

					});
			},

			// 搜索标题获取已完成列表
			getDoneByTitle() {
				this.$http.get('list/queryAllFinishByTitle', {
						"openId": this.openId,
						"listTitle": this.searchData.searchDoneTitle
					})
					.then(res => {
						this.doneList = res;
						this.currentDone = 0;
					});
			},

			// 按时间获取未完成列表
			getTodoByTime() {
				this.$http.get('list/queryAllToDoByTime', {
						"openId": this.openId,
						"before": this.searchData.before,
						"after": this.searchData.after,
					})
					.then(res => {
						wx.showToast({
							title: '筛选成功',
							icon: 'success',
							duration: 1000
						})
						this.todoList = res;
					});
			},

			// 按时间获取已完成列表
			getDoneByTime() {
				this.$http.get('list/queryAllFinishByTime', {
						"openId": this.openId,
						"before": this.searchData.before,
						"after": this.searchData.after,
					})
					.then(res => {
						wx.showToast({
							title: '筛选成功',
							icon: 'success',
							duration: 1000
						})
						this.doneList = res;
					});
			},

			// 获取未完成的详情
			getTodoDetail(listId) {
				this.$http.get('list/queryDetails', {
						"openId": this.openId,
						"listId": listId,
					})
					.then(res => {
						this.todoListDetail = res.data;
						this.editData = res.data;
					});
			},

			// 获取已完成的详情
			getDoneDetail(listId) {
				this.$http.get('list/queryDetails', {
						"openId": this.openId,
						"listId": listId,
					})
					.then(res => {
						this.doneListDetail = res.data;
					});
			},

			// 未完成转为已完成
			toFinish(listId) {
				this.$http.put('list/toDoToFinish', {
						"openId": this.openId,
						"listId": listId,
					})
					.then(res => {
						if (res.msg = "成功") {
							wx.showToast({
								title: "修改成功",
								icon: 'success',
								duration: 1000
							});
							this.currentTodo = 0;
							this.getTodoTagList();
							this.getUnfinishedList();
						}
					});
				this.showUfnPop = false;

			},

			// 已完成改为未完成
			toUnfinish(listId) {
				this.$http.put('list/FinishTotoDo', {
						"openId": this.openId,
						"listId": listId,
					})
					.then(res => {
						if (res.msg = "成功") {
							wx.showToast({
								title: "修改成功",
								icon: 'success',
								duration: 1000
							});
							this.currentDone = 0;
							this.getDoneTagList();
							this.getFinishedList();
						}

					});
				this.showFnsdPop = false;

			},

			// 提交未完成的编辑
			submitTodoEdit() {
				if(!this.editData.listTitle){
					uni.showToast({
						title: '标题不能为空',
						icon: 'none'
					});
					return
				}
				this.showUfnPop = false;
				this.$http.put('list/updateNewList', {
						"openId": this.openId,
						"listId": this.editData.listId,
						"listTitle": this.editData.listTitle,
						"listTime": this.editData.listTime,
						"tag": {
							"tagName": this.editData.tag.tagName,
						},
						"listPriorityName": this.editData.listPriorityName,
						"listContent": this.editData.listContent,
					})
					.then(res => {
						if (res.msg = "成功") {
							wx.showToast({
								title: "修改成功",
								icon: 'success',
								duration: 1000
							});
							this.resetForm();
							this.getTodoTagList();
							this.getUnfinishedList();
						}
					});
			},

			// 提交已完成的编辑
			submitDoneEdit() {
				if(!this.doneListDetail.listTitle){
					uni.showToast({
						title: '标题不能为空',
						icon: 'none'
					});
					return
				}
				this.showFnsdPop = false;
				this.$http.put('list/updateNewList', {
						"openId": this.openId,
						"listId": this.doneListDetail.listId,
						"listTitle": this.doneListDetail.listTitle,
						"listTime": this.doneListDetail.listTime,
						"tag": {
							"tagName": this.doneListDetail.tag.tagName,
						},
						"listPriorityName": this.doneListDetail.listPriorityName,
						"listContent": this.doneListDetail.listContent,
					})
					.then(res => {
						if (res.msg = "成功") {
							wx.showToast({
								title: "修改成功",
								icon: 'success',
								duration: 1000
							});
							this.getDoneTagList();
							this.getFinishedList();
						}
					});
			},

			// 提交新增
			submitAdd() {
				if (!this.addNew.listTime) {
					uni.showToast({
						title: '请选择日期',
						icon: 'none'
					});
					return
				}
				if (!this.addNew.listTitle) {
					uni.showToast({
						title: '请填写标题',
						icon: 'none'
					});
					return
				}
				this.showAddUfnPop = false;
				this.dragNew = true;
				this.$http.put('list/insertNewList', {
						"openId": this.openId,
						"listTitle": this.addNew.listTitle,
						"listTime": this.addNew.listTime,
						"tag": {
							"tagName": this.addNew.tag.tagName,
						},
						"listPriorityName": this.addNew.listPriorityName,
						"listContent": this.addNew.listContent,
					})
					.then(res => {
						if (res.msg = "成功") {
							wx.showToast({
								title: "添加成功",
								icon: 'success',
								duration: 1000
							});
							this.getTodoTagList();
							this.getUnfinishedList();
							// 置空
							this.resetForm('addNew')
						}
					});
			},

			// 删除
			delItem: function(e) {
				// this.delTodoShow = true;
				// if(this.delTodo = true){
				// let that = this;
				// that.indexList.splice(e.data.index, 1)
				this.$http.delete('list/deleteList', {
						"listId": e.item.listId
					})
					.then(res => {
						// console.log("删除成功")
						// if(res == "true"){
						wx.showToast({
							title: "删除成功",
							icon: 'success',
							duration: 1000
						});
						this.getTodoTagList();
						this.getDoneTagList();
						this.getUnfinishedList();
						this.getFinishedList();
						// }
					});
				// }else{
				// 	console.log("取消删除")
				// }

				// console.log("del",e.item.listId);
			},

			// 切换 未完成 / 已完成 的tab
			changeTab(name) {
				this.value1 = name;
				if (name === 1) { // 切换至已完成
					// 控制依次顺序 
					this.$http.get('list/queryTagName1/' + this.openId).then(res => {
						// 获取已完成页标签列表
						this.listTypes2 = res.data;
						// 获取已完成列表
						this.getFinishedList();
					});
					this.searchData.searchTodoTitle = "";
					this.finishedListView = true;
					this.unfinishedListView = false;

				} else if (name === 0) { // 切换至未完成   0
					// 控制依次顺序 
					this.$http.get('list/queryTagName/' + this.openId).then(res => {
						// 获取未完成页标签列表
						this.listTypes1 = res.data;
						// 获取未完成列表
						this.getUnfinishedList()
					});
					this.searchData.searchDoneTitle = "";
					this.finishedListView = false;
					this.unfinishedListView = true;
				}
			},

			// 切换未完成事项分类tab
			clickTodoTabs(item) {
				this.currentTodo = item.index;
				this.$http.get('list/queryAllToDoByTag/', {
					"openId": this.openId,
					"tagName": item.name,
				}).then(res => {
					this.todoList = res;
				});
			},

			// 切换已完成事项分类tab
			clickDoneTabs(item) {
				this.currentDone = item.index;
				this.$http.get('list/queryAllFinishByTag/', {
					"openId": this.openId,
					"tagName": item.name,
				}).then(res => {
					this.doneList = res;
				});
			},

			// 点击单条未完成事项
			clickUnfinished(index) {
				this.getTodoDetail(index);
				this.showUfnPop = true;
				this.unfinished = false;
			},

			// 点击单条已完成事项
			clickFinished(index) {
				this.getDoneDetail(index);
				this.showFnsdPop = true;
				this.finished = false;
			},

			// 点击未完成的编辑按钮
			todoClickEdit() {
				this.selectData = JSON.stringify(this.editData)
				this.todoDetail = false;
				this.todoEdit = true;
			},

			// 关闭未完成的编辑按钮
			todoClickEditClose() {
				this.todoDetail = true;
				this.todoEdit = false;
				let data = JSON.parse(this.selectData);
				this.editData.listTitle = data.listTitle
				this.editData.listTime = data.listTime
				this.editData.listPriorityName = data.listPriorityName
				this.editData.tag.tagName = data.tag.tagName
				this.editData.listContent = data.listContent
				// this.resetForm('editData');
			},

			// 点击已完成的编辑按钮
			doneClickEdit() {
				this.selectData = JSON.stringify(this.doneListDetail)
				this.doneDetail = false;
				this.doneEdit = true;
			},

			// 关闭已完成的编辑按钮
			doneClickEditClose() {
				this.doneDetail = true;
				this.doneEdit = false;
				let data = JSON.parse(this.selectData);
				this.doneListDetail.listTitle = data.listTitle
				this.doneListDetail.listTime = data.listTime
				this.doneListDetail.listPriorityName = data.listPriorityName
				this.doneListDetail.tag.tagName = data.tag.tagName
				this.doneListDetail.listContent = data.listContent
			},

			// 下拉列表选择
			getValue(e, type) {
				switch (type) {
					case 'todoTag':
						this.todoListDetail.tag.tagName = e;
						break;
					case 'todoPriority':
						this.todoListDetail.listPriorityName = e;
						break;
					case 'doneTag':
						this.doneListDetail.tag.tagName = e;
						break;
					case 'donePriority':
						this.doneListDetail.listPriorityName = e;
						break;
					case 'addTag':
						this.addNew.tag.tagName = e;
						break;
					case 'addPriority':
						this.addNew.listPriorityName = e;
						break;
					default:
						break;
				}
				console.log(e);
			},

			// 打开按日历筛选未完成
			openTodoCal() {
				// this.showTodoCalendar = true;
				this.$refs.todoCalendar.open();
			},

			// 打开按日历筛选已完成
			openDoneCal() {
				// this.showDoneCalendar = true;
				this.$refs.doneCalendar.open();
			},

			// 确定日期筛选未完成
			confirmTodoCal(e) {
				this.searchData.before = e.range.before;
				this.searchData.after = e.range.after;
				this.getTodoByTime();
			},

			// 确定日期筛选已完成
			confirmDoneCal(e) {
				this.searchData.before = e.range.before;
				this.searchData.after = e.range.after;
				this.getDoneByTime();
			},

			// 点击新增待办事项
			addUnfnsButton() {
				this.dragNew = false;
				this.showAddUfnPop = true;
			},

			// 重置表单
			resetForm(type) {
				this.showAddUfnPop = false;
				switch (type) {
					case 'addNew':
						this.addNew = {
							listContent: "",
							listPriorityName: "非紧急重要",
							listTime: "",
							listTitle: "",
							tag: {
								tagName: "全部"
							},
						};
						break;
					case 'todoListDetail':
						this.todoListDetail = {
							listContent: "",
							listId: "",
							listPriorityName: "",
							listTime: "",
							listTitle: "",
							tag: {
								tagName: ""
							},
						};
						break;
					case 'doneListDetail':
						this.doneListDetail = {
							listContent: "",
							listId: "",
							listPriorityName: "",
							listTime: "",
							listTitle: "",
							tag: {
								tagName: ""
							},
						};
						break;
					case 'searchData':
						this.searchData = {
							searchTodoTitle: "",
							searchDoneTitle: "",
							before: "",
							after: "",
						};
						break;
					case 'editData':
						// console.log("edit")
						this.editData = {
							listContent: "",
							listId: "",
							listPriorityName: "",
							listTime: "",
							listTitle: "",
							tag: {
								tagName: ""
							},
						};
						break;
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.priorityView {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14px;
		background-color: #ffffff;
		/* overflow: hidden; */
	}
</style>
