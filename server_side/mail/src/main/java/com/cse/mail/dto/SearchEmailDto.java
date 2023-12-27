package com.cse.mail.dto;


import java.io.Serializable;
import java.util.Objects;

public class SearchEmailDto implements Serializable {
    private final int userId;
    private final int folderId;
    private final String searchType;
    private final String searchFor;
    private final String sortBy;
    private final int pageNumber;
    private final int numberOfElementsInPage;

    public SearchEmailDto(int userId, int folderId, String searchType, String searchFor, String sortBy, int pageNumber, int numberOfElementsInPage) {
        this.userId = userId;
        this.folderId = folderId;
        this.searchType = searchType;
        this.searchFor = searchFor;
        this.sortBy = sortBy;
        this.pageNumber = pageNumber;
        this.numberOfElementsInPage = numberOfElementsInPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchEmailDto)) return false;
        SearchEmailDto that = (SearchEmailDto) o;
        return userId == that.userId && folderId == that.folderId && pageNumber == that.pageNumber && numberOfElementsInPage == that.numberOfElementsInPage && Objects.equals(searchType, that.searchType) && Objects.equals(searchFor, that.searchFor) && Objects.equals(sortBy, that.sortBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, folderId, searchType, searchFor, sortBy, pageNumber, numberOfElementsInPage);
    }

    public int getUserId() {
        return userId;
    }

    public int getFolderId() {
        return folderId;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public String getSortBy() {
        return sortBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getNumberOfElementsInPage() {
        return numberOfElementsInPage;
    }
}