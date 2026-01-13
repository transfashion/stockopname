package id.transfashion.stockopname.ui.transfer

import android.os.Bundle
import id.transfashion.stockopname.BaseScannerActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.PrintLabelMode

class TransferActivity : BaseScannerActivity() {

    override var currentMode: PrintLabelMode = PrintLabelMode.TRANSFER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        setupDrawer(findViewById(R.id.toolbar))
        supportActionBar?.title = "Transfer"
        setupScanner()
    }
}
