<!--<template>-->
<!--  <div class="mod-home">-->
<!--    <p>一个基于spring boot、spring oauth2.0、mybatis、redis的轻量级、前后端分离、拥有完整sku和下单流程的完全开源商城</p>-->
<!--    <p>&nbsp;</p>-->
<!--    <p>该项目仅供学习参考、可供个人学习使用、如需商用联系作者进行授权，否则必将追究法律责任</p>-->
<!--    <p>&nbsp;</p>-->
<!--    <h2>前言</h2>-->
<!--    <p>-->
<!--      <code>mall4j商城</code>项目致力于为中小企业打造一个完整、易于维护的开源的电商系统，采用现阶段流行技术实现。后台管理系统包含商品管理、订单管理、运费模板、规格管理、会员管理、运营管理、内容管理、统计报表、权限管理、设置等模块。-->
<!--    </p>-->
<!--    <p>&nbsp;</p>-->
<!--    <h2>技术选型</h2>-->
<!--    <figure>-->
<!--      <table-->
<!--        border="1"-->
<!--        cellspacing="0"-->
<!--        cellpadding="5px"-->
<!--      >-->
<!--        <thead>-->
<!--          <tr>-->
<!--            <th>技术</th>-->
<!--            <th>版本</th>-->
<!--            <th>说明</th>-->
<!--          </tr>-->
<!--        </thead>-->
<!--        <tbody>-->
<!--          <tr>-->
<!--            <td>Spring Boot</td>-->
<!--            <td>3.0.4</td>-->
<!--            <td>MVC核心框架</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>MyBatis</td>-->
<!--            <td>3.5.0</td>-->
<!--            <td>ORM框架</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>MyBatisPlus</td>-->
<!--            <td>3.5.3.1</td>-->
<!--            <td>基于mybatis，使用lambda表达式的</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>Swagger-UI</td>-->
<!--            <td>4.0.0</td>-->
<!--            <td>文档生产工具</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>redisson</td>-->
<!--            <td>3.19.3</td>-->
<!--            <td>对redis进行封装、集成分布式锁等</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>hikari</td>-->
<!--            <td>3.2.0</td>-->
<!--            <td>数据库连接池</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>log4j2</td>-->
<!--            <td>2.17.2</td>-->
<!--            <td>更快的log日志工具</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>lombok</td>-->
<!--            <td>1.18.8</td>-->
<!--            <td>简化对象封装工具</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>hutool</td>-->
<!--            <td>5.8.15</td>-->
<!--            <td>更适合国人的java工具集</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>xxl-job</td>-->
<!--            <td>2.3.1</td>-->
<!--            <td>定时任务</td>-->
<!--          </tr>-->
<!--        </tbody>-->
<!--      </table>-->
<!--    </figure>-->
<!--    <p>&nbsp;</p>-->
<!--    <h2>部署教程</h2>-->
<!--    <p>&nbsp;</p>-->
<!--    <h3>1.开发环境</h3>-->
<!--    <figure>-->
<!--      <table-->
<!--        border="1"-->
<!--        cellspacing="0"-->
<!--        cellpadding="5px"-->
<!--      >-->
<!--        <thead>-->
<!--          <tr>-->
<!--            <th>工具</th>-->
<!--            <th>版本</th>-->
<!--          </tr>-->
<!--        </thead>-->
<!--        <tbody>-->
<!--          <tr>-->
<!--            <td>jdk</td>-->
<!--            <td>17</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>mysql</td>-->
<!--            <td>5.7+</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>redis</td>-->
<!--            <td>3.2+</td>-->
<!--          </tr>-->
<!--        </tbody>-->
<!--      </table>-->
<!--    </figure>-->
<!--    <h3>2.启动</h3>-->
<!--    <ul>-->
<!--      <li>推荐使用idea，安装lombok插件，使用idea导入maven项目</li>-->
<!--      <li>-->
<!--        将shop.sql导入到mysql中，修改-->
<!--        <code>application-dev.yml</code>更改 datasource.url、user、password-->
<!--      </li>-->
<!--      <li>启动redis</li>-->
<!--      <li>-->
<!--        通过-->
<!--        <code>WebApplication</code>启动项目后台接口，-->
<!--        <code>ApiApplication</code> 启动项目前端接口-->
<!--      </li>-->
<!--    </ul>-->
<!--    <p>&nbsp;</p>-->
<!--  </div>-->
<!--</template>-->

