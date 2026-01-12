package id.transfashion.stockopname.ui.printlabel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.transfashion.stockopname.R

class PrintlabelCameraFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Pastikan Anda membuat file layout fragment_printlabel_camera.xml
		return inflater.inflate(R.layout.fragment_printlabel_camera, container, false)
	}
}
