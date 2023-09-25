package starter.Data;

public class userTable {
    private String email;
    private String display_name;
    private String role;
    private String status;
    private java.util.Date creation_time;
    private java.util.Date terms_agreement_time;
    private String terms_version;
    private java.util.Date last_update_time;
    private java.util.Date deletion_time;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(java.util.Date creation_time) {
        this.creation_time = creation_time;
    }

    public java.util.Date getTerms_agreement_time() {
        return terms_agreement_time;
    }

    public void setTerms_agreement_time(java.util.Date terms_agreement_time) {
        this.terms_agreement_time = terms_agreement_time;
    }

    public String getTerms_version() {
        return terms_version;
    }

    public void setTerms_version(String terms_version) {
        this.terms_version = terms_version;
    }

    public java.util.Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(java.util.Date last_update_time) {
        this.last_update_time = last_update_time;
    }

    public java.util.Date getDeletion_time() {
        return deletion_time;
    }

    public void setDeletion_time(java.util.Date deletion_time) {
        this.deletion_time = deletion_time;
    }
}