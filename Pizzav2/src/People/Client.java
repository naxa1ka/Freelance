package People;

import District.District;

public class Client extends User {
    private District district;

    public Client(String login, String password, District district) {
        super(login, password);
        this.district = district;
    }


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
