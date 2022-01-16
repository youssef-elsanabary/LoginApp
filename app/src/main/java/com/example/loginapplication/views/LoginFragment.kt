package com.example.loginapplication.views


import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.loginapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    lateinit var auth :FirebaseAuth
    lateinit var databaseReference : DatabaseReference
    lateinit var database : FirebaseDatabase


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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        log_in.setOnClickListener {

            if (TextUtils.isEmpty(editTextTextEmailAddress.text.toString())){
                editTextTextEmailAddress.setError("Please enter your e-mail")
                return@setOnClickListener
            }else  if (TextUtils.isEmpty(editTextTextPassword.text.toString())){
                editTextTextPassword.setError("Please enter your password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.action_loginFragment_to_profileFragment)

                    } else {
                        Toast.makeText(
                            context,
                            "Login Failed, please try again ",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            //findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
        sign_up.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        forgePassword.setOnClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext(),R.style.MyAlertDialogStyle)
            builder.setTitle("Forget Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val userPassword =view.findViewById<EditText>(R.id.et_password)

            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgePassword(userPassword)

            })
            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ ->  })

            builder.show()

        }

    }

private fun forgePassword(userPassword: EditText){
    if (userPassword.text.toString().isEmpty()){
        return
    }
    auth.sendPasswordResetEmail(userPassword.text.toString())
        .addOnCompleteListener {
            if (it.isSuccessful)
                Toast.makeText(context,"Email Sent. " , Toast.LENGTH_LONG).show()
        }
}
}