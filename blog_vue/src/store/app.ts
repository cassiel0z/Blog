// Utilities
import { defineStore } from 'pinia'

interface storeState {
  token: string
  userId: string
}

export const useAppStore = defineStore('app', {
  state: (): storeState => ({
    token: localStorage.getItem('token')||'',
    userId: localStorage.getItem('userId')||''
  }),
  actions: {
    setLoginInfo(token: string, userId: string): void {
      this.token = token
      this.userId = userId
      localStorage.setItem("token", token)
      localStorage.setItem("userId", userId)
    },
    logOut(): void {
      this.$reset()
      localStorage.clear();
    }
  }
})
