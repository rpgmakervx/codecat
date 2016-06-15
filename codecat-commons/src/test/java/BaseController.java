/**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午5:15
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :
 * Created by YangZH on 16-6-15
 * 下午5:15
 */
@RestController
@EnableAutoConfiguration
public class BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(BaseController.class, args);
    }
}
