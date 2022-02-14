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
import com.anushka.tmdbclient.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val userNameLiveData = MutableLiveData<String>()
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val confirmPasswordLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // making the activity fullscreen
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.backArrow.setOnClickListener {
            val intent = startActivity(Intent(this, SignInActivity::class.java))
        }

        isValidLiveData.apply {
            this.value = false

            addSource(userNameLiveData){
                userName->
                val password = passwordLiveData.value
                val email = emailLiveData.value
                this.value = validateForm(userName,email, password)
            }
            addSource(emailLiveData) { email ->
                val password = passwordLiveData.value
                val userName = userNameLiveData.value
                this.value = validateForm(userName,email, password)
            }
            addSource(passwordLiveData) { password ->
                val email = emailLiveData.value
                val userName = userNameLiveData.value
                this.value = validateForm(userName,email, password)
            }

        }

        // the email entered is converted into a string and then stored as the value
        // of emailLiveData
        binding.userNameEDT.doOnTextChanged { text, _, _, _ ->
            userNameLiveData.value = text.toString()
        }
        binding.emailEDT.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text.toString()
        }
        // the password entered is converted into a string and then stored as the value
        // of passwordLiveData
        binding.passwordEDT.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text.toString()
        }

        // using a lifecycle observer to observe changes and make the Log In
        // button visible if the values entered by the user if right
        isValidLiveData.observe(this) { isValid ->
            binding.createAccountButton.isEnabled = isValid

        }

        // using LiveData To Validate the forms
        binding.createAccountButton.setOnClickListener {
            val intent = startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()

        }
    }

    // this fun stores the entered email and password
    private fun validateForm(userName :String?, email: String?, password: String?): Boolean {

        val isValidUserName = userName !=null
                && userName.isNotBlank()
                && userName.length >= 6


        val isValidEmail = email != null
                && email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val isValidPassword = password != null
                && password.isNotBlank()
                && password.length >= 6


        return isValidUserName && isValidEmail && isValidPassword

    }
}