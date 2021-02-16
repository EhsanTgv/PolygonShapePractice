package com.taghavi.polygonshape

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

class PolygonalDrawable(color: Int, sides: Int) : Drawable() {
    private var numberOfSides = 3
    private val polygon = Path()
    private val temporal = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun draw(canvas: Canvas) {
        canvas.drawPath(polygon, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return paint.alpha
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        computeHex(bounds)
        invalidateSelf()
    }

    private fun computeHex(bounds: Rect) {
        val width = bounds.width()
        val height = bounds.height()
        val size = Math.min(width, height)
        val centerX = bounds.left + width / 2
        val centerY = bounds.top + height / 2
        polygon.reset()
        polygon.addPath(createHexagon(size, centerX, centerY))
        polygon.addPath(createHexagon((size * .8f).toInt(), centerX, centerY))
    }

    private fun createHexagon(size: Int, centerX: Int, centerY: Int): Path {
        val section = (2.0 * Math.PI / numberOfSides).toFloat()
        val radius = size / 2
        val polygonPath = temporal
        polygonPath.reset()
        polygonPath.moveTo(
            (centerX + radius * cos(0.0)).toFloat(),
            (centerY + radius * sin(0.0)).toFloat()
        )
        for (i in 1 until numberOfSides) {
            polygonPath.lineTo(
                (centerX + radius * cos((section * i).toDouble())).toFloat(),
                (centerY + radius * sin((section * i).toDouble())).toFloat()
            )
        }
        polygonPath.close()
        return polygonPath
    }

    init {
        paint.color = color
        polygon.fillType = Path.FillType.EVEN_ODD
        numberOfSides = sides
    }
}