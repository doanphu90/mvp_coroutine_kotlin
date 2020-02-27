package com.kotlin.spweartherapp.mvp.model

data class CurrentCondition(
    val observationTime: String,
    val tempC: String,
    val tempF: String,
    val weatherCode: String,
    val weatherIconUrl: String,
    val weatherDesc: String,
    val windspeedKmph: String,
    val windspeedMiles: String,
    val winddirDegree: String,
    val winddir16Point: String,
    val precipMM: String,
    val precipInches: String,
    val humidity: String
)

data class WeatherIconUrl(
    val value: String
)

data class WeatherDesc(
    val value: String
)