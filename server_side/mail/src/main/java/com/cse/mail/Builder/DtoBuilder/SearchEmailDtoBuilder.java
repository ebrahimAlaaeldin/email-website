package com.cse.mail.Builder.DtoBuilder;

import com.cse.mail.dto.SearchEmailDto;

public class SearchEmailDtoBuilder {

    private int userId;
    private int folderId;
    private String searchType;
    private String searchFor;
    private String sortBy;
    private int pageNumber;
    private int numberOfElementsInPage;

    public SearchEmailDtoBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public SearchEmailDtoBuilder setFolderId(int folderId) {
        this.folderId = folderId;
        return this;
    }

    public SearchEmailDtoBuilder setSearchType(String searchType) {
        this.searchType = searchType;
        return this;
    }

    public SearchEmailDtoBuilder setSearchFor(String searchFor) {
        this.searchFor = searchFor;
        return this;
    }

    public SearchEmailDtoBuilder setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public SearchEmailDtoBuilder setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public SearchEmailDtoBuilder setNumberOfElementsInPage(int numberOfElementsInPage) {
        this.numberOfElementsInPage = numberOfElementsInPage;
        return this;
    }

    public SearchEmailDto build() {
        return new SearchEmailDto(userId, folderId, searchType, searchFor, sortBy, pageNumber, numberOfElementsInPage);
    }

}
