package place.picker.placepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class MainActivity extends Activity implements PlaceSelectionListener {

    int PLACE_PICKER_RESULT_CODE=1;
    int PLACE_AUTOCOMPLETE_REQUESTCODE=1;
    GoogleApiClient gClient;
    TextView mapDetails;
    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapDetails= (TextView)findViewById(R.id.map_details);

        findPlaces();

        autocompleteFragment= (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);




    }

    public void findPlaces() {
        PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();

        Intent i;
        try {
            i= builder.build(this);
            startActivityForResult(i,PLACE_PICKER_RESULT_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Place place= PlaceAutocomplete.getPlace(this,data);
                Log.i("onActivityResult",String.valueOf(place.getName()+" "+place.getAddress()));
                mapDetails.setText(place.getAddress());

            // If some error occured
            }else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status= PlaceAutocomplete.getStatus(this,data);
                Log.e("onPlace",status.getStatusMessage());
            } else if (requestCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onPlaceSelected(Place place) {

        mapDetails.setText(place.getAddress());
        Log.i("onPlaceSelected",String.valueOf(place.getName()));

    }
    // If some error occured in Place Auto Complete
    @Override
    public void onError(Status status) {
        Log.d("onError",status.getStatusMessage());
    }
}
