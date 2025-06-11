package sayyeed.com.news.repositories.profile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sayyeed.com.news.dtos.FilterResultDTO;
import sayyeed.com.news.dtos.profile.ProfileFilterDTO;
import sayyeed.com.news.entities.profile.ProfileEntity;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileFilterRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ProfileEntity> filter (ProfileFilterDTO filterDTO, int page, int size){

        StringBuilder condition = new StringBuilder("where visible = true");
        Map<String, Object> params = new HashMap<>();

        // filter by name/surname/username
        if (filterDTO.getQuery() != null) {
            condition.append(" and (lower(p.name) like :query or lower(p.surname) like :query or lower(p.username) like :query) ");
            params.put("query", "%"+filterDTO.getQuery().toLowerCase()+"%");
        }

        // filter by role
        if (filterDTO.getRole() != null) {
            condition.append(" and pr.role =:role ");
            params.put("role",  filterDTO.getRole());
        }

        // filter by createDateFrom/createdDateTo
        if (filterDTO.getCreatedDateFrom() != null && filterDTO.getCreatedDateTo() != null){
            condition.append(" and p.createdDate between :fromDate and :toDate ");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
            params.put("toDate", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }else if (filterDTO.getCreatedDateFrom() != null){
            condition.append(" and p.createdDate >= :fromDate ");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }else if (filterDTO.getCreatedDateTo() != null){
            condition.append(" and p.createdDate <= :fromDate ");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }

        // filter by status
        if (filterDTO.getStatus() != null){
            condition.append(" and p.status = :status");
            params.put("status", filterDTO.getStatus());
        }

        StringBuilder selectBuilder = new StringBuilder("Select  p From ProfileEntity p ");
        StringBuilder countBuilder = new StringBuilder("Select Count (*) From ProfileEntity p ");
        if (filterDTO.getRole() != null) {
            selectBuilder.append(" inner join p.roleList as pr ");
            countBuilder.append(" inner join p.roleList as pr ");
        }

        selectBuilder.append(condition);
        countBuilder.append(condition);

        // Ordering by createdDate
        selectBuilder.append(" order by p.createdDate desc");

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(page * size);
        List<ProfileEntity> profileEntityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();


        return new FilterResultDTO<>(profileEntityList, totalCount);
    }
}