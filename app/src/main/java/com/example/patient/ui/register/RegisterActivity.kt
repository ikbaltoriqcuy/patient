package com.example.patient.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.patient.R
import com.example.patient.base.BaseActivity
import com.example.patient.data.User
import com.example.patient.ui.dialog.ProgressDialog
import com.example.patient.ui.home.HomeActivity
import com.example.patient.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {
    private val registerViewmodel: RegisterViewmodel by viewModel()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        progressDialog = ProgressDialog(this@RegisterActivity)
        setEvent()
        setupObservable()
    }

    private fun setEvent() {
        btnRegister.setOnClickListener {
            progressDialog.show()
            registerViewmodel.register(
                lblUsername.text.toString(),
                lblPassword.text.toString(),
                lblConfirmPassword.text.toString())
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setupObservable() {
        registerViewmodel.responseRegister.observe(this@RegisterActivity, {
            progressDialog.dismiss()
            if (it) {
                registerViewmodel.insertUser(
                    User(
                        lblUsername.text.toString(),
                        lblPassword.text.toString()
                    )
                )
                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
        registerViewmodel.responseFailedRegister.observe(this@RegisterActivity, {
            progressDialog.dismiss()
            Toast.makeText(this@RegisterActivity, it ,Toast.LENGTH_SHORT).show()
        })
    }
}