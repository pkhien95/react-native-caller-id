
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

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNCallerId.sln` in `node_modules/react-native-caller-id/windows/RNCallerId.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Caller.Id.RNCallerId;` to the usings at the top of the file
  - Add `new RNCallerIdPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNCallerId from 'react-native-caller-id';

// TODO: What to do with the module?
RNCallerId;
```
  