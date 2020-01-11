import {del} from "./delReducer";
import {edit} from "./editReducer";
import {error} from "./errorReducer";
import {stars} from "./starReducer";
import {search} from "./searchReducer";
import {searchWindow} from "./searchWindowReducer";
import {combineReducers} from "redux";

export default combineReducers({del, edit, error, stars, search, searchWindow});