package ObjectsForTests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Objects;

@JsonAutoDetect
public class Posts {
    private Integer id;
    private String title;
    private String body;
    private Integer userId;

    public Posts(String title, String body, Integer userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getUserId() {
        return userId;
    }

    public Posts(Integer id, String title, String body, Integer userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Posts() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posts posts = (Posts) o;
        return id.equals(posts.id) && title.equals(posts.title) && body.equals(posts.body) && userId.equals(posts.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, userId);
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", title:'" + title + '\'' +
                ", body:'" + body + '\'' +
                ", userId:" + userId +
                '}';
    }
}
