package com.example.learnlanguage.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class FlipViewEnglish(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val frontText = "Card English"
    var backText = "Back"
    var isFlipped = false
    private val paintText: Paint = Paint()
    private val paintBackground: Paint

    init {
        paintText.color = Color.BLACK
        paintText.textSize = 50f
        paintBackground = Paint()
        paintBackground.color = Color.GREEN
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2
        val centerY = height / 2
        if (isFlipped) {
            drawBackground(canvas, centerX, centerY)
            drawText(canvas, backText, centerX, centerY)
        } else {
            drawText(canvas, frontText, centerX, centerY)
        }
    }

    private fun drawText(canvas: Canvas, text: String, x: Int, y: Int) {
        val bounds = Rect()
        paintText.getTextBounds(text, 0, text.length, bounds)
        val textWidth = bounds.width()
        val textHeight = bounds.height()
        canvas.drawText(
            text,
            (x - textWidth / 2).toFloat(),
            (y + textHeight / 2).toFloat(),
            paintText
        )
    }

    private fun drawBackground(canvas: Canvas, x: Int, y: Int) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
    }

    fun flip() {
        isFlipped = !isFlipped
        invalidate()
    }
     fun getTextBackData(text: String) {
        this.backText = text
    }
    override fun performClick(): Boolean {
        flip()
        return super.performClick()
    }
}
