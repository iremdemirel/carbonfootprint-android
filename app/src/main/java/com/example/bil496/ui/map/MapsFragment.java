package com.example.bil496.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bil496.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, TaskLoadedCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    boolean firstEntry = true;
    Marker locationMarker = null;
    Marker selectedMarker = null;
    private Polyline currentPolyline;
    public GoogleMap googleMapUni = null;
    public boolean onRoute = false;
    public Fragment thisFragment = this;
    public ArrayList<Marker> markerList = null;
    DatabaseReference reference;
    public String currentUserID;
    public long userScore = 0;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            reference = FirebaseDatabase.getInstance().getReference();
            currentUserID = FirebaseAuth.getInstance().getUid();

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            googleMapUni = googleMap;
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                public LatLng lastLoc = null;

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    if (locationMarker != null) {
                        locationMarker.remove();
                    }
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    locationMarker = googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                    if (onRoute && selectedMarker != null && lastLoc.longitude != userLocation.longitude && lastLoc.latitude != userLocation.latitude) {
                        String urlString = getUrl(locationMarker.getPosition(), selectedMarker.getPosition(), "driving");
                        new FetchURL(getContext(), thisFragment
                        ).execute(urlString, "driving");
                        double sum = Math.abs(selectedMarker.getPosition().latitude - locationMarker.getPosition().latitude);
                        sum += Math.abs(selectedMarker.getPosition().longitude - locationMarker.getPosition().longitude);
                        if (sum < 0.002) {
                            currentPolyline.remove();
                            onRoute = false;
                            reference.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("score").setValue(userScore + 10);

                            // karbon ayakizi düşür
                        }
                    }
                    lastLoc = userLocation;
                    if (firstEntry) {
                        Marker closestMarker = markerList.get(0);
                        double closestDistance = 10000;
                        for (int i = 0; i < markerList.size(); i++) {
                            double sum = Math.abs(lastLoc.latitude - (markerList.get(i).getPosition().latitude));
                            sum += Math.abs(lastLoc.longitude - (markerList.get(i).getPosition().longitude));
                            if (closestDistance > sum) {
                                closestMarker = markerList.get(i);
                                closestDistance = sum;
                            }
                        }
                        String urlString = getUrl(locationMarker.getPosition(), closestMarker.getPosition(), "driving");
                        new FetchURL(getContext(), thisFragment
                        ).execute(urlString, "driving");
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                        firstEntry = false;
                        onRoute = true;
                        selectedMarker = closestMarker;
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            };

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
            parseXML(googleMap);
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            googleMap.setOnMarkerClickListener(marker -> onMarkerClick(marker));
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = reference.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("score");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userScore = (long) snapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void parseXML(GoogleMap googleMap) {
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getAssets().open("data.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = documentBuilder.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Element element = document.getDocumentElement();
        element.normalize();
        markerList = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("Placemark");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String name = element1.getElementsByTagName("name").item(0).getTextContent();
                String coordinates = ((Element) element1.getElementsByTagName("Point").item(0)).getElementsByTagName("coordinates").item(0).getTextContent();
                LatLng recycleLoc = new LatLng(Double.parseDouble(coordinates.substring(coordinates.indexOf(",") + 1, coordinates.lastIndexOf(",")))
                        , Double.parseDouble(coordinates.substring(0, coordinates.indexOf(","))));
                markerList.add(googleMap.addMarker(new MarkerOptions().position(recycleLoc).title(name)));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getActivity(), "My Latitude: " + marker.getPosition(), Toast.LENGTH_SHORT).show();
        List<LatLng> path = new ArrayList();
        String urlString = getUrl(locationMarker.getPosition(), marker.getPosition(), "driving");
        new FetchURL(getContext(), this
        ).execute(urlString, "driving");
        onRoute = true;
        selectedMarker = marker;
        return false;
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                if (firstEntry) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                    firstEntry = false;
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        parseXML(googleMap);
        //LatLng sydney = new LatLng(-34, 151);
        //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setOnMarkerClickListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
        MapsFragment.super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = googleMapUni.addPolyline((PolylineOptions) values[0]);
    }

    public boolean compareLocation(LatLng l1, LatLng l2) {
        if (l1.latitude == l2.latitude) {
            if (l1.longitude == l2.longitude) {
                return true;
            }
        }
        return false;
    }
}