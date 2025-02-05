import { computed, ref } from "vue"
import { defineStore } from "pinia"
import { User } from "@/types"

export const useUserStore = defineStore("user-store", () => {
  const authorisation = ref<string>("")

  const isAuthenticated = computed<boolean>(() => {
    return authorisation.value != null && authorisation.value != ""
  })

  const user = ref<User>({
    id: "",
    username: "",
    email: ""
  })

  return { authorisation, isAuthenticated, user }
})