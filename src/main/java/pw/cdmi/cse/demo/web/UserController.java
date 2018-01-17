package pw.cdmi.cse.demo.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.cdmi.cse.demo.common.MessageSourceService;
import pw.cdmi.cse.demo.exception.NotFoundException;
import pw.cdmi.cse.demo.model.Result;
import pw.cdmi.cse.demo.model.User;
import pw.cdmi.cse.demo.repository.UserRepository;

import javax.inject.Inject;

@RestController
@RequestMapping("/user")
public class UserController {

    @Inject
    private MessageSourceService messageSourceService;

    @Inject
    private UserRepository userRepository;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        userRepository.save(user);
        return Result.ok(messageSourceService.getMessage("sys.info.addok"));
    }

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        User user = userRepository.findOne(id);
        if(user == null){
           throw new NotFoundException(messageSourceService.getMessage("sys.error.notfound"));
        }
        return user;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        userRepository.delete(id);
        return Result.ok(messageSourceService.getMessage("sys.info.deleteok"));
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping
    public Result update(@RequestBody User user) {
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
    @GetMapping
    public Page<User> list(int page, int size, String orderDirection, String orderField) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(orderDirection), orderField);
        return userRepository.findAll(pageRequest);
    }

}
