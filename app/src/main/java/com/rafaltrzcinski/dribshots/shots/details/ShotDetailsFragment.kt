package com.rafaltrzcinski.dribshots.shots.details

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.databinding.FragmentShotDetailsBinding
import com.rafaltrzcinski.dribshots.rest.model.Shot

class ShotDetailsFragment() : Fragment() {

    private var shot: Shot = Shot()

    constructor(shot: Shot) : this() {
        this.shot = shot
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentShotDetailsBinding>(
                inflater, R.layout.fragment_shot_details, container, false
        )

        binding.viewModel = ShotDetailsViewModel(shot)

        return binding.root
    }
}