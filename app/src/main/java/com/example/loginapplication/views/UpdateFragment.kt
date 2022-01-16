package com.example.loginapplication.views

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginapplication.R
import com.example.loginapplication.databinding.FragmentProfileBinding
import com.example.loginapplication.databinding.FragmentUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_update.*


class UpdateFragment : Fragment() {
    private lateinit var binding : FragmentUpdateBinding
    lateinit var auth : FirebaseAuth
    lateinit var databaseReference : DatabaseReference
    lateinit var database : FirebaseDatabase
    lateinit var name2:String
    lateinit var email2:String
    lateinit var password2:String
    lateinit var address2 :String
    lateinit var phone2 :String
    lateinit var age2 :String



    lateinit var v : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database.getReference("User Info")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_update, container, false)
        return v
    }
     override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         binding = FragmentUpdateBinding.bind(view)
         val currentUser = auth.currentUser
         val currentUserDb = databaseReference?.child((currentUser?.uid!!))
         save.setOnClickListener {
             name2 =  binding.updatedName.text.toString().trim()
             email2 = binding.updatedEMail.text.toString().trim()
             password2 = binding.updatedPass.text.toString().trim()
             address2 = binding.updatedAddress.text.toString().trim()
             phone2 =  binding.updatedPhone.text.toString().trim()
             age2 =  binding.updatedDataOFbirth.text.toString().trim()

             if (name2.isEmpty()) {
                 binding.updatedName.error = "Please enter your name"
                 binding.updatedName.requestFocus()
                 return@setOnClickListener
             } else if (email2.isEmpty()) {
                 binding.updatedEMail.error = "Please enter your e-mail"
                 binding.updatedEMail.requestFocus()
                 return@setOnClickListener
             } else if (password2.isEmpty()) {
                 binding.updatedPass.error = "Please enter your password"
                 binding.updatedPass.requestFocus()
                 return@setOnClickListener
             } else if (address2.isEmpty()) {
                 binding.updatedAddress.error = "Please enter your address"
                 binding.updatedAddress.requestFocus()
                 return@setOnClickListener
             } else if (phone2.isEmpty()) {
                 binding.updatedPhone.error = "Please enter your phone"
                 binding.updatedPhone.requestFocus()
                 return@setOnClickListener
             } else if (age2.isEmpty()) {
                 binding.updatedDataOFbirth.error = "Please enter your age"
                 binding.updatedDataOFbirth.requestFocus()
                 return@setOnClickListener
             }
             currentUser?.updateEmail(email2)
                 ?.addOnCompleteListener {
                     if (it.isSuccessful){
                         currentUserDb?.child("e_mail")
                             ?.setValue(email2)
                     }
                 }
             currentUser?.updatePassword(password2)
                 ?.addOnCompleteListener {
                     if (it.isSuccessful){
                         currentUserDb?.child("password")
                             ?.setValue(password2)
                     }
                 }


             currentUserDb?.child("Full name")
                 ?.setValue(name2)
             currentUserDb?.child("Address")
                 ?.setValue(address2)
             currentUserDb?.child("Phone")
                 ?.setValue(phone2)
             currentUserDb?.child("Age")
                 ?.setValue(age2)
             Toast.makeText(context, "Updated Success ", Toast.LENGTH_LONG)
                 .show()
             findNavController().navigate(R.id.action_updateFragment_to_profileFragment)
         }
         dismiss.setOnClickListener {
             findNavController().navigate(R.id.action_updateFragment_to_profileFragment)
         }
     }
}
