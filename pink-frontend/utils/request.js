import config from './config.js';
export default {
	request(options = {}) {
		return new Promise((resolve, reject) => {
			let url = options.url;
			if (url.indexOf("http://") == -1 && url.indexOf("https://") == -1) {
				options.url = config.domain + url;
			}
			options.header.Authorization = uni.getStorageSync("token");
			options.complete = (response) => {
				if (response.statusCode == 200 || response.statusCode == 0) {
					// 这里自定义登录
					if (response.data.code == 401) {
						uni.showModal({
							title: '登录',
							content: '您还未登录',
							showCancel: false,
							confirmText: '去登录',
							success() {
								uni.navigateTo({
									url: "/pages/login/login"
								})
							}
						})
					}
					if (response.data.code == 200) {
						resolve(response.data);
					}
				} else {
					uni.showToast({
						title: '请求异常！',
						icon: "none"
					});
				}
			}
			uni.request(options)
		})
	},

	post(url, data = {}, header = {}) {
		let options = {
			url: url,
			data: data,
			header: header,
			method: "POST"
		}
		return this.request(options);
	},

	get(url, data = {}, header = {}) {
		let options = {
			url: url,
			data: data,
			header: header
		}
		return this.request(options);
	},

	put(url, data = {}, header = {}) {
		let options = {
			url: url,
			data: data,
			header: header,
			method: "PUT"
		}
		return this.request(options);
	},
	delete(url, data = {}, header = {}) {
		let options = {
			url: url,
			data: data,
			header: header,
			method: "DELETE"
		}
		return this.request(options);
	},
};
