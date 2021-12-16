package com.example.patient.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.patient.R
import com.example.patient.base.BaseActivity
import com.example.patient.data.PatientItem
import com.example.patient.ui.adapter.PatientAdapter
import com.example.patient.ui.dialog.ProgressDialog
import com.example.patient.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private val homeViewmodel: HomeViewmodel by viewModel()
    private var patientAdapter: PatientAdapter? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this@HomeActivity)
        setEvent()
        setupObservable()

        if (homeViewmodel.getUser() == null) {
            btnAddPatient.visibility = View.GONE
        } else {
            btnAddPatient.visibility = View.VISIBLE
        }

        progressDialog.show()
        homeViewmodel.getAllPatient()
    }

    private fun setupList(data: List<PatientItem>) {
        patientAdapter = PatientAdapter(this@HomeActivity) { id ->
            progressDialog.show()
            patientAdapter = null
            homeViewmodel.deletePatient(id)
        }
        patientAdapter?.data = data
        lstExpandable.setAdapter(patientAdapter)
        patientAdapter?.notifyDataSetChanged()
    }

    private fun setupObservable() {
        homeViewmodel.responsePatients.observe(this@HomeActivity, {
            progressDialog.dismiss()
            setupList(it)
        })
        homeViewmodel.responsePatient.observe(this@HomeActivity, {
            progressDialog.dismiss()
            val list: ArrayList<PatientItem> = arrayListOf()
            list.add(it)
            setupList(list.toList())
        })
        homeViewmodel.responseAddPatient.observe(this@HomeActivity, {
            progressDialog.dismiss()
            if (it) {
                progressDialog.show()
                homeViewmodel.getAllPatient()
                Toast.makeText(this@HomeActivity, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@HomeActivity, "Gagal Ditambahkan", Toast.LENGTH_SHORT).show()
            }
        })
        homeViewmodel.responseDeletePatient.observe(this@HomeActivity, {
            progressDialog.dismiss()
            if (it) {
                progressDialog.show()
                homeViewmodel.getAllPatient()
                Toast.makeText(this@HomeActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@HomeActivity, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show()
            }
        })
        homeViewmodel.responseFailed.observe(this@HomeActivity, {
            Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        })
    }

    private fun setEvent() {
        btnAddPatient.setOnClickListener {
            progressDialog.show()
            homeViewmodel.addPatient()
        }
        btnSearch.setOnClickListener {
            progressDialog.show()
            if (lblId.text.toString().isNotEmpty()) {
                homeViewmodel.searchPatient(lblId.text.toString())
            } else {
                homeViewmodel.getAllPatient()
            }
        }
        btnLogin.setOnClickListener {
            homeViewmodel.deleteUser()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}