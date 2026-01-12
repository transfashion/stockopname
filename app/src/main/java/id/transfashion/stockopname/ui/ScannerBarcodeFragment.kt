package id.transfashion.stockopname.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import id.transfashion.stockopname.BaseOpnameActivity
import id.transfashion.stockopname.R

class ScannerBarcodeFragment : Fragment() {

	private lateinit var etBarcode: EditText
	private lateinit var btnShowKeyboard: ImageButton

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_scanner_barcode, container, false)
		etBarcode = view.findViewById(R.id.etBarcode)
		btnShowKeyboard = view.findViewById(R.id.btnShowKeyboard)

		setupBarcodeScanner()
		setupKeyboardToggle()

		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		etBarcode.requestFocus()
	}

	private fun setupBarcodeScanner() {
		etBarcode.showSoftInputOnFocus = false
		etBarcode.setOnEditorActionListener { _, actionId, event ->
			if (actionId == EditorInfo.IME_ACTION_DONE ||
				(event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
			) {
				val barcode = etBarcode.text.toString().trim()
				if (barcode.isNotEmpty()) {
					(activity as? BaseOpnameActivity)?.findBarcode(barcode)
//					etBarcode.text.clear()
				}
				true
			} else {
				false
			}
		}
	}

	private fun setupKeyboardToggle() {
		btnShowKeyboard.setOnClickListener {
			etBarcode.requestFocus()
			val window = requireActivity().window
			val controller = WindowInsetsControllerCompat(window, etBarcode)

			val isKeyboardVisible = ViewCompat.getRootWindowInsets(etBarcode)
				?.isVisible(WindowInsetsCompat.Type.ime()) ?: false

			if (isKeyboardVisible) {
				controller.hide(WindowInsetsCompat.Type.ime())
			} else {
				controller.show(WindowInsetsCompat.Type.ime())
			}
		}
	}

	fun setHold(hold: Boolean) {
		if (hold) {
			etBarcode.isEnabled = false
			btnShowKeyboard.isEnabled = false
			etBarcode.setText("Wait...")
		} else {
			etBarcode.isEnabled = true
			btnShowKeyboard.isEnabled = true
			etBarcode.text.clear()
			etBarcode.requestFocus()
		}
	}
}
