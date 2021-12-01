'use strict';

import {NativeModules, Platform} from 'react-native';

const {RNCallerId: CallerId} = NativeModules;

export const setCallerList = async (callerList) => {
  try {
    return await CallerId.setCallerList(callerList);
  } catch (error) {
    throw error;
  }
};

//Android only
export const requestOverlayPermission = async () => {
  try {
    if (Platform.OS === 'android') {
        return await CallerId.requestOverlayPermission();
    }
    return
  } catch (error) {
    throw error;
  }
};

//iOS only
export const getExtensionEnabledStatus = async () => {
  try {
    if (Platform.OS === 'ios') {
        return await CallerId.getExtensionEnabledStatus();
    }
    return
  } catch (error) {
    throw error;
  }
};
