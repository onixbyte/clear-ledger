import { defineStore } from "pinia"
import { ref } from "vue"
import { Ledger } from "@/types"

export const useLedgerStore = defineStore("ledger-store", () => {
  const ledgers = ref<Ledger[]>([])
  return { ledgers }
}, {
  persist: true
})
