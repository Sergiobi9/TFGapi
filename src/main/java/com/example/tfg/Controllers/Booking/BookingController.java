package com.example.tfg.Controllers.Booking;

import com.example.tfg.Entities.Booking.Booking;
import com.example.tfg.Entities.Booking.RegisterBooking;
import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Booking.BookingRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @PostMapping("/create")
    public ResponseEntity registerBooking(@RequestBody RegisterBooking booking) {
        Map<Object, Object> model = new HashMap<>();

        String concertId = booking.getConcertId();
        Concert concert = concertRepository.findConcertById(concertId);
        if (concert == null){
            model.put(ResponseInfo.INFO, ResponseInfo.CONCERT_DOES_NOT_EXIST);
            return new ResponseEntity(model, HttpStatus.valueOf(200));
        }

        int numberAssistants = concert.getNumberAssistants();
        int userBookings = booking.getBookings();
        int totalConcertBookings = bookingRepository.findAllByConcertId(concertId).size();

        int concertBookingsUpdated = totalConcertBookings + userBookings;
        if (concertBookingsUpdated < numberAssistants){
            model.put(ResponseInfo.INFO, ResponseInfo.BOOKING_EXCEEDED);
            return new ResponseEntity(model, HttpStatus.valueOf(200));
        }

        for (int i = 0; i < booking.getBookings(); i++){
            Booking bookingToRegister = new Booking(booking.getUserId(), booking.getConcertId(), concert.getPrice(), booking.getDateBooked());
            bookingRepository.insert(bookingToRegister);
        }

        model.put(ResponseInfo.INFO, ResponseInfo.BOOKING_SUCCEEDED);
        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

}
