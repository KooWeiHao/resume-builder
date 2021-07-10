import {withTranslation} from "react-i18next";
import {Component} from "react";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import * as Yup from "yup";
import {Formik, Form, Field, ErrorMessage} from "formik";
import {signUp} from "../../store/actions/auth.action";
import {clearMessage} from "../../store/actions/message.action";
import {ReactComponent as SuccessImage} from "../../assets/images/success.svg";

class SignUpComponent extends Component{
    constructor(props) {
        super(props);

        this.state = {
            form:{
                data:{
                    username: "",
                    password: ""
                },
                ui:{
                    successful: false
                }
            }
        };
    }

    validationSchema = Yup.object().shape({
        username: Yup.string()
            .required(this.props.t("sign.up.form.error.required")),

        password: Yup.string()
            .min(7, this.props.t("sign.up.form.error.password.minimum.length"))
            .required(this.props.t("sign.up.form.error.required"))
    });

    onSubmit = (values, {setSubmitting}) =>{
        const {clearMessage, signUp} = this.props;
        clearMessage();

        const {username, password} = values;
        const setSuccessfulState = (value)=>{
            const {ui} = this.state.form;
            ui.successful = value;
            this.setState({ui});
        };
        setSuccessfulState(false);

        signUp(username, password).then(() =>{
            setSuccessfulState(true);
        }).catch(()=>{
            setSuccessfulState(false);
        }).finally(()=>{
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
            <div className="row form-center">
                <div className="col-12">
                    <div className="card">
                        <div className="card-header text-center mariner-background">
                            <h2 className="white-text">{t("sign.up.form.title")}</h2>
                        </div>

                        <div className="card-body">
                            <Formik initialValues={form.data} validationSchema={this.validationSchema} onSubmit={this.onSubmit}>
                                {({ touched, errors, isSubmitting, handleChange }) => (
                                    <Form>
                                        {!form.ui.successful && (
                                            <fieldset disabled={isSubmitting}>
                                                <div className="row form-group required">
                                                    <div className="col-12">
                                                        <label className="form-control-label" htmlFor="username"><FontAwesomeIcon icon="user" /> {t("sign.up.form.username.title")}</label>
                                                        <Field type="text" className={`form-control ${touched.username && errors.username ? "is-invalid" : ""}`} id="username" name="username" placeholder={t("sign.up.form.username.placeholder")} onChange={e => this.onChange(e, handleChange)}/>
                                                        <ErrorMessage component="div" name="username" className="invalid-feedback font-italic"/>
                                                    </div>
                                                </div>
                                                <div className="row form-group required">
                                                    <div className="col-12">
                                                        <label className="form-control-label" htmlFor="password"><FontAwesomeIcon icon="lock" /> {t("sign.up.form.password.title")}</label>
                                                        <Field type="password" className={`form-control ${touched.password && errors.password ? "is-invalid" : ""}`} id="password" name="password" placeholder={t("sign.up.form.password.placeholder")} onChange={e => this.onChange(e, handleChange)}/>
                                                        <ErrorMessage component="div" name="password" className="invalid-feedback font-italic"/>
                                                    </div>
                                                </div>
                                                <div className="row form-group">
                                                    <div className="col-12">
                                                        <button name="formSignup" type="submit" className="btn btn-primary btn-block" disabled={isSubmitting}>{t("sign.up.form.button.sign.up")}</button>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        )}

                                        {message && (form.ui.successful
                                            ? <>
                                                <div className="row text-center">
                                                    <div className="col-12 pb-3">
                                                        <SuccessImage className="mariner-fill"/>
                                                    </div>
                                                </div>
                                                <div className="alert alert-success" role="alert">
                                                    {t("sign.up.message.account.created", {username: message})}
                                                </div>
                                                <div className="row text-right">
                                                    <div className="col-12">
                                                        <Link to="/login">
                                                            <button name="formSignUp" className="btn btn-primary">
                                                                {t("sign.up.form.button.login")}
                                                            </button>
                                                        </Link>
                                                    </div>
                                                </div>
                                            </>
                                            : <div className="alert alert-danger" role="alert">
                                                {t(`sign.up.form.error.${message}`)}
                                            </div>
                                        )}
                                    </Form>
                                )}
                            </Formik>
                        </div>

                        {!form.ui.successful && (
                            <div className="card-footer text-sm-right">
                                <Link to="/login" className="aluminium-link">{t("sign.up.form.button.login")}</Link>
                            </div>
                        )}
                    </div>
                </div>
            </div>
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
        signUp: (username, password) => {
            return dispatch(signUp(username, password));
        },

        clearMessage: () => {
            dispatch(clearMessage());
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withTranslation()(SignUpComponent));
