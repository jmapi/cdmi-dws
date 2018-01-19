package pw.cdmi.cse.demo.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Article {
    @Id
    private Long id;
    private String title;
    private String content;
}
