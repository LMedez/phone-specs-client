package com.luc.phonespecs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.luc.phonespecs.databinding.ActivityMainBinding
import com.luc.phonespecs.ui.home.HomeFragmentDirections
import com.luc.phonespecs.utils.contentView

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PhoneSpecs)

        setUpBottomNavigationAndFab()

        val actionMenuView = binding.additionalMenu
        menuInflater.inflate(R.menu.bottom_app_bar_menu_left, actionMenuView.menu)
    }

    private fun setUpBottomNavigationAndFab() {

        binding.run {
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(this@MainActivity)
        }

        binding.fab.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)
            setOnClickListener {
                navigateToSearch()
            }
        }
    }

    private fun navigateToSearch() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
        }
        val directions = HomeFragmentDirections.actionHomeFragmentToSelectionSearchFragment()
        findNavController(R.id.nav_host_fragment).navigate(directions)
    }


    private fun setBottomAppBarForSearch() {
        hideBottomAppBar()
        binding.fab.hide()
    }


    private fun hideBottomAppBar() {
        binding.run {
            bottomAppBar.performHide()
            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
            // the fab and bottom app bar until after it's exit animation finishes.
            bottomAppBar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator?) {
                    if (isCanceled) return

                    // Hide the BottomAppBar to avoid it showing above the keyboard
                    // when composing a new email.
                    bottomAppBar.visibility = View.GONE
                    fab.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    isCanceled = true
                }
            })
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.bottomAppBar.performShow()
                binding.fab.show()
            }

            R.id.selectionSearchFragment -> setBottomAppBarForSearch()

            R.id.phoneDetail -> setBottomAppBarForSearch()

        }
    }
}