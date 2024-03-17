package ntnu.group03.Bachelor_IDATA2900_AMS;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class WebController {

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("message", "Hello World");
        return "welcome";
    }

}
