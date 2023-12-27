package com.cse.mail.dal.model;

import jakarta.persistence.*;

@Entity(name = "Filter")
@Table(name = "filters")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Folder folder;

    @Column(
            name = "filter_mode",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String filterMode;

    @Column(
            name = "filter_data",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String filterData;

    public Filter(Folder folder, String filterMode, String filterData) {
        this.folder = folder;
        this.filterMode = filterMode;
        this.filterData = filterData;
    }

public int getId() {
        return id;
}

public Folder getFolder() {
        return folder;
}

public String getFilterMode() {
        return filterMode;
}

public String getFilterData() {
        return filterData;
}
    
}