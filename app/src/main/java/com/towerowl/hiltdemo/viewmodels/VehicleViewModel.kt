package com.towerowl.hiltdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerowl.hiltdemo.data.Vehicle
import com.towerowl.hiltdemo.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _popularVehicles: MutableLiveData<List<Vehicle>> = MutableLiveData()
    val popularVehicles: LiveData<List<Vehicle>> get() = _popularVehicles
    private var fetchPopularVehiclesJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    fun fetchPopularVehicles() {
        viewModelScope.launch(IO) {
            vehicleRepository
                .getPopularVehicles()
                .let { popularVehicles -> _popularVehicles.postValue(popularVehicles) }
        }.also { fetchPopularVehiclesJob = it }
    }

}