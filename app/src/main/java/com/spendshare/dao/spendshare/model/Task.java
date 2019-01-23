package com.spendshare.dao.spendshare.model;

public class Task {
    private String taskName;
    private String status;
    private String groupName;
    private int boxNum;
    private int progress;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
