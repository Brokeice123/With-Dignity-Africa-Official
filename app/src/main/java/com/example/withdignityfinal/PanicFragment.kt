package com.example.withdignityfinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class PanicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_panic, container, false)

        val emailButton = view.findViewById<Button>(R.id.emailButton)
        emailButton.setOnClickListener {
            // Implement email sending functionality here
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:ninjamariao@gmail.com") // Change to your support email
                putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                putExtra(Intent.EXTRA_TEXT, "Dear Dignity Africa,")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }

        val callButton = view.findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            // Implement phone call functionality here
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+254748927062") // Change to your support phone number
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }

        val chatButton = view.findViewById<Button>(R.id.chatButton)
        chatButton.setOnClickListener {
            // Implement WhatsApp chat functionality here
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=+254748927062&text=Hello!%20I%20need%20help.") // Change the phone number and message as needed
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }

        return view
    }
}
