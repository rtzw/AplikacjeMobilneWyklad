package com.example.am_wyklad.Fragments

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.media.Image
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.example.am_wyklad.R


class StartFragment : Fragment() {
    lateinit var image: ImageView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        image = view.findViewById(R.id.image)
        Handler().postDelayed({
            startAnimation()
        },50)
            return view

    }
    fun startAnimation() {
        var MaxY : Float = image.getTop().toFloat()
        var MinY : Float = image.getHeight().toFloat()
        var width : Float = image.width.toFloat()
        var MaxX : Float = image.width.toFloat() - width

        val animation1: ObjectAnimator = ObjectAnimator.ofFloat(image, "x", - width, MaxX + width).setDuration(4000)
        val animation2: ObjectAnimator = ObjectAnimator.ofFloat(image, "x", MaxX + width, -width).setDuration(6000)


        val heightAnimation: ObjectAnimator = ObjectAnimator.ofFloat(image, "y", MinY, MaxY).setDuration(3000)
        heightAnimation.interpolator = DecelerateInterpolator()

        val heightAnimation2: ObjectAnimator = ObjectAnimator.ofFloat(image, "y", MinY, MaxY).setDuration(3000)
        heightAnimation2.interpolator = AccelerateInterpolator()

        val widthAnimation: ObjectAnimator = ObjectAnimator.ofFloat(image, "x", -width, MaxX / 2).setDuration(3000)
        widthAnimation.interpolator = AccelerateInterpolator()

        val widthAnimation2: ObjectAnimator = ObjectAnimator.ofFloat(image, "x", MaxX / 2, MaxX + width).setDuration(3000)
        widthAnimation2.interpolator = DecelerateInterpolator()


        var animation = AnimatorSet()

        animation.play(heightAnimation).with(widthAnimation)
            .before(heightAnimation2).before(widthAnimation2)

        animation.play(animation1).with(animation2)

        animation.start()
    }


}