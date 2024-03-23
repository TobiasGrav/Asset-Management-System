package ntnu.group03.idata2900.ams.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class WebController {

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String index() {
        return "welcome";
    }

}
