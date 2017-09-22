package com.rafaltrzcinski.dribshots.shots.list

import android.app.FragmentTransaction
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.databinding.ActivityShotsBinding
import com.rafaltrzcinski.dribshots.di.Injector
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.shots.details.ShotDetailsFragment

class ShotsActivity : AppCompatActivity(), ShotsActivityContract.View {

    private val presenter = Injector.component.getShotsListPresenter()
    private val shotsAdapter = ShotsAdapter()
    private var swipeLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityShotsBinding>(this, R.layout.activity_shots)

        initSwipeRefresh(binding)
        initToolbar(binding)
        initRecyclerView(binding, initLayoutManager())

        presenter.bind(this)
        presenter.getShots()
    }

    private fun initSwipeRefresh(binding: ActivityShotsBinding) {
        swipeLayout = binding.swipeLayout
        swipeLayout?.setOnRefreshListener { presenter.getShots() }
    }

    private fun initToolbar(binding: ActivityShotsBinding) {
        binding.toolbar.apply {
            title = getString(R.string.dribble_shots)
            navigationIcon = getDrawable(R.drawable.ic_dribble)
        }
    }

    private fun initRecyclerView(binding: ActivityShotsBinding, gridLayoutManager: StaggeredGridLayoutManager) {

        val onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getNextShots()
                }
            }
        }

        binding.shotsRecycler.apply {
            layoutManager = gridLayoutManager
            adapter = shotsAdapter
            addOnScrollListener(onScrollListener)
        }
    }


    private fun initLayoutManager() = StaggeredGridLayoutManager(2, VERTICAL).apply {
        gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

    }

    override fun loadShots(shots: List<Shot>) {
        shotsAdapter.addItems(shots)
    }

    override fun loadNextShots(shots: List<Shot>) {
        shotsAdapter.addNextItems(shots)
    }

    override fun showLoadingError() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.loading_error))
                .setMessage(getString(R.string.check_connection))
                .setPositiveButton(getString(R.string.ok), null)
                .show()
    }

    override fun attachShotDetails(shot: Shot) {
        fragmentManager.apply {
            popBackStack()
            beginTransaction().apply {
                setCustomAnimations(
                        R.animator.shot_flip_right_in,
                        R.animator.shot_flip_left_out,
                        R.animator.shot_flip_left_in,
                        R.animator.shot_flip_left_out
                )
                replace(R.id.fragment_frame, ShotDetailsFragment.newInstance(shot))
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                addToBackStack(null)
            }.commit()
        }
    }

    override fun detachShotDetails() = fragmentManager.popBackStack()

    override fun startLoading() {
        swipeLayout?.isRefreshing = true
    }

    override fun finishLoading() {
        swipeLayout?.isRefreshing = false
    }
}


