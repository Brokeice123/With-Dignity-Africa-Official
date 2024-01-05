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

class HomeFragment : Fragment() {

    private lateinit var scrollView: HorizontalScrollView
    private lateinit var panicCardView: CardView
    private lateinit var packagesCardView: CardView
    private lateinit var serviceCardView: CardView
    private lateinit var willCardView: CardView
    private lateinit var checklistCardView: CardView
    private lateinit var policyCardView: CardView
    private lateinit var avbobCardView: CardView
    private lateinit var preplanCardView: CardView
    private lateinit var helpCardView: CardView
    private lateinit var adviceCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

        panicCardView = view.findViewById(R.id.panic_cardview)
        packagesCardView = view.findViewById(R.id.packages_cardview)
        serviceCardView = view.findViewById(R.id.service_cardview)
        willCardView = view.findViewById(R.id.will_cardview)
        checklistCardView = view.findViewById(R.id.checklist_cardview)
        policyCardView = view.findViewById(R.id.policy_cardview)
        avbobCardView = view.findViewById(R.id.avbob_cardview)
        preplanCardView = view.findViewById(R.id.preplan_cardview)
        helpCardView = view.findViewById(R.id.help_cardview)
        adviceCardView = view.findViewById(R.id.advice_cardview)

        panicCardView.setOnClickListener {  }
        packagesCardView.setOnClickListener {  }
        serviceCardView.setOnClickListener {  }
        willCardView.setOnClickListener {  }
        checklistCardView.setOnClickListener {  }
        policyCardView.setOnClickListener {  }
        avbobCardView.setOnClickListener {  }
        preplanCardView.setOnClickListener {  }
        helpCardView.setOnClickListener {  }
        adviceCardView.setOnClickListener {  }

    }

    private fun findViewById(homeScrollview: Int): HorizontalScrollView {
        TODO("Not yet implemented")
    }


}