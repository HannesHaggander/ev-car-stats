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
    private val _filteredPopularVehicles: MutableLiveData<List<Vehicle>> = MutableLiveData()
    val filteredPopularVehicles: LiveData<List<Vehicle>> get() = _filteredPopularVehicles
    private val _searchQuery: MutableLiveData<String> = MutableLiveData()
    val searchQuery: LiveData<String> get() = _searchQuery

    private var fetchPopularVehiclesJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    init {
        fetchPopularVehicles()
        _popularVehicles.observeForever { popularVehicles ->
            _filteredPopularVehicles.postValue(
                searchQuery.value.let { query ->
                    if (query.isNullOrEmpty()) popularVehicles
                    else popularVehicles.filter { it.name.contains(query, ignoreCase = true) }
                }
            )
        }

        _searchQuery.observeForever { query ->
            _filteredPopularVehicles.postValue(
                if (query.isNullOrEmpty()) _popularVehicles.value
                else _popularVehicles.value.orEmpty()
                    .filter { it.name.contains(query, ignoreCase = true) }
            )
        }
    }

    private fun fetchPopularVehicles() {
        viewModelScope.launch(IO) {
            vehicleRepository
                .getPopularVehicles()
                .let { popularVehicles -> _popularVehicles.postValue(popularVehicles) }
        }.also { fetchPopularVehiclesJob = it }
    }

    fun updateSearchQuery(query: String) {
        if (_searchQuery.value == query) return
        _searchQuery.postValue(query)
    }

}