package ua.nure.verenych.sportmanager.ui.main.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.nure.verenych.sportmanager.data.model.TeamMember
import ua.nure.verenych.sportmanager.databinding.ItemTeamMemberBinding

class TeamAdapter : ListAdapter<TeamMember, TeamAdapter.MemberViewHolder>(DiffCallback) {

    inner class MemberViewHolder(private val b: ItemTeamMemberBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(member: TeamMember) {
            b.tvName.text  = "${member.firstName} ${member.lastName}"
            b.tvRole.text  = when (member.role) {
                "coach"  -> "Тренер"
                "player" -> "Гравець"
                "admin"  -> "Адміністратор"
                else     -> member.role
            }
            b.tvEmail.text = member.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val b = ItemTeamMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(b)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<TeamMember>() {
        override fun areItemsTheSame(a: TeamMember, b: TeamMember) = a.id == b.id
        override fun areContentsTheSame(a: TeamMember, b: TeamMember) = a == b
    }
}
