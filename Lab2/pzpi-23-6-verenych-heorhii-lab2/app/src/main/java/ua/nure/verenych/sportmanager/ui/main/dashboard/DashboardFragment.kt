package ua.nure.verenych.sportmanager.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.nure.verenych.sportmanager.data.network.RetrofitClient
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.FragmentDashboardBinding
import ua.nure.verenych.sportmanager.ui.main.MainActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = SessionManager(requireContext())
        binding.tvWelcome.text = "Вітаємо, ${session.getFirstName()} ${session.getLastName()}!"
        loadProfile(session)
    }

    private fun loadProfile(session: SessionManager) {
        lifecycleScope.launch {
            try {
                val resp = RetrofitClient.api.getProfile(session.getBearerToken())
                if (resp.isSuccessful) {
                    val data = resp.body()!!
                    with(binding) {
                        tvTeam.text    = "Команда: ${data.user.teamName ?: "Не призначено"}"
                        tvGoals.text   = "Голи: ${data.stats.totalGoals}"
                        tvGames.text   = "Ігор зіграно: ${data.stats.gamesPlayed}"
                        tvRating.text  = "Середній рейтинг: ${data.stats.avgRating ?: "—"}"
                        tvPresent.text = "Відвідуваність: ${data.attendance.present}/${data.attendance.total}"
                        val activeInjuries = data.injuries.count { it.status == "active" }
                        tvInjuries.text = "Активних травм: $activeInjuries"
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Помилка завантаження", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
