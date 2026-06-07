package br.edu.utfpr.trucoscoreboard

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
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

        val sharedPreferences = getSharedPreferences("truco_prefs", MODE_PRIVATE)
        firstPlayerName = sharedPreferences.getString("first_player_name", "").toString()
        secondPlayerName = sharedPreferences.getString("second_player_name", "").toString()
        
        binding.edtFirstPlayerWins.setText(firstPlayerName)
        binding.edtSecondPlayerName.setText(secondPlayerName)

        binding.btnConfirm.setOnClickListener { changeName() }
        binding.btnGoBack.setOnClickListener { goBack() }
    }

    private fun goBack() {
        finish()
    }

    private fun changeName() {
        firstPlayerName = binding.edtFirstPlayerWins.text.toString()
        secondPlayerName = binding.edtSecondPlayerName.text.toString()

        val sharedPreferences = getSharedPreferences("truco_prefs", MODE_PRIVATE)
        sharedPreferences.edit {
            putString("first_player_name", firstPlayerName)
            putString("second_player_name", secondPlayerName)
        }

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        Snackbar.make(binding.root, R.string.name_change_successfully, Snackbar.LENGTH_SHORT).show()
    }

}