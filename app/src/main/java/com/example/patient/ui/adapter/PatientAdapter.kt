package com.example.patient.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.patient.R
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.patient.data.PatientItem
import com.example.patient.util.Utils

class PatientAdapter(val context: Context, val onDelete:(String)->Unit) : BaseExpandableListAdapter() {

    var data: List<PatientItem>? = null

    override fun getGroupCount(): Int {
        return data?.size ?: 0
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return data!!.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return 0
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.group_item, null)
        }
        val lblTitle = convertView?.findViewById<AppCompatTextView>(R.id.lblTitle)
        val btnDelete = convertView?.findViewById<AppCompatImageView>(R.id.btnDelete)

        lblTitle?.text = data?.get(groupPosition)?.name
        btnDelete?.setOnClickListener {
            onDelete(data?.get(groupPosition)?.id.toString())
        }
        return convertView!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.child_item, null)
        }
        val lblTime = convertView?.findViewById<AppCompatTextView>(R.id.lblTime)
        val lblAddress = convertView?.findViewById<AppCompatTextView>(R.id.lblAddress)
        val lblPhoneNumber = convertView?.findViewById<AppCompatTextView>(R.id.lblPhoneNumber)
        val imgAvatar = convertView?.findViewById<AppCompatImageView>(R.id.imgAvatar)

        lblTime?.text = Utils.convertTime(data?.get(groupPosition)?.createdAt ?: "")
        lblPhoneNumber?.text = data?.get(groupPosition)?.contact
        lblAddress?.text = data?.get(groupPosition)?.address_2

        Glide.with(context)
            .load(Uri.parse(data?.get(groupPosition)?.avatar))
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
            .apply(RequestOptions().skipMemoryCache(true))
            .error(R.drawable.ic_baseline_hourglass_empty_24)
            .into(imgAvatar!!)

        return convertView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}