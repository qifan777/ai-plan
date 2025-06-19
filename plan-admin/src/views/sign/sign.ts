import { api } from '@/utils/api-instance'
export const signQueryOptions = async (keyword: string, id: string) => {
  return (await api.signForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
