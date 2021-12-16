package com.example.patient.ui.adapter

import android.annotation.SuppressLint
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

    var data: List<PatientItem> = listOf()

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return data[groupPosition]
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

    @SuppressLint("InflateParams")
    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view: View? = convertView
        if (view == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.group_item, null)
        }

        val lblTitle = view?.findViewById<AppCompatTextView>(R.id.lblTitle)
        val imgExpand = view?.findViewById<AppCompatImageView>(R.id.imgExpand)
        val imgCollapse = view?.findViewById<AppCompatImageView>(R.id.imgCollpase)

        if (isExpanded) {
            imgExpand?.visibility = View.VISIBLE
            imgCollapse?.visibility = View.GONE
        } else {
            imgExpand?.visibility = View.GONE
            imgCollapse?.visibility = View.VISIBLE
        }

        lblTitle?.text = data?.get(groupPosition)?.name
        return view!!
    }

    @SuppressLint("InflateParams")
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.child_item, null)
        }
        val lblTime = view?.findViewById<AppCompatTextView>(R.id.lblTime)
        val lblAddress = view?.findViewById<AppCompatTextView>(R.id.lblAddress)
        val lblPhoneNumber = view?.findViewById<AppCompatTextView>(R.id.lblPhoneNumber)
        val imgAvatar = view?.findViewById<AppCompatImageView>(R.id.imgAvatar)

        lblTime?.text = Utils.convertTime(data?.get(groupPosition)?.createdAt ?: "")
        lblPhoneNumber?.text = data?.get(groupPosition)?.contact
        lblAddress?.text = data?.get(groupPosition)?.address_2

        val btnDelete = view?.findViewById<AppCompatImageView>(R.id.btnDelete)
        btnDelete?.setOnClickListener {
            onDelete(data?.get(groupPosition)?.id.toString())
        }

        imgAvatar?.let { image ->
            Glide.with(context)
                .load(Uri.parse(data?.get(groupPosition)?.avatar))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(RequestOptions().skipMemoryCache(true))
                .error(R.drawable.ic_baseline_hourglass_empty_24)
                .into(image)
        }

        return view!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}