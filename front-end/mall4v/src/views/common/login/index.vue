<template>
  <div class="login-page">
    <div class="login-card">
      <div class="login-header">
        <div class="brand-mark">种</div>
        <h1 class="title">农产品种子商城管理后台</h1>
        <p class="subtitle">统一管理种子、农资、农产品、订单与库存</p>
      </div>
      <el-form
        ref="dataFormRef"
        :model="dataForm"
        :rules="dataRule"
        class="login-form"
        @keyup.enter="dataFormSubmit()"
      >
        <el-form-item prop="userName">
          <el-input
            v-model="dataForm.userName"
            placeholder="请输入账号"
            size="large"
            :prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="dataForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            show-password
            :prefix-icon="Lock"
            @keyup.enter="dataFormSubmit()"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="submit-btn"
            :loading="loading"
            @click="dataFormSubmit()"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <Verify
      ref="verifyRef"
      :captcha-type="'blockPuzzle'"
      :img-size="{ width: '400px', height: '200px' }"
      @success="login"
    />
  </div>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { encrypt } from '@/utils/crypto'
import { getUUID } from '@/utils'
import Verify from '@/components/verifition/Verify.vue'
import cookie from 'vue-cookies'

const router = useRouter()

const dataForm = ref({
  userName: '',
  password: '',
  uuid: '',
  captcha: ''
})

const dataRule = {
  userName: [{ required: true, message: '帐号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  captcha: [{ required: true, message: '验证码不能为空', trigger: 'blur' }]
}

const verifyRef = ref(null)
const dataFormRef = ref(null)
const loading = ref(false)

const dataFormSubmit = () => {
  dataFormRef.value?.validate((valid) => {
    if (valid) {
      verifyRef.value?.show()
    }
  })
}

const login = (verifyResult) => {
  if (loading.value) return
  loading.value = true
  http({
    url: http.adornUrl('/adminLogin'),
    method: 'post',
    data: http.adornData({
      userName: dataForm.value.userName,
      passWord: encrypt(dataForm.value.password),
      captchaVerification: verifyResult.captchaVerification
    })
  })
    .then(({ data }) => {
      cookie.set('Authorization', data.accessToken)
      router.replace({ name: 'home' })
    })
    .catch(() => {
      loading.value = false
    })
}

const getCaptcha = () => {
  dataForm.value.uuid = getUUID()
}

const handerKeyup = (e) => {
  const keycode = e.which || e.keyCode
  if (keycode === 13) {
    dataFormSubmit()
  }
}

onMounted(() => {
  getCaptcha()
  document.addEventListener('keyup', handerKeyup)
})

onBeforeUnmount(() => {
  document.removeEventListener('keyup', handerKeyup)
})
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1b5e20 0%, #2e7d32 45%, #4e342e 100%);
  position: relative;
  overflow: hidden;
  padding: 16px;
}

.login-page::before {
  content: '';
  position: absolute;
  inset: -120px;
  background: radial-gradient(
      circle at 15% 20%,
      rgba(255, 255, 255, 0.18) 0%,
      rgba(255, 255, 255, 0) 52%
    ),
    radial-gradient(
      circle at 85% 30%,
      rgba(255, 241, 179, 0.18) 0%,
      rgba(255, 241, 179, 0) 48%
    ),
    radial-gradient(
      circle at 35% 85%,
      rgba(124, 179, 66, 0.18) 0%,
      rgba(124, 179, 66, 0) 50%
    );
  filter: blur(2px);
  pointer-events: none;
}

.login-card {
  position: relative;
  width: 100%;
  max-width: 420px;
  padding: 36px 34px;
  background: rgba(255, 255, 255, 0.98);
  border-radius: 18px;
  box-shadow: 0 22px 70px rgba(0, 0, 0, 0.28);
  border: 1px solid rgba(255, 255, 255, 0.55);
}

.login-header {
  text-align: center;
  margin-bottom: 26px;

  .brand-mark {
    width: 54px;
    height: 54px;
    border-radius: 16px;
    margin: 0 auto 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 22px;
    font-weight: 900;
    color: #ffffff;
    background: linear-gradient(135deg, #2e7d32 0%, #7cb342 45%, #f9a825 100%);
    box-shadow: 0 10px 24px rgba(46, 125, 50, 0.28);
  }

  .title {
    font-size: 22px;
    color: #1a1a1a;
    margin: 0 0 8px;
    letter-spacing: 0.5px;
  }

  .subtitle {
    font-size: 14px;
    color: #606266;
    line-height: 1.6;
  }
}

.login-form :deep(.el-form-item) {
  margin-bottom: 18px;
}

.login-form :deep(.el-form-item:last-child) {
  margin-bottom: 0;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 10px;
  padding: 0 16px;
  padding-left: 18px;
  min-height: 46px;
  background: #ffffff;
  box-shadow: 0 0 0 1px rgba(46, 125, 50, 0.18) inset;
  transition: box-shadow 0.2s, background 0.2s;
}

.login-form :deep(.el-input__inner) {
  padding-left: 2px;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(46, 125, 50, 0.28) inset;
}

.login-form :deep(.el-input__prefix-inner) {
  margin-right: 12px;
}

.login-form :deep(.el-input__suffix-inner) {
  margin-left: 0;
  display: flex;
  align-items: center;
  gap: 10px;
}

.login-form :deep(.el-input__icon) {
  color: #8c8c8c;
}

.login-form :deep(.el-input__icon:hover) {
  color: var(--el-color-primary);
}

.login-form :deep(.el-input__password) {
  opacity: 1;
}

.submit-btn {
  width: 100%;
  height: 46px;
  border-radius: 10px;
  font-size: 16px;
  font-weight: 800;
  border: none;
  background: linear-gradient(135deg, #2e7d32 0%, #7cb342 70%, #8bc34a 100%);
  box-shadow: 0 10px 22px rgba(46, 125, 50, 0.22);
}

.submit-btn:hover {
  background: linear-gradient(135deg, #256628 0%, #6ca83a 70%, #7cb342 100%);
  box-shadow: 0 12px 26px rgba(46, 125, 50, 0.26);
}

@media (max-width: 420px) {
  .login-card {
    padding: 28px 20px;
    border-radius: 16px;
  }
}
</style>
