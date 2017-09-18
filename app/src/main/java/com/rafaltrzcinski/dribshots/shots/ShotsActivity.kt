package com.rafaltrzcinski.dribshots.shots

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.databinding.ActivityShotsBinding
import com.rafaltrzcinski.dribshots.di.Injector
import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShotsActivity : AppCompatActivity(), ShotsActivityContract.View {

    private val presenter = ShotsPresenter(
            Injector.component.getApiRequests(),
            Schedulers.io(),
            AndroidSchedulers.mainThread()
    )

    private val shotsAdapter = ShotsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityShotsBinding>(this, R.layout.activity_shots)
        initToolbar(binding)
        val gridLayoutManager = initLayoutManager()
        initRecyclerView(binding, gridLayoutManager)

        presenter.bind(this)
        presenter.getShots()
    }

    private fun initToolbar(binding: ActivityShotsBinding) {
        binding.toolbar.apply {
            title = getString(R.string.dribble_shots)
            navigationIcon = getDrawable(R.drawable.ic_dribble)
        }
    }

    private fun initLayoutManager() = StaggeredGridLayoutManager(2, VERTICAL).apply {
        gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

    }

    private fun initRecyclerView(binding: ActivityShotsBinding, gridLayoutManager: StaggeredGridLayoutManager) =
            binding.shotsRecycler.apply {
                layoutManager = gridLayoutManager
                adapter = shotsAdapter
            }

    override fun loadShots(shots: List<Shot>) {
        shotsAdapter.addItems(shots)
    }

    override fun showLoadingError() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.loading_error))
                .setMessage(getString(R.string.check_connection))
                .setPositiveButton(getString(R.string.ok), null)
                .show()
    }
}

