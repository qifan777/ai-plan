<script lang="ts" setup>
    import TaskTable from './components/task-table.vue'
    import TaskQuery from './components/task-query.vue'
    import { useTableHelper } from '@/components/base/table/table-helper'
    import { api } from '@/utils/api-instance'
    import { provide } from 'vue'
    import type { TaskSpec } from '@/apis/__generated/model/static'
    import { useQueryHelper } from '@/components/base/query/query-helper'
    const initQuery: TaskSpec = {}
    const tableHelper = useTableHelper(
        api.taskForAdminController.query,
        api.taskForAdminController,
        initQuery
    )
    const { query, restQuery } = useQueryHelper<TaskSpec>(initQuery)
    provide('taskTableHelper', tableHelper)
</script>
<template>
    <div class="task-view">
        <task-query
                v-model:query="query"
                @reset="restQuery"
                @search="tableHelper.reloadTableData({ query })"
        ></task-query>
        <task-table></task-table>
    </div>
</template>

<style lang="scss" scoped>
  .task-view {
    background: white;
    padding: 20px;
    border-radius: 5px;
  }
</style>
