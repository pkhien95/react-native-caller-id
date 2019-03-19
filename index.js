'use strict';

import {Platform} from "react-native";

let CallerId;

if (Platform.OS === "ios") {
    CallerId = require("./index.ios").default;
} else {
    CallerId = require("./index.android").default;
}

export default CallerId;
