package com.example.mvvvmretrofitrxjava;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {
    @GET("repos")
        //放URL 地址中最後的位置
    Call<List<GithubRepo>> getDynamicGithub();

    @GET("users/{user_id}/repos")
        //user_id 要跟 value 的 "user_id" 名稱要一樣
    Call<List<GithubRepo>> getDynamicGithub(@Path(value = "user_id", encoded = true) String userId); // Call<List<GithubRepo>> = 資料接回來的型態

    @GET("users/{user_id}/repos")
    Observable<List<GithubRepo>> getGithubRX(@Path(value = "user_id", encoded = true) String userId); // Call<List<GithubRepo>> = 資料接回來的型態

    @GET("users/{user_id}/repos")
    MutableLiveData<List<GithubRepo>> getGithubRXlive(@Path(value = "user_id", encoded = true) String userId); // Call<List<GithubRepo>> = 資料接回來的型態

    @GET("repos/{user_id}/{repos}/issues")
        //user_id 要跟 value 的 "user_id" 名稱要一樣
    Call<List<GithubRepo>> getGithubIssue(@Path(value = "user_id", encoded = true) String userId,
                                          @Path(value = "repos", encoded = true) String repos);




//    @POST("{user_id}/Internet_test/issues/1/comments")
//    @Headers({"Authorization: token 6c7566e49fc3091d9e5fab0748993d4ec28b8ba7", //用post 的寫法1，寫法2在 AppClientManager
//            "Content-Type: application/json"}
//    Call<GithubRepo> getDynamicGithubByhPost(@Path(value = "user_id", encoded = true) String userId,
//                                             @Body Comment body);
//
//    @PATCH("{user_id}/{repo}/issues/comments/519868249")
//    @Headers({"Authorization: token bc7ccec19ffbcd6b0f939b0bab71a5d8ab984717 ", //token 不對的話會回傳401
//            "Content-Type: application/json"})
//        //github 不寫  "Content-Type: application/json" 可加可不加
//    Call<GithubRepo> UpdatedGithubByhPatch(@Path(value = "user_id", encoded = true) String userId,
//                                           @Body Comment body);


}


