import {NativeModules} from 'react-native';

const {RNCallerId: CallerId} = NativeModules;

export const setCallerList = async (callerList) => {
  try {
    return await CallerId.setCallerList(callerList);
  } catch (error) {
    throw error;
  }
};

export default CallerId;