<!--<style lang="scss" scoped>-->
<!--.mod-home {-->
<!--  line-height: 1.5;-->
<!--}-->
<!--</style>-->
<template>
  <div class="dashboard-home">
    <section class="top-section">
      <div class="hero-bg" />

      <div class="hero-content">
        <div class="hero-left">
          <div class="hero-title">
            <span class="hero-greet">
              {{ greetingText }}
            </span>
            <span class="hero-name">
              {{ userStore.name || '管理员' }}
            </span>
          </div>
          <div class="hero-sub">
            欢迎回到工作台。这里可以快速管理农产品、订单、库存与系统配置。
          </div>

          <div class="hero-chips">
            <div class="chip">
              <span class="chip-label">当前时间</span>
              <span class="chip-value">{{ nowText }}</span>
            </div>
            <div class="chip">
              <span class="chip-label">环境</span>
              <span class="chip-value">{{ envText }}</span>
            </div>
            <div class="chip">
              <span class="chip-label">接口前缀</span>
              <span class="chip-value chip-value--mono">
                {{ baseApiText }}
              </span>
            </div>
          </div>

          <div class="hero-actions">
            <el-button
              type="primary"
              @click="goTo('/prodInfo')"
            >
              发布农产品
            </el-button>
            <el-button @click="goTo('/order/order')">
              查看订单
            </el-button>
            <el-button
              type="primary"
              link
              @click="refresh"
            >
              刷新统计
            </el-button>
          </div>
        </div>
      </div>
    </section>

    <section class="stats-section">
      <el-row :gutter="12">
        <el-col
          :xs="12"
          :md="6"
        >
          <div
            class="stat-card stat-card--purple"
            @click="goTo('/order/order')"
          >
            <div class="stat-icon">
              <el-icon :size="22">
                <Document />
              </el-icon>
            </div>
            <div class="stat-main">
              <div class="stat-label">
                订单总数
              </div>
              <div class="stat-value">
                {{ stats.orderTotal }}
              </div>
            </div>
          </div>
        </el-col>

        <el-col
          :xs="12"
          :md="6"
        >
          <div
            class="stat-card stat-card--pink"
            @click="goTo('/order/order')"
          >
            <div class="stat-icon">
              <el-icon :size="22">
                <Calendar />
              </el-icon>
            </div>
            <div class="stat-main">
              <div class="stat-label">
                今日订单
              </div>
              <div class="stat-value">
                {{ stats.todayOrderCount }}
              </div>
            </div>
          </div>
        </el-col>

        <el-col
          :xs="12"
          :md="6"
        >
          <div
            class="stat-card stat-card--blue"
            @click="goTo('/prod/prodList')"
          >
            <div class="stat-icon">
              <el-icon :size="22">
                <Goods />
              </el-icon>
            </div>
            <div class="stat-main">
              <div class="stat-label">
                农产品总数
              </div>
              <div class="stat-value">
                {{ stats.prodTotal }}
              </div>
            </div>
          </div>
        </el-col>

        <el-col
          :xs="12"
          :md="6"
        >
          <div
            class="stat-card stat-card--green"
            @click="goTo('/user/user')"
          >
            <div class="stat-icon">
              <el-icon :size="22">
                <User />
              </el-icon>
            </div>
            <div class="stat-main">
              <div class="stat-label">
                用户总数
              </div>
              <div class="stat-value">
                {{ stats.userTotal }}
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </section>

    <section class="bottom-section">
      <el-row :gutter="16">
        <el-col
          :xs="24"
          :md="24"
        >
          <el-card
            class="card"
            shadow="hover"
          >
            <template #header>
              <span class="card-title">快捷入口</span>
            </template>

            <div class="quick-grid">
              <div
                class="quick-item"
                @click="goTo('/prodInfo')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Plus />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    发布农产品
                  </div>
                  <div class="quick-desc">
                    新增农产品 / 规格 / 库存
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/order/order')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <List />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    订单管理
                  </div>
                  <div class="quick-desc">
                    查询 / 发货 / 售后
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/prod/prodList')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Goods />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    农产品列表
                  </div>
                  <div class="quick-desc">
                    上下架 / 分类 / 标签
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/prod/category')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Menu />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    分类管理
                  </div>
                  <div class="quick-desc">
                    维护类目结构
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/shop/transport')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Van />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    运费模板
                  </div>
                  <div class="quick-desc">
                    按地区 / 重量配置
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/admin/indexImg')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Picture />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    轮播图
                  </div>
                  <div class="quick-desc">
                    首页运营位配置
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/sys/menu')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <Setting />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    菜单权限
                  </div>
                  <div class="quick-desc">
                    角色 / 菜单 / 权限点
                  </div>
                </div>
              </div>

              <div
                class="quick-item"
                @click="goTo('/sys/user')"
              >
                <div class="quick-icon">
                  <el-icon :size="22">
                    <UserFilled />
                  </el-icon>
                </div>
                <div class="quick-text">
                  <div class="quick-title">
                    系统用户
                  </div>
                  <div class="quick-desc">
                    后台账号管理
                  </div>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col
          :xs="24"
          :md="24"
          class="stack-gap"
        >
          <el-card
            class="card"
            shadow="hover"
          >
            <template #header>
              <span class="card-title">系统概览</span>
            </template>

            <div class="sys">
              <div class="sys-tip-top">
                <div class="sys-tip-title">
                  运营提醒
                </div>
                <div class="sys-tip-item">
                  <span class="sys-tip-dot" />
                  <span class="sys-tip-text">建议每天核对“今日订单”并及时发货</span>
                </div>
                <div class="sys-tip-item">
                  <span class="sys-tip-dot" />
                  <span class="sys-tip-text">新品上架后可在“轮播图”设置首页推荐位</span>
                </div>
              </div>

              <div class="sys-divider" />

              <div class="sys-row">
                <span class="sys-k">前端</span>
                <span class="sys-v">Vue 3 · Vite · Element Plus</span>
              </div>
              <div class="sys-row">
                <span class="sys-k">后端</span>
                <span class="sys-v">Spring Boot</span>
              </div>
              <div class="sys-row">
                <span class="sys-k">接口前缀</span>
                <span class="sys-v">
                  {{ baseApiText }}
                </span>
              </div>

              <div class="sys-tip">
                统计卡片依赖：
                <code>/order/order/page</code>、
                <code>/prod/prod/page</code>、
                <code>/admin/user/page</code>。
              </div>

              <div class="sys-actions">
                <el-button
                  type="primary"
                  size="small"
                  @click="goTo('/order/order')"
                >
                  查看订单
                </el-button>
                <el-button
                  size="small"
                  @click="goTo('/prod/prodList')"
                >
                  查看商品
                </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </section>
  </div>
