package hr.algebra.spacex.presentation.launches


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import hr.algebra.spacex.R
import hr.algebra.spacex.domain.model.Launch

class LaunchesViewPagerAdapter(private val items: List<Launch>)
    : RecyclerView.Adapter<LaunchesViewPagerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)


        fun bind(launch: Launch) {
            ivItem.load(launch.launchImagePath) {
                error(R.drawable.elon_musk)
                placeholder(R.drawable.elon_musk)
            }
            tvTitle.text = launch.name
            tvDate.text = launch.launchDate
            tvDescription.text = launch.details ?: "No description available!"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}