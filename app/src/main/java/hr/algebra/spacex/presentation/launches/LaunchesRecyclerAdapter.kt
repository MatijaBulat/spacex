package hr.algebra.spacex.presentation.launches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import hr.algebra.spacex.R
import hr.algebra.spacex.domain.model.Launch

class LaunchesRecyclerAdapter(
    private var launches: List<Launch>,
    private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<LaunchesRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val ivListItemImg = itemView.findViewById<ImageView>(R.id.ivListItemImg)
        private val tvListItemTitle = itemView.findViewById<TextView>(R.id.tvListItemTitle)
        private val tvListItemDate = itemView.findViewById<TextView>(R.id.tvListItemDate)

        init {
            view.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }


        fun bind(launch: Launch) {
            ivListItemImg.load(launch.launchImagePath) {

                error(R.drawable.elon_musk)
                placeholder(R.drawable.elon_musk)
            }
            tvListItemTitle.text = launch.name
            tvListItemDate.text = launch.launchDate
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