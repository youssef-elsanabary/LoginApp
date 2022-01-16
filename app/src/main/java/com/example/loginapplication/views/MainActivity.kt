package com.example.loginapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.loginapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var appToolbar: Toolbar
  //  private val profileFragment = ProfileFragment()
    //private val updateFragment = UpdateFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appToolbar = findViewById(R.id.appToolbar)
        setSupportActionBar(appToolbar)

    }

  /*  override fun passData(
        full2name: String,
        old2email: String,
        old2password: String,
        old2address: String,
        old2phone: String,
        old2age: String
    ) {
       val  bundle = Bundle()
        bundle.putString("messegeInput",full2name)
        profileFragment.arguments = bundle

        val transaction = SupportF
    }*/
}