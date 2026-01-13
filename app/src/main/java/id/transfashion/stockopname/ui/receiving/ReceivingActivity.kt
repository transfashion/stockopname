package id.transfashion.stockopname.ui.receiving

import android.os.Bundle
import id.transfashion.stockopname.BaseScannerActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.PrintLabelMode

class ReceivingActivity : BaseScannerActivity() {

    override var currentMode: PrintLabelMode = PrintLabelMode.RECEIVING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        setupDrawer(findViewById(R.id.toolbar))
        supportActionBar?.title = "Receiving"
        setupScanner()
    }
}
