package ua.nure.verenych.sportmanager.ui.main.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.verenych.sportmanager.data.model.PlayerStat
import ua.nure.verenych.sportmanager.databinding.ItemStatBinding

class StatAdapter : ListAdapter<PlayerStat, StatAdapter.StatViewHolder>(DiffCallback) {

    inner class StatViewHolder(private val b: ItemStatBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(stat: PlayerStat) {
            b.tvEventTitle.text = stat.eventTitle
            b.tvDate.text       = stat.eventDate.take(10)
            b.tvGoals.text      = "Голи: ${stat.goals ?: 0}"
            b.tvRating.text     = "Рейтинг: ${stat.rating ?: "—"}"
            b.tvComment.text    = stat.coachComment ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val b = ItemStatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatViewHolder(b)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<PlayerStat>() {
        override fun areItemsTheSame(a: PlayerStat, b: PlayerStat) = a.id == b.id
        override fun areContentsTheSame(a: PlayerStat, b: PlayerStat) = a == b
    }
}
