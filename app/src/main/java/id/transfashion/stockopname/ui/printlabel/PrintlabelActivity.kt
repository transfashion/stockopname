package id.transfashion.stockopname.ui.printlabel

import android.os.Bundle
import id.transfashion.stockopname.BaseScannerActivity
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.PrintLabelMode

class PrintlabelActivity : BaseScannerActivity() {

    override var currentMode: PrintLabelMode = PrintLabelMode.PRINT_LABEL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        setupDrawer(findViewById(R.id.toolbar))
        setupScanner()
    }
}
