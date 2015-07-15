package bkit4u.hoai.bcr.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import bkit4u.hoai.bcr.AppConstant.AppConstant;
import bkit4u.hoai.bcr.Model.SendDataModel;

/**
 * Created by hoai on 10/07/2015.
 */
public class PreferencesController
{
    Context mContext;
    String fileName = AppConstant.PreferencesFilename;

    public PreferencesController(Context context)
    {
        this.mContext = context;
    }
    public SendDataModel restoringPreferences()
    {
        SendDataModel mModel = new SendDataModel();
        SharedPreferences settingPref = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        mModel.setGoForwardString(settingPref.getString("forward","ffw"));
        mModel.setGoBackwardString(settingPref.getString("backward", "fbw"));
        mModel.setDropDownString(settingPref.getString("dropdown", "fdr"));
        mModel.setPickUpString(settingPref.getString("pickup", "fpu"));
        mModel.setTurnLeftString(settingPref.getString("turnleft", "ftl"));
        mModel.setTurnRightString(settingPref.getString("turnright", "ftr"));
        mModel.setGoUpString(settingPref.getString("goup", "fgu"));
        mModel.setGoDownString(settingPref.getString("godown","fgd"));
        mModel.setStopString(settingPref.getString("stop","fst"));
        return mModel;
    }

    public void savingPreferences(SendDataModel mModel)
    {
        SharedPreferences settingPref = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor =  settingPref.edit();
        mEditor.putString("forward",mModel.getGoForwardString());
        mEditor.putString("backward",mModel.getGoBackwardString());
        mEditor.putString("dropdown",mModel.getDropDownString());
        mEditor.putString("pickup",mModel.getPickUpString());
        mEditor.putString("turnleft",mModel.getTurnLeftString());
        mEditor.putString("turnright",mModel.getTurnRightString());
        mEditor.putString("goup",mModel.getGoUpString());
        mEditor.putString("godown",mModel.getGoDownString());
        mEditor.putString("stop",mModel.getStopString());
        mEditor.commit();
    }
}
