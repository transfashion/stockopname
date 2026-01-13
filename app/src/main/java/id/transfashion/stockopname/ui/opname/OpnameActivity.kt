package id.transfashion.stockopname.ui.opname

import android.os.Bundle
import id.transfashion.stockopname.BaseScannerActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.PrintLabelMode

class OpnameActivity : BaseScannerActivity() {

    override var currentMode: PrintLabelMode = PrintLabelMode.OPNAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        setupDrawer(findViewById(R.id.toolbar))
        supportActionBar?.title = "Opname"
        setupScanner()
    }
}
