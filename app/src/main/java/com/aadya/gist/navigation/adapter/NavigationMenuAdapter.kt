package com.aadya.gist.navigation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterNavigationMenuBinding
import com.aadya.gist.navigation.adapter.NavigationMenuAdapter.MyViewHolder
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.utils.SessionManager
import java.util.*

class NavigationMenuAdapter(
    context: Context,
    navigationMenus: ArrayList<NavigationMenu>?,
    navigationMenuListener: NavigationMenuListener
) : RecyclerView.Adapter<MyViewHolder>() {

    private val navigationMenus: ArrayList<NavigationMenu> = ArrayList<NavigationMenu>()
    private val context: Context
    private val navigationMenuListener: NavigationMenuListener
    private val sessionManager: SessionManager


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_navigation_menu, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val navigationMenu: NavigationMenu = navigationMenus[i]
        with(viewHolder) {
           binding.menuText.text = navigationMenus[i].category_name
          //  translateTextToSelectedLanguage(navigationMenus[i].category_name,binding.menuText)
            binding.menuMainLayout.setOnClickListener {
                navigationMenuListener.onItemClick(
                    navigationMenu
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return navigationMenus.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface NavigationMenuListener {
        //  fun onItemClick(id: Int)
        fun onItemClick(navigationMenu: NavigationMenu?)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterNavigationMenuBinding.bind(itemView)
    }

    init {
        this.navigationMenus.addAll(navigationMenus!!)
        this.context = context
        this.navigationMenuListener = navigationMenuListener
        sessionManager = SessionManager()
    }




}