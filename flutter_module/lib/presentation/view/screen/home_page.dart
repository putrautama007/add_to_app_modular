import 'package:core_module/platform_channel/platform_channel.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_module/external/main_route.dart';

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String _msg = "";

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  Future<void> getData() async {
    String message;
    try {
      var value =
          await PlatformChannel.platform.invokeMethod('getBatteryLevel');
      message = value.toString();
    } on PlatformException catch (e) {
      message = "Failed to get data from native : '${e.message}'.";
    }

    setState(() {
      _msg = message;
    });
  }

  Future<void> navigateToNewActivity() async {
    try {
      await PlatformChannel.platform.invokeMethod('navigateToNewActivity');
    } on PlatformException catch (e) {}
  }

  @override
  void initState() {
    super.initState();
    getData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Home Page"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Battery level from native $_msg',
            ),
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            FlatButton(
              onPressed: () {
                Modular.to.pushNamed(MainRoutes.MODULE_A);
              },
              child: Text("To Second Home Page"),
            ),
            FlatButton(
              onPressed: () => navigateToNewActivity(),
              child: Text("To Native Activity"),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
