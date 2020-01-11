const path = require('path');
module.exports = {
    "entry": "./index.js",
    "output": {
        "path": path.resolve(__dirname, "dist"),
        "filename": "bundle.js"
    },
    "resolve": {
        "alias": {
            "react": "preact/compat",
            "react-dom/test-utils": "preact/test-utils",
            "react-dom": "preact/compat"
        }
    },
    "devtool": "sourcemap"
};