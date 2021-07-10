import axios from "axios";
import store from "../store/store";
import {finishLoading, startLoading} from "../store/actions/loading-bar.action";
import qs from "qs";
import {unauthorized} from "../store/actions/auth.action";

const {dispatch} = store;
const http = axios.create({
    baseURL: `${process.env.REACT_APP_RESOURCE_SERVER}/rest`
});

http.interceptors.request.use(
    config =>{
        dispatch(startLoading());
        const token = localStorage.getItem('token');
        if(token){
            config.headers.Authorization = `Bearer ${JSON.parse(token)['access_token']}`;
        }

        return config;
    },
    error =>{
        dispatch(finishLoading());
        return Promise.reject(error);
    }
);

http.interceptors.response.use(
    response =>{
        dispatch(finishLoading());
        return response;
    },
    async error =>{
        dispatch(finishLoading());
        const originalRequest = error.config;
        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            try {
                const accessToken = await refreshAccessToken();
                axios.defaults.headers.Authorization = `Bearer ${accessToken}`;

                return http(originalRequest);
            }
            catch (e) {
                return dispatch(unauthorized());
            }
        }

        return Promise.reject(error);
    }
);

function refreshAccessToken() {
    const headers = {
        "Content-Type": "application/x-www-form-urlencoded",
    };

    const token = JSON.parse(localStorage.getItem('token'));
    const params = {
        "refreshToken": token ? token['refresh_token'] : ""
    };

    return axios.post(`${process.env.REACT_APP_RESOURCE_SERVER}/rest/auth/refresh-access-token`, qs.stringify(params), {headers: headers})
        .then(response =>{
            const refreshedToken = response.data;
            token['access_token'] = refreshedToken['access_token'];
            token['refresh_token'] = refreshedToken['refresh_token'];
            localStorage.setItem('token', JSON.stringify(token));

            return Promise.resolve(token['access_token']);
        });
}

export default http;
