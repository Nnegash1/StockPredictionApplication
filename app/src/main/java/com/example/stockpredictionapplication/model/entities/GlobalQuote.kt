package com.example.stockpredictionapplication.model.entities

// To parse the JSON, install Klaxon and do:
//
//   val welcome9 = Welcome9.fromJson(jsonString)


import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

data class Global(
    @SerializedName(value = "Global Quote")
    val globalQuote: GlobalQuote
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Global>(json)
    }
}

data class GlobalQuote(
    @SerializedName(value = "01. symbol")
    val Symbol: String,

    @SerializedName(value = "02. open")
    val _open: String,

    @SerializedName(value = "03. high")
    val high: String,

    @SerializedName(value = "04. low")
    val low: String,

    @SerializedName(value = "05. price")
    val price: String,

    @SerializedName(value = "06. volume")
    val volume: String,

    @SerializedName(value = "07. latest trading day")
    val latestTradingDay: String,

    @SerializedName(value = "08. previous close")
    val previousClose: String,

    @SerializedName(value = "09. change")
    val change: String,

    @SerializedName(value = "10. change percent")
    val changePercent: String
)
