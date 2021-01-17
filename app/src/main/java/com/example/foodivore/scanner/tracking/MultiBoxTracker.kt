/*
This code is based on: https://github.com/tensorflow/tensorflow/blob/master/tensorflow/examples/android/src/org/tensorflow/demo/tracking/MultiBoxTracker.java

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/
package com.example.foodivore.scanner.tracking

import android.content.Context
import android.graphics.*
import android.graphics.Paint.*
import android.text.TextUtils
import android.util.Pair
import android.util.TypedValue
import com.example.foodivore.scanner.env.BorderedText
import com.example.foodivore.scanner.env.ImageUtils
import com.example.foodivore.scanner.test.ClassifierTflite
import java.util.*

/**
 * A tracker wrapping ObjectTracker that also handles non-max suppression and matching existing
 * objects to new detections.
 */
class MultiBoxTracker(private val context: Context) {
    private val TEXT_SIZE_DIP = 18f
    private val MIN_SIZE = 16.0f
    private val COLORS = intArrayOf(
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA,
        Color.WHITE,
        Color.parseColor("#55FF55"),
        Color.parseColor("#FFA500"),
        Color.parseColor("#FF8888"),
        Color.parseColor("#AAAAFF"),
        Color.parseColor("#FFFFAA"),
        Color.parseColor("#55AAAA"),
        Color.parseColor("#AA33AA"),
        Color.parseColor("#0D0068")
    )
    val screenRects: MutableList<Pair<Float, RectF>> = LinkedList()
//    private val logger: Logger = Logger()
    private val availableColors: Queue<Int> = LinkedList()
    private val trackedObjects: MutableList<TrackedRecognition> = LinkedList()
    private val boxPaint = Paint()
    private var textSizePx = 0f
    private var borderedText: BorderedText? = null
    private var frameToCanvasMatrix: Matrix? = null
    private var frameWidth = 0
    private var frameHeight = 0
    private var sensorOrientation = 0

    init {
        for (color in COLORS) {
            availableColors.add(color)
        }
        boxPaint.color = Color.RED
        boxPaint.style = Style.STROKE
        boxPaint.strokeWidth = 10.0f
        boxPaint.strokeCap = Cap.ROUND
        boxPaint.strokeJoin = Join.ROUND
        boxPaint.strokeMiter = 100f
        textSizePx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, context.resources.displayMetrics
        )
        borderedText = BorderedText(textSizePx)
    }

    @Synchronized
    fun setFrameConfiguration(
        width: Int, height: Int, sensorOrientation: Int
    ) {
        frameWidth = width
        frameHeight = height
        this.sensorOrientation = sensorOrientation
    }

    @Synchronized
    fun drawDebug(canvas: Canvas) {
        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 60.0f
        val boxPaint = Paint()
        boxPaint.color = Color.RED
        boxPaint.alpha = 200
        boxPaint.style = Style.STROKE
        for (detection in screenRects) {
            val rect = detection.second
            canvas.drawRect(rect, boxPaint)
            canvas.drawText("" + detection.first, rect.left, rect.top, textPaint)
            borderedText!!.drawText(canvas, rect.centerX(), rect.centerY(), "" + detection.first)
        }
    }

    @Synchronized
    fun trackResults(results: List<ClassifierTflite.Recognition>, timestamp: Long) {
//        logger.i("Processing %d results from %d", results.size, timestamp)
        processResults(results)
    }

    private fun getFrameToCanvasMatrix(): Matrix? {
        return frameToCanvasMatrix
    }

    @Synchronized
    fun draw(canvas: Canvas) {
        val rotated = sensorOrientation % 180 == 90
        val multiplier = Math.min(
            canvas.height / (if (rotated) frameWidth else frameHeight).toFloat(),
            canvas.width / (if (rotated) frameHeight else frameWidth).toFloat()
        )
        frameToCanvasMatrix = ImageUtils.getTransformationMatrix(
            frameWidth,
            frameHeight,
            (multiplier * if (rotated) frameHeight else frameWidth).toInt(),
            (multiplier * if (rotated) frameWidth else frameHeight).toInt(),
            sensorOrientation,
            false
        )
        for (recognition in trackedObjects) {
            val trackedPos = RectF(recognition.location)
            getFrameToCanvasMatrix()!!.mapRect(trackedPos)
            boxPaint.color = recognition.color
            val cornerSize = Math.min(trackedPos.width(), trackedPos.height()) / 8.0f
            canvas.drawRoundRect(trackedPos, cornerSize, cornerSize, boxPaint)
            val labelString = if (!TextUtils.isEmpty(recognition.title)) String.format(
                "%s %.2f", recognition.title,
                100 * recognition.detectionConfidence
            ) else String.format("%.2f", 100 * recognition.detectionConfidence)
            //            borderedText.drawText(canvas, trackedPos.left + cornerSize, trackedPos.top,
            // labelString);
            borderedText!!.drawText(
                canvas, trackedPos.left + cornerSize, trackedPos.top, "$labelString%",
            )
        }
    }

    private fun processResults(results: List<ClassifierTflite.Recognition>) {
        val rectsToTrack: MutableList<Pair<Float, ClassifierTflite.Recognition>> =
            LinkedList<Pair<Float, ClassifierTflite.Recognition>>()
        screenRects.clear()
        val rgbFrameToScreen = Matrix(getFrameToCanvasMatrix())
        for (result in results) {
            if (result.location == null) {
                continue
            }
            val detectionFrameRect = RectF(result.location)
            val detectionScreenRect = RectF()
            rgbFrameToScreen.mapRect(detectionScreenRect, detectionFrameRect)
//            logger.v(
//                "Result! Frame: " + result.getLocation()
//                    .toString() + " mapped to screen:" + detectionScreenRect
//            )
            screenRects.add(Pair(result.confidence, detectionScreenRect))
            if (detectionFrameRect.width() < MIN_SIZE || detectionFrameRect.height() < MIN_SIZE) {
//                logger.w("Degenerate rectangle! $detectionFrameRect")
                continue
            }
            rectsToTrack.add(Pair<Float, ClassifierTflite.Recognition>(result.confidence, result))
        }
        trackedObjects.clear()
        if (rectsToTrack.isEmpty()) {
//            logger.v("Nothing to track, aborting.")
            return
        }
        for (potential in rectsToTrack) {
            val trackedRecognition = TrackedRecognition()
            trackedRecognition.detectionConfidence = potential.first
            trackedRecognition.location = RectF(potential.second.location)
            trackedRecognition.title = potential.second.title
            trackedRecognition.color = COLORS[trackedObjects.size]
            trackedObjects.add(trackedRecognition)
            if (trackedObjects.size >= COLORS.size) {
                break
            }
        }
    }

    private class TrackedRecognition {
        var location: RectF? = null
        var detectionConfidence = 0f
        var color = 0
        var title: String? = null
    }
}
