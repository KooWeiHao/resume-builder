import {Route, Switch} from "react-router-dom";
import ResumeComponent from "./resume.component";
import ResumeAddComponent from "./resume-add.component";

function ResumeRoute(){
    return (
        <Switch>
            <Route exact path="/resume" component={ResumeComponent} />
            <Route exact path="/resume/add" component={ResumeAddComponent} />
        </Switch>
    );
}

export default ResumeRoute;
