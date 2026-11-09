package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1SecondaryActivity : AppCompatActivity() {

    private lateinit var receivedInstructionsText: EditText
    private lateinit var registerButton: Button
    private lateinit var cancelButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)

        receivedInstructionsText = findViewById(R.id.received_text)
        registerButton = findViewById(R.id.register_button)
        cancelButton = findViewById(R.id.cancel_button)

        val instructions = intent.getStringExtra(Constants.INSTRUCTIONS) ?: ""

        receivedInstructionsText.setText(instructions)

        registerButton.setOnClickListener {
            val reply = Intent().putExtra(Constants.INSTRUCTIONS, instructions)
            setResult(RESULT_OK, reply)
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}