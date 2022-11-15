package com.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.google.android.material.snackbar.Snackbar
import com.example.minesweeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resetBtnListener()
        onCheckboxClicked(binding.checkboxFlag)
    }

    private fun resetBtnListener() {
        binding.btnReset.setOnClickListener {
            binding.mineSweeper.resetGame()
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkboxFlag -> {
                    if (checked) {
                        binding.mineSweeper.setFlagMode(true)
                    } else {
                        binding.mineSweeper.setFlagMode(false)
                    }
                }
            }
        }
    }

    fun deleteOne(view: View) {
        binding.txtCounter.text = (binding.txtCounter.text.toString().toInt() - 1).toString()
    }

    fun backToTen(view: View) {
        binding.txtCounter.text = resources.getString(R.string.numberTen)
    }

    fun showResult(win: Boolean) {
        if (win) {
            Snackbar.make(binding.root, resources.getString(R.string.win), Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(binding.root, resources.getString(R.string.loss), Snackbar.LENGTH_LONG).show()
        }
    }
}