import {Route, Switch} from "react-router-dom";
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";
import PageNotFoundComponent from "./page-not-found.component";

function PageNotFoundRoute(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("page.not.found")}</title>
            </Helmet>

            <Switch>
                <Route exact path="/page-not-found" component={PageNotFoundComponent} />
            </Switch>
        </>
    );
}

export default withTranslation()(PageNotFoundRoute);
