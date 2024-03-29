package ntnu.group03.idata2900.ams.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class WebController {
    @GetMapping("/")
    @PreAuthorize("hasAuthority(T(ntnu.group03.idata2900.ams.util.SecurityAccessUtil).ADMIN)")
    public String index() {
        return "welcome";
    }

}
