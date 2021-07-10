import './App.scss';
import {Helmet} from "react-helmet";
import {withTranslation} from "react-i18next";
import Routes from "./configs/routes";
import LoadingBar from "./configs/loading-bar";

import { library } from '@fortawesome/fontawesome-svg-core';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {fab} from '@fortawesome/free-brands-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';
library.add(fas, fab, far);

function App(props) {
    const {t} = props;

    return (
        <div>
            <Helmet defaultTitle={t("app.title")} titleTemplate={t("app.title") + " - %s"}/>

            <LoadingBar/>

            <Routes/>
        </div>
    );
}

export default withTranslation()(App);
