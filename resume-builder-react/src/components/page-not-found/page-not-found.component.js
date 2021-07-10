import {withTranslation} from "react-i18next";

function PageNotFoundComponent(props){
    const {t} = props;

    return (
        <div>
            {t("page.not.found")}
        </div>
    );
}

export default withTranslation()(PageNotFoundComponent);
