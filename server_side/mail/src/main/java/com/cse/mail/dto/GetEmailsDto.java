package com.cse.mail.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GetEmailsDto implements Serializable {
    private final List<PostEmailDto> emails;

    private final int numberOfPages;

    public GetEmailsDto(List<PostEmailDto> emails, int numberOfPages) {
        this.emails = emails;
        this.numberOfPages = numberOfPages;
    }

    public List<PostEmailDto> getEmails() {
        return emails;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetEmailsDto)) return false;
        GetEmailsDto emailsDto = (GetEmailsDto) o;
        return numberOfPages == emailsDto.numberOfPages && Objects.equals(emails, emailsDto.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emails, numberOfPages);
    }
}
