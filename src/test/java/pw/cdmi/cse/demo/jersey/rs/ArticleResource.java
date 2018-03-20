package pw.cdmi.cse.demo.jersey.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pw.cdmi.cse.demo.model.Article;
import pw.cdmi.cse.demo.model.Result;
import pw.cdmi.cse.demo.model.User;
import pw.cdmi.cse.demo.repository.ArticleRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/article")
public class ArticleResource {
    @Autowired
    private ArticleRepository articleRepository;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Result add(Article article) {
        articleRepository.save(article);
        return Result.ok("添加成功");
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Result update(Article article) {
        articleRepository.save(article);
        return Result.ok("更新成功");
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Result delete(@PathParam("id") Long id) {
        articleRepository.delete(id);
        return Result.ok("删除成功");
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Article get(@PathParam("id") Long id) {
        return articleRepository.findOne(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Page<Article> list(@QueryParam("page") int page,
                           @QueryParam("size") int size,
                           @QueryParam("orderDirection") String orderDirection,
                           @QueryParam("orderField") String orderField) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(orderDirection), orderField);
        return articleRepository.findAll(pageRequest);
    }
}
