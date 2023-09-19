package com.example.horoscapp.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.example.horoscapp.R
import com.example.horoscapp.databinding.FragmentLuckBinding
import com.example.horoscapp.ui.core.listeners.OnSwipeTouchListener
import com.example.horoscapp.ui.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LuckFragment : Fragment() {

    private var _binding : FragmentLuckBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var randomCardProvider: RandomCardProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        preparePrediction()
        initListeners()
    }

    private fun preparePrediction() {
        val luck = randomCardProvider.getLucky()
        luck?.let {
            val currentPrediction = getString(it.text)
            binding.tvLucky.text = currentPrediction
            binding.ivLuckyCard.setImageResource(it.image)
            binding.tvShare.setOnClickListener { shareResult(currentPrediction) }
        }
    }

    private fun shareResult(prediction : String) {
        val sendIntent : Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prediction)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        //binding.ivRoulette.setOnClickListener { spinRoulette() }
        binding.ivRoulette.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){

            override fun onSwipeRight() {
                spinRoulette()
            }
            override fun onSwipeLeft() {
                spinRoulette()
            }

        })
    }

    private fun spinRoulette() {
        val random = java.util.Random()
        val degrees = random.nextInt(1400) + 360

        val animator = ObjectAnimator.ofFloat(binding.ivRoulette, View.ROTATION, 0f, degrees.toFloat())
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()
    }

    private fun slideCard() {
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                //cuando la animacion inicie
                binding.reverse.isVisible = true
            }
            override fun onAnimationEnd(p0: Animation?) {
                //cuando la animacion termine
                growCard()
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //cuando se repita la animacion
            }
        })

        binding.reverse.startAnimation(slideUpAnimation)
    }

    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                //al iniciar la aanimacion
            }

            override fun onAnimationEnd(p0: Animation?) {
                //al terminar la animacion
                binding.reverse.isVisible = false
                showPredictionVew()
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //si se repite la animacion
            }

        })

        binding.reverse.startAnimation(growAnimation)
    }

    private fun showPredictionVew() {
        val disappearAnimation = AlphaAnimation(1.0f, 0.0f)
        disappearAnimation.duration = 200

        val appearAnimation = AlphaAnimation(0.0f, 1.0f)
        appearAnimation.duration = 1000

        disappearAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                //al iniciar la animacion
            }

            override fun onAnimationEnd(p0: Animation?) {
                //al terminar la animacion
                binding.preview.isVisible = false
                binding.prediction.isVisible = true
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //si se repite la animacion
            }
        })
        binding.preview.startAnimation(disappearAnimation)
        binding.prediction.startAnimation(appearAnimation)
    }
}