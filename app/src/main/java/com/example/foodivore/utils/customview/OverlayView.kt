package com.example.foodivore.utils.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.foodivore.deepmodel.DetectionResult
import java.util.*

class OverlayView : View {

    companion object {
        private val INPUT_SIZE = 300

    }

    private val paint: Paint = Paint()
    private val callbacks = mutableListOf<DrawCallback?>()
    private var results: List<DetectionResult>? = null
    private var colors: List<Int>? = null
    private var resultsViewHeight = 0f

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            20f,
            resources.displayMetrics
        )
        resultsViewHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 112f, resources.displayMetrics)
    }

    fun addCallback(callback: DrawCallback?) {
        callbacks.add(callback)
    }

    @SuppressLint("DefaultLocale")
    override fun onDraw(canvas: Canvas?) {
        for (callback in callbacks) {
            callback!!.drawCallback(canvas)
        }

        if (results != null) {
            for (i in results!!.indices) {
                if (results!![i].confidence!! > 0.5) {
                    val box = results!![i].location?.let { reCalcSize(it) }
                    val title: String = results!![i].title + String.format(
                        " %2.2f",
                        results!![i].confidence?.times(100)
                    ) + "%"
                    paint.color = Color.RED
                    paint.style = Paint.Style.STROKE
                    canvas!!.drawRect(box!!, paint)
                    paint.strokeWidth = 2.0f
                    paint.style = Paint.Style.FILL_AND_STROKE
                    canvas.drawText(title, box.left, box.top, paint)
                }
            }
        }
    }

    fun setResults(results: List<DetectionResult>?) {
        this.results = results
        postInvalidate()
    }

    interface DrawCallback {
        fun drawCallback(canvas: Canvas?)
    }

    private fun reCalcSize(rect: RectF): RectF? {
        val padding = 5
        val overlayViewHeight = height - resultsViewHeight
        val sizeMultiplier = Math.min(
            width.toFloat() / INPUT_SIZE as Float,
            overlayViewHeight / INPUT_SIZE as Float
        )
        val offsetX: Float = (width - INPUT_SIZE * sizeMultiplier) / 2
        val offsetY: Float =
            (overlayViewHeight - INPUT_SIZE * sizeMultiplier) / 2 + resultsViewHeight
        val left = Math.max(padding.toFloat(), sizeMultiplier * rect.left + offsetX)
        val top = Math.max(offsetY + padding, sizeMultiplier * rect.top + offsetY)
        val right = Math.min(rect.right * sizeMultiplier, (width - padding).toFloat())
        val bottom = Math.min(rect.bottom * sizeMultiplier + offsetY, (height - padding).toFloat())
        return RectF(left, top, right, bottom)
    }
}