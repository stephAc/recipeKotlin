package com.iutorsay.recipesapplication.fragments.creation

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iutorsay.recipesapplication.R
import com.iutorsay.recipesapplication.adapters.DetailIngredientAdapter
import com.iutorsay.recipesapplication.adapters.EditIngredientsAdapter
import com.iutorsay.recipesapplication.adapters.EditInstructionsAdapter
//import com.iutorsay.recipesapplication.adapters.EditIngredientsAdapter
import com.iutorsay.recipesapplication.data.entities.Ingredient
import com.iutorsay.recipesapplication.databinding.FragmentIngredientsBinding
import com.iutorsay.recipesapplication.viewmodels.RecipeCreationViewModel
import kotlinx.android.synthetic.main.fragment_ingredients.*

class IngredientsFragment : Fragment() {
    private lateinit var creationViewModel: RecipeCreationViewModel
    private lateinit var editIngredientAdapter: EditIngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creationViewModel = activity?.run {
            ViewModelProviders.of(this).get(RecipeCreationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val binding = DataBindingUtil.inflate<FragmentIngredientsBinding>(inflater, R.layout.fragment_ingredients, container, false).apply {
            viewmodel = creationViewModel
            setLifecycleOwner(this@IngredientsFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editIngredientAdapter = EditIngredientsAdapter() { ingredient: Ingredient -> onIngredientCloseButtonClick(ingredient) }

        ingredients_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = editIngredientAdapter
            setHasFixedSize(true)
        }

        editIngredientAdapter.setIngredients(creationViewModel.currentIngredients)

        button_add_ingredient.setOnClickListener { addIngredientToList() }
    }

    private fun onIngredientCloseButtonClick(ingredient: Ingredient) {
        creationViewModel.currentIngredients.remove(ingredient)
        editIngredientAdapter.setIngredients(creationViewModel.currentIngredients)
    }

    private fun addIngredientToList() {
         if (hasNoErrors()) {
            val ingredientToAdd = Ingredient(0, 0, creationViewModel.inputIngredientName.value!!, creationViewModel.inputIngredientQuantity.value!!)
            creationViewModel.currentIngredients.add(ingredientToAdd)
            editIngredientAdapter.setIngredients(creationViewModel.currentIngredients)
            creationViewModel.inputIngredientName.value = ""
            creationViewModel.inputIngredientQuantity.value = ""
         }
    }

    private fun hasNoErrors(): Boolean {
        var hasNoErrros = true

        if (creationViewModel.inputIngredientName.value.isNullOrBlank()) {
            hasNoErrros = false
            input_name.error = "Le nom de l'ingrédient ne peut pas être vide"
        }

        if (creationViewModel.inputIngredientQuantity.value.isNullOrBlank()) {
            hasNoErrros = false
            input_quantity.error = "La quantité ne peut pas être vide"
        }

        return hasNoErrros
    }
}