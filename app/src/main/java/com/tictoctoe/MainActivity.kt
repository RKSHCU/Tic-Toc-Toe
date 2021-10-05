package com.tictoctoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.tictoctoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val values: DataValues = DataValues("Player X Turn")
    private var player: Boolean = false
    private var count: Int = 0
    private lateinit var btnArray: Array<Array<Button>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            dataValue = values
            btnArray = arrayOf(
                arrayOf<Button>(btn1, btn2, btn3),
                arrayOf<Button>(btn4, btn5, btn6),
                arrayOf<Button>(btn7, btn8, btn9)
            )
            for (i in 0..2)
                for (j in 0..2)
                    btnArray[i][j].setOnClickListener {
                        setButtonText(it)
                        checkWinner()
                        checkDraw()
                    }
            resetbtn.setOnClickListener {
                reset()
            }
            invalidateAll()
        }
    }

    private fun checkDraw() {
        if(count==9)
            binding.apply{
                statusView.text="Match Draw"
                statusView.setTextColor(Color.parseColor("#0000ff"))
            }
    }

    private fun checkWinner() {
        //Horizontal Check
        for (i in 0..2)
            if (btnArray[i][0].text == btnArray[i][1].text && btnArray[i][0].text == btnArray[i][2].text && btnArray[i][0].text != "")
                winnerMessage(btnArray[i][0].text.toString())

        //Vertical Check
        for (i in 0..2)
            if (btnArray[0][i].text == btnArray[1][i].text && btnArray[0][i].text == btnArray[2][i].text && btnArray[0][i].text != "")
                winnerMessage(btnArray[0][i].text.toString())
        //Diagonal Check
        if(btnArray[0][0].text == btnArray[1][1].text && btnArray[0][0].text==btnArray[2][2].text && btnArray[0][0].text!="")
            winnerMessage(btnArray[0][0].text.toString())
        if(btnArray[0][2].text == btnArray[1][1].text && btnArray[0][2].text==btnArray[2][0].text && btnArray[0][2].text!="")
            winnerMessage(btnArray[0][2].text.toString())
    }

    private fun winnerMessage(winner: String) {
        values.status = "Player $winner Winner"
        binding.statusView.setTextColor(Color.parseColor("#008000"))
        for (i in 0..2)
            for (j in 0..2)
                btnArray[i][j].isEnabled = false
        binding.invalidateAll()
    }

    private fun setButtonText(view: View) {
        when (view.id) {
            R.id.btn1 -> updateText(0, 0, player)
            R.id.btn2 -> updateText(0, 1, player)
            R.id.btn3 -> updateText(0, 2, player)
            R.id.btn4 -> updateText(1, 0, player)
            R.id.btn5 -> updateText(1, 1, player)
            R.id.btn6 -> updateText(1, 2, player)
            R.id.btn7 -> updateText(2, 0, player)
            R.id.btn8 -> updateText(2, 1, player)
            R.id.btn9 -> updateText(2, 2, player)
        }

    }

    private fun updateText(row: Int, col: Int, player: Boolean) {
        if (btnArray[row][col].text == "") {
            if (this.player) {
                btnArray[row][col].text = "0"
                values.status = "Player X Turn"
            } else {
                btnArray[row][col].text = "X"
                values.status = "Player 0 Turn"
            }
            this.player = !player
            count++;
            binding.invalidateAll()
        } else
            Toast.makeText(this, "This Field is already occupied", Toast.LENGTH_SHORT).show()
    }

    private fun reset() {
        for (i in 0..2)
            for (j in 0..2) {
                btnArray[i][j].text = ""
                btnArray[i][j].isEnabled = true
            }
        count = 0
        values.status = "Player X Turn"
        player = false
        binding.apply {
            statusView.setTextColor(Color.parseColor("#000000"))
            invalidateAll()
        }
    }
}