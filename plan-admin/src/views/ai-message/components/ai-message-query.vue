<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { AiMessageSpec } from '@/apis/__generated/model/static'
import { aiSessionQueryOptions } from '@/views/ai-session/ai-session.ts'

const emit = defineEmits<{ search: [value: AiMessageSpec]; reset: [] }>()
const query = defineModel<AiMessageSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="消息类型">
        <el-input v-model.trim="query.type"></el-input>
      </el-form-item>
      <el-form-item label="消息内容">
        <el-input v-model.trim="query.textContent"></el-input>
      </el-form-item>
      <el-form-item label="会话">
        <remote-select
          label-prop="name"
          :query-options="aiSessionQueryOptions"
          v-model="query.id"
        ></remote-select>
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
