import http from "../configs/http";

class ResumeService {
    addOrUpdateResumeAboutMe(profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective){
        const headers ={
            "Content-Type": "multipart/form-data"
        };

        const params = new FormData();
        params.append("profilePicture", profilePicture);
        params.append("name", name);
        params.append("title", title);
        params.append("email", email);
        params.append("mobileNumber", mobileNumber);
        params.append("dateOfBirth", dateOfBirth);
        params.append("nationality", nationality);
        params.append("careerObjective", careerObjective);

        return http.post('/resume/add-or-update-resume-about-me', params, {headers: headers});
    }

    findResume(pageNumber, pageSize, code, name, createdDateStart, createdDateEnd){
        const headers ={
            "Content-Type": "application/json"
        };

        const params = {
            "code": code,
            "name": name,
            "createdDateStart": createdDateStart,
            "createdDateEnd": createdDateEnd,
            "pageNumber": pageNumber,
            "pageSize": pageSize
        };

        return http.post('/resume/find-resume', params, {headers: headers});

    }
}

export default new ResumeService();
