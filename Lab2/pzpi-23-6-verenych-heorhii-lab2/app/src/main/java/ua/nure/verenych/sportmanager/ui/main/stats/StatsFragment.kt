package ua.nure.verenych.sportmanager.ui.main.stats

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ua.nure.verenych.sportmanager.data.network.RetrofitClient
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val adapter = StatAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvStats.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStats.adapter = adapter

        val session = SessionManager(requireContext())
        loadStats(session)
        binding.swipeRefresh.setOnRefreshListener { loadStats(session) }
    }

    private fun loadStats(session: SessionManager) {
        lifecycleScope.launch {
            binding.swipeRefresh.isRefreshing = true
            try {
                val resp = RetrofitClient.api.getMyStats(session.getBearerToken())
                if (resp.isSuccessful) adapter.submitList(resp.body() ?: emptyList())
                else Toast.makeText(requireContext(), "Помилка завантаження", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Помилка з'єднання", Toast.LENGTH_SHORT).show()
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
