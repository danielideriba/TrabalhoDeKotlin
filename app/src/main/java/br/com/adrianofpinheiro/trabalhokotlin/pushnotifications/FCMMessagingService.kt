package br.com.adrianofpinheiro.trabalhokotlin.pushnotifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import br.com.adrianofpinheiro.trabalhokotlin.BuildConfig
import br.com.adrianofpinheiro.trabalhokotlin.R
import br.com.adrianofpinheiro.trabalhokotlin.ui.activitys.MainActivity
import br.com.adrianofpinheiro.trabalhokotlin.utils.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by danielideriba on 25/04/19.
 */

class FCMMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        if(BuildConfig.DEBUG) {
            Log.d(TAG, "NEW_TOKEN - ${token}")
        }

        Prefs.saveToken = token.toString()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "From: " + remoteMessage!!.from!!)
            Log.d(TAG, "Notification Message Body: " + remoteMessage.notification!!.body!!)
        }


        sendNotification(remoteMessage!!)
        val intent = Intent(this@FCMMessagingService, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("message", remoteMessage.notification!!.body!!)
        startActivity(intent)

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setContentText(remoteMessage.notification!!.body)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private val TAG = "FCM Service"
    }
}