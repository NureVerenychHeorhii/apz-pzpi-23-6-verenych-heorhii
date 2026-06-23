package ua.nure.verenych.sportmanager.ui.main.events

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ua.nure.verenych.sportmanager.data.network.RetrofitClient
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private val adapter = EventAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter

        val session = SessionManager(requireContext())
        loadEvents(session)

        binding.swipeRefresh.setOnRefreshListener { loadEvents(session) }
    }

    private fun loadEvents(session: SessionManager) {
        lifecycleScope.launch {
            binding.swipeRefresh.isRefreshing = true
            try {
                val resp = RetrofitClient.api.getMyEvents(session.getBearerToken())
                if (resp.isSuccessful) {
                    adapter.submitList(resp.body() ?: emptyList())
                } else {
                    Toast.makeText(requireContext(), "Помилка завантаження подій", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Помилка з'єднання", Toast.LENGTH_SHORT).show()
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
