package com.example.waterfit

import kotlinx.serialization.Serializable

@Serializable
class WaterItem(
    val litres : String?,
    val date : String?) : java.io.Serializable {
}
