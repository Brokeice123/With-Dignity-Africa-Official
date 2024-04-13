package com.example.withdignityfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class WillFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var bookSessionButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_will, container, false)

        db = FirebaseFirestore.getInstance()

        // Initialize the instance variables
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        datePicker = view.findViewById(R.id.datePicker)
        bookSessionButton = view.findViewById(R.id.bookSessionButton)

        bookSessionButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val selectedDate = getFormattedDate(datePicker)

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && selectedDate.isNotEmpty()) {
                addBooking(name, email, phone, selectedDate)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields and select a date.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun getFormattedDate(datePicker: DatePicker): String {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(calendar.time)
    }

    private fun addBooking(name: String, email: String, phone: String, date: String) {
        val booking = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "date" to date,
            "status" to "pending"
        )

        db.collection("bookings").add(booking)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(requireContext(), "Session booked successfully!", Toast.LENGTH_SHORT).show()
                // Clear the inputs
                nameEditText.text.clear()
                emailEditText.text.clear()
                phoneEditText.text.clear()
                datePicker.updateDate(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error booking session.", Toast.LENGTH_SHORT).show()
                // Optionally, show an error message to the user
            }
    }
}
