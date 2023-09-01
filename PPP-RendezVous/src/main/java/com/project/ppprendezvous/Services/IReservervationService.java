package com.project.ppprendezvous.Services;

import com.project.ppprendezvous.Entity.ReservationMedicament;
import com.sun.media.sound.InvalidDataException;

import java.util.List;

public interface IReservervationService {
    ReservationMedicament addReservation(ReservationMedicament rm, String username) throws InvalidDataException;

    ReservationMedicament addReservationMedicament(ReservationMedicament rm);

    void deleteReservationMedicament(Long id);

    ReservationMedicament updateReservationMedicament(ReservationMedicament rm, Long id) throws InvalidDataException;

    ReservationMedicament retrieveReservationMedicament(Long id);

    List<ReservationMedicament> retrieveAllReservationMedicament();


    void approveReservationMedicament(Long id) throws InvalidDataException;

    void cancelReservationMedicament(Long id);

    List<ReservationMedicament> retrieveReservationMedicamentByUsername(String username);

    List<ReservationMedicament> retrieveActiveReservationMedicament();

    List<ReservationMedicament> retrieveActiveReservationMedicamentByUsername(String username);

    String GenerateQrCodeWithUsername(Long id);

    String GenerateQrCodeWithoutUsername(Long id);
}
