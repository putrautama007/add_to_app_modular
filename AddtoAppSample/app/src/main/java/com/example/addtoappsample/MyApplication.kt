package com.example.addtoappsample

import android.app.Application
import android.content.Intent
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import android.content.Context
import android.content.ContextWrapper
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

const val ADD_TO_APP_ID = "add_to_app_engine"
const val SECOND_ADD_TO_APP_ID = "second_add_to_app_engine"
const val FIRST_INITIAL_ROUTE = "/home"
const val SECOND_INITIAL_ROUTE = "/moduleA"
private const val CHANNEL = "example.flutter/AddToApp"

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

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                run {
                    when (call.method) {
                        "getBatteryLevel" -> {
                            val batteryLevel = getBatteryLevel()
                            if (batteryLevel != -1) {
                                result.success(batteryLevel)
                            } else {
                                result.error("UNAVAILABLE", "Battery level not available.", null)
                            }
                        }
                        "navigateToNewActivity" -> {
                            val intent = Intent(this, SecondActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        else -> result.notImplemented()
                    }
                }
            }


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

    private fun getBatteryLevel(): Int {
        val batteryLevel: Int
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }

        return batteryLevel
    }
}