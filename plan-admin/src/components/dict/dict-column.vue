<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import type { DictDto } from '@/apis/__generated/model/dto'
import { queryDict } from '@/components/dict/dict'

const props = withDefaults(defineProps<{ dictId: number; value?: string | string[] }>(), {
  value: '',
})
const options = ref<DictDto['DictRepository/COMPLEX_FETCHER'][]>([])
onMounted(async () => {
  const res = queryDict({ dictId: props.dictId })
  if (res) {
    options.value = (await res).content
  }
})
const keyNames = computed(() => {
  if (props.value instanceof Array) {
    return props.value.map((value) => {
      return getKeyName(value)
    })
  }
  return [getKeyName(props.value)]
})
const getKeyName = (value: string) => {
  const option = options.value.find((option) => {
    return option.keyEnName === value
  })
  return option ? option.keyName : ''
}
</script>

<template>
  <div>{{ keyNames.join(',') }}</div>
</template>

<style scoped lang="scss"></style>
