import { ref, computed } from "vue"
import { useRoute } from "vue-router"
import { defineStore } from "pinia"
import { Ledger } from "@/types"

export const useLedgerStore = defineStore("ledger-store", () => {
  const ledgers = ref<Record<string, Ledger>>({})

  return { ledgers }
}, {
  persist: true
})
