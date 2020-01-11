export const defaultSearch = () => ({
    constellation: null,
    distance_parsecs: null,
    temperature: null,
    apparent_magnitude: null,
    hemisphere: null,
    potential_supernovae: null,
});
export const range = (min, max) => ({min, max});

export const setSearch = (criteria) => ({type: "SET_SEARCH", data: criteria});
export const clear = () => ({type: "CLEAR_SEARCH"});