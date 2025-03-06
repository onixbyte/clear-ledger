<template>
  <div class="table-wrapper">
    <el-table :data="transactions.records" stripe>
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="交易金额">
        <template #default="{ row }">
          ¥ {{ (row as Transaction).amount / 100 }}
        </template>
      </el-table-column>
      <el-table-column label="交易详情" prop="description" />
      <el-table-column label="交易时间">
        <template #default="{ row }">
          {{ moment((row as Transaction).transactionDate).format("YYYY年MM月DD日 HH:mm:ss") }}
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="pagination-controls"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :size="pageSize"
      layout="total, prev, pager, next, jumper"
      :total="transactions.totalRow"
      @current-change="getTransactions"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue"
import { Page, Transaction } from "@/types"
import * as TransactionApi from "@/api/transaction"
import moment from "moment"

const { ledgerId } = defineProps<{
  ledgerId: string
}>()

const transactions = ref<Page<Transaction>>({
  records: [],
  pageNumber: 0,
  pageSize: 0,
  totalPage: 0,
  totalRow: 0,
})

const pageNum = ref<number>(1)

const pageSize = ref<number>(20)

const getTransactions = async () => {
  const _transactions = await TransactionApi.getTransactions(ledgerId, pageNum.value, pageSize.value)
  transactions.value = { ..._transactions }
}

onMounted(async () => {
  await getTransactions()
})
</script>

<style scoped lang="less">
.table-wrapper {
  .pagination-controls {
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
