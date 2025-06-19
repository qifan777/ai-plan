<script setup lang="ts">
import type { UploadProps, UploadUserFile } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { handleUploadSuccess } from '@/utils/common'
import { computed } from 'vue'
withDefaults(defineProps<{ onSuccess?: UploadProps['onSuccess'] }>(), {
  onSuccess: () => handleUploadSuccess,
})
const API = import.meta.env.VITE_API_PREFIX + '/oss/upload'
const file = defineModel<UploadUserFile>({ required: false })
const handleFileChange = (files: UploadUserFile[]) => {
  if (files.length) {
    file.value = files[0]
  }
}
const fileList = computed(() => {
  if (file.value) {
    return file.value.uid ? [file.value] : []
  }
  return []
})
</script>

<template>
  <el-upload
    :file-list="fileList"
    @update:file-list="handleFileChange"
    :limit="1"
    :action="API"
    :on-success="onSuccess"
    drag
  >
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
