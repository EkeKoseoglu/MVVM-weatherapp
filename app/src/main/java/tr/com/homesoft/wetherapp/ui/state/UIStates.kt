package tr.com.homesoft.wetherapp.ui.state

import androidx.annotation.StringRes

sealed class UIState<T>

class  Loading<T>: UIState<T>()

class  HasData<T>(val data: T): UIState<T>()

class  NoData<T>: UIState<T>()

class  Error<T>(@StringRes val erroMessageId: Int): UIState<T>()