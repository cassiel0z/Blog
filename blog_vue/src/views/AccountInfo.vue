<template>
    <v-container fluid class="mt-9">

        <v-card elevation="4" class="pa-3">
            <v-row align="center" class="spacer" no-gutters>
                <v-col cols="4" sm="3" md="2">
                    <v-avatar size="80">
                        <v-img :src="avatar"></v-img>
                    </v-avatar>
                </v-col>

                <v-col sm="5" md="3">
                    <strong>{{ user.username }}</strong>
                </v-col>

                <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="2">
                    <strong>账户设置</strong>
                </v-col>

                <v-col class="grey--text text-truncate hidden-sm-and-down">
                    修改用户名，邮箱，密码
                </v-col>
            </v-row>
        </v-card>

        <v-card elevation="4" class="mt-2">
            <v-list flat rounded>
                <!-- 修改用户名 -->
                <v-dialog v-model="dialog1" width="400" persistent height="500">
                    <template v-slot:activator="{ isActive, props }">
                        <v-list-item v-bind="props" v-on="isActive" class="mt-2">
                            <v-list-item-title><v-icon color="accent">mdi-account</v-icon> 用户名 </v-list-item-title>
                            <v-list-item-subtitle class="my-2 ml-1">{{ user.username }}</v-list-item-subtitle>
                        </v-list-item>
                    </template>
                    <v-card>
                        <v-card-title class="text-h5 mt-2 ml-2">
                            修改用户名
                        </v-card-title>
                        <v-card-text class="mt-5"><v-text-field v-model="newName" counter="10" label="输入新用户名" required
                                outlined @keyup.enter="updatename()"></v-text-field></v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn class="mb-5" @click="updatename()" color="primary" width="120" height="40">
                                修改
                            </v-btn>
                            <v-btn class="mb-5" variant="text" @click="dialog1 = false" width="120" height="40">
                                取消
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>

                <v-divider></v-divider>

                <!-- 修改邮箱 -->
                <v-dialog v-model="dialog2" width="500" persistent>
                    <template v-slot:activator="{ isActive, props }">
                        <v-list-item v-bind="props" v-on="isActive" class="mt-2">
                            <v-list-item-title><v-icon color="accent">mdi-gmail</v-icon> 邮箱 </v-list-item-title>
                            <v-list-item-subtitle class="my-2 ml-1">{{ user.email }}</v-list-item-subtitle>
                        </v-list-item>

                    </template>
                    <v-card height="600" class="pa-5">
                        <v-card-title class="text-h4 mt-4 ml-1">修改邮箱</v-card-title>
                        <v-row class="mt-5">
                            <v-col cols="12">
                                <v-text-field label="邮箱" required outlined v-model="user.email" disabled></v-text-field>
                            </v-col>
                            <v-col cols="8">
                                <v-text-field label="验证码" required outlined v-model="otp1"></v-text-field>
                            </v-col>
                            <v-col cols="4">
                                <v-btn color="primary" @click="getemailOTP()" class="mx-auto" block height="55" outlined
                                    :disabled="counter2 !== 0">{{ counter2 ===
                                        0 ? '获取验证码' : `${counter2}秒后重试` }}</v-btn>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field label="新邮箱" required outlined v-model="newEmail"></v-text-field>
                            </v-col>
                            <v-col cols="8">
                                <v-text-field label="验证码" required outlined v-model="otp2"></v-text-field>
                            </v-col>
                            <v-col cols="4">
                                <v-btn color="primary" @click="getemailOTP()" class="mx-auto" block height="55" outlined
                                    :disabled="counter2 !== 0">{{ counter2 ===
                                        0 ? '获取验证码' : `${counter2}秒后重试` }}</v-btn>
                            </v-col>
                            <v-col cols="6">
                                <v-spacer></v-spacer>
                            </v-col>
                            <v-col cols="3">
                                <v-btn color="primary" @click="emailreset()" class="mr-3" block height="40">
                                    完成
                                </v-btn>
                            </v-col>
                            <v-col cols="3">
                                <v-btn variant="text" @click="dialog2 = false; step1 = 1" block height="40">
                                    取消
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-card>

                </v-dialog>

                <v-divider></v-divider>

                <!-- 修改密码 -->
                <v-dialog v-model="dialog3" width="500" persistent>
                    <template v-slot:activator="{ isActive, props }">

                        <v-list-item v-bind="props" v-on="isActive" class="mt-2">
                            <v-list-item-title> <v-icon color="accent">mdi-key</v-icon> 密码 </v-list-item-title>
                            <v-list-item-subtitle class="my-2 ml-1">点击修改</v-list-item-subtitle>
                        </v-list-item>

                    </template>

                    <v-card height="600" class="pa-5">

                        <v-card-title class="text-h4 mt-4 ml-5">修改密码</v-card-title>


                        <v-row class="mt-5">
                            <v-col cols="12">
                                <v-text-field label="邮箱" required outlined v-model="user.email" disabled></v-text-field>
                            </v-col>
                            <v-col cols="8">
                                <v-text-field label="验证码" required outlined v-model="otp3"></v-text-field>
                            </v-col>
                            <v-col cols="4">
                                <v-btn color="primary" @click="getotp()" class="mx-auto" block height="55" outlined
                                    :disabled="counter !== 0">{{ counter === 0
                                        ? '获取验证码' : `${counter}秒后重试` }}</v-btn>
                            </v-col>

                        </v-row>

                        <v-row class="mt-5">
                            <v-col cols="12">
                                <v-text-field label="密码" required outlined v-model="resetPsw"></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field label="重复密码" required outlined v-model="repsw"></v-text-field>
                            </v-col>
                        </v-row>

                        <v-row>
                            <v-spacer></v-spacer>
                            <v-col cols="3">
                                <v-btn color="primary" @click="pswreset()" class="mr-3" block height="40">
                                    完成
                                </v-btn>
                            </v-col>
                            <v-col cols="3">
                                <v-btn variant="text" @click="dialog3 = false" block height="40">
                                    取消
                                </v-btn>
                            </v-col>
                        </v-row>




                    </v-card>
                </v-dialog>
            </v-list>
        </v-card>

        <v-btn block height="50" color="warning" variant=text @click="logOut()">
            退出登录
        </v-btn>

    </v-container>
