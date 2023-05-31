<template>
  <v-app-bar flat>
    <v-app-bar-title>
      JSP期末大作业
      <v-btn> <v-icon icon="mdi-magnify"></v-icon> <span class="mb-1">搜索</span>
        <div class="ml-1 text-disabled text-caption px-1"
          style="border-style: solid;border-color: rgba(48, 48, 48, 0.204);border-width: thin;border-radius: 5px;">按 / 键
        </div>
      </v-btn>
    </v-app-bar-title>

    <v-tabs>
      <v-tab to="/">首页</v-tab>
      <v-tab to="/food">特色美食</v-tab>
      <v-tab to="/attractions">特色景点</v-tab>
      <v-tab to="/celebrities">名人名事</v-tab>
    </v-tabs>

    <!-- <v-spacer></v-spacer> -->

    <template v-slot:append>

      <div v-if="!store.$state.token">
        <v-btn variant="text">
          登录
          <v-dialog v-model="loginDialog" activator="parent" width="400">
            <loginBox @msg="getMsg" />
          </v-dialog>
        </v-btn>

        <v-btn variant="text">
          注册
          <v-dialog v-model="registerDialog" activator="parent" width="400">
            <registerBox @msg="getMsg" />
          </v-dialog>
        </v-btn>
      </div>
      <v-avatar @click="router.push('/account')" v-else>
        <v-img :src="avatar"></v-img>
      </v-avatar>

    </template>

    <v-snackbar v-model="snackbar">
      {{ text }}

      <template v-slot:actions>
        <v-btn color="pink" variant="text" @click="snackbar = false">
          关闭
        </v-btn>
      </template>
    </v-snackbar>

  </v-app-bar>
</template>

<script lang="ts" setup>
import { useAppStore } from '@/store/app';
import { useRouter } from 'vue-router'
import loginBox from '@/components/loginBox.vue';
import registerBox from '@/components/registerBox.vue';
import defaultAvatar from '@/assets/defaultAvatar.png'

// @ts-ignore
import { getUserAvatar } from '@/request/user.js'

import { onMounted, ref } from 'vue';

const store = useAppStore()
const router = useRouter()

let loginDialog = ref<boolean>(false)
let registerDialog = ref<boolean>(false)
let snackbar = ref<boolean>(false)
let text = ref<string>('')
let avatar = ref<any>(defaultAvatar)

function getMsg(value: any): void {
  snackbar.value = true
  text.value = value
}
onMounted(async () => {
  let avatar = await getUserAvatar()
  if (avatar.status === 200) {
    avatar.value = URL.createObjectURL(avatar.data)
  }
})

</script>
