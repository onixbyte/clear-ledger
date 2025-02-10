<template>
  <div class="ledger-page-wrapper">
    <el-page-header icon="" class="ledger-header">

      <template #title>
        <span class="ledger-header-title">{{ ledger.name }}</span>
      </template>

      <template #content>
        <div class="ledger-header-content">
          <el-tag>{{ role }}</el-tag>
        </div>
      </template>

      <template #extra>
        <el-button
          type="primary"
          v-show="ledger.role == 'owner'">
          编辑账本
        </el-button>
        <el-button type="danger">退出账本</el-button>
      </template>
    </el-page-header>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"
import { useLedgerStore } from "@/store"

const route = useRoute()

const ledgerStore = useLedgerStore()

const ledgerId = route.params.ledgerId

const ledger = ledgerStore.ledgers
  .filter((_ledger) => _ledger.id == ledgerId)[0]

const role = computed(() => ledger.role == "owner" ? "管理员" : "成员")

onMounted(() => {})
</script>

<style scoped lang="less">
.ledger-page-wrapper {
  .ledger-header {
    .ledger-header-content {
      font-size: 14px;
    }
  }
}
</style>
