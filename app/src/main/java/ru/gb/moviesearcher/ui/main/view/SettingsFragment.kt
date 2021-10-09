package ru.gb.moviesearcher.ui.main.view

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gb.moviesearcher.databinding.SettingsFragmentBinding


class SettingsFragment : Fragment() {
    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChildMode()
    }

    private fun initChildMode() {

        with(binding) {
            childModeSwitch.isChecked = requireActivity().getSharedPreferences(CHILD_MODE,MODE_PRIVATE)
                .getBoolean(CHILD_MODE_KEY, false)


            childModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val sharedPreferences: SharedPreferences =
                        requireActivity().getSharedPreferences(CHILD_MODE, Context.MODE_PRIVATE)
                    sharedPreferences.edit()
                        .putBoolean(CHILD_MODE_KEY, true)
                        .apply()

                } else{
                    val sharedPreferences: SharedPreferences =
                        requireActivity().getSharedPreferences(CHILD_MODE, Context.MODE_PRIVATE)
                    sharedPreferences.edit()
                        .putBoolean(CHILD_MODE_KEY, false)
                        .apply()
                }
            }
        }
    }
        companion object {
            @JvmStatic
            fun newInstance() = SettingsFragment()
        }
    }