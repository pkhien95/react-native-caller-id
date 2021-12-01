import {NativeModules} from 'react-native';

const {RNCallerId: CallerId} = NativeModules;

export const setCallerList = async (callerList) => {
  try {
    return await CallerId.setCallerList(callerList);
  } catch (error) {
    throw error;
  }
};

export const requestOverlayPermission = async (callerList) => {
  try {
    return await CallerId.requestOverlayPermission(callerList);
  } catch (error) {
    throw error;
  }
};