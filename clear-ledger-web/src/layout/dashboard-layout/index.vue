<template>
  <div class="dashboard-layout">
    <el-container class="dashboard-wrapper">
      <el-header class="dashboard-header">
        <span class="header-title">Clear Ledger</span>
      </el-header>
      <el-container class="dashboard-content-wrapper">
        <el-aside width="200px">
          <el-menu>
            <el-menu-item></el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue"
import { useUserStore } from "@/store"


const userStore = useUserStore()

onMounted(async () => {

})
</script>

<style scoped lang="less">
.dashboard-layout {
  .dashboard-wrapper {
    .dashboard-header {
      display: flex;
      align-items: center;
      background-color: #888;

      .header-title {
        font-size: 18px;
      }
    }
  }
}
</style>
