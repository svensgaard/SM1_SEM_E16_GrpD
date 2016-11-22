package Services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by Dan on 15-11-2016.
 */

public class GeofenceService extends IntentService {
    public static final String TAG = "GeofenceService";

    public GeofenceService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()){
            //TODO HANDLE ERROR
        } else {
            int transition = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            String requestId = geofence.getRequestId();

            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
                //LOGIC FOR WHEN USER ENTERS GEOFENCE HERE
                Log.d(TAG, "Entering Geofence - " + requestId);
            } else if (transition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Log.d(TAG, "Exiting Geofence - " + requestId);
            }
        }

    }
}
