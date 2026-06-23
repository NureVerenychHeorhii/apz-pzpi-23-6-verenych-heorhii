package ua.nure.verenych.sportmanager.ui.main.medical

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.verenych.sportmanager.data.model.Injury
import ua.nure.verenych.sportmanager.databinding.ItemInjuryBinding

class InjuryAdapter : ListAdapter<Injury, InjuryAdapter.InjuryViewHolder>(DiffCallback) {

    inner class InjuryViewHolder(private val b: ItemInjuryBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(injury: Injury) {
            b.tvType.text     = injury.injuryType
            b.tvDate.text     = "Дата: ${injury.incidentDate.take(10)}"
            b.tvRecovery.text = "Відновлення: ${injury.recoveryDate?.take(10) ?: "—"}"
            b.tvStatus.text   = injury.status
            b.tvStatus.setTextColor(if (injury.status == "active") Color.RED else Color.parseColor("#4CAF50"))
            b.tvNotes.text    = injury.notes ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InjuryViewHolder {
        val b = ItemInjuryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InjuryViewHolder(b)
    }

    override fun onBindViewHolder(holder: InjuryViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Injury>() {
        override fun areItemsTheSame(a: Injury, b: Injury) = a.id == b.id
        override fun areContentsTheSame(a: Injury, b: Injury) = a == b
    }
}
