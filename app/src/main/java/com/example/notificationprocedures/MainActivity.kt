package com.example.notificationprocedures

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationprocedures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var CHANNEL_ID = "1"
    var counter:Int = 0
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.button.setOnClickListener {

            counter++
            mainBinding.button.text = counter.toString()
            if (counter % 5 == 0)
            {
                startNotification()
            }
        }
    }
    fun startNotification()
    {
        val intent = Intent(applicationContext,MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= 23)
        {
            PendingIntent.getActivity(applicationContext,0,intent
                ,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(applicationContext,0,intent
                ,PendingIntent.FLAG_UPDATE_CURRENT)

        }
        val builder = NotificationCompat.Builder(applicationContext,CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            builder.setSmallIcon(R.drawable.notifications)
                .setContentTitle("Title")
                .setContentText("Notification Text")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        else
        {

            builder.setSmallIcon(R.drawable.notifications)
                .setContentTitle("My Title")
                .setContentText("this is notification text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        }

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.notify(1,builder.build())






    }
}