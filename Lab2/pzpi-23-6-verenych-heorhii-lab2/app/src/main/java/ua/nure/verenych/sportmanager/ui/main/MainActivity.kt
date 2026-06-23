package ua.nure.verenych.sportmanager.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ua.nure.verenych.sportmanager.R
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.ActivityMainBinding
import ua.nure.verenych.sportmanager.ui.login.LoginActivity
import ua.nure.verenych.sportmanager.ui.main.dashboard.DashboardFragment
import ua.nure.verenych.sportmanager.ui.main.events.EventsFragment
import ua.nure.verenych.sportmanager.ui.main.medical.MedicalFragment
import ua.nure.verenych.sportmanager.ui.main.profile.ProfileFragment
import ua.nure.verenych.sportmanager.ui.main.stats.StatsFragment
import ua.nure.verenych.sportmanager.ui.main.team.TeamFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_dashboard -> DashboardFragment()
                R.id.nav_events    -> EventsFragment()
                R.id.nav_stats     -> StatsFragment()
                R.id.nav_medical   -> MedicalFragment()
                R.id.nav_team      -> TeamFragment()
                R.id.nav_profile   -> ProfileFragment()
                else               -> DashboardFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    fun logout() {
        session.clearSession()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
