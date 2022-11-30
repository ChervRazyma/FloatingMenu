package com.example.floatingmenu.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.floatingmenu.R
import com.example.floatingmenu.adapters.MenuItemAdapter
import com.example.floatingmenu.models.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigationBottomBar: BottomNavigationView

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuItemAdapter: MenuItemAdapter
    private var menuItems: ArrayList<MenuItem>? = arrayListOf(
        MenuItem(1),
        MenuItem(2),
        MenuItem(3),
        MenuItem(4),
        MenuItem(5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationBottomBar = findViewById(R.id.nav_bar)
        menuRecyclerView = findViewById(R.id.menu_recyclerList)

        initMenuAdapter()
        initItemTouchHelper()
        initMenuList()

        navigationBottomBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu -> {
                    if (menuRecyclerView.isNotEmpty())
                        menuItemAdapter.submitList(arrayListOf())
                    else
                        menuItemAdapter.submitList(menuItems)
                } else -> {}
            }
            true
        }
    }

    private fun initMenuList() {
        menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = menuItemAdapter
            animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.item_anim)
            itemAnimator?.addDuration = 1000
        }
        menuItemAdapter.submitList(menuItems)
    }

    private fun initMenuAdapter() {
        menuItemAdapter = MenuItemAdapter()
        menuItemAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                Handler(Looper.getMainLooper()).postDelayed( {
                    menuRecyclerView.layoutManager?.scrollToPosition(0)
                }, 50)
            }
        })
    }

    private fun initItemTouchHelper() {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                menuItems?.removeAt(viewHolder.adapterPosition)
                menuItemAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                menuItemAdapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(menuRecyclerView)
    }
}