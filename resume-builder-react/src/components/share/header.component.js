import {withTranslation} from "react-i18next";
import {Dropdown} from "react-bootstrap";
import {connect} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {logout} from "../../store/actions/auth.action";
import {ReactComponent as LogoImage} from "../../assets/images/logo.svg";
import i18n from '../../configs/i18n';

function HeaderComponent(props){
    const {t, user, logout} = props;

    const changeLanguage = async()=>{
        const language = ['en', 'cn'].filter(l =>{
            return l !== i18n.language;
        })[0];
        await i18n.changeLanguage(language);
    }

    return (
        <div>
            <nav className="navbar navbar-expand-sm navbar-light shadow-sm mb-1">
                <a className="navbar-brand mb-0 h1" href={'/resume'}>
                    <LogoImage className="align-top"/>
                    {t("app.title")}
                </a>
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item" onClick={changeLanguage}>
                        <div className="nav-link font-weight-bold" role="button">
                            {t(`app.language.${i18n.language}`)}
                        </div>
                    </li>
                    <li className="nav-item">
                        <Dropdown>
                            <Dropdown.Toggle variant="primary" className="dropdown-toggle-hide-icon">
                                {user.toUpperCase()} <FontAwesomeIcon icon="user-circle"/>
                            </Dropdown.Toggle>

                            <Dropdown.Menu align="right">
                                <Dropdown.Item onClick={logout}>
                                    <FontAwesomeIcon icon="sign-out-alt"/> {t("app.button.logout")}
                                </Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </li>
                </ul>
            </nav>
        </div>
    );
}

function mapStateToProps(state){
    const { user } = state.authReducer;
    return {
        user,
    };
}

function mapDispatchToProps(dispatch){
    return {
        logout: () =>{
            dispatch(logout());
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withTranslation()(HeaderComponent));

