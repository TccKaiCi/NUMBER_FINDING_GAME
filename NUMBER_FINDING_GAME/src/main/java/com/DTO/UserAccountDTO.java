package com.DTO;

public class UserAccountDTO {
    private String strUid;
    private String strUserName;
    private String strNameInf;
    private String strPassWord;
    private String strGender;
//    dateTime

    public UserAccountDTO() {
    }

    public UserAccountDTO(String strUid, String strUserName, String strNameInf, String strPassWord, String strGender) {
        this.strUid = strUid;
        this.strUserName = strUserName;
        this.strNameInf = strNameInf;
        this.strPassWord = strPassWord;
        this.strGender = strGender;
    }

    public String getStrNameInf() {
        return strNameInf;
    }

    public void setStrNameInf(String strNameInf) {
        this.strNameInf = strNameInf;
    }

    public String getStrUid() {
        return strUid;
    }

    public void setStrUid(String strUid) {
        this.strUid = strUid;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrPassWord() {
        return strPassWord;
    }

    public void setStrPassWord(String strPassWord) {
        this.strPassWord = strPassWord;
    }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }
}
