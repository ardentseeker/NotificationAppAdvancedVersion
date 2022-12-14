package com.example.notificationprocedures

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationprocedures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var CHANNEL_ID = "1"
    var counter: Int = 0
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
            if (counter % 5 == 0) {
                startNotification()
            }
        }
    }

    private fun startNotification()
    {
        val intent = Intent(applicationContext, MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= 23)
        { PendingIntent.getActivity(applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        else
        {
            PendingIntent.getActivity(
                applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

        }
        //action Button

        val actionIntent = Intent(applicationContext, Receiver::class.java)
        actionIntent.putExtra("toast", "this is notification msg")
        val actionPending = if (Build.VERSION.SDK_INT >= 23)
        {
            PendingIntent.getBroadcast(applicationContext, 1, actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else
        {
            PendingIntent.getBroadcast(
                applicationContext, 1, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        //dismissButton
        val dismissIntent = Intent(applicationContext, ReceiverDismiss::class.java)
        val dismissPending = if (Build.VERSION.SDK_INT >= 23)
        {
            PendingIntent.getBroadcast(applicationContext, 2, dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else
        {
            PendingIntent.getBroadcast(
                applicationContext, 2, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
            val icon : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.pic)
            val text :String = resources.getString(R.string.big_text)
            val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT)
                val manager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
                builder.setSmallIcon(R.drawable.notifications)
                    .setContentTitle("Title")
                    .setContentText("Notification Text")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.notifications, "Toast msg",actionPending)
                    .addAction(R.drawable.notifications, "Dismissed",dismissPending)
                    .setLargeIcon(icon)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                    //.setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            } else {

                builder.setSmallIcon(R.drawable.notifications)
                    .setContentTitle("My Title")
                    .setContentText("this is notification text")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.notifications,"Toast msg",actionPending)
                    .addAction(R.drawable.notifications, "Dismissed",dismissPending)
                    .setLargeIcon(icon)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                    //.setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            }

            val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
            notificationManagerCompat.notify(1, builder.build())

        }
    }
