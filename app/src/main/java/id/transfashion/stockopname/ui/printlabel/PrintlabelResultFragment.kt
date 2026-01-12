package id.transfashion.stockopname.ui.printlabel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.transfashion.stockopname.R

class PrintlabelResultFragment : Fragment() {

    private var tvErrorMessage: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_printlabel_result, container, false)
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage)
        return view
    }

    fun setError(message: CharSequence?) {
        tvErrorMessage?.apply {
            if (message.isNullOrEmpty()) {
                visibility = View.GONE
            } else {
                text = message
                visibility = View.VISIBLE
            }
        }
    }
}
