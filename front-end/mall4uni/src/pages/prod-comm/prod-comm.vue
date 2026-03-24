<template>
  <view class="container">
    <!-- 商品信息卡片 -->
    <view class="prod-card">
      <view class="item-cont">
        <view class="prod-pic">
          <image :src="prod.pic" mode="aspectFill" />
        </view>
        <view class="prod-info">
          <view class="prodname">{{ prod.prodName }}</view>
          <view class="sku-name">{{ prod.skuName }}</view>
        </view>
      </view>
    </view>

    <!-- 评价表单 -->
    <view class="comm-form">
      <!-- 评分 -->
      <view class="form-item score-item">
        <text class="label">评分</text>
        <uni-rate v-model="formData.score" :size="24" margin="10" />
      </view>

      <!-- 文字评价 -->
      <view class="form-item content-item">
        <textarea
          v-model="formData.content"
          placeholder="分享你的使用体验，给其他小伙伴一点参考吧~"
          maxlength="200"
          placeholder-style="color:#999;font-size:28rpx"
        />
        <text class="word-count">{{ formData.content.length }}/200</text>
      </view>

      <!-- 图片上传 -->
      <view class="form-item upload-item">
        <view class="upload-title">添加图片</view>
        <view class="image-list">
          <view
            v-for="(pic, index) in formData.pics"
            :key="index"
            class="image-item"
          >
            <image
              :src="pic.url"
              mode="aspectFill"
              @tap="previewImage(index)"
            />
            <view class="delete-icon" @tap.stop="onDelete(index)">
              <text class="close-x">×</text>
            </view>
            <view v-if="pic.status === 'uploading'" class="upload-mask">
              <text class="loading-text">上传中...</text>
            </view>
          </view>
          <view
            v-if="formData.pics.length < 9"
            class="upload-btn"
            @tap="chooseImage"
          >
            <text class="plus">+</text>
            <text class="txt">添加图片</text>
          </view>
        </view>
      </view>

      <!-- 匿名选项 -->
      <view class="form-item anonymous-item">
        <view class="left">
          <text class="title">匿名评价</text>
          <text class="desc">评价将以匿名的形式展现</text>
        </view>
        <switch
          :checked="formData.isAnonymous"
          color="#07c160"
          @change="anonymousChange"
        />
      </view>

      <!-- 提交按钮 -->
      <view class="submit-btn-wrap">
        <button class="submit-btn" @tap="submitComm">提交评价</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from "vue";
import { onLoad } from "@dcloudio/uni-app";
import http from "@/utils/http";

const prod = ref({});
const formData = ref({
  score: 5,
  content: "",
  pics: [],
  isAnonymous: false,
  prodId: null,
  orderItemId: null,
});

onLoad((options) => {
  const orderItem = JSON.parse(decodeURIComponent(options.orderItem));
  prod.value = orderItem;
  // 极其详尽的兼容处理：适配后端可能返回的所有字段格式
  formData.value.prodId =
    orderItem.prodId || orderItem.prod_id || orderItem.productId;
  formData.value.orderItemId =
    orderItem.orderItemId ||
    orderItem.order_item_id ||
    orderItem.primaryOrderNo;

  // 如果还是没有拿到 ID，尝试从商品对象本身提取（某些接口返回结构不同）
  if (!formData.value.prodId && orderItem.prodId) {
    formData.value.prodId = orderItem.prodId;
  }

  // 打印日志，请在 H5 或小程序控制台确认输出内容
  console.log("--- 评价页接收到的原始数据 ---", orderItem);
  console.log("--- 提取后的表单提交数据 ---", formData.value);
});

const anonymousChange = (e) => {
  formData.value.isAnonymous = e.detail.value;
};

const submitComm = () => {
  if (!formData.value.content.trim()) {
    uni.showToast({
      title: "请输入评价内容",
      icon: "none",
    });
    return;
  }

  // 提取已上传成功的图片URL
  const uploadedPics = formData.value.pics
    .filter((p) => p.status === "success" || p.url.startsWith("http"))
    .map((p) => p.url);

  const params = {
    ...formData.value,
    isAnonymous: formData.value.isAnonymous ? 1 : 0,
    pics: JSON.stringify(uploadedPics),
  };

  http
    .request({
      url: "/p/prodComm",
      method: "POST",
      data: params,
    })
    .then(() => {
      uni.showToast({
        title: "评价成功",
        icon: "success",
      });
      setTimeout(() => {
        uni.navigateBack();
      }, 1500);
    });
};

