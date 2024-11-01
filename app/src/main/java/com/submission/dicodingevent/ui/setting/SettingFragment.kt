package com.submission.dicodingevent.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.submission.dicodingevent.databinding.FragmentSettingFragmentBinding
import com.submission.dicodingevent.ui.datastorepref.MainViewModel
import com.submission.dicodingevent.ui.datastorepref.Settingpref
import com.submission.dicodingevent.ui.datastorepref.VmFactory
import com.submission.dicodingevent.ui.datastorepref.dataStore

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingFragmentBinding? = null
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by viewModels {
        VmFactory(Settingpref.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingFragmentBinding.inflate(inflater, container, false)
        val root: View? = _binding?.root

        mainViewModel.getThemeSet().observe(viewLifecycleOwner) { isDarkModeActive ->
            binding?.switchButton?.isChecked = isDarkModeActive
        }

        binding?.switchButton?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSet(isChecked)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
