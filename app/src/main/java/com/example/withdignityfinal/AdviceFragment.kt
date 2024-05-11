package com.example.withdignityfinal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class AdviceFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_advice, container, false)

        val helpfriendAdvice = view.findViewById<CardView>(R.id.HelpfriendAdvice)
        helpfriendAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.HelpfriendAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val willAdvice = view.findViewById<CardView>(R.id.WillAdvice)
        willAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.WillAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val whattodoAdvice = view.findViewById<CardView>(R.id.WhattodoAdvice)
        whattodoAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.WhattodoAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val honouringAdvice = view.findViewById<CardView>(R.id.HonouringAdvice)
        honouringAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.HonouringAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val livingfuneralAdvice = view.findViewById<CardView>(R.id.LivingfuneralAdvice)
        livingfuneralAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.LivingfuneralAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val deathpositiveMindsetAdvice = view.findViewById<CardView>(R.id.DeathpositiveMindsetAdvice)
        deathpositiveMindsetAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.DeathpositiveMindsetAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val funeralquizAdvice = view.findViewById<CardView>(R.id.FuneralquizAdvice)
        funeralquizAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.FuneralquizAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val culturalpracticesAdvice = view.findViewById<CardView>(R.id.CulturalpracticesAdvice)
        culturalpracticesAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.CulturalpracticesAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val burialxCremationAdvice = view.findViewById<CardView>(R.id.BurialxCremationAdvice)
        burialxCremationAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.BurialxCremationAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val costAdvice = view.findViewById<CardView>(R.id.CostAdvice)
        costAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.CostAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val funeralxMemorialAdvice = view.findViewById<CardView>(R.id.FuneralxMemorialAdvice)
        funeralxMemorialAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.FuneralxMemorialAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val eulogyAdvice = view.findViewById<CardView>(R.id.EulogyAdvice)
        eulogyAdvice.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.advice.EulogyAdvice()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }


}