<template>
  <view class="container">
    <!-- 头部搜索区 -->
    <view class="search-bar">
      <view
        class="search-box"
        @tap="toSearchPage"
      >
        <image
          src="@/static/images/icon/search.png"
          class="search-img"
        />
        <text class="sear-input">
          搜索您想要的农品/种子
        </text>
      </view>
    </view>
    <!-- 滚动内容区 -->
    <view class="main">
      <!-- 左侧菜单start -->
      <scroll-view
        scroll-y="true"
        class="leftmenu"
      >
        <block
          v-for="(item, index) in categoryList"
          :key="index"
        >
          <view
            :class="'menu-item ' + (selIndex==index?'active':'') + ' '"
            :data-index="index"
            :data-id="item.categoryId"
            @tap="onMenuTab"
          >
            {{ item.categoryName }}
          </view>
        </block>
        <view
          v-if="!categoryList.length"
          class="ca-empty"
        >
          {{ categoryList && categoryList.length ? '该分类下暂无农品' : '暂无农品' }}
        </view>
      </scroll-view>
      <!-- 左侧菜单end -->

      <!-- 右侧内容start -->
      <scroll-view
        scroll-y="true"
        class="rightcontent"
      >
        <view class="adver-map">
          <view class="item-a">
            <image
              :src="util.checkFileUrl(categoryImg)"
              mode="widthFix"
            />
          </view>
        </view>
        <!-- 子分类 -->
        <view
          v-if="subCategoryList.length"
          class="th-cate-con"
        >
          <block
            v-for="(thCateItem, index) in subCategoryList"
            :key="index"
          >
            <view class="sub-category">
              <view
                class="sub-category-item"
                :data-categoryid="thCateItem.categoryId"
                :data-parentid="thCateItem.parentId"
                @tap="toCatePage"
              >
                <image
                  :src="util.checkFileUrl(thCateItem.pic)"
                  class="more-pic"
                  mode="widthFix"
                />
                <text>{{ thCateItem.categoryName }}</text>
              </view>
            </view>
          </block>
        </view>
        <view
          v-else
          class="cont-item empty"
        >
          该分类下暂无子分类~
        </view>
        <!-- 当前分类下的部分商品列表 -->
        <view class="cate-prod-list">
          <view
            v-if="prodList.length"
            class="cate-prod-wrap"
          >
            <view
              v-for="(prod, idx) in prodList"
              :key="idx"
              class="cate-prod-item"
              :data-prodid="prod.prodId"
              @tap="toProdPage"
            >
              <image
                :src="util.checkFileUrl(prod.pic)"
                class="cate-prod-img"
                mode="aspectFill"
              />
              <view class="cate-prod-info">
                <view class="cate-prod-name">
                  {{ prod.prodName }}
                </view>
                <view v-if="prod.brief" class="cate-prod-brief">
                  {{ prod.brief }}
                </view>
                <view class="cate-prod-price">
                  {{ prod.price }}
                </view>
              </view>
            </view>
          </view>
          <view
            v-else
            class="cont-item empty"
          >
            该分类下暂无农品~
          </view>
        </view>
      </scroll-view>
      <!-- 右侧内容end -->
    </view>
  </view>
</template>

<script setup>
import util from '@/utils/util.js'
const categoryList = ref([])
const subCategoryList = ref([])
const categoryImg = ref('')
const parentId = ref('')
const prodList = ref([])
/**
 * 生命周期函数--监听页面加载
 */
onLoad(() => {
  // 加载分类列表
  http.request({
    url: '/category/categoryInfo',
    method: 'GET',
    data: {
      parentId: ''
    }
  })
    .then(({ data }) => {
      categoryImg.value = data[0].pic
      categoryList.value = data
      getProdList(data[0].categoryId)
      parentId.value = categoryList.value[0].categoryId
    })
})

const selIndex = ref(0)
/**
 * 分类点击事件
 */
const onMenuTab = (e) => {
  const index = e.currentTarget.dataset.index
  getProdList(categoryList.value[index].categoryId)
  parentId.value = categoryList.value[index].categoryId
  categoryImg.value = categoryList.value[index].pic
  selIndex.value = index
}

/**
 * 跳转搜索页
 */
const toSearchPage = () => {
  uni.navigateTo({
    url: '/pages/search-page/search-page'
  })
}

const getProdList = (categoryId) => {
  // 加载分类列表
  http.request({
    url: '/category/categoryInfo',
    method: 'GET',
    data: {
      parentId: categoryId
    }
  })
    .then(({ data }) => {
      subCategoryList.value = data
    })
  // 加载当前分类下的商品列表（取前 10 条）
  http.request({
    url: '/prod/pageProd',
    method: 'GET',
    data: {
      categoryId,
      current: 1,
      size: 10,
      sort: 0,
      isAllProdType: true
    }
  })
    .then(({ data }) => {
      prodList.value = data.records || []
    })
}

const toProdPage = (e) => {
  const prodid = e.currentTarget.dataset.prodid
  if (prodid) {
    uni.navigateTo({
      url: '/pages/prod/prod?prodid=' + prodid
    })
  }
}

/**
 * 跳转子分类商品页面
 */
const toCatePage = (e) => {
  const { categoryid } = e.currentTarget.dataset
  uni.navigateTo({
    url: `/pages/sub-category/sub-category?parentId=${parentId.value}&categoryId=${categoryid}`
  })
}

</script>

<style scoped lang="scss">
@use "./category.scss";
</style>
