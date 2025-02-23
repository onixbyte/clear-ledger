<template>
  <el-dialog v-model="isDialogueVisible" title="编辑账本" width="400" destroy-on-close>
    <el-form :model="editLedgerForm" label-width="80">
      <el-form-item label="账本名称">
        <el-input v-model="editLedgerForm.name"></el-input>
      </el-form-item>
      <el-form-item label="账本描述">
        <el-input
          type="textarea"
          :rows="1"
          autosize
          v-model="editLedgerForm.description" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button type="primary" @click="onEditLedger(editLedgerForm)">
          提交
        </el-button>
        <el-button type="danger" @click="onCancel">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { EditLedgerRequest, Ledger } from "@/types"
import * as LedgerApi from "@/api/ledger"
import { useLedgerStore } from "@/store"

const ledgerStore = useLedgerStore()

const { ledger } = defineProps<{ ledger: Ledger }>()

const isDialogueVisible = defineModel<boolean>()

const editLedgerForm = ref<EditLedgerRequest>({
  id: ledger.id,
  name: ledger.name,
  description: ledger.description,
})

const closeDialogue = () => {
  isDialogueVisible.value = false
}

const onCancel = () => {
  closeDialogue()
}

const onEditLedger = (editLedgerRequest: EditLedgerRequest) => {
  LedgerApi.editLedger(editLedgerRequest).then(() => {
    ledgerStore.ledgers[editLedgerRequest.id] = {
      ...ledgerStore.ledgers[editLedgerRequest.id],
      id: editLedgerRequest.id,
      name: editLedgerRequest.name,
      description: editLedgerRequest.description,
    }
    closeDialogue()
  })
}
</script>

<style scoped lang="less"></style>
