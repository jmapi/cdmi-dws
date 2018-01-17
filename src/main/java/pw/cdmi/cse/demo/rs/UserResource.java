package pw.cdmi.cse.demo.rs;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pw.cdmi.cse.demo.common.MessageSourceService;
import pw.cdmi.cse.demo.model.Result;
import pw.cdmi.cse.demo.model.User;
import pw.cdmi.cse.demo.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/user")
public class UserResource {
    @Inject
    private UserRepository userRepository;
    @Inject
    private MessageSourceService messageSourceService;
    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public User get(@PathParam("id") Long id) {
        User user = userRepository.findOne(id);
        if(user == null){
            throw new NotFoundException(messageSourceService.getMessage("sys.error.notfound"));
        }
        return user;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Result add(User user) {
        userRepository.save(user);
        return Result.ok(messageSourceService.getMessage("sys.info.addok"));
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Result delete(@PathParam("id") Long id) {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
        return Result.ok(messageSourceService.getMessage("sys.info.deleteok"));
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Result update(User user) {
        if(!userRepository.exists(user.getId())){
            throw new NotFoundException();
        }
        userRepository.save(user);
        return Result.ok(messageSourceService.getMessage("sys.info.updateok"));
    }

    /**
     * 用户列表
     * @param page
     * @param size
     * @param orderDirection
     * @param orderField
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Page<User> list(@QueryParam("page") int page,
                           @QueryParam("size") int size,
                           @QueryParam("orderDirection") String orderDirection,
                           @QueryParam("orderField") String orderField) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(orderDirection), orderField);
        return userRepository.findAll(pageRequest);
    }

}
