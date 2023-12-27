package com.cse.mail.Builder.DtoBuilder;

import java.util.List;

import com.cse.mail.dto.EmailDto;
import com.cse.mail.dto.EmailsRequestDto;

public class EmailRequestDtoBuilder {
    private List<EmailDto> emails;
    private int numberOfPages;

    public EmailRequestDtoBuilder setEmails(List<EmailDto> emails) {
        this.emails = emails;
        return this;
    }

    public EmailRequestDtoBuilder setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public EmailsRequestDto build() {

        return new EmailsRequestDto(emails, numberOfPages);
    }

}
