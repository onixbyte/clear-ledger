import { defineStore } from "pinia"
import { ref } from "vue"
import { Ledger } from "@/types"

export const useLedgerStore = defineStore("ledger-store", () => {
  const ledgers = ref<Record<string, Ledger>>({})
  return { ledgers }
}, {
  persist: true
})
