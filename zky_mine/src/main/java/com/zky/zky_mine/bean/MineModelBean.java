package com.zky.zky_mine.bean;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.zky.zky_mine.BR;


/**
 * Created by lk
 * Date 2019-11-11
 * Time 14:41
 * Detail:
 */
public class MineModelBean extends BaseObservable {
    private String modifyPaw;
    private String modifyQrPaw;
    private String oldPaw;
    private String typeLevel;
    private int countLevel;
    private String countState;
    private int state;
    private String tmpS;
    private String tmpX;
    private String tmpC;
    private String tmpSc;
    private String userName;
    private String imageHeard;
    private int errorHeard;
    private String curName;
    private String phone;
    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
        notifyPropertyChanged(BR.curName);
    }

    @Bindable
    public String getImageHeard() {
        return imageHeard;
    }

    public void setImageHeard(String imageHeard) {
        this.imageHeard = imageHeard;
        notifyPropertyChanged(BR.imageHeard);
    }

    public int getErrorHeard() {
        return errorHeard;
    }

    public void setErrorHeard(int errorHeard) {
        this.errorHeard = errorHeard;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public String getTmpS() {
        return tmpS;
    }

    public void setTmpS(String tmpS) {
        this.tmpS = tmpS;
    }

    public String getTmpX() {
        return tmpX;
    }

    public void setTmpX(String tmpX) {
        this.tmpX = tmpX;
    }

    public String getTmpC() {
        return tmpC;
    }

    public void setTmpC(String tmpC) {
        this.tmpC = tmpC;
    }

    public String getTmpSc() {
        return tmpSc;
    }

    public void setTmpSc(String tmpSc) {
        this.tmpSc = tmpSc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        setCountState(getdState(state + ""));
    }

    @Bindable
    public String getCountState() {
        return getdState(state + "");
    }

    public void setCountState(String countState) {
        this.countState = countState;
        notifyPropertyChanged(BR.countState);
    }

    public int getCountLevel() {
        return countLevel;
    }

    public void setCountLevel(int countLevel) {
        this.countLevel = countLevel;
        setTypeLevel(getType(countLevel + ""));
    }

    @Bindable
    public String getModifyPaw() {
        return modifyPaw;
    }

    public void setModifyPaw(String modifyPaw) {
        this.modifyPaw = modifyPaw;
        notifyPropertyChanged(BR.modifyPaw);
    }

    @Bindable
    public String getModifyQrPaw() {
        return modifyQrPaw;
    }

    public void setModifyQrPaw(String modifyQrPaw) {
        this.modifyQrPaw = modifyQrPaw;
        notifyPropertyChanged(BR.modifyQrPaw);

    }

    @Bindable
    public String getOldPaw() {
        return oldPaw;
    }

    public void setOldPaw(String oldPaw) {
        this.oldPaw = oldPaw;
        notifyPropertyChanged(BR.oldPaw);

    }

    @Bindable
    public String getTypeLevel() {
        return getType(countLevel + "");
    }

    public void setTypeLevel(String typeLevel) {
        this.typeLevel = typeLevel;
        notifyPropertyChanged(BR.typeLevel);
    }

    public String getType(String accountLevel) {
        // 0-中央、2-省（自治区）、3-市（自治州）、4-县（区）、5-学校
        String l = "";
        if ("0".equals(accountLevel)) {
            l = "中央";

        } else if ("2".equals(accountLevel)) {
            l = "省（自治区）";
        } else if ("3".equals(accountLevel)) {
            l = "市（自治州）";
        } else if ("4".equals(accountLevel)) {
            l = "县（区）";
        } else if ("5".equals(accountLevel)) {
            l = "学校";
        }
        return l;
    }


    public String getdState(String state) {
        String l = "";
        if ("0".equals(state)) {

            l = "未激活";
        } else {
            l = "激活";
        }
        return l;
    }


}
