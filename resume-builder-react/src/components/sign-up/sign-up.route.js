import {Route, Switch} from "react-router-dom";
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";
import SignUpComponent from "./sign-up.component";

function SignUpRoute(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("sign.up.page.title")}</title>
            </Helmet>

            <Switch>
                <Route exact path="/sign-up" component={SignUpComponent} />
            </Switch>
        </>
    );
}

export default withTranslation()(SignUpRoute);
