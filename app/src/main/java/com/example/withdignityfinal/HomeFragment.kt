package com.example.withdignityfinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.HorizontalScrollView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val paniccardView = view.findViewById<CardView>(R.id.panic_cardview)
        paniccardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.PanicFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val packagescardView = view.findViewById<CardView>(R.id.packages_cardview)
        packagescardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.FuneralPackageFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val servicescardView = view.findViewById<CardView>(R.id.service_cardview)
        servicescardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.FindFuneralFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val willcardView = view.findViewById<CardView>(R.id.will_cardview)
        willcardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.WillFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val checklistcardView = view.findViewById<CardView>(R.id.checklist_cardview)
        checklistcardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.ChecklistFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val policycardView = view.findViewById<CardView>(R.id.policy_cardview)
        policycardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.PolicyFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val preplancardView = view.findViewById<CardView>(R.id.preplan_cardview)
        preplancardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.PreplanFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val helpcardView = view.findViewById<CardView>(R.id.help_cardview)
        helpcardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.HelpFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val advicecardView = view.findViewById<CardView>(R.id.advice_cardview)
        advicecardView.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.AdviceFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view

        val scrollView= view?.findViewById<HorizontalScrollView>(R.id.home_scrollview)



        if (scrollView != null) {
            scrollView.setOnContextClickListener(){Item ->
                when(Item.id){
                    R.id.panic_cardview -> {
                        replaceFragment(com.example.withdignityfinal.PanicFragment())
                        true
                    }
                    else -> false
                }
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}