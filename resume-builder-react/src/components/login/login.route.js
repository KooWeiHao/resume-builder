import {Route, Switch} from "react-router-dom";
import LoginComponent from "./login.component";
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";

function LoginRoute(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("login.page.title")}</title>
            </Helmet>

            <Switch>
                <Route exact path="/login" component={LoginComponent} />
            </Switch>
        </>
    );
}

export default withTranslation()(LoginRoute);
