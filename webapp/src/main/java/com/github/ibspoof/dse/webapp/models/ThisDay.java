package com.github.ibspoof.dse.webapp.models;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ibspoof.dse.webapp.serializers.BlobToPage;

import java.nio.ByteBuffer;

@Table(name = "this_day",
        readConsistency = "LOCAL_QUORUM",
        writeConsistency = "LOCAL_QUORUM"
)
public class ThisDay {

    @Column(name = "title")
    private String title;

    @ClusteringColumn
    @Column(name = "year")
    private Integer year;

    @PartitionKey
    @Column(name = "date")
    private String date;

    @Column(name = "pages")
    @JsonSerialize(using = BlobToPage.class)
    private ByteBuffer pages;

    public ByteBuffer getPages() {
        return pages;
    }

    public void setPages(byte[] pages) {
        this.pages = ByteBuffer.wrap(pages);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}