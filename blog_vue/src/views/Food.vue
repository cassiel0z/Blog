<template>

</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
let route=useRouter()

onMounted(() => {
    console.log(route.currentRoute.value.path)
})
</script>

<style scoped>

</style>
