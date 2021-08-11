import {ADD_OR_UPDATE_RESUME} from "../actions/types";

const initialState = {resumes: [], currentResume: null};

const resumeReducer = (state = initialState, action) =>{
    const {type, payload} = action;

    switch (type){
        case ADD_OR_UPDATE_RESUME:
            return {
                resumes: state.resumes.map(resume =>{
                    if(resume.code === payload.code){
                        return {...resume, ...payload};
                    }
                    return resume;
                }),
                currentResume: payload
            };

        default:
            return state;
    }
}

export default resumeReducer;
