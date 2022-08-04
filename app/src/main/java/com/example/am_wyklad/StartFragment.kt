//package com.example.am_wyklad
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
//import com.example.am_wyklad.Fragments.LoginFragment
//
//
//class BlankFragment : Fragment() {
//    lateinit var someButton: View;
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_start, container, false)
//        someButton = view.findViewById(R.id.someButton);
//        someButton.setOnClickListener(){
//            val loginFragment = LoginFragment();
//            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
//
//            fragmentTransaction.replace(R.id.mainActivity, loginFragment)
//            fragmentTransaction.commit()
//        }
//        return view
//    }
//}