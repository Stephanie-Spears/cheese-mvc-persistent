package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {

        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);

        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

//        // SQL errors unless cheese is removed from menus PRIOR to removing cheese from cheeseDao.
        // TODO: probably a better way to do this, nested for loop instead of two separate loops ->check error, too. previously only final for loop in method

        for (int cheeseId : cheeseIds) {
            Cheese cheese = cheeseDao.findOne(cheeseId);
            List<Menu> menus = cheese.getMenus();
            for (Menu menu : menus) {
                menu.removeItem(cheese);
            }
        }
        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";

    }

    // Display all cheeses by category
    @RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
    public String category (Model model, @PathVariable int categoryId) {

        Category cat = categoryDao.findOne(categoryId);
        List<Cheese> cheeses = cat.getCheeses();

        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheese in Category: " + cat.getName());

        return "cheese/index";
    }

//    // Ability to edit a cheese
//    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
//    public String displayEditForm(Model model, @PathVariable int cheeseId) {
//////        //todo: does instantiating local object in separate method call, then passing to view have a larger overhead? Or is it the same? If same, readability suffers for succinctness *check
//        Cheese cheese = cheeseDao.findOne(cheeseId);
//
//        model.addAttribute("cheese", cheese);
//        model.addAttribute("categories", categoryDao.findAll());
//        model.addAttribute("title", "Edit Cheese " + cheese.getName() + " ID " + cheeseId );
//
//        return "cheese/edit";
//    }
//
//    @RequestMapping(value = "edit", method = {RequestMethod.POST})
//    public String processEditForm(Model model, @ModelAttribute @Valid Cheese cheese, Errors errors,
//                                  @RequestParam int cheeseId, String name, String description, @RequestParam int category) {
    // TODO: pretty sure I can return the object instead of the individual fields, and instantiate that way
//
//        // cheeseID added to model and th:if statements added to edit.html hidden input to prevent cheeseId from being set to 0 if there are errors
//        if (errors.hasErrors()) {
//            model.addAttribute("cheeseID", cheeseId);
//            model.addAttribute("categories", categoryDao.findAll());
//            model.addAttribute("title", "Edit Cheese " + cheese.getName() + " ID " + cheeseId );
//            return "cheese/edit";
//        }
//
//        Category cat = categoryDao.findOne(category);
//        cheeseDao.findOne(cheeseId).setCategory(cat);
//        cheeseDao.findOne(cheeseId).setName(name);
//        cheeseDao.findOne(cheeseId).setDescription(description);
//
//        return "redirect:";
//    }

}
