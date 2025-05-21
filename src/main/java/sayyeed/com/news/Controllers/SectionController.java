package sayyeed.com.news.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sayyeed.com.news.Services.SectionService;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    private SectionService service;
}
