package tr.com.homesoft.weatherapp.ui.base

import androidx.lifecycle.LiveData

class AbsentLiveData<T: Any?> private constructor(): LiveData<T>(){

    init {
        postValue(null)
    }
    companion object {
        operator fun <T> invoke(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}