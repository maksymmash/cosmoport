package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipRestController {
    @Autowired
    private ShipService shipService;
    
    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Ship> ships = shipService.getAll();
        if (id > ships.get(ships.size() - 1).getId()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Ship ship = this.shipService.getShipById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        HttpHeaders headers = new HttpHeaders();
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getName() == null || ship.getPlanet() == null || ship.getShipType() == null || ship.getProdDate() == null || ship.getSpeed() == null || ship.getCrewSize() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        if (ship.getName().length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getPlanet().length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getName().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getPlanet().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getSpeed() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        double roundSpeed = Math.round(ship.getSpeed() * 100.0d) / 100.0d;
        if (roundSpeed <= 0.01 || roundSpeed >= 0.99) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ship.setSpeed(roundSpeed);

        if (ship.getCrewSize() <= 1 || ship.getCrewSize() >= 9999) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getProdDate().getTime() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ship.getProdDate().getTime());
        int prodYear = calendar.get(Calendar.YEAR);
        if (prodYear <= 2800 || prodYear >= 3019) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.shipService.save(ship);
        return new ResponseEntity<>(ship, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> updateShip(@RequestBody Ship ship, @PathVariable("id") Long id) {
        boolean updated;
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Ship> ships = shipService.getAll();
        if (id > ships.get(ships.size() - 1).getId()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (ship.getName() == null && ship.getPlanet() == null && ship.getShipType() == null && ship.getProdDate() == null && ship.getSpeed() == null && ship.getCrewSize() == null && ship.getUsed() == null) {
            Ship oldShip = this.shipService.getShipById(id);
            return new ResponseEntity<>(oldShip, HttpStatus.OK);
        }
        if ("".equals(ship.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(ship.getPlanet())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Ship oldShip = this.shipService.getShipById(id);
        if (oldShip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (ship.getName() == null) {
            oldShip.setName(oldShip.getName());
        }
        if (ship.getPlanet() == null) {
            oldShip.setPlanet(oldShip.getPlanet());
        }
        if (ship.getName() != null) {
            oldShip.setName(ship.getName());
        }
        if (ship.getPlanet() != null) {
            oldShip.setPlanet(ship.getPlanet());
        }
        if (ship.getShipType() != null) {
            oldShip.setShipType(ship.getShipType());
        }
        if (ship.getSpeed() != null) {
            oldShip.setSpeed(ship.getSpeed());
        }
        if (ship.getProdDate() != null) {
            oldShip.setProdDate(ship.getProdDate());
        }
        if (ship.getUsed() != null) {
            oldShip.setUsed(ship.getUsed());
        }
        if (ship.getCrewSize() != null) {
            oldShip.setCrewSize(ship.getCrewSize());
        }
        if (ship.getProdDate() != null && ship.getProdDate().getTime() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getCrewSize() != null && (ship.getCrewSize() <= 1 || ship.getCrewSize() >= 9999)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        updated = this.shipService.updateShip(oldShip, id);
        if (updated) {
            return new ResponseEntity<>(oldShip, HttpStatus.OK);
        } else return new ResponseEntity<>(oldShip, HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Ship> ships = shipService.getAll();
        if (id > ships.get(ships.size() - 1).getId()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Ship ship = this.shipService.getShipById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.shipService.deleteShip(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> getAllShips(@RequestParam(required=false) String name, @RequestParam(required=false) String planet, @RequestParam(required=false) ShipType shipType,
                                                  @RequestParam(required=false) Long after, @RequestParam(required=false) Long before, @RequestParam(required = false) Boolean isUsed,
                                                  @RequestParam(required=false) Double minSpeed, @RequestParam(required=false) Double maxSpeed, @RequestParam(required=false) Integer minCrewSize, @RequestParam(required=false) Integer maxCrewSize,
                                                  @RequestParam(required=false) Double minRating, @RequestParam(required=false) Double maxRating, @RequestParam(required=false) ShipOrder order,
                                                  @RequestParam(required=false, defaultValue = "0") Integer pageNumber, @RequestParam(required=false, defaultValue = "3") Integer pageSize) {
        if (order != null){
            String fieldName = order.getFieldName();
            if (fieldName.equals("id")){
                order = ShipOrder.ID;
            }
            if (fieldName.equals("speed")){
                order = ShipOrder.SPEED;
            }
            if (fieldName.equals("prodDate")){
                order = ShipOrder.DATE;
            }
            if (fieldName.equals("rating")){
                order = ShipOrder.RATING;
            }
        } else order = ShipOrder.ID;
        
        List<Ship> ships = this.shipService.getAll(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating,
                order, pageNumber, pageSize);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("/ships/count")
    public ResponseEntity<Integer> getShipCount(@RequestParam(required=false) String name, @RequestParam(required=false) String planet, @RequestParam(required=false) ShipType shipType,
                                                @RequestParam(required=false) Long after, @RequestParam(required=false) Long before, @RequestParam(required = false) Boolean isUsed,
                                                @RequestParam(required=false) Double minSpeed, @RequestParam(required=false) Double maxSpeed, @RequestParam(required=false) Integer minCrewSize,
                                                @RequestParam(required=false) Integer maxCrewSize, @RequestParam(required=false) Double minRating, @RequestParam(required=false) Double maxRating) {
        Integer shipCount = this.shipService.getShipCount(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        if (shipCount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shipCount, HttpStatus.OK);
    }
}