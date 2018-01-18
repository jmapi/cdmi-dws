package pw.cdmi.cse.demo.jersey.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pw.cdmi.cse.demo.model.Result;
import pw.cdmi.cse.demo.model.CartItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/cart")
public class CartResource {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Result addToCart(CartItem cartItem) {
        redisTemplate.opsForList().rightPush("CART_"+cartItem.getCustomerId(), cartItem);
        return Result.ok("添加购物车成功");
    }

    @GET
    @Path("{customerId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Object> get(@PathParam("customerId") String customerId) {
        long size = redisTemplate.opsForList().size("CART_"+customerId);
        return redisTemplate.opsForList().range("CART_"+customerId, 0, size);
    }
}
