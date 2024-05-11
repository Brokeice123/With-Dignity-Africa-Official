package com.example.withdignityfinal.advice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.withdignityfinal.R


class WillAdvice : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_will_advice, container, false)

        val contactus = view.findViewById<Button>(R.id.book)
        contactus.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.PanicFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

}