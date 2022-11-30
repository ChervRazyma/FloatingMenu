package com.example.floatingmenu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.floatingmenu.R
import com.example.floatingmenu.models.MenuItem


class MenuItemAdapter(
): ListAdapter<MenuItem, MenuItemAdapter.ViewHolder>(object : DiffUtil.ItemCallback<MenuItem>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
        oldItem.id == newItem.id

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuItemAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position) as MenuItem)
    }

    override fun getItemViewType(position: Int): Int {
        if (position < 0)
            return 0
        return getItem(position).id
    }

    inner class ViewHolder(
        private val view: View,
    ): RecyclerView.ViewHolder(view) {

        fun bind(item: MenuItem) {

        }
    }
}