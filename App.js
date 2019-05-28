import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  NativeModules,
  Button,
  DeviceEventEmitter,
  Alert,
  TextInput
} from 'react-native';

const { RNFloatWidget } = NativeModules;


export default class App extends Component<Props> {
  state = {
    title: '',
    body: '',
    time: null
  }
  componentDidMount() {
    DeviceEventEmitter.addListener('eventoTeste', this.onClose)
  }

  onClose = () => {
    Alert.alert(
      'Alert Title',
      'My Alert Msg',
      [
        {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
        {
          text: 'Cancel',
          onPress: () => console.log('Cancel Pressed'),
          style: 'cancel',
        },
        {text: 'OK', onPress: () => console.log('OK Pressed')},
      ],
      {cancelable: false},
    );
  }
  generateColor = () => {
    const color = '#' + Math.random().toString(16).substr(-6);
    /*  this.setState({
       color
     }) */
    return color;
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>dsadsa</Text>

        <Button title="Create" onPress={() => {
          RNFloatWidget.createButton()
        }} />

        <Button title="Start" onPress={() => {
          RNFloatWidget.start()
        }} />

        <Button title="close" onPress={() => {
          RNFloatWidget.closeWidget()
        }} />
        
        {/* <Button title="acao" onPress={() => {
          RNFloatWidget.start()
        }} />
        <Button title="abrir" onPress={() => {
          RNFloatWidget.openWidget()
        }} />
        <Button title="fechar" onPress={() => {
          RNFloatWidget.closeWidget()
        }} />
        <Button title="setColor" onPress={() => {
          RNFloatWidget.setColor(this.generateColor())
        }}
        />
        <Button title="criar Botao" onPress={() => {
          RNFloatWidget.createButton()
        }}
        />
        <View style={{ flexDirection: 'row' }}>
          <Button title="setImage1" onPress={() => {
            RNFloatWidget.setImage('http://i.imgur.com/DvpvklR.png')
          }}
          />
          <Button title="setImage2" onPress={() => {
            RNFloatWidget.setImage('https://i.imgur.com/HPlo1rm.jpg')
          }}
          />
        </View> */}

        {/* <TextInput onChangeText={(title) => {
          this.setState({ title }, () => {
            RNFloatWidget.setTitle(this.state.title)
          })
        }} />
        <TextInput onChangeText={(body) => {
          this.setState({ body }, () => {
            RNFloatWidget.setBody(this.state.body)
          })
        }} /> */}
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
  welcome: {
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
