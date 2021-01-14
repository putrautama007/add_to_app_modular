import 'package:flutter/material.dart';
import 'package:flutter_a/flutter_a.dart';
import 'package:flutter_module/presentation/view/screen/home_page.dart';
import 'package:libraries_module/modular/modular.dart';

import 'external/main_route.dart';

void main() => runApp(
      ModularApp(
        module: AppModule(),
      ),
    );

class AppModule extends MainModule {
  @override
  List<Bind> get binds => [];

  @override
  List<ModularRouter> get routers => [
        ModularRouter(
          MainRoutes.HOME,
          child: (context, args) => MyHomePage(),
        ),
        ModularRouter(
          MainRoutes.MODULE_A,
          module: FeatureA(),
        ),
      ];

  @override
  Widget get bootstrap => MyApp();
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: MainRoutes.HOME,
      navigatorKey: Modular.navigatorKey,
      onGenerateRoute: Modular.generateRoute,
    );
  }
}
