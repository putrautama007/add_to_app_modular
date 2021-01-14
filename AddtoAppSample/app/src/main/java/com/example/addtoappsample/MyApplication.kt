package com.example.addtoappsample

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

const val ADD_TO_APP_ID = "add_to_app_engine"
const val SECOND_ADD_TO_APP_ID = "second_add_to_app_engine"
const val FIRST_INITIAL_ROUTE = "/home"
const val SECOND_INITIAL_ROUTE = "/moduleA"

class MyApplication : Application() {
    lateinit var flutterEngine: FlutterEngine
    lateinit var secondFlutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()

        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)
        // Configure an initial route.
        flutterEngine.navigationChannel.setInitialRoute(FIRST_INITIAL_ROUTE)
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(ADD_TO_APP_ID, flutterEngine)


        // Instantiate a FlutterEngine.
        secondFlutterEngine = FlutterEngine(this)
        // Configure an initial route.
        secondFlutterEngine.navigationChannel.setInitialRoute(SECOND_INITIAL_ROUTE)
        // Start executing Dart code to pre-warm the FlutterEngine.
        secondFlutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(SECOND_ADD_TO_APP_ID, secondFlutterEngine)
    }
}