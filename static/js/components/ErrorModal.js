import React from "preact/compat";
import {h} from "preact";
import {AppStore} from "../store";
import {useStoreState} from "pullstate";
import {set_error} from "../data/error";

const accept = () => {
    console.log("Accepting error.");
    AppStore.update(s => {
        set_error(s, null);
    });
};

export const ErrorModal = () => {
    let error = useStoreState(AppStore, s => s.error);
    console.log(error);
    if (error != null) {
        return <div className={"window"}>
            <h2 className={"error"}>Error</h2>
            <p className={"error"}>{error.reason}</p>
            <div className={"button_row"}>
                <button onClick={accept}>OK
                </button>
            </div>
        </div>;
    } else {
        return null;
    }
};