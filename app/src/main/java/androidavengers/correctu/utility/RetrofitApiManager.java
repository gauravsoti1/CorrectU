package androidavengers.correctu.utility;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by hp on 1/28/2016.
 */
public class RetrofitApiManager {
    private static RetrofitApiManager ourInstance;
    RestAdapter restAdapter;
    private GetWordApiInterface getWordApiServiceInstance;

    private RetrofitApiManager() {
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonConstants.SERVER_URL)
                .build();
    }

    public static RetrofitApiManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitApiManager();
        }
        return ourInstance;
    }

    public GetWordApiInterface  getWordApiService(){
        if(ourInstance.getWordApiServiceInstance == null) {
            ourInstance.getWordApiServiceInstance = ourInstance.restAdapter.create(GetWordApiInterface.class);
        }
        return ourInstance.getWordApiServiceInstance;
    }

    public static interface GetWordApiInterface {

        @GET("/definition/{word}")
        void gettingWordMeaning(@Path("word") String word,
                                Callback<List<WordPojo>> cb);


    }
}
