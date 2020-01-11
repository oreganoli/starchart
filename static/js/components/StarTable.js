import React from "preact/compat";
import {displayDeclination, displayLy, displayRightAsc, displaySolar, displayTemp} from "../data/stars";
import {useSelector} from "react-redux";

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
    if (stars.length === 0) {
        return <p>No stars loaded.</p>;
    } else {
        return <table>
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
        </table>;
    }
};

export default StarTable;