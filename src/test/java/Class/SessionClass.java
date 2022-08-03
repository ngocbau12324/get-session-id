package Class;

public class SessionClass {
    private String session_id;
    private String created_date;
    private String expired_date;

    public SessionClass(SessionClass session) {
        this.session_id = session.getSession_id();
        this.created_date = session.getCreated_date();
        this.expired_date = session.getExpired_date();
    }

    public SessionClass(String session_id, String created_date, String expired_date) {
        this.session_id = session_id;
        this.created_date = created_date;
        this.expired_date = expired_date;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    @Override
    public String toString() {
        return "SessionClass{" +
                "session_id='" + session_id + '\'' +
                ", created_date='" + created_date + '\'' +
                ", expired_date='" + expired_date + '\'' +
                '}';
    }
}
