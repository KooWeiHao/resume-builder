import {withTranslation} from "react-i18next";

function ResumeComponent(props){
    const {t} = props;

    return (
        <div>
            {t("app.title")}
        </div>
    );
}

export default withTranslation()(ResumeComponent);
