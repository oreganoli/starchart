import React from "preact/compat";
import {h} from "preact";

export const ModalOverlay = (props) => {
    return <div className={"modal_overlay"}>
        <div className={"modal_dimmer"}/>
        {props.children}
    </div>
};