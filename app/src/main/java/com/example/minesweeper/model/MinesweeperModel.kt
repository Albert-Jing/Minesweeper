package com.example.minesweeper.model

import kotlin.random.Random.Default.nextInt

object MineSweeperModel {
    const val EMPTY: Short = 0
    const val FLAG: Short = 1
    const val BOMB: Short = 2
    var flagMode: Boolean = false
    private const val startBombCount = 10
    private var bombsLeft = startBombCount

    private val model = arrayOf(
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY))

    private val display = arrayOf(
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1))

    init {
        generateBombs()
    }

    fun setFlagModeModel(flagMode: Boolean) {
        this.flagMode = flagMode
    }

    fun resetModel() {
        for (i in 0..8) {
            for (j in 0..8) {
                model[j][i] = EMPTY
                display[j][i] = -1
            }
        }
        generateBombs()
    }

    fun getFieldContent(x: Int, y: Int) = model[y][x]

    private fun generateBombs() {
        val randomIndices = generateRandomPositions()
        for (idx in randomIndices) {
            val pair = indexToCoordinates(idx)
            model[pair.first][pair.second] = BOMB
        }
        bombsLeft = startBombCount
    }

    private fun generateRandomPositions() : MutableSet<Int> {
        val set = mutableSetOf<Int>()
        while (set.size < startBombCount) {
            set.add(nextInt(0, 80))
        }
        return set
    }

    private fun indexToCoordinates(i: Int) : Pair<Int, Int> {
        val column = (i % 9)
        val row = Math.floorDiv(i, 9)
        return Pair(row, column)
    }

    fun makeFlag(x: Int, y: Int) {
        model[y][x] = FLAG
    }

    fun getDisplay(x: Int, y: Int) : Int {
        return display[y][x]
    }

    fun setDisplay(x: Int, y: Int, count: Int) {
        display[y][x] = count
    }

    fun getNearbyBomb(x: Int, y: Int) : Int {
        var count : Int = 0
        for (i in x-1..x+1) {
            for (j in y-1..y+1) {
                if (i in 0..8 && j in 0..8 && (model[j][i] == FLAG || model[j][i] == BOMB)) {
                    count += 1
                }
            }
        }
        return count
    }

    fun expand(x: Int, y: Int) {
        expandHelper(x, y)
    }

    private fun expandHelper(x: Int, y: Int) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9 || display[y][x] != -1 || model[y][x] != EMPTY)
            return
        val bombCount = getNearbyBomb(x, y)
        display[y][x] = bombCount
        if (bombCount == 0) {
            expandHelper(x+1, y)
            expandHelper(x-1, y)
            expandHelper(x, y+1)
            expandHelper(x, y-1)
            expandHelper(x-1, y-1)
            expandHelper(x+1, y+1)
            expandHelper(x-1, y+1)
            expandHelper(x+1, y-1)
        }
    }

    fun displayAll() {
        for (i in 0..8) {
            for (j in 0..8) {
                if (display[j][i] == -1) {
                    display[j][i] = 0
                }
            }
        }
    }

    fun decreaseBombCount() {
        bombsLeft -= 1
    }

    fun getBombCount() : Int {
        return bombsLeft
    }
}