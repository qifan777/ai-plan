import { api } from '@/utils/api-instance'

export const postCommentQueryOptions = async (keyword: string, id: string) => {
  return (
    await api.postCommentForAdminController.query({ body: { query: { content: keyword, id } } })
  ).content
}
