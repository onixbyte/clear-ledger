import { ref, computed } from "vue"
import { useRoute } from "vue-router"
import { defineStore } from "pinia"
import { Ledger } from "@/types"

export const useLedgerStore = defineStore("ledger-store", () => {
  const ledgers = ref<Record<string, Ledger>>({})

  const getCurrentLedger = computed(() => {
    const route = useRoute()
    const ledgerId = route.params.ledgerId as string
    return ledgers.value[ledgerId] ?? null
  })

  return { ledgers, getCurrentLedger }
}, {
  persist: true
})
