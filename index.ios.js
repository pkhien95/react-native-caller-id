import {NativeModules} from 'react-native';

const {RNCallerId: CallerId} = NativeModules;

export const getExtensionEnabledStatus = async () => {
  try {
    return await CallerId.getExtensionEnabledStatus();
  } catch (error) {
    throw error;
  }
};

export const setCallerList = async (callerList) => {
  try {
    return await CallerId.setCallerList(callerList);
  } catch (error) {
    throw error;
  }
};

export default CallerId;