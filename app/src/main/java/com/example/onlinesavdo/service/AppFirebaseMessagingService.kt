package com.example.onlinesavdo.service

import android.annotation.SuppressLint
import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.multidex.BuildConfig.APPLICATION_ID
import com.airbnb.lottie.BuildConfig.APPLICATION_ID
import com.example.onlinesavdo.BuildConfig.APPLICATION_ID
import com.example.onlinesavdo.R
import com.example.onlinesavdo.screen.MainActivity
import com.example.onlinesavdo.utils.PrefUtils
import com.facebook.crypto.BuildConfig.APPLICATION_ID
import com.google.firebase.messaging.BuildConfig
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.app.NotificationCompat.Builder as Builder1

class AppFirebaseMessagingService:FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("tag-debug : ", token)
        PrefUtils.setFCMToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            Log.d("tag-debug : body ",remoteMessage?.notification?.body.toString())
            Log.d("tag-debug : title ",remoteMessage?.notification?.title.toString())
            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showmessage(
                    title ?: "",
                    body ?: ""
                )
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    fun showmessage(title:String, body:String, id:Long = System.currentTimeMillis()){
        val defaultSoundUri: Uri? =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = BuildConfig.VERSION_NAME
        val builder =
            Builder1(this,channelId)
                .setDefaults(DEFAULT_VIBRATE)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setSmallIcon(R.drawable.menu)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources,R.drawable.menu))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#FFFFFF"))
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(100,200,300,400,500,400,300,200,400))
                .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val channel = NotificationChannel(
                channelId,
                "${BuildConfig.VERSION_NAME} channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(id.toInt(),builder.build())

    }
}


