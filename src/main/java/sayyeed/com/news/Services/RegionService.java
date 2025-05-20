package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.Repositories.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

}
