package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    ShipRepository shipRepository;
    Specification<Ship> specification;

    @Override
    public List<Ship> getAll(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                             Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order, Integer pageNumber, Integer pageSize) {
        specification = new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!StringUtils.isEmpty(name)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (!StringUtils.isEmpty(planet)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("planet"), "%" + planet + "%"));
                }
                if (Objects.nonNull(shipType)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("shipType"), shipType));
                }
                if (Objects.nonNull(after) && Objects.nonNull(before) && after <= before) {
                    Date afterDate = new Date(after);
                    Date beforeDate = new Date(before);
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("prodDate"), afterDate, beforeDate));
                }
                if (Objects.nonNull(isUsed)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("isUsed"), isUsed));
                }
                if (Objects.nonNull(minSpeed) && Objects.nonNull(maxSpeed) && minSpeed <= maxSpeed){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed));
                }
                if (Objects.nonNull(minCrewSize) && Objects.nonNull(maxCrewSize) && minCrewSize <= maxCrewSize){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize));
                }
                if (Objects.nonNull(minRating) && Objects.nonNull(maxRating) && minRating <= maxRating){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), minRating, maxRating));
                }
                if (Objects.nonNull(maxSpeed)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), Double.MIN_VALUE, maxSpeed));
                }
                if (Objects.nonNull(maxCrewSize)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), Integer.MIN_VALUE, maxCrewSize));
                }
                if (Objects.nonNull(maxRating)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), Double.MIN_VALUE, maxRating));
                }
                if (Objects.nonNull(minSpeed)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), minSpeed, Double.MAX_VALUE));
                }
                if (Objects.nonNull(minCrewSize)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), minCrewSize, Integer.MAX_VALUE));
                }
                if (Objects.nonNull(minRating)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), minRating, Double.MAX_VALUE));
                }
                return p;
            }
        };
        List<Ship> ships = shipRepository.findAll(specification, PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()))).getContent();
        return ships;
    }

    @Override
    public Integer getShipCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        specification = new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!StringUtils.isEmpty(name)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (!StringUtils.isEmpty(planet)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("planet"), "%" + planet + "%"));
                }
                if (Objects.nonNull(shipType)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("shipType"), shipType));
                }
                if (Objects.nonNull(after) && Objects.nonNull(before) && after <= before) {
                    Date afterDate = new Date(after);
                    Date beforeDate = new Date(before);
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("prodDate"), afterDate, beforeDate));
                }
                if (Objects.nonNull(isUsed)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("isUsed"), isUsed));
                }
                if (Objects.nonNull(minSpeed) && Objects.nonNull(maxSpeed) && minSpeed <= maxSpeed){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed));
                }
                if (Objects.nonNull(minCrewSize) && Objects.nonNull(maxCrewSize) && minCrewSize <= maxCrewSize){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize));
                }
                if (Objects.nonNull(minRating) && Objects.nonNull(maxRating) && minRating <= maxRating){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), minRating, maxRating));
                }
                if (Objects.nonNull(maxSpeed)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), Double.MIN_VALUE, maxSpeed));
                }
                if (Objects.nonNull(maxCrewSize)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), Integer.MIN_VALUE, maxCrewSize));
                }
                if (Objects.nonNull(maxRating)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), Double.MIN_VALUE, maxRating));
                }
                if (Objects.nonNull(minSpeed)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("speed"), minSpeed, Double.MAX_VALUE));
                }
                if (Objects.nonNull(minCrewSize)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("crewSize"), minCrewSize, Integer.MAX_VALUE));
                }
                if (Objects.nonNull(minRating)){
                    p = criteriaBuilder.and(p, criteriaBuilder.between(root.get("rating"), minRating, Double.MAX_VALUE));
                }
                return p;
            }
        };
        return (int) shipRepository.count(specification);
    }

    @Override
    public List<Ship> getAll() {
        return shipRepository.findAll();
    }

    public void save(Ship ship) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ship.getProdDate().getTime());
        int prodYear = calendar.get(Calendar.YEAR);
        double rating;
        if (!ship.getUsed()) {
            rating = Math.round(((80 * ship.getSpeed() * 1) / (3019 - prodYear + 1)) * 100.0d) / 100.0d;
        } else rating = Math.round(((80 * ship.getSpeed() * 0.5) / (3019 - prodYear + 1)) * 100.0d) / 100.0d;
        ship.setRating(rating);
        shipRepository.save(ship);
    }

    @Override
    public Ship getShipById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateShip(Ship ship, Long id) {
        double roundSpeed = Math.round(ship.getSpeed() * 100.0d) / 100.0d;
        ship.setSpeed(roundSpeed);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ship.getProdDate().getTime());
        int prodYear = calendar.get(Calendar.YEAR);
        double rating;
        if (!ship.getUsed()) {
            rating = Math.round(((80 * ship.getSpeed() * 1) / (3019 - prodYear + 1)) * 100.0d) / 100.0d;
        } else rating = Math.round(((80 * ship.getSpeed() * 0.5) / (3019 - prodYear + 1)) * 100.0d) / 100.0d;

        ship.setRating(rating);
        if (shipRepository.existsById(id)) {
            shipRepository.save(ship);
            return true;
        }
        return false;
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }
}
