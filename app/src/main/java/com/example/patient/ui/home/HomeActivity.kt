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
    private lateinit var patientAdapter: PatientAdapter
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

    fun setupList(data: List<PatientItem>) {
        patientAdapter = PatientAdapter(this@HomeActivity) { id ->
            progressDialog.show()
            homeViewmodel.deletePatient(id)
        }
        patientAdapter.data = data
        lstExpandable.setAdapter(patientAdapter)
        patientAdapter.notifyDataSetChanged()
    }

    fun setupObservable() {
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
            if (it == "failed")
                progressDialog.show()
        })
    }

    fun setEvent() {
        btnAddPatient.setOnClickListener {
            progressDialog.show()
            homeViewmodel.addPatient()
        }
        btnSearch.setOnClickListener {
            progressDialog.show()
            if (!lblId.text.toString().isEmpty()) {
                homeViewmodel.searchPatient(lblId.text.toString())
            } else {
                homeViewmodel.getAllPatient()
            }
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}