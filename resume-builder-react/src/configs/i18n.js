import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import languageDetector from 'i18next-browser-languagedetector';

import commonEN from "../assets/locales/en/common.json";
import loginEn from "../assets/locales/en/login.json";
import signUpEn from "../assets/locales/en/sign-up.json";
import resumeEn from "../assets/locales/en/resume.json";

import commonZH from "../assets/locales/zh/common.json";

const resources = {
        en:{
                translation: {
                        ...commonEN,
                        ...loginEn,
                        ...signUpEn,
                        ...resumeEn
                }
        },
        zh:{
                translation: {
                        ...commonZH
                }
        }
};

i18next
    .use(languageDetector)
    .use(initReactI18next)
    .init({
        resources,
        debug: true,
        lng: localStorage.getItem('i18nextLng') || 'en',
        fallbackLng: 'en',
        supportedLngs: ['en', 'zh'],
        load: 'languageOnly'
    });

export default i18next;
