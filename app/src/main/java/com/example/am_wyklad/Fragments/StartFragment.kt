package com.example.am_wyklad.Fragments

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.example.am_wyklad.R


class StartFragment : Fragment() {
    lateinit var text: View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        text = view.findViewById(R.id.text2);
        Handler().postDelayed({
            startAnimation()
        },50)
            return view

    }
    fun startAnimation() {
        var sunMaxY : Float = text.getTop().toFloat()
        var sunMinY : Float = text.getHeight().toFloat()
        var sunWidth : Float = text.width.toFloat()
        var sunMaxX : Float = text.width.toFloat() - sunWidth

        val cloud1Animator: ObjectAnimator = ObjectAnimator.ofFloat(text, "x", -sunWidth, sunMaxX+sunWidth).setDuration(4000)
        val cloud2Animator: ObjectAnimator = ObjectAnimator.ofFloat(text, "x", sunMaxX+sunWidth, -sunWidth).setDuration(6000)


        val sunriseHeightAnimator: ObjectAnimator = ObjectAnimator.ofFloat(text, "y", sunMinY, sunMaxY).setDuration(3000)
        sunriseHeightAnimator.interpolator = DecelerateInterpolator()

        val sunsetHeightAnimator: ObjectAnimator = ObjectAnimator.ofFloat(text, "y", sunMaxY, sunMinY).setDuration(3000)
        sunsetHeightAnimator.interpolator = AccelerateInterpolator()

        val sunriseWidthAnimator: ObjectAnimator = ObjectAnimator.ofFloat(text, "x", -sunWidth, sunMaxX/2).setDuration(3000)
        sunriseWidthAnimator.interpolator = AccelerateInterpolator()
        val sunsetWidthAnimator: ObjectAnimator = ObjectAnimator.ofFloat(text, "x", sunMaxX/2, sunMaxX+sunWidth).setDuration(3000)
        sunsetWidthAnimator.interpolator = DecelerateInterpolator()


        var animation: AnimatorSet = AnimatorSet()

        animation.play(sunriseHeightAnimator).with(sunriseWidthAnimator)
            .before(sunsetHeightAnimator).before(sunsetWidthAnimator)

        animation.play(cloud1Animator).with(cloud2Animator)

        animation.start()
    }


}