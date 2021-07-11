import {Route, Switch} from "react-router-dom";
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";
import ResumeComponent from "./resume.component";

function ResumeRoute(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("resume.page.title.home")}</title>
            </Helmet>

            <Switch>
                <Route exact path="/resume" component={ResumeComponent} />
            </Switch>
        </>
    );
}

export default withTranslation()(ResumeRoute);
