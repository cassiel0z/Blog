<template>
  <v-card>

    <div v-if="!show3">
      <v-container v-if="!show2">

        <v-row>
          <v-col cols="12" class="text-center">
            <span class="text-h6 mx-auto">欢迎回来</span>
          </v-col>

          <v-spacer></v-spacer>

          <v-col cols="12" class="text-center">
            <v-switch color="primary" class="mx-auto" v-model="loginMethod" :true-value="2" :false-value="1"
              label="密码登录"></v-switch>
          </v-col>


          <!-- 邮箱登录 -->
          <template v-if="loginMethod === 1">
            <v-col cols="12" class="mt-4">
              <v-text-field label="邮箱" outlined prepend-inner-icon="mdi-email" placeholder="hello@email.com"
                v-model="email" @keyup.enter="getCode()"></v-text-field>

              <v-btn color="purple" @click="getCode()" class="mx-auto mt-5" block height="55" :loading="loading">
                <span class="text-subtitle-1">继续</span>
              </v-btn>

            </v-col>
          </template>

          <!-- 密码登录 -->
          <template v-if="loginMethod === 2">
            <v-col cols="12">
              <v-text-field label="邮箱" outlined prepend-inner-icon="mdi-email" placeholder="hello@email.com"
                v-model="email"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                prepend-inner-icon="mdi-key" outlined :type="show1 ? 'text' : 'password'" name="input-10-1" label="密码"
                @keyup.enter="submit()" @click:append="show1 = !show1">
              </v-text-field>
            </v-col>
            <v-col cols="4">
              <v-btn color="purple" block height="55" outlined @click="show3 = true">
                <span class="text-subtitle-1">忘记密码</span>
              </v-btn>
            </v-col>
            <v-col cols="8">
              <v-btn color="purple" @click="submit()" class="mx-auto" block height="55">
                <span class="text-subtitle-1">登录</span>
              </v-btn>
            </v-col>

          </template>
        </v-row>
      </v-container>

      <template v-else>
        <v-btn variant="text" @click="show2 = false">返回</v-btn>
        <v-otp-input v-model="otp" @finish="submit()" class="my-10"></v-otp-input>
        <v-btn block color="purple" height="55" class="mt-4" :disabled="counter !== 0" @click="getCode()">{{ counter !== 0
          ? `在${counter}秒后重试` : '重新获取验证码' }}</v-btn>
      </template>
    </div>

    <!-- 忘记密码 -->
    <div v-else class="ma-5">
      <v-row>
        <v-btn variant="text" @click="show3 = false">返回</v-btn>
      </v-row>
      <v-row justify="center">
        <v-col cols="8">
          <v-text-field v-model="email" label="邮箱" required outlined :rules="rules.emailRules"></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-btn color="purple" @click="getCode()" class="mx-auto" block height="55" outlined :disabled="counter !== 0">{{
            counter === 0 ? '获取验证码' : `${counter}秒后重试` }}</v-btn>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="otp" label="验证码" required outlined></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="resetPsw" label="密码" required outlined type="password"
            :rules="rules.pswRules"></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-text-field v-model="repsw" label="重复密码" required outlined type="password"
            :rules="rules.repswRules"></v-text-field>
        </v-col>

        <v-col cols="12">
          <v-btn color="purple" class="mx-auto" block height="55" :loading="loading" @click="passwordReset()">
            <span class="text-subtitle-1">修改密码</span>
          </v-btn>
        </v-col>
      </v-row>
    </div>


  </v-card>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router'
// @ts-ignore
import { getOTP, OTPLogin, pswLogin, pswReset } from '../request/user.js'
import { useAppStore } from '@/store/app';


const emit = defineEmits(['msg'])
const router = useRouter()
const store =useAppStore()

