package ua.nure.verenych.sportmanager.ui.main.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.verenych.sportmanager.data.model.Event
import ua.nure.verenych.sportmanager.databinding.ItemEventBinding

class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallback) {

    inner class EventViewHolder(private val b: ItemEventBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(event: Event) {
            b.tvTitle.text    = event.title
            b.tvDate.text     = event.eventDate.take(10)
            b.tvLocation.text = event.location ?: "—"
            b.tvTeam.text     = event.teamName ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val b = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(b)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(a: Event, b: Event) = a.id == b.id
        override fun areContentsTheSame(a: Event, b: Event) = a == b
    }
}
