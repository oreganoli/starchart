export const set_error = (s, err) => {
    // remove other modals
    s.del = null;
    s.edit = null;
    s.searchWindow = false;
    s.error = err;
};