</template>

<script setup>
import {
  Calendar,
  Document,
  Goods,
  List,
  Menu,
  Picture,
  Plus,
  Setting,
  User,
  UserFilled,
  Van
} from '@element-plus/icons-vue'

const userStore = useUserStore()
const router = useRouter()

const nowText = ref('')
let timer = null

const envText = computed(() => {
  return import.meta.env.MODE === 'development' ? '开发环境' : '生产环境'
})

const baseApiText = computed(() => {
  return import.meta.env.VITE_APP_BASE_API || '-'
})

const greetingText = computed(() => {
  const h = new Date().getHours()
  if (h < 6) return '夜深了，'
  if (h < 9) return '早上好，'
  if (h < 12) return '上午好，'
  if (h < 14) return '中午好，'
  if (h < 18) return '下午好，'
  if (h < 22) return '晚上好，'
  return '夜深了，'
})

const stats = reactive({
  orderTotal: '-',
  prodTotal: '-',
  userTotal: '-',
  todayOrderCount: '-'
})

function formatNow () {
  const d = new Date()
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hh = String(d.getHours()).padStart(2, '0')
  const mm = String(d.getMinutes()).padStart(2, '0')
  const ss = String(d.getSeconds()).padStart(2, '0')
  return `${y}-${m}-${day} ${hh}:${mm}:${ss}`
}

