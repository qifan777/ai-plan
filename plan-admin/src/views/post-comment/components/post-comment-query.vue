<script lang="ts" setup>
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { PostCommentSpec } from '@/apis/__generated/model/static'
import { postQueryOptions } from '@/views/post/post'

const emit = defineEmits<{ search: [value: PostCommentSpec]; reset: [] }>()
const query = defineModel<PostCommentSpec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="帖子">
        <remote-select
          label-prop="name"
          :query-options="postQueryOptions"
          v-model="query.postId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="评论内容">
        <el-input v-model.trim="query.content"></el-input>
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
