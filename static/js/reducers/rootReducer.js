import {stars} from "./starReducer";
import {search} from "./searchReducer";
import {combineReducers} from "redux";

export default combineReducers({stars, search});