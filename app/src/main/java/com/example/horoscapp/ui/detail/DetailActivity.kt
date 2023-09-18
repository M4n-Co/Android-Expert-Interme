package com.example.horoscapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.horoscapp.R
import com.example.horoscapp.databinding.ActivityDetailBinding
import com.example.horoscapp.domain.model.HoroscopeModel
import com.example.horoscapp.domain.model.HoroscopeModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val detailViewModel : DetailViewModel by viewModels()

    private val args : DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        detailViewModel.getHoroscope(args.type)
    }

    private fun initUI() {
        initUIState()
        initListeners()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                detailViewModel.state.collect{
                    when(it){
                        DetailState.Loading -> loadingState()
                        is DetailState.Error -> errorState()
                        is DetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.pb.isVisible = true
    }
    private fun errorState() {
        binding.pb.isVisible = false
    }
    private fun successState(state: DetailState.Success) {
        binding.pb.isVisible = false
        binding.tvTitlte.text = state.sign
        binding.tvBody.text = state.prediction

        val image = when(state.horoscopeModel){
            Aries -> R.drawable.detail_aries
            Taurus -> R.drawable.detail_taurus
            Gemini -> R.drawable.detail_gemini
            Cancer -> R.drawable.detail_cancer
            Leo -> R.drawable.detail_leo
            Virgo -> R.drawable.detail_virgo
            Libra -> R.drawable.detail_libra
            Scorpio -> R.drawable.detail_scorpio
            Sagittarius -> R.drawable.detail_sagittarius
            Capricorn -> R.drawable.detail_capricorn
            Aquarius -> R.drawable.detail_aquarius
            Pisces -> R.drawable.detail_pisces
        }

        binding.ivDetail.setImageResource(image)
    }

}