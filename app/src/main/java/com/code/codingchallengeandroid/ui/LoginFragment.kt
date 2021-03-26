package com.code.codingchallengeandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.databinding.FragmentLoginBinding
import com.code.codingchallengeandroid.ui.activity.HomeActivity
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.utils.startNewActivity
import com.code.codingchallengeandroid.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    lateinit var viewModel: UserViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var strUsername: String
    private lateinit var strPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userPreferences = UserPreferences(requireContext())
        checkData()
        binding.buttonLogin.setOnClickListener {
            login()
        }

        return binding.root
    }

    private fun checkData() {
        userPreferences.userName.asLiveData().observe(requireActivity(), {
            if(it == null) {
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        })
       /* viewModel.getDetails(requireContext())!!.observe(
            viewLifecycleOwner,
            {
                if (it == null) {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_registrationFragment)
                }
            })*/
    }

    private fun login(){
        strUsername = binding.editTextUserName.text.toString().trim()
        strPassword = binding.editTextPassword.text.toString().trim()

        if(strUsername.isEmpty()){
            binding.editTextUserName.error = "Please enter the username"
            binding.editTextUserName.requestFocus()
        }
        else if(strPassword.isEmpty()){
            binding.editTextPassword.error = "Please enter the password"
            binding.editTextPassword.requestFocus()
        }
        else if(strUsername.length > 8 || strPassword.length > 8){
            binding.editTextPassword.error = "Length should not be more than 8 characters"
            binding.editTextPassword.requestFocus()
            binding.editTextUserName.error = "Length should not be more than 8 characters"
            binding.editTextUserName.requestFocus()
        }
        else{
            viewModel.getLoginDetails(requireContext(), strUsername, strPassword)!!.observe(
                viewLifecycleOwner,
                {
                    if (it == null) {
                        Toast.makeText(
                            requireContext(),
                            "Invalid username or password...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                })
        }
    }

}