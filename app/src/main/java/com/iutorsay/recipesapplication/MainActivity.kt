package com.iutorsay.recipesapplication

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.iutorsay.recipesapplication.utilities.replaceFragmentWithoutBackStack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            toolbar.toolbar_title.typeface = resources.getFont(R.font.circular_std_bold)
        }

        handleNavigationClick()

        showFragment(MainListFragment())
    }

    private fun handleNavigationClick() {
        navigation.setOnNavigationItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.home -> {
                    showFragment(MainListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun showFragment(fragment: Fragment) {
        replaceFragmentWithoutBackStack(this as AppCompatActivity, R.id.content, fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> this.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Si on est sur l'écran d'accueil, on change le titre de la toolbar
        val isOnMainFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)?.isVisible

        isOnMainFragment?.let {
            toolbar.toolbar_title.text = resources.getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }
}