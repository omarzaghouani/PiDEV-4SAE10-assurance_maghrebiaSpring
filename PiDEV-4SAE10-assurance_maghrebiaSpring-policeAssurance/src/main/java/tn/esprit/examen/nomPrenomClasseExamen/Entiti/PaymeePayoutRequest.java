package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

public class PaymeePayoutRequest {
    private int refundId;
    private double amount;
    private String recipientEmail;
    private String note;

    // Getters and Setters
    public int getRefundId() { return refundId; }
    public void setRefundId(int refundId) { this.refundId = refundId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

}
