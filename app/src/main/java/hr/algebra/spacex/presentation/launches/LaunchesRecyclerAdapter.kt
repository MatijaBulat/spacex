package hr.algebra.spacex.presentation.launches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.spacex.R
import hr.algebra.spacex.domain.model.Launch

class LaunchesRecyclerAdapter(
    private var launches: List<Launch>,
    private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<LaunchesRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }

        //

        fun bind(launch: Launch) {


        }
    }

    fun filterList(filterList: List<Launch>) {
        launches = filterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.launches_list_item, parent, false)) {
            clickListener(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(launches[position])
    }

    override fun getItemCount(): Int {
        return launches.size
    }

}