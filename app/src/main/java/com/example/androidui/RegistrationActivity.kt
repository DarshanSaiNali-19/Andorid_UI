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
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var uidEditText: EditText
    lateinit var pwdEditText: EditText
    lateinit var confirmpwdEditText: EditText
    lateinit var fullnameEditText: EditText
    lateinit var mobileEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var addressEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        uidEditText = findViewById(R.id.userIDE)
        pwdEditText = findViewById(R.id.pwdE)
        confirmpwdEditText = findViewById(R.id.confirmpwdE)
        fullnameEditText = findViewById(R.id.fullnameE)
        mobileEditText = findViewById(R.id.mobileE)
        emailEditText = findViewById(R.id.emailE)
        addressEditText = findViewById(R.id.addressE)

    }

    fun submitClick(view: View) {
        Log.d("AuthActivity", "submit Clicked")

        val userid = userIDE.text.toString()
        val password = pwdE.text.toString()
        val confirmPassword = confirmpwdE.text.toString()
        val fullName = fullnameE.text.toString()
        val email = emailE.text.toString()
        val mobile = mobileE.text.toString()
        val address = addressE.text.toString()


        when{
            userid.isEmpty() -> userIDE.setError("Please Enter Userid")
            password.isEmpty() -> pwdE.setError("Please enter Password")
            confirmPassword.isEmpty() -> confirmpwdE.setError("Enter passwprd")
            password.length < 8 -> passwordE.setError("Password should be atleast 8 char long")
            confirmPassword != password -> confirmpwdE.setError("Not match of password")
            fullName.isEmpty() -> fullnameE.setError("Enter Full Name")
            email.isEmpty() -> emailE.setError("Enter Email")
            mobile.isEmpty() -> mobileE.setError("Enter Mobile number")
            address.isEmpty() -> addressE.setError("Enter Address")
            else ->{
                Toast.makeText(this,"Userid: $userid \n Password: $password \n Registeration Successful",
                    Toast.LENGTH_LONG).show()
                displayNotification()

            }

        }


    }



    fun cancelClick(view: View){
        Log.d("RegistrationActivity","Cancel clicked")

        useridE.setText("")
        passwordE.setText("")
        confirmpwdE.setText("")
        fullnameE.setText("")
        emailE.setText("")
        mobileE.setText("")
        addressE.setText("")

        finish()

    }

    fun displayNotification() {
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
        builder.setContentTitle("Registration Activity")
        builder.setContentText("Successfully registered \n Please Login")

        /*
        Action - launch Activity (general)
                -launch services
                - broadcast sent
         */
        val i = Intent(this, AuthActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, i,0)
        builder.setContentIntent(pi)
        val myNotification = builder.build()

        myNotification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_NO_CLEAR

        //3.display
        nManager.notify(1, myNotification)

    }
}