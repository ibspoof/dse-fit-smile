
package com.github.ibspoof.dse.webapp.pojos.onthisday;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "displaytitle",
        "pageid",
        "extract",
        "extract_html",
        "thumbnail",
        "originalimage",
        "lang",
        "dir",
        "timestamp",
        "description",
        "coordinates",
        "normalizedtitle"
})
public class Page {

    @JsonProperty("title")
    private String title;

    @JsonProperty("displaytitle")
    private String displaytitle;

    @JsonProperty("pageid")
    private Integer pageid;

    @JsonProperty("extract")
    private String extract;

    @JsonProperty("extract_html")
    private String extractHtml;

    @JsonProperty("thumbnail")
    private Thumbnail thumbnail;

    @JsonProperty("originalimage")
    private Originalimage originalimage;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("dir")
    private String dir;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @JsonProperty("normalizedtitle")
    private String normalizedtitle;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("displaytitle")
    public String getDisplaytitle() {
        return displaytitle;
    }


    @JsonProperty("displaytitle")
    public void setDisplaytitle(String displaytitle) {
        this.displaytitle = displaytitle;
    }

    @JsonProperty("pageid")
    public Integer getPageid() {
        return pageid;
    }

    @JsonProperty("pageid")
    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    @JsonProperty("extract")
    public String getExtract() {
        return extract;
    }

    @JsonProperty("extract")
    public void setExtract(String extract) {
        this.extract = extract;
    }

    @JsonProperty("extract_html")
    public String getExtractHtml() {
        return extractHtml;
    }

    @JsonProperty("extract_html")
    public void setExtractHtml(String extractHtml) {
        this.extractHtml = extractHtml;
    }

    @JsonProperty("thumbnail")
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @JsonProperty("thumbnail")
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("originalimage")
    public Originalimage getOriginalimage() {
        return originalimage;
    }

    @JsonProperty("originalimage")
    public void setOriginalimage(Originalimage originalimage) {
        this.originalimage = originalimage;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("dir")
    public String getDir() {
        return dir;
    }

    @JsonProperty("dir")
    public void setDir(String dir) {
        this.dir = dir;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty("normalizedtitle")
    public String getNormalizedtitle() {
        return normalizedtitle;
    }

    @JsonProperty("normalizedtitle")
    public void setNormalizedtitle(String normalizedtitle) {
        this.normalizedtitle = normalizedtitle;
    }

}
