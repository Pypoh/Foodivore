package com.example.foodivore.deepmodel

import android.graphics.RectF

class DetectionResult(id: Int, title: String, confidence: Float, location: RectF) : Comparable<DetectionResult> {

    val id: Int? = null
    val title: String? = null
    val confidence: Float? = null
    var location: RectF? = null

    override fun compareTo(other: DetectionResult): Int {
        return compareValuesBy(other.confidence, this.confidence)
    }

    override fun toString(): String {
        return "DetectionResult{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", confidence=" + confidence +
                ", location=" + location +
                '}'
    }
}