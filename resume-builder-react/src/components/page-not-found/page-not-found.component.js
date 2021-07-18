import {withTranslation} from "react-i18next";
import {Helmet} from "react-helmet";

function PageNotFoundComponent(props){
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("page.not.found")}</title>
            </Helmet>

            <div>
                {t("page.not.found")}
            </div>
        </>
    );
}

export default withTranslation()(PageNotFoundComponent);
