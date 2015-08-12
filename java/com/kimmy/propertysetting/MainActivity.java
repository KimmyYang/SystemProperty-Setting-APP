package com.kimmy.propertysetting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import java.lang.reflect.*;
import android.provider.Settings;

public class MainActivity extends Activity {

    private Button setProBtn1;
    private Button setProBtn0;
    private TextView valueText;
    private Spinner spinner;
    final static  String TAG = "kimmy";
    Class SysPropertiesClass;

    private ArrayAdapter<String> propList;
    final String PERSIST_GSMAPP_T_ONE = "persist.gsmapp.t_one_number";
    final String PERSIST_AAA_TEST = "persist.aaa.test";
    String mProperty = PERSIST_GSMAPP_T_ONE;
    private String[] property = {PERSIST_GSMAPP_T_ONE, PERSIST_AAA_TEST};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //spinner
        spinner = (Spinner)findViewById(R.id.spinner);
        propList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, property);
        
        spinner.setAdapter(propList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                mProperty = (String)arg0.getItemAtPosition(position);
                Log.i(TAG,"input spinner property = "+mProperty);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        valueText = (TextView)findViewById(R.id.textView_1);
        valueText.setText("Null");
        setProBtn1 = (Button)findViewById(R.id.setpro1_button);

        setProBtn1.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                setProperty(1);
                String property = getProperty();
                Log.i(TAG,"get property = "+property);
                valueText.setText(property);

            }

        });

        setProBtn0 = (Button)findViewById(R.id.setpro0_button);

        setProBtn0.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                setProperty(0);
                String property = getProperty();
                Log.i(TAG,"get property = "+property);
                valueText.setText(property);

            }

        });
    }
    public  void setProperty(int value) {
        // Modifying a system property

        Log.i(TAG,"kimmy , set property = "+value);
        final ClassLoader classloader = ClassLoader.getSystemClassLoader();
        try {
            final Class<Object> classToLoad = (Class<Object>) classloader.loadClass("android.os.SystemProperties");
            //final Object myInstance  = classToLoad.newInstance();
            final Method doSomething = classToLoad.getDeclaredMethod("set", new Class[]{String.class, String.class});

            doSomething.invoke(classToLoad,new Object[] {mProperty, Integer.toString(value)});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public  String getProperty() {
        String property=null;
        final ClassLoader classloader = ClassLoader.getSystemClassLoader();
        try {
            final Class<Object> classToLoad = (Class<Object>) classloader.loadClass("android.os.SystemProperties");
            //final Object myInstance  = classToLoad.newInstance();
            final Method doSomething = classToLoad.getDeclaredMethod("get", new Class[]{String.class});
            property = (String)doSomething.invoke(classToLoad,new Object[] {mProperty});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        finally {
            return property;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
