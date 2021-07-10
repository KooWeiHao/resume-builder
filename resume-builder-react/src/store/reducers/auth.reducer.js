import {LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT, SIGN_UP_FAIL, SIGN_UP_SUCCESS, UNAUTHORIZED} from "../actions/types";

const token = localStorage.getItem('token');
const initialState = token
    ? {isAuthenticated: true, user: JSON.parse(token)['user']}
    : {isAuthenticated: false, user: null};

const authReducer = (state = initialState, action) =>{
    const {type, payload} = action;

    switch (type) {
        case SIGN_UP_SUCCESS:
            return {
                ...state,
                isAuthenticated: false
            };

        case SIGN_UP_FAIL:
            return {
                ...state,
                isAuthenticated: false
            };

        case LOGIN_SUCCESS:
            return {
                ...state,
                isAuthenticated: true,
                user: payload
            };

        case LOGIN_FAIL: case LOGOUT: case UNAUTHORIZED:
            return {
                ...state,
                isAuthenticated: false,
                user: null
            };

        default:
            return state;
    }
};

export default authReducer;
