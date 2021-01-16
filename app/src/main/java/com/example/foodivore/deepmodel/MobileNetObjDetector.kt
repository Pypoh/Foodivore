package com.example.foodivore.deepmodel

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class MobileNetObjDetector(assetManager: AssetManager) {

    companion object {
        private val MODEL_FILENAME = "detect.tflite"
        private val LABEL_FILENAME = "labelmap.txt"
        private val INPUT_SIZE = 300
        private val NUM_BYTES_PER_CHANNEL = 1
        private val IMAGE_MEAN = 128.0f
        private val IMAGE_STD = 128.0f
        private val NUM_DETECTIONS = 10
        private val LOGGING_TAG = MobileNetObjDetector::class.java.name

        fun create(assetManager: AssetManager): MobileNetObjDetector {
            return MobileNetObjDetector(assetManager)
        }

        fun loadModelFile(assets: AssetManager): MappedByteBuffer {
            val fileDescriptor = assets.openFd(MODEL_FILENAME)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }
    }

    private var imgData: ByteBuffer
    private lateinit var tfLite: Interpreter
    private var intValues: IntArray
    private var outputLocations: Array<Array<FloatArray>>
    private var outputClasses: Array<FloatArray>
    private var outputScores: Array<FloatArray>
    private var numDetections: FloatArray
    private val labels = Vector<String>()

    init {
        imgData =
            ByteBuffer.allocateDirect(1 * INPUT_SIZE * INPUT_SIZE * 3 * NUM_BYTES_PER_CHANNEL)
        imgData.order(ByteOrder.nativeOrder())
        intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        outputLocations = Array(1) {
            Array(NUM_DETECTIONS) {
                FloatArray(
                    4
                )
            }
        }
        outputClasses = Array(1) { FloatArray(NUM_DETECTIONS) }
        outputScores = Array(1) { FloatArray(NUM_DETECTIONS) }
        numDetections = FloatArray(1)

        val labelsInput: InputStream = assetManager.open(LABEL_FILENAME)
        val br = BufferedReader(InputStreamReader(labelsInput))
        var line: String?
        while (br.readLine().also { line = it } != null) {
            labels.add(line)
        }
        br.close()

        try {
            tfLite = Interpreter(loadModelFile(assetManager))
            Log.i(LOGGING_TAG, "Input tensor shapes:")
            for (i in 0 until tfLite.inputTensorCount) {
                val shape = tfLite.getInputTensor(i).shape()
                var stringShape = ""
                for (j in shape.indices) {
                    stringShape = stringShape + ", " + shape[j]
                }
                Log.i(
                    LOGGING_TAG,
                    "Shape of input tensor $i: $stringShape"
                )
            }
            Log.i(LOGGING_TAG, "Output tensor shapes:")
            for (i in 0 until tfLite.outputTensorCount) {
                val shape = tfLite.getOutputTensor(i).shape()
                var stringShape = ""
                for (j in shape.indices) {
                    stringShape = stringShape + ", " + shape[j]
                }
                Log.i(
                    LOGGING_TAG,
                    "Shape of output tensor " + i + ": " + tfLite.getOutputTensor(i)
                        .name() + " " + stringShape
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    fun close() {
        tfLite.close()
    }

    fun detectObjects(bitmap: Bitmap) : List<DetectionResult> {
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        imgData.rewind()
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val pixelValue = intValues[i * INPUT_SIZE + j]
                imgData.put((pixelValue shr 16 and 0xFF).toByte())
                imgData.put((pixelValue shr 8 and 0xFF).toByte())
                imgData.put((pixelValue and 0xFF).toByte())
            }
        }

        val inputArray = arrayOf<Any>(imgData)
        val outputMap: MutableMap<Int, Any> = HashMap()
        outputMap[0] = outputLocations
        outputMap[1] = outputClasses
        outputMap[2] = outputScores
        outputMap[3] = numDetections
        tfLite.runForMultipleInputsOutputs(inputArray, outputMap)

        val recognitions = ArrayList<DetectionResult>(NUM_DETECTIONS)
        for (i in 0 until NUM_DETECTIONS) {
            val detection = RectF(
                outputLocations[0][i][1] * INPUT_SIZE,
                outputLocations[0][i][0] * INPUT_SIZE,
                outputLocations[0][i][3] * INPUT_SIZE,
                outputLocations[0][i][2] * INPUT_SIZE
            )
            val labelOffset = 1
            recognitions.add(
                DetectionResult(
                    i,
                    labels[outputClasses[0][i].toInt() + labelOffset],
                    outputScores[0][i],
                    detection
                )
            )
        }
        return recognitions
    }


}