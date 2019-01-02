package tr.com.homesoft.wetherapp.ui.state

sealed class UIState {

    object Loading : UIState()

    object HasData : UIState()

    object NoData : UIState()

    object Error : UIState()
}