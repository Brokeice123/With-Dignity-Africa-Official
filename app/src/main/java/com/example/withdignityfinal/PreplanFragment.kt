package com.example.withdignityfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

@Suppress("UNREACHABLE_CODE")
class PreplanFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_preplan, container, false)

        val preneedburial = view.findViewById<CardView>(R.id.preneedburial)
        preneedburial.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.preneedburial()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        val preneedcremation = view.findViewById<CardView>(R.id.preneedcremation)
        preneedcremation.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.preneedcremation()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        val concierged_preneed_cremation = view.findViewById<CardView>(R.id.concierged_preneed_cremation)
        concierged_preneed_cremation.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.concierged_preneed_cremation()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        val concierged_preneed_funeral = view.findViewById<CardView>(R.id.concierged_preneed_funeral)
        concierged_preneed_funeral.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.concierged_preneed_funeral()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view

    }

}