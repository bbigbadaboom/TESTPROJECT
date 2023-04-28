package Tools;

public enum RestContentType {
    JSON("application/json"),
    Text("text/plain"),
    FORM_DATA("multipart/form-data");

    private String contentType;

    RestContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
