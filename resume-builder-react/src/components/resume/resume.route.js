import {Route, Switch} from "react-router-dom";
import ResumeComponent from "./resume.component";
import ResumeModifyComponent from "./resume-modify.component";

function ResumeRoute(){
    return (
        <Switch>
            <Route exact path="/resume" component={ResumeComponent} />
            <Route exact path="/resume/:state/:category" component={ResumeModifyComponent} />
        </Switch>
    );
}

export default ResumeRoute;
