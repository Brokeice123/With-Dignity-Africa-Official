package com.example.withdignityfinal.packagefragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.withdignityfinal.R

class isithunzi_package : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_isithunzi_package, container, false)

        val isithunzideposit = view.findViewById<Button>(R.id.deposit)
        isithunzideposit.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.deposit()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val textView = view.findViewById<TextView>(R.id.marquee)
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.marqueeRepeatLimit = 2 // Repeat the marquee animation indefinitely
        textView.isSelected = true // Start the marquee animation

        return view
    }

}