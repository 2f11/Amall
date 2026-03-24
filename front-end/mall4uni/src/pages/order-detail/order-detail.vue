<template>
  <view class="container">
    <view class="order-detail">
      <!-- 订单状态显示 -->
      <view class="order-status-header">
        <view class="status-text">
          {{
            status == 1
              ? "待付款"
              : status == 2
              ? "待发货"
              : status == 3
              ? "待收货"
              : status == 5
              ? "已完成"
              : "已取消"
          }}
        </view>
        <view v-if="status == 1" class="status-desc">
          请在{{ countdownText }}内完成支付，否则订单将自动取消
        </view>
      </view>

      <view v-if="userAddrDto" class="delivery-addr">
        <view class="user-info">
          <text class="item">
            {{ userAddrDto.receiver }}
          </text>
          <text class="item">
            {{ userAddrDto.mobile }}
          </text>
        </view>
        <view class="addr">
          {{ userAddrDto.province }}{{ userAddrDto.city }}{{ userAddrDto.area
          }}{{ userAddrDto.area }}{{ userAddrDto.addr }}
        </view>
      </view>

      <!-- 商品信息 -->
      <view v-if="orderItemDtos" class="prod-item">
        <block v-for="(item, index) in orderItemDtos" :key="index">
          <view class="item-cont" :data-prodid="item.prodId" @tap="toProdPage">
            <view class="prod-pic">
              <image :src="item.pic" />
            </view>
            <view class="prod-info">
              <view class="prodname">
                {{ item.prodName }}
              </view>
              <view class="prod-info-cont">
                <text class="number"> 数量：{{ item.prodCount }} </text>
                <text class="info-item">
                  {{ item.skuName }}
                </text>
              </view>
              <view class="price-nums clearfix">
                <text class="prodprice">
                  <text class="symbol"> ￥ </text>
                  <text class="big-num">
                    {{ wxs.parsePrice(item.price)[0] }}
                  </text>
                  <text class="small-num">
                    .{{ wxs.parsePrice(item.price)[1] }}
                  </text>
                </text>
                <view class="btn-box" />
              </view>
            </view>
          </view>
        </block>
      </view>

      <!-- 订单信息 -->
      <view class="order-msg">
        <view class="msg-item">
          <view class="item">
            <text class="item-tit"> 订单编号： </text>
            <text class="item-txt">
              {{ orderNumber }}
            </text>
          </view>
          <view class="item">
            <text class="item-tit"> 下单时间： </text>
            <text class="item-txt">
              {{ createTime }}
            </text>
          </view>
        </view>
        <view class="msg-item">
          <view class="item">
            <text class="item-tit"> 支付方式： </text>
            <text class="item-txt"> 微信支付 </text>
          </view>
          <view class="item">
            <text class="item-tit"> 配送方式： </text>
            <text class="item-txt"> 普通配送 </text>
          </view>
          <view class="item">
            <text v-if="!!remarks" class="item-tit"> 订单备注： </text>
            <text class="item-txt remarks">
              {{ remarks }}
            </text>
          </view>
        </view>
      </view>

      <view class="order-msg">
        <view class="msg-item">
          <view class="item">
            <view class="item-tit"> 订单总额： </view>
            <view class="item-txt price">
              <text class="symbol"> ￥ </text>
              <text class="big-num">
                {{ wxs.parsePrice(total)[0] }}
              </text>
              <text class="small-num"> .{{ wxs.parsePrice(total)[1] }} </text>
            </view>
          </view>
          <view class="item">
            <view class="item-tit"> 运费： </view>
            <view class="item-txt price">
              <text class="symbol"> ￥ </text>
              <text class="big-num">
                {{ wxs.parsePrice(transfee)[0] }}
              </text>
              <text class="small-num">
                .{{ wxs.parsePrice(transfee)[1] }}
              </text>
            </view>
          </view>
          <view class="item">
            <view class="item-tit"> 优惠券： </view>
            <view class="item-txt price">
              <text class="symbol"> -￥ </text>
              <text class="big-num">
                {{ wxs.parsePrice(reduceAmount)[0] }}
              </text>
              <text class="small-num">
                .{{ wxs.parsePrice(reduceAmount)[1] }}
              </text>
            </view>
          </view>
          <view class="item payment">
            <view class="item-txt price">
              实付款：
              <text class="symbol"> ￥ </text>
              <text class="big-num">
                {{ wxs.parsePrice(actualTotal)[0] }}
              </text>
              <text class="small-num">
                .{{ wxs.parsePrice(actualTotal)[1] }}
              </text>
            </view>
          </view>
        </view>
      </view>

      <!-- 底部栏 -->
      <view
        v-if="status == 1 || status == 3 || status == 5 || status == 6"
        class="order-detail-footer"
      >
        <view class="footer-box">
          <text v-if="status == 1" class="apply-service" @tap="onCancelOrder">
            取消订单
          </text>
          <text v-if="status == 1" class="buy-again" @tap="normalPay">
            立即支付
          </text>
          <text v-if="status == 3" class="buy-again" @tap="onConfirmReceive">
            确认收货
          </text>
          <text
            v-if="status == 5 || status == 6"
            class="apply-service"
            @tap="delOrderList"
          >
            删除订单
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
const wxs = number();

