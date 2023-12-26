package com.cse.mail.dto;

import java.util.List;
import java.util.Objects;

public class TransferDto {
    private int folderIdSource;
    private int folderIdDestination;
    private List<Integer> emailIds;

    public TransferDto(int folderIdSource, int folderIdDestination, List<Integer> emailIds) {
        this.folderIdSource = folderIdSource;
        this.folderIdDestination = folderIdDestination;
        this.emailIds = emailIds;
    }


    public int getFolderIdSource() {
        return folderIdSource;
    }

    public void setFolderIdSource(int folderIdSource) {
        this.folderIdSource = folderIdSource;
    }

    public int getFolderIdDestination() {
        return folderIdDestination;
    }

    public void setFolderIdDestination(int folderIdDestination) {
        this.folderIdDestination = folderIdDestination;
    }

    public List<Integer> getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(List<Integer> emailIds) {
        this.emailIds = emailIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferDto)) return false;
        TransferDto that = (TransferDto) o;
        return folderIdSource == that.folderIdSource && folderIdDestination == that.folderIdDestination && Objects.equals(emailIds, that.emailIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folderIdSource, folderIdDestination, emailIds);
    }
}