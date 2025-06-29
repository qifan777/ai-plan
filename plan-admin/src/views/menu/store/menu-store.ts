import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { MenuInput, MenuSpec } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { MenuTreeDto } from '@/typings'

export const useMenuStore = defineStore('menu', () => {
  const initQuery: MenuSpec = {}
  const initForm: MenuInput = {
    menuType: 'DIRECTORY',
    name: '',
    path: '',
    visible: true,
    orderNum: 0,
  }
  const tableHelper = useTableHelper(api.menuController.query, api.menuController, initQuery)
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<MenuSpec>(initQuery)
  const updateForm = ref<MenuInput>({ ...initForm })
  const createForm = ref<MenuInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
export const buildMenuTree = (
  menus: MenuTreeDto[],
  parentId?: string,
  filter?: (menu: MenuTreeDto) => boolean,
) => {
  const children: MenuTreeDto[] = []
  menus.forEach((menu) => {
    if (menu.parent?.id === parentId && (filter != null ? filter(menu) : true)) {
      children.push(menu)
      const buildMenus = buildMenuTree(menus, menu.id, filter)
      if (buildMenus.length > 0) {
        menu.children = buildMenus
      }
    }
  })
  children.sort((a, b) => {
    return (a.orderNum || 99999) - (b.orderNum || 99999)
  })
  return children
}
export const menuQueryOptions = async (keyword: string, id: string) => {
  return (await api.menuController.query({ body: { query: { name: keyword, id: id } } })).content
}
