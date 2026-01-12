package id.transfashion.stockopname

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import id.transfashion.stockopname.data.model.BarcodeScannerOptions
import id.transfashion.stockopname.data.model.PrintLabelMode
import id.transfashion.stockopname.utils.BarcodeReader

abstract class BaseOpnameActivity : BaseDrawerActivity() {

    protected lateinit var containerCamera: FragmentContainerView
    protected lateinit var containerBarcode: FragmentContainerView

    var isUseCamera = false
        protected set

    protected lateinit var barcodeReader: BarcodeReader
    abstract var currentMode: PrintLabelMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupScanner() {
        containerCamera = findViewById(R.id.fragment_printlabel_camera)
        containerBarcode = findViewById(R.id.fragment_printlabel_barcode)
        barcodeReader = BarcodeReader(this)
        updateScannerVisibility()
    }

    override fun onResume() {
        super.onResume()
        updateScannerVisibility()
    }

    protected fun updateScannerVisibility() {
        val prefs = getSharedPreferences("app_setting", Context.MODE_PRIVATE)
        val barcodeReaderName = prefs.getString("barcode_reader", BarcodeScannerOptions.SCANNER.name)
        isUseCamera = BarcodeScannerOptions.entries.find { it.name == barcodeReaderName }?.isUseCamera ?: false

        if (isUseCamera) {
            containerCamera.visibility = View.VISIBLE
            containerBarcode.visibility = View.GONE
        } else {
            containerCamera.visibility = View.GONE
            containerBarcode.visibility = View.VISIBLE
        }
    }

    fun findBarcode(barcode: String) {
        barcodeReader.findBarcode(barcode, currentMode)
    }
}
