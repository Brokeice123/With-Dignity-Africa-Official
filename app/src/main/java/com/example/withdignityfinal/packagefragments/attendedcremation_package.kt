package com.example.withdignityfinal.packagefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.withdignityfinal.R


class attendedcremation_package : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attendedcremation_package, container, false)

        val attendedcremationdeposit = view.findViewById<Button>(R.id.deposit)
        attendedcremationdeposit.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.deposit()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }


}