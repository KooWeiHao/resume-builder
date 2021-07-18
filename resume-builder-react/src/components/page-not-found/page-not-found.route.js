import {Route, Switch} from "react-router-dom";
import PageNotFoundComponent from "./page-not-found.component";

function PageNotFoundRoute(){
    return (
        <Switch>
            <Route exact path="/page-not-found" component={PageNotFoundComponent} />
        </Switch>
    );
}

export default PageNotFoundRoute;
