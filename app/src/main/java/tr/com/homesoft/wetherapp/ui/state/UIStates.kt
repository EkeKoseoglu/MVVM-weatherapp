package tr.com.homesoft.wetherapp.ui.state

import androidx.annotation.StringRes

sealed class UIState

object Loading: UIState()

object HasData: UIState()

object NoData: UIState()

class Error(@StringRes val erroMessageId: Int): UIState()