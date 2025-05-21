package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.Repositories.SectionRepository;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

}
