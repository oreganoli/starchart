import React from "preact/compat";
import {h} from "preact";
import {AppStore} from "../store";
import {useStoreState} from "pullstate";
import {set_error} from "../data/error";

export const ErrorModal = () => {
    let error = useStoreState(AppStore, s => s.error);
    console.log(error);
    if (error == null) {
        return null;
    }
    return <div className={"window"}>
        <h2 className={"error"}>Error</h2>
        <p className={"error"}>{error.reason}</p>
        <div className={"button_row"}>
            <button onClick={() => AppStore.update(s => {
                set_error(s, null);
            })}>OK
            </button>
        </div>
    </div>;
};