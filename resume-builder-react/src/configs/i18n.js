import i18next from "i18next";
import { initReactI18next } from "react-i18next";

import commonEN from "../assets/locales/en/common.json";
import loginEn from "../assets/locales/en/login.json";
import signUpEn from "../assets/locales/en/sign-up.json";
import homeEn from "../assets/locales/en/home.json";

const resources = {
        en:{
                translation: {
                        ...commonEN,
                        ...loginEn,
                        ...signUpEn,
                        ...homeEn
                }
        }
};

i18next
    .use(initReactI18next)
    .init({
        resources,
        debug: true,
        lng: 'en',
        fallbackLng: 'en',
        supportedLngs: ['en'],
        load: 'languageOnly'
    });

export default i18next;
