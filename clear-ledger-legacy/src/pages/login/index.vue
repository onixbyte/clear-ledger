<template>
  <div class="login-form-wrapper">
    <el-form class="login-form" label-position="right" label-width="60">
      <h1 class="login-form-title">家庭账本</h1>
      <el-form-item label="用户名">
        <el-input v-model="username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="password" />
      </el-form-item>
      <div class="login-form-footer">
        <router-link :to="{ name: `Register` }">
          没有账号？立即注册！
        </router-link>
        <el-button type="primary" @click="login(username, password)">
          登录
        </el-button>
        <el-button type="danger" @click="clearLoginForm">清空</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import * as AuthApi from "@/api/auth"
import { useUserStore } from "@/store"
import router from "@/router"

const userStore = useUserStore()

const username = ref<string>("")
const password = ref<string>("")

const clearLoginForm = () => {
  username.value = ""
  password.value = ""
}

const login = async (username: string, password: string) => {
  const response = await AuthApi.login(username, password)
  if (response) {
    userStore.authorisation = response.authorisation
    userStore.user = response.user
  }
  if (userStore.isAuthenticated) {
    await router.push({ name: "Home" })
  }
}
</script>

<style scoped lang="less">
.login-form-wrapper {
  display: flex;
  height: 100vh;
  width: 100vw;
  align-content: center;
  justify-content: center;
  align-items: center;

  .login-form {
    width: 400px;

    .login-form-title {
      text-align: center;
    }

    .login-form-footer {
      display: flex;
      justify-content: end;
      align-items: baseline;
    }
  }
}
</style>
