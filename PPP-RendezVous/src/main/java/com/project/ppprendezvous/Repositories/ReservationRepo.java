package com.project.ppprendezvous.Repositories;

import com.project.ppprendezvous.Entity.ReservationMedicament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<ReservationMedicament,Long> {
    List<ReservationMedicament> findReservationMedicamentByUsername(String username);

    List<ReservationMedicament> findReservationMedicamentsByExpired(boolean b);

    List<ReservationMedicament> findReservationMedicamentsByExpiredAndUsername(boolean b, String username);
}
