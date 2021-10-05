package com.example.androidui

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

/*
1. Implement listener Interface
2.Provide callback method
3.register listener to view
 */


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var loginButton : Button
    lateinit var registerButton : Button
    lateinit var parentLayout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton = findViewById(R.id.registerB)
        registerButton.setOnClickListener(this)
        registerForContextMenu(registerButton) //imp for context menu

        loginButton = findViewById(R.id.loginB)
        loginButton.setOnClickListener(this)
        registerForContextMenu(loginButton)

        parentLayout = findViewById(R.id.parentL)
        registerForContextMenu(parentLayout)

//        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        nManager.cancel(1)

    }

    val MENU_RIDER = 1
    val MENU_DRIVER = 2
    val MENU_LOGIN_DRIVER = 3
    val MENU_LOGIN_RIDER = 4

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when(v?.id){
            R.id.loginB ->{
                menu?.setHeaderTitle("Logging In")
                menu?.add(ContextMenu.NONE, MENU_LOGIN_DRIVER, 0, "Driver" )
                menu?.add(0, MENU_LOGIN_RIDER,0,"as Rider")
            }
            R.id.registerB ->{
                menu?.setHeaderTitle("Register")
                menu?.add(ContextMenu.NONE, MENU_DRIVER, 0, "Driver" )
                menu?.add(0, MENU_RIDER,0,"as Rider")
            }
            R.id.parentL ->{
                menu?.add(0, 10, 0, "EXIT")
            }
        }
        //add menu items


        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            MENU_LOGIN_DRIVER, MENU_LOGIN_RIDER ->{
                val i = Intent(this, AuthActivity::class.java)
                startActivity(i)
                return true
            }
            MENU_RIDER ->{
                Log.d("MainActivity","Rider Selected")
                val i = Intent(this, RegistrationActivity::class.java)
                startActivity(i)
                return true
            }
            MENU_DRIVER ->{
                Log.d("MainActivity","Driver Selected")
                Toast.makeText(this,"feature coming soon..",Toast.LENGTH_LONG).show()
                return true
            }
            else -> {
                Toast.makeText(this, "App Closed",Toast.LENGTH_LONG).show()
                finish()
            }
        }
            return super.onContextItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginB -> {
                Log.d("MainActivity", "Login Button Clicked")
                //launch Authactivity
                val i = Intent(this, AuthActivity::class.java)
                startActivity(i)
                displayNotification(this , "Login CLicked", "Clicked", AuthActivity::class.java)
            }
            R.id.registerB -> {
                Log.d("MainActivity", "Register Button Clicked")
                val r = Intent(this, RegistrationActivity::class.java )
                startActivity(r)
            }
        }


    }

    val MENU_LOGIN = 1
    val MENU_LOGOUT = 2
    val MENU_EXIT = 3
    val MENU_REGISTER = 4

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //add menu items

//        val loginItem = menu?.add(0, MENU_LOGIN, 1, "Login")
//       // loginItem?.setIcon(android.R.drawable.ic_menu_search)
//       // loginItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        val loginSubmenu = menu?.addSubMenu("Login")
        loginSubmenu?.add(0,MENU_LOGIN, 0, "Rider")
        loginSubmenu?.add(0,MENU_LOGIN, 0, "Driver")



        val logoutItem = menu?.add(0, MENU_LOGOUT, 2, "Logout")
       // logoutItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        menu?.add(0, MENU_EXIT, 3, "Exit")
        val registerItem = menu?.add(0, MENU_REGISTER,4,"Register")
        registerItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        //to hide menu return false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            MENU_LOGIN -> {
                Log.d("MainActivity","Login Selected")
                //launch Activity
                val i = Intent(this,AuthActivity::class.java)
                startActivity(i)
                return true
            }
            MENU_LOGOUT -> {
                Log.d("MainActivity","Logout Selected")
                val myToast = Toast.makeText(this,"Logging out,,", Toast.LENGTH_LONG)
                myToast.setGravity(Gravity.TOP,20,60)
                myToast.show()
                return true
            }
            MENU_EXIT -> {
                Log.d("MainActivity","Exit Selected")
                //dialog
                val dlg = MyDialog()
                dlg.isCancelable = false

                val dataBundle = Bundle()
                dataBundle.putString("dTitle","Confirmation")
                dataBundle.putString("dMessage", "Do you want to EXIT?")
                dataBundle.putInt("flag",1)

                dlg.arguments = dataBundle
                dlg.show(supportFragmentManager, "xyz")
                //finish()
                return true
            }
            MENU_REGISTER -> {
                Log.d("MainActivity","Register Selected")
                val r = Intent(this, RegistrationActivity::class.java)
                startActivity(r)
            }
        }

        return super.onOptionsItemSelected(item)
    }



}