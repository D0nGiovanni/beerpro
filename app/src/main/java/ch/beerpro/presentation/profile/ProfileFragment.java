package ch.beerpro.presentation.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.presentation.MainViewModel;
import ch.beerpro.presentation.profile.mybeers.MyBeersActivity;
import ch.beerpro.domain.models.MyBeer;
import ch.beerpro.presentation.profile.myratings.MyRatingsActivity;
import ch.beerpro.presentation.profile.mywishlist.WishlistActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Because the profile view is not a whole activity but rendered as part of the MainActivity in a tab, we use a so-called fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private ImageView userProfileImageView;
    private TextView userProfileName;

    // TODO create attributes for all views that need to be accessed from the code
    private TextView myBeers;
    private TextView ratings;
    private TextView wishlist;
    private TextView fridge;

    private MainViewModel model;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        /* Fragments also have a layout file, this one is in res/layout/fragment_profile_screen.xml: */
        View rootView = inflater.inflate(R.layout.fragment_profile_screen, container, false);

        // TODO use rootView.findViewById to assign the attribute views
        userProfileImageView = rootView.findViewById(R.id.imageViewProfile);
        userProfileName = rootView.findViewById(R.id.textViewName);
        myBeers = rootView.findViewById(R.id.textViewBeersNo);
        ratings = rootView.findViewById(R.id.textViewRatingsNo);
        wishlist = rootView.findViewById(R.id.textViewWishListNo);
        fridge = rootView.findViewById(R.id.textViewFridgeNo);


        // TODO set OnClickListeners to start activities. The Activities to start are MyRatingsActivity, WishlistActivity and MyBeersActivity.
        rootView.findViewById(R.id.profileLinks).setOnClickListener(view -> {
            if (view == myBeers) {
                Intent i = new Intent(this.getActivity(), MyBeersActivity.class);
                startActivity(i);
            }
            else if (view == ratings) {
                Intent i = new Intent(this.getActivity(), MyRatingsActivity.class);
                startActivity(i);
            }
            else if (view == wishlist) {
                Intent i = new Intent(this.getActivity(), WishlistActivity.class);
                startActivity(i);
            }
            else if (view == fridge) {
            }
        });

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getMyWishlist().observe(this, this::updateWishlistCount);
        model.getMyRatings().observe(this, this::updateRatingsCount);
        model.getMyBeers().observe(this, this::updateMyBeersCount);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            // TODO set user name
            userProfileName.setText(name);

            Uri photoUrl = user.getPhotoUrl();
            GlideApp.with(this).load(photoUrl).apply(new RequestOptions().circleCrop()).into(userProfileImageView);
        }

        return rootView;
    }

    private void updateMyBeersCount(List<MyBeer> myBeers) {
        // TODO set beer count text
        this.myBeers.setText(String.valueOf(myBeers.size()));
    }

    private void updateRatingsCount(List<Rating> ratings) {
        // TODO set ratings count text
        this.ratings.setText(String.valueOf(ratings.size()));
    }

    private void updateWishlistCount(List<Wish> wishes) {
        // TODO set wishlist count text
        this.wishlist.setText(String.valueOf(wishes.size()));
    }
}
