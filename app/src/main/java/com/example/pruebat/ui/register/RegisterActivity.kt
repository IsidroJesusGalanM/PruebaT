package com.example.pruebat.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pruebat.R
import com.example.pruebat.databinding.ActivityRegisterBinding
import com.example.pruebat.ui.login.Login
import com.example.pruebat.ui.principal.PrincipalActivity
import com.example.pruebat.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        registerViewModel.singUpState.observe(this){state ->
            when (state) {
                is Resource.Success-> {
                    handleLoading(isLoading = false)
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PrincipalActivity::class.java))
                }
                is Resource.Error ->{
                    handleLoading(isLoading = false)
                    Toast.makeText(this, "Registro rechazado", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    handleLoading(isLoading = true)
                }
                else -> Unit
            }
        }

        registerViewModel.navlogin.observe(this){ nav ->
            if (nav){
                startActivity(Intent(this,Login::class.java))
                registerViewModel.navToLoginDone()
            }
        }
    }

    private fun initListeners() {
        with(binding){
            registerButton.setOnClickListener {
                handleSignUp()
            }

            loginBack.setOnClickListener {
                registerViewModel.goToLogin()
            }
        }
    }

    private fun handleSignUp() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
        registerViewModel.register(email, password)
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding){
            if (isLoading){
                registerButton.text = ""
                registerButton.isEnabled = false
                pbLoadingSign.visibility = View.VISIBLE
            }else{
                pbLoadingSign.visibility = View.GONE
                registerButton.isEnabled = true
                registerButton.text = getString(R.string.iniciar_sesion)
            }
        }
    }



}