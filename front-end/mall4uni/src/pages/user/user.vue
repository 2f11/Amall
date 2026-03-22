<template>
  <view class="container">
    <!-- 用户信息 -->
    <view class="profile-header" @tap="onHeaderTap">
      <view class="profile-avatar">
        <image :src="avatarSrc" />
      </view>
      <view class="profile-text">
        <view class="profile-name">
          {{ displayName }}
        </view>
        <view class="profile-sub">
          {{ subtitleText }}
        </view>
      </view>
    </view>
    <!-- end 用户信息 -->

    <view class="list-cont">
      <!-- 订单状态 -->
      <view class="total-order">
        <view class="order-tit">
          <text style="font-weight: bold"> 我的订单 </text>
          <view class="checkmore" data-sts="0" @tap="toOrderListPage">
            <text>查看全部</text>
            <text class="arrowhead" />
          </view>
        </view>
        <view class="procedure">
          <view class="items" data-sts="1" @tap="toOrderListPage">
            <view class="order-icon">
              <image src="@/static/images/icon/toPay.png" />
            </view>
            <text>待支付</text>
            <text v-if="orderAmount.unPay > 0" class="num-badge">
              {{ orderAmount.unPay }}
            </text>
          </view>
          <view class="items" data-sts="2" @tap="toOrderListPage">
            <view class="order-icon">
              <image src="@/static/images/icon/toDelivery.png" />
            </view>
            <text>待发货</text>
            <text v-if="orderAmount.payed > 0" class="num-badge">
              {{ orderAmount.payed }}
            </text>
          </view>
          <view class="items" data-sts="3" @tap="toOrderListPage">
            <view class="order-icon">
              <image src="@/static/images/icon/toTake.png" />
            </view>
            <text>待收货</text>
            <text v-if="orderAmount.consignment > 0" class="num-badge">
              {{ orderAmount.consignment }}
            </text>
          </view>
          <view class="items" data-sts="5" @tap="toOrderListPage">
            <view class="order-icon">
              <image src="@/static/images/icon/toComment.png" />
            </view>
            <text>已完成</text>
          </view>
        </view>
      </view>
      <!--end 订单状态 -->

      <view class="prod-col">
        <view class="col-item" @tap="myCollectionHandle">
          <view v-if="loginResult" class="num">
            {{ collectionCount }}
          </view>
          <view v-else class="num"> -- </view>
          <view class="tit"> 我的收藏 </view>
        </view>
        <view class="col-item" @tap="handleTips">
          <view class="num"> -- </view>
          <view class="tit"> 我的消息 </view>
        </view>
        <view class="col-item" @tap="handleTips">
          <view class="num"> -- </view>
          <view class="tit"> 我的足迹 </view>
        </view>
      </view>

      <view class="my-menu">
        <view class="memu-item" @tap="toDistCenter">
          <view class="i-name">
            <image src="@/static/images/icon/promotion.png" />
            <text>分销中心</text>
          </view>
          <view class="arrowhead" />
        </view>
        <view class="memu-item" @tap="toCouponCenter">
          <view class="i-name">
            <image src="@/static/images/icon/getCoupon.png" />
            <text>领券中心</text>
          </view>
          <view class="arrowhead" />
        </view>
        <view class="memu-item" @tap="toMyCouponPage">
          <view class="i-name">
            <image src="@/static/images/icon/myCoupon.png" />
            <text>我的优惠券</text>
          </view>
          <view class="arrowhead" />
        </view>
        <view class="memu-item" @tap="toAddressList">
          <view class="i-name">
            <image src="@/static/images/icon/myAddr.png" />
            <text>收货地址</text>
          </view>
          <view class="arrowhead" />
        </view>
      </view>
      <!--end 列表项 -->

      <view v-if="isAuthInfo" class="log-out" @tap="logout">
        <view class="log-out-n">
          <text>退出登录</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
const picDomain = import.meta.env.VITE_APP_RESOURCES_URL;

const isAuthInfo = ref(false);
const loginResult = ref("");
const orderAmount = ref("");
const collectionCount = ref(0);