</template>
  
<script lang="ts" setup>
import { ref, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/store/app';
import defaultAvatar from '@/assets/defaultAvatar.png'


// @ts-ignore
import { getEmailOTP, getOTP, getUserAvatar, getUserInfo, logout, OTPLogin, pswReset, updateEmail, updateName } from '@/request/user'

const store = useAppStore()

interface User {
    username: string,
    email: string,
}

const router = useRouter()

let avatar = ref<any>(defaultAvatar)
let user = reactive<User>({
    username: '',
    email: '',
})

let newEmail = ref<string>('')
let newName = ref<string>('')
let resetPsw = ref<string>('')
let repsw = ref<string>('')



let dialog1 = ref<boolean>(false)
let dialog2 = ref<boolean>(false)
let dialog3 = ref<boolean>(false)

let counter = ref<number>(0)
let counter2 = ref<number>(0)

let otp1 = ref<string>('')
let otp2 = ref<string>('')
let otp3 = ref<string>('')


let step1 = ref<number>(0)
let step2 = ref<number>(0)

function pswreset(): void {
    if (resetPsw.value.length >= 8) {
        if (resetPsw.value === repsw.value) {
            pswReset(user.email, resetPsw.value, otp3.value).then((res: { data: { code: number; message: string; }; }) => {
                if (res.data.code === 0) {
                    alert('密码修改成功')
                    router.go(0)
                }
                else if (res.data.code === -1) {
                    if (res.data.message === '验证失败') {
                        alert('验证码错误')
                    }
                }
            }).catch(() => {
                alert('网络错误')
            })
        }
        else {
            alert('两次输入密码不匹配')
        }
    }
    else {
        alert('密码长度不能小于8位')
    }
}
function emailreset(): void {
    if (otp2.value !== '') {
        if (nextStep1()) {
            updateEmail(newEmail.value, otp2.value).then((res: { data: { code: number; data: { token: string; id: string; }; message: string; }; }) => {
                console.log(res)
                if (res.data.code === 0) {
                    alert('邮箱修改成功')
                    store.setLoginInfo(res.data.data.token, res.data.data.id)
                    router.go(0)
                }
                else if (res.data.code === -1) {
                    if (res.data.message === '邮箱已占用') {
                        alert('邮箱已占用')
                    }
                    else if (res.data.message === '验证失败') {
                        alert('验证码错误')
                    }
                }

            })
        }
        else{
        alert('原邮箱验证码错误')
        }

    }
    else {
        alert('请输入验证码')
    }
}
function getotp(): void {
    if (counter.value === 0) {
        getOTP(user.email).then((res: { data: { code: number; }; }) => {
            if (res.data.code === 0) {
                alert('验证码已发送')
                if (counter.value === 0) {
                    counter.value = 60;
                    const count = setInterval(() => {
                        counter.value--
                        if (counter.value <= 0) {
                            clearInterval(count)
                            counter.value = 0
                        }
                    }, 1000);
                }

            }
        })
    }
}
function getemailOTP(): void {
    if (newEmail.value !== '') {
        if (/.+@.+/.test(newEmail.value)) {
            if (counter2.value === 0) {
                getEmailOTP(newEmail.value).then((res: { data: { code: number; }; }) => {
                    if (res.data.code === 0) {
                        alert('验证码已发送')
                        if (counter2.value === 0) {
                            counter2.value = 60;
                            const count = setInterval(() => {
                                counter2.value--
                                if (counter2.value <= 0) {
                                    clearInterval(count)
                                    counter2.value = 0
                                }
                            }, 1000);
                        }
                    }
                })
            }
        }
        else {
            alert('邮箱格式错误')
        }
    }
    else {
        alert('邮箱不能为空')
    }
}
function updatename(): void {
    if (newName.value !== '') {
        if (newName.value.length <= 10) {
            updateName(newName.value).then(() => {
                alert('修改成功')
                router.go(0)
            }).catch(() => {
                alert('网络错误')
            })
        }
        else {
            alert('姓名长度不能大于10')
        }
    }
    else {
        alert('请输入姓名')
    }
}
function nextStep1(): boolean {
    if (otp1.value !== '') {
        OTPLogin(user.email, otp1.value).then((res: { data: { code: number; }; }) => {
            if (res.data.code === 0) {
                return true
            }
            else if (res.data.code === -1) {
                alert('验证码错误')
            }
            return false
        })
    }
}

function logOut(): void {
    logout().then((res: { data: { code: number; }; }) => {
        if (res.data.code === 0) {
            store.logOut()
            router.push('/')
            router.go(0)
        }
    })
}


onMounted(async () => {
    let avatar = await getUserAvatar()
    if (avatar.status === 200) {
        avatar.value = URL.createObjectURL(avatar.data)
    }

    let response = (await getUserInfo()).data.data
    user.username = response.username
    user.email = response.email

})




</script>
