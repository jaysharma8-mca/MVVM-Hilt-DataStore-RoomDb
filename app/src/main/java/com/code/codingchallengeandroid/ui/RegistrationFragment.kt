package com.code.codingchallengeandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.databinding.FragmentRegistrationBinding
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.utils.enable
import com.code.codingchallengeandroid.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: UserViewModel
    private var strFullName: String = ""
    private var strUsername: String = ""
    private var strPassword: String = ""
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userPreferences = UserPreferences(requireContext())
        validationsEditText()

        binding.buttonRegister.setOnClickListener {
            registration()
        }
        return binding.root
    }

    private fun registration() {

        val navController = Navigation.findNavController(requireView())
        lifecycleScope.launch {
            viewModel.saveData(strFullName, strUsername, strPassword)
            viewModel.insertData(requireContext(), strFullName, strUsername, strPassword)
            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private fun validationsEditText() {
        val symbols = "0123456789/?!:;%"
        binding.editTextName.addTextChangedListener {
            strFullName = it?.toString()?.trim().toString()
            when {
                strFullName.isEmpty() -> {
                    binding.buttonRegister.enable(false)
                }
                strFullName.length <= 2 -> {
                    binding.buttonRegister.enable(false)
                }
                strFullName in symbols -> {
                    binding.buttonRegister.enable(false)
                }
                else -> {
                    binding.buttonRegister.enable(true)
                }
            }
        }

        binding.editTextUserName.addTextChangedListener {
            strUsername = it?.toString()?.trim().toString()
            when {
                strUsername.isEmpty() -> {
                    binding.buttonRegister.enable(false)
                }
                strUsername.length <= 3 -> {
                    binding.buttonRegister.enable(false)
                }
                else -> {
                    binding.buttonRegister.enable(true)
                }
            }
        }

        binding.editTextPassword.addTextChangedListener {
            strPassword = it?.toString()?.trim().toString()
            when {
                strPassword.isEmpty() -> {
                    binding.buttonRegister.enable(false)
                }
                strPassword.length <= 3 -> {
                    binding.buttonRegister.enable(false)
                }
                else -> {
                    binding.buttonRegister.enable(true)
                }
            }
        }
    }

}