package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Colocviu1_1Service : Service() {

    private var isRunning = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val instructions = intent?.getStringExtra(Constants.INSTRUCTIONS) ?: ""
        isRunning = true

        Thread {
            Thread.sleep(Constants.DELAY)

            if (isRunning) {
                val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val broadcast = Intent(Constants.ACTION_INSTRUCTIONS)

                broadcast.putExtra(Constants.DATE_TIME, dateTime)
                broadcast.putExtra(Constants.INSTRUCTIONS, instructions)
                sendBroadcast(broadcast)

                Log.d(Constants.TAG, "Trimis: $dateTime ; $instructions")
            }
        }.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }
}