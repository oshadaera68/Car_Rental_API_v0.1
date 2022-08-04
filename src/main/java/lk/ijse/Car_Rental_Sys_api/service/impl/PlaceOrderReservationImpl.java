package lk.ijse.Car_Rental_Sys_api.service.impl;

import lk.ijse.Car_Rental_Sys_api.dto.ReservationDto;
import lk.ijse.Car_Rental_Sys_api.entity.Reservation;
import lk.ijse.Car_Rental_Sys_api.repo.CarRepo;
import lk.ijse.Car_Rental_Sys_api.repo.RentRepo;
import lk.ijse.Car_Rental_Sys_api.repo.ReservationRepo;
import lk.ijse.Car_Rental_Sys_api.service.PlaceOrderReservationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlaceOrderReservationImpl implements PlaceOrderReservationService {

    @Autowired
    private ReservationRepo repo;

    @Autowired
    private RentRepo rentRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private ModelMapper map1;

    @Override
    public void saveReservation(ReservationDto dto) {
        Reservation reservation = map1.map(dto, Reservation.class);
        if (!repo.existsById(dto.getResId())) {
            repo.save(map1.map(dto, Reservation.class));
            if (dto.getRentDtoList().size() < 1) {
                throw new RuntimeException("No cars added for the order...! please add car for the ordering process..!");
            }
        } else {
            throw new RuntimeException("This reservation id is already exists....!!!");
        }
    }


    @Override
    public void updateReservation(ReservationDto dto) {
        /*if (repo.existsById(dto.getResId())) {
            repo.save(map1.map(dto, Reservation.class));
        } else {
            throw new RuntimeException("This Driver id is already exists....!!!");
        }*/
    }

    @Override
    public void deleteReservation(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Empty Driver...!");
        }
    }

    @Override
    public ReservationDto searchReservation(String id) {
        if (repo.existsById(id)) {
            return map1.map(repo.findById(id).get(), ReservationDto.class);
        } else {
            throw new RuntimeException("No Driver For " + id + " ..!");
        }
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return map1.map(repo.findAll(), new TypeToken<List<ReservationDto>>() {
        }.getType());
    }
}