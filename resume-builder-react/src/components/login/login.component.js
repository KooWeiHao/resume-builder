import {withTranslation} from "react-i18next";
import {Component} from "react";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import {login} from "../../store/actions/auth.action";
import {clearMessage} from "../../store/actions/message.action";
import * as Yup from "yup";
import {Formik, Form, Field, ErrorMessage} from "formik";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Helmet} from "react-helmet";

class LoginComponent extends Component{
    constructor(props) {
        super(props);

        this.state = {
            form:{
                data:{
                    username: "",
                    password: ""
                }
            }
        };
    }

    validationSchema = Yup.object().shape({
        username: Yup.string()
            .required(this.props.t("login.form.error.required")),

        password: Yup.string()
            .required(this.props.t("login.form.error.required"))
    });

    onSubmit = (values, {setSubmitting}) =>{
        const {clearMessage, login, history} = this.props;
        clearMessage();

        const {username, password} = values;

        login(username, password).then(() =>{
            history.push('/resume');
        }).catch(()=>{
            setSubmitting(false);
        });
    };

    onChange = (event, handleChange)=>{
        handleChange(event);
        this.props.clearMessage();
    }

    render(){
        const {t, message} = this.props;
        const {form} = this.state;

        return (
            <>
                <Helmet>
                    <title>{t("login.page.title")}</title>
                </Helmet>

                <div className="row form-center">
                    <div className="col-12">
                        <div className="card">
                            <div className="card-header text-center mariner-background">
                                <h2 className="white-text">{t("login.form.title")}</h2>
                            </div>

                            <div className="card-body">
                                <Formik initialValues={form.data} validationSchema={this.validationSchema} onSubmit={this.onSubmit}>
                                    {({touched, errors, isSubmitting, handleChange}) => (
                                        <Form>
                                            <fieldset disabled={isSubmitting}>
                                                <div className="row form-group required">
                                                    <div className="col-12">
                                                        <label className="form-control-label" htmlFor="username"><FontAwesomeIcon icon="user" /> {t("login.form.username.title")}</label>
                                                        <Field type="text" className={`form-control ${touched.username && errors.username ? "is-invalid" : ""}`} id="username" name="username" placeholder={t("login.form.username.placeholder")} onChange={e => this.onChange(e, handleChange)}/>
                                                        <ErrorMessage component="div" name="username" className="invalid-feedback font-italic"/>
                                                    </div>
                                                </div>
                                                <div className="row form-group required">
                                                    <div className="col-12">
                                                        <label className="form-control-label" htmlFor="password"><FontAwesomeIcon icon="lock" /> {t("login.form.password.title")}</label>
                                                        <Field type="password" className={`form-control ${touched.password && errors.password ? "is-invalid" : ""}`} id="password" name="password" placeholder={t("login.form.password.placeholder")} onChange={e => this.onChange(e, handleChange)}/>
                                                        <ErrorMessage component="div" name="password" className="invalid-feedback font-italic"/>
                                                    </div>
                                                </div>
                                                <div className="row form-group">
                                                    <div className="col-12">
                                                        <button name="formLogin" type="submit" className="btn btn-primary btn-block" disabled={isSubmitting}>{t("login.form.button.login")}</button>
                                                    </div>
                                                </div>
                                            </fieldset>

                                            {message && (
                                                <div className="alert alert-danger" role="alert">
                                                    {t(`login.form.error.${message}`)}
                                                </div>
                                            )}
                                        </Form>
                                    )}
                                </Formik>
                            </div>

                            <div className="card-footer text-right">
                                <Link to="/sign-up" className="aluminium-link">{t("login.form.button.sign.up")}</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}

function mapStateToProps(state){
    const { message } = state.messageReducer;
    return {
        message,
    };
}

function mapDispatchToProps(dispatch){
    return {
        login: (username, password) => {
            return dispatch(login(username, password));
        },

        clearMessage: () => {
            dispatch(clearMessage());
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withTranslation()(LoginComponent));
