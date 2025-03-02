package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

public class ContractStatistics {

    private long ongoing;
    private long completed;
    private long cancelled;

    // Constructors
    public ContractStatistics(long ongoing, long completed, long cancelled) {
        this.ongoing = ongoing;
        this.completed = completed;
        this.cancelled = cancelled;
    }

    // Getters and setters
    public long getOngoing() {
        return ongoing;
    }

    public void setOngoing(long ongoing) {
        this.ongoing = ongoing;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }
}