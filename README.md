
# react-native-caller-id

## Getting started

`$ npm install react-native-caller-id --save`

### Mostly automatic installation

`$ react-native link react-native-caller-id`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-caller-id` and add `RNCallerId.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNCallerId.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNCallerIdPackage;` to the imports at the top of the file
  - Add `new RNCallerIdPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-caller-id'
  	project(':react-native-caller-id').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-caller-id/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-caller-id')
  	```


## Usage
```javascript
import CallerId from 'react-native-caller-id';

// Check extension enabled status on iOS
const enabled = await CallerId.getExtensionEnabledStatus();

// Set caller ids list
const callers = [
  {
    name: "A Caller Name",
    number: "8494141414"
  }
];
await CallerId.setCallerList(callers);
```
  