import {SET_LOADING_BAR_PROGRESS} from "../actions/types";

const initialState = {progress: 0};

const loadingBarReducer = (state = initialState, action)=>{
    const {type, payload} = action;

    switch (type) {
        case SET_LOADING_BAR_PROGRESS:
            return {
                ...state,
                progress: payload
            };

        default:
            return state;
    }
};

export default loadingBarReducer;
