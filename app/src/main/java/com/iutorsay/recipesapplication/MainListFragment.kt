package com.iutorsay.recipesapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.adapters.HomePageAdapter
import com.iutorsay.recipesapplication.utilities.replaceFragment
import com.iutorsay.recipesapplication.viewmodels.MainListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.main_list_fragment.*


class MainListFragment : Fragment() {

    companion object {
        fun newInstance() = MainListFragment()
    }

    private lateinit var viewModel: MainListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).toolbar.toolbar_title.text = resources.getString(R.string.app_name)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        viewModel = ViewModelProviders.of(this).get(MainListViewModel::class.java)

        viewModel.recipes.observe(this, Observer { recipes ->
            recipes?.let {
                main_recipe_list.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter  = HomePageAdapter(activity!!, recipes)
                }
            }
        })

        button.setOnClickListener {
            replaceFragment(context as AppCompatActivity, R.id.content, RecipeCreationFragment())
        }
    }

}
