import {ADD_OR_UPDATE_RESUME, FIND_RESUME} from "../actions/types";

const initialState = {resumes: [], currentResume: null, totalResume: 0, currentPage: 0, totalPages: 0};

const resumeReducer = (state = initialState, action) =>{
    const {type, payload} = action;

    switch (type){
        case ADD_OR_UPDATE_RESUME:
            return {
                ...state,
                resumes: state.resumes.map(resume =>{
                    if(resume.code === payload.code){
                        return {...resume, ...payload};
                    }
                    return resume;
                }),
                currentResume: payload
            };

        case FIND_RESUME:
            return {
                ...state,
                resumes: payload['result'],
                totalResume: payload['totalResultCount'],
                currentPage: payload['currentPage'],
                totalPages: payload['totalPages']
            }

        default:
            return state;
    }
}

export default resumeReducer;
