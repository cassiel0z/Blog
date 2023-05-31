<template>
  <v-card>
    <v-form ref="form" lazy-validation>
      <v-row justify="center" class="ma-5">

        <v-col cols="12">
          <v-text-field v-model="name" :counter="10" label="姓名" :rules="rules.nameRules" required variant="outlined"></v-text-field>
        </v-col>

        <v-col cols="8">
          <v-text-field v-model="email" :rules="rules.emailRules" label="邮箱" required variant="outlined"></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-btn color="purple" @click="getOTP()" class="mx-auto" block height="55" variant="outlined" :disabled="counter !== 0">{{
            counter === 0 ? '获取验证码' : `${counter}秒后重试` }}</v-btn>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="otp" label="验证码" required variant="outlined"></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="password" label="密码" required variant="outlined" :rules="rules.pswRules"
            type="password"></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="repsw" label="重复密码" required variant="outlined" :rules="rules.repswRules"
            type="password"></v-text-field>
        </v-col>


        <v-col cols="12">
          <v-btn color="purple " @click="submit()" class="mx-auto" block height="55" :loading="loading">
            <span class="text-subtitle-1">注册</span>
          </v-btn>
        </v-col>

      </v-row>

    </v-form>


  </v-card>
</template>

<script lang="ts" setup>
import { Ref, computed, ref } from 'vue';

// @ts-ignore
import { getRegisterOTP, register } from '../request/user'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/store/app';

const emit = defineEmits(['msg'])
const router = useRouter()
const store = useAppStore()



let counter: Ref<number> = ref(0)
let loading: Ref<boolean> = ref(false)
let name: Ref<string> = ref('')
let email: Ref<string> = ref('')
let otp: Ref<string> = ref('')
let password: Ref<string> = ref('')
let repsw: Ref<string> = ref('')



let rules: any = computed(() => {
  return {
    nameRules: [
      (v: string) => !!v || '请输入姓名',
      (v: string) => v.length <= 10 || '姓名长度不能大于10'
    ],
    emailRules: [
      (v: string) => !!v || '邮箱不能为空',
      (v: string) => /.+@.+/.test(v) || '邮箱格式错误',
    ],
    pswRules: [
      (v: string) => !!v || '密码不能为空',
      (v: string) => v.length >= 8 || '密码长度不能小于8位',
    ],
    repswRules: [
      (v: string) => password.value === v || '两次输入密码不匹配',
    ],

  }
})


function submit(): void {
  if (validate()) {
    loading.value = true
    register(email.value, password.value, name.value, otp.value).then((res: { data: { code: number; data: { token: string; id: string } } }) => {
      if (res.data.code === 0) {
        emit('msg', '注册成功')
        store.setLoginInfo(res.data.data.token, res.data.data.id)
        router.go(0)
      }
      if (res.data.code === -1) {
        emit('msg', '验证码错误')
      }
    }).catch(() => {
      emit('msg', '网络错误')
    })
    loading.value = false

  }
}

function getOTP(): void {
  if (email.value !== '') {
    if (/.+@.+/.test(email.value)) {
      if (counter.value === 0) {
        getRegisterOTP(email.value).then((res: { data: { code: number; message: string } }) => {
          if (res.data.code === 0) {
            counter.value = 60;
            var count = setInterval(() => {
              counter.value--
              if (counter.value <= 0) {
                clearInterval(count)
                counter.value = 0
              }
            }, 1000);
            emit('msg', '验证码发送成功')
          }
          if (res.data.code === -1) {
            if (res.data.message === '用户已注册') {
              emit('msg', '用户已注册')
            }
          }
        }).catch(() => {
          emit('msg', '网络错误')
        })

      }

    }
    else {
      emit('msg', '邮箱格式错误')
    }
  }
  else {
    emit('msg', '邮箱不能为空')
  }


}
function validate(): boolean {
  for (let v of rules.value.nameRules) {
    if (v(name.value) !== true) {
      console.log(v(name.value))
      emit('msg', v(name.value))
      return false
    }
  }
  for (let v of rules.value.emailRules) {
    if (v(email.value) !== true) {
      emit('msg', v(email.value))
      return false
    }
  }

  if (!otp.value) {
    emit('msg', '请输入验证码')
    return false
  }

  for (let v of rules.value.pswRules) {
    if (v(password.value) !== true) {
      emit('msg', v(password.value))
      return false
    }
  }
  for (let v of rules.value.repswRules) {
    if (v(repsw.value) !== true) {
      emit('msg', v(repsw.value))
      return false
    }
  }
  return true

}



</script>
