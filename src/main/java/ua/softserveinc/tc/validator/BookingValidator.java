package ua.softserveinc.tc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;

import java.util.Date;

/**
 * Created by TARAS on 25.06.2016.
 */
public class BookingValidator implements Validator {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;


    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Booking booking = (Booking) target;

        ValidationUtils.rejectIfEmpty(errors, ChildConstants.ID_CHILD, "Child have not chosen");
        ValidationUtils.rejectIfEmpty(errors, RoomConstants.ID_ROOM, "Room has not chosen");
        ValidationUtils.rejectIfEmpty(errors, BookingConstants.DB.BOOKING_START_TIME, "Start time has not chosen");
        ValidationUtils.rejectIfEmpty(errors, BookingConstants.DB.BOOKING_END_TIME, "End time has not chosen");

        Date startTime = booking.getBookingStartTime();
        Date endTime = booking.getBookingEndTime();
        Integer capacity = roomService.findById(booking.getRoom()).getCapacity();
        if (capacity <= bookingService.getBookings(startTime, endTime).stream().count()) {
            errors.reject("Room is full");
        }

        if (startTime.after(endTime)) {
            errors.reject("Start time can not be bigger than End time.");
        }


        Child child = booking.getChild();

        Long childId = roomService.findById(child.getId()).getId();
        if (childId == booking.getChild().getId()) {
            errors.reject("Child is already booked at this time.");
        }
    }
}