// 图片上传相关方法
const chooseImage = () => {
  const count = 9 - formData.value.pics.length;
  if (count <= 0) return;

  uni.chooseImage({
    count: count,
    sizeType: ["compressed"],
    sourceType: ["album", "camera"],
    success: (res) => {
      const tempFilePaths = res.tempFilePaths;
      tempFilePaths.forEach((path) => {
        // 先添加到列表显示，状态为上传中
        const index = formData.value.pics.length;
        formData.value.pics.push({
          url: path,
          status: "uploading",
        });

        uni.uploadFile({
          url: import.meta.env.VITE_APP_BASE_API + "/p/file/upload",
          filePath: path,
          name: "file",
          header: {
            Authorization: uni.getStorageSync("Token"),
          },
          success: (uploadRes) => {
            const data = JSON.parse(uploadRes.data);
            if (data.code === "00000") {
              // 上传成功，更新 url 和 status
              formData.value.pics[index].url = data.data;
              formData.value.pics[index].status = "success";
            } else {
              uni.showToast({
                title: data.msg || "上传失败",
                icon: "none",
              });
              formData.value.pics.splice(index, 1);
            }
          },
          fail: () => {
            uni.showToast({
              title: "网络错误，上传失败",
              icon: "none",
            });
            formData.value.pics.splice(index, 1);
          },
        });
      });
    },
  });
};

// 预览图片
const previewImage = (index) => {
  uni.previewImage({
    current: index,
    urls: formData.value.pics.map((p) => p.url),
  });
};

// 删除图片
const onDelete = (index) => {
  formData.value.pics.splice(index, 1);
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  padding: 20rpx;
  box-sizing: border-box;
}

.prod-card {
  background-color: #fff;
  padding: 30rpx;
  border-radius: 16rpx;
  margin-bottom: 20rpx;

  .item-cont {
    display: flex;
    align-items: center;

    .prod-pic {
      width: 120rpx;
      height: 120rpx;
      border-radius: 8rpx;
      overflow: hidden;
      flex-shrink: 0;

      image {
        width: 100%;
        height: 100%;
      }
    }

    .prod-info {
      margin-left: 20rpx;
      flex: 1;

      .prodname {
        font-size: 28rpx;
        color: #333;
        line-height: 1.4;
        margin-bottom: 8rpx;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }

      .sku-name {
        font-size: 24rpx;
        color: #999;
      }
    }
  }
}

.comm-form {
  background-color: #fff;
  padding: 30rpx;
  border-radius: 16rpx;

  .form-item {
    margin-bottom: 40rpx;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .score-item {
    display: flex;
    align-items: center;

    .label {
      font-size: 30rpx;
      font-weight: bold;
      color: #333;
      margin-right: 30rpx;
    }
  }

  .content-item {
    background-color: #f9f9f9;
    border-radius: 12rpx;
    padding: 20rpx;
    position: relative;

    textarea {
      width: 100%;
      height: 240rpx;
      font-size: 28rpx;
      color: #333;
      line-height: 1.5;
    }

    .word-count {
      position: absolute;
      right: 20rpx;
      bottom: 20rpx;
      font-size: 24rpx;
      color: #ccc;
    }
  }

  .upload-item {
    .upload-title {
      font-size: 28rpx;
      color: #333;
      font-weight: bold;
      margin-bottom: 20rpx;
    }

    .image-list {
      display: flex;
      flex-wrap: wrap;

      .image-item {
        width: 160rpx;
        height: 160rpx;
        margin-right: 20rpx;
        margin-bottom: 20rpx;
        position: relative;
        border-radius: 8rpx;
        overflow: hidden;

        image {
          width: 100%;
          height: 100%;
        }

        .delete-icon {
          position: absolute;
          top: 0;
          right: 0;
          width: 40rpx;
          height: 40rpx;
          background-color: rgba(0, 0, 0, 0.5);
          display: flex;
          align-items: center;
          justify-content: center;
          border-bottom-left-radius: 8rpx;

          .close-x {
            color: #fff;
            font-size: 32rpx;
            line-height: 1;
          }
        }

        .upload-mask {
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background-color: rgba(0, 0, 0, 0.3);
          display: flex;
          align-items: center;
          justify-content: center;

          .loading-text {
            color: #fff;
            font-size: 20rpx;
          }
        }
      }

      .upload-btn {
        width: 160rpx;
        height: 160rpx;
        background-color: #f7f7f7;
        border: 1px dashed #ddd;
        border-radius: 8rpx;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;

        .plus {
          font-size: 60rpx;
          color: #999;
          line-height: 1;
        }

        .txt {
          font-size: 24rpx;
          color: #999;
          margin-top: 10rpx;
        }
      }
    }
  }

  .anonymous-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #f5f5f5;
    padding-top: 30rpx;

    .left {
      .title {
        font-size: 28rpx;
        color: #333;
        display: block;
        margin-bottom: 4rpx;
      }
      .desc {
        font-size: 22rpx;
        color: #999;
      }
    }
  }

  .submit-btn-wrap {
    margin-top: 60rpx;

    .submit-btn {
      width: 100%;
      height: 88rpx;
      line-height: 88rpx;
      background-color: #07c160;
      color: #fff;
      font-size: 32rpx;
      font-weight: bold;
      border-radius: 44rpx;
      border: none;

      &::after {
        border: none;
      }

      &:active {
        opacity: 0.8;
      }
    }
  }
}
</style>
