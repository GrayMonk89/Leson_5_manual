package com.gb.lesson5manual

data class WeatherDTO(val fact: FactDTO?)


data class FactDTO(val temp: Int?, val feel_lick: Int?, val condition: String?)