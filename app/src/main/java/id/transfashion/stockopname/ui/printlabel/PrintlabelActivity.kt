package id.transfashion.stockopname.ui.printlabel

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import id.transfashion.stockopname.BaseDrawerActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.BarcodeScannerOptions
import id.transfashion.stockopname.data.model.Label
import id.transfashion.stockopname.utils.BluetoothPrintManager
import id.transfashion.stockopname.utils.toHtml
import kotlinx.coroutines.launch

class PrintlabelActivity : BaseDrawerActivity() {

    private lateinit var containerCamera: FragmentContainerView
    private lateinit var containerBarcode: FragmentContainerView
    private val bluetoothPrintManager by lazy { BluetoothPrintManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printlabel)

        setupDrawer(findViewById(R.id.toolbar))

        containerCamera = findViewById(R.id.fragment_printlabel_camera)
        containerBarcode = findViewById(R.id.fragment_printlabel_barcode)

        updateScannerVisibility()
    }

    override fun onResume() {
        super.onResume()
        updateScannerVisibility()
    }

    private fun updateScannerVisibility() {
        val prefs = getSharedPreferences("app_setting", Context.MODE_PRIVATE)
        val barcodeReaderName = prefs.getString("barcode_reader", BarcodeScannerOptions.SCANNER.name)
        val isUseCamera = BarcodeScannerOptions.entries.find { it.name == barcodeReaderName }?.isUseCamera ?: false

        if (isUseCamera) {
            containerCamera.visibility = View.VISIBLE
            containerBarcode.visibility = View.GONE
        } else {
            containerCamera.visibility = View.GONE
            containerBarcode.visibility = View.VISIBLE
        }
    }

    private fun getResultFragment(): PrintlabelResultFragment? {
        return supportFragmentManager.findFragmentById(R.id.fragment_printlabel_result) as? PrintlabelResultFragment
    }

    fun readBarcode(barcode: String) {
        lifecycleScope.launch {
            holdBarcodeReader(true)
            val resultFragment = getResultFragment()
            resultFragment?.setError(null)

            try {
                if (barcode.length < 13) {
                    resultFragment?.setError("Barcode <b>\"$barcode\"</b> tidak ditemukan".toHtml())
                    return@launch
                }

                // Simulasi Data (Idealnya ini dari Repository/API)
                val label = Label(
                    barcode = barcode,
                    description = "Product Name Simulation",
                    category = "Category",
                    price = java.math.BigDecimal(100000),
                    discount = "50%",
                    oldPrice = java.math.BigDecimal(200000),
                    pricingCode = "P01"
                )

                printBarcode(label)
            } catch (e: Exception) {
                resultFragment?.setError(e.message ?: "Gagal memproses barcode")
            } finally {
                holdBarcodeReader(false)
            }
        }
    }

    private suspend fun printBarcode(label: Label) {
        val prefs = getSharedPreferences("app_setting", Context.MODE_PRIVATE)
        val printerPrefix = prefs.getString("printer_prefix", "")

        if (printerPrefix.isNullOrEmpty()) {
            throw Exception("Printer belum diatur di Setting")
        }

        val tsplCommand = StringBuilder().apply {
            appendLine("SIZE 35 mm, 30 mm")
            appendLine("GAP 2 mm, 0")
            appendLine("DIRECTION 0")
            appendLine("CLS")
            appendLine("TEXT 10,10,\"ROMAN.TTF\",0,1,1,\"${label.description}\"")
            appendLine("BARCODE 10,50,\"128\",50,1,0,2,2,\"${label.barcode}\"")
            appendLine("PRINT 1")
        }.toString()

        bluetoothPrintManager.sendToPrinter(printerPrefix, tsplCommand)
    }

    fun holdBarcodeReader(hold: Boolean) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_printlabel_barcode) as? PrintlabelBarcodeFragment
        fragment?.setHold(hold)
    }
}
