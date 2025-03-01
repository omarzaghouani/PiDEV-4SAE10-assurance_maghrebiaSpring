package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.User;

import java.util.List;
import java.util.Optional;

public interface IRefundDetailsService {
    List<User> getAllusers();

    RefundDetails addRefund(RefundDetails refundDetails);

  Optional<RefundDetails> getRefundById(int id);

  List<RefundDetails> getAllRefunds();

  RefundDetails updateRefund(int id, RefundDetails refundDetails);

  void deleteRefund(int id);

    List<RefundDetails> findByUser_Id(int userId);

    RefundDetails processRefund(RefundDetails refund);
}
