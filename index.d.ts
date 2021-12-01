declare module "react-native-caller-id" {
  export interface CallerList {
    name: string
    position: string
    numbers: Array<string>
  }

  export function setCallerList(
    callerList: Array<CallerList>
  ): Promise<void>;

  //Android Only
  export function requestOverlayPermission(): void;

  //iOS Only
  export function getExtensionEnabledStatus(): Promise<boolean>
}
