package com.example.pruebat.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pruebat.R
import com.example.pruebat.databinding.ActivityLoginBinding
import com.example.pruebat.ui.principal.PrincipalActivity
import com.example.pruebat.ui.register.RegisterActivity
import com.example.pruebat.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {
    //lateinit vars
    private lateinit var binding:ActivityLoginBinding

    //ViewModel Provider
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
    }

    private fun setup() {
        loggedControl()
        initObserver()
        initListeners()
    }

    private fun loggedControl() {
        if (loginViewModel.getSharedPref(applicationContext)){
            startActivity(Intent(this,PrincipalActivity::class.java))
            finish()
        }
    }

    // initialization of observers
    private fun initObserver() {

        //observer from login status change
        loginViewModel.loginState.observe(this){ state->
            when(state){
                is Resource.Success -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PrincipalActivity::class.java))
                    loginViewModel.setSharedPreferences(state.data,applicationContext)
                    finish()
                }
                is Resource.Error -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    handleLoading(isLoading = true)
                }
                else -> Unit
            }
        }

        //observer to nav to register activity
        loginViewModel.navigateRegister.observe(this){ nav ->
            if (nav){
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                loginViewModel.navToRegisterDone()
            }
        }
    }

    //initialize Listener
    private fun initListeners() {
        with(binding){

            //login
            loginButton.setOnClickListener {
                handleLogin()
            }

            //button to nav register activity
            registerUser.setOnClickListener {
                loginViewModel.goToRegister()
            }
        }
    }

    //handle to login function
    private fun handleLogin() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        loginViewModel.login(email, password)
    }

    //handle of loading view
    private fun handleLoading(isLoading: Boolean) {
        with(binding){
            if (isLoading){
                loginButton.text = ""
                loginButton.isEnabled = false
                pbLogin.visibility = View.VISIBLE
            }else{
                pbLogin.visibility = View.GONE
                loginButton.isEnabled = true
                loginButton.text = getString(R.string.iniciar_sesion)
            }
        }
    }
}