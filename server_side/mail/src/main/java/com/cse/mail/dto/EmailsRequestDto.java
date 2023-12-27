package com.cse.mail.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EmailsRequestDto implements Serializable {
    private final List<EmailDto> emails;

    private final int numberOfPages;

    public EmailsRequestDto(List<EmailDto> emails, int numberOfPages) {
        this.emails = emails;
        this.numberOfPages = numberOfPages;
    }

    public List<EmailDto> getEmails() {
        return emails;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailsRequestDto)) return false;
        EmailsRequestDto emailsDto = (EmailsRequestDto) o;
        return numberOfPages == emailsDto.numberOfPages && Objects.equals(emails, emailsDto.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emails, numberOfPages);
    }
}
