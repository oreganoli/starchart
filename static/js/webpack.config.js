const path = require('path');
module.exports = {
    "entry": ["babel-polyfill", "./index.js"],
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
    "mode": "development",
    "devtool": "sourcemap",
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader'
                }
            }
        ]
    }
};