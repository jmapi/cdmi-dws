package pw.cdmi.cse.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pw.cdmi.cse.demo.model.Article;

public interface ArticleRepository extends MongoRepository<Article, Long> {


}