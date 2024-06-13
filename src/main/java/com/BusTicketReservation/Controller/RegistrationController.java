package com.BusTicketReservation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketReservation.Dto.UserRegisteredDto;
import com.BusTicketReservation.Service.UserServiceImpl;

@Controller
public class RegistrationController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    // Endpoint for accessing the registration form page
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        UserRegisteredDto userRegisteredDto = new UserRegisteredDto();
        model.addAttribute("userRegisteredDto", userRegisteredDto);
        return "USER_REGISTER/registerPage";
    }
    
    // Endpoint for saving registration
    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("userRegisteredDto") UserRegisteredDto userRegisteredDto) {
        userServiceImpl.save(userRegisteredDto);
        return "redirect:/login";
    }
}
