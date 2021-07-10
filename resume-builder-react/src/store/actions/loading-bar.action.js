import {SET_LOADING_BAR_PROGRESS} from "./types";

export const startLoading = () =>(dispatch) =>{
    let initialProgress = 30;
    let load = 0;

    while (load < 3){
        dispatch({
            type: SET_LOADING_BAR_PROGRESS,
            payload: initialProgress
        })
        initialProgress += 30;
        load++;
    }
};

export const finishLoading = () =>({
    type: SET_LOADING_BAR_PROGRESS,
    payload: 100
});

export const resetLoading = () =>({
    type: SET_LOADING_BAR_PROGRESS,
    payload: 100
});
