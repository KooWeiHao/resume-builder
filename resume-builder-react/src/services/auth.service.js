import http from "../configs/http";
import qs from 'qs';

class AuthService{
    signUp(username, password){
        const headers ={
            "Content-Type": "application/json"
        };

        const params = {
            "username": username,
            "password": password
        };

        return http.post('/auth/sign-up', params, {headers: headers});
    }

    login(username, password){
        const headers ={
            "Content-Type": "application/x-www-form-urlencoded"
        };

        const params = {
            "username": username,
            "password": password
        };

        return http.post('/auth/login', qs.stringify(params), {headers: headers});
    }

    logout(refreshToken){
        const headers ={
            "Content-Type": "application/x-www-form-urlencoded"
        };

        const params = {
            "refreshToken": refreshToken
        };

        return http.post('/auth/logout', qs.stringify(params), {headers: headers});
    }
}

export default new AuthService();
