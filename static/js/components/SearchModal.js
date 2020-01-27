import React from "preact/compat";
import {h} from "preact";
import {useState} from "preact/hooks";
import {AppStore} from "../store";
import {useStoreState} from "pullstate";
import {refresh, search_stars} from "../data/stars";
import {defaultSearch, range} from "../data/search";

const reject = () => {
    AppStore.update(s => {
        s.searchWindow = false;
    });;
}
const accept = (criteria, checks) => {
    var crit = criteria;
    if (!checks.constellation) {
        crit.constellation = null;
    }
    console.log(crit);
    AppStore.update(s => {
        s.searchWindow = false;
        s.search = crit;
    })
    search_stars(crit).then(stars => AppStore.update(s => {
        s.stars = stars;
    }));
};
export const SearchModal = () => {
    let search = useStoreState(AppStore, s => s.search)
    let searchWindow = useStoreState(AppStore, s => s.searchWindow)
    let [tempCrit, setTempCrit] = useState(search || defaultSearch());
    let [checks, setChecks] = useState({
        constellation: tempCrit.constellation != null
    })
    if (!searchWindow) {
        return null;
    }
    return <div className="window">
        <h2>Search</h2>
        <input type="checkbox" checked={checks.constellation} onChange={(e) => setChecks({...checks, constellation: e.target.checked})}></input>
        <label>Constellation</label>
        <div className="input_row">
            <input type={"text"} disabled={!checks.constellation} value={tempCrit.constellation} onChange={(e) => setTempCrit({...tempCrit, constellation: e.target.value})}/>
        </div>
        <input type="checkbox"/>
        <label>Distance <b>[pc]</b></label>
        <div className="input_row">
            <input style={"width: 6ch;"} type={"number"} min={0} step={0.01}/><b>[pc]</b>
             - 
            <input style={"width: 6ch;"} type={"number"} min={0} step={0.01}/><b>[pc]</b>
        </div>
        <input type="checkbox"/>
        <label>Temperature</label>
        <div className="input_row">
        <input style={"width: 6ch;"} type={"number"} min={4000} step={0.01}/><b>[°C]</b>
        -
        <input style={"width: 6ch;"} type={"number"} min={4000} step={0.01}/><b>[°C]</b>
        </div>
        <input type="checkbox"/>
        <label>Apparent magnitude</label>
        <div className="input_row">
        <input style={"width: 6ch;"} type={"number"} min={-26.74} max={15.00} step={0.01}/>
        -
        <input style={"width: 6ch;"} type={"number"} min={-26.74} max={15.00} step={0.01}/>
        </div>
        <label>Hemisphere</label>
        <div className="input_row">
            <select>
                <option value={null} selected>Any</option>
                <option value="Northern">Northern</option>
                <option value="Southern">Southern</option>
                <option value="Equatorial">Equatorial</option>
            </select>
        </div>
        <label>Potential supernovae</label>
        <div className="input_row">
            <select>
                <option value={null} selected>Either</option>
                <option value={true}>Yes</option>
                <option value={false}>No</option>
            </select>
        </div>
        <div className="button_row">
            <button onClick={() => accept(tempCrit, checks)}>🔍 Search</button>
            <button onClick={() => reject()}>Cancel</button>
        </div>
    </div>;
};