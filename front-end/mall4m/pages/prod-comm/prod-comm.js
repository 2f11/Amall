var http = require('../../utils/http.js');

Page({
  data: {
    orderNumber: '',
    items: [],
    editing: false,
    editingIndex: -1,
    score: 5,
    content: '',
    isAnonymous: 0,
    submitting: false
  },

  onLoad: function (options) {
    this.setData({
      orderNumber: options.orderNumber || ''
    })
    this.loadItems()
  },

  loadItems: function () {
    if (!this.data.orderNumber) {
      return
    }
    wx.showLoading()
    http.request({
      url: '/prodComm/orderItems',
      method: 'GET',
      data: {
        orderNumber: this.data.orderNumber
      },
      callBack: (res) => {
        this.setData({
          items: res || []
        })
        wx.hideLoading()
      },
      errCallBack: () => {
        wx.hideLoading()
      }
    })
  },

  startComm: function (e) {
    var index = e.currentTarget.dataset.index
    var item = this.data.items[index]
    if (!item || item.commSts == 1) {
      return
    }
    this.setData({
      editing: true,
      editingIndex: index,
      score: 5,
      content: '',
      isAnonymous: 0
    })
  },

  cancelComm: function () {
    this.setData({
      editing: false,
      editingIndex: -1
    })
  },

  onScoreChange: function (e) {
    this.setData({
      score: e.detail.value
    })
  },

  onContentInput: function (e) {
    this.setData({
      content: e.detail.value
    })
  },

  onAnonymousChange: function (e) {
    this.setData({
      isAnonymous: e.detail.value ? 1 : 0
    })
  },

  submitComm: function () {
    if (this.data.submitting) {
      return
    }
    var index = this.data.editingIndex
    var item = this.data.items[index]
    if (!item) {
      return
    }
    if (!this.data.content) {
      wx.showToast({
        title: '请输入评价内容',
        icon: 'none'
      })
      return
    }
    this.setData({
      submitting: true
    })
    http.request({
      url: '/prodComm/submit',
      method: 'POST',
      data: {
        orderItemId: item.orderItemId,
        prodId: item.prodId,
        score: this.data.score,
        content: this.data.content,
        isAnonymous: this.data.isAnonymous
      },
      callBack: () => {
        wx.showToast({
          title: '提交成功',
          icon: 'none'
        })
        this.setData({
          submitting: false,
          editing: false,
          editingIndex: -1
        })
        this.loadItems()
      },
      errCallBack: () => {
        this.setData({
          submitting: false
        })
      }
    })
  }
})

