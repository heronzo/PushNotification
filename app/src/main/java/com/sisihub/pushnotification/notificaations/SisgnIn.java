package com.sisihub.pushnotification.notificaations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sisihub.pushnotification.R;
import com.sisihub.pushnotification.home.Home;
import com.sisihub.pushnotification.portal.StudentsPortal;
/*
The sign in activity handling user authentication both by the fcm server and the gmail server
 */

public class SisgnIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
        Button signIn;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog progress;
    private FirebaseAuth mFirebaseAuth;
    private static final int REC_SNIN = 9001;
    private static final String TAG = "SignInActivity";
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sisgn_in);
        signIn = (Button) findViewById(R.id.sign_in);
        mFirebaseAuth = FirebaseAuth.getInstance();
        img = (ImageView) findViewById(R.id.imageView) ;
        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_animation);
        //img.startAnimation(animation);
        //progress = new ProgressDialog(this);
        signIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this/* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                signIn();
                break;
        }
    }
    /*
     *signIn method
     */
    private void signIn() {
//        progress.setMessage("Fetching Accounts ");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
//        progress.show();
//        final int totalProgressTime = 20;
//        final Thread tr = new Thread(){
//            @Override
//            public void run() {
//                int jumpTime = 0;
//                while(jumpTime < totalProgressTime){
//                    try {
//                        sleep(20);
//                        jumpTime += 5;
//                        progress.setProgress(jumpTime);
//                    } catch (InterruptedException e) {
//                  // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        tr.start();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REC_SNIN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REC_SNIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                startActivity(new Intent(this, MainActivity.class));
                firebaseAuthWithGoogle(account);


            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
                Toast.makeText(this,"Google Sign Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SisgnIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //startActivity(new Intent(SisgnIn.this, StudentsPortal.class));
                            Toast.makeText(SisgnIn.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Services error.", Toast.LENGTH_SHORT).show();
    }
}
