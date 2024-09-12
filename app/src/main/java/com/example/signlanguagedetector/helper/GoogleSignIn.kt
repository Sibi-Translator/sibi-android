package com.example.signlanguagedetector.helper

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth


object GoogleLogin {

    var auth : FirebaseAuth? = null
    val googleClientID = "17529980087-lt2pimqhe8igmd6gkd27g6nvpt9q4e4c.apps.googleusercontent.com"
    fun getGoogleLoginAuth(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(googleClientID)
            .requestId()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }

    val option = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(googleClientID)
        .build()
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(option)
        .build()

    var credentialManager :CredentialManager? = null

//    fun login(activity: Activity, coroutineScope: CoroutineScope) {
//        credentialManager = CredentialManager.create(activity)
//        coroutineScope.launch {
//            try {
//                val result = credentialManager!!.getCredential(
//                    request = request,
//                    context = activity,
//                )
//            } catch (e: NoCredentialException) {
//                e.printStackTrace()
//                // if there is no credential, request to add google account
//                val addAccountIntent = Intent(Settings.ACTION_ADD_ACCOUNT)
//                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                addAccountIntent.putExtra(
//                    Settings.EXTRA_ACCOUNT_TYPES,
//                    arrayOf<String>("com.google")
//                )
//                activity.startActivity(addAccountIntent)
//            } catch (e: GetCredentialException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    val signInRequest = BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//        .setSupported(true)
//        // Your server's client ID, not your Android client ID.
//        .setServerClientId(googleClientID)
//        // Only show accounts previously used to sign in.
//        .setFilterByAuthorizedAccounts(true)
//        .build())
//        .build()
//
//    fun firebaseLogin(context: Context) {
//        val oneTapClient = Identity.getSignInClient(context)
//        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//        val idToken = googleCredential.googleIdToken
//        when {
//            idToken != null -> {
//                // Got an ID token from Google. Use it to authenticate
//                // with Firebase.
//                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                auth.signInWithCredential(firebaseCredential)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                            updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            updateUI(null)
//                        }
//                    }
//            }
//            else -> {
//                // Shouldn't happen.
//                Log.d(TAG, "No ID token!")
//            }
//        }
//    }
}