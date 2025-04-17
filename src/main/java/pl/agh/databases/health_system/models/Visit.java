package pl.agh.databases.health_system.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.List;

@Node
public class Visit {
    @Id @GeneratedValue
    private Long id;
    private Date date;
    private double cost;
    private List<String> prescriptions; //should be separate type
    private String patientsCondition;

    public Visit() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getPatientsCondition() {
        return patientsCondition;
    }

    public void setPatientsCondition(String patientsCondition) {
        this.patientsCondition = patientsCondition;
    }
}
