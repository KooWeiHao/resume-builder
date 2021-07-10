import {Route, Switch} from "react-router-dom";
import HomeComponent from "./home.component";
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";

function HomeRoute(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("home.page.title")}</title>
            </Helmet>

            <Switch>
                <Route exact path="/home" component={HomeComponent} />
            </Switch>
        </>
    );
}

export default withTranslation()(HomeRoute);
