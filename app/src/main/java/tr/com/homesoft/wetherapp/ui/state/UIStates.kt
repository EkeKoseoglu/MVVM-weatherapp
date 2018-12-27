package tr.com.homesoft.wetherapp.ui.state

import androidx.annotation.StringRes

sealed class UIState<out T: Any>

object  Loading: UIState<Nothing>()

class  HasData<out T: Any>(val data: T): UIState<T>()

object  NoData: UIState<Nothing>()

class  Error<out T: Any>(@StringRes val erroMessageId: Int): UIState<T>()