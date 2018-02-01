package doors.open.ottawa.services;

import doors.open.ottawa.model.BuildingPOJO;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Class APIService.
 *
 * Use Retrofit 2 to fetch the buildings.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public interface APIService {

    String BASE_URL = "https://doors-open-ottawa-ro.mybluemix.net/";
    String FEED = "buildings";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(FEED)
    Call<BuildingPOJO[]> getBuildings();
}
