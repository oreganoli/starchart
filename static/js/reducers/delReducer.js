export const del = (state, action) => {
    if (typeof state == "undefined") {
        return null;
    }
    switch (action.type) {
        case "SET_DEL":
            return action.data;
        case "CLEAR_DEL":
            return null;
        default:
            return state;
    }
};