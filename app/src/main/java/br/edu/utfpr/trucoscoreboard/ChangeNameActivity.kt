package br.edu.utfpr.trucoscoreboard

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.trucoscoreboard.databinding.ActivityChangeNameBinding
import com.google.android.material.snackbar.Snackbar

class ChangeNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeNameBinding
    private var firstPlayerName = ""
    private var secondPlayerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeNameBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firstPlayerName = intent.getStringExtra("first_player_name").toString()
        secondPlayerName = intent.getStringExtra("second_player_name").toString()
        
        binding.edtFirstPlayerWins.setText(firstPlayerName)
        binding.edtSecondPlayerName.setText(secondPlayerName)

        binding.btnConfirm.setOnClickListener { changeName() }
        binding.btnGoBack.setOnClickListener { goBack() }
    }

    private fun goBack() {
        val resultIntent = Intent()
        resultIntent.putExtra("first_player_name", firstPlayerName)
        resultIntent.putExtra("second_player_name", secondPlayerName)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun changeName() {
        firstPlayerName = binding.edtFirstPlayerWins.text.toString()
        secondPlayerName = binding.edtSecondPlayerName.text.toString()

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        Snackbar.make(binding.root, "Nomes alterados com sucesso!", Snackbar.LENGTH_SHORT).show()
    }

}