import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserDto } from "@/apis/__generated/model/dto";
import { api } from "@/utils/api-instance";
import type { AppDispatch, RootState } from "@/store";

type User = UserDto["UserRepository/USER_ROLE_FETCHER"];
type UserState = {
  user?: User;
};

const initialState: UserState = {};
export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUserInfo: (state, action: PayloadAction<User>) => {
      state.user = {
        ...action.payload,
        rolesView: [...action.payload.rolesView],
      };
    },
    resetUserInfo: (state) => {
      state.user = undefined;
    },
  },
});
export const { setUserInfo, resetUserInfo } = userSlice.actions;
export const getUserInfo = () => {
  return async (dispatch: AppDispatch) => {
    const res = await api.userForFrontController.getUserInfo();
    dispatch(setUserInfo(res));
  };
};
export const selectUserInfo = (state: RootState) => {
  return state.user.user;
};
export const checkRoles = (state: RootState, roles: string[]) => {
  return (
    roles.filter((role) => {
      return state.user.user?.rolesView.map((row) => row.name).includes(role);
    }).length > 0
  );
};

export default userSlice.reducer;
