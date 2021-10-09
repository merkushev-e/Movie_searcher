package ru.gb.moviesearcher.ui.main.model

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import ru.gb.moviesearcher.R

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val builder = NotificationCompat.Builder(context,"1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Movie Searcher")
            .setContentText("Please connect to the Internet")

        val notification: Notification = builder.build()
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1,notification)
    }
}