import React from "preact/compat";
import {h} from "preact";
import {
    displayDeclination,
    displayLy,
    displayRightAsc,
    displaySolar,
    displayTemp,
    search_stars,
    setStars
} from "../data/stars";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "preact/hooks";

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
    </tr>
};

export const StarTable = () => {
    let stars = useSelector(state => state.stars);
    let search = useSelector(state => state.search);
    let dispatch = useDispatch();
    useEffect(() => {
        search_stars(search).then(stars => dispatch(setStars(stars)));
    }, [search]);
    let title = search == null ? "All stars" : "Search results";
    if (stars.length === 0) {
        return <div><h2>{title}</h2><p>No stars loaded.</p></div>
    } else {
        return <div>
            <h2>{title}</h2>
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
                </thead>
                <tbody>{stars.map(each => <StarRow star={each}/>)}</tbody>
            </table>
        </div>;
    }
};

export default StarTable;