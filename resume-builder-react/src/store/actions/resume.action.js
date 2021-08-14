import resumeService from "../../services/resume.service";
import {ADD_OR_UPDATE_RESUME} from "./types";

export const addOrUpdateResumeAboutMe = (profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective)=>(dispatch)=>{
    return resumeService.addOrUpdateResumeAboutMe(profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective)
        .then(response =>{
            dispatch({
                type: ADD_OR_UPDATE_RESUME,
                payload: response.data['resume']
            });

            return Promise.resolve(response.data['resume']);
        })
        .catch(error =>{
            const message = (error.response && error.response.data && error.response.data['message']) || error.message || error.toString();

            return Promise.reject(message);
        })
}
