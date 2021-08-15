import countryService from "../../services/country.service";
import {FIND_COUNTRY_ALL} from "./types";

export const findCountryAll = (locale) =>(dispatch) =>{
    return countryService.findCountryAll(locale)
        .then(response =>{
            dispatch({
                type: FIND_COUNTRY_ALL,
                payload: response.data
            });

            return Promise.resolve();
        })
}
