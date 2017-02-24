package com.sisihub.pushnotification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText et1;
    TextView vMsg;
    String messages = "No new notifications";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
   // private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>
     //       mFirebaseAdapter;
   private SharedPreferences mSharedPreferences;
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent());
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        EventBus.getDefault().register(this);

        //et1 = (EditText) findViewById(R.id.Edit1);
        vMsg = (TextView) findViewById(R.id.tvViewMessage);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        FirebaseMessaging.getInstance().subscribeToTopic( "Test" );
        FirebaseInstanceId.getInstance().getToken();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SisgnIn.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        if(getIntent().getExtras() !=null){

            messages = getIntent().getExtras().getString("message");
            if (messages==null){
                messages = "No new notifications";
            }

        }

        vMsg.setText("Message:\t"+messages);
    }/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String message) {
        //et1.setText(message);
        vMsg.setText(message);
    };
    /*
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String message){
        vMsg.setText(message);
    }*/
    /*
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessage(String message){
        vMsg.setText(message);
    };
    */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String message) {
        vMsg.setText("Message \t"+message +"\n\n" +"Link: www.maseno.ac.ke");
    };

    @Override
    protected void onStart() {
        super.onStart();
            Intent intent = new Intent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                setContentView(R.layout.activity_main);
                // extract the extra-data in the Notification
                String msg = extras.getString("message");
                //et1 = (EditText) findViewById( R.id.Edit1 );
                //et1.setText(msg);
            }
        }


    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                setContentView(R.layout.activity_main);
                // extract the extra-data in the Notification
                String msg = extras.getString("message");
                //et1 = (EditText) findViewById( R.id.Edit1 );
                //et1.setText(msg);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
