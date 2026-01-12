package id.transfashion.stockopname

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.transfashion.stockopname.ui.login.LoginActivity
import id.transfashion.stockopname.utils.SessionManager

abstract class BaseActivity : AppCompatActivity() {

	protected lateinit var sessionManager: SessionManager

	override fun onCreate(savedInstanceState: Bundle?) {
		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_NO
		)

		super.onCreate(savedInstanceState)

		// Inisialisasi SessionManager SEKALI untuk semua Activity
		sessionManager = SessionManager(this)
	}

	/**
	 * Wajib dipanggil oleh Activity yang membutuhkan login
	 */
	protected fun requireLogin() {
		if (!sessionManager.isLoggedIn()) {
			redirectToLogin()
		}
	}

	/**
	 * Redirect ke LoginActivity dengan clear task
	 * Digunakan saat:
	 * - User belum login
	 * - Session expired
	 * - Logout
	 */
	protected fun redirectToLogin() {
		val intent = Intent(this, LoginActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		startActivity(intent)
		finish()
	}
}
