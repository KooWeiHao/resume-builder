import {connect} from "react-redux";
import {Redirect, Route, Switch, withRouter} from "react-router-dom";
import HeaderComponent from "../components/share/header.component";
import FooterComponent from "../components/share/footer.component";
import LoginRoute from "../components/login/login.route";
import SignUpRoute from "../components/sign-up/sign-up.route";
import HomeRoute from "../components/home/home.route";
import PageNotFoundRoute from "../components/page-not-found/page-not-found.route";
import {Component} from "react";
import {clearMessage} from "../store/actions/message.action";

class Routes extends Component{
    componentDidMount() {
        const {history, clearMessage} = this.props;
        this.unlisten = history.listen(()=>{
            clearMessage();
        });
    }

    componentWillUnmount() {
        this.unlisten();
    }

    render() {
        const {isAuthenticated} = this.props;

        return (
            <Switch>
                <Route exact path='/'>
                    <Redirect to="/home" />
                </Route>
                <Route path="/login" component={LoginRoute} />
                <Route path="/sign-up" component={SignUpRoute} />
                <AuthenticatedRoute path="/home" component={HomeRoute} isAuthenticated={isAuthenticated}/>

                <Route path="/page-not-found" component={PageNotFoundRoute} />
                <Redirect to="/page-not-found" />
            </Switch>
        );
    }
}

function AuthenticatedRoute({isAuthenticated, ...route}){
    return isAuthenticated
        ? (
            <>
                <HeaderComponent/>
                <FooterComponent/>
                <Route {...route}/>
            </>
        )
        : (
            <Redirect to={{pathname: "/login"}}/>
        );
}


function mapStateToProps(state) {
    const { isAuthenticated } = state.authReducer;
    return {
        isAuthenticated
    };
}

function mapDispatchToProps(dispatch){
    return {
        clearMessage: () => {
            dispatch(clearMessage());
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Routes));
