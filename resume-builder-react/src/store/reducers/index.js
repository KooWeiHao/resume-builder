import {combineReducers} from "redux";
import messageReducer from "./message.reducer";
import authReducer from "./auth.reducer";
import loadingBarReducer from "./loading-bar.reducer";
import resumeReducer from "./resume.reducer";
import countryReducer from "./country.reducer";

export default combineReducers({
    messageReducer,
    authReducer,
    loadingBarReducer,
    resumeReducer,
    countryReducer
});
