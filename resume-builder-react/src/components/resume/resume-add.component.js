import {withTranslation} from "react-i18next";
import {Helmet} from "react-helmet";

function ResumeAddComponent(props) {
    const {t} = props;

    return (
        <>
            <Helmet>
                <title>{t("resume.page.title.add")}</title>
            </Helmet>
        </>
    );
}

export default  withTranslation()(ResumeAddComponent);
