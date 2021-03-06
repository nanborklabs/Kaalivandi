package com.kaalivandi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 1/9/16.
 */
public class ForgotPassword extends Fragment {

    private static final String TAG = "FORGOT_PASSWORD";
    private View mView;

    

    
    @BindView(R.id.forgot_phone)
    TextInputLayout mPhoneBox;
    
    @BindView(R.id.forgot_email ) TextInputLayout mEmail;
    @BindView(R.id.forgot_check_button )
    Button mCheckButton;

    @BindView(R.id.forgot_bottomsheet)
    BottomSheetLayout mbottomSheet;

    forgotInterface mCallback;

    KaalivandRequestQueue mRequestQueue;

    ProgressDialog mDialog;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new ProgressDialog(getContext());


        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.forgot_password,container,false);
        ButterKnife.bind(this,mView);

            
            


            //check button
            mCheckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.setMessage("please wait..");
                    mDialog.show();
                    final String mPhone = mPhoneBox.getEditText().getText().toString();
                    final String email = mEmail.getEditText().getText().toString();
                    Vaildate(mPhone,email);
                }
            });



        


        return mView;
    }


    private void Vaildate(String mPhone, String email) {

        String URL  = "http://www.kaalivandi.com/MobileApp/ForgotPassword?Number="+mPhone+"&EmailID="+email;
        final StringRequest mRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mDialog.hide();
                Log.d(TAG, "onResponse: "+response);

                Log.d(TAG, "onResponse: "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mDialog.hide();
                Snackbar sn = Snackbar.make(mView,"Some Problem Occurred ,Please Try again", Snackbar.LENGTH_SHORT);
                sn.show();
            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
       mCallback  = (forgotInterface)context;
        super.onAttach(context);
    }


    public  interface forgotInterface{
        void PasswordReset();
    }
}
