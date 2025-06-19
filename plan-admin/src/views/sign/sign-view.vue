<script lang="ts" setup>
import SignTable from './components/sign-table.vue'
import SignQuery from './components/sign-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { SignSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'
const initQuery: SignSpec = {}
const tableHelper = useTableHelper(
  api.signForAdminController.query,
  api.signForAdminController,
  initQuery,
)
const { query, restQuery } = useQueryHelper<SignSpec>(initQuery)
provide('signTableHelper', tableHelper)
</script>
<template>
  <div class="sign-view">
    <sign-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></sign-query>
    <sign-table></sign-table>
  </div>
</template>

<style lang="scss" scoped>
.sign-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
