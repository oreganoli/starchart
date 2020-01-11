import React from "preact/compat";
import {h} from "preact";
import {
    displayDeclination,
    displayLy,
    displayRightAsc,
    displaySolar,
    displayTemp,
    search_stars
} from "../data/stars";
import {useStoreState} from "pullstate";
import {useEffect} from "preact/hooks";
import {AppStore} from "../store";

const edit_star = (id) => AppStore.update(s => {
    s.edit = id;
});
const del_star = (id) => AppStore.update(s => {
    s.del = id;
});

const StarRow = (props) => {
    let {
        id, name, constellation,
        absolute_magnitude, declination,
        right_ascension, hemisphere,
        apparent_magnitude, catalog_name,
        temperature, distance, mass
    } = props.star;
    return <tr>
        <td>{name}</td>
        <td>{constellation}</td>
        <td>{catalog_name}</td>
        <td>{apparent_magnitude}</td>
        <td>{absolute_magnitude}</td>
        <td>{displayDeclination(declination)}</td>
        <td>{displayRightAsc(right_ascension)}</td>
        <td>{hemisphere}</td>
        <td>{displayTemp(temperature)}</td>
        <td>{displayLy(distance)}</td>
        <td>{displaySolar(mass)}</td>
        <td>
            <button onClick={() => edit_star(id)}>📝 Edit</button>
            <button onClick={() => del_star(id)}>🗑️ Delete</button>
        </td>
    </tr>
};

const Buttons = () => (<div>
    <button>All stars</button>
    <button>🔍 Search</button>
    <button>⭐ Add star</button>
</div>);

export const StarTable = () => {
    let stars = useStoreState(AppStore, s => s.stars);
    let search = useStoreState(AppStore, s => s.search);
    useEffect(() => {
        search_stars(search).then(stars => AppStore.update(s => {
            s.stars = stars;
        }));
    }, [search]);
    let title = search == null ? "All stars" : "Search results";
    if (stars.length === 0) {
        return <div className={"container"}>
            <h2>{title}</h2>
            <Buttons/>
            <p>No stars loaded.</p>
        </div>;
    } else {
        return <div className={"container"}>
            <h2>{title}</h2>
            <Buttons/>
            <table>
                <thead>
                <th>Name</th>
                <th>Constellation</th>
                <th>Catalog name</th>
                <th>Apparent magnitude</th>
                <th>Absolute magnitude</th>
                <th>Declination</th>
                <th>Right ascension</th>
                <th>Hemisphere</th>
                <th>Temperature</th>
                <th>Distance</th>
                <th>Mass</th>
                <th>Actions</th>
                </thead>
                <tbody>{stars.map(each => <StarRow star={each}/>)}</tbody>
            </table>
        </div>;
    }
};

export default StarTable;