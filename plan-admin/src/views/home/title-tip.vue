<script setup lang="ts">
import TitleIcon from '@/assets/icons/title.svg'

defineProps<{ title: string }>()
</script>

<template>
  <div class="title-tip">
    <div class="title"></div>
    <img class="icon" :src="TitleIcon" alt="" />
    <span>{{ title }}</span>
  </div>
</template>

<style scoped lang="scss">
.title-tip {
  height: 30px;
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  .title {
    display: flex;
    align-items: center;
    font-size: 16px;
  }
  .icon {
    width: 20px;
    height: 20px;
    line-height: 1;
  }
  span {
    margin-left: 8px;
    margin-right: 8px;
    font-size: 16px;
    color: #303133;
  }
}
</style>
