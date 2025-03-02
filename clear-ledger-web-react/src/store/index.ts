import { configureStore } from "@reduxjs/toolkit"
import authReducer from "@/store/auth-slice"

const preloadedState = {
  auth: {
    user: null,
    token: localStorage.getItem("token"),
    isAuthenticated: !!localStorage.getItem("token"),
  },
}

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
  preloadedState,
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
export type AppStore = typeof store