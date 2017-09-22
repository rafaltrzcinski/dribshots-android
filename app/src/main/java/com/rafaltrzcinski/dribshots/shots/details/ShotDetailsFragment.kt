package com.rafaltrzcinski.dribshots.shots.details

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.databinding.FragmentShotDetailsBinding
import com.rafaltrzcinski.dribshots.di.Injector
import com.rafaltrzcinski.dribshots.rest.model.Shot

class ShotDetailsFragment : Fragment() {

    companion object {
        private val SHOT_PARCEL = "SHOT_PARCEL"

        fun newInstance(shot: Shot): ShotDetailsFragment {
            val instance = ShotDetailsFragment()
            val args = Bundle()
            args.putParcelable(SHOT_PARCEL, shot)
            instance.arguments = args
            return instance
        }
    }

    private var presenter = Injector.component.getShotsListPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentShotDetailsBinding>(
                inflater, R.layout.fragment_shot_details, container, false
        )

        val shot: Shot = arguments.getParcelable(SHOT_PARCEL)

        binding.viewModel = ShotDetailsViewModel(shot, presenter)

        return binding.root
    }
}