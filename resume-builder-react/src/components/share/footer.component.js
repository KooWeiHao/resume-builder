import {withTranslation} from "react-i18next";

function FooterComponent(props){
    const {t} = props;

    return (
        <div className="text-center bg-light p-2 fixed-bottom">
            {t("app.trademark")}
        </div>
    );
}

export default withTranslation()(FooterComponent);
