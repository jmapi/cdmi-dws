package pw.cdmi.cse.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.cdmi.cse.demo.exception.NotFoundException;
import pw.cdmi.cse.demo.model.User;
import pw.cdmi.cse.demo.repository.UserRepository;

import javax.inject.Inject;
import java.util.Locale;

@RestController
@RequestMapping("/user")
public class UserController {

    @Inject
    private MessageSource messageSource;

    private Locale locale = LocaleContextHolder.getLocale();

    @Inject
    private UserRepository userRepository;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity add(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        User user = userRepository.findOne(id);
        if(user == null){
           throw new NotFoundException(messageSource.getMessage("sys.error.notfound", null, locale));
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().build();
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
    public ResponseEntity list(int page, int size, String orderDirection, String orderField) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(orderDirection), orderField);
        return ResponseEntity.ok(userRepository.findAll(pageRequest));
    }

}
