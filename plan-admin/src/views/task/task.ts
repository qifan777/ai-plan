import { api } from '@/utils/api-instance'

export const taskQueryOptions = async (keyword: string, id: string) => {
  return (await api.taskForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
