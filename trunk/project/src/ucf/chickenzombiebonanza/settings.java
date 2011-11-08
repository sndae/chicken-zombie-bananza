package ucf.chickenzombiebonanza;

import android.app.Activity;
import ucf.chickenzombiebonanza.settings;
import ucf.chickenzombiebonanza.ChickenZombieBonanzaActivity;
import android.content.Intent;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Iterator;

public final class settings extends Activity{
	public static final String TAG = "ContactsAdder";
	private Button mBackButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "Activity State: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mBackButton = (Button) findViewById(R.id.backButton);//settingsButton 
        
        mBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "mAddAccountButton clicked");
                Context ctx = null;
				CharSequence txt = "lol";
				int duration = 0;
				//Toast.makeText(this, "Button clicked! " + "test", Toast.LENGTH_SHORT).show(); 
                
                
                //Toast.makeText(this, "Button clicked! ", Toast.LENGTH_SHORT).show();
                goBack();
            }
            
            protected void goBack() {
                
				//Intent backy = new Intent(this, ChickenZombieBonanzaActivity.class);
                //startActivity(backy);
            	
            	finish();
            }
        });
        }

}
