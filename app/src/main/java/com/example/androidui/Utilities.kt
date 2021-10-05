package com.example.androidui


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

fun showMessage(ctx: Context, msg:String) {
    Toast.makeText(ctx,msg,Toast.LENGTH_LONG).show()
}
fun displayNotification(ctx: Context,title:String,descr:String,cls:Class<*>) {

    //1.get NotificationManager reference
    val nManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    //2. create notification object
    val builder: Notification.Builder

    //Above Oreo devices-Notification Channel
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
        val channel = NotificationChannel("AndroidUI", "AndroidUI", NotificationManager.IMPORTANCE_DEFAULT)
        channel.setSound(Settings.System.DEFAULT_RINGTONE_URI,null)
        nManager.createNotificationChannel(channel)
        builder = Notification.Builder(ctx,"AndroidUI")
    }
    else {
        builder = Notification.Builder(ctx)
    }

    builder.setSmallIcon(android.R.drawable.btn_star_big_on)
    builder.setContentTitle(title)
    builder.setContentText(descr)

    /*
    Action-launch activity(general)
        -launch service
        -broadcast sent
     */

    val i = Intent(ctx,cls)
    val pi = PendingIntent.getActivity(ctx, 0,i,0)
    builder.setContentIntent(pi)
    val myNotification = builder.build()
    myNotification.flags= Notification.FLAG_AUTO_CANCEL or Notification.FLAG_NO_CLEAR

    //3.display
    nManager.notify(1,myNotification)

}