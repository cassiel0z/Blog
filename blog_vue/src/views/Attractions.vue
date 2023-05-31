<template>
    <v-container>
        <v-row dense>
            <v-col cols="12">
                <v-card color="#1F7087" theme="dark" v-for="content in contents" :key="content.text" class="my-2">
                    <v-row>
                        <v-col cols="8">
                            <v-card-title class="text-h5">
                                {{ content.title }}
                            </v-card-title>
                            <v-card-text>{{ content.text }}</v-card-text>
                        </v-col>
                        <v-spacer></v-spacer>
                        <v-col cols="3">
                            <v-hover v-slot="{ isHovering, props }">
                                <v-img src="https://cdn.vuetifyjs.com/images/cards/foster.jpg" min-width="100"
                                    max-width="300" v-bind="props" class="mx-auto my-auto h-100">
                                    <v-fade-transition>
                                        <div v-if="isHovering"
                                            class="d-flex transition-fast-in-fast-out h-100 v-card--reveal text-h2"
                                            >

                                            <v-btn icon class="mx-2" color="green"><v-icon>mdi-note-edit</v-icon></v-btn>
                                            <v-btn icon class="mx-2" color="error"><v-icon>mdi-delete</v-icon></v-btn>
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
    <v-btn id="btn" icon size="large" color="primary lighte--1"><v-icon>mdi-plus</v-icon></v-btn>
</template>

<script lang="ts" setup>
import { onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
// @ts-ignore
import {addArticle,getArticle,deleteArticle} from '@/request/travel.js'

let route = useRouter()

interface Content {
    title: string,
    text: string,
    pic: string
}

let contents = reactive<Content[]>([
    {
        title: '文章1',
        text: 'balabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabala',
        pic: ''
    },
    {
        title: '文章2',
        text: 'balabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabalabala',
        pic: ''
    }
])

onMounted( async () => {
    let response = await getArticle()
})
</script>

<style scoped>
#btn {
    position: fixed;
    bottom: 70px;
    right: 70px;
}

.v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    background-color: rgba(0, 0, 0, 0.437);
    position: absolute;
    width: 100%;
}
</style>
