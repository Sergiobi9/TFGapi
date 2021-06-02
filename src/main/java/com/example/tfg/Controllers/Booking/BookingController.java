package com.example.tfg.Controllers.Booking;

import com.example.tfg.Entities.Booking.Booking;
import com.example.tfg.Entities.Booking.BookingTicketsList;
import com.example.tfg.Entities.Booking.RegisterBooking;
import com.example.tfg.Entities.Concert.Concert;
import com.example.tfg.Entities.Concert.ConcertHistory;
import com.example.tfg.Entities.Concert.ConcertLocation;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricing;
import com.example.tfg.Entities.Concert.Pricing.ConcertIntervalPricingDetails;
import com.example.tfg.Helpers.Constants;
import com.example.tfg.Helpers.DateUtils;
import com.example.tfg.Helpers.ResponseInfo;
import com.example.tfg.Repositories.Booking.BookingRepository;
import com.example.tfg.Repositories.Concert.ConcertHistoryRepository;
import com.example.tfg.Repositories.Concert.ConcertLocationRepository;
import com.example.tfg.Repositories.Concert.ConcertRepository;
import com.example.tfg.Repositories.Concert.Pricing.ConcertIntervalPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertLocationRepository concertLocationRepository;

    @Autowired
    private ConcertHistoryRepository concertHistoryRepository;

    @Autowired
    private ConcertIntervalPricingRepository concertIntervalPricingRepository;

    @PostMapping("/create")
    public ResponseEntity registerBooking(@RequestBody RegisterBooking booking) {
        Map<Object, Object> model = new HashMap<>();

        String userId = booking.getUserId();
        String concertId = booking.getConcertId();
        Concert concert = concertRepository.findConcertById(concertId);
        if (concert == null){
            model.put(ResponseInfo.INFO, ResponseInfo.CONCERT_DOES_NOT_EXIST);
            return new ResponseEntity(model, HttpStatus.valueOf(200));
        }

        boolean foundLimitExceed = false;
        for (int i = 0; i < booking.getBookings().size(); i++){
            int bookings = booking.getBookings().get(i).getTicketsBought();
            String bookingTypeId = booking.getBookings().get(i).getId();

            ConcertIntervalPricing concertIntervalPricing = concertIntervalPricingRepository.findConcertIntervalPricingById(booking.getBookings().get(i).getId());

            for (int j = 0; j < bookings; j++){
                int numberTickets = concertIntervalPricing.getNumberTickets();
                int totalConcertBookings = bookingRepository.findAllByConcertIdAndBookingTypeId(booking.getConcertId(), bookingTypeId).size();

                int concertBookingsUpdated = totalConcertBookings + bookings;
                if (concertBookingsUpdated > numberTickets){
                    foundLimitExceed = true;
                } else{
                    double cost = booking.getBookings().get(i).getCost();
                    Booking bookingToRegister = new Booking(booking.getUserId(), bookingTypeId, booking.getConcertId(), cost, booking.getDateBooked());
                    bookingRepository.insert(bookingToRegister);
                }
            }
        }

        ConcertHistory concertHistory = concertHistoryRepository.findConcertHistoryByConcertId(concertId);
        if (concertHistory == null){
            concertHistory = registerConcertHistory(concertId);
            concertHistoryRepository.insert(concertHistory);
        }

        ArrayList<String> assistantsIds = concertHistory.getAssistantsIds();
        boolean userAlreadyAssistingToConcert = isUserAlreadyAssistingToConcert(assistantsIds, userId);

        if (!userAlreadyAssistingToConcert){
            assistantsIds.add(userId);
            concertHistory.setAssistantsIds(assistantsIds);
            concertHistoryRepository.save(concertHistory);
        }

        if (foundLimitExceed){
            model.put(ResponseInfo.INFO, ResponseInfo.BOOKING_EXCEEDED);
        } else {
            model.put(ResponseInfo.INFO, ResponseInfo.BOOKING_SUCCEEDED);
        }
        return new ResponseEntity(model, HttpStatus.valueOf(200));
    }

    @GetMapping("/all/user/{userId}/{currentDate}")
    public ResponseEntity getUserTicketsByUserId(@PathVariable String userId, @PathVariable String currentDate) {
        ArrayList<BookingTicketsList> bookingsToReturn = new ArrayList<>();
        ArrayList<String> concertsController = new ArrayList<>();

        List<Booking> userBookings = bookingRepository.findAllByUserId(userId);
        for (int i = 0; i < userBookings.size(); i++){
            String concertId = userBookings.get(i).getConcertId();
            Concert concert = concertRepository.findConcertById(concertId);
            if (DateUtils.currentDateIsBefore(concert.getDateStarts(), currentDate) && !concertsController.contains(concertId)){
                ConcertLocation concertLocation = concertLocationRepository.findByConcertId(concertId);
                List<Booking> userConcertBookingsList = bookingRepository.findAllByUserIdAndConcertId(userId, concertId);
                ArrayList<Booking> userConcertBookings = new ArrayList<>(userConcertBookingsList);
                BookingTicketsList bookingTicketsListToAdd = getTicketList(concertLocation, concert, userConcertBookings);

                bookingsToReturn.add(bookingTicketsListToAdd);
                concertsController.add(concertId);
            }
        }

        return new ResponseEntity(bookingsToReturn, HttpStatus.valueOf(200));
    }

    private BookingTicketsList getTicketList(ConcertLocation concertLocation, Concert concert, ArrayList<Booking> userConcertBookings){
        return new BookingTicketsList(concertLocation, concert, userConcertBookings);
    }

        private boolean isUserAlreadyAssistingToConcert(ArrayList<String> assistantsIds, String userId){
        return assistantsIds != null && assistantsIds.contains(userId);
    }

    private ConcertHistory registerConcertHistory(String concerId){
        return new ConcertHistory(concerId);
    }

}
