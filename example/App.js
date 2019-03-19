
import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';
import CallerId from "react-native-caller-id";

const instructions = Platform.select({
  ios: 'Enable the extension in Settings > Phone > Call Blocking & Identification\nAdd more callers in id.json file',
  android:
    'Use the number which you provided in the callers list to call.\nYou should see an overlay.\nAdd more callers in id.json file',
});


type Props = {};
export default class App extends Component<Props> {
  
  addCaller() {
    const callers = require("./id.json").callers;
    if(callers && callers.length > 0) {
      CallerId.setCallerList(callers);
    }
  }
  
  componentDidMount(): void {
    this.addCaller();
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>react-native-caller-id example</Text>
        <Text style={styles.instructions}>{instructions}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  title: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
