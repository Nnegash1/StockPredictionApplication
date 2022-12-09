package com.example.stockpredictionapplication.model.entities


// To parse the JSON, install Klaxon and do:
//
//   val welcome10 = Welcome10.fromJson(jsonString)


import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

data class CompanyHistory(
    @SerializedName(value = "Meta Data")
    val metaData: MetaData,

    @SerializedName(value = "Time Series (Daily)")
    val timeSeriesDaily: Map<String, TimeSeriesDaily>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<CompanyHistory>(json)
    }
}

data class MetaData(
    @SerializedName(value = "1. Information")
    val the1Information: String,

    @SerializedName(value = "2. Symbol")
    val the2Symbol: String,

    @SerializedName(value = "3. Last Refreshed")
    val the3LastRefreshed: String,

    @SerializedName(value = "4. Output Size")
    val the4OutputSize: String,

    @SerializedName(value = "5. Time Zone")
    val the5TimeZone: String
)

data class TimeSeriesDaily(
    @SerializedName(value = "1. open")
    val the1Open: String,

    @SerializedName(value = "2. high")
    val the2High: String,

    @SerializedName(value = "3. low")
    val the3Low: String,

    @SerializedName(value = "4. close")
    val the4Close: String,

    @SerializedName(value = "5. volume")
    val the5Volume: String
)
