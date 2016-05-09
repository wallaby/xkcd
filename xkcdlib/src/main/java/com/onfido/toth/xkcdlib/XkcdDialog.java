package com.onfido.toth.xkcdlib;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A dialog to present the current xkcd comic.
 */
public class XkcdDialog extends DialogFragment {

  private static final Retrofit retrofit;
  private static final XkcdService service;

  static {
    retrofit  = new Retrofit.Builder()
        .baseUrl(XkcdService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    service = retrofit.create(XkcdService.class);
  }

  private FeedbackListener listener;

  /**
   * Use this factory method to create a new instance of
   * this fragment.
   *
   * @return A new instance of fragment XkcdDialog.
   */
  public static XkcdDialog newInstance() {
    return new XkcdDialog();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View dialogView = inflater.inflate(R.layout.fragment_xkcd_dialog, container, false);
    final TextView titleText = (TextView) dialogView.findViewById(R.id.xkcdlib_title_text);
    final ImageView comicImageView = (ImageView) dialogView.findViewById(R.id.xkcdlib_comic_image);
    Call<Comic> currentComic = service.getComic();

    currentComic.enqueue(new Callback<Comic>() {
      @Override
      public void onResponse(Call<Comic> call, Response<Comic> response) {
        final Comic comic = response.body();
        titleText.setText(String.format(getString(R.string.xkcdlib_dialog_title), comic.getTitle()));
        comicImageView.setContentDescription(comic.getTranscript());
        Picasso.with(getActivity()).load(comic.getImg())
            .resize(comicImageView.getWidth(), comicImageView.getWidth()).centerInside().into(comicImageView,
                new com.squareup.picasso.Callback() {
                  @Override
                  public void onSuccess() {
                    comicImageView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                        Toast.makeText(getActivity(), comic.getAlt(), Toast.LENGTH_LONG).show();
                      }
                    });
                  }

                  @Override
                  public void onError() {
                    Toast.makeText(getActivity(), R.string.xkcdlib_comic_image_load_error, Toast.LENGTH_SHORT).show();
                  }
                });
      }

      @Override
      public void onFailure(Call<Comic> call, Throwable t) {
        Toast.makeText(getActivity(), R.string.xkcdlib_comic_load_error, Toast.LENGTH_SHORT).show();
      }
    });

    ImageButton likeButton = (ImageButton) dialogView.findViewById(R.id.xkcdlib_like_button);
    likeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.positive();
          dismiss();
        }
      }
    });


    ImageButton dislikeButton = (ImageButton) dialogView.findViewById(R.id.xkcdlib_dislike_button);
    dislikeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.negative();
          dismiss();
        }
      }
    });

    return dialogView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof FeedbackListener) {
      listener = (FeedbackListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement FeedbackListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    listener = null;
  }

  /**
   * Users of this library should implement this listener to get the feedback of the user on the comics.
   */
  public interface FeedbackListener {
    void positive();
    void negative();
  }
}
