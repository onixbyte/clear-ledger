<script setup lang="ts"></script>

<template>
  <div class="dashboard-layout">
    <el-container class="dashboard-wrapper">
      <el-header class="dashboard-header">
        <span class="header-title">Clear Ledger</span>
      </el-header>
      <el-container class="dashboard-content-wrapper">
        <el-aside width="200px">Aside</el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

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
