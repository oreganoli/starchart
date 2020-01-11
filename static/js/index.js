import "preact/debug";
import {render, h} from "preact";
import {createStore} from "redux"
import rootReducer from "./reducers/rootReducer";
import {Provider} from "react-redux"
import React from "preact/compat";
import StarTable from "./components/StarTable";

require("babel-polyfill");

const store = createStore(rootReducer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
const root = document.getElementById("root");
render(<Provider store={store}>
    <h1>starchart</h1>
    <StarTable/>
</Provider>, root);