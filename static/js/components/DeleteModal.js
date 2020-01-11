import React from "preact/compat";
import {h} from "preact";
import {useStoreState} from "pullstate";
import {delete_star, search_stars} from "../data/stars";
import {AppStore} from "../store";

const accept = (id) => {
    let search = useStoreState(AppStore, s => s.search);
    delete_star(id)
        .then(() => {
            console.log(`deleted star with id ${id}`);
            AppStore.update(s => {
                s.del = null;
            });
        }).then(() => search_stars(search).then(result => AppStore.update(s => {
        s.stars = result;
    })));
};

const reject = () => {
    AppStore.update(s => {
        s.del = null;
    })
};

export const DeleteModal = () => {
    let del = useStoreState(AppStore, s => s.del);
    if (del == null) {
        return null;
    }
    return <div className={"window"}>
        <h2>Delete star?</h2>
        <p>Are you sure you want to delete the star with the id {del}?</p>
        <div className={"button_row"}>
            <button onClick={() => accept(del)}>Yes</button>
            <button onClick={() => reject()}>No</button>
        </div>
    </div>;
};