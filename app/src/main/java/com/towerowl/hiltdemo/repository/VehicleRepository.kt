package com.towerowl.hiltdemo.repository

import com.towerowl.hiltdemo.data.Vehicle
import java.math.BigDecimal
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface VehicleRepository {
    suspend fun getPopularVehicles(): List<Vehicle>
}

class VehicleRepositoryImpl : VehicleRepository {

    override suspend fun getPopularVehicles(): List<Vehicle> = suspendCoroutine { s ->
        s.resume(MOCK_VEHICLES)
    }

    companion object {
        private val MOCK_VEHICLES: List<Vehicle> = listOf(
            Vehicle(
                name = "Tesla Model S Plaid",
                range = 628f,
                acceleration = 2.1f,
                BigDecimal(1379990)
            ),
            Vehicle(
                name = "Porsche Taycan 4S",
                range = 320259f,
                acceleration = 2.8f,
                BigDecimal(1170000)
            ),
            Vehicle(
                name = "Tesla Model 3 Performance",
                range = 567f,
                acceleration = 3.3f,
                BigDecimal(699080)
            ),
        )
    }
}