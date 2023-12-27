package com.cse.mail.Builder.DataBaseBuilder;

import java.util.List;

import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.Filter;
import com.cse.mail.dal.model.Folder;
import com.cse.mail.dal.model.User;

public class FolderBuilder {

    private User user;
    private String folderName;

    private boolean isRemovable;
    private boolean isRenamable;
    private List<Email> emails;
    private List<Filter> filters;

    public FolderBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public FolderBuilder setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public FolderBuilder setIsRemovable(boolean isRemovable) {
        this.isRemovable = isRemovable;
        return this;
    }

    public FolderBuilder setIsRenamable(boolean isRenamable) {
        this.isRenamable = isRenamable;
        return this;
    }

    public FolderBuilder setEmails(List<Email> emails) {
        this.emails = emails;
        return this;
    }

    public FolderBuilder setFilters(List<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public Folder build() {
        return new Folder(user, folderName, isRemovable, isRenamable, emails, filters);
    }

}
