<script lang="ts" setup>
import NoticeTable from './components/notice-table.vue'
import NoticeQuery from './components/notice-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { NoticeSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: NoticeSpec = {}
const tableHelper = useTableHelper(
  api.noticeForAdminController.query,
  api.noticeForAdminController,
  initQuery,
)
const { query, restQuery } = useQueryHelper<NoticeSpec>(initQuery)
provide('noticeTableHelper', tableHelper)
</script>
<template>
  <div class="notice-view">
    <notice-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></notice-query>
    <notice-table></notice-table>
  </div>
</template>

<style lang="scss" scoped>
.notice-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
