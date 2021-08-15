import {FIND_COUNTRY_ALL} from "../actions/types";

const initialState = [];

const countryReducer = (state = initialState, action)=>{
    const {type, payload} = action;

    switch (type) {
        case FIND_COUNTRY_ALL:
            return payload;

        default:
            return state;
    }
}

export default countryReducer;
