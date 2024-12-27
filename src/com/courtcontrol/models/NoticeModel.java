/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.models;

/**
 *
 * @author Bibhusan Silwal
 * LMU ID- 23048642
 */
public class NoticeModel {
    private int noticeId;
    private String noticeBy;
    private String noticeDate;
    private String noticeHeading;
    private String notice;

    public NoticeModel(int noticeId, String noticeBy, String noticeDate, String noticeHeading, String notice) {
        this.noticeId = noticeId;
        this.noticeBy = noticeBy;
        this.noticeDate = noticeDate;
        this.noticeHeading = noticeHeading;
        this.notice = notice;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeBy() {
        return noticeBy;
    }

    public void setNoticeBy(String noticeBy) {
        this.noticeBy = noticeBy;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeHeading() {
        return noticeHeading;
    }

    public void setNoticeHeading(String noticeHeading) {
        this.noticeHeading = noticeHeading;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
    
}
