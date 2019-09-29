package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R

class MenuFragment : Fragment() {
    lateinit var toMvpButton: Button
    lateinit var toMvvmButtton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toMvpButton = view.findViewById<Button>(R.id.to_mvp_button).also {
            it.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_from_menu_to_mvp)
            }
        }
        toMvvmButtton = view.findViewById<Button>(R.id.to_mvvm_button).also {
            it.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_from_menu_to_mvvm)
            }
        }
    }
}
