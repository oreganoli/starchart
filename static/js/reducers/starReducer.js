export const stars = (state, action) => {
    if (typeof state == "undefined") {
        return [];
    }
    switch (action.type) {
        case "SET_STARS":
            return action.data;
        case "CLEAR_STARS":
            return [];
        default:
            return state;
    }
};