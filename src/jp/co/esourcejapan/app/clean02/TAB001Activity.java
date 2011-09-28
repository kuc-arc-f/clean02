// テスト
package jp.co.esourcejapan.app.clean02;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.starmicronics.stario.*;

import jp.co.esourcejapan.fw.*;

public class TAB001Activity extends Activity {
	private String TAG ="TAB001Activity";

	private static final int MENU1_ID = Menu.FIRST;
	private static final int MENU2_ID = Menu.FIRST+1;
	//
	private static String portName = "";
	private static String portSettings = "";
	//
	Handler mHandler;
	private WebView       m_Web;
	private String        m_STR_url       ="http://esj-clean.main.jp/dev2/php/tst4.php";
	private String        m_STR_url02     ="http://esj-clean.main.jp/dev2/php/tst5.php";
	private String        m_STR_url03     ="http://esj-clean.main.jp/dev2/php/tst6.php";
	private ArrayList<String>    m_LIST = new ArrayList<String>();
	//
	private jp.co.esourcejapan.fw.HttpUtil m_Http = new HttpUtil();
	private jp.co.esourcejapan.fw.ComUtil m_Util  = new ComUtil();
	/* class */
	class JsObj {
		private Activity activity;

		public JsObj(Activity androidJs) {
			this.activity = androidJs;
		}

