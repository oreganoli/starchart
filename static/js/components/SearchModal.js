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
    if (!checks.constellation) {
        criteria.constellation = null;
    }
    AppStore.update(s => {
        s.searchWindow = false;
        s.search = criteria;
    })
};
export const SearchModal = () => {
    let search = useStoreState(AppStore, s => s.searchWindow)
    let [tempCrit, setTempCrit] = useState(search || defaultSearch());
    let [checks, setChecks] = useState({
        constellation: tempCrit.constellation != null,
    })
    if (!search) {
        return null;
    }
    return <div className="window">
        <h2>Search</h2>
        <input type="checkbox" checked={checks.constellation} onChange={(e) => setChecks({...checks, constellation: e.target.checked})}></input>
        <label>Constellation</label>
        <div className="input_row">
            <input type={"text"} disabled={!checks.constellation} value={tempCrit.constellation} onChange={(e) => setTempCrit({...tempCrit, constellation: e.target.value})}/>
        </div>
        <div></div>
        <div className="button_row">
            <button onClick={() => accept(tempCrit, checks)}>🔍 Search</button>
            <button onClick={() => reject()}>Cancel</button>
        </div>
    </div>;
};