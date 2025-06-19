<script lang="ts" setup>
import PostCommentTable from './components/post-comment-table.vue'
import PostCommentQuery from './components/post-comment-query.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { provide } from 'vue'
import type { PostCommentSpec } from '@/apis/__generated/model/static'
import { useQueryHelper } from '@/components/base/query/query-helper'

const initQuery: PostCommentSpec = {}
const tableHelper = useTableHelper(
  api.postCommentForAdminController.query,
  api.postCommentForAdminController,
  initQuery,
)
const { query, restQuery } = useQueryHelper<PostCommentSpec>(initQuery)
provide('postCommentTableHelper', tableHelper)
</script>
<template>
  <div class="post-comment-view">
    <post-comment-query
      v-model:query="query"
      @reset="restQuery"
      @search="tableHelper.reloadTableData({ query })"
    ></post-comment-query>
    <post-comment-table></post-comment-table>
  </div>
</template>

<style lang="scss" scoped>
.post-comment-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
