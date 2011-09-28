//　設定
package jp.co.esourcejapan.app.clean02;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.widget.*;



public class Settings extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.settings);
            
            EditText portName = (EditText)findViewById(R.id.EditText_PortName);
        	EditText portSettings = (EditText)findViewById(R.id.EditText_PortSettings);
        	
        	portName.setText( TAB001Activity.getPortName());
        	portSettings.setText( TAB001Activity.getPortSettings());
        }catch(Exception e){
        	e.printStackTrace();
			Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
			toast.show();
        }
    	//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
    public void SaveSettings(View view)
    {
    	EditText portName = (EditText)findViewById(R.id.EditText_PortName);
    	EditText portSettings = (EditText)findViewById(R.id.EditText_PortSettings);
    	
    	TAB001Activity.setPortName(portName.getText().toString());
    	TAB001Activity.setPortSettings(portSettings.getText().toString());
    	Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
