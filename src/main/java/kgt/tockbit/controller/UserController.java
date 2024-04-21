package kgt.tockbit.controller;

import kgt.tockbit.domain.User;
import kgt.tockbit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/register")
    public String createForm(){
        return "users/createUserForm";
    }

    @PostMapping("/auth/register")
    public String create(UserForm form){
        User user = new User();
        user.setEmail(form.getEmail());

        System.out.println("user = "+ user.getEmail());

        userService.join(user);

        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }


    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }
}