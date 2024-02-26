package com.example.withdignityfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.cardview.widget.CardView

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
            val fragment = PanicFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val packagescardView = view.findViewById<CardView>(R.id.packages_cardview)
        packagescardView.setOnClickListener {
            // Handle click event here
            val fragment = FuneralPackageFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val servicescardView = view.findViewById<CardView>(R.id.service_cardview)
        servicescardView.setOnClickListener {
            // Handle click event here
            val fragment = FindFuneralFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val willcardView = view.findViewById<CardView>(R.id.will_cardview)
        willcardView.setOnClickListener {
            // Handle click event here
            val fragment = WillFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val checklistcardView = view.findViewById<CardView>(R.id.checklist_cardview)
        checklistcardView.setOnClickListener {
            // Handle click event here
            val fragment = ChecklistFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val policycardView = view.findViewById<CardView>(R.id.policy_cardview)
        policycardView.setOnClickListener {
            // Handle click event here
            val fragment = PolicyFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val preplancardView = view.findViewById<CardView>(R.id.preplan_cardview)
        preplancardView.setOnClickListener {
            // Handle click event here
            val fragment = PreplanFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val helpcardView = view.findViewById<CardView>(R.id.help_cardview)
        helpcardView.setOnClickListener {
            // Handle click event here
            val fragment = HelpFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val advicecardView = view.findViewById<CardView>(R.id.advice_cardview)
        advicecardView.setOnClickListener {
            // Handle click event here
            val fragment = AdviceFragment()
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
                        replaceFragment(PanicFragment())
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