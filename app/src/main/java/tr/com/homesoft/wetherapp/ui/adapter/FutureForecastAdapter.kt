package tr.com.homesoft.wetherapp.ui.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import tr.com.homesoft.wetherapp.BR
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.util.extensions.bind

class FutureForecastAdapter(private val itemClick: (Any) -> Unit) :
    RecyclerView.Adapter<FutureForecastAdapter.ViewHolder>() {

    var isMetric = false

    var forecastDataList: List<UnitSpecificWeeklyForecastEntry> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = parent.bind<ViewDataBinding>(R.layout.future_list_item)

        return ViewHolder(binding, itemClick, isMetric)
    }

    override fun getItemCount() = forecastDataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastDataList[position])
    }

    class ViewHolder(
        private val binding: ViewDataBinding,
        private val itemClick: (Any) -> Unit,
        private val isMetric: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(unitSpecificWeeklyForecastEntry: Any) {

            itemView.setOnClickListener { itemClick(unitSpecificWeeklyForecastEntry) }
            with(binding) {
                setVariable(BR.obj, unitSpecificWeeklyForecastEntry)
                setVariable(BR.isMetric, isMetric)
                executePendingBindings()
            }

        }
    }
}