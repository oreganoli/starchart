import React from "preact/compat";
import {h} from "preact";
import {useStoreState} from "pullstate";
import {AppStore} from "../store";

export const ModalOverlay = (props) => {
    let [del, edit, error, searchWindow] = [
        useStoreState(AppStore, state => state.del),
        useStoreState(AppStore, s => s.edit),
        useStoreState(AppStore, s => s.error),
        useStoreState(AppStore, s => s.searchWindow)
    ];
    if (del == null && edit == null && error == null && !searchWindow) {
        return null;
    } else {
        return <div className={"modal_overlay"}>
            <div className={"modal_dimmer"}/>
            {props.children}
        </div>;
    }
};