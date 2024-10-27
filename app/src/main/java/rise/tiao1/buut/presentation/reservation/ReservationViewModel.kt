package rise.tiao1.buut.presentation.reservation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor ():ViewModel() {

    private val _state = mutableStateOf(ReservationScreenState())
        val state: State<ReservationScreenState>
        get() = _state

    @OptIn(ExperimentalMaterial3Api::class)
    fun update(input: Long?){
        _state.value = state.value.copy(selectedDate = input)
    }

}