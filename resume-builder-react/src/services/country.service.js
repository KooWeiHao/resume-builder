import http from "../configs/http";
import qs from "qs";

class CountryService{
    findCountryAll(locale){
        const headers ={
            "Content-Type": "application/x-www-form-urlencoded"
        };

        const params = {
            "locale": locale,
        };

        return http.post('/country/find-country-all', qs.stringify(params), {headers: headers});
    }
}

export default new CountryService();
