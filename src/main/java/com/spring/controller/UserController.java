package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.exceptions.DuplicatedNameException;
import com.spring.exceptions.InvalidNameException;
import com.spring.model.Language;
import com.spring.model.User;
import com.spring.service.ILanguageService;
import com.spring.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ILanguageService languageService;
	
	// ------------------------------ Views ------------------------------------------
	
	//GET
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		
		try {
			
			List<User> users = userService.findAllUsers();
			
			model.addAttribute("users", users);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("findAllException", "Can't find the users");
		}

		return "user-index";
	}
	
	//GET
	@RequestMapping(value="/newUser", method=RequestMethod.GET)
	public String newUser(Model model) {
		
		try {
			
			List<Language> languages = languageService.findAllLanguages();
			model.addAttribute("languages", languages);
			model.addAttribute("user",new User());
			model.addAttribute("create", true);
			
		} catch (Exception e) {
			
			model.addAttribute("findAllException", "Can't find the languages");
		}
		
		return "user-form";
		
	}
	
	//GET
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@RequestParam("id") Long id, Model model) {
		
		try {
			
			List<Language> languages = languageService.findAllLanguages();
			User user = userService.findUserById(id);
			
			// filter the languages that are not being used by the user
			for (Language userLanguage:user.getLanguages()) {
				
				int index = languages.indexOf(userLanguage);
				languages.remove(index);
			}
			
			model.addAttribute("languages", languages);
			model.addAttribute("user", user);
			model.addAttribute("update", true);
			
		} catch (Exception e) {
			
			model.addAttribute("pageLoadException", "Unexpected error, try again later");
			model.addAttribute("user", new User());
		}

		return "user-form";
		
	}
	
	
	// --------------------------------- Process -----------------------------------
	
	//CREATE / PATCH - POST
	@RequestMapping(value="/persistUser", method=RequestMethod.POST)
	public ModelAndView createNewUser(@ModelAttribute User commingUser, RedirectAttributes redirect, @RequestParam("id") Long id) {	
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/users");
		String url = (commingUser.getId() == null) 
							? "redirect:http://localhost:8080/users/newUser" 
							: "redirect:http://localhost:8080/users/update?id=" + commingUser.getId();
		
		try {
			
			List<Language> userLanguages = commingUser.getLanguages();
			List<Language> languages = new ArrayList<Language>();
			
			if (userLanguages.size() > 0) {
				
				// fetch the languages from the database and set them to the user
				for (Language userLanguage:userLanguages) {
					
					if (userLanguage.getId() != null) {
						
						System.out.println(userLanguage.getName());
						Language language = languageService.findLanguageById(userLanguage.getId());
						
						languages.add(language);
						
					}
					
				}
				
				commingUser.setLanguages(languages);
			}
			
			// create or update
			if (commingUser.getId() == null) {
				
				userService.createNewUser(commingUser);
				
			} else {
				
				User oldUser = userService.findUserById(id);
				
				userService.updateUser(commingUser, oldUser);
			}

			
		} catch (InvalidNameException e) {
			
			modelAndView.setViewName(url);
			redirect.addFlashAttribute("createException", "Invalid username");
			return modelAndView;
			
		} catch(DuplicatedNameException e) {
			
			modelAndView.setViewName(url);
			redirect.addFlashAttribute("createException", "That username is already in use");
			return modelAndView;
			
		} catch (Exception e) {
			
			String errorMessage = (commingUser.getId() == null) 
					? "Can't create the user" 
					: "Can't update the user";
			
			e.printStackTrace();
			modelAndView.setViewName(url);
			redirect.addFlashAttribute("generalException", errorMessage);
			return modelAndView;
		}
		
		return modelAndView;
	}
	
	//DELETE - POST
	@RequestMapping(value="/deleteUserById/{id}", method=RequestMethod.POST)
	public ModelAndView deleteUserById(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/users");
		
		try {
			
			userService.deleteUser(id);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("deleteId", id);
			redirect.addFlashAttribute("deleteException", "Can't delete the user");
		}
		
		
		return modelAndView;
		
	}

}
