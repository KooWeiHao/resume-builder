import {Component, createRef} from "react";
import {Formik, Form, Field, ErrorMessage} from "formik";
import {withTranslation} from "react-i18next";
import * as Yup from "yup";
import Dropzone from 'react-dropzone';
import {Modal} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {connect} from "react-redux";
import {addOrUpdateResumeAboutMe} from "../../../store/actions/resume.action";

class AboutMe extends Component{
    constructor(props) {
        super(props);
        this.formAboutMe = createRef();

        this.state = {
            form:{
                data:{
                    name: "",
                    title: "",
                    email: "",
                    mobileNumber: "",
                    dateOfBirth: "",
                    nationality: "",
                    careerObjective: "",
                    profilePicture: null,
                    profilePictureSource: ""
                },
                ui:{
                    successful: false,
                    profilePictureError: null
                }
            },
            isSaveModalOpened: false
        }
    }

    validationSchema = Yup.object().shape({
        profilePicture: Yup.mixed().required(),

        name: Yup.string()
            .required(this.props.t("resume.page.modify.form.error.required")),

        email: Yup.string()
            .email(this.props.t("resume.page.modify.form.error.invalid.email"))
            .required(this.props.t("resume.page.modify.form.error.required")),

        mobileNumber: Yup.number()
            .required(this.props.t("resume.page.modify.form.error.required")),

        dateOfBirth: Yup.date()
            .required(this.props.t("resume.page.modify.form.error.required")),

        nationality: Yup.string()
            .required(this.props.t("resume.page.modify.form.error.required"))
    });

    setFormState = (category, mapObject)=>{
        const form = this.state.form;
        Object.keys(mapObject).forEach(key =>{
            form[category][key] = mapObject[key];
        });
        this.setState({form});
    }

    onOpenSaveModal = ()=>{
        this.setState({isSaveModalOpened: true});
    }

    onCloseSaveModal = ()=>{
        this.setState({isSaveModalOpened: false});
    }

    onSave = () =>{
        this.onOpenSaveModal();
    }

    onConfirmSave = () =>{
        const {profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective} = this.formAboutMe.current.values;

        this.props.addOrUpdateResumeAboutMe(profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective).then(res =>{
           console.log(res);
        }).catch(error =>{
            this.formAboutMe.current.setSubmitting(false);
            console.log(error);
        }).finally(()=>{
            this.formAboutMe.current.setSubmitting(false);
        });

        this.onCloseSaveModal();
    }

    onCancel = ()=>{

    }

    onDrop = (acceptedFiles, fileRejections)=>{
        if(fileRejections.length > 0){
            const profilePictureData = {
                profilePicture : null,
                profilePictureSource: ""
            };
            this.setFormState("data", profilePictureData);
            this.setFormState("ui", {"profilePictureError": "resume.page.modify.form.error.invalid.image"});
            this.formAboutMe.current.setFieldValue("profilePicture", null);
        }
        else{
            this.setFormState("ui", {"profilePictureError": null});
        }

        if(acceptedFiles[0]){
            URL.revokeObjectURL(this.state.form.data.profilePictureSource);
            const profilePictureData = {
                profilePicture : acceptedFiles[0],
                profilePictureSource: URL.createObjectURL(acceptedFiles[0])
            };
            this.setFormState("data", profilePictureData);
            this.formAboutMe.current.setFieldValue("profilePicture", acceptedFiles[0]);
        }
    }

    onValidateProfilePicture = ()=>{
        if(!this.state.form.data.profilePictureSource){
            this.setFormState("ui", {"profilePictureError": "resume.page.modify.form.error.required"});
        }
    }

    getClassName = (isSubmitting)=>{
        let className = "profile-picture-dropzone";

        if(this.state.form.ui.profilePictureError){
            className = `${className} border-danger`;
        }

        if(isSubmitting){
            className = `${className} pointer-events-none`;
        }

        return className;
    }

