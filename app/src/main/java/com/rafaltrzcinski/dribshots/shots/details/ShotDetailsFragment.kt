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
import com.rafaltrzcinski.dribshots.shots.list.ShotsActivityContract

class ShotDetailsFragment() : Fragment() {

    private var shot: Shot = Shot()
    private var presenter: ShotsActivityContract.Presenter? = null

    constructor(shot: Shot, presenter: ShotsActivityContract.Presenter) : this() {
        this.shot = shot
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentShotDetailsBinding>(
                inflater, R.layout.fragment_shot_details, container, false
        )

        binding.viewModel = ShotDetailsViewModel(shot, presenter!!)

        return binding.root
    }
}