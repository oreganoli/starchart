export const get_all_stars = async () => {
    let request = new Request("/stars", {
        method: "GET"
    });
    let response = await fetch(request);
    return await response.json();
};