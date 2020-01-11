import React from "preact/compat";
import {h} from "preact";
import {useState} from "preact/hooks";
import {AppStore} from "../store";
import {useStoreState} from "pullstate";

export const EditModal = () => {
    let edit = useStoreState(AppStore, s => s.edit);
    if (edit == null) {
        return null;
    }
    let title = edit.id == null ? "Create star" : "Edit star";
    let [workingCopy, setWorkingCopy] = useState({
        id: edit.id || null, // as visible above, a null ID means a new star is being added
        name: edit.name || "",
        constellation: edit.constellation || "",
        absolute_magnitude: null, // not user-editable, derived by the server
        catalog_name: null, // also derived server-side
        hemisphere: null, // ditto
        declination: edit.declination || {
            degrees: 0,
            minutes: 0,
            seconds: 0,
        },
        right_ascension: edit.right_ascension || {
            hours: 0,
            minutes: 0,
            seconds: 0
        },
        apparent_magnitude: edit.apparent_magnitude || 0,
        temperature: edit.temperature || 4000,
        distance: edit.distance || 1,
        mass: edit.mass || 1
    });
    return <div className={"window"}>
        <h2>{title}</h2>
        <label>Name</label>
        <div className={"input_row"}>
            <input type={"text"} value={workingCopy.name}
                   onChange={(e) => setWorkingCopy({...workingCopy, name: e.target.value})}/>
        </div>
        <label>Constellation</label>
        <div className={"input_row"}>
            <input type={"text"} value={workingCopy.constellation}/>
        </div>
        <label>Declination</label>
        <div className={"input_row"}>
            <input type={"number"} min={-90} max={90} step={1} value={workingCopy.declination.degrees}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       declination: {...workingCopy.declination, degrees: +e.target.value}
                   })}/>
            <b>°</b>
            <input type={"number"} min={-59} max={59} step={1} value={workingCopy.declination.minutes}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       declination: {...workingCopy.declination, minutes: +e.target.value}
                   })}/>
            <b>'</b>
            <input type={"number"} min={-59} max={59} step={1} value={workingCopy.declination.seconds}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       declination: {...workingCopy.declination, seconds: +e.target.value}
                   })}/>
            <b>"</b>
        </div>
        <label>Right ascension</label>
        <div className={"input_row"}>
            <input type={"number"} min={0} max={23} step={1} value={workingCopy.right_ascension.hours}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       right_ascension: {...workingCopy.right_ascension, hours: +e.target.value}
                   })}/>
            <b>°</b>
            <input type={"number"} min={0} max={59} step={1} value={workingCopy.right_ascension.minutes}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       right_ascension: {...workingCopy.right_ascension, minutes: +e.target.value}
                   })}/>
            <b>'</b>
            <input type={"number"} min={0} max={59} step={1} value={workingCopy.right_ascension.seconds}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       right_ascension: {...workingCopy.right_ascension, seconds: +e.target.value}
                   })}/>
            <b>"</b>
        </div>
        <label>Apparent magnitude</label>
        <div className={"input_row"}>
            <input type={"number"} min={-26.74} max={15.00} step={"any"} value={workingCopy.apparent_magnitude}
                   onChange={(e) => setWorkingCopy({
                       ...workingCopy,
                       apparent_magnitude: Number.parseFloat(e.target.value)
                   })}/>
        </div>
        <label>Distance</label>
        <div className={"input_row"}>
            <input type={"number"} min={1} step={"any"} value={workingCopy.distance}
                   onChange={(e) => setWorkingCopy({...workingCopy, distance: Number.parseFloat(e.target.value)})}/>
            <b>[ly]</b>
        </div>
        <label>Temperature</label>
        <div className={"input_row"}>
            <input type={"number"} min={4000} step={"any"} value={workingCopy.temperature}
                   onChange={(e) => setWorkingCopy({...workingCopy, temperature: Number.parseFloat(e.target.value)})}/>
            <b>[°C]</b>
        </div>
        <label>Mass</label>
        <div className={"input_row"}>
            <input type={"number"} min={0.1} max={50} step={"any"} value={workingCopy.mass}
                   onChange={(e) => setWorkingCopy({...workingCopy, mass: Number.parseFloat(e.target.value)})}/>
            <b>M<sub>☉</sub></b>
        </div>
        <div className={"button_row"}>
            <button onClick={() => console.log(workingCopy)}>Save</button>
            <button onClick={() => AppStore.update(s => {
                s.edit = null;
            })}>Cancel
            </button>
        </div>
    </div>
};