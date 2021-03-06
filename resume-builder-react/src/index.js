import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from "react-router-dom";
import "./configs/i18n";
import {Provider} from "react-redux";
import store from "./store/store";

const App = React.lazy(() => import('./App'));

ReactDOM.render(
    <Provider store={store}>
        {/*<React.StrictMode>*/}
            <BrowserRouter>
                <React.Suspense fallback="Loading...">
                    <App />
                </React.Suspense>
            </BrowserRouter>
        {/*</React.StrictMode>*/}
    </Provider>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
