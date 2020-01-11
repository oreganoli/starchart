import {Store} from "pullstate";

export const AppStore = new Store({
    del: null,
    edit: null,
    error: null,
    search: null,
    searchWindow: false,
    stars: []
});