import {connect} from "react-redux";
import {default as TopLoadingBar} from "react-top-loading-bar";
import {resetLoading} from "../store/actions/loading-bar.action";

function LoadingBar(props){
    const {progress, resetLoading} = props;

    return (
        <TopLoadingBar progress={progress} onLoaderFinished={resetLoading} loaderSpeed="1000" color="#dc3545"/>
    );
}

function mapStateToProps(state) {
    const { progress } = state.loadingBarReducer;
    return {
        progress
    };
}

function mapDispatchToProps(dispatch){
    return {
        resetLoading: () => {
            dispatch(resetLoading());
        }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(LoadingBar);
