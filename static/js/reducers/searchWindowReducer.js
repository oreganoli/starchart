export const searchWindow = (state, action) => {
    if (typeof state == "undefined") {
        return false;
    }
    if (action.type === "SET_SEARCH_WINDOW") {
        return action.data;
    } else {
        return state;
    }
};