package ro.pub.cs.systems.eim.colocviu1_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {

    private lateinit var northButton: Button
    private lateinit var eastButton: Button
    private lateinit var westButton: Button
    private lateinit var southButton: Button
    private lateinit var instructionsText: EditText

    private lateinit var navigateButton: Button

    private var buttonCount = 0

    private val secondaryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val registered = result.data?.getStringExtra(Constants.INSTRUCTIONS) ?: ""
            Toast.makeText(this, "Inregistrat $registered", Toast.LENGTH_LONG).show()
            } else {
            Toast.makeText(this, "Anulat", Toast.LENGTH_SHORT).show()
        }
    }

    private val dirListener = View.OnClickListener { view ->
        val direction = (view as Button).text.toString()
        val current = instructionsText.text.toString()

        var textNou = ""

        if (current.isEmpty()) {
            textNou = direction
        } else {
            textNou = "$current, $direction"
        }

        instructionsText.setText(textNou)
        buttonCount++

        Log.d(Constants.TAG, "S-au apasat: $buttonCount")

        if (buttonCount >= 4) {
            val serviceIntent = Intent(this, Colocviu1_1Service::class.java)
            serviceIntent.putExtra(Constants.INSTRUCTIONS, textNou)
            startService(serviceIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        northButton = findViewById(R.id.north_button)
        eastButton = findViewById(R.id.east_button)
        westButton = findViewById(R.id.west_button)
        southButton = findViewById(R.id.south_button)
        instructionsText = findViewById(R.id.instructions_text)

        northButton.setOnClickListener(dirListener)
        eastButton.setOnClickListener(dirListener)
        westButton.setOnClickListener(dirListener)
        southButton.setOnClickListener(dirListener)

        navigateButton = findViewById(R.id.navigate_button)

        navigateButton.setOnClickListener {
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)
            intent.putExtra(Constants.INSTRUCTIONS, instructionsText.text.toString())
            secondaryLauncher.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.BUTTON_COUNT, buttonCount)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        buttonCount = savedInstanceState.getInt(Constants.BUTTON_COUNT)
        Toast.makeText(this, "S-au apasat: $buttonCount", Toast.LENGTH_SHORT).show()
        Log.d(Constants.TAG, "S-au apasat: $buttonCount")
    }

    override fun onDestroy() {
        stopService(Intent(this, Colocviu1_1Service::class.java))
        super.onDestroy()
    }


}