package com.jj.clearscore.presentation.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jj.clearscore.databinding.FragmentScoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    //viewModel
    private val viewModel: ScoreViewModel by viewModels()

    //binding
    private lateinit var binding: FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //initialize binding
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.fetchScore()
    }

    //init observers
    private fun initObservers() {
        //observe state
        viewModel.state.observe(viewLifecycleOwner, {
            if (it.isLoading) {
                showLoader()
            } else {
                if (it.creditScore == null) {
                    showError()
                } else {
                    showScore(it)
                }
            }
        })
    }

    //loading
    private fun showLoader() {
        binding.progress.visibility = View.VISIBLE
        binding.donutScore.visibility = View.GONE
        binding.error.visibility = View.GONE
    }

    //error
    private fun showError() {
        binding.progress.visibility = View.GONE
        binding.donutScore.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }

    // score
    private fun showScore(state: ScoreState) {
        binding.progress.visibility = View.GONE
        binding.donutScore.visibility = View.VISIBLE
        binding.error.visibility = View.GONE

        binding.donutScore.apply {
            maxScore = state.creditScore?.maxScore ?: 700
            score = state.creditScore?.score ?: 0
            arcAngle = 80f
        }
    }

}