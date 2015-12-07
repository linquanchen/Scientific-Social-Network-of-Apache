package models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by stain on 12/6/2015.
 */
public class PMessage
{
    public String fromUserMail;

    public String getFromUserMail() {
        return fromUserMail;
    }

    public void setFromUserMail(String fromUserMail) {
        this.fromUserMail = fromUserMail;
    }

    public String getToUserMail() {
        return toUserMail;
    }

    public void setToUserMail(String toUserMail) {
        this.toUserMail = toUserMail;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getMailDate() {
        return mailDate;
    }

    public void setMailDate(String mailDate) {
        this.mailDate = mailDate;
    }

    public boolean isReadstatus() {
        return readstatus;
    }

    public void setReadstatus(boolean readstatus) {
        this.readstatus = readstatus;
    }

    public String toUserMail;
    public String mailTitle;
    public String mailContent;
    public String mailDate;
    public boolean readstatus;
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PMessage(){
    }
    public PMessage(JsonNode node) {
        if (node.get("id")!=null) id = node.get("id").asLong();
        if (node.get("fromUserMail")!=null) fromUserMail = node.get("fromUserMail").asText();
        if (node.get("toUserMail")!=null) toUserMail = node.get("toUserMail").asText();
        if (node.get("mailTitle")!=null) mailTitle = node.get("mailTitle").asText();
        if (node.get("mailContent")!=null) mailContent = node.get("mailContent").asText();
        if (node.get("mailDate")!=null) mailDate = node.get("mailDate").asText();
        if (node.get("readStatus")!=null) readstatus = node.get("readStatus").asBoolean();
    }

}