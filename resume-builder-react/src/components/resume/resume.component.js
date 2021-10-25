import {withTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Helmet} from "react-helmet";
import {findResume} from "../../store/actions/resume.action";
import {connect} from "react-redux";
import {Component} from "react";
import SmartTableComponent from "../share/smart-table.component";
import moment from "moment";

class ResumeComponent extends Component{
    componentDidMount() {
        this.props.findResume(0, 10);
    }

    render() {
        const {t, match, resumes} = this.props;

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
                                            <Link to={`${match.url}/add/about-me`}>
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
                    <SmartTableComponent
                        className="table-striped table-bordered"
                        data={resumes}
                        columns={[
                            {Header: "", id: "index"},
                            {Header: "Code", accessor: "code"},
                            {Header: "Name", accessor: "name"},
                            {Header: "Created Date", accessor: res => moment(res["createdDate"]).local().format("DD-MM-YYYY")}
                        ]}
                    />
                </div>
            </>
        );
    }
}

function mapStateToProps(state){
    const {resumes, currentResume, totalResume, currentPage, totalPages} = state.resumeReducer;
    return {
        resumes, currentResume, totalResume, currentPage, totalPages
    };
}

function mapDispatchToProps(dispatch){
    return {
        findResume: (pageNumber, pageSize, code, name, createdDateStart, createdDateEnd) =>{
            return dispatch(findResume(pageNumber, pageSize, code, name, createdDateStart, createdDateEnd))
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withTranslation()(ResumeComponent));
