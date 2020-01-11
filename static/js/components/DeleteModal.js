import React from "preact/compat";
import {h} from "react";
import {useSelector} from "react-redux";
import {useDispatch} from "react-redux";
import {delete_star} from "../data/stars";

const clear_del = () => ({type: "CLEAR_DEL"});

const accept = (dispatch, id) => {
    delete_star(id)
        .then(dispatch(clear_del()))
};

const reject = (dispatch) => {
    dispatch(clear_del());
};

export const DeleteModal = () => {
    let del = useSelector(state => state.del);
    let dispatch = useDispatch();
    if (del != null) {
        return <div className={"window"}>
            <h2>Delete star?</h2>
            <p>Are you sure you want to delete the star with the id {del}?</p>
            <div className={"button_row"}>
                <button onClick={() => accept(dispatch, del)}>Yes</button>
                <button onClick={() => reject(dispatch)}>No</button>
            </div>
        </div>;
    } else {
        return null;
    }
};