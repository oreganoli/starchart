import {render} from "preact";
import {html} from "htm/preact"

const appName = "starchart";
const root = document.getElementById("root");
const Hello = () => html`<h1>${appName}</h1>`;
render(html`${Hello()}`, root);