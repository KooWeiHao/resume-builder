import {Route, Switch} from "react-router-dom";
import SignUpComponent from "./sign-up.component";

function SignUpRoute(){
    return (
        <Switch>
            <Route exact path="/sign-up" component={SignUpComponent} />
        </Switch>
    );
}

export default SignUpRoute;
