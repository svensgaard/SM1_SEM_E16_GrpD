package Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import grpd.sm1sem.prototype.EncounteredReportsActivity;
import grpd.sm1sem.prototype.R;

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
            Log.d(TAG, "GeofencingEvent has encountered an error");
        } else {
            int transition = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            String requestId = geofence.getRequestId();

            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
                //LOGIC FOR WHEN USER ENTERS GEOFENCE HERE
                Log.d(TAG, "Entering Geofence - " + requestId);

                CharSequence text = "Geofence entered";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();

                NotifyUser(requestId);

            } else if (transition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Log.d(TAG, "Exiting Geofence - " + requestId);
            }
        }
    }

    private void NotifyUser(String requestId) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon) // notification icon
                .setContentTitle("Nearby Report Encountered!") // title
                .setContentText("Entered geofence with id: " + requestId) // body message
                .setAutoCancel(true); // clear notification when clicked

        Intent redirectIntent = new Intent(this, EncounteredReportsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, redirectIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
