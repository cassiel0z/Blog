<template>
    <v-container>
        <v-row>
            <v-col cols="12">
                <v-card color="#1F7087" theme="dark" v-for="(content, index) in contents" :key="content.content"
                    class="my-2">
                    <v-row>
                        <v-col cols="8">
                            <v-card-title class="text-h5">
                                {{ content.topic }}
                            </v-card-title>
                            <v-card-text>{{ content.content }}</v-card-text>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col cols="3">
                            <v-hover v-slot="{ isHovering, props }">
                                <v-img :src="content.pic" min-width="100" max-width="400" v-bind="props"
                                    class="mx-auto my-auto h-100">
                                    <v-fade-transition>
                                        <div v-if="isHovering"
                                            class="d-flex transition-fast-in-fast-out h-100 v-card--reveal text-h2">

                                            <v-dialog v-model="dialog1" width="auto">
                                                <template v-slot:activator="{ props }">
                                                    <v-btn icon class="mx-2" v-bind="props" color="green"
                                                        @click="setView(index)"><v-icon>mdi-note-edit</v-icon></v-btn>
                                                </template>
                                                <v-card min-width="400" min-height="430">

                                                    <v-card-title>添加文章</v-card-title>

                                                    <v-form class="mx-4" ref="form">
                                                        <v-file-input accept="image/*" v-model="post.pic"
                                                            variant="underlined" label="封面" color="primary"></v-file-input>
                                                        <v-text-field label="标题" v-model="post.topic" variant="underlined"
                                                            color="primary"></v-text-field>
                                                        <v-textarea label="正文" v-model="post.content" variant="underlined"
                                                            color="primary"></v-textarea>
                                                    </v-form>

                                                    <v-card-actions>
                                                        <v-spacer></v-spacer>
                                                        <v-btn variant="text" @click="dialog1 = false">
                                                            取消
                                                        </v-btn>
                                                        <v-btn color="primary" variant="text" @click="update(content.id)">
                                                            保存
                                                        </v-btn>
                                                    </v-card-actions>
                                                </v-card>
                                            </v-dialog>



                                            <v-dialog v-model="dialog2" width="auto">
                                                <template v-slot:activator="{ props }">
                                                    <v-btn icon class="mx-2" v-bind="props"
                                                        color="error"><v-icon>mdi-delete</v-icon></v-btn>
                                                </template>
                                                <v-card min-width="200" min-height="50">

                                                    <v-card-title>确定删除吗</v-card-title>

                                                    <v-card-actions>
                                                        <v-spacer></v-spacer>
                                                        <v-btn variant="text" @click="dialog2 = false">
                                                            取消
                                                        </v-btn>
                                                        <v-btn color="primary" variant="text"
                                                            @click="deletePost(content.id)">
                                                            确定
                                                        </v-btn>
                                                    </v-card-actions>
                                                </v-card>
                                            </v-dialog>


                                        </div>
                                    </v-fade-transition>
                                </v-img>
                            </v-hover>
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAppStore } from '@/store/app';

// @ts-ignore
import { getArticles, deleteArticle, getPic, updateArticle } from '@/request/article.js'

let route = useRouter()
const store = useAppStore()
const props = defineProps(['type'])
let form = ref()

let dialog1 = ref(false)
let dialog2 = ref(false)


interface Content {
    topic: string,
    content: string,
    pic: string,
    id: string,
}

interface Post { pic?: File[], topic: string, content: string, id: string }


let post: Post = reactive({
    pic: undefined,
    topic: '',
    content: '',
    id: '',
})

let contents = ref<Content[]>([])

async function update(id: string) {
    const formData = new FormData();
    formData.append('id', id)
    formData.append('topic', post.topic)
    formData.append('content', post.content)
    formData.append('image', new Blob(post.pic))
    console.log(formData.get('image'))
    let response = await updateArticle(formData, props.type)
    if (response.code === 0) {
        route.go(0)
    }
}

async function deletePost(id: string) {
    let response = await deleteArticle(id, props.type)
    if (response.data.code === 0) {
        route.go(0)
    }
    dialog2.value = false
}

function setView(index: number) {
    post.topic = contents.value[index].topic
    post.content = contents.value[index].content
    post.id = contents.value[index].id
    dialog1.value = true
}

onMounted(async () => {
    let response = await getArticles(props.type)
    contents.value = response.data.data

    for (let t of contents.value) {
        let pic = await getPic(t.id, props.type)
        t.pic = URL.createObjectURL(pic.data)
    }
})
</script>

<style scoped>
.v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    background-color: rgba(0, 0, 0, 0.437);
    position: absolute;
    width: 100%;
}
</style>
