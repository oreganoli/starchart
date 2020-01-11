import React from "preact/compat";
import {h} from "preact";
import {useSelector} from "react-redux";

export const ModalOverlay = (props) => {
    let [del, edit, error, searchWindow] = [
        useSelector(state => state.del),
        useSelector(state => state.edit),
        useSelector(state => state.error),
        useSelector(state => state.searchWindow)
    ];
    if (del != null || edit != null || error != null || searchWindow) {
        return <div className={"modal_overlay"}>
            <div className={"modal_dimmer"}/>
            {props.children}
        </div>;
    } else {
        return null;
    }
};