package com.example.onlinesavdo.screen.changelanguage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.FragmentChangeLanguageBinding
import com.example.onlinesavdo.screen.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*


class ChangeLanguageFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentChangeLanguageBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      binding = FragmentChangeLanguageBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeLanguageFragment()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUzbek.setOnClickListener {
            Hawk.put("pref_lang","uz")
            requireActivity().finish()
            startActivity(Intent(requireActivity(),MainActivity::class.java))
        }

        binding.btnEnglish.setOnClickListener {
            requireActivity().finish()
            startActivity(Intent(requireActivity(),MainActivity::class.java))
            Hawk.put("pref_lang","en")
        }
    }
}