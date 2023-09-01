package com.project.pppreclamationmicroservice.Controllers;

import com.project.pppreclamationmicroservice.Responses.BarResponse;
import com.project.pppreclamationmicroservice.Responses.PieResponse;
import com.project.pppreclamationmicroservice.services.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private DashboardService dashboardService;

    //admin
    /*@GetMapping("/nbClaimByStatus")
    public PieResponse pieClaim() {
        return  dashboardService.pieClaim();
    }*/
    @GetMapping("/claim/admin")
    public PieResponse pieReclamation() {
        return  dashboardService.pieReclamation();
    }
    //admin

    /*@GetMapping("/nbClaimByMonth")
    public BarResponse getClaims() {
        return  dashboardService.getClaims();}*/
    @GetMapping("/claims")
    public BarResponse getClaims() {
        return  dashboardService.getClaims();
    }

    //admin

    @GetMapping("/nbClaimByStatusAndUsername/{username}")
    public PieResponse getNbClaimByStatusAndUsername(@PathVariable String username) {
        return  dashboardService.pieClaimByStatusAndUsername(username);
    }
    //admin
    @GetMapping("/getNbClaimsByType")
    public PieResponse getNbClaimsByType() {
        return  dashboardService.getClaimsByType();
    }


}