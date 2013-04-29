package com.madeja;

import android.R.drawable;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.madeja.gpslib.GPSLib;

public class MainActivity extends MapActivity {
    /** Called when the activity is first created. */
    LinearLayout linearLayout;
    MapView mapView;
    final Activity activity = this;
    boolean zapnuteGPS;
    GPSLib gps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        zapnuteGPS = false;
		gps = new GPSLib(getApplicationContext());
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	Location loc = gps.getCurrentLocation();
        //final MapController controller = mapView.getController();
        
        mapView.getController().animateTo(new GeoPoint((int)(loc.getLatitude() * 1E6), (int)(loc.getLongitude() * 1E6)));
        Log.i("I", "Location" + loc.toString());
        mapView.getController().setZoom(15);
        
    	OnMenuItemClickListener polohyListener = new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.e("x","polohy");
				// TODO Auto-generated method stub
				return false;
			}
		};
		
    	OnMenuItemClickListener gpsListener = new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (!zapnuteGPS) {
					gps.zapniGPS();
					gps.zapniKompas(activity, mapView);
					zapnuteGPS = true;
					Toast.makeText( getApplicationContext(), 
							"Tracking zapnut�", 
							Toast.LENGTH_SHORT ).show(); 
				} else {
					gps.vypniGPS();
					zapnuteGPS = false;
					Toast.makeText( getApplicationContext(), 
							"Tracking vypnut�", 
							Toast.LENGTH_SHORT ).show(); 
				}

				// TODO Auto-generated method stub
				return false;
			}
		};

		
        menu.add("Polohy")
            .setIcon(drawable.ic_menu_recent_history)
            .setOnMenuItemClickListener(polohyListener)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add("GPS ON/OFF")
        	.setIcon(drawable.ic_menu_compass)
        	.setOnMenuItemClickListener(gpsListener)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        //Whatever else is under the "More" menu
        return true;
    }
    
    @Override
    protected boolean isRouteDisplayed()
    {

        return false;
    }
}