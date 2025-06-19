<script setup lang="ts">
import type { UserSpec } from '@/apis/__generated/model/static'

import DictSelect from '@/components/dict/dict-select.vue'

const emit = defineEmits<{ search: [value: UserSpec]; rest: [] }>()
const query = defineModel<UserSpec>({ required: true })
</script>

<template>
  <el-form inline label-width="80" size="small">
    <el-form-item label="手机号">
      <el-input v-model="query.phone"></el-input>
    </el-form-item>
    <el-form-item label="昵称">
      <el-input v-model="query.nickname"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <dict-select v-model="query.gender" :dict-id="1001"></dict-select>
    </el-form-item>
    <el-form-item label=" ">
      <div class="btn-wrapper">
        <el-button size="small" type="primary" @click="emit('search')"> 查询 </el-button>
        <el-button size="small" type="warning" @click="emit('rest')"> 重置</el-button>
      </div>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss"></style>
