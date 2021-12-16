package com.example.patient.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.patient.R
import com.example.patient.base.BaseActivity
import com.example.patient.data.User
import com.example.patient.ui.dialog.ProgressDialog
import com.example.patient.ui.home.HomeActivity
import com.example.patient.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnLogin
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.lblPassword
import kotlinx.android.synthetic.main.activity_login.lblUsername
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val loginViewmodel: LoginViewmodel by viewModel()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialog = ProgressDialog(this@LoginActivity)
        setEvent()
        setupObservable()
    }

    private fun setEvent() {
        btnLogin.setOnClickListener {
            progressDialog.show()
            loginViewmodel.login(
                lblUsername.text.toString(),
                lblPassword.text.toString()
            )
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
    private fun setupObservable() {
        loginViewmodel.responseLogin.observe(this@LoginActivity, { success ->
            progressDialog.dismiss()
            if (success) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
        loginViewmodel.responseFailedLogin.observe(this@LoginActivity, {
            progressDialog.dismiss()
            Toast.makeText(this@LoginActivity, it , Toast.LENGTH_SHORT).show()
        })
    }
}