package ua.nure.verenych.sportmanager.ui.main.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.nure.verenych.sportmanager.data.network.RetrofitClient
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.FragmentProfileBinding
import ua.nure.verenych.sportmanager.ui.main.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = SessionManager(requireContext())
        loadProfile(session)
        binding.btnLogout.setOnClickListener { (requireActivity() as MainActivity).logout() }
    }

    private fun loadProfile(session: SessionManager) {
        lifecycleScope.launch {
            try {
                val resp = RetrofitClient.api.getProfile(session.getBearerToken())
                if (resp.isSuccessful) {
                    val user = resp.body()!!.user
                    with(binding) {
                        tvFullName.text  = "${user.firstName} ${user.lastName}"
                        tvEmail.text     = user.email
                        tvRole.text      = when (user.role) {
                            "coach"  -> "Тренер"
                            "player" -> "Гравець"
                            "admin"  -> "Адміністратор"
                            else     -> user.role
                        }
                        tvTeam.text      = user.teamName ?: "Не призначено"
                        tvCategory.text  = user.teamCategory ?: "—"
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Помилка завантаження профілю", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
