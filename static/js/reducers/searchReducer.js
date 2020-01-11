export const search = (state, action) => {
    if (typeof state == "undefined") {
        return null;
    }
    switch (action.type) {
        case "SET_SEARCH":
            return action.data;
        case "CLEAR_SEARCH":
            return null;
        default:
            return state;
    }
};