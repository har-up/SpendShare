package com.spendshare.dao.spendshare.model;

public class Box {
    private String boxAddress;
    private String status;
    private String checkManName;
    private String checkDate;

    public String getBoxAddress() {
        return boxAddress;
    }

    public void setBoxAddress(String boxAddress) {
        this.boxAddress = boxAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckManName() {
        return checkManName;
    }

    public void setCheckManName(String checkManName) {
        this.checkManName = checkManName;
    }

    public String  getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
}

