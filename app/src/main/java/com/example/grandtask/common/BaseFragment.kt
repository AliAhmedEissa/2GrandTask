package com.example.grandtask.common

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.grandtask.common.utils.ViewsManager

abstract class BaseFragment : Fragment() {

    protected fun showProgressView() {
        (requireActivity() as ViewsManager).showProgressBar()
    }

    protected fun hideProgressView() {
        (requireActivity() as ViewsManager).hideProgressBar()
    }

    protected fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}