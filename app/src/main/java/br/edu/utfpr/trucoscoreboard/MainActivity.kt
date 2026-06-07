package br.edu.utfpr.trucoscoreboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.trucoscoreboard.databinding.ActivityChangeNameBinding
import br.edu.utfpr.trucoscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var firstPlayerScore = 0
    private var secondPlayerScore = 0
    private var firstPlayerWins = 0
    private var secondPlayerWins = 0
    private var firstPlayerName = ""
    private var secondPlayerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            firstPlayerScore = savedInstanceState.getInt("first_player_score")
            secondPlayerScore = savedInstanceState.getInt("second_player_score")
            firstPlayerWins = savedInstanceState.getInt("first_player_wins")
            secondPlayerWins = savedInstanceState.getInt("second_player_wins")
            updateUI()
        } else {
            updateUI()
        }

        setupPlayerButtons(
            1,
            binding.btn1FirstPlayer,
            binding.btn3FirstPlayer,
            binding.btn6FirstPlayer,
            binding.btn9FirstPlayer,
            binding.btn12FirstPlayer
        )
        setupPlayerButtons(
            2,
            binding.btn1SecondPlayer,
            binding.btn3SecondPlayer,
            binding.btn6SecondPlayer,
            binding.btn9SecondPlayer,
            binding.btn12SecondPlayer
        )

        binding.btnClearHistory.setOnClickListener { resetWins() }
        binding.btnHistory.setOnClickListener { showHistoryActivity() }
        binding.btnChangeName.setOnClickListener { showChangeNameActivity() }
    }

    private fun showChangeNameActivity() {
        val intent = Intent(this, ChangeNameActivity::class.java)

        intent.putExtra("first_player_name", firstPlayerName)
        intent.putExtra("second_player_name", secondPlayerName)

        getResult.launch(intent)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == RESULT_OK) {
            firstPlayerName = result.data?.getStringExtra("first_player_name").toString()
            secondPlayerName = result.data?.getStringExtra("second_player_name").toString()
        }
    }

    private fun showHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)

        intent.putExtra("first_player_wins", firstPlayerWins)
        intent.putExtra("second_player_wins", secondPlayerWins)

        startActivity(intent)
    }

    private fun setupPlayerButtons(player: Int, vararg buttons: View) {
        val points = listOf(1, 3, 6, 9, 12)
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { addPoints(player, points[index]) }
        }
    }

    private fun addPoints(player: Int, points: Int) {
        when (player) {
            1 -> firstPlayerScore += points
            2 -> secondPlayerScore += points
        }
        updateUI()
        checkWinner()
    }

    private fun checkWinner() {
        if (firstPlayerScore >= 12) {
            firstPlayerWins++
            showWinnerDialog(getString(R.string.player_1_wins))
        } else if (secondPlayerScore >= 12) {
            secondPlayerWins++
            showWinnerDialog(getString(R.string.player_2_wins))
        }
    }

    private fun showWinnerDialog(message: String) {
        AlertDialog.Builder(this).setTitle(R.string.winner_dialog_title).setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ ->
                resetPoints()
            }.setCancelable(false).show()
    }

    private fun updateUI() {
        binding.tvFirstPlayerPoints.text =
            getString(R.string.player_points, firstPlayerScore.toString())
        binding.tvSecondPlayerPoints.text =
            getString(R.string.player_points, secondPlayerScore.toString())
    }

    private fun resetPoints() {
        firstPlayerScore = 0
        secondPlayerScore = 0
        updateUI()
    }

    private fun resetWins() {
        firstPlayerWins = 0
        secondPlayerWins = 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("first_player_score", firstPlayerScore)
        outState.putInt("second_player_score", secondPlayerScore)
        outState.putInt("first_player_wins", firstPlayerWins)
        outState.putInt("second_player_wins", secondPlayerWins)
    }
}