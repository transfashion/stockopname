package id.transfashion.stockopname.utils

import android.content.Context
import androidx.lifecycle.lifecycleScope
import id.transfashion.stockopname.BaseOpnameActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.Label
import id.transfashion.stockopname.data.model.PrintLabelMode
import id.transfashion.stockopname.ui.ScannerBarcodeFragment
import id.transfashion.stockopname.ui.ScannerCameraFragment
import id.transfashion.stockopname.ui.ScannerResultFragment
import kotlinx.coroutines.launch

class BarcodeReader(private val activity: BaseOpnameActivity) {

    private val bluetoothPrintManager by lazy { BluetoothPrintManager(activity) }

    fun findBarcode(barcode: String, mode: PrintLabelMode) {
        activity.lifecycleScope.launch {
            holdBarcodeReader(true)
            val resultFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_printlabel_result) as? ScannerResultFragment
            resultFragment?.setError(null)

            try {
                if (barcode.equals("TM34567890123")) {
                    soundBarcodeNotFound()
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

                soundBarcodeFound()

                when (mode) {
                    PrintLabelMode.PRINT_LABEL -> {
                        printBarcode(label)
                    }
                    PrintLabelMode.OPNAME -> {

                    }
                    PrintLabelMode.RECEIVING -> {
                    }
                    PrintLabelMode.TRANSFER -> {
                    }
                }

            } catch (e: Exception) {
                resultFragment?.setError(e.message ?: "Gagal memproses barcode")
            } finally {
                holdBarcodeReader(false)
            }
        }
    }

    private suspend fun printBarcode(label: Label) {
        val prefs = activity.getSharedPreferences("app_setting", Context.MODE_PRIVATE)
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

        //  bluetoothPrintManager.sendToPrinter(printerPrefix, tsplCommand)
    }

    fun holdBarcodeReader(hold: Boolean) {
        if (activity.isUseCamera) {
            val cameraFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_printlabel_camera) as? ScannerCameraFragment
            cameraFragment?.setHold(hold)
        } else {
            val barcodeFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_printlabel_barcode) as? ScannerBarcodeFragment
            barcodeFragment?.setHold(hold)
        }
    }

    fun soundBarcodeFound() {
        if (activity.isUseCamera) {
            val cameraFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_printlabel_camera) as? ScannerCameraFragment
            cameraFragment?.beepFound()
        }
    }

    fun soundBarcodeNotFound() {
        if (activity.isUseCamera) {
            val cameraFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_printlabel_camera) as? ScannerCameraFragment
            cameraFragment?.beepNotFound()
        }
    }
}
