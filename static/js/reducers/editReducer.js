export const edit = (state, action) => {
    if (typeof state == "undefined") {
        return null;
    }
    switch (action.type) {
        case "SET_EDIT":
            return action.data;
        case "CLEAR_EDIT":
            return null;
        default:
            return state;
    }
};