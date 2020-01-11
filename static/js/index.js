require("preact/debug");
import {render, h} from "preact";
import {createStore} from "redux"
import rootReducer from "./reducers/rootReducer";
import {Provider} from "react-redux"
import React from "preact/compat";
import StarTable from "./components/StarTable";
import {ModalOverlay} from "./components/ModalOverlay";
import {DeleteModal} from "./components/DeleteModal";

require("babel-polyfill");

const store = createStore(rootReducer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
render(<Provider store={store}>
    <ModalOverlay>
        <DeleteModal/>
    </ModalOverlay>
    <h1>starchart</h1>
    <StarTable/>
</Provider>, document.body);