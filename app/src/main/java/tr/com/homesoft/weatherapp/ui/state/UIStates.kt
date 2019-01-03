package tr.com.homesoft.weatherapp.ui.state

sealed class UIState {

    object Loading : UIState()

    object HasData : UIState()

    object NoData : UIState()

    object Error : UIState()
}