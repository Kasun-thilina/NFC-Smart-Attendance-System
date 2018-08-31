package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kasuncreations.loginmgmt.Listener;
import com.kasuncreations.loginmgmt.MainActivity;
import com.kasuncreations.loginmgmt.MainNFCActivity;
import com.kasuncreations.loginmgmt.R;
import com.kasuncreations.loginmgmt.RegisterActivity;
import com.kasuncreations.loginmgmt.UserAreaActivity;
import com.kasuncreations.loginmgmt.variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NFCReadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NFCReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NFCReadFragment extends DialogFragment {


    public static final String TAG = NFCReadFragment.class.getSimpleName();
    public String[] myDataFromActivity;



    public static NFCReadFragment newInstance() {

        return new NFCReadFragment();
    }

    private TextView mTvMessage;
    private TextView mTvMessagedef;
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MainNFCActivity activity = (MainNFCActivity) getActivity();
         myDataFromActivity= activity.getMyData();

        View view = inflater.inflate(R.layout.fragment_nfcread,container,false);
        initViews(view);
        return view;



    }



    private void initViews(View view) {

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvMessagedef = (TextView) view.findViewById(R.id.tv_message_def);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MainNFCActivity)context;
        mListener.onDialogDisplayed();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onDialogDismissed();
    }

    public void onNfcDetected(Ndef ndef){

        readFromNFC(ndef);
    }


    private void readFromNFC(Ndef ndef) {
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: "+message);
            if(message.equals("in"))
            {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = "http://nfcappnibm.mywebcommunity.org/attendance.php";

                String username=myDataFromActivity[0];
                String date=myDataFromActivity[1];
                String time=myDataFromActivity[2];
                mTvMessage.setText("Attendance marked for user: "+username);
                mTvMessagedef.setText("Date: "+date+"      Time: "+time);

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {


                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();

                                } else if (error instanceof AuthFailureError) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NetworkError) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name",username );
                        params.put("date",date );
                        params.put("time",time );
                        params.put("subject","null" );

                        return params;
                    }
                };
                queue.add(postRequest);

            }
            else
            {
                mTvMessage.setText("Login Failed");
            }

            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();

        }


    }










/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NFCReadFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NFCReadFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static NFCReadFragment newInstance(String param1, String param2) {
        NFCReadFragment fragment = new NFCReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfcread, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
