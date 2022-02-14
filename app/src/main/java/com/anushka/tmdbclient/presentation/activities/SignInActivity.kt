package com.anushka.tmdbclient.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.anushka.tmdbclient.R
import com.anushka.tmdbclient.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding


    // instantiating Email and Password liveData objects
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // making the activity fullscreen
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        isValidLiveData.apply {
            this.value = false
            addSource(emailLiveData) { email ->
                val password = passwordLiveData.value
                this.value = validateForm(email, password)
            }
            addSource(passwordLiveData) { password ->
                val email = emailLiveData.value
                this.value = validateForm(email, password)
            }
        }

        // the email entered is converted into a string and then stored as the value
        // of emailLiveData
        binding.enterEmail.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text.toString()
        }
        // the password entered is converted into a string and then stored as the value
        // of passwordLiveData
        binding.enterPassword.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text.toString()
        }

        // using a lifecycle observer to observe changes and make the Log In
        // button visible if the values entered by the user if right
        isValidLiveData.observe(this) { isValid ->
            binding.logInButton.isEnabled = isValid

        }
        binding.logInButton.setOnClickListener {
            val intent = startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }

        // using LiveData To Validate the forms
        binding.createAccountText.setOnClickListener {
            val intent = startActivity(Intent(this, RegisterActivity::class.java))
            finish()

        }
    }

    // this fun stores the entered email and password
    private fun validateForm(email: String?, password: String?): Boolean {

        val isValidEmail = email != null
                && email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val isValidPassword = password != null
                && password.isNotBlank()
                && password.length >= 6

        return isValidEmail && isValidPassword


    }
}