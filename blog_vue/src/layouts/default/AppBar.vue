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
import loginBox from '@/components/loginBox.vue';
import registerBox from '@/components/registerBox.vue';
import {  ref } from 'vue';


let loginDialog = ref<boolean>(false)
let registerDialog = ref<boolean>(false)
let snackbar = ref<boolean>(false)
let text = ref<string>('')

function getMsg(value: any): void {
  snackbar.value = true
  text.value = value
}

</script>
