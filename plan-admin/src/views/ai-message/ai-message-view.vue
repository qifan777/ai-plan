<script lang="ts" setup>
import AiMessageTable from './components/ai-message-table.vue'
import AiMessageQuery from './components/ai-message-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { AiMessageSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: AiMessageSpec = {}
const tableHelper = useTableHelper(
  api.aiMessageForAdminController.query,
  api.aiMessageForAdminController,
  initQuery,
)
const { query, restQuery } = useQueryHelper<AiMessageSpec>(initQuery)
provide('aiMessageTableHelper', tableHelper)
</script>
<template>
  <div class="ai-message-view">
    <ai-message-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></ai-message-query>
    <ai-message-table></ai-message-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-message-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
