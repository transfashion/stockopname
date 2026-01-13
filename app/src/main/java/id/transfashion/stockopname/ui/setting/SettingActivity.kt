package id.transfashion.stockopname.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import id.transfashion.stockopname.R
import id.transfashion.stockopname.data.model.BarcodeScannerOptions
import id.transfashion.stockopname.data.model.PrinterOptions

class SettingActivity : AppCompatActivity() {

    private lateinit var etSiteCode: EditText
    private lateinit var etBrandCode: EditText
    private lateinit var spBarcodeReader: Spinner
    private lateinit var spPrinter: Spinner

    private val prefs by lazy {
        getSharedPreferences("app_setting", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
		// harus menggunakan Light Theme
		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_NO
		)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Setting"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        bindView()
        setupBarcodeReaderSpinner()
        setupPrinterSpinner()
        loadSetting()
    }

    private fun bindView() {
        etSiteCode = findViewById(R.id.etSiteCode)
        etBrandCode = findViewById(R.id.etBrandCode)
        spBarcodeReader = findViewById(R.id.spBarcodeReader)
        spPrinter = findViewById(R.id.spPrinter)
    }

    private fun setupBarcodeReaderSpinner() {
        val options = BarcodeScannerOptions.entries.map { it.displayName }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spBarcodeReader.adapter = adapter
    }

    private fun setupPrinterSpinner() {
        val options = PrinterOptions.entries.map { it.displayName }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spPrinter.adapter = adapter
    }

    /**
     * Load setting saat Activity dibuka
     */
    private fun loadSetting() {
        etSiteCode.setText(prefs.getString("site_code", ""))
        etBrandCode.setText(prefs.getString("brand_code", ""))

        val barcodeReaderName = prefs.getString("barcode_reader", BarcodeScannerOptions.SCANNER.name)
        val barcodeIndex = BarcodeScannerOptions.entries.indexOfFirst { it.name == barcodeReaderName }
            .coerceAtLeast(0)
        spBarcodeReader.setSelection(barcodeIndex)

        val printerPrefix = prefs.getString("printer_prefix", "")
        val printerIndex = PrinterOptions.entries.indexOfFirst { it.prefix == printerPrefix }
            .coerceAtLeast(0)
        spPrinter.setSelection(printerIndex)
    }

    /**
     * Simpan setting otomatis
     */
    private fun saveSetting() {
        val selectedBarcodeReader = BarcodeScannerOptions.entries[spBarcodeReader.selectedItemPosition]
        val selectedPrinter = PrinterOptions.entries[spPrinter.selectedItemPosition]

        prefs.edit().apply {
            putString("site_code", etSiteCode.text.toString())
            putString("brand_code", etBrandCode.text.toString())
            putString("barcode_reader", selectedBarcodeReader.name)
            putString("printer_prefix", selectedPrinter.prefix)
            apply()
        }
    }

    override fun onPause() {
        super.onPause()
        saveSetting() // AUTO SAVE saat Back / Home
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
