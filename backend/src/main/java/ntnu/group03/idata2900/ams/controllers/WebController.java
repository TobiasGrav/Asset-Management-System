package ntnu.group03.idata2900.ams.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class WebController {

    @GetMapping("/")
    public String index() {
        return "welcome";
    }

}
