import {h, render} from "/js/web_modules/preact.js";
import htm from "/js/web_modules/htm.js";

const appName = "starchart";
const html = htm.bind(h);
const Hello = () => html`<h1>${appName}</h1>`;
render(html`${Hello()}`, document.body);