import {combineReducers} from "redux";
import messageReducer from "./message.reducer";
import authReducer from "./auth.reducer";
import loadingBarReducer from "./loading-bar.reducer";

export default combineReducers({
    messageReducer,
    authReducer,
    loadingBarReducer
});
