package tr.com.homesoft.weatherapp.ui.view.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.EditTextPreference

class SummarizedEditTextPreference constructor(
    context: Context, attrs: AttributeSet
) : EditTextPreference(context, attrs) {

    override fun setText(text: String) {
        super.setText(text.capitalize())
        notifyChanged()
    }

    override fun getSummary(): CharSequence = if (super.getText() == null) "" else text.capitalize()

}