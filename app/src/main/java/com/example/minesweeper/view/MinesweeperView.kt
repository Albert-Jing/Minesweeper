package com.example.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.minesweeper.MainActivity
import com.example.minesweeper.R
import com.example.minesweeper.model.MineSweeperModel

class MineSweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintCell = Paint()


    var bitmapOne : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.one)
    var bitmapTwo : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.two)
    var bitmapThree : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.three)
    var bitmapFour : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.four)
    var bitmapFive : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.five)
    var bitmapSix : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.six)
    var bitmapSeven : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.seven)
    var bitmapEight : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.eight)

    var bitmapBomb : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bomb)
    var bitmapExplodingBomb: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bomb_explode)
    var bitmapHidden : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hidden)
    var flagBitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.flag)
    var bitmapEmpty : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.empty)

    init {
        paintBackGround.color = Color.GRAY
        paintBackGround.style = Paint.Style.FILL

        paintCell.color = Color.BLACK
        paintCell.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        showBitmaps()
    }

    private fun showBitmaps() {
        val row = width/9
        val column = height/9
        bitmapBombs(row, column)
        bitmapBombCount(row, column)
        bitmapCells(row, column)
    }

    private fun bitmapCells(row : Int, column: Int) {
        bitmapEmpty = Bitmap.createScaledBitmap(bitmapEmpty, row, column, false)
        flagBitmap = Bitmap.createScaledBitmap(flagBitmap, row, column, false)
        bitmapHidden = Bitmap.createScaledBitmap(bitmapHidden, row, column, false)
    }

    private fun bitmapBombs(row : Int, column : Int) {
        bitmapBomb = Bitmap.createScaledBitmap(bitmapBomb, row, column, false)
        bitmapExplodingBomb = Bitmap.createScaledBitmap(bitmapExplodingBomb, row, column, false)
    }

    private fun bitmapBombCount(row : Int, column : Int) {
        bitmapOne = Bitmap.createScaledBitmap(bitmapOne, row, column, false)
        bitmapTwo = Bitmap.createScaledBitmap(bitmapTwo, row, column, false)
        bitmapThree = Bitmap.createScaledBitmap(bitmapThree, row, column,false)
        bitmapFour = Bitmap.createScaledBitmap(bitmapFour, row, column,false)
        bitmapFive = Bitmap.createScaledBitmap(bitmapFive, row, column,false)
        bitmapSix = Bitmap.createScaledBitmap(bitmapSix, row, column,false)
        bitmapSeven = Bitmap.createScaledBitmap(bitmapSeven, row, column,false)
        bitmapEight = Bitmap.createScaledBitmap(bitmapEight, row, column,false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBorder(canvas)
        drawRows(canvas)
        drawColumns(canvas)
        drawBitmaps(canvas)
        drawCurrentState(canvas)
    }

    private fun drawBorder(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
    }

    private fun drawRows(canvas: Canvas) {
        for (i in 1..8) {
            canvas.drawLine(
                0f, (i * height / 9).toFloat(), width.toFloat(), (i * height / 9).toFloat(),
                paintLine
            )
        }
    }

    private fun drawColumns(canvas: Canvas) {
        for (i in 1..8) {
            canvas.drawLine(
                (i * width / 9).toFloat(), 0f, (i * width / 9).toFloat(), height.toFloat(),
                paintLine
            )
        }
    }

    private fun drawBitmaps(canvas: Canvas) {
        for (i in 0..8) {
            for (j in 0..8) {
                canvas?.drawBitmap(
                    bitmapHidden,
                    (i * width / 9).toFloat(),
                    (j * height / 9).toFloat(),
                    null
                )
            }
        }
    }

    private fun drawCurrentState(canvas: Canvas) {
        for (x in 0..8) {
            for (y in 0..8) {
                if (MineSweeperModel.getDisplay(x, y) != -1) {
                    drawClickedCell(x, y, canvas)
                } else {
                    canvas?.drawBitmap(bitmapHidden, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    private fun drawClickedCell(x: Int, y: Int, canvas: Canvas) {
        val content = MineSweeperModel.getFieldContent(x, y)
        if (content == MineSweeperModel.FLAG) {
            canvas?.drawBitmap(flagBitmap, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (content == MineSweeperModel.BOMB) {
            if (MineSweeperModel.getDisplay(x, y) != -2) canvas?.drawBitmap(bitmapBomb, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
            else canvas?.drawBitmap(bitmapExplodingBomb, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else {
            val bombCount = MineSweeperModel.getDisplay(x, y)
            drawBombCount(canvas, x, y, bombCount)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 9)
            val tY = event.y.toInt() / (height / 9)

            if (tX < 9 && tY < 9) {
                if (MineSweeperModel.getDisplay(tX, tY) == -1) {
                    clickedCell(tX, tY)
                }
            }
        }
        return true
    }

    private fun clickedCell(tX: Int, tY: Int) {
        if (MineSweeperModel.flagMode) {
            clickedFlag(tX, tY)
        } else {
            if (MineSweeperModel.getFieldContent(tX, tY) == MineSweeperModel.BOMB) {
                clickedBomb(tX, tY)
            } else {
                clickedEmptyCell(tX, tY)
            }
        }
        invalidate()
    }

    private fun clickedBomb(tX: Int, tY: Int) {
        MineSweeperModel.setDisplay(tX, tY, -2)
        endGame(false)
    }

    private fun clickedEmptyCell(tX: Int, tY: Int) {
        val nearbyBombCount = MineSweeperModel.getNearbyBomb(tX, tY)
        if (nearbyBombCount == 0) {
            MineSweeperModel.expand(tX, tY)
        } else {
            MineSweeperModel.setDisplay(tX, tY, nearbyBombCount)
        }
    }

    private fun clickedFlag(tX: Int, tY: Int) {
        if (MineSweeperModel.getFieldContent(tX, tY) == MineSweeperModel.EMPTY) {
            endGame(false)
        } else if (MineSweeperModel.getFieldContent(tX, tY) == MineSweeperModel.BOMB) {
            MineSweeperModel.makeFlag(tX, tY)
            (context as MainActivity).deleteOne(this)
            MineSweeperModel.setDisplay(tX, tY, 0)
            MineSweeperModel.decreaseBombCount()
            if (MineSweeperModel.getBombCount() == 0) {
                endGame(true)
            }
        }
    }

    fun setFlagMode(flagMode: Boolean) {
        MineSweeperModel.setFlagModeModel(flagMode)
    }

    private fun drawBombCount(canvas: Canvas, x: Int, y: Int, count: Int) {
        if (count == 0) {
            canvas?.drawBitmap(bitmapEmpty, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 1) {
            canvas?.drawBitmap(bitmapOne, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 2) {
            canvas?.drawBitmap(bitmapTwo, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 3) {
            canvas?.drawBitmap(bitmapThree, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 4) {
            canvas?.drawBitmap(bitmapFour, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 5) {
            canvas?.drawBitmap(bitmapFive, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 6) {
            canvas?.drawBitmap(bitmapSix, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else if (count == 7) {
            canvas?.drawBitmap(bitmapSeven, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        } else {
            canvas?.drawBitmap(bitmapEight, (x * width / 9).toFloat(), (y * height / 9).toFloat(), null)
        }
    }

    private fun endGame(win: Boolean) {
        (context as MainActivity).showResult(win)
        MineSweeperModel.displayAll()
    }

    fun resetGame() {
        MineSweeperModel.resetModel()
        invalidate()
        (context as MainActivity).backToTen(this)
    }
}
