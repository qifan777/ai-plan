<script lang="ts" setup>
import PostTable from './components/post-table.vue'
import PostQuery from './components/post-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { PostSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: PostSpec = {}
const tableHelper = useTableHelper(
  api.postForAdminController.query,
  api.postForAdminController,
  initQuery
)
const { query, restQuery } = useQueryHelper<PostSpec>(initQuery)
provide('postTableHelper', tableHelper)
</script>
<template>
  <div class="post-view">
    <post-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></post-query>
    <post-table></post-table>
  </div>
</template>

<style lang="scss" scoped>
.post-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
