<template>
  <el-dialog v-model="isDialogueVisible" title="加入账本" width="400">
    <el-form :model="joinLedgerForm" label-width="60">
      <el-form-item label="账本 ID">
        <el-input v-model="joinLedgerForm.ledgerId"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button
          type="primary"
          @click="onJoinLedger(joinLedgerForm.ledgerId)">
          提交
        </el-button>
        <el-button type="danger" @click="onCancel">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue"
import * as LedgerApi from "@/api/ledger"
import { useLedgerStore } from "@/store"
import { AxiosError } from "axios"
import { ElMessage } from "element-plus"
import { CommonErrorResponse } from "@/types"

const ledgerStore = useLedgerStore()

const isDialogueVisible = defineModel<boolean>({
  required: true,
})

const joinLedgerForm = ref<{
  ledgerId: string
}>({
  ledgerId: "",
})

const closeDialogue = () => {
  isDialogueVisible.value = false
}

const onJoinLedger = (ledgerId: string) => {
  LedgerApi.joinLedger(ledgerId)
    .then((ledger) => {
      ledgerStore.ledgers.push(ledger)
      ElMessage.success(`恭喜您已成功加入账本 ${ledger.name}`)
    })
    .catch((error) => {
      if (error instanceof AxiosError) {
        const { data }: { data: CommonErrorResponse } = error.response!
        ElMessage.error(data.message)
      } else {
        ElMessage.error("未知错误，请稍后再试")
      }
    })
  closeDialogue()
}

const onCancel = () => {
  joinLedgerForm.value.ledgerId = ""
  closeDialogue()
}
</script>

<style scoped lang="less"></style>
