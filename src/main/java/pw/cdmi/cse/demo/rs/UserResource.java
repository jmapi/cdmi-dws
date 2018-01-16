package pw.cdmi.cse.demo.rs;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
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

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") Long id) {
        User user = userRepository.findOne(id);
        if(user == null){
            throw new NotFoundException("用户不存在");
        }
        return Response.ok(user).build();
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(User user) {
        userRepository.save(user);
        return Response.ok().build();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
        return Response.ok().build();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(User user) {
        if(!userRepository.exists(user.getId())){
            throw new NotFoundException();
        }
        userRepository.save(user);
        return Response.ok().build();
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
    public Response list(@QueryParam("page") int page,
                           @QueryParam("size") int size,
                           @QueryParam("orderDirection") String orderDirection,
                           @QueryParam("orderField") String orderField) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(orderDirection), orderField);
        return Response.ok(userRepository.findAll(pageRequest)).build();
    }

}
