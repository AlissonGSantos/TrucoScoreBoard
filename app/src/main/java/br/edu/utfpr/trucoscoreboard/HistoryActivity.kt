package br.edu.utfpr.trucoscoreboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.trucoscoreboard.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private var firstPlayerWins = 0
    private var secondPlayerWins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvFirstPlayerWins.text = getString(R.string.player_wins, intent.getIntExtra("first_player_wins", 0).toString())
        binding.tvSecondPlayerWins.text = getString(R.string.player_wins, intent.getIntExtra("second_player_wins", 0).toString())

        binding.btnGoBack.setOnClickListener { finish() }

    }
}