function getTodayStartEnd () {
  const d = new Date()
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  return [
    `${y}-${m}-${day} 00:00:00`,
    `${y}-${m}-${day} 23:59:59`
  ]
}

function goTo (path) {
  router.push(path)
}

function loadStats () {
  const baseParams = { current: 1, size: 1 }

  http({
    url: http.adornUrl('/order/order/page'),
    method: 'get',
    params: http.adornParams(baseParams, false)
  }).then(({ data }) => {
    stats.orderTotal = data?.total ?? 0
  }).catch(() => {
    stats.orderTotal = 0
  })

  http({
    url: http.adornUrl('/prod/prod/page'),
    method: 'get',
    params: http.adornParams(baseParams)
  }).then(({ data }) => {
    stats.prodTotal = data?.total ?? 0
  }).catch(() => {
    stats.prodTotal = 0
  })

  http({
    url: http.adornUrl('/admin/user/page'),
    method: 'get',
    params: http.adornParams(baseParams)
  }).then(({ data }) => {
    stats.userTotal = data?.total ?? 0
  }).catch(() => {
    stats.userTotal = 0
  })

  const [startTime, endTime] = getTodayStartEnd()
  http({
    url: http.adornUrl('/order/order/page'),
    method: 'get',
    params: http.adornParams({
      current: 1,
      size: 1,
      startTime,
      endTime
    }, false)
  }).then(({ data }) => {
    stats.todayOrderCount = data?.total ?? 0
  }).catch(() => {
    stats.todayOrderCount = 0
  })
}

function refresh () {
  loadStats()
}

onMounted(() => {
  nowText.value = formatNow()
  timer = setInterval(() => {
    nowText.value = formatNow()
  }, 1000)

  loadStats()
})

onBeforeUnmount(() => {
  if (timer) clearInterval(timer)
})
</script>

<style lang="scss" scoped>
.dashboard-home {
  padding: 0 16px 22px;
  min-height: 100%;
  background: #f1f4f5;
}

.top-section {
  position: relative;
  margin: 10px 0 16px;
}

