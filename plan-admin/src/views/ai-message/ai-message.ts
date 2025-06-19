import { api } from '@/utils/api-instance'

export const aiMessageQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiMessageForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
