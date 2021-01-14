package com.example.addtoappsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startActivity(
                FlutterActivity.withCachedEngine(ADD_TO_APP_ID)
                    .build(this)
            )
        }

        buttonToOtherModule.setOnClickListener {
            startActivity(
                FlutterActivity.withCachedEngine(SECOND_ADD_TO_APP_ID)
                    .build(this)
            )
        }
    }
}