const countdownText = ref("30:00");
let timer = null;

/**
 * 启动倒计时
 */
const startCountdown = (createTimeStr) => {
  if (timer) clearInterval(timer);
  if (!createTimeStr) return;

  const update = () => {
    const startTime = new Date(createTimeStr.replace(/-/g, "/")).getTime();
    const endTime = startTime + 30 * 60 * 1000;
    const now = new Date().getTime();
    const diff = endTime - now;

    if (diff <= 0) {
      countdownText.value = "00:00";
      clearInterval(timer);
      // 倒计时结束，刷新订单详情以更新状态
      loadOrderDetail(orderNumber.value);
      return;
    }

    const minutes = Math.floor(diff / (60 * 1000));
    const seconds = Math.floor((diff % (60 * 1000)) / 1000);
    countdownText.value = `${minutes.toString().padStart(2, "0")}:${seconds
      .toString()
      .padStart(2, "0")}`;
  };

  update();
  timer = setInterval(update, 1000);
};

/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  loadOrderDetail(options.orderNum);
});

/**
 * 页面卸载，清除定时器
 */
onUnload(() => {
  if (timer) clearInterval(timer);
});

/**
 * 跳转商品详情页
 */
const toProdPage = (e) => {
  const prodid = e.currentTarget.dataset.prodid;
  uni.navigateTo({
    url: "/pages/prod/prod?prodid=" + prodid,
  });
};

const remarks = ref("");
const orderItemDtos = ref([]);
const reduceAmount = ref("");
const transfee = ref("");
const status = ref(0);
const actualTotal = ref(0);
const userAddrDto = ref(null);
const orderNumber = ref("");
const createTime = ref("");
const total = ref(0); // 商品总额
/**
 * 加载订单数据
 */
const loadOrderDetail = (orderNum) => {
  uni.showLoading(); // 加载订单详情
  http
    .request({
      url: "/p/myOrder/orderDetail",
      method: "GET",
      data: {
        orderNumber: orderNum,
      },
    })
    .then(({ data }) => {
      orderNumber.value = orderNum;
      actualTotal.value = data.actualTotal;
      userAddrDto.value = data.userAddrDto;
      remarks.value = data.remarks;
      orderItemDtos.value = data.orderItemDtos;
      createTime.value = data.createTime;
      status.value = data.status;
      transfee.value = data.transfee;
      reduceAmount.value = data.reduceAmount;
      total.value = data.total;
      uni.hideLoading();

      // 如果是待支付状态，启动倒计时
      if (status.value == 1) {
        startCountdown(createTime.value);
      } else {
        if (timer) clearInterval(timer);
      }
    });
};

/**
 * 取消订单
 */
const onCancelOrder = () => {
  uni.showModal({
    title: "",
    content: "确定要取消此订单？",
    confirmColor: "#3e62ad",
    cancelColor: "#3e62ad",
    cancelText: "否",
    confirmText: "是",
    success(res) {
      if (res.confirm) {
        uni.showLoading({
          mask: true,
        });
        http
          .request({
            url: "/p/myOrder/cancel/" + orderNumber.value,
            method: "PUT",
            data: {},
          })
          .then(() => {
            loadOrderDetail(orderNumber.value);
            uni.hideLoading();
          });
      }
    },
  });
};

/**
 * 模拟支付
 */
const normalPay = () => {
  uni.showLoading({
    mask: true,
  });
  http
    .request({
      url: "/p/order/normalPay",
      method: "POST",
      data: {
        orderNumbers: orderNumber.value,
      },
    })
    .then(({ data }) => {
      uni.hideLoading();
      if (data) {
        uni.showToast({
          title: "模拟支付成功",
          icon: "none",
        });
        setTimeout(() => {
          uni.redirectTo({
            url:
              "/pages/pay-result/pay-result?sts=1&orderNumbers=" +
              orderNumber.value,
          });
        }, 1200);
      } else {
        uni.showToast({
          title: "支付失败！",
          icon: "none",
        });
      }
    });
};

/**
 * 确认收货
 */
const onConfirmReceive = () => {
  uni.showModal({
    title: "",
    content: "我已收到货？",
    confirmColor: "#eb2444",
    success(res) {
      if (res.confirm) {
        uni.showLoading({
          mask: true,
        });
        http
          .request({
            url: "/p/myOrder/receipt/" + orderNumber.value,
            method: "PUT",
          })
          .then(() => {
            loadOrderDetail(orderNumber.value);
            uni.hideLoading();
          });
      }
    },
  });
};

/**
 * 删除已完成||已取消的订单
 */
const delOrderList = () => {
  uni.showModal({
    title: "",
    content: "确定要删除此订单吗？",
    confirmColor: "#eb2444",
    success(res) {
      if (res.confirm) {
        uni.showLoading();
        http
          .request({
            url: "/p/myOrder/" + orderNumber.value,
            method: "DELETE",
          })
          .then(() => {
            uni.hideLoading();
            uni.showToast({
              title: res || "删除成功",
              icon: "none",
            });
            setTimeout(() => {
              uni.redirectTo({
                url: "/pages/orderList/orderList",
              });
            }, 1000);
          });
      }
    },
  });
};
</script>

<style scoped lang="scss">
@use "./order-detail.scss";
</style>
