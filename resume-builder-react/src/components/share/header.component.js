import {withTranslation} from "react-i18next";
import {Dropdown} from "react-bootstrap";
import {connect} from "react-redux";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {logout} from "../../store/actions/auth.action";

function HeaderComponent(props){
    const {t, user, logout} = props;

    return (
        <div>
            <nav className="navbar navbar-light shadow-sm mb-1">
                <a className="navbar-brand mb-0 h1" href={'/home'}>{t("app.title")}</a>
                <Dropdown>
                    <Dropdown.Toggle variant="primary" className="dropdown-toggle-hide-icon">
                        {user.toUpperCase()} <FontAwesomeIcon icon="user-circle"/>
                    </Dropdown.Toggle>

                    <Dropdown.Menu align="right">
                        <Dropdown.Item onClick={logout}>
                            <FontAwesomeIcon icon="sign-out-alt"/> {t("button.logout")}
                        </Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
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

