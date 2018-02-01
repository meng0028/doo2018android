package doors.open.ottawa.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import doors.open.ottawa.model.BuildingPOJO;
import retrofit2.Call;

/**
 * Class APIServiceWrapper.
 *
 * Run API calls in background, as an asynchronous task.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class APIServiceWrapper extends IntentService {

    public static final String API_SERVICE_MESSAGE   = "APIServiceMessage";
    public static final String API_SERVICE_PAYLOAD   = "APIServicePayload";
    public static final String API_SERVICE_EXCEPTION = "APIServiceException";

    public APIServiceWrapper() {
        super("APIServiceWrapper");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make the web service request
        APIService apiService = APIService.retrofit.create(APIService.class);
        Call<BuildingPOJO[]> call = apiService.getBuildings();

        BuildingPOJO[] buildingsArray;
        try {
            buildingsArray = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Intent messageIntent = new Intent(API_SERVICE_MESSAGE);
            messageIntent.putExtra(API_SERVICE_EXCEPTION, e.getMessage());
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
            return;
        }

        // Return data to MainActivity
        Intent messageIntent = new Intent(API_SERVICE_MESSAGE);
        messageIntent.putExtra(API_SERVICE_PAYLOAD, buildingsArray);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }
}
