package ua.nure.verenych.sportmanager.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.nure.verenych.sportmanager.data.model.LoginRequest
import ua.nure.verenych.sportmanager.data.network.RetrofitClient
import ua.nure.verenych.sportmanager.data.prefs.SessionManager
import ua.nure.verenych.sportmanager.databinding.ActivityLoginBinding
import ua.nure.verenych.sportmanager.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        // Auto-login if token exists
        if (session.isLoggedIn()) {
            navigateToMain()
            return
        }

        binding.btnLogin.setOnClickListener { performLogin() }
    }

    private fun performLogin() {
        val email    = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введіть email та пароль", Toast.LENGTH_SHORT).show()
            return
        }

        setLoading(true)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val body = response.body()!!
                    session.saveSession(
                        token     = body.token,
                        userId    = body.user.id,
                        role      = body.user.role,
                        firstName = body.user.firstName,
                        lastName  = body.user.lastName,
                        teamId    = body.user.teamId
                    )
                    navigateToMain()
                } else {
                    Toast.makeText(this@LoginActivity, "Невірний email або пароль", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Помилка з'єднання з сервером", Toast.LENGTH_SHORT).show()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnLogin.isEnabled = !loading
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
