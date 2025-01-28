import { defineStore } from "pinia"
import { computed, ref } from "vue"

export const useUserStore = defineStore("user-store", () => {
  const authorisation = ref<string>("")

  /**
   * A property that checks whether if a user is logged in.
   */
  const isAuthenticated = computed<boolean>(() => {
    return authorisation.value != null && authorisation.value != ""
  })

  return { authorisation, isAuthenticated }
})