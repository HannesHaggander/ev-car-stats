package com.towerowl.hiltdemo.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.towerowl.hiltdemo.R
import com.towerowl.hiltdemo.viewmodels.VehicleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vehicleViewModel.popularVehicles.observe(viewLifecycleOwner) { popularVehicles ->
            Log.d(TAG, "onViewCreated: ${popularVehicles.joinToString("\n")}")
        }
        vehicleViewModel.fetchPopularVehicles()
    }

    companion object {
        private const val TAG = "OverviewFragment"
    }

}