import React from "preact/compat";
import {h} from "preact";
import {useDispatch} from "react-redux";

export const get_all_stars = async () => {
    let request = new Request("/stars", {
        method: "GET"
    });
    let response = await fetch(request);
    return await response.json();
};

export const search_stars = async (criteria) => {
    if (criteria == null) {
        return await get_all_stars();
    } else {
        let request = new Request("/search", {
            method: "POST",
            body: JSON.stringify(criteria)
        });
        let response = await fetch(request);
        return await response.json();
    }
};

export const delete_star = async (id) => {
    let request = new Request(`/stars/${id}`, {
        method: "DELETE"
    });
    let response = await fetch(request);
    return await response.json();
};

export const setStars = (stars) => ({type: "SET_STARS", data: stars});

export const displayDeclination = ({degrees, minutes, seconds}) => (`${degrees}°${minutes}'${seconds}"`);
export const displayRightAsc = ({hours, minutes, seconds}) => (`${hours}h ${minutes}m ${seconds}s`);
export const displayTemp = (temp) => (`${temp}°C`);
export const displayLy = (dist) => (`${dist} ly`);
export const displaySolar = (mass) => (<span>{mass}M<sub>☉</sub></span>);

export const edit = (id) => {
    console.log(`Editing is not implemented yet. Was called with id = ${id}.`);
};
export const del = (dispatch, id) => {
    dispatch({type: "SET_DEL", data: id});
};