		public void exec_print(final String title1, final String title2, final String title3) {
			mHandler.post(new Runnable() {
				public void run() {
					try
					{
						proc_print(title1, title2, title3);
					}catch(Exception e){
						e.printStackTrace();
						m_Util.errorDialog(TAB001Activity.this, e.getMessage());
					}
					
				}
			});
		}
		public void exec_print02(final String title) {
			mHandler.post(new Runnable() {
				public void run() {
					try
					{
						proc_print02(title);
					}catch(Exception e){
						e.printStackTrace();
						m_Util.errorDialog(TAB001Activity.this, e.getMessage());
					}
					
				}
			});
		}
		public void exec_print03(final String title) {
			mHandler.post(new Runnable() {
				public void run() {
					try
					{
						proc_print03(title);
					}catch(Exception e){
						e.printStackTrace();
						m_Util.errorDialog(TAB001Activity.this, e.getMessage());
					}
					
				}
			});
		}
	}
    /** Called when the activity is first created. */
	private class WebViewClientLoading extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab001 );
        try
        {
            SharedPreferences prefs = getSharedPreferences("StarAndroidSample", 0);
	        portName = prefs.getString("PortName", "");
	        if(portName == null)
	        {
	        	portName = "";
	        }
	        portSettings = prefs.getString("PortSettings", "");
	        if(portSettings == null)
	        {
	        	portSettings = "";
	        }
        	mHandler = new Handler();
            init_proc();
        }catch(Exception e){
        	portName = "";
        	portSettings = "";
        	e.printStackTrace();
        	m_Util.errorDialog(this, e.toString());
        }
    }
    public void onStart ()
    {
    	super.onStart();
    	try
    	{
        	SharedPreferences prefs = getSharedPreferences("StarAndroidSample", 0);
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putString("PortName", portName);
        	editor.putString("PortSettings", portSettings);
        	editor.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }    
    //
    private void init_proc() throws Exception{
    	try
    	{
        	m_Web = (WebView) findViewById(R.id.web01 );
        	m_Web.clearCache(true);
        	m_Web.getSettings().setJavaScriptEnabled(true);
        	m_Web.getSettings().setBuiltInZoomControls(true);
        	m_Web.setWebViewClient(new WebViewClientLoading());
        	
    		JsObj jo = new JsObj(this);
    		m_Web.addJavascriptInterface(jo, "Android");        	
    		m_Web.loadUrl(m_STR_url);
    	}catch(Exception e){
    		throw e;
    	}
    }
    //
    private void proc_print(String title1,String title2,String title3){
    	try
    	{
//			m_Util.showDialog(this,"I", title1);
Log.d(TAG, "title1=" +title1);
Log.d(TAG, "title2=" +title2);
Log.d(TAG, "title3=" +title3);
Toast toast = Toast.makeText(this, "proc_print=" + title1 + ":"+ title2 + ":" + title3, Toast.LENGTH_SHORT);
toast.show();

    		m_LIST.clear();
    		m_LIST.add(title1);
    		m_LIST.add(title2);
    		m_LIST.add(title3);
    		PrintRecieptMini();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    //
	private void get_itemData(String url) throws Exception{
    	try
    	{
        	String s = m_Http.doGet(url);
        	JSONArray arr = new JSONArray( s );
        	StringBuilder sb =new StringBuilder();
			for(int i = 0; i < arr.length(); i++){
        	    String[] lime = new String[5];
				JSONObject obj = arr.getJSONObject(i);
				
				String s_id       = obj.getString("id");
				String s_item     = obj.getString("item");
				sb.append(s_item  +", ");
Log.d(TAG, "s_item=" +s_item);
        	    m_LIST.add(s_item);
			}
Toast toast = Toast.makeText(this, "get_itemData=" +sb.toString(), Toast.LENGTH_SHORT);
toast.show();
		//debug
String s_msg = sb.toString();
for(int i= 0; i < s_msg.length(); i++){
	System.out.printf("\\u%04X%n", Character.codePointAt(s_msg, i));
	int i_num =  Character.codePointAt(s_msg, i);
	String ss =String.format("\\u%04X%n", i_num);
	Log.d(TAG, "ss=" + ss);
}

    	}catch(Exception e){
    		throw e;
    	}
	}
    //
    private void proc_print02(String title){
    	try
    	{
//    		m_Util.showDialog(this,"I", title);
    		get_itemData(m_STR_url02);
    		if(m_LIST.size() < 1){
        		m_Util.errorDialog(this, "Item data is Nothing");
    			return;
    		}
    		PrintRecieptMini();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    private void proc_print03(String title){
    	try
    	{
    		get_itemData(m_STR_url03);
    		if(m_LIST.size() < 1){
        		m_Util.errorDialog(this, "Item data is Nothing");
    			return;
    		}
    		PrintRecieptMini();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    /*
    public void PrintReciept(View view)
    {
    	if(portSettings.toUpperCase().contains("MINI") == true)
    	{
    		PrintRecieptMini();
			Toast toast = Toast.makeText(this, "PrintRecieptMini: Select.", Toast.LENGTH_SHORT);
			toast.show();
    	}
    	else
    	{
    		PrintRecieptNormal();
    	}
    }
    */
    //
    private void PrintRecieptNormal()
    {
    	StarIOPort port = null;
    	try
    	{
    		port = StarIOPort.getPort(portName, portSettings, 5000);
    		
    		String buffer = "\u001b\u001d\u0061\u0001";             //Center Alignment - Refer to Pg. 3-29
            buffer = buffer + "\u005B" + "If loaded.. Logo1 goes here" + "\u005D\n";
            buffer = buffer + "\u001B\u001C\u0070\u0001\u0000";          //Stored Logo Printing - Refer to Pg. 3-38
            buffer = buffer + "Star Clothing Boutique\n";
            buffer = buffer + "1150 King Georges Post Rd.\n";
            buffer = buffer + "Edison, NJ 08837\n\n";
            buffer = buffer + "\u001b\u001d\u0061\u0000";             //Left Alignment - Refer to Pg. 3-29
            buffer = buffer + "\u001b\u0044\u0002\u0010\\u0022\u0000";      //Setting Horizontal Tab - Pg. 3-27
            buffer = buffer + "Date: 12/31/2008 " + "\u0009" + " Time: 9:10 PM\n";      //Moving Horizontal Tab - Pg. 3-26
            buffer = buffer + "------------------------------------------------ \n";
            buffer = buffer + "\u001b\u0045";                    //Select Emphasized Printing - Pg. 3-14
            buffer = buffer + "SALE\n";
            buffer = buffer + "\u001b\u0046";                    //Cancel Emphasized Printing - Pg. 3-14
            buffer = buffer + "300678566 " + "\u0009" + "  PLAN T-SHIRT" + "\u0009" + "         10.99\n";
            buffer = buffer + "300692003 " + "\u0009" + "  BLACK DENIM" + "\u0009" + "         29.99\n";
            buffer = buffer + "300651148 " + "\u0009" + "  BLUE DENIM" + "\u0009" + "         29.99\n";
            buffer = buffer + "300642980 " + "\u0009" + "  STRIPE DRESS" + "\u0009" + "         49.99\n";
            buffer = buffer + "300638471 " + "\u0009" + "  BLACK BOOT" + "\u0009" + "         35.99\n\n";
            buffer = buffer + "Subtotal " + "\u0009" + "" + "\u0009" + "        156.95";
            buffer = buffer + "Tax " + "\u0009" + "" + "\u0009" + "" + "         00.00";
            buffer = buffer + "------------------------------------------------ \n";
            buffer = buffer + "Total" + "\u0006" + "" + "\u0009" + "\u001b\u0069\u0001\u0001" + "         $156.95\n";    //Character Expansion - Pg. 3-10
            buffer = buffer + "\u001b\u0069\u0000\u0000";                                                          //Cancel Expansion - Pg. 3-10
            buffer = buffer + "------------------------------------------------ \n";
            buffer = buffer + "Charge\n" + "$159.95\n";
            buffer = buffer + "Visa XXXX-XXXX-XXXX-0123\n\n";
            buffer = buffer + "\u001b\u0034" + "Refunds and Exchanges" + "\u001b\u0035\n";                       //Specify/Cancel White/Black Invert - Pg. 3-16
            buffer = buffer + "Within " + "\u001b\u002d\u0001" + "30 days" + "\u001b\u002d\u0000" + " with receipt\n"; //Specify/Cancel Underline Printing - Pg. 3-15
            buffer = buffer + "And tags attached\n\n";
            buffer = buffer + "\u001b\u001d\u0061\u0001";             //Center Alignment - Refer to Pg. 3-29
            buffer = buffer + "\u001b\u0062\u0006\u0002\u0002" + " 12ab34cd56" + "\u001e\n";             //Barcode - Pg. 3-39 - 3-40
            buffer = buffer + "\u001b\u0064\u0002";                                            //Cut  - Pg. 3-41
            buffer = buffer + "\u0007";                                                    //Open Cash Drawer
            
            byte[] data = buffer.getBytes();
            
            port.writePort(data, 0, data.length);
            try
            {
            	Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
            	
            }
    	}
    	catch(StarIOPortException e)
    	{
    		Builder dialog = new AlertDialog.Builder(this);
			dialog.setNegativeButton("Ok", null);
			AlertDialog alert = dialog.create();
			alert.setTitle(e.getMessage());
			alert.show(); 
    	}
    	finally
    	{
    		if(port != null)
    		{
    			try
    			{
    				StarIOPort.releasePort(port);
    			}
    			catch(StarIOPortException e)
    			{
    				
    			}
    		}
    	}
    }    
    //
    private void PrintRecieptMini()
    {
    	StarIOPort port = null;
    	try
    	{
    		port = StarIOPort.getPort(portName, portSettings, 5000);
    		
    		String buffer = "\u001b\u0040";                     //Initialize printer Pg. 1-43
    		buffer = buffer + "\u001b\u0061\u0001";             //Center Alignment - Refer to Pg. 1-18
            buffer = buffer + "Star Clothing Boutique\n";
            buffer = buffer + "1150 King Georges Post Rd.\n";
            buffer = buffer + "Edison, NJ 08837\n\n";
            buffer = buffer + "\u001b\u0061\u0000";             //Left Alignment - Refer to Pg. 1-18
            buffer = buffer + "\u001b\u0044\u0001\u0008\u0017\u0000";      //Setting Horizontal Tab - Pg. 1-19
            buffer = buffer + "Date: 12/31/2008 " + " Time: 9:10 PM\n";      //Moving Horizontal Tab - Pg. 1-19
            buffer = buffer + "-------------------------------- \n";
            buffer = buffer + "\u001b\u0045\u0001";                    //Select Emphasized Printing - Pg.  1-13
            buffer = buffer + "SALE\n";
            buffer = buffer + "\u001b\u0045\u0000";                    //Cancel Emphasized Printing - Pg. 1-13
            /*
            buffer = buffer + "30067" + "\u0009" + "PLAN T-SHIRT" + "\u0009" + " 10.99\n";
            buffer = buffer + "30069" + "\u0009" + "BLACK DENIM" + "\u0009" + " 29.99\n";
            buffer = buffer + "30065" + "\u0009" + "BLUE DENIM" + "\u0009" + " 29.99\n";
            buffer = buffer + "30064" + "\u0009" + "STRIPE DRESS" + "\u0009" + " 49.99\n";
            buffer = buffer + "30063" + "\u0009" + "BLACK BOOT" + "\u0009" + " 35.99\n\n";
            */
            for(int i=0; i< m_LIST.size(); i++){
            	String s_item = m_LIST.get(i); 
                buffer = buffer + "30067" + "\u0009" + s_item  + "\u0009" + " 10.99\n";
            }
            buffer = buffer + "Subtotal" + "\u0009" + "\u0009" + "156.95\n";
            buffer = buffer + "Tax" + "\u0009" + "\u0009" + " 00.00\n";
            buffer = buffer + "--------------------------------\n";
            buffer = buffer + "\u001b\u0044\u0016\u0000";      //Setting Horizontal Tab - Pg. 1-19
            buffer = buffer + "Total" + "\u0009" + "\u0009" + "\u001b\u0021\u0012" + "$156.95\n";    //Character Size - Pg. 1-14
            buffer = buffer + "\u001b\u0021\u0000";                                                          //Cancel Expansion - Pg. 1-14
            buffer = buffer + "--------------------------------\n";
            buffer = buffer + "Charge\n" + "$159.95\n";
            buffer = buffer + "Visa XXXX-XXXX-XXXX-0123\n\n";
            buffer = buffer + "\u001d\u0042\u0001" + "Refunds and Exchanges" + "\u001d\u0042\u0000\n";                       //Specify/Cancel White/Black Invert - Pg. 1-15
            buffer = buffer + "Within " + "\u001b\u002d\u0001" + "30 days" + "\u001b\u002d\u0000" + " with receipt\n"; //Specify/Cancel Underline Printing - Pg. 1-12
            buffer = buffer + "And tags attached\n\n";
            buffer = buffer + "          \u001d\u0068\u0030\u001d\u0077\u0001\u001d\u006b\u0049\u000c" + " 12ab34cd56 " + "\n\n\n\n\n";             //Barcode - Pg.1-33 - 1-36
            
            byte[] data = buffer.getBytes();
            
            port.writePort(data, 0, data.length);
            try
            {
            	Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
            	
            }
    	}
    	catch(StarIOPortException e)
    	{
    		Builder dialog = new AlertDialog.Builder(this);
			dialog.setNegativeButton("Ok", null);
			AlertDialog alert = dialog.create();
			alert.setTitle(e.getMessage());
			alert.show(); 
    	}
    	finally
    	{
    		if(port != null)
    		{
    			try
    			{
    				StarIOPort.releasePort(port);
    			}
    			catch(StarIOPortException e)
    			{
    				
    			}
    		}
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	boolean result = super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU1_ID, Menu.NONE, "Setting");
    	
    	return result;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	Intent intent;
    	switch( item.getItemId() ){
    	case MENU1_ID :
        	Intent myIntent = new Intent(this, Settings.class);
            startActivityForResult(myIntent, 0); 
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }    
    //
    public static String getPortName()
    {
    	return portName;
    }
    public static void setPortName(String pName)
    {
    	portName = pName;
    }
    
    public static String getPortSettings()
    {
    	return portSettings;
    }
    public static void setPortSettings(String pSettings)
    {
    	portSettings = pSettings;
    }    
    
    
}