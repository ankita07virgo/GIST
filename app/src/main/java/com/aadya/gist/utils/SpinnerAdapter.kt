package com.aadya.gist.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aadya.aadyanews.R
import com.aadya.gist.navigation.model.NavigationMenu

class SpinnerAdapter(val context: Context, var dataSource: List<NavigationMenu>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var mSessionManager: SessionManager
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource[position].category_name
        vh.label.setTextColor(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.text) as TextView

    }
init {
    mSessionManager = SessionManager.getInstance(context)!!
}

}