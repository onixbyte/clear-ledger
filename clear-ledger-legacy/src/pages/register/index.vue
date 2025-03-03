<template>
  <div class="register-form-wrapper">
    <el-form class="register-form" label-position="right" label-width="80">
      <h1 class="register-form-title">家庭账本</h1>
      <el-form-item label="用户名">
        <el-input v-model="username" />
      </el-form-item>
      <el-form-item label="电子邮箱">
        <el-input v-model="emailAddress" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="password" />
      </el-form-item>
      <div class="register-form-footer">
        <router-link :to="{ name: `Login` }">
          已有账号？立即登录！
        </router-link>
        <el-button
          type="primary"
          @click="register(username, emailAddress, password)">
          注册
        </el-button>
        <el-button type="danger" @click="clearRegisterForm">清空</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useRouter } from "vue-router"
import moment from "moment"
import { ElNotification } from "element-plus"
import * as AuthApi from "@/api/auth"

const username = ref<string>("")
const emailAddress = ref<string>("")
const password = ref<string>("")

const router = useRouter()

const register = async (
  username: string,
  emailAddress: string,
  password: string
) => {
  const response = await AuthApi.register(username, emailAddress, password)
  if (response.status >= 200 && response.status < 300) {
    ElNotification({
      type: "success",
      message: "注册成功！",
      duration: moment.duration({ seconds: 3 }).asMilliseconds()
    })
    await router.push({ name: "Login" })
  }
}

const clearRegisterForm = () => {
  username.value = ""
  emailAddress.value = ""
  password.value = ""
}
</script>

<style scoped lang="less">
.register-form-wrapper {
  display: flex;
  height: 100vh;
  width: 100vw;
  align-content: center;
  justify-content: center;
  align-items: center;

  .register-form {
    width: 400px;

    .register-form-title {
      text-align: center;
    }

    .register-form-footer {
      display: flex;
      justify-content: end;
      align-items: baseline;
    }
  }
}
</style>
