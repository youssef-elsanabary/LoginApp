package com.example.loginapplication.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_register.*


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase

    lateinit var root : View
    lateinit var name1 :String
    lateinit var email1 :String
    lateinit var password1:String
    lateinit var address1 :String
    lateinit var phone1 :String
    lateinit var age1 :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User Info")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        root =inflater.inflate(R.layout.fragment_profile, container, false)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                name1 = binding.userName.text.toString()
                email1 = binding.userEMail.text.toString()
                password1 = binding.userPass.text.toString()
                address1 = binding.userAddress.text.toString()
                phone1 = binding.userPhone.text.toString()
                age1 = binding.userAge.text.toString()

                binding.userName.text = snapshot.child("Full name").value.toString()
                binding.userEMail.text = snapshot.child("e_mail").value.toString()
                binding.userPass.text = snapshot.child("password").value.toString()
                binding.userAddress.text = snapshot.child("Address").value.toString()
                binding.userPhone.text = snapshot.child("Phone").value.toString()
                binding.userAge.text = snapshot.child("Age").value.toString()
                    binding.logOut.setOnClickListener {
                    auth.signOut()
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        binding.update.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateFragment)
        }
    }
}
