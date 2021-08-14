import {SIGN_UP_SUCCESS, SIGN_UP_FAIL, SET_MESSAGE, LOGIN_FAIL, LOGIN_SUCCESS, UNAUTHORIZED, LOGOUT} from "./types";
import authService from "../../services/auth.service";

export const signUp = (username, password) =>(dispatch) =>{
    return authService.signUp(username, password)
        .then(response =>{
            dispatch({
                type: SIGN_UP_SUCCESS
            });

            dispatch({
                type: SET_MESSAGE,
                payload: response.data['username']
            });

            return Promise.resolve();
        })
        .catch(error =>{
            const message = (error.response && error.response.data && error.response.data['message']) || error.message || error.toString();

            dispatch({
                type: SIGN_UP_FAIL
            });

            dispatch({
                type: SET_MESSAGE,
                payload: message
            });

            return Promise.reject();
        });
};

export const login = (username, password) =>(dispatch) =>{
    return authService.login(username, password)
        .then(response =>{
            const token = response.data;
            token['user'] = username;
            localStorage.setItem('token', JSON.stringify(token));

            dispatch({
                type: LOGIN_SUCCESS,
                payload: username
            });

            return Promise.resolve();
        })
        .catch(error =>{
            const message = (error.response && error.response.data && error.response.data['message']) || error.message || error.toString();

            dispatch({
                type: LOGIN_FAIL
            });

            dispatch({
                type: SET_MESSAGE,
                payload: message
            });

            return Promise.reject();
        });
};

export const logout = ()=>async (dispatch) => {
    const token = JSON.parse(localStorage.getItem('token'));
    const refreshToken = token ? token['refresh_token'] : "";

    await authService.logout(refreshToken);
    localStorage.removeItem('token');

    dispatch({
        type: LOGOUT
    });
};

export const unauthorized = ()=>(dispatch) =>{
    localStorage.removeItem('token');

    dispatch({
        type: UNAUTHORIZED
    });
}
