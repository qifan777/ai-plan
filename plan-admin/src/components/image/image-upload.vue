<script lang="ts" setup>
import { computed } from 'vue'
import {
  ElMessage,
  ElUpload,
  type UploadFiles,
  type UploadProps,
  type UploadUserFile,
} from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { handleUploadSuccess } from '@/utils/common'
import type { Result } from '@/typings'

const API = import.meta.env.VITE_API_PREFIX + '/oss/upload'
const model = defineModel<string>({ required: false, default: '' })

const types = ['image/png', 'image/jpeg', 'image/webp']
const beforeImageUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (!types.includes(rawFile.type)) {
    ElMessage.error('Image picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Image picture size can not exceed 2MB!')
    return false
  }
  return true
}
const fileList = computed<UploadFiles>(() => {
  if (model.value) {
    return [
      { url: model.value, name: '', status: 'success', size: 0, uid: 0 },
    ] satisfies UploadFiles
  }
  return []
})
const handleImageSuccess: UploadProps['onSuccess'] = (response: Result<string>, uploadFile) => {
  model.value = response.result
  uploadFile.url = response.result
}
</script>
<template>
  <el-upload
    :file-list="fileList"
    :action="API"
    :before-upload="beforeImageUpload"
    :on-success="handleImageSuccess"
    list-type="picture-card"
  >
    <el-icon>
      <plus></plus>
    </el-icon>
  </el-upload>
</template>

<style scoped></style>
