import 'package:flutter_a/presentation/second_home_page.dart';
import 'package:libraries_module/modular/modular.dart';

class FeatureA extends ChildModule {
  @override
  List<Bind> get binds => [];

  @override
  List<ModularRouter> get routers => [
        ModularRouter(
          "/",
          child: (_, __) => SecondHomePage(),
        ),
      ];
}
