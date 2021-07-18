import {Route, Switch} from "react-router-dom";
import LoginComponent from "./login.component";

function LoginRoute(){
    return (
        <Switch>
            <Route exact path="/login" component={LoginComponent} />
        </Switch>
    );
}

export default LoginRoute;
