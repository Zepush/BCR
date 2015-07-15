package bkit4u.hoai.bcr.View.Fragment;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import bkit4u.hoai.bcr.Controller.PreferencesController;
import bkit4u.hoai.bcr.Model.SendDataModel;
import bkit4u.hoai.bcr.R;

/**
 * Created by hoai on 10/07/2015.
 */
public class PopupSettingFragment extends DialogFragment
{
    private static PopupSettingFragment ourInstance;
    PreferencesController mController;
    CompletedSettingsCallback mCallback;
    SendDataModel mData;
    public static PopupSettingFragment getInstance()
    {
        if(ourInstance == null)
        {
            ourInstance = new PopupSettingFragment();
        }

        return ourInstance;
    }

    private PopupSettingFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View rootView = inflater.inflate(R.layout.settings_dialog_layout, container,
                false);

        final EditText editForward = (EditText)rootView.findViewById(R.id.editFW);
        final EditText editBackward = (EditText)rootView.findViewById(R.id.editBW);
        final EditText editTurnLeft = (EditText)rootView.findViewById(R.id.editTL);
        final EditText editTurnRight = (EditText)rootView.findViewById(R.id.editTR);
        final EditText editDropDown = (EditText)rootView.findViewById(R.id.editDD);
        final EditText editPickup = (EditText)rootView.findViewById(R.id.editPU);
        final EditText editStop = (EditText)rootView.findViewById(R.id.editST);
        final EditText editGoUp = (EditText)rootView.findViewById(R.id.editGU);
        final EditText editGoDown = (EditText)rootView.findViewById(R.id.editGD);

        mController = new PreferencesController(getActivity());
        mData = mController.restoringPreferences();
        editForward.setText(mData.getGoForwardString());
        editBackward.setText(mData.getGoBackwardString());
        editTurnLeft.setText(mData.getTurnLeftString());
        editTurnRight.setText(mData.getTurnRightString());
        editDropDown.setText(mData.getDropDownString());
        editPickup.setText(mData.getPickUpString());
        editStop.setText(mData.getStopString());
        editGoUp.setText(mData.getGoUpString());
        editGoDown.setText(mData.getGoDownString());

        Button mSetUpBtn = (Button)rootView.findViewById(R.id.dialog_setup_btn);

        final DialogFragment targetDialog = this;
        mSetUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mData = new SendDataModel();
                mData.setGoForwardString(editForward.getText().toString());
                mData.setGoBackwardString(editBackward.getText().toString());
                mData.setTurnRightString(editTurnRight.getText().toString());
                mData.setTurnLeftString(editTurnLeft.getText().toString());
                mData.setStopString(editStop.getText().toString());
                mData.setGoUpString(editGoUp.getText().toString());
                mData.setGoDownString(editGoDown.getText().toString());
                mData.setPickUpString(editPickup.getText().toString());
                mData.setDropDownString(editDropDown.getText().toString());

                mController.savingPreferences(mData);
                if(mCallback != null)
                {
                    mCallback.onCompletedSettingsAndGetSendDataModel(mData);
                }
                targetDialog.dismiss();

            }
        });

        ourInstance = this;
        return rootView;
    }

    public void setCompletedSettingsCallback(CompletedSettingsCallback callback)
    {
        this.mCallback = callback;
    }
}
