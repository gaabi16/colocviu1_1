package ro.pub.cs.systems.eim.colocviu1_1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {

    private lateinit var northButton: Button
    private lateinit var eastButton: Button
    private lateinit var westButton: Button
    private lateinit var southButton: Button
    private lateinit var instructionsText: EditText

    private var buttonCount = 0

    private val dirListener = View.OnClickListener { view ->
        val direction = (view as Button).text.toString()
        instructionsText = findViewById(R.id.instructions_text)
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

        northButton.setOnClickListener(dirListener)
        eastButton.setOnClickListener(dirListener)
        westButton.setOnClickListener(dirListener)
        southButton.setOnClickListener(dirListener)
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

}