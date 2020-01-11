import "preact/debug";
import {render} from "preact";
import {createStore} from "redux"
import rootReducer from "./reducers/rootReducer";
import {Provider} from "react-redux"
import {get_all_stars, setStars} from "./data/stars";
import React from "preact/compat";
import StarTable from "./components/StarTable";

require("babel-polyfill");

const appName = "starchart";
const store = createStore(rootReducer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
get_all_stars().then(stars => store.dispatch(setStars(stars)));
const root = document.getElementById("root");
render(<Provider store={store}><StarTable/></Provider>, root);