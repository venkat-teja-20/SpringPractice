package com.example.demo;



import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Member {
    public int mID;
    public String name;
    public int age;
    public int cur;
    public String email;
    public String privilage;
    static Map<String, Double> currencyRates = new HashMap<>();
    static String[] currencies = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CNY", "INR"};
    static ArrayList<String> currencyList = new ArrayList<>(Arrays.asList(currencies));
    static{
        currencyRates.put("USD", 0.012); // 1 INR = 0.012 USD
        currencyRates.put("EUR", 0.011); // 1 INR = 0.011 EUR
        currencyRates.put("GBP", 0.0098); // 1 INR = 0.0098 GBP
        currencyRates.put("JPY", 1.73); // 1 INR = 1.73 JPY
        currencyRates.put("AUD", 0.019); // 1 INR = 0.019 AUD
        currencyRates.put("CAD", 0.017); // 1 INR = 0.017 CAD
        currencyRates.put("CNY", 0.088); // 1 INR = 0.088 CNY
        currencyRates.put("INR", 1.0); // 1 INR = 1.0 INR (itself)
    }
    @GetMapping(value = "/api/member",produces = "application/json")
    @ResponseBody
    public ArrayList<Member> getData(){
        return Database.data;
    }
    @GetMapping(value = "/api/{member_id}/fetch-balance",produces = "application/json")
    @ResponseBody
    public Object fetchBalance(@PathVariable int mid,@RequestParam String currency){
        if(!currencyList.contains(currency))
            return "Invalid Currency";
        return "Balance "+(Database.data.get(mid).cur*currencyRates.get(currency));
    }
    @PostMapping(value = "/api/enrol-member",produces = "application/json")
    @ResponseBody
    public Object enrolMember(@RequestBody Map<String, Object> req, HttpServletResponse response){
        Member m=new Member();
        m.mID=(int)req.get("id");
        m.name=(String)req.get("name");
        m.age=(int)req.get("age");
        m.cur=(int)req.get("cur");
        m.email=(String)req.get("email");
        m.privilage=(String)req.get("privilage");
        Database.data.add(m);
        response.setStatus(201);
        return "Member Enrolled";
    }
}
