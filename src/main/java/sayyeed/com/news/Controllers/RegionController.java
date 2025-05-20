package sayyeed.com.news.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sayyeed.com.news.Services.RegionService;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService service;

}
