package com.onfido.toth.xkcdlib;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Service to connect to xkcd with retrofit.
 */
public interface XkcdService {
  String BASE_URL = "http://xkcd.com/";

  @GET("info.0.json")
  Call<Comic> getComic();

  @GET("{id}/info.0.json")
  Call<Comic> getComic(@Path("id") int id);
}
