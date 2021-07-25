import {withTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Helmet} from "react-helmet";

function ResumeComponent(props){
    const {t, match} = props;

    return (
        <>
            <Helmet>
                <title>{t("resume.page.title.home")}</title>
            </Helmet>

            <div className="container-fluid pt-4">
                <div className="row">
                    <div className="col-12">
                        <div className="card">
                            <div className="card-header mariner-background">
                                <div className="row align-items-center">
                                    <div className="col-10">
                                        <span className="white-text font-weight-bold">{t("resume.page.home.search.form.title")}</span>
                                    </div>
                                    <div className="col-2">
                                        <Link to={`${match.url}/add`}>
                                            <button className="btn btn-sm btn-light float-right"><FontAwesomeIcon icon="plus" className="mariner-text"/> {t("app.button.add")}</button>
                                        </Link>
                                    </div>
                                </div>
                            </div>

                            <div className="card-body">

                            </div>

                            <div className="card-footer">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default withTranslation()(ResumeComponent);
