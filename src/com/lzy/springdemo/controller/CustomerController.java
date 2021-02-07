package com.lzy.springdemo.controller;

import com.lzy.springdemo.entity.Customer;
import com.lzy.springdemo.service.CustomerService;
import com.lzy.springdemo.util.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("customer")
public class CustomerController {

    // need to inject the customer service
    @Autowired
    private CustomerService customerService;



    @GetMapping("/showFormForAdd")
    public  String showFormForAdd(Model model){

        // create model attribute to bind form data
        Customer customer = new Customer();

        model.addAttribute("customer",customer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer")Customer customer){

        // save the customer using our service
        customerService.saveCustomer(customer);

        return  "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int id, Model model){

        // get the customer from the service
        Customer customer = customerService.getCustomer(id);

        // set customer as a model attribute to pre-populate the form
        model.addAttribute("customer",customer);

        // send over to our form
        return "customer-form";
    }
    @GetMapping("/delete")
    public String showFormForDelete(@RequestParam("customerId")int id,Model model){

        // delete the customer
        customerService.deleteCustomer(id);

        // send over to our form
        return "redirect:/customer/list";

    }
    @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                  Model model){

        // search customers from the service
        List<Customer> customers = customerService.searchCustomers(theSearchName);

        // add the customers to the model
        model.addAttribute("customers",customers);

        return  "list-customers";
    }
    @GetMapping("/list")
    public String listCustomers(Model model,@RequestParam(required = false) String sort){

        List<Customer> customers = null;

        if(sort != null){
            int sortField = Integer.parseInt(sort);
            customers = customerService.getCustomers(sortField);
        }else{
            customers = customerService.getCustomers(SortUtils.LAST_NAME);
        }
        model.addAttribute("customers",customers);
        return "list-customers";

    }

}
