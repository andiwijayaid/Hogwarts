package id.andiwijaya.hogwarts.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import id.andiwijaya.hogwarts.R

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return binding.root
    }

    protected abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    protected fun pop(navDirections: NavDirections) = findNavController().navigate(
        navDirections,
        NavOptions.Builder().apply {
            setEnterAnim(R.anim.slide_in_left)
            setExitAnim(R.anim.slide_out_right)
            setPopEnterAnim(R.anim.slide_in_right)
            setPopExitAnim(R.anim.slide_out_left)
            setPopUpTo(R.id.nav_graph, true)
            setLaunchSingleTop(true)
        }.build()
    )

    protected fun goTo(navDirections: NavDirections) = findNavController().navigate(
        navDirections,
        NavOptions.Builder().apply {
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.slide_in_left)
            setPopExitAnim(R.anim.slide_out_right)
        }.build()
    )

    protected fun back() = findNavController().navigateUp()

}