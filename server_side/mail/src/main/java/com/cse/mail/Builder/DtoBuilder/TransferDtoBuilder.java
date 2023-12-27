package com.cse.mail.Builder.DtoBuilder;

import java.util.List;

import com.cse.mail.dto.TransferDto;

public class TransferDtoBuilder {

    private int folderIdSource;
    private int folderIdDestination;
    private List<Integer> emailIds;

    public TransferDtoBuilder setFolderIdSource(int folderIdSource) {
        this.folderIdSource = folderIdSource;
        return this;
    }

    public TransferDtoBuilder setFolderIdDestination(int folderIdDestination) {
        this.folderIdDestination = folderIdDestination;
        return this;
    }

    public TransferDtoBuilder setEmailIds(List<Integer> emailIds) {
        this.emailIds = emailIds;
        return this;
    }

    public TransferDto build() {

        return new TransferDto(folderIdSource, folderIdDestination, emailIds);
    }

}
