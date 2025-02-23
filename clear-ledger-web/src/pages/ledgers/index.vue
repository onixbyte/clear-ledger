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
          v-show="ledger.role == 'owner'"
          @click="editLedger">
          编辑账本
        </el-button>
        <el-button type="danger">退出账本</el-button>
      </template>
    </el-page-header>

    <edit-ledger-dialogue
      v-model="isEditLedgerDialogueVisible"
      :ledger="ledger" />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue"
import { useRoute } from "vue-router"
import { useLedgerStore } from "@/store"
import EditLedgerDialogue from "@/components/edit-ledger-dialogue.vue"

const route = useRoute()

const ledgerStore = useLedgerStore()

const ledgerId = computed(() => route.params.ledgerId as string)

const ledger = computed(() => ledgerStore.ledgers[ledgerId.value])

const isEditLedgerDialogueVisible = ref<boolean>(false)

const role = computed(() => (ledger.value.role == "owner" ? "管理员" : "成员"))

const editLedger = () => {
  isEditLedgerDialogueVisible.value = true
}
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
