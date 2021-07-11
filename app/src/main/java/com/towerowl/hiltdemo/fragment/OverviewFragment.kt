package com.towerowl.hiltdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.towerowl.hiltdemo.R
import com.towerowl.hiltdemo.data.Vehicle
import com.towerowl.hiltdemo.viewmodels.VehicleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireActivity()).apply {
        setContent {
            Column {
                val query = vehicleViewModel.searchQuery.observeAsState(initial = "")
                StatelessVehicleSearchField(
                    value = query.value,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .smallPadding()
                ) {
                    vehicleViewModel.updateSearchQuery(it)
                }

                val popularVehicles = vehicleViewModel
                    .filteredPopularVehicles
                    .observeAsState(initial = listOf())

                LazyColumn {
                    itemsIndexed(
                        items = popularVehicles.value
                    ) { _, vehicle ->
                        OverViewItem(vehicle = vehicle)
                    }
                }
            }
        }
    }

    @Composable
    fun OverViewItem(vehicle: Vehicle) {
        MaterialTheme {
            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    InformationText(vehicle.name)
                    InformationText(
                        getString(
                            R.string.generic_range_with_arg,
                            vehicle.range.toString(),
                            "km"
                        )
                    )
                    InformationText(
                        getString(
                            R.string.generic_price_with_arg,
                            vehicle.price.toString(),
                            "sek"
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun InformationText(text: String) {
        Text(
            text = text,
            modifier = Modifier.smallPadding()
        )
    }

    @Composable
    fun StatelessVehicleSearchField(value: String, modifier: Modifier, onChange: (String) -> Unit) {
        TextField(
            value = value,
            onValueChange = onChange,
            label = { Text(getString(R.string.generic_search)) },
            modifier = modifier,
        )
    }

    private fun Modifier.smallPadding() = padding(5.dp)
}