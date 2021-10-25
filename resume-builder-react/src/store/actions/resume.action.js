import resumeService from "../../services/resume.service";
import {ADD_OR_UPDATE_RESUME, FIND_RESUME} from "./types";

export const addOrUpdateResumeAboutMe = (profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective)=>(dispatch)=>{
    return resumeService.addOrUpdateResumeAboutMe(profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective)
        .then(response =>{
            dispatch({
                type: ADD_OR_UPDATE_RESUME,
                payload: response.data
            });

            return Promise.resolve();
        })
        .catch(error =>{
            const message = (error.response && error.response.data && error.response.data['message']) || error.message || error.toString();

            return Promise.reject(message);
        })
}

export const findResume = (pageNumber, pageSize, code, name, createdDateStart, createdDateEnd)=>(dispatch)=>{
    return resumeService.findResume(pageNumber, pageSize, code, name, createdDateStart, createdDateEnd)
        .then(response =>{
            dispatch({
                type: FIND_RESUME,
                payload: response.data
            });

            return Promise.resolve();
        })
        .catch(error =>{
            const message = (error.response && error.response.data && error.response.data['message']) || error.message || error.toString();

            return Promise.reject(message);
        })
}
