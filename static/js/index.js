import "preact/debug";
import {render, h} from "preact";
import React from "preact/compat";
import StarTable from "./components/StarTable";
import {ModalOverlay} from "./components/ModalOverlay";
import {DeleteModal} from "./components/DeleteModal";
import {EditModal} from "./components/EditModal";
import {ErrorModal} from "./components/ErrorModal";

render(<div className={"container"}>
    <ModalOverlay>
        <ErrorModal/>
        <DeleteModal/>
        <EditModal/>
    </ModalOverlay>
    <h1>starchart</h1>
    <StarTable/>
</div>, document.body);