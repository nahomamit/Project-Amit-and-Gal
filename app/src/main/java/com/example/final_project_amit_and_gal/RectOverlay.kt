package com.example.final_project_amit_and_gal

import android.graphics.*
import com.example.final_project_amit_and_gal.GraphicOverlay.Graphic


class RectOverlay(private val graphicOverlay: GraphicOverlay, private val rect: Rect) :
    Graphic(graphicOverlay) {
    private val RECT_COLOR = Color.RED
    private val strokeWidth = 4.0f
    private val rectPaint: Paint
    override fun draw(canvas: Canvas?) {
        val rectF = RectF(rect)
        rectF.left = translateX(rectF.left)
        rectF.right = translateX(rectF.right)
        rectF.top = translateY(rectF.top)
        rectF.bottom = translateY(rectF.bottom)
        canvas!!.drawRect(rectF, rectPaint)
    }

    init {
        rectPaint = Paint()
        rectPaint.color = RECT_COLOR
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = strokeWidth
        postInvalidate()
    }
}