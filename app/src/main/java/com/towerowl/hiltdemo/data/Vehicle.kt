package com.towerowl.hiltdemo.data

import java.math.BigDecimal

data class Vehicle(
    val name: String,
    val range: Float,
    val acceleration: Float,
    val price: BigDecimal,
)