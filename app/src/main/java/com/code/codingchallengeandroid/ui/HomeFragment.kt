package com.code.codingchallengeandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.code.codingchallengeandroid.databinding.FragmentHomeBinding
import com.code.codingchallengeandroid.ui.activity.AuthActivity
import com.code.codingchallengeandroid.userpreferences.UserPreferences
import com.code.codingchallengeandroid.utils.startNewActivity
import com.code.codingchallengeandroid.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: UserViewModel
    private lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userPreferences = UserPreferences(requireContext())
        checkData()

        binding.buttonLogout.setOnClickListener {
           logout()
        }

        return binding.root
    }

    private fun checkData() {
        userPreferences.userFullNameFlow.asLiveData().observe(requireActivity(),{
            binding.textViewName.text = "Welcome $it"
        })
        /*viewModel.getDetails(requireContext())!!.observe(
            viewLifecycleOwner,
            {
                if(it !=null){
                    ("Welcome " + it.fullName).also { it -> binding.textViewName.text = it }
                }

            })*/
    }

    private fun logout() = lifecycleScope.launch{
        Toast.makeText(requireContext(), "User Logged Out", Toast.LENGTH_SHORT).show()
        requireActivity().startNewActivity(AuthActivity::class.java)
        userPreferences.clear()
        viewModel.deleteUsers()
    }

}