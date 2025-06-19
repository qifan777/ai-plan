<script lang="ts" setup>
import type { NoticeSpec } from '@/apis/__generated/model/static'

const emit = defineEmits<{ search: [value: NoticeSpec]; reset: [] }>()
const query = defineModel<NoticeSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="标题">
        <el-input v-model.trim="query.title"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model.trim="query.content"></el-input>
      </el-form-item>
      <el-form-item label="是否激活">
        <el-switch v-model="query.active"></el-switch>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="() => emit('search', query)">
            查询
          </el-button>
          <el-button type="warning" size="small" @click="() => emit('reset')"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-form-item) {
  margin-bottom: 5px;
}

.search {
  display: flex;
  flex-flow: column nowrap;
  width: 100%;

  .btn-wrapper {
    margin-left: 20px;
  }
}
</style>
