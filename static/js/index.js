import "preact/debug";
import {render, h} from "preact";
import React from "preact/compat";
import StarTable from "./components/StarTable";
import {ModalOverlay} from "./components/ModalOverlay";
import {DeleteModal} from "./components/DeleteModal";

render(<div>
    <ModalOverlay>
        <DeleteModal/>
    </ModalOverlay>
    <h1>starchart</h1>
    <StarTable/>
</div>, document.body);