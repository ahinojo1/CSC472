package csc472.depaul.edu.messender.Fragments;

import csc472.depaul.edu.messender.Notifications.MyResponse;
import csc472.depaul.edu.messender.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAmaxNAgE:APA91bGCJOWKkntMwKIAN29DjnHUD-yz5oKH5wUlRWG9bpHTNkdVqV6NBl0hv5RTe4W8ZO8UPQBRDmcNgQUlx2706O8lNzjs8bSswfsLJ-xERbMC5db4EeaL_b5REh4WWSf5triwdjzr"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}