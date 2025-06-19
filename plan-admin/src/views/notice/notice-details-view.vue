<script setup lang="ts">
import { onActivated, reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { NoticeInput } from '@/apis/__generated/model/static'
import { assertFormValidate } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { useFormHelper } from '@/components/base/form/form-helper'

const props = defineProps<{ id?: string }>()
const formRef = ref<FormInstance>()
const initForm: NoticeInput = { classroomId: '', active: false, content: '', title: '' }
const { formData: form, restForm } = useFormHelper<NoticeInput>(initForm)
const rules = reactive<FormRules<NoticeInput>>({
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
  active: [{ required: true, message: '请输入是否激活', trigger: 'blur' }],
})
const handleConfirm = () => {
  formRef.value?.validate(
    assertFormValidate(() =>
      api.noticeForAdminController.save({ body: form.value }).then(async (res) => {
        form.value.id = res
        ElMessage.success('操作成功')
      }),
    ),
  )
}
onActivated(() => {
  if (props.id) {
    api.noticeForAdminController.findById({ id: props.id }).then((res) => {
      form.value = { ...res, classroomId: res.classroom.id }
    })
  } else {
    restForm()
  }
})
</script>

<template>
  <div class="form">
    <el-form labelWidth="120" class="form" ref="formRef" :model="form" :rules="rules">
      <el-form-item label="标题" prop="title">
        <el-input v-model.trim="form.title"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="form.content" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="是否激活" prop="active">
        <el-switch v-model="form.active"></el-switch>
      </el-form-item>
    </el-form>
    <el-row justify="center">
      <el-button type="primary" @click="handleConfirm">提交</el-button>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.form {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
