import { ElMessage, type UploadProps } from 'element-plus'
import _ from 'lodash'
import type { ValidateFieldsError } from 'async-validator/dist-types'
import type { Result } from '@/typings'
import type { ResponseEntity } from '@/apis/__generated/model/static'

export const assertSuccess = async <T>(result: T) => {
  return await new Promise<T>((resolve, reject) => {
    if (!_.isNil(result)) {
      ElMessage.success({ message: '操作成功' })
      resolve(result)
    } else {
      reject(result)
    }
  })
}
export const assertFormValidate = (callback: () => void) => {
  return (valid: boolean, fields: ValidateFieldsError | undefined) => {
    if (valid) {
      callback()
    } else {
      if (fields) {
        const messages: string[] = []
        for (const field in fields) {
          fields[field].forEach((error) => {
            if (error.message) {
              messages.push(error.message)
            }
          })
        }
        ElMessage.error({ message: messages.join('\n') })
      }
    }
  }
}

export const handleUploadSuccess: UploadProps['onSuccess'] = (
  response: Result<string>,
  uploadFile,
) => {
  console.log(response.result)
  console.log(undefined)
  if (uploadFile) {
    uploadFile.url = response.result
  }
}

export const downloadFile = (blob: Blob | number[] | ResponseEntity<number[]>) => {
  const b = blob as { data: Blob; filename: string }
  const url = window.URL.createObjectURL(b.data)
  const link = document.createElement('a')
  link.href = url
  link.download = b.filename // 设置文件名
  document.body.appendChild(link) // 一定要添加到DOM中
  // 触发点击下载
  link.click()
  // 下载完成后清理
  window.URL.revokeObjectURL(url)
  document.body.removeChild(link)
}
