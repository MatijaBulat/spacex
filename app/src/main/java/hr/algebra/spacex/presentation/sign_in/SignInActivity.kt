package hr.algebra.spacex.presentation.sign_in

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import hr.algebra.spacex.MainActivity
import hr.algebra.spacex.R
import hr.algebra.spacex.common.util.setBooleanPreference
import hr.algebra.spacex.common.util.startActivity
import hr.algebra.spacex.databinding.ActivitySignInBinding
import hr.algebra.spacex.databinding.ActivitySplashBinding
import hr.algebra.spacex.presentation.splash.DATA_IMPORTED
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var googleAuthUiClient: GoogleAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
        setObserver()

        googleAuthUiClient = GoogleAuthClient(applicationContext, Identity.getSignInClient(applicationContext))
        if(googleAuthUiClient.getSignedInUser() != null) {
            binding.btnSignIn.visibility = View.GONE
            println("pozz brate")
            Toast.makeText(this, "${googleAuthUiClient.getSignedInUser()!!.userName} logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                if (state.isSignInSuccessful) {
                    startActivity<MainActivity>()
                    Toast.makeText(this@SignInActivity, "${googleAuthUiClient.getSignedInUser()!!.userName} logged in", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult() ) { result ->
        if(result.resultCode == RESULT_OK) {
            lifecycleScope.launch {
                val signInResult = googleAuthUiClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )
                viewModel.onSignInResult(signInResult)
            }
        }
    }

    private fun setUpListeners() {
        binding.btnSkip.setOnClickListener {
            startActivity<MainActivity>()
        }
        binding.btnSignIn.setOnClickListener {
            println("btnSignIn")
            lifecycleScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }
    }
}