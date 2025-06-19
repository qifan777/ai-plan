import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

const BASE_URL = import.meta.env.VITE_API_PREFIX
export const request = axios.create({
  baseURL: BASE_URL,
  timeout: 600000,
})
request.interceptors.request.use(
  (config) => {
    if (config.url && config.url.endsWith('blob')) {
      config.responseType = 'blob'
    }
    return config
  },
  (error) => {},
)
request.interceptors.response.use(
  (res) => {
    if (res.data instanceof Blob) {
      return {
        data: res.data,
        filename: decodeURIComponent(
          res.headers['content-disposition'].split(';')[1].split('=')[1],
        ),
      }
    }
    return res.data.result
  },
  ({ response }) => {
    if (response.data.code !== 1) {
      if (response.data instanceof Blob && response.data.type == 'application/json') {
        const reader = new FileReader()

        reader.onload = function (event) {
          if (!event) return
          const jsonString = event.target!.result as string
          const jsonObject = JSON.parse(jsonString)
          console.log(jsonObject)
          ElMessage.warning({ message: jsonObject.msg })
        }
        reader.readAsText(response.data)
      } else {
        ElMessage.warning({ message: response.data.msg })
      }
    }
    if (response.data.code === 1001010) {
      router.push('/rest-password')
    }
    if (response.data.code === 1001007 || response.data.code === 1001008) {
      console.log(1001007)
      router.push('/login')
    } else {
      /* empty */
    }
    return Promise.reject(response.data.result)
  },
)
