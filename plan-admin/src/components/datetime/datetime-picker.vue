<script setup lang="ts">
import { computed } from 'vue'
import dayjs from 'dayjs'
const props = withDefaults(defineProps<{ minDateTime?: string; maxDateTime?: string }>(), {
  minDateTime: '',
  maxDateTime: '',
})
const emit = defineEmits<{
  'update:minDateTime': [value: string]
  'update:maxDateTime': [value: string]
}>()
const timeRange = computed({
  get: () => {
    return [
      props.minDateTime ? dayjs(props.minDateTime).toDate() : null,
      props.maxDateTime ? dayjs(props.maxDateTime).toDate() : null,
    ] as [Date, Date]
  },
  set: (value) => {
    if (value[0] && value[1]) {
      emit('update:minDateTime', dayjs(value[0]).format('YYYY-MM-DD HH:mm:ss'))
      emit('update:maxDateTime', dayjs(value[1]).format('YYYY-MM-DD HH:mm:ss'))
    }
  },
})
</script>

<template>
  <el-date-picker
    v-model="timeRange"
    type="datetimerange"
    range-separator="至"
    start-placeholder="开始日期"
    end-placeholder="结束日期"
  />
</template>

<style scoped lang="scss"></style>
