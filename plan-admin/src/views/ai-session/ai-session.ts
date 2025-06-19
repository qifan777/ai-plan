import { api } from '@/utils/api-instance'

export const aiSessionQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiSessionForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