const avatarSrc = computed(() => {
  const pic = loginResult.value && loginResult.value.pic;
  if (pic) {
    return pic.indexOf("http") === -1 ? picDomain + pic : pic;
  }
  return "/static/images/icon/head04.png";
});

const displayName = computed(() => {
  const nickName = loginResult.value && loginResult.value.nickName;
  if (nickName) return nickName;
  return isAuthInfo.value ? "用户昵称" : "未登录";
});

const subtitleText = computed(() => {
  return isAuthInfo.value ? "安心选种 · 当季农品" : "点击登录账号";
});
/**
 * 生命周期函数--监听页面显示
 */
onShow(() => {
  const token = uni.getStorageSync("Token");
  const storedLoginResult = uni.getStorageSync("loginResult");
  isAuthInfo.value = !!token;
  loginResult.value = storedLoginResult || (token ? {} : "");

  if (!isAuthInfo.value) {
    orderAmount.value = "";
    collectionCount.value = 0;
    return;
  }

  uni.showLoading();
  Promise.all([
    getOrderCount(),
    showCollectionCount(),
    fetchUserInfo(),
  ]).finally(() => {
    uni.hideLoading();
  });
});

const onHeaderTap = () => {
  if (!isAuthInfo.value) {
    toLogin();
  }
};

const toDistCenter = () => {
  uni.showToast({
    icon: "none",
    title: "该功能未开源",
  });
};
const toCouponCenter = () => {
  uni.showToast({
    icon: "none",
    title: "该功能未开源",
  });
};
const toMyCouponPage = () => {
  uni.showToast({
    icon: "none",
    title: "该功能未开源",
  });
};
const handleTips = () => {
  uni.showToast({
    icon: "none",
    title: "该功能未开源",
  });
};
const toAddressList = () => {
  uni.navigateTo({
    url: "/pages/delivery-address/delivery-address",
  });
};

const toOrderListPage = (e) => {
  const sts = e.currentTarget.dataset.sts;
  uni.navigateTo({
    url: "/pages/orderList/orderList?sts=" + sts,
  });
};

const getOrderCount = () => {
  return http
    .request({
      url: "/p/myOrder/orderCount",
      method: "GET",
      data: {},
    })
    .then(({ data }) => {
      orderAmount.value = data;
    });
};

const showCollectionCount = () => {
  return http
    .request({
      url: "/p/user/collection/count",
      method: "GET",
      data: {},
    })
    .then(({ data }) => {
      collectionCount.value = data;
    });
};

const fetchUserInfo = () => {
  return http
    .request({
      url: "/p/user/userInfo",
      method: "GET",
      data: {},
    })
    .then(({ data }) => {
      if (!data) return;
      const merged = {
        ...(loginResult.value || {}),
        ...data,
      };
      loginResult.value = merged;
      uni.setStorageSync("loginResult", merged);
    });
};

/**
 * 我的收藏跳转
 */
const myCollectionHandle = () => {
  let url = "/pages/prod-classify/prod-classify?sts=5";
  const id = 0;
  const title = "我的收藏";
  if (id) {
    url += "&tagid=" + id + "&title=" + title;
  }
  uni.navigateTo({
    url,
  });
};

/**
 * 去登陆
 */
const toLogin = () => {
  uni.navigateTo({
    url: "/pages/accountLogin/accountLogin",
  });
};

/**
 * 退出登录
 */
const logout = () => {
  http
    .request({
      url: "/logOut",
      method: "post",
    })
    .then(() => {
      util.removeTabBadge();
      uni.removeStorageSync("loginResult");
      uni.removeStorageSync("Token");
      uni.removeStorageSync("hadLogin");
      uni.removeStorageSync("expiresTimeStamp");
      uni.removeStorageSync("isRefreshingToken");
      uni.showToast({
        title: "退出成功",
        icon: "none",
      });
      orderAmount.value = "";
      collectionCount.value = 0;
      isAuthInfo.value = false;
      loginResult.value = "";
      setTimeout(() => {
        uni.switchTab({
          url: "/pages/index/index",
        });
      }, 1000);
    });
};
</script>

<style scoped lang="scss">
@use "./user.scss";
</style>
