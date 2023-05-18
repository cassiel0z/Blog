<template>
  <div class="text-center my-auto mx-auto">
    <p class="text-h1">404</p>
    <p class="text-h5">您请求的资源不存在,正在返回首页 {{ count }}...</p>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

let count = ref<number>(3)

onMounted(() => {
  let counter = setInterval(() => {
    count.value--
    if (count.value <= 0) {
      clearInterval(counter)
      router.push('/')
    }
  }, 1000);
})


</script>
