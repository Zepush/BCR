package bkit4u.hoai.bcr.Model;

/**
 * Created by hoai on 10/07/2015.
 */
public class SendDataModel
{
    private String goForwardString;
    private String goBackwardString;
    private String turnLeftString;
    private String turnRightString;
    private String pickUpString;
    private String dropDownString;
    private String goUpString;
    private String goDownString;
    private String stopString;

    public SendDataModel()
    {

    }

    public String getGoForwardString()
    {
        return goForwardString;
    }

    public String getGoBackwardString()
    {
        return goBackwardString;
    }

    public String getTurnLeftString()
    {
        return turnLeftString;
    }

    public String getTurnRightString()
    {
        return turnRightString;
    }

    public String getDropDownString()
    {
        return dropDownString;
    }

    public String getPickUpString()
    {
        return pickUpString;
    }

    public String getGoDownString()
    {
        return goDownString;
    }

    public String getGoUpString()
    {
        return goUpString;
    }

    public String getStopString()
    {
        return stopString;
    }

    public void setGoForwardString(String goForwardString)
    {
        this.goForwardString = goForwardString;
    }

    public void setGoBackwardString(String goBackwardString)
    {
        this.goBackwardString = goBackwardString;
    }

    public void setTurnLeftString(String turnLeftString)
    {
        this.turnLeftString = turnLeftString;
    }

    public void setTurnRightString(String turnRightString)
    {
        this.turnRightString = turnRightString;
    }

    public void setDropDownString(String dropDownString)
    {
        this.dropDownString = dropDownString;
    }

    public void setPickUpString(String pickUpString)
    {
        this.pickUpString = pickUpString;
    }

    public void setGoUpString(String goUpString)
    {
        this.goUpString = goUpString;
    }

    public void setGoDownString(String goDownString)
    {
        this.goDownString = goDownString;
    }

    public void setStopString(String stopString)
    {
        this.stopString = stopString;
    }
}
