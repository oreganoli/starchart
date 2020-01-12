import React from "preact/compat";
import {h} from "preact";
import {AppStore} from "../store";
import {set_error} from "./error";
import {useStoreState} from "pullstate";

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
    await fetch(request);
};

export const upsert = async (star) => {
    let request = new Request("/stars", {
        method: "POST",
        body: JSON.stringify(star)
    });
    let response = await fetch(request);
    if (response.status !== 200) {
        let err = await response.json();
        AppStore.update(s => set_error(s, err));
    }
};

export const refresh = async (criteria) => {
    console.log("Refreshing...");
    console.log("Search criteria are:");
    console.log(criteria);
    let results = await search_stars(criteria);
    console.log(`reload results are:`);
    console.log(results);
    AppStore.update(s => {
        s.stars = results;
    });
};

export const displayDeclination = ({degrees, minutes, seconds}) => (`${degrees}°${minutes}'${seconds}"`);
export const displayRightAsc = ({hours, minutes, seconds}) => (`${hours}h ${minutes}m ${seconds}s`);
export const displayTemp = (temp) => (`${temp}°C`);
export const displayLy = (dist) => (`${dist} ly`);
export const displaySolar = (mass) => (<span>{mass}M<sub>☉</sub></span>);