<script setup lang="ts">
import type { UploadProps, UploadUserFile } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { handleUploadSuccess } from '@/utils/common'
withDefaults(defineProps<{ onSuccess?: UploadProps['onSuccess'] }>(), {
  onSuccess: () => handleUploadSuccess,
})
const API = import.meta.env.VITE_API_PREFIX + '/oss/upload'
const fileList = defineModel<UploadUserFile[]>({ required: false })
</script>

<template>
  <el-upload v-model:file-list="fileList" :action="API" :on-success="onSuccess" drag>
    <el-icon class="el-icon--upload">
      <upload-filled />
    </el-icon>
    <div class="el-upload__text">拖拽文件至这里或者 <em>点击上传</em></div>
    <template #tip>
      <div class="el-upload__tip">文件不能大于20M</div>
    </template>
  </el-upload>
</template>

<style scoped lang="scss"></style>
