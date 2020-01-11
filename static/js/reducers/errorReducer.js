export const error = (state, action) => {
    if (typeof state == "undefined") {
        return null;
    }
    switch (action.type) {
        case "SET_ERROR":
            return action.data;
        case "CLEAR_ERROR":
            return null;
        default:
            return state;
    }
};