package edu.quinnipiac.ser210.guessinggame

/*
  * Sam Woodburn
  * 3/22/24
  * HFAD- Chapter12 Demo
  * Guessing Game- using live data
  * resultViewModelFactory- a class to create and initialize view models
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ResultViewModelFactory(private val finalResult: String) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel(finalResult) as T
        throw IllegalArgumentException("Unknown View Model")
    }
}