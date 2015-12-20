package com.angga.project.anggata;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationHelper.LocationHelperListener, View.OnClickListener {

    private Location location;
    private LogHelper logHelper;
    private LocationHelper locationHelper;
    private ItemLogAdapter itemLogAdapter;
    private boolean isServiceStarted;

    private Button startStopButton;
    private TextView serviceText;
    private ListView logContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        locationHelper = LocationHelper.GetInstance(this);
        logHelper = LogHelper.getInstance();
    }

    private void getView() {
        startStopButton = (Button) findViewById(R.id.button_start_stop);
        serviceText = (TextView) findViewById(R.id.text_service);
        logContainer = (ListView) findViewById(R.id.log_container);
        startStopButton.setOnClickListener(this);
        setServiceText();
    }

    private void setServiceText() {
        if(isServiceStarted)
            serviceText.setText(R.string.start_service);
        else
            serviceText.setText(R.string.stop_service);
    }

    private void startStopButtonClicked() {
        if(isServiceStarted)
            locationHelper.startService();
        else
            locationHelper.stopService();
        setServiceText();
    }

    @Override
    protected void onStart() {
        setServiceText();
        super.onStart();
    }

    @Override
    public void onGetNewLocation(Location location) {
        this.location = location;
        logHelper.addToLog(location, System.currentTimeMillis()/1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_stop:
                startStopButtonClicked();
                break;
            default:
                break;
        }

    }
}
