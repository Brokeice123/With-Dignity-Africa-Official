package com.example.withdignityfinal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class FuneralPackageFragment : Fragment() {
    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_funeral_package, container, false)

        val isithunzipackage = view.findViewById<CardView>(R.id.isithunzipackage)
        isithunzipackage.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.isithunzi_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val bronzefuneral = view.findViewById<CardView>(R.id.bronzefuneralpackage)
        bronzefuneral.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.bronzefuneral_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val attendedcremation = view.findViewById<CardView>(R.id.attendedcremationpackage)
        attendedcremation.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.attendedcremation_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val fullservicecremation = view.findViewById<CardView>(R.id.fullservicecremationpackage)
        fullservicecremation.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.fullservicecremation_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val silverfuneral = view.findViewById<CardView>(R.id.silverfuneralpackage)
        silverfuneral.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.silverfuneral_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val goldfuneral = view.findViewById<CardView>(R.id.goldfuneralpackage)
        goldfuneral.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.goldfuneral_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val platinumfuneral = view.findViewById<CardView>(R.id.platinumfuneralpackage)
        platinumfuneral.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.packagefragments.platinumfuneral_package()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

}