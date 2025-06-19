import { api } from '@/utils/api-instance'

export const postQueryOptions = async (keyword: string, id: string) => {
  return (await api.postForAdminController.query({ body: { query: { content: keyword, id } } }))
    .content
}
