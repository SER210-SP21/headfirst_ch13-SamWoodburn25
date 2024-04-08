package edu.quinnipiac.ser210.guessinggame
/*
  * Sam Woodburn
  * 3/22/24
  * HFAD- Chapter12 Demo
  * Guessing Game- using live data
  * GameViewModel- extends view model, checks for the correct guess and updates the screen accordingly
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment")
    private val secretword = words.random().uppercase()
    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    private var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft: LiveData<Int>
        get() = _livesLeft
    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisply()
    }

    private fun deriveSecretWordDisply(): String {
        var disply = ""
        secretword.forEach {
            disply += checkLetter(it.toString())
        }
        return disply
    }

    private fun checkLetter(str: String) = when (correctGuesses.contains(str)){
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if(guess.length == 1) {
            if (secretword.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisply()
            } else {
                _incorrectGuesses.value += "$guess"
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if (isWon() || isLost()) _gameOver.value = true
        }
    }

    private fun isWon() = secretword.equals(secretWordDisplay.value, true)
    private fun isLost() = livesLeft.value ?: 0  <= 0

    fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "you won!"
        else if (isLost()) message = "you lost!"
        message += "The words was $secretword."
        return message
    }

    fun finishGame() {
        _gameOver.value = true
    }
}