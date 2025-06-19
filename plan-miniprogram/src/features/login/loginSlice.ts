import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "@/store";

export const loginSlice = createSlice({
  name: "login",
  initialState: {
    registerShow: false,
  },
  reducers: {
    setRegisterShow: (state, action: PayloadAction<boolean>) => {
      console.log("setRegisterShow", action.payload);
      state.registerShow = action.payload;
    },
  },
});
export const selectRegisterShow = (state: RootState) =>
  state.login.registerShow;
export const { setRegisterShow } = loginSlice.actions;
export default loginSlice.reducer;