    render() {
        const {t} = this.props;
        const {form, isSaveModalOpened} = this.state;

        return (
            <div>
                <Formik initialValues={form.data} validationSchema={this.validationSchema} onSubmit={this.onSave} innerRef={this.formAboutMe}>
                    {({touched, errors, isSubmitting, values}) =>(
                        <Form>
                            {!form.ui.successful && (
                                <fieldset disabled={isSubmitting}>
                                    <div className="row">
                                        <div className="col-9"/>
                                        <div className="col-3 form-group required">
                                            <Dropzone onDrop={this.onDrop} onFileDialogCancel={this.onValidateProfilePicture} accept={['image/jpeg', 'image/png']} maxFiles={1} multiple={false}>
                                                {({getRootProps, getInputProps}) => (
                                                    <section className="container">
                                                        <label className="form-control-label" htmlFor="profilePicture">{t("resume.page.modify.form.profile.picture.title")}</label>
                                                        <div {...getRootProps({className: this.getClassName(isSubmitting)})}>
                                                            <input {...getInputProps()} className="form-control"/>
                                                            {(form.data.profilePictureSource) && (
                                                                <img alt={form.data.profilePicture.name} src={form.data.profilePictureSource} className="profile-picture-image"/>
                                                            )}
                                                            <div className={`profile-picture-placeholder ${!(form.data.profilePictureSource) ? 'opacity-1' : 'opacity-0'}`}>{t("resume.page.modify.form.profile.picture.placeholder")}</div>
                                                        </div>
                                                        {form.ui.profilePictureError && (
                                                            <div className="invalid-feedback font-italic display-unset">{t(form.ui.profilePictureError)}</div>
                                                        )}
                                                    </section>
                                                )}
                                            </Dropzone>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-6 form-group required">
                                            <label className="form-control-label" htmlFor="name">{t("resume.page.modify.form.name.title")}</label>
                                            <Field type="text" className={`form-control ${touched.name && errors.name ? "is-invalid" : ""}`} id="name" name="name" placeholder={t("resume.page.modify.form.name.placeholder")} />
                                            <ErrorMessage component="div" name="name" className="invalid-feedback font-italic"/>
                                        </div>
                                        <div className="col-6 form-group">
                                            <label className="form-control-label" htmlFor="title">{t("resume.page.modify.form.title.title")}</label>
                                            <Field type="text" className="form-control" id="title" name="title" placeholder={t("resume.page.modify.form.title.placeholder")} />
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-6 form-group required">
                                            <label className="form-control-label" htmlFor="email">{t("resume.page.modify.form.email.title")}</label>
                                            <Field type="email" className={`form-control ${touched.email && errors.email ? "is-invalid" : ""}`} id="email" name="email" placeholder={t("resume.page.modify.form.email.placeholder")} />
                                            <ErrorMessage component="div" name="email" className="invalid-feedback font-italic"/>
                                        </div>
                                        <div className="col-6 form-group required">
                                            <label className="form-control-label" htmlFor="mobileNumber">{t("resume.page.modify.form.mobile.number.title")}</label>
                                            <Field type="number" className={`form-control ${touched.mobileNumber && errors.mobileNumber ? "is-invalid" : ""}`} id="mobileNumber" name="mobileNumber" placeholder={t("resume.page.modify.form.mobile.number.placeholder")} />
                                            <ErrorMessage component="div" name="mobileNumber" className="invalid-feedback font-italic"/>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-6 form-group required">
                                            <label className="form-control-label" htmlFor="dateOfBirth">{t("resume.page.modify.form.date.of.birth.title")}</label>
                                            <Field type="date" className={`form-control ${touched.dateOfBirth && errors.dateOfBirth ? "is-invalid" : ""}`} id="dateOfBirth" name="dateOfBirth" placeholder={t("resume.page.modify.form.date.of.birth.placeholder")} />
                                            <ErrorMessage component="div" name="dateOfBirth" className="invalid-feedback font-italic"/>
                                        </div>
                                        <div className="col-6 form-group required">
                                            <label className="form-control-label" htmlFor="nationality">{t("resume.page.modify.form.nationality.title")}</label>
                                            <Field type="text" className={`form-control ${touched.nationality && errors.nationality ? "is-invalid" : ""}`} id="nationality" name="nationality" placeholder={t("resume.page.modify.form.nationality.placeholder")} />
                                            <ErrorMessage component="div" name="nationality" className="invalid-feedback font-italic"/>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-12 form-group">
                                            <label className="form-control-label" htmlFor="careerObjective">{t("resume.page.modify.form.career.objective.title")}</label>
                                            <Field as="textarea" rows="3" maxLength="300" className="form-control" id="careerObjective" name="careerObjective" placeholder={t("resume.page.modify.form.career.objective.placeholder")} />
                                            <div className="text-right aluminium-text small">{values.careerObjective.length}/300</div>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-12">
                                            <button name="formSave" type="submit" className="btn btn-primary" disabled={isSubmitting} onClick={this.onValidateProfilePicture}><FontAwesomeIcon icon="save" /> {t("resume.page.modify.form.button.save")}</button>
                                            {/*<button name="formCancel" type="button" className="btn btn-danger" disabled={isSubmitting} onClick={this.onCancel}><FontAwesomeIcon icon="times" /> {t("resume.page.modify.form.button.cancel")}</button>*/}
                                        </div>
                                    </div>
                                </fieldset>
                            )}
                        </Form>
                    )}
                </Formik>

                <Modal show={isSaveModalOpened} onHide={this.onCloseSaveModal} backdrop="static">
                    <Modal.Header closeButton>
                        <Modal.Title>{t("resume.page.modify.form.alert.save.title")}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{t("resume.page.modify.form.alert.save.message")}</Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary" onClick={this.onConfirmSave}>{t("resume.page.modify.form.button.confirm")}</button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

function mapStateToProps(state){
    return {};
}

function mapDispatchToProps(dispatch){
    return {
        addOrUpdateResumeAboutMe: (profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective) =>{
            return dispatch(addOrUpdateResumeAboutMe(profilePicture, name, title, email, mobileNumber, dateOfBirth, nationality, careerObjective));
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withTranslation()(AboutMe));