let loading = ref<boolean>(false);
let show1 = ref<boolean>(false);
let show2 = ref<boolean>(false);
let show3 = ref<boolean>(false);
let loginMethod = ref<number>(1);
let email = ref<string>('');
let password = ref<string>('');
let resetPsw = ref<string>('');
let repsw = ref<string>('');
let otp = ref<string>('');
let counter = ref<number>(0);


let rules: any = computed(() => {
  return {
    emailRules: [
      (v: string) => !!v || '邮箱不能为空',
      (v: string) => /.+@.+/.test(v) || '邮箱格式错误',
    ],
    pswRules: [
      (v: string) => !!v || '密码不能为空',
      (v: string) => v.length >= 8 || '密码长度需大于8',
    ],
    repswRules: [
      (v: string) => resetPsw.value === v || '两次输入密码不匹配',
    ],
  }
})



function submit(): void {
  loading.value = true
  if (loginMethod.value === 1) {
    OTPLogin(email, otp).then((res: { data: { code: number; data: { token: any; id: any; }; }; }) => {
      if (res.data.code === 0) {
        emit('msg', '登录成功')

        //将登录信息保存在vuex和localstorage中
        // store.commit('setLoginInfo', { token: res.data.data.token, userId: res.data.data.id })
        router.go(0)
      }
      else if (res.data.code === -1) {
        emit('msg', '验证码错误')
      }
    }).catch(() => {
      emit('msg', '网络错误')
    })
  }
  else {
    if (email.value === '') {
      emit('msg', '邮箱不能为空')
    }
    else if (!/.+@.+/.test(email.value)) {
      emit('msg', '邮箱格式错误')
    }
    else if (password.value === '') {
      emit('msg', '密码不能为空')
    }
    else {
      passwordLogin()
    }
  }
  loading.value = false
}
function passwordLogin(): void {
  pswLogin(email.value, password.value).then((res: { data: { code: number; data: { token: any; id: any; }; message: string; }; }) => {
    if (res.data.code === 0) {
      emit('msg', '登录成功')
      store.setLoginInfo(res.data.data.token, res.data.data.id)
      router.go(0)
    }
    else if (res.data.code === -1) {
      if (res.data.message === '用户不存在') {
        emit('msg', '用户未注册')
      }
      else if (res.data.message === '密码错误') {
        emit('msg', '密码错误')
      }
    }
  }).catch(() => {
    emit('msg', '网络错误')
  })
}
function getCode(): void {
  if (email.value !== '') {
    if (/.+@.+/.test(email.value)) {

      if (counter.value === 0) {
        loading.value = true

        getOTP(email.value).then((res: { data: { code: number } }) => {
          if (res.data.code === 0) {
            emit('msg', '验证码已发送')
            loading.value = false
            show2.value = true;
            if (counter.value === 0) {
              counter.value = 60;
              var count = setInterval(() => {
                counter.value--
                if (counter.value <= 0) {
                  clearInterval(count)
                  counter.value = 0
                }
              }, 1000);
            }

          }
          else if (res.data.code === -1) {
            emit('msg', '用户未注册')
            loading.value = false
          }
        }).catch(() => {
          emit('msg', '网络错误')
          loading.value = false
        })
      }
      else {
        show2.value = true
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
function passwordReset(): void {
  if (validate()) {
    loading.value = true
    pswReset(email.value, resetPsw.value, otp.value).then((res: { data: { code: number; message: string; }; }) => {
      if (res.data.code === 0) {
        emit('msg', '密码修改成功')
        router.go(0)
      }
      else if (res.data.code === -1) {

        if (res.data.message === '用户不存在') {
          emit('msg', '用户未注册')
        }
        else if (res.data.message === '验证失败') {
          emit('msg', '验证码错误')
        }

      }
    }).catch(() => {
      emit('msg', '网络错误')
    })
    loading.value = false
  }
}

function validate(): boolean {
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
    if (v(resetPsw.value) !== true) {
      emit('msg', v(resetPsw.value))
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



onMounted(() => {
})




</script>

<style scoped></style>