.hero-bg {
  position: absolute;
  inset: 0;
  border-radius: 18px;
  background: radial-gradient(circle at 0 0, #7cb342 0, rgba(124, 179, 66, 0) 52%),
  radial-gradient(circle at 100% 0, #f9a825 0, rgba(249, 168, 37, 0) 58%),
  linear-gradient(135deg, #1b5e20 0%, #2e7d32 35%, #2e7d32 55%, #4e342e 100%);
  opacity: 0.96;
}

.hero-content {
  position: relative;
  z-index: 1;
  padding: 18px 18px 16px;
  border-radius: 18px;
  color: #fff;
  display: flex;
  align-items: flex-start;
}

.hero-left {
  width: 100%;
}

.hero-title {
  font-size: 22px;
  font-weight: 800;
}

.hero-greet {
  opacity: 0.9;
}

.hero-name {
  margin-left: 6px;
  color: #fff2b2;
}

.hero-sub {
  margin-top: 8px;
  font-size: 13px;
  opacity: 0.85;
  max-width: 640px;
}

.hero-chips {
  margin-top: 14px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.chip {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.12);
  backdrop-filter: blur(8px);
  display: inline-flex;
  align-items: center;
  gap: 6px;
}

.chip-label {
  font-size: 11px;
  opacity: 0.7;
}

.chip-value {
  font-size: 11px;
  font-weight: 600;
}

.chip-value--mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono',
  'Courier New', monospace;
  max-width: 180px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.hero-actions {
  margin-top: 14px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.stats-section {
  margin: 0 0 16px;
}

.stat-card {
  border-radius: 14px;
  padding: 12px;
  background: #ffffff;
  border: 1px solid #ebeef5;
  display: flex;
  gap: 12px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 26px rgba(0, 0, 0, 0.12);
  border-color: rgba(46, 125, 50, 0.35);
}

.stat-icon {
  width: 38px;
  height: 38px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-card--purple .stat-icon {
  background: linear-gradient(135deg, #b37feb 0%, #9254de 100%);
}

.stat-card--pink .stat-icon {
  background: linear-gradient(135deg, #ff85c0 0%, #ff4d4f 100%);
}

.stat-card--blue .stat-icon {
  background: linear-gradient(135deg, #69c0ff 0%, #40a9ff 100%);
}

.stat-card--green .stat-icon {
  background: linear-gradient(135deg, #73d13d 0%, #52c41a 100%);
}

.stat-label {
  font-size: 11px;
  color: #909399;
}

.stat-value {
  margin-top: 6px;
  font-size: 20px;
  font-weight: 900;
  color: #1a1a2e;
}

.bottom-section {
  margin-bottom: 0;
}

.stack-gap {
  margin-top: 16px;
}

.card {
  border-radius: 14px;
  border: none;
  background: #ffffff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
}

:deep(.el-card__header) {
  padding: 12px 14px;
  border-bottom: 1px solid #ebeef5;
}

:deep(.el-card__body) {
  padding: 14px;
}

.card-title {
  font-size: 15px;
  font-weight: 800;
  color: #1a1a2e;
}

/* 快捷入口九宫格 */
.quick-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.quick-item {
  background: #f8f9fc;
  border-radius: 12px;
  padding: 12px;
  display: flex;
  gap: 12px;
  cursor: pointer;
  transition: background 0.2s, transform 0.2s, box-shadow 0.2s;
}

.quick-item:hover {
  background: #eaf4ea;
  transform: translateY(-1px);
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.07);
}

.quick-icon {
  width: 38px;
  height: 38px;
  border-radius: 12px;
  background: rgba(46, 125, 50, 0.1);
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
  justify-content: center;
}

.quick-title {
  font-size: 14px;
  font-weight: 800;
  color: #303133;
}

.quick-desc {
  margin-top: 3px;
  font-size: 12px;
  color: #909399;
}

/* 系统信息卡片 */
.sys-row {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  padding: 8px 0;
}

.sys-k {
  font-size: 12px;
  color: #909399;
}

.sys-v {
  font-size: 12px;
  color: #303133;
  font-weight: 700;
  text-align: right;
  word-break: break-all;
}

.sys-divider {
  height: 1px;
  background: #ebeef5;
  margin: 10px 0 12px;
}

.sys-tip-top {
  margin-bottom: 2px;
}

.sys-tip-title {
  font-size: 13px;
  font-weight: 900;
  color: #1a1a2e;
  margin-bottom: 10px;
}

.sys-tip-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 0;
}

.sys-tip-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--el-color-primary);
  flex: none;
}

.sys-tip-text {
  font-size: 12px;
  color: #606266;
  line-height: 1.5;
}

.sys-tip {
  font-size: 12px;
  color: #606266;
  line-height: 1.6;
}

.sys-actions {
  margin-top: 12px;
  display: flex;
  gap: 8px;
}

@media (max-width: 768px) {
  .quick-grid {
    grid-template-columns: 1fr;
  }
}
</style>
