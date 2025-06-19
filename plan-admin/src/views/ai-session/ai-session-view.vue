<script lang="ts" setup>
import AiSessionTable from './components/ai-session-table.vue'
import AiSessionQuery from './components/ai-session-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { AiSessionSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: AiSessionSpec = {}
const tableHelper = useTableHelper(
  api.aiSessionForAdminController.query,
  api.aiSessionForAdminController,
  initQuery,
)
const { query, restQuery } = useQueryHelper<AiSessionSpec>(initQuery)
provide('aiSessionTableHelper', tableHelper)
</script>
<template>
  <div class="ai-session-view">
    <ai-session-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></ai-session-query>
    <ai-session-table></ai-session-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-session-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
