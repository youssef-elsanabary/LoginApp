package com.example.loginapplication.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    lateinit var auth : FirebaseAuth
    lateinit var databaseReference : DatabaseReference
    lateinit var database : FirebaseDatabase
    var name = ""
    var email = ""
    var password = ""
    var address = ""
    var phone = ""
    var age = ""

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register.setOnClickListener {

            name = name_et.text.toString().trim()
            email = e_mail_et.text.toString().trim()
            password = pass_et.text.toString().trim()
            address = address_et.text.toString().trim()
            phone = phone_et.text.toString().trim()
            age = age_et.text.toString().trim()

            if (TextUtils.isEmpty(name)) {
                name_et.error = "Please enter your name"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(email)) {
                e_mail_et.error = "Please enter your e-mail"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(password)) {
                pass_et.error = "Please enter your password"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(address)) {
                address_et.error = "Please enter your address"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(phone)) {
                phone_et.error = "Please enter your phone"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(age)) {
                age_et.error = "Please enter your age"
                return@setOnClickListener
            }
                auth.createUserWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                            val id = databaseReference.push().key
                            currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener {

                                }
                            currentUserDb?.child("Full name")
                                ?.setValue(name)
                            currentUserDb?.child("e_mail")
                                ?.setValue(email)
                            currentUserDb?.child("password")
                                ?.setValue(password)
                            currentUserDb?.child("Address")
                                ?.setValue(address)
                            currentUserDb?.child("Phone")
                                ?.setValue(phone)
                            currentUserDb?.child("Age")
                                ?.setValue(age)
                            Toast.makeText(context, "Registration Success ", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                "Registration Failed, please try again ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        }
    }
