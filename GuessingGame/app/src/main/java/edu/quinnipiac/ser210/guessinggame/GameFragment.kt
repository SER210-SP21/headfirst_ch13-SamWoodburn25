package edu.quinnipiac.ser210.guessinggame

/*
  * Sam Woodburn
  * 3/22/24
  * HFAD- Chapter12 Demo
  * Guessing Game- using live data
  * game fragment- extends fragment, plays game and updates screen according to the game fragment view model
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.lifecycle.ViewModelProvider
import edu.quinnipiac.ser210.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.gameOver.observe(viewLifecycleOwner, Observer {newValue ->
            if(newValue){
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}