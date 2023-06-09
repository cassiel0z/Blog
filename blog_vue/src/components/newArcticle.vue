<template>
    <v-menu v-model="menu" :close-on-content-click="false" location="top">
        <template v-slot:activator="{ props }">
            <v-btn id="btn" icon size="large" v-bind="props" color="primary lighte--1"><v-icon>mdi-plus</v-icon></v-btn>
        </template>

        <v-card min-width="400" min-height="430">

            <v-card-title>添加文章</v-card-title>

            <v-form class="mx-4" ref="form">
                <v-file-input accept="image/*" v-model="post.cover" label="封面" color="primary"
                    variant="underlined"></v-file-input>
                <v-text-field label="标题" v-model="post.title" :rules="nonEmpty" variant="underlined"
                    color="primary"></v-text-field>
                <v-textarea label="正文" v-model="post.text" :rules="nonEmpty" variant="underlined"
                    color="primary"></v-textarea>
            </v-form>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn variant="text" @click="menu = false">
                    取消
                </v-btn>
                <v-btn color="primary" variant="text" @click="submit()">
                    保存
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-menu>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { useAppStore } from '@/store/app';
import { useRouter } from 'vue-router';


// @ts-ignore
import { addArticle } from '@/request/article.js'

const store = useAppStore()
let route = useRouter()
const props = defineProps(['type'])

let form = ref()

let menu = ref(false)

let nonEmpty = ref([(e: string | File[]) => !!e || '不能为空'])

let post: { cover?: File[], title: string, text: string } = reactive({
    cover: undefined,
    title: '',
    text: ''
})


async function submit(): Promise<void> {
    if (await validate()) {
        const formData = new FormData();
        formData.append('userId', store.userId)
        formData.append('topic', post.title)
        formData.append('content', post.text)
        formData.append('image', new Blob(post.cover))
        console.log(formData.get('image'))
        let response = await addArticle(formData, props.type)
        if (response.data.code === 0) {
            route.go(0)
        }
    }
}

async function validate() {
    const { valid } = await form.value.validate()
    return valid
}

</script>

<style scoped>
#btn {
    position: fixed;
    bottom: 70px;
    right: 70px;
}
</style>
