package com.iutorsay.recipesapplication.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.iutorsay.recipesapplication.MainActivity
import com.iutorsay.recipesapplication.PreparationActivity
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.adapters.DetailIngredientAdapter
import com.iutorsay.recipesapplication.adapters.DetailStepAdapter
import com.iutorsay.recipesapplication.adapters.bindImageFromUrl
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.repositories.IngredientRepository
import com.iutorsay.recipesapplication.data.repositories.StepRepository
import com.iutorsay.recipesapplication.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        val recipe = arguments?.getSerializable("recipe") as Recipe
        val ingredients = IngredientRepository.getInstance().getRecipeIngredients(recipe.recipeId)
        val steps = StepRepository.getInstance().getRecipeInstructions(recipe.recipeId)

        (activity as MainActivity).toolbar.toolbar_title.text = recipe.name
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        bindImageFromUrl(recipe_picture, recipe)
        recipe_description.text = recipe.description

        ingredients.observe(this, Observer { ingredientsList ->
            ingredientsList?.let {
                recipe_ingredients.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = DetailIngredientAdapter(it, 1)
                }
            }
        })

        steps.observe(this, Observer { stepsList ->
            stepsList?.let {
                recipe_instructions.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = DetailStepAdapter(it)
                }
            }
        })

        number_button.setOnClickListener(ElegantNumberButton.OnClickListener {
            ingredients.value?.let { ings ->
                recipe_ingredients.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = DetailIngredientAdapter(ings,  number_button.number.toInt())
                }
            }
        })

        preparation_button.setOnClickListener {
            val intent = Intent(context, PreparationActivity::class.java)

            intent.putExtra("recipe", recipe)

            startActivity(intent)
        }
    }
}
