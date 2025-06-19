import { configureStore } from "@reduxjs/toolkit";
import loginReducer from "@/features/login/loginSlice";
import userReducer from "@/features/user/userSlice";

const store = configureStore({
  reducer: {
    user: userReducer,
    login: loginReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
