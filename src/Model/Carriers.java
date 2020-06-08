package Model;

public class Carriers {
    private String Digi;
    private String Vodafone;
    private String Orange;

    public Carriers (String Digi, String Vodafone, String Orange){
        this.Digi=Digi;
        this.Orange=Orange;
        this.Vodafone=Vodafone;
    }

    public String getDigi() {
        return Digi;
    }

    public String getOrange() {
        return Orange;
    }

    public String getVodafone() {
        return Vodafone;
    }

    public void setDigi(String digi) {
        Digi = digi;
    }

    public void setOrange(String orange) {
        Orange = orange;
    }

    public void setVodafone(String vodafone) {
        Vodafone = vodafone;
    }
}
