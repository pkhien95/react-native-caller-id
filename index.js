'use strict';

import {NativeModules} from 'react-native';

const {RNCallerId: CallerId} = NativeModules;

export const setCallerList = async (callerList) => {
  try {
    return await CallerId.setCallerList(callerList);
  } catch (error) {
    throw error;
  }
};

//Android only
export const requestOverlayPermission = async (callerList) => {
  try {
    return await CallerId.requestOverlayPermission(callerList);
  } catch (error) {
    throw error;
  }
};

//iOS only
export const getExtensionEnabledStatus = async () => {
  try {
    return await CallerId.getExtensionEnabledStatus();
  } catch (error) {
      throw error;
  }
};
