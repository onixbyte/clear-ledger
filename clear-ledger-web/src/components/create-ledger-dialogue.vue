<template>
  <el-dialog v-model="isDialogueVisible" title="加入账本" width="400">
    <el-form :model="createLedgerForm" label-width="80">
      <el-form-item label="账本名称">
        <el-input v-model="createLedgerForm.name"></el-input>
      </el-form-item>
      <el-form-item label="账本描述">
        <el-input
          type="textarea"
          :rows="1"
          autosize
          v-model="createLedgerForm.description" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" @click="onCreateLedger(createLedgerForm)">
          提交
        </el-button>
        <el-button type="danger" @click="onCancel">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { CommonErrorResponse, CreateLedgerRequest } from "@/types"
import * as LedgerApi from "@/api/ledger"
import { useLedgerStore } from "@/store"
import { AxiosError } from "axios"
import { ElMessage, ElNotification } from "element-plus"

const ledgerStore = useLedgerStore()

const isDialogueVisible = defineModel<boolean>()

const createLedgerForm = ref<CreateLedgerRequest>({
  name: "",
  description: "",
})

const closeDialogue = () => {
  isDialogueVisible.value = false
}

const clearData = () => {
  createLedgerForm.value.name = ""
  createLedgerForm.value.description = ""
}

const onCreateLedger = (request: CreateLedgerRequest) => {
  LedgerApi.createLedger(request)
    .then((ledger) => {
      ledgerStore.ledgers[ledger.id] = ledger
      ElNotification.success({
        title: "账本创建成功！",
        message: `恭喜你成功创建账本 ${ledger.name}，ID 为 ${ledger.id} ，快邀请他人来加入吧！`,
        duration: 0
      })
    })
    .catch((error) => {
      if (error instanceof AxiosError) {
        const { data }: { data: CommonErrorResponse } = error.response!
        ElMessage.error(data.message)
      } else {
        ElMessage.error("发生未知错误，请稍后再试")
      }
    })
    .then(() => {
      clearData()
      closeDialogue()
    })
}

const onCancel = () => {
  clearData()
  closeDialogue()
}
</script>

<style scoped lang="less"></style>