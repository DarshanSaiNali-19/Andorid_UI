package com.example.androidui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    lateinit var uidEditText: EditText
    lateinit var pwdEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        uidEditText = findViewById(R.id.useridE)
        pwdEditText = findViewById(R.id.passwordE)

        cancelB.setOnLongClickListener{
            showPopup(it)
            true
        }

    }

    fun submitClick(view: View) {
        Log.d("AuthActivity", "submit Clicked")

        val userid = useridE.text.toString()
        val password = passwordE.text.toString()

        when {
            userid.isEmpty() -> {
                useridE.setError("Pls enter userid")
            }
            password.isEmpty() -> {
                passwordE.setError("Pls enter password")
            }
            password.length < 8 -> {
                passwordE.setError("Password should be atleast 8 char long")
            }
            else -> {
                val dlg = MyDialog()
                val dBundle = Bundle()
                dBundle.putString("dTitle","Logging in")
                dBundle.putString("dMessage", "Do you want to Submit below data \nUserid : $userid \n Password: $password")
                dBundle.putInt("flag",2)

                dlg.arguments = dBundle
                dlg.show(supportFragmentManager, null)

//                Toast.makeText(
//                    this, "Userid: $userid \n Password: $password",
//                    Toast.LENGTH_LONG
//                ).show()
            }
        }

//        if(userid.isEmpty() || password.isEmpty()){
////            Toast.makeText(this,"pls enter all fields...",
////            Toast.LENGTH_LONG).show()
//            if(userid.isEmpty()){
//                useridE.setError("Pls Enter userid")
//            }
//            if(password.isEmpty()){
//                passwordE.setError("Pls Enter password")
//            }
//        }
//        else{
//            Toast.makeText(this,"Userid: $userid \n Password: $password",
//            Toast.LENGTH_LONG).show()
//
//            if(password.length < 8){
//                passwordE.setError("Password should be atleast 8 characters long")
//            }
//        }


    }

    val MENU_RESET = 1
    val MENU_GO_BACK = 2
    fun cancelClick(view: View) {
        Log.d("AuthActivity", "Cancel clicked")

//        useridE.setText("")
//        passwordE.setText("")
//        finish()

       showPopup(view)
    }

    fun showPopup(view: View){
        //show a pop up menu
        val pMenu = PopupMenu(this, view)
        pMenu.menu.add(0, MENU_RESET, 0, "RESET DATA")
        pMenu.menu.add(0, MENU_GO_BACK, 0, "LEAVE THE PAGE")
        pMenu.setOnMenuItemClickListener {
            when(it.itemId){
                MENU_RESET -> {
                    useridE.setText("")
                    passwordE.setText("")
                }
                MENU_GO_BACK -> {
                    finish()
                }
            }
            true
        }
        pMenu.show()
    }

    override fun onStop() {
        super.onStop()
        //displayNotification()
    }

    override fun onBackPressed() {
        //display status bar notification
        displayNotification()
        super.onBackPressed()
    }

    private fun displayNotification() {
        //1. get refernce to notification manager
        val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //2. create notification object
        val builder : Notification.Builder

        //above oreo - notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val channel = NotificationChannel("AndroidUI", "AndroidUI",
               NotificationManager.IMPORTANCE_DEFAULT)

            channel.setSound(Settings.System.DEFAULT_RINGTONE_URI, null)

            nManager.createNotificationChannel(channel)

            builder = Notification.Builder(this,"AndroidUI")
        }
        else{
            builder = Notification.Builder(this)
        }


        builder.setSmallIcon(android.R.drawable.btn_star_big_on)
        builder.setContentTitle("Authentication")
        builder.setContentText("Pls complete Login")

        /*
        Action - launch Activity (general)
                -launch services
                - broadcast sent
         */
        val i = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, i,0)
        builder.setContentIntent(pi)
        val myNotification = builder.build()

        myNotification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_NO_CLEAR

        //3.display
        nManager.notify(1, myNotification)

    }
}