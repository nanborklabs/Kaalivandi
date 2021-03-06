package com.kaalivandi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;
import com.kaalivandi.StringValidator;
import com.kaalivandi.UI.CEditText;
import com.kaalivandi.UI.IosLight;
import com.kaalivandi.UI.Iosthin;
import com.kaalivandi.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.name;

/**
 *
 * Created by user on 21-08-2016.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.register_title)
    IosLight mTitle;
    @BindView(R.id.register_sub_title)
    Iosthin mSubTitle;

    @BindView(R.id.register_name_ted)
    TextInputLayout muserNameBox;
    @BindView(R.id.register_password_ted)
    TextInputLayout mPassBox;

    @BindView(R.id.register_phone_ted)
    TextInputLayout mPhoneBox;
    @BindView(R.id.register_email_ted)
    TextInputLayout mEmailBox;
    @BindView(R.id.register_button)
    Button mRegisterButton;


    String mNumber = null;
    ProgressDialog mDialog;

    private static final String TAG = "REGISTER";
    @BindView(R.id.register_signin)
    TextView registerSignin;
    @BindView(R.id.reg_user_icon)
    ImageView regUserIcon;
    @BindView(R.id.reg_mobile_icon)
    ImageView regMobileIcon;
    @BindView(R.id.reg_password_icon)
    ImageView regPasswordIcon;
    @BindView(R.id.reg_mail_icon)
    ImageView regMailIcon;

    private View mView;
    private static RegisterFragment mFragment = null;

    private Context mContext;

    private Registration mCallback;

    private KaalivandRequestQueue mRequestQueue;

    int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
        mDialog = new ProgressDialog(getContext());
        if (getArguments() != null) {
            mNumber = getArguments().getString("Number");
        }
    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        mCallback = (Registration) context;

        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, mView);


        //set Custom Fonts faces to Texts
//        final Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/grand.otf");
//        final Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/fallingsky.otf");
//
//
//        if (tf != null && tf2!=null){
//            mTitle.setTypeface(tf);
//            mSubTitle.setTypeface(tf2);
//            mRegisterButton.setTypeface(tf2);
//        }

        introAnimations();


        if (mNumber != null) {
            try {
                mPhoneBox.getEditText().setText(mNumber);
            } catch (NullPointerException e) {
                mPhoneBox.getEditText().setText("");
            }

        }


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setIndeterminate(true);
                mDialog.setTitle("Welcome");
                mDialog.setMessage("Registering on Kaalivandi Network..");

                 String mPass = mPassBox.getEditText().getText().toString();
                String muser = muserNameBox.getEditText().getText().toString();
                String mPhone = mPhoneBox.getEditText().getText().toString();
                String mEmail = mEmailBox.getEditText().getText().toString();
                muser = muser.trim();
                mPhone = mPhone.trim();
                mEmail = mEmail.trim();
                if (StringValidator.CheckUserName(muser)){
                    //Right  user Name

                    if (StringValidator.checkeEMail(mEmail)){
                        //Right Emai
                        if (StringValidator.checkPhoneNumber(mPhone)){
                            //Right phone Number
                            if (StringValidator.checkPassword(mPass)){
                                    //Right Password
                                register(muser, mPass, mPhone, mEmail);
                            }
                            else {
                                showErrorNumber((CEditText) mPassBox.getEditText());
                            }

                        }else{

                            showErrorNumber((CEditText) mPhoneBox.getEditText());
                        }

                    }else{
                        showErrorNumber((CEditText) mEmailBox.getEditText());
                    }


                }
                else{
                    showErrorNumber((CEditText) muserNameBox.getEditText());
                }


















                    //same password procced to register


            }
        });
        return mView;
    }


    private void showErrorNumber(CEditText v) {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        v.startAnimation(shake);
        v.setText("");


    }
    private void introAnimations() {
        mTitle.setScaleX(0);
        int size = -300;
         size = -Utils.getScreenWidth(getContext());
        regUserIcon.setTranslationX(size);
        regMailIcon.setTranslationX(size);
        regPasswordIcon.setTranslationX(size);
        regMobileIcon.setTranslationX(size);


        mTitle.animate().scaleX(1).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        int versize = Utils.getScreenWidth(getContext());
        muserNameBox.setTranslationX(versize);
        mEmailBox.setTranslationX(versize);
        mPhoneBox.setTranslationX(versize);
        mPassBox.setTranslationX(versize);


        mRegisterButton.setScaleX(0);
        mRegisterButton.setScaleY(0);
        muserNameBox.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(400).start();
        regUserIcon.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(400).start();
        mEmailBox.animate().translationX(0).setInterpolator(new DecelerateInterpolator(1.5f)).setDuration(300).setStartDelay(500).start();
        regMailIcon.animate().translationX(0).setInterpolator(new DecelerateInterpolator(1.5f)).setDuration(300).setStartDelay(500).start();
        mPhoneBox.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(600).start();
        regMobileIcon.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(600).start();
        mPassBox.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(700).start();
        regPasswordIcon.animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).setStartDelay(700).start();
        mRegisterButton.animate().scaleX(1).scaleY(1).setInterpolator(new OvershootInterpolator(2f)).setDuration(300).setStartDelay(1000).start();


    }

    private void register(final String muser, final String mPass, final String mPhone, final String mEmail) {
        final String URl = prepareURL(muser, mPass, mPhone, mEmail);
        Log.d(TAG, "making Request " + URl);
        final StringRequest mRegisterReq = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.equals("\"True\"")) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    if (mCallback != null) {
                        mCallback.registered(muser, mPhone, mEmail);
                    } else {
                        throw new NullPointerException("Activity must implement Method");
                    }

                } else if (response.equals("\"False\"")) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Not Registered");
                    alertDialog.setMessage("Please Enter valid Credentials");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();


                } else {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Not Registered");
                    alertDialog.setMessage("Error occurred , Please try again");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    mCallback.notRegisterered();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (!(count>3)){

                    count++;
                    register(muser, mPass, mPhone, mEmail);
                }
                else{
                    if (mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Not Registered");
                    alertDialog.setMessage("Please Make sure you have an internet Connection");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    mCallback.notRegisterered();
                }

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRegisterReq);
    }

    private String prepareURL(String muser, String mPass, String mPhone, String mEmail) {
        mEmail = mEmail.trim();
        mPass = mPass.trim();
        mPhone = mPhone.trim();
        muser = muser.trim();
        return "http://www.kaalivandi.com/MobileApp/NewRegistration?Number=" + mPhone + "&Email=" + mEmail + "&Name=" + muser + "&Password=" + mPass;

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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public static Fragment newInstance() {
        if (mFragment == null) {
            mFragment = new RegisterFragment();
        } else {
            return mFragment;
        }
        return mFragment;
    }

    @OnClick(R.id.register_signin)
    public void onClick() {
    }

    public interface Registration {
        void registered(String muser, String mPass, String mEmail);

        void notRegisterered();
